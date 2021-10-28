package com.eystar.console.sink;

import com.eystar.console.env.BeanFactory;
import com.eystar.gen.entity.CPPon;
import com.eystar.gen.service.PPonService;
import com.eystar.gen.service.impl.PPonServiceImpl;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ProbePonWindowSink extends RichSinkFunction<List<CPPon>> {
    protected ApplicationContext beanFactory;

    private final static Logger logger = LoggerFactory.getLogger(ProbePonWindowSink.class);

    private PPonService pPonService;


    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
                .getExecutionConfig().getGlobalJobParameters();
        beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);

        pPonService = beanFactory.getBean(  PPonServiceImpl.class);
    }

    public void invoke(List<CPPon> models, Context context) throws Exception {

        try {
            pPonService.insertList(models);
            logger.debug("探针Pon = " + models.size() + "，插入探针PON口信息到Crate");
        } catch (Exception e) {
            logger.debug("探针Pon = " + models.size() + "，插入探针PON口信息到Crate出错", e);
        }
    }
}
