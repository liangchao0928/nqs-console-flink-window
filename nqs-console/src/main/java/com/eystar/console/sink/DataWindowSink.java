package com.eystar.console.sink;

import com.eystar.common.util.XxlConfBean;
import com.eystar.console.env.BeanFactory;
import com.eystar.console.handler.message.DataMessage;
import com.eystar.console.handler.message.SaveData;
import com.eystar.console.handler.parser.AbstractDataParser;
import com.eystar.console.handler.parser.ParserContext;
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


public class DataWindowSink extends RichSinkFunction<SaveData> {
    private final static Logger logger = LoggerFactory.getLogger(DataWindowSink.class);
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private GwDataService gwDataService;
    private GwDataDetailService gwDataDetailService;
    private XxlConfBean xxlConfBean;
    protected ApplicationContext beanFactory;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
                .getExecutionConfig().getGlobalJobParameters();
        beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);
        gwDataService=beanFactory.getBean(GwDataServiceImpl.class);
        gwDataDetailService=beanFactory.getBean(GwDataDetailServiceImpl.class);
        xxlConfBean= beanFactory.getBean(XxlConfBean.class);
        xxlConfBean.init();
    }

    @Override
    public void invoke(SaveData data, Context context) throws Exception {
        System.out.println(data.toString());

    }


    @Override
    public void close() throws Exception {
    }


}
