package com.eystar.console.sink;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.util.UUIDKit;
import com.eystar.console.env.BeanFactory;
import com.eystar.console.handler.message.GwInfoMessage;
import com.eystar.gen.entity.CPStatus;
import com.eystar.gen.service.PStatusService;
import com.eystar.gen.service.impl.PStatusServiceImpl;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Date;

public class ProbeStatusSink  extends RichSinkFunction<GwInfoMessage> {
    protected ApplicationContext beanFactory;

    private final static Logger logger = LoggerFactory.getLogger(ProbeStatusSink.class);

    private static PStatusService pStatusService;


    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
                .getExecutionConfig().getGlobalJobParameters();
        beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);

        pStatusService = beanFactory.getBean(PStatusServiceImpl.class);
    }

    public void invoke(GwInfoMessage message, Context context) throws Exception {
        JSONObject gwInfoJson = message.getMsgJson();
        String probeId = message.getProbeId();
        long time = message.getTestTime();

        JSONObject object = gwInfoJson.getJSONObject("status_info");

        CPStatus model = new CPStatus();
        model.setId(UUIDKit.nextShortUUID());
        model.setProbeId(probeId);
        model.setTimesheet(time);
        model.setCpuRate(object.getDouble("cpu_rate"));
        model.setCreateTime(time);
        model.setRamRate( object.getDouble("ram_rate"));
        model.setRunTime(object.getString("run_time"));

        // 封装更多时间标签
        Date date = new Date(time * 1000);

        long timesheet_d = DateUtil.beginOfDay(date).getTime() / 1000;

        model.setTimesheetH( DateUtil.beginOfDay(date).getTime() / 1000 + DateUtil.hour(date, true) * 3600);
        model.setTimesheetD( timesheet_d);
        model.setTimesheetW( DateUtil.beginOfWeek(date).getTime() / 1000);
        model.setTimesheetM( DateUtil.beginOfMonth(date).getTime() / 1000);

        model.setTimesheetPar( new Date(timesheet_d * 1000));
        model.setCreateTime( System.currentTimeMillis() / 1000);
        try {
//            pStatusService.insert(model);
            logger.debug("探针ID = " + probeId + "，插入探针状态信息到BigData");
        } catch (Exception e) {
            logger.debug("探针ID = " + probeId + "，插入探针状态信息到BigData出错", e);
        }
    }
}
