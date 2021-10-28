package com.eystar.console.handler.probe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.common.util.*;
import com.eystar.console.env.BeanFactory;
import com.eystar.console.util.InfoLoader;
import com.eystar.console.util.ValKit;
import com.eystar.gen.entity.CPHeartbeat;
import com.eystar.gen.entity.TPProbe;
import com.eystar.gen.service.IpRegionService;
import com.eystar.gen.service.PdcRegionService;
import com.eystar.gen.service.ProbeAccessTypeService;
import com.eystar.gen.service.ProbeService;
import com.eystar.gen.service.impl.IpRegionServiceImpl;
import com.eystar.gen.service.impl.PdcRegionServiceImpl;
import com.eystar.gen.service.impl.ProbeAccessTypeServiceImpl;
import com.eystar.gen.service.impl.ProbeServiceImpl;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eystar.console.handler.message.HeartBeatMessage;
import org.springframework.context.ApplicationContext;

public class WindowRegisterProcessFunction extends ProcessWindowFunction<HeartBeatMessage, List<CPHeartbeat>, Boolean, TimeWindow> {
	private static final long serialVersionUID = -7543689324733148489L;
	private final static Logger logger = LoggerFactory.getLogger(WindowRegisterProcessFunction.class);

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

		List<CPHeartbeat> datas = new ArrayList<CPHeartbeat>();

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
				if (message == null) {
					return;
				}
				logger.debug("探针ID = " + message.getProbeId() + "，开始注册");
				JSONObject infoObj = message.getInfoJson();
				String probeId = message.getProbeId();
				// 接收到探针心跳，实时更新探针状态到MySQL
				TPProbe probe = new TPProbe();
				probe.setId(probeId);
				probe.setInternetIp(message.getInternetIp());
				probe.setCreateTime(message.getTestTime());
				probe.setLastRegistTime(message.getTestTime());
				probe.setLastHeartbeatTime(message.getTestTime());

				probe.setSoftVer(infoObj.get("soft_ver") == null ? "" : infoObj.getString("soft_ver"));
				probe.setSoVer(infoObj.get("so_ver") == null ? "" : infoObj.getString("so_ver"));
				probe.setTaskQueueSize(infoObj.get("task_queue_size") == null ? 0 : infoObj.getInteger("task_queue_size"));
				probe.setTaskSize(infoObj.get("task_size") == null ? 0 : infoObj.getInteger("task_size"));


				// 根据探针的外网IP设置探针的地域
				ProbeHelper.setProbeAreaByIp(probe, message.getInternetIp());
				String probe_alias = StrUtil.isBlank(probe.getProvinceName()) ? "其他" : probe.getProvinceName();
				if (StrUtil.isNotBlank(probe.getCityName())) {
					probe_alias += "-" + probe.getCityName();
				}
				if (StrUtil.isNotBlank(probe.getDistrictName())) {
					probe_alias += "-" + probe.getDistrictName();
				}
				probe_alias += "-临时-" + UUIDKit.nextShortUUID();
				probe.setProbeAlias( probe_alias);

				// 插入MySQL数据库成功
				if (probeService.insertProbe(probe)>0) {
					// 写入redis
					JSONObject redisJson = new JSONObject();
					redisJson.put("id", probeId);
					redisJson.put("internet_ip", message.getInternetIp());
					redisJson.put("last_heartbeat_time", message.getTestTime());
					redisJson.put("probe_alias", probe_alias);
					redisJson.put("probe_alias_modified", 0);
					redisJson.put("province_code", probe.getProvinceCode() + "");
					redisJson.put("province_name", probe.getDistrictName());
					redisJson.put("city_code", probe.getCityCode() + "");
					redisJson.put("city_name", probe.getCityName());
					redisJson.put("district_code", probe.getDistrictCode() + "");
					redisJson.put("district_name", probe.getDistrictName());
					redisJson.put("town_code", probe.getTownCode() + "");
					redisJson.put("town_name", probe.getTownName());
					redisJson.put("region_path", probe.getRegionPath());
					RedisModifyHelper.updateProbe(probeId, redisJson.toJSONString());

					// 通知生成默认任务增量
					JSONObject taskSegJson = new JSONObject();
					taskSegJson.put("probeId", probeId);
					taskSegJson.put("provinceCode", probe.getProvinceCode() + "");
					taskSegJson.put("cityCode", probe.getCityCode() + "");
					taskSegJson.put("districtCode", probe.getDistrictCode() + "");
					redisUtils.lpush(XxlConfBean.getXxlValueByString("gw-keys.redis.queue.default.task"), taskSegJson.toJSONString());
					System.out.println("探针" + probeId + "注册完成，通知生成默认任务增量" + taskSegJson.toJSONString());

					// 插入探针心跳记录到bigdata
					CPHeartbeat probeHeartBeatInfo = new CPHeartbeat();
					probeHeartBeatInfo.setId(UUIDKit.nextShortUUID());
					probeHeartBeatInfo.setProbeId(probeId);
					probeHeartBeatInfo.setHeartbeatTime(message.getTestTime());
					probeHeartBeatInfo.setSoftVer(infoObj.get("soft_ver") == null ? "" : infoObj.getString("soft_ver"));
					probeHeartBeatInfo.setSoVer(infoObj.get("so_ver") == null ? "" : infoObj.getString("so_ver"));
					probeHeartBeatInfo.setTaskQueueSize(infoObj.get("task_queue_size") == null ? 0l : infoObj.getLong("task_queue_size"));
					probeHeartBeatInfo.setTaskSize(infoObj.get("task_size") == null ? 0l : infoObj.getLong("task_size"));
					probeHeartBeatInfo.setInternetIp(ValKit.defStr(message.getInternetIp()));


					// 封装更多时间标签
					Date date = new Date(message.getTestTime() * 1000);
					long heartbeat_time_d = DateUtil.beginOfDay(date).getTime() / 1000;
					probeHeartBeatInfo.setHeartbeatTimeH(DateUtil.beginOfDay(date).getTime() / 1000 + DateUtil.hour(date, true) * 3600);
					probeHeartBeatInfo.setHeartbeatTimeD(heartbeat_time_d);
					probeHeartBeatInfo.setHeartbeatTimeW(DateUtil.beginOfWeek(date).getTime() / 1000);
					probeHeartBeatInfo.setHeartbeatTimeM(DateUtil.beginOfMonth(date).getTime() / 1000);
					probeHeartBeatInfo.setHeartbeatTimePar(new Date(heartbeat_time_d * 1000));
					probeHeartBeatInfo.setCreateTime(System.currentTimeMillis() / 1000);

					datas.add(probeHeartBeatInfo);
					// 防止修改探针dns信息时探针上报接口信息发生冲突
					redisUtils.del(Constants.PROBE_ACCESS_AMEND + probeId);
				}else {
					logger.error("探针注册插入MySQL没有成功");
				}
			} catch (Exception e) {
				logger.error("探针注册出现异常，Message = " + message, e);
			}
		});
		out.collect(datas);
	}

	@Override
	public void close() throws Exception {
	}
}