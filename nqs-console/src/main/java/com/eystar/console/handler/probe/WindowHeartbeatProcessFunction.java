package com.eystar.console.handler.probe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.common.util.*;
import com.eystar.console.env.BeanFactory;
import com.eystar.console.handler.thread.ProbeRegistThread;
import com.eystar.console.util.InfoLoader;
import com.eystar.console.util.ValKit;
import com.eystar.gen.entity.CPHeartbeat;
import com.eystar.gen.entity.TPProbe;
import com.eystar.gen.service.*;
import com.eystar.gen.service.impl.*;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.eystar.console.handler.message.HeartBeatMessage;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.context.ApplicationContext;

public class WindowHeartbeatProcessFunction extends ProcessWindowFunction<HeartBeatMessage, List<CPHeartbeat>, Boolean, TimeWindow> {
	private static final long serialVersionUID = -7543689324733148489L;
	private final static Logger logger = LoggerFactory.getLogger(WindowHeartbeatProcessFunction.class);
	private static long sum = 0;

	private RedisUtils redisUtils;

	private ProbeService probeService;

	private IpRegionService ipRegionService;

	private PdcRegionService pdcRegionService;

	private XxlConfBean xxlConfBean;

	private ProbeAccessTypeService probeAccessTypeService;

	protected ApplicationContext beanFactory;


	@Override
	public void open(Configuration parameters) throws Exception {
		ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
				.getExecutionConfig().getGlobalJobParameters();
		beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);
		redisUtils = beanFactory.getBean(RedisUtils.class);
		probeService = beanFactory.getBean(ProbeServiceImpl.class);
		ipRegionService = beanFactory.getBean(IpRegionServiceImpl.class);
		pdcRegionService = beanFactory.getBean(PdcRegionServiceImpl.class);
		probeAccessTypeService = beanFactory.getBean(ProbeAccessTypeServiceImpl.class);
		xxlConfBean= beanFactory.getBean(XxlConfBean.class);

		//初始化工具类数据
		InfoLoader.init(redisUtils,probeService);
		IPHelper.init(redisUtils,ipRegionService,pdcRegionService);
		RedisModifyHelper.init(redisUtils);

