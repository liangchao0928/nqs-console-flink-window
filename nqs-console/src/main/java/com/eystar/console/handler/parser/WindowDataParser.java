package com.eystar.console.handler.parser;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.util.IPHelper;
import com.eystar.common.util.UUIDKit;
import com.eystar.common.util.XxlConfBean;
import com.eystar.console.handler.message.DataMessage;
import com.eystar.console.kafka.KafkaMessageProducer;
import com.eystar.console.util.InfoLoader;
import com.eystar.console.util.ScoreHelper;
import com.eystar.gen.entity.gwdata.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Date;


public  class WindowDataParser {

	protected static final Logger logger = LoggerFactory.getLogger(WindowDataParser.class);




	//方法入库
	public JSONObject process(DataMessage message) {
		try {
			System.out.println("任务类型："+message.getTaskTypeName()+"执行开始");
			// 对数据进行预处理
			JSONObject jsonGwData =fillRecord(message);
			if(jsonGwData==null ||jsonGwData.isEmpty()){
				return null;
			}

			// 拼接任务类型参数
			JSONObject MsgJson = message.getMsgJson();
			Field[] fields=getGwData(message.getTaskTypeName()).getDeclaredFields();
			for(int i=0;i<fields.length;i++){
				jsonGwData.put(fields[i].getName(),MsgJson.get(fields[i].getName()));
			}
			// 准备数据
			prepare(jsonGwData);
			ScoreHelper.fillScore(jsonGwData);
			jsonGwData.put("create_time",jsonGwData.getLong("test_time"));
			jsonGwData.put("month_time",(DateUtil.beginOfMonth(new Date(jsonGwData.getLong("test_time") * 1000L)).toJdkDate()));


			// 6、将保存后的数据消息发送到kafka中
			sendSavedToKafka(jsonGwData);
			System.out.println("任务类型："+message.getTaskTypeName()+"完成！");
			return jsonGwData;
		} catch (Exception e) {
			System.out.println("解析数据处理失败，本次数据的内容 = " + message.toString()+e.getMessage());
			return null;
		}
	}

	// 1.对数据进行预处理
	private  JSONObject fillRecord(DataMessage message) {
		JSONObject record=new JSONObject();

		// 封装基础信息
		record.put("id", UUIDKit.nextShortUUID());
		record.put("probe_id", message.getProbeId());
		record.put("task_from", message.getTaskFrom());
		record.put("task_id", message.getTaskId());
		record.put("task_type_name", message.getTaskTypeName());
		record.put("test_time", message.getTestTime());

		// 封装探针信息
		JSONObject probeJson = InfoLoader.loadProbe(message.getProbeId());
		record.put("probe_alias", probeJson.get("probe_alias"));
		record.put("probe_name", probeJson.get("probe_name"));
		record.put("pppoe_username", probeJson.get("pppoe_username"));
		record.put("loid", probeJson.get("loid"));
		record.put("serial_num", probeJson.get("sn"));
		record.put("probe_ip", probeJson.get("ip"));
		record.put("pc", probeJson.get("pc"));
		record.put("vendor", probeJson.get("vendor"));
		record.put("province_code", probeJson.get("province_code"));
		record.put("province_name", probeJson.get("province_name"));
		record.put("city_code", probeJson.get("city_code"));
		record.put("city_name", probeJson.get("city_name"));
		record.put("district_code", probeJson.get("district_code"));
		record.put("district_name", probeJson.get("district_name"));
		record.put("town_code", probeJson.get("town_code"));
		record.put("town_name", probeJson.get("town_name"));

		// 封装任务信息
		JSONObject taskJson = InfoLoader.loadTaskSrcDest(message.getTaskId());
		// 系统中已删除的任务处理
		try {
			if (taskJson.isEmpty()) {
				String error = "taskId = [" + message.getTaskId() + "],系统中不存在";
				System.out.println(error);
			}
		}catch (Exception e){
			System.out.println(e.getMessage());
			return  null;
		}
		// 更改获取方式获取key值发生变化
		JSONObject taskParamJson = InfoLoader.loadTaskParam(taskJson.getString("task_param_id"));
		record.put("task_md5", taskJson.get("task_md5"));
		record.put("task_param_id", taskJson.get("task_param_id"));
		record.put("task_param_name", taskParamJson == null ? "" : taskParamJson.get("task_param_name"));
		record.put("access_type_name", taskJson.get("access_type_name"));
		record.put("dest_id", taskJson.get("dest_id"));
		record.put("dest_name", taskJson.get("dest_name"));
		record.put("dest_addr", taskJson.get("dest_addr"));
		// 默认host_province、host_city、operator从任务中获取
		// 入库task_src_dest 值少
		record.put("host_province", taskJson.get("host_province"));// 省
		record.put("host_city", taskJson.get("host_city"));// 市
		record.put("operator", taskJson.get("operator"));// 运营商
		// 解析host_ip
		if (StrUtil.isNotBlank(record.getString("host_ip"))) {
			JSONObject ipInfo = IPHelper.getIpInfo(record.getString("host_ip"));
			record.put("host_province", ipInfo.get("province_name"));// 省
			record.put("host_city", ipInfo.get("city_name"));// 市
			record.put("operator", ipInfo.get("operator"));// 运营商
		}

		// 封装更多时间标签
		Date date = new Date(message.getTestTime() * 1000);
		record.put("test_time_h", DateUtil.beginOfDay(date).getTime() / 1000 + DateUtil.hour(date, true) * 3600);
		record.put("test_time_d", DateUtil.beginOfDay(date).getTime() / 1000);
		record.put("test_time_w", DateUtil.beginOfWeek(date).getTime() / 1000);
		record.put("test_time_m", DateUtil.beginOfMonth(date).getTime() / 1000);
		return record;
	}

	// 2.准备数据
	public  void prepare(JSONObject record){

		String typeName=record.getString("task_type_name");

		switch (typeName){
			case "HTTP":
				HttpDataParser http=new HttpDataParser();
				http.prepare(record);
				break;
			case "GAME":
				GameDataParser game=new GameDataParser();
				game.prepare(record);
				break;
			default:
				break;
		}

	};




	public static Class<? extends GwData> getGwData(String taskTypeName){

		if(StrUtil.equals("HTTP",taskTypeName)) {
			return  GwHttpData.class;
		}else	if(StrUtil.equals("PING",taskTypeName)){
			return GwPingData.class;
		}else	if(StrUtil.equals("DNS",taskTypeName)){
			return GwDnsData.class;
		}else if(StrUtil.equals("FLASH",taskTypeName)){
			return GwFlashData.class;
		}else{
			return GwData.class;
		}
	}

	private void sendSavedToKafka(JSONObject saveRecordJson) {
		JSONObject taskParam = InfoLoader.loadTaskParam(saveRecordJson.getString("task_param_id"));
		if (taskParam != null && !taskParam.isEmpty()) {
			String alarmTemplateId = taskParam.getString("alarm_template_id");
			if (StrUtil.isNotBlank(alarmTemplateId)) {
				JSONObject alarmTemplate = InfoLoader.loadAlarmTemplate(alarmTemplateId);
				if (alarmTemplate != null && !alarmTemplate.isEmpty()) {
					saveRecordJson.put("alarm_template_id", alarmTemplateId);
					saveRecordJson.put("alarm_template_json", alarmTemplate);
				}
			}
		}
		KafkaMessageProducer.send(XxlConfBean.getXxlValueByString("gw-console.kafka.topic.data.saved"),saveRecordJson.toJSONString());
	}

	public void  after(JSONObject record){

	}
}
