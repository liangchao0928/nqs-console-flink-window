package com.eystar.console.sink;

import com.eystar.console.env.BeanFactory;
import com.eystar.gen.entity.CPTraffic;
import com.eystar.gen.service.TrafficService;
import com.eystar.gen.service.impl.TrafficServiceImpl;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ProbeTrafficWindowSink extends RichSinkFunction<List<CPTraffic>> {

    protected ApplicationContext beanFactory;

    private final static Logger logger = LoggerFactory.getLogger(ProbeTrafficWindowSink.class);

    private static TrafficService trafficService;


    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
                .getExecutionConfig().getGlobalJobParameters();
        beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);
        trafficService=beanFactory.getBean(TrafficServiceImpl.class);
    }

    public void invoke(List<CPTraffic> records, Context context) throws Exception {
        try {
            trafficService.insertList(records);
            System.out.println(records.size() );
            logger.debug("探针Traffic size= " + records.size() + "，插入流量历史记录到bigdate");
        } catch (Exception e) {
            logger.debug("探针Traffic size= " + records.size() + "，插入流量历史记录到Crate出错", e);
        }
    }
}
