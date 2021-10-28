package com.eystar.console.sink;

import com.eystar.console.env.BeanFactory;
import com.eystar.gen.entity.CPStatus;
import com.eystar.gen.service.PStatusService;
import com.eystar.gen.service.impl.PStatusServiceImpl;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ProbeStatusWindowSink extends RichSinkFunction<List<CPStatus>> {
    protected ApplicationContext beanFactory;

    private final static Logger logger = LoggerFactory.getLogger(ProbeStatusWindowSink.class);

    private static PStatusService pStatusService;


    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
                .getExecutionConfig().getGlobalJobParameters();
        beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);

        pStatusService = beanFactory.getBean(PStatusServiceImpl.class);
    }

    public void invoke(List<CPStatus> models, Context context) throws Exception {

        try {
            pStatusService.insertList(models);
            logger.debug("探针Status = " + models.size() + "，插入探针状态信息到BigData");
        } catch (Exception e) {
            logger.debug("探针Status = " + models.size() + "，插入探针状态信息到BigData出错", e);
        }
    }
}
