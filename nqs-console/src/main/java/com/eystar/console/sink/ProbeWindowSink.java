package com.eystar.console.sink;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.common.util.UUIDKit;
import com.eystar.common.util.XxlConfBean;
import com.eystar.console.env.BeanFactory;
import com.eystar.console.handler.message.GwInfoMessage;
import com.eystar.console.handler.thread.ProbeInfoThread;
import com.eystar.console.util.InfoLoader;
import com.eystar.gen.entity.CPPinfo;
import com.eystar.gen.entity.CPPinfoReal;
import com.eystar.gen.service.PInfoRealService;
import com.eystar.gen.service.PInfoService;
import com.eystar.gen.service.ProbeService;
import com.eystar.gen.service.impl.PInfoRealServiceImpl;
import com.eystar.gen.service.impl.PInfoServiceImpl;
import com.eystar.gen.service.impl.ProbeServiceImpl;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ProbeWindowSink extends RichSinkFunction<List<CPPinfo>> {

    private final static Logger logger = LoggerFactory.getLogger(ProbeWindowSink.class);


    private PInfoService pInfoService;
    private PInfoRealService pInfoRealService;


    protected ApplicationContext beanFactory;

    @Override
    public void open(Configuration parameters) throws Exception {
        ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
                .getExecutionConfig().getGlobalJobParameters();
        beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);
        pInfoService = beanFactory.getBean(PInfoServiceImpl.class);
        pInfoRealService = beanFactory.getBean(PInfoRealServiceImpl.class);
    }

    @Override
    public void invoke(List<CPPinfo> pinfoList, Context context) throws Exception {
        long start = System.currentTimeMillis();
        try {
            System.out.println(pinfoList.size());
            pInfoService.insertList(pinfoList);
        } catch (Exception e) {
            System.out.println("批量进行probe info sink出现异常"+ e.getMessage());
            logger.error("批量进行probe info sink出现异常", e);
        }
        List<CPPinfoReal> realList = new ArrayList<CPPinfoReal>();
        try {
            // 更新c_p_pinfo_real的实时记录表
            for (int i = 0; i < pinfoList.size(); i++) {
                CPPinfoReal cpPinfoReal = new  CPPinfoReal();
                CPPinfo cp= pinfoList.get(i);
                cpPinfoReal.setId(cp.getId());
                cpPinfoReal.setCreateTime(cp.getCreateTime());
                cpPinfoReal.setAccessTypeInfo(cp.getAccessTypeInfo());
                cpPinfoReal.setNeighborInfo(cp.getNeighborInfo());
                cpPinfoReal.setProbeId(cp.getProbeId());
                cpPinfoReal.setProbeInfo(cp.getProbeInfo());
                cpPinfoReal.setSgwInfo(cp.getSgwInfo());
                cpPinfoReal.setStatusInfo(cp.getStatusInfo());
                cpPinfoReal.setTimesheet(cp.getTimesheet());
                cpPinfoReal.setTrafficInfo(cp.getTrafficInfo());
                cpPinfoReal.setTimesheetD(cp.getTimesheetD());
                realList.add(cpPinfoReal);
            }
            pInfoRealService.insertList(realList);
        } catch (Exception e) {
            logger.error("批量进行probe info real sink出现异常", e);
        }
        logger.info("ProbeInfo Sink 入库信息条数 = {}，入库耗时ms = {}", pinfoList.size(), System.currentTimeMillis() - start);
    }


    @Override
    public void close() throws Exception {
    }




}
