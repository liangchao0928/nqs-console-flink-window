package com.eystar.console.sink;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.common.util.*;
import com.eystar.console.env.BeanFactory;
import com.eystar.console.handler.message.HeartBeatMessage;
import com.eystar.console.handler.probe.ProbeAccessTypeHelper;
import com.eystar.console.handler.probe.ProbeHelper;
import com.eystar.console.handler.thread.ProbeRegistThread;
import com.eystar.console.util.InfoLoader;
import com.eystar.gen.entity.CPHeartbeat;
import com.eystar.gen.entity.TPProbe;
import com.eystar.gen.service.*;
import com.eystar.gen.service.impl.*;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Date;

public class ProbeRegistSink extends RichSinkFunction<HeartBeatMessage> {

//    private final static Logger logger = LoggerFactory.getLogger(HeartClickHouseSink.class);

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
        if (message == null) {
            return;
        }
        System.out.println("探针ID = " + message.getProbeId() + "，开始注册");

        JSONObject infoObj = message.getInfoJson();
        String id = message.getProbeId();
        TPProbe record = new TPProbe();
        record.setId(id);
        record.setInternetIp(message.getInternetIp());
        record.setCreateTime(message.getTestTime());
        record.setLastRegistTime(message.getTestTime());
        record.setLastHeartbeatTime(message.getTestTime());
        record.setTaskQueueSize(infoObj.get("task_queue_size") == null ? 0 : infoObj.getInteger("task_queue_size"));
        record.setTaskSize(infoObj.get("task_size") == null ? 0 : infoObj.getInteger("task_size"));
        record.setSoftVer(infoObj.getString("soft_ver"));
        record.setSoVer(infoObj.getString("so_ver"));
        ProbeHelper.setProbeAreaByIp(record, message.getInternetIp());
        String probe_alias = StrUtil.isBlank(record.getProvinceName()) ? "其他" : record.getProvinceName();
        if (StrUtil.isNotBlank(record.getCityName())) {
            probe_alias += "-" + record.getCityName();
        }
        if (StrUtil.isNotBlank(record.getDistrictName())) {
            probe_alias += "-" + record.getDistrictName();
        }
        probe_alias += "-临时-" + UUIDKit.nextShortUUID();
        record.setProbeAlias( probe_alias);

        if (probeService.insertProbe(record)>0) {
            // 写入redis
            JSONObject redisJson = new JSONObject();
            redisJson.put("id", id);
            redisJson.put("internet_ip", message.getInternetIp());
            redisJson.put("last_heartbeat_time", message.getTestTime());
            redisJson.put("probe_alias", probe_alias);
            redisJson.put("probe_alias_modified", 0);
            redisJson.put("province_code", record.getProvinceCode()+"");
            redisJson.put("province_name", record.getDistrictName());
            redisJson.put("city_code", record.getCityCode()+"");
            redisJson.put("city_name", record.getCityName());
            redisJson.put("district_code", record.getDistrictCode()+"");
            redisJson.put("district_name", record.getDistrictName());
            redisJson.put("town_code", record.getTownCode()+"");
            redisJson.put("town_name", record.getTownName());
            redisJson.put("region_path", record.getRegionPath());
            RedisModifyHelper.updateProbe(id, redisJson.toJSONString());
            JSONObject taskSegJson = new JSONObject();
            taskSegJson.put("probeId", id);
            taskSegJson.put("provinceCode", record.getProvinceCode()+"");
            taskSegJson.put("cityCode", record.getCityCode()+"");
            taskSegJson.put("districtCode", record.getDistrictCode()+"");
            redisUtils.lpush(XxlConfBean.getXxlValueByString("gw-keys.redis.queue.default.task"), taskSegJson.toJSONString());
            System.out.println("探针" + id + "注册完成，通知生成默认任务增量" + taskSegJson.toJSONString());

            // 插入探针心跳记录到bigdata
            CPHeartbeat probeHeartBeatInfo=new CPHeartbeat();
            probeHeartBeatInfo.setId( UUIDKit.nextShortUUID());
            probeHeartBeatInfo.setProbeId(id);
            probeHeartBeatInfo.setHeartbeatTime(message.getTestTime());
            probeHeartBeatInfo.setSoftVer(infoObj.get("soft_ver") == null ? "" : infoObj.getString("soft_ver"));
            probeHeartBeatInfo.setSoVer(infoObj.get("so_ver") == null ? "" : infoObj.getString("so_ver"));
            probeHeartBeatInfo.setTaskQueueSize(infoObj.get("task_queue_size") == null ? 0l : infoObj.getLong("task_queue_size"));
            probeHeartBeatInfo.setTaskSize(infoObj.get("task_size") == null ? 0l : infoObj.getLong("task_size"));
            probeHeartBeatInfo.setInternetIp(message.getInternetIp());


            // 封装更多时间标签
            Date date = new Date(message.getTestTime() * 1000);

            long heartbeat_time_d = DateUtil.beginOfDay(date).getTime() / 1000;

            probeHeartBeatInfo.setHeartbeatTimeH( DateUtil.beginOfDay(date).getTime() / 1000 + DateUtil.hour(date, true) * 3600);
            probeHeartBeatInfo.setHeartbeatTimeD( heartbeat_time_d);
            probeHeartBeatInfo.setHeartbeatTimeW( DateUtil.beginOfWeek(date).getTime() / 1000);
            probeHeartBeatInfo.setHeartbeatTimeM( DateUtil.beginOfMonth(date).getTime() / 1000);

            probeHeartBeatInfo.setHeartbeatTimePar( new Date(heartbeat_time_d * 1000));
            probeHeartBeatInfo.setCreateTime( System.currentTimeMillis() / 1000);


            try {
                System.out.println("ProbeRegistSink"+probeHeartBeatInfo.toString());
//                heartbeatService.insert(probeHeartBeatInfo);
                System.out.println("探针id = " + id + ", 注册心跳插入bigdata探针心跳信息表完成");
            } catch (Exception e) {
                System.out.println("探针id = " + id + ", 注册心跳插入bigdata探针心跳信息表错误"+e.getMessage());
            }
        } else {
            System.out.println("探针注册插入MySQL没有成功");
        }

    }


    @Override
    public void close() throws Exception {
        super.close();
    }

}
