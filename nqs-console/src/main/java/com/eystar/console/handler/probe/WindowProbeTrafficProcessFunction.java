package com.eystar.console.handler.probe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.util.UUIDKit;
import com.eystar.gen.entity.CPTraffic;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eystar.console.handler.message.GwInfoMessage;

public class WindowProbeTrafficProcessFunction extends ProcessWindowFunction<GwInfoMessage, List<CPTraffic>, Boolean, TimeWindow> {
	private static final long serialVersionUID = -7543689324733148489L;
	protected final static Logger logger = LoggerFactory.getLogger(WindowProbeTrafficProcessFunction.class);

	@Override
	public void open(Configuration parameters) throws Exception {

	}

	@Override
	public void process(Boolean isbadMsg, Context context, Iterable<GwInfoMessage> elements, Collector<List<CPTraffic>> out) throws Exception {
		List<CPTraffic> datas = new ArrayList<CPTraffic>();
		try {
			elements.forEach(message -> {
				JSONObject gwInfoJson = message.getMsgJson();
				String probeId = message.getProbeId();
				long time = message.getTestTime();
				JSONArray array = gwInfoJson.getJSONArray("traffic_info");
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

					// 封装更多时间标签
					Date date = new Date(time * 1000);

					long timesheet_d = DateUtil.beginOfDay(date).getTime() / 1000;

					record.setTimesheetH( DateUtil.beginOfDay(date).getTime() / 1000 + DateUtil.hour(date, true) * 3600);
					record.setTimesheetD( timesheet_d);
					record.setTimesheetW( DateUtil.beginOfWeek(date).getTime() / 1000);
					record.setTimesheetM( DateUtil.beginOfMonth(date).getTime() / 1000);

					record.setTimesheetPar( new Date(timesheet_d * 1000));
					record.setCreateTime( System.currentTimeMillis() / 1000);
					datas.add(record);
				}
			});
		} catch (Exception e) {
			logger.error("处理probe traffic process 出现异常，待处理的消息为 = " + elements, e);
		}
		out.collect(datas);
	}

	@Override
	public void close() throws Exception {
	}

}