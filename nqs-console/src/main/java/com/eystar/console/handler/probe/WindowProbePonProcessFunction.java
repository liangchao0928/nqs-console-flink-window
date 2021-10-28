package com.eystar.console.handler.probe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.eystar.common.util.SmartGateWayUtil;
import com.eystar.common.util.UUIDKit;
import com.eystar.gen.entity.CPPon;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.eystar.console.handler.message.GwInfoMessage;

import cn.hutool.core.date.DateUtil;

public class WindowProbePonProcessFunction extends ProcessWindowFunction<GwInfoMessage, List<CPPon>, Boolean, TimeWindow> {
	private static final long serialVersionUID = -7543689324733148489L;
	protected final static Logger logger = LoggerFactory.getLogger(WindowProbePonProcessFunction.class);

	@Override
	public void open(Configuration parameters) throws Exception {
	}

	@Override
	public void process(Boolean isbadMsg, Context context, Iterable<GwInfoMessage> elements, Collector<List<CPPon>> out) throws Exception {
		List<CPPon> datas = new ArrayList<CPPon>();

		try {
			elements.forEach(message -> {
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
				datas.add(model);
			});

		} catch (Exception e) {
			logger.error("处理probe pon process 出现异常，待处理的消息为 = " + elements, e);
		}
		out.collect(datas);
	}

	@Override
	public void close() throws Exception {
	}

}