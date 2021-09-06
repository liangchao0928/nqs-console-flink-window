package com.eystar.console.sink;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.common.util.RedisModifyHelper;
import com.eystar.common.util.UUIDKit;
import com.eystar.common.util.XxlConfBean;
import com.eystar.console.env.BeanFactory;
import com.eystar.console.handler.message.GwInfoMessage;
import com.eystar.console.handler.probe.ProbeClickHouseHelper;
import com.eystar.console.handler.thread.ProbeInfoThread;
import com.eystar.console.util.InfoLoader;
import com.eystar.gen.entity.CPPinfo;
import com.eystar.gen.service.*;
import com.eystar.gen.service.impl.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Date;

public class ProbeClickHouseSink extends RichSinkFunction<GwInfoMessage> {

    private final static Logger logger = LoggerFactory.getLogger(ProbeClickHouseSink.class);


    private RedisUtils redisUtils;
    private PInfoService pInfoService;
    private ProbeAccessTypeService probeAccessTypeService;
    private ProbeService probeService;
    private TrafficService trafficService;
    private XxlConfBean xxlConfBean;
    private PPonService pPonService;
    private PStatusService pStatusService;



    protected ApplicationContext beanFactory;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
                .getExecutionConfig().getGlobalJobParameters();
        beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);
        redisUtils = beanFactory.getBean(RedisUtils.class);
        probeService = beanFactory.getBean(ProbeServiceImpl.class);
        pInfoService = beanFactory.getBean(PInfoServiceImpl.class);
        probeAccessTypeService = beanFactory.getBean(ProbeAccessTypeServiceImpl.class);
        trafficService=beanFactory.getBean(TrafficServiceImpl.class);
        pPonService = beanFactory.getBean(  PPonServiceImpl.class);
        pStatusService = beanFactory.getBean(PStatusServiceImpl.class);
        xxlConfBean= beanFactory.getBean(XxlConfBean.class);
        //初始化工具类数据
        InfoLoader.init(redisUtils,probeService);
        RedisModifyHelper.init(redisUtils);
        ProbeInfoThread.init(redisUtils,probeService);
        ProbeClickHouseHelper.init(redisUtils,probeAccessTypeService,trafficService,pPonService,pStatusService);
        xxlConfBean.init();
    }

    @Override
    public void invoke(GwInfoMessage message, Context context) throws Exception {
        try {
            if (message.getTestTime() == 0) {
                Long time = message.getMsgJson().getLongValue("time"); // 这个时间是探针上报的时间
                // 如果时间是3天前的数据，可能就是探针上时间不准导致，这个时候将上报的时间改为当前时间
                if (Math.abs(System.currentTimeMillis() / 1000 - time) > xxlConfBean.getXxlValueByLong("gw-console.probe.time.offset")) {
                    time = System.currentTimeMillis() / 1000;
                }
                message.setTestTime(time);
            }


            String probeId = message.getProbeId();
            long time = message.getTestTime();
            // 保存 网关状态信息、PON口状态、WAN口信息到probe表
            JSONObject gwInfoJson = message.getMsgJson();
            JSONObject probe_info = gwInfoJson.getJSONObject("probe_info"); // 探针状态信息，可为空
            JSONObject status_info = gwInfoJson.getJSONObject("status_info"); // 网关状态信息
            JSONArray access_type_info = gwInfoJson.getJSONArray("access_type_info");
            JSONArray traffic_info = gwInfoJson.getJSONArray("traffic_info");
            JSONObject sgw_info = gwInfoJson.getJSONObject("sgw_info");
            System.out.println("探针ID = " + probeId + "，上报探针信息");
            // 根据探针上报的信息，更新MySQL探针表中探针的信息
            if (probe_info != null && sgw_info != null && status_info != null) {
                probe_info.put("id", probeId);
                probe_info.put("loid", sgw_info.getString("loid"));
                probe_info.put("pppoe_username", sgw_info.getString("pppoe_username"));
                probe_info.put("ram_rate", status_info.get("ram_rate"));
                probe_info.put("cpu_rate", status_info.get("cpu_rate"));
                ProbeInfoThread.run(probe_info);
            }
                // 保存探针端口信息到MySQL 如果探针端口没有修改过才能保存信息
            ProbeClickHouseHelper.saveAccessTypeInfo(probeId, access_type_info, time);
            // 保留流量信息历史记录
            ProbeClickHouseHelper.saveTrafficInfo(probeId, traffic_info, time, "wan");
            // 保存状态信息历史记录
            ProbeClickHouseHelper.saveStatusInfo(probeId, status_info, time);
            // 保存PON口历史记录
            if (sgw_info != null) {
                JSONObject pon_info = sgw_info.getJSONObject("pon_info");
                ProbeClickHouseHelper.savePonInfo(probeId, probe_info == null ? null : probe_info.getString("pc"), pon_info, time);
            }

            // 存储探针所有信息到BigData中
            CPPinfo pinfo = new CPPinfo();
            pinfo.setId( UUIDKit.nextShortUUID());
            pinfo.setProbeId(probeId);
            pinfo.setTimesheet(time);
            pinfo.setProbeInfo(probe_info == null ? null : probe_info.toJSONString());
            pinfo.setAccessTypeInfo(access_type_info == null ? null : access_type_info.toJSONString());
            pinfo.setTrafficInfo( traffic_info == null ? null : traffic_info.toJSONString());
            pinfo.setSgwInfo(sgw_info == null ? null : sgw_info.toJSONString());
            pinfo.setStatusInfo(status_info == null ? null : status_info.toJSONString());
            pinfo.setMonthTime((DateUtil.beginOfMonth(new Date(time * 1000L)).toJdkDate()));
            pinfo.setCreateTime(time);

            try {
                pInfoService.insert(pinfo);
                //TODO 因为唯有索引，更新c_p_pinfo_real的实时记录表（不存在即插入，存在即更新）
                // 更新c_p_pinfo_real的实时记录表
//                String delsql = "ALTER TABLE c_p_pinfo_real DELETE WHERE probe_id =='" + pinfo.getStr("probe_id") + "'";
//                BigDataDb.use().delete(delsql);
//                BigDataDb.use().save("c_p_pinfo_real", pinfo);

                System.out.println("探针id = " + probeId + ", 插入BigData探针信息完成");
            } catch (Exception e) {
                System.out.println("探针id = " + probeId + ", 插入BigData探针信息错误"+ e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("处理探针信息失败，本次消息为 = " + message.toString()+ e.getMessage());
        }
    }


    @Override
    public void close() throws Exception {
        super.close();
    }




}
