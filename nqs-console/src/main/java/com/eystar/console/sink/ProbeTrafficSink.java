package com.eystar.console.sink;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.util.UUIDKit;
import com.eystar.console.env.BeanFactory;
import com.eystar.console.handler.message.GwInfoMessage;
import com.eystar.gen.entity.CPTraffic;
import com.eystar.gen.service.TrafficService;
import com.eystar.gen.service.impl.TrafficServiceImpl;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProbeTrafficSink   extends RichSinkFunction<GwInfoMessage> {

    protected ApplicationContext beanFactory;

    private final static Logger logger = LoggerFactory.getLogger(ProbeTrafficSink.class);

    private static TrafficService trafficService;


    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
                .getExecutionConfig().getGlobalJobParameters();
        beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);

        trafficService=beanFactory.getBean(TrafficServiceImpl.class);
    }

    public void invoke(GwInfoMessage message, Context context) throws Exception {
        JSONObject gwInfoJson = message.getMsgJson();
        String probeId = message.getProbeId();
        long time = message.getTestTime();
        JSONArray array = gwInfoJson.getJSONArray("traffic_info");

        List<CPTraffic> records = new ArrayList<CPTraffic>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            CPTraffic record = new CPTraffic();
            record.setId( UUIDKit.nextShortUUID());
            record.setProbeId(probeId);
            record.setAccessTypeName(object.getString("access_type_name"));
            record.setIp(object.getString("ip"));
            record.setTimesheet(time);
            record.setUpAvg(object.getDouble("up_avg"));
            record.setUpMax(object.getDouble("up_max"));
            record.setDownAvg( object.getDouble("down_avg"));
            record.setDownMax(object.getDouble("down_max"));
            record.setTrafficType("wan");
            record.setCreateTime(Long.valueOf(time));
            records.add(record);

            // 封装更多时间标签
            Date date = new Date(time * 1000);

            long timesheet_d = DateUtil.beginOfDay(date).getTime() / 1000;

            record.setTimesheetH( DateUtil.beginOfDay(date).getTime() / 1000 + DateUtil.hour(date, true) * 3600);
            record.setTimesheetD( timesheet_d);
            record.setTimesheetW( DateUtil.beginOfWeek(date).getTime() / 1000);
            record.setTimesheetM( DateUtil.beginOfMonth(date).getTime() / 1000);

            record.setTimesheetPar( new Date(timesheet_d * 1000));
            record.setCreateTime( System.currentTimeMillis() / 1000);

            records.add(record);
        }
        try {
//            trafficService.insertList(records);
            logger.debug("探针ID = " + probeId + "，插入流量历史记录到Crate");
        } catch (Exception e) {
            logger.debug("探针ID = " + probeId + "，插入流量历史记录到Crate出错", e);
        }
    }
}
