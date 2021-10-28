package com.eystar.console.handler.parser;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.util.IPHelper;
import com.eystar.common.util.UUIDKit;
import com.eystar.console.handler.message.SaveData;
import com.eystar.gen.entity.gwdata.GwData;
import com.eystar.gen.entity.gwdata.GwHttpDetailData;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eystar.console.handler.message.DataMessage;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WindowDataProcessFunction extends ProcessWindowFunction<DataMessage, SaveData, String, TimeWindow> {
	private static final long serialVersionUID = -7543689324733148489L;
	protected final static Logger logger = LoggerFactory.getLogger(WindowDataProcessFunction.class);

	@Override
	public void open(Configuration parameters) throws Exception {
	}

	@Override
	public void process(String taskTypeName, Context context, Iterable<DataMessage> elements, Collector<SaveData> out) throws Exception {
		List<GwData> records = new ArrayList<GwData>();
		List<GwData> detailRecords = new ArrayList<GwData>();
		try {

			WindowDataParser parser = new WindowDataParser();
			SaveData data = new SaveData();
			data.setTaskTypeName(taskTypeName);
			elements.forEach(message -> {
				JSONObject jsonGwData = parser.process(message);
				if (jsonGwData != null) {
					if(StrUtil.equals("HTTP",taskTypeName)){
						System.out.println(jsonGwData.containsKey("detail")&&StrUtil.isNotBlank(jsonGwData.getString("detail")));
						if(jsonGwData.containsKey("detail")&&StrUtil.isNotBlank(jsonGwData.getString("detail"))) {
						    JSONArray array = JSONArray.parseArray(jsonGwData.getString("detail"));
							List<GwData> lgd = new ArrayList<GwData>();
							for (int i = 0; i < array.size(); i++) {
								JSONObject DetailDataJson = DataParserHelper.setColumns(jsonGwData);
								DetailDataJson.put("id", UUIDKit.nextShortUUID());// 重新生成任务ID
								DetailDataJson.put("parent_id", jsonGwData.getString("id"));

								JSONObject object = array.getJSONObject(i);
								Set<String> keys = object.keySet();
								for (String key : keys) {
									DetailDataJson.put(key, object.get(key));
								}

								if (StrUtil.isNotBlank(DetailDataJson.getString("host_ip"))) {
									JSONObject ipInfo = IPHelper.getIpInfo(DetailDataJson.getString("host_ip"));
									DetailDataJson.put("host_province", ipInfo.getString("province_name"));
									DetailDataJson.put("host_city", ipInfo.getString("city_name"));
									DetailDataJson.put("operator", ipInfo.getString("operator"));
								}
								GwHttpDetailData gwHttpDetailData = JSON.parseObject(DetailDataJson.toJSONString(), GwHttpDetailData.class);
//							fillEachDetailRecord(gwHttpDetailData);
								lgd.add(gwHttpDetailData);
							}
							if (lgd != null && lgd.size() > 0) {
								detailRecords.addAll(lgd);
							}
						}
					}
					GwData saveRecord= JSON.parseObject(jsonGwData.toJSONString(),WindowDataParser.getGwData(message.getTaskTypeName()));
					records.add(saveRecord);
				}
			});
			data.setRecords(records);
			data.setDetailRecords(detailRecords);
			System.out.println(data);
			out.collect(data);
		} catch (Exception e) {
			logger.error("处理probe data process出现异常，待处理的消息为 = " + elements, e);
		}

	}

	@Override
	public void close() throws Exception {
	}
}