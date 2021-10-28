package com.eystar.console.handler.thread;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.common.util.RedisModifyHelper;
import com.eystar.common.util.UUIDKit;
import com.eystar.common.util.XxlConfBean;
import com.eystar.console.handler.message.HeartBeatMessage;

import com.eystar.console.handler.probe.ProbeHelper;
import com.eystar.gen.entity.CPHeartbeat;
import com.eystar.gen.entity.TPProbe;
import com.eystar.gen.service.HeartbeatService;
import com.eystar.gen.service.ProbeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


public class ProbeRegistThread {

	private final static Logger logger = LoggerFactory.getLogger(ProbeRegistThread.class);


	private  static RedisUtils redisUtils;

	private static ProbeService probeService;

	private static HeartbeatService heartbeatService;


	public static void init(ProbeService ps, RedisUtils ru, HeartbeatService hs){
		 probeService=ps;
		 redisUtils=ru;
		 heartbeatService=hs;
	}


	public static void run(HeartBeatMessage message) {
		try {
			if (message == null) {
				return;
			}
			System.out.println("探针ID = " + message.getProbeId() + "，开始注册");
			JSONObject infoObj = message.getInfoJson();
			String id = message.getProbeId();
			TPProbe record = new TPProbe();
			record.setId(id);
			record.setInternetIp(message.getInternetIp());
			record.setCreateTime(message.getTestTime());
			record.setLastRegistTime(message.getTestTime());
			record.setLastHeartbeatTime(message.getTestTime());
			record.setTaskQueueSize(infoObj.get("task_queue_size") == null ? 0 : infoObj.getInteger("task_queue_size"));
			record.setTaskSize(infoObj.get("task_size") == null ? 0 : infoObj.getInteger("task_size"));
			record.setSoftVer(infoObj.getString("soft_ver"));
			record.setSoVer(infoObj.getString("so_ver"));
			ProbeHelper.setProbeAreaByIp(record, message.getInternetIp());
			String probe_alias = StrUtil.isBlank(record.getProvinceName()) ? "其他" : record.getProvinceName();
			if (StrUtil.isNotBlank(record.getCityName())) {
				probe_alias += "-" + record.getCityName();
			}
			if (StrUtil.isNotBlank(record.getDistrictName())) {
				probe_alias += "-" + record.getDistrictName();
			}
			probe_alias += "-临时-" + UUIDKit.nextShortUUID();
			record.setProbeAlias( probe_alias);

			if (probeService.insertProbe(record)>0) {
				// 写入redis
				JSONObject redisJson = new JSONObject();
				redisJson.put("id", id);
				redisJson.put("internet_ip", message.getInternetIp());
				redisJson.put("last_heartbeat_time", message.getTestTime());
				redisJson.put("probe_alias", probe_alias);
				redisJson.put("probe_alias_modified", 0);
				redisJson.put("province_code", record.getProvinceCode()+"");
				redisJson.put("province_name", record.getDistrictName());
				redisJson.put("city_code", record.getCityCode()+"");
				redisJson.put("city_name", record.getCityName());
				redisJson.put("district_code", record.getDistrictCode()+"");
				redisJson.put("district_name", record.getDistrictName());
				redisJson.put("town_code", record.getTownCode()+"");
				redisJson.put("town_name", record.getTownName());
				redisJson.put("region_path", record.getRegionPath());
				RedisModifyHelper.updateProbe(id, redisJson.toJSONString());
				JSONObject taskSegJson = new JSONObject();
				taskSegJson.put("probeId", id);
				taskSegJson.put("provinceCode", record.getProvinceCode()+"");
				taskSegJson.put("cityCode", record.getCityCode()+"");
				taskSegJson.put("districtCode", record.getDistrictCode()+"");
				redisUtils.lpush(XxlConfBean.getXxlValueByString("gw-keys.redis.queue.default.task"), taskSegJson.toJSONString());
				System.out.println("探针" + id + "注册完成，通知生成默认任务增量" + taskSegJson.toJSONString());

				// 插入探针心跳记录到bigdata
				CPHeartbeat probeHeartBeatInfo=new CPHeartbeat();
//				probeHeartBeatInfo.setId( UUIDKit.nextShortUUID());
//				probeHeartBeatInfo.setProbeId(id);
//				probeHeartBeatInfo.setHeartbeatTime(message.getTestTime());
//				probeHeartBeatInfo.setSoftVer(infoObj.get("soft_ver") == null ? "" : infoObj.getString("soft_ver"));
//				probeHeartBeatInfo.setSoVer(infoObj.get("so_ver") == null ? "" : infoObj.getString("so_ver"));
//				probeHeartBeatInfo.setTaskQueueSize(infoObj.get("task_queue_size") == null ? 0l : infoObj.getLong("task_queue_size"));
//				probeHeartBeatInfo.setTaskSize(infoObj.get("task_size") == null ? 0l : infoObj.getLong("task_size"));
//				probeHeartBeatInfo.setInternetIp(message.getInternetIp());
//				probeHeartBeatInfo.setProbeName(record.getProbeName());
//				probeHeartBeatInfo.setType(0);
//				probeHeartBeatInfo.setType(StrUtil.isNotBlank(record.getType()+"")?Integer.valueOf( record.getType()+""):0);
//				probeHeartBeatInfo.setCreateHour(DateUtil.beginOfHour(new Date(message.getTestTime().longValue() * 1000L)).getTime());
//				probeHeartBeatInfo.setCreateTime(message.getTestTime());
//				probeHeartBeatInfo.setMonthTime( DateUtil.beginOfMonth(new Date(message.getTestTime().longValue() * 1000L)).toJdkDate());




				try {
					heartbeatService.insert(probeHeartBeatInfo);
					System.out.println("探针id = " + id + ", 注册心跳插入bigdata探针心跳信息表完成");
				} catch (Exception e) {
					System.out.println("探针id = " + id + ", 注册心跳插入bigdata探针心跳信息表错误"+e.getMessage());
				}
			} else {
				System.out.println("探针注册插入MySQL没有成功");
			}
		} catch (Exception e) {
			System.out.println("探针注册出现异常"+e.getMessage());
		}
	}

}

