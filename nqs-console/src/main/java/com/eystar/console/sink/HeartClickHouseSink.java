package com.eystar.console.sink;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.common.util.*;
import com.eystar.console.env.BeanFactory;
import com.eystar.console.handler.message.HeartBeatMessage;
import com.eystar.console.handler.probe.ProbeAccessTypeHelper;
import com.eystar.console.handler.thread.ProbeRegistThread;
import com.eystar.console.util.InfoLoader;
import com.eystar.gen.entity.CPHeartbeat;
import com.eystar.gen.entity.TPProbe;
import com.eystar.gen.service.*;
import com.eystar.gen.service.impl.*;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Date;

public class HeartClickHouseSink extends RichSinkFunction<HeartBeatMessage> {
    private final static Logger logger = LoggerFactory.getLogger(HeartClickHouseSink.class);

    private RedisUtils redisUtils;

    private ProbeService probeService;

    private IpRegionService ipRegionService;

    private PdcRegionService pdcRegionService;

    private HeartbeatService heartbeatService;

    private XxlConfBean xxlConfBean;

    private ProbeAccessTypeService probeAccessTypeService;

    protected ApplicationContext beanFactory;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
                .getExecutionConfig().getGlobalJobParameters();
        beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);
        redisUtils = beanFactory.getBean(RedisUtils.class);
        probeService = beanFactory.getBean(ProbeServiceImpl.class);
        ipRegionService = beanFactory.getBean(IpRegionServiceImpl.class);
        pdcRegionService = beanFactory.getBean(PdcRegionServiceImpl.class);
        heartbeatService = beanFactory.getBean(HeartbeatServiceImpl.class);
        probeAccessTypeService = beanFactory.getBean(ProbeAccessTypeServiceImpl.class);
        xxlConfBean= beanFactory.getBean(XxlConfBean.class);

        //初始化工具类数据
        InfoLoader.init(redisUtils,probeService);
        IPHelper.init(redisUtils,ipRegionService,pdcRegionService);
        RedisModifyHelper.init(redisUtils);
        ProbeRegistThread.init(probeService,redisUtils,heartbeatService);
        ProbeAccessTypeHelper.init(redisUtils,probeAccessTypeService);
        xxlConfBean.init();
    }

    @Override
    public void invoke(HeartBeatMessage message, Context context) throws Exception {
        if (message.getTestTime() == 0) {
            Long time = message.getMsgJson().getLongValue("time"); // 这个时间是探针上报的时间
            // 如果时间是3天前的数据，可能就是探针上时间不准导致，这个时候将上报的时间改为当前时间
				if (Math.abs(System.currentTimeMillis() / 1000 - time) > xxlConfBean.getXxlValueByLong("gw-console.probe.time.offset")) {
                time = System.currentTimeMillis() / 1000;
            }
            message.setTestTime(time);
        }

        JSONObject infoObj = message.getInfoJson();
        String probeId = message.getProbeId();
        JSONObject probeInfo = InfoLoader.loadProbe(probeId);
        System.out.println(probeInfo);
    if (probeInfo == null || probeInfo.isEmpty()) {
        ProbeRegistThread.run(message);
       }else {
           TPProbe probe = new TPProbe();
            probe.setId(probeId);
            JSONObject object = ProbeAccessTypeHelper.findDefaultAccessTypeFromRedis(probeId);
            if (object != null && StrUtil.equals(object.getString("connect_status"), "Disconnected")) { // 说明默认口没有连接，将状态改为离线
                probe.setStatus((short)20);
            } else {
                probe.setStatus((short) 10);
            }
            probe.setInternetIp(message.getInternetIp());
            probe.setLastHeartbeatTime(message.getTestTime());
            probe.setSoftVer(infoObj.get("soft_ver") == null ? "" : infoObj.getString("soft_ver"));
            probe.setSoVer(infoObj.get("so_ver") == null ? "" : infoObj.getString("so_ver"));
            probe.setTaskQueueSize(infoObj.get("task_queue_size") == null ? 0 : infoObj.getInteger("task_queue_size"));
            probe.setTaskSize(infoObj.get("task_size") == null ? 0 : infoObj.getInteger("task_size"));

            try {
                probeService.updateProbe(probe);
                System.out.println("探针ID = " + probeId + ", 心跳更新MySQL探针表完成，心跳时间 = " + message.getTestTime());
            } catch (Exception e) {
                System.out.println("探针ID = " + probeId + ", 心跳更新MySQL探针表错误" + e.getMessage());
            }

            probeInfo.put("last_heartbeat_time", message.getTestTime());
            probeInfo.put("internet_ip", message.getInternetIp());
            RedisModifyHelper.updateProbe(probeId, probeInfo.toJSONString());

            CPHeartbeat probeHeartBeatInfo = new CPHeartbeat();
            probeHeartBeatInfo.setId(UUIDKit.nextShortUUID());
            probeHeartBeatInfo.setProbeId(probeId);
            probeHeartBeatInfo.setProbeName(probeInfo.getString("probe_name"));
//            probeHeartBeatInfo.setProbeType(probeInfo.getString("type"));
            probeHeartBeatInfo.setHeartbeatTime(message.getTestTime());
            probeHeartBeatInfo.setSoftVer(infoObj.get("soft_ver") == null ? "" : infoObj.getString("soft_ver"));
            probeHeartBeatInfo.setSoVer(infoObj.get("so_ver") == null ? "" : infoObj.getString("so_ver"));
            probeHeartBeatInfo.setTaskQueueSize(infoObj.get("task_queue_size") == null ? 0 : infoObj.getInteger("task_queue_size"));
            probeHeartBeatInfo.setTaskSize(infoObj.get("task_size") == null ? 0 : infoObj.getInteger("task_size"));
            probeHeartBeatInfo.setInternetIp(message.getInternetIp());
            probeHeartBeatInfo.setType(probeInfo.getInteger("type"));
            probeHeartBeatInfo.setCreateHour(DateUtil.beginOfHour(new Date(message.getTestTime().longValue() * 1000L)).getTime());
            probeHeartBeatInfo.setCreateTime(message.getTestTime());
            probeHeartBeatInfo.setMonthTime(DateUtil.beginOfMonth(new Date(message.getTestTime().longValue() * 1000L)).toJdkDate());

        try {
            heartbeatService.insert(probeHeartBeatInfo);
            System.out.println("探针ID = " + probeId + ", 心跳插入BigData探针心跳信息表完成，心跳时间 = " + message.getTestTime());
        } catch (Exception e) {
            System.out.println("探针ID = " + probeId + ", 心跳插入BigData探针心跳信息表错误"+ e.getMessage());
        }
            redisUtils.del(Constants.PROBE_ACCESS_AMEND + probeId);
        }
    }


    @Override
    public void close() throws Exception {
        super.close();
    }


}
