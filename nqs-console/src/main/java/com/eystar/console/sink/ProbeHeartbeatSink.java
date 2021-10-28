package com.eystar.console.sink;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.util.*;
import com.eystar.console.env.BeanFactory;
import com.eystar.console.handler.message.HeartBeatMessage;
import com.eystar.console.handler.probe.ProbeAccessTypeHelper;
import com.eystar.gen.entity.CPHeartbeat;
import com.eystar.gen.entity.TPProbe;
import com.eystar.gen.service.*;
import com.eystar.gen.service.impl.*;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.slf4j.LoggerFactory;
import java.util.List;


public class ProbeHeartbeatSink extends RichSinkFunction<List<CPHeartbeat>> {

    private final static Logger logger = LoggerFactory.getLogger(HeartClickHouseSink.class);

    private HeartbeatService heartbeatService;

    protected ApplicationContext beanFactory;

    @Override
    public void open(Configuration parameters) throws Exception {
        ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
                .getExecutionConfig().getGlobalJobParameters();
        beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);
        heartbeatService = beanFactory.getBean(HeartbeatServiceImpl.class);
//        ProbeRegistThread.init(probeService,redisUtils,heartbeatService);
    }


    public void invoke(List<CPHeartbeat> list, Context context)  {
            try {
                long start = System.currentTimeMillis();
                heartbeatService.insertList(list);
//                logger.info("heartbeat sink 入库信息条数 = {}，入库耗时ms = {}", list.size(), System.currentTimeMillis() - start);
                System.out.println("heartbeat sink 入库信息条数 = "+list.size()+"，入库耗时ms = "+(System.currentTimeMillis() - start));
            } catch (Exception e) {
                logger.error("批量进行heartbeat sink出现异常", e);
                System.out.println("批量进行heartbeat sink出现异常"+ e.getMessage());
            }
    }


    @Override
    public void close() throws Exception {
        super.close();
    }

    }