		ProbeAccessTypeHelper.init(redisUtils,probeAccessTypeService);
		xxlConfBean.init();
	}

	@Override
	public void process(Boolean isbadMsg, Context context, Iterable<HeartBeatMessage> elements, Collector<List<CPHeartbeat>> out) throws Exception {
		long WstartTime = System.currentTimeMillis();
		sum = 0;
		List<CPHeartbeat> datas = new ArrayList<CPHeartbeat>();
		elements.forEach(message -> {
			sum += 1;
			try {
				if (message.getTestTime() == 0) {
					Long time = message.getMsgJson().getLongValue("time"); // 这个时间是探针上报的时间
					// 如果时间是3天前的数据，可能就是探针上时间不准导致，这个时候将上报的时间改为当前时间
					if (Math.abs(System.currentTimeMillis() / 1000 - time) > xxlConfBean.getXxlValueByLong("gw-console.probe.time.offset")) {
						time = System.currentTimeMillis() / 1000;
					}
					message.setTestTime(time);
				}
				if (message == null) {
					return ;
				}

				long startTime = System.currentTimeMillis();
				String probeId = message.getProbeId();
				JSONObject infoObj = message.getInfoJson();
				JSONObject probeInfo = message.getProbeJson();
				// 接收到探针心跳，实时更新探针状态到MySQL
				TPProbe probe = new TPProbe();
				probe.setId(probeId);
				JSONObject object = ProbeAccessTypeHelper.findDefaultAccessTypeFromRedis(probeId);
				if (object != null) {
					if (StrUtil.equalsIgnoreCase(object.getString("connect_status"), "connected")) { // 说明默认口没有连接，将状态改为离线
						probe.setStatus((short) 10);
						probeInfo.put("status", 10);
					} else {
						probe.setStatus( (short)20);
						probeInfo.put( "status",20);
					}
				} else {
					probe.setStatus((short) 20);
					probeInfo.put( "status",20);
				}

				probe.setInternetIp(message.getInternetIp());
				probe.setLastHeartbeatTime(message.getTestTime());
				probe.setSoftVer(infoObj.get("soft_ver") == null ? "" : infoObj.getString("soft_ver"));
				probe.setSoVer(infoObj.get("so_ver") == null ? "" : infoObj.getString("so_ver"));
				probe.setTaskQueueSize(infoObj.get("task_queue_size") == null ? 0 : infoObj.getInteger("task_queue_size"));
				probe.setTaskSize(infoObj.get("task_size") == null ? 0 : infoObj.getInteger("task_size"));

				try {
//                probeService.updateProbe(probe);
					logger.info("探针ID = " + probeId + ", 心跳更新MySQL探针表完成，心跳时间 = " + message.getTestTime());
				} catch (Exception e) {
					System.out.println("探针ID = " + probeId + ", 心跳更新MySQL探针表错误" + e.getMessage());
				}

				// 更新redis中探针的最后心跳时间和外网IP
				probeInfo.put("last_heartbeat_time", message.getTestTime());
				probeInfo.put("internet_ip", message.getInternetIp());
				RedisModifyHelper.updateProbe(probeId, probeInfo.toJSONString());

				CPHeartbeat probeHeartBeatInfo = new CPHeartbeat();

				probeHeartBeatInfo.setId(UUIDKit.nextShortUUID());
				probeHeartBeatInfo.setProbeId(probeId);
				probeHeartBeatInfo.setProbeName(ValKit.defStr(probeInfo.getString("probe_name")));
				probeHeartBeatInfo.setHeartbeatTime(message.getTestTime());
				probeHeartBeatInfo.setSoftVer(infoObj.get("soft_ver") == null ? "" : infoObj.getString("soft_ver"));
				probeHeartBeatInfo.setSoVer(infoObj.get("so_ver") == null ? "" : infoObj.getString("so_ver"));
				probeHeartBeatInfo.setTaskQueueSize(infoObj.get("task_queue_size") == null ? 0l : infoObj.getLong("task_queue_size"));
				probeHeartBeatInfo.setTaskSize(infoObj.get("task_size") == null ? 0l : infoObj.getLong("task_size"));
				probeHeartBeatInfo.setInternetIp(ValKit.defStr(message.getInternetIp()));
				probeHeartBeatInfo.setType(ValKit.defLong(probeInfo.getLong("type")));
				// 封装更多时间标签
				Date date = new Date(message.getTestTime() * 1000);

				long heartbeat_time_d = DateUtil.beginOfDay(date).getTime() / 1000;

				probeHeartBeatInfo.setHeartbeatTimeH( DateUtil.beginOfDay(date).getTime() / 1000 + DateUtil.hour(date, true) * 3600);
				probeHeartBeatInfo.setHeartbeatTimeD( heartbeat_time_d);
				probeHeartBeatInfo.setHeartbeatTimeW( DateUtil.beginOfWeek(date).getTime() / 1000);
				probeHeartBeatInfo.setHeartbeatTimeM( DateUtil.beginOfMonth(date).getTime() / 1000);
				probeHeartBeatInfo.setHeartbeatTimePar( new Date(heartbeat_time_d * 1000));
				probeHeartBeatInfo.setCreateTime( System.currentTimeMillis() / 1000);

				datas.add(probeHeartBeatInfo);
				long endTime = System.currentTimeMillis();
				logger.info("一条心跳完成拼装====》" + (endTime - startTime));
				// 防止修探针dns信息时探针上报接口信息发生冲突
				redisUtils.del(Constants.PROBE_ACCESS_AMEND + probeId);
			} catch (Exception e) {
				logger.error("处理probe heartbeat process出现异常，待处理的消息为 = " + message, e);
			}
		});
		long WendTime = System.currentTimeMillis();
		logger.info("窗口完成拼装------总用时===" + (WendTime - WstartTime) + "完成条数：" + sum);
		out.collect(datas);
	}

	@Override
	public void close() throws Exception {

	}
}