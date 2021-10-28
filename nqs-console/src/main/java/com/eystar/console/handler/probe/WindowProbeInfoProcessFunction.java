package com.eystar.console.handler.probe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.common.util.UUIDKit;
import com.eystar.common.util.XxlConfBean;
import com.eystar.console.env.BeanFactory;
import com.eystar.console.handler.thread.ProbeInfoThread;
import com.eystar.console.util.InfoLoader;
import com.eystar.gen.entity.CPPinfo;
import com.eystar.gen.service.PInfoRealService;
import com.eystar.gen.service.PInfoService;
import com.eystar.gen.service.ProbeService;
import com.eystar.gen.service.impl.PInfoRealServiceImpl;
import com.eystar.gen.service.impl.PInfoServiceImpl;
import com.eystar.gen.service.impl.ProbeServiceImpl;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.eystar.console.handler.message.GwInfoMessage;
import org.springframework.context.ApplicationContext;

public class WindowProbeInfoProcessFunction extends ProcessWindowFunction<GwInfoMessage, List<CPPinfo>, Boolean, TimeWindow> {
	private static final long serialVersionUID = -7543689324733148489L;
	private final static Logger logger = LoggerFactory.getLogger(WindowProbeInfoProcessFunction.class);

	private RedisUtils redisUtils;
	private PInfoService pInfoService;
	private PInfoRealService pInfoRealService;
	private ProbeService probeService;
	private XxlConfBean xxlConfBean;


	protected ApplicationContext beanFactory;

	@Override
	public void open(Configuration parameters) throws Exception {
		super.open(parameters);
		ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
				.getExecutionConfig().getGlobalJobParameters();
		beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);
		redisUtils = beanFactory.getBean(RedisUtils.class);
		probeService = beanFactory.getBean(ProbeServiceImpl.class);
		pInfoService = beanFactory.getBean(PInfoServiceImpl.class);
		pInfoRealService = beanFactory.getBean(PInfoRealServiceImpl.class);
		xxlConfBean= beanFactory.getBean(XxlConfBean.class);
		//初始化工具类数据
		InfoLoader.init(redisUtils,probeService);
		ProbeInfoThread.init(redisUtils,probeService);
		xxlConfBean.init();
	}

	@Override
	public void process(Boolean isbadMsg, Context context, Iterable<GwInfoMessage> elements, Collector<List<CPPinfo>> out) throws Exception {
		List<CPPinfo> datas = new ArrayList<CPPinfo>();

		elements.forEach(message -> {
			try {
				if (message.getTestTime() == 0) {
					Long time = message.getMsgJson().getLongValue("time"); // 这个时间是探针上报的时间
					// 如果时间是3天前的数据，可能就是探针上时间不准导致，这个时候将上报的时间改为当前时间
					if (Math.abs(System.currentTimeMillis() / 1000 - time) > xxlConfBean.getXxlValueByLong("gw-console.probe.time.offset")) {
						time = System.currentTimeMillis() / 1000;
					}
					message.setTestTime(time);
				}
				JSONObject gwInfoJson = message.getMsgJson();
				System.out.println(gwInfoJson);
				String probeId = message.getProbeId();
				long time = message.getTestTime();
				logger.debug("探针ID = " + probeId + "，开始上报探针信息");
				JSONObject probe_info = gwInfoJson.getJSONObject("probe_info"); // 探针状态信息，可为空
				JSONObject sgw_info = gwInfoJson.getJSONObject("sgw_info");
				JSONObject status_info = gwInfoJson.getJSONObject("status_info"); // 网关状态信息
				// 根据探针上报的信息，更新MySQL探针表中探针的信息
				if (probe_info != null && sgw_info != null && status_info != null) {
					probe_info.put("id", probeId);
					probe_info.put("loid", sgw_info.getString("loid"));
					probe_info.put("pppoe_username", sgw_info.getString("pppoe_username"));
					probe_info.put("ram_rate", status_info.get("ram_rate"));
					probe_info.put("cpu_rate", status_info.get("cpu_rate"));
					ProbeInfoThread.run(probe_info);
				}

				JSONArray access_type_info = gwInfoJson.getJSONArray("access_type_info");
				JSONArray traffic_info = gwInfoJson.getJSONArray("traffic_info");

				// 存储探针所有信息到BigData中
				CPPinfo pinfo = new CPPinfo();
				pinfo.setId( UUIDKit.nextShortUUID());
				pinfo.setProbeId(probeId);
				pinfo.setTimesheet(time);
				pinfo.setProbeInfo(probe_info == null ? null : probe_info.toJSONString());
				pinfo.setAccessTypeInfo(access_type_info == null ? null : access_type_info.toJSONString());
				pinfo.setTrafficInfo( traffic_info == null ? null : traffic_info.toJSONString());
				pinfo.setSgwInfo(sgw_info == null ? null : sgw_info.toJSONString());
				pinfo.setStatusInfo(status_info == null ? null : status_info.toJSONString());

				// 封装更多时间标签
				Date date = new Date(time * 1000);

				long timesheet_d = DateUtil.beginOfDay(date).getTime() / 1000;

				pinfo.setTimesheetH( DateUtil.beginOfDay(date).getTime() / 1000 + DateUtil.hour(date, true) * 3600);
				pinfo.setTimesheetD(timesheet_d);
				pinfo.setTimesheetW( DateUtil.beginOfWeek(date).getTime() / 1000);
				pinfo.setTimesheetM( DateUtil.beginOfMonth(date).getTime() / 1000);

				pinfo.setTimesheetPar( new Date(timesheet_d * 1000));
				pinfo.setCreateTime( System.currentTimeMillis() / 1000);
				datas.add(pinfo);
			} catch (Exception e) {
				logger.error("处理probe info process出现异常，待处理的消息为 = " + message, e);
			}
		});
		out.collect(datas);
	}

	@Override
	public void close() throws Exception {

	}

}