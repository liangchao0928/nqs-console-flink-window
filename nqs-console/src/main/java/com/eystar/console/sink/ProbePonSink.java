package com.eystar.console.sink;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.util.SmartGateWayUtil;
import com.eystar.common.util.UUIDKit;
import com.eystar.console.env.BeanFactory;
import com.eystar.console.handler.message.GwInfoMessage;
import com.eystar.gen.entity.CPPon;
import com.eystar.gen.service.PPonService;
import com.eystar.gen.service.impl.PPonServiceImpl;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Date;

public class ProbePonSink extends RichSinkFunction<GwInfoMessage> {
    protected ApplicationContext beanFactory;

    private final static Logger logger = LoggerFactory.getLogger(ProbePonSink.class);

    private PPonService pPonService;


    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
                .getExecutionConfig().getGlobalJobParameters();
        beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);

        pPonService = beanFactory.getBean(  PPonServiceImpl.class);
    }

    public void invoke(GwInfoMessage message, Context context) throws Exception {
        JSONObject gwInfoJson = message.getMsgJson();
        String probeId = message.getProbeId();
        long time = message.getTestTime();
        JSONObject sgw_info = gwInfoJson.getJSONObject("sgw_info");
        JSONObject probe_info = gwInfoJson.getJSONObject("probe_info"); // 探针状态信息，可为空
        String pc = probe_info == null ? null : probe_info.getString("pc");

        JSONObject pon_info = sgw_info.getJSONObject("pon_info");

        CPPon model = new CPPon();
        model.setId( UUIDKit.nextShortUUID());

        model.setCurrent( pon_info.getDouble("current"));
        model.setProbeId(probeId);
        Double rx_power = SmartGateWayUtil.parsePower(pc, pon_info.getDouble("rx_power"));
        model.setRxPower(rx_power);
        model.setTimesheet(time);
        model.setTemperature(pon_info.getDouble("temperature"));
        model.setTxPower(pon_info.getDouble("tx_power"));
        model.setVoltage(pon_info.getDouble("voltage"));

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
//            pPonService.insert(model);
            logger.debug("探针ID = " + probeId + "，插入探针PON口信息到Crate");
        } catch (Exception e) {
            logger.debug("探针ID = " + probeId + "，插入探针PON口信息到Crate出错", e);
        }
    }
}
