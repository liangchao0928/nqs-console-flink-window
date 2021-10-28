package com.eystar.console.handler.probe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.eystar.common.util.UUIDKit;
import com.eystar.gen.entity.CPStatus;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.eystar.console.handler.message.GwInfoMessage;

import cn.hutool.core.date.DateUtil;

public class WindowProbeStatusProcessFunction extends ProcessWindowFunction<GwInfoMessage, List<CPStatus>, Boolean, TimeWindow> {
	private static final long serialVersionUID = -7543689324733148489L;
	protected final static Logger logger = LoggerFactory.getLogger(WindowProbeStatusProcessFunction.class);

	@Override
	public void open(Configuration parameters) throws Exception {

	}

	@Override
	public void process(Boolean isbadMsg, Context context, Iterable<GwInfoMessage> elements, Collector<List<CPStatus>> out) throws Exception {
		List<CPStatus> datas = new ArrayList<CPStatus>();
		try {
			elements.forEach(message -> {
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
				datas.add(model);
			});

		} catch (Exception e) {
			logger.error("处理probe status process 出现异常，待处理的消息为 = " + elements, e);
		}
		out.collect(datas);
	}

	@Override
	public void close() throws Exception {
	}

}