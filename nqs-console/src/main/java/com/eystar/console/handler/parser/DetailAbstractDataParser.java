package com.eystar.console.handler.parser;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.util.IPHelper;
import com.eystar.common.util.UUIDKit;
import com.eystar.gen.entity.gwdata.GwData;
import com.eystar.gen.entity.gwdata.GwHttpDetailData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class DetailAbstractDataParser extends AbstractDataParser {

	private String taskTypeName;

	public DetailAbstractDataParser() {

	}

	public DetailAbstractDataParser(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}

	@Override
	public void prepare(JSONObject record) {

	}

	@Override
	public void after(JSONObject srcRecord) {
		JSONArray array = null;
		try {
			if(StrUtil.equals("HTTP",srcRecord.getString("task_type_name"))){
				array = JSONArray.parseArray(srcRecord.getString("detail"));
			}
			if (array == null) {
				return;
			}
		} catch (Exception e) {
			System.out.println("解析detai出错，不是JSON格式，类型 = " + taskTypeName + "，detail = " + srcRecord.getString("detail")+ e.getMessage());
			return;
		}
		List<GwData>  lgd=new ArrayList<GwData>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject DetailDataJson=DataParserHelper.setColumns(srcRecord);
			DetailDataJson.put("id", UUIDKit.nextShortUUID());// 重新生成任务ID
			DetailDataJson.put("parent_id",srcRecord.getString("id"));

			JSONObject object = array.getJSONObject(i);
			Set<String> keys= object.keySet();
			for(String key:keys){
				DetailDataJson.put(key,object.get(key));
			}

			if (StrUtil.isNotBlank(DetailDataJson.getString("host_ip"))) {
				JSONObject ipInfo = IPHelper.getIpInfo(DetailDataJson.getString("host_ip"));
				DetailDataJson.put("host_province",ipInfo.getString("province_name"));
				DetailDataJson.put("host_city",ipInfo.getString("city_name"));
				DetailDataJson.put("operator",ipInfo.getString("operator"));
			}
			GwHttpDetailData gwHttpDetailData=JSON.parseObject(DetailDataJson.toJSONString(),GwHttpDetailData.class);
			fillEachDetailRecord(gwHttpDetailData);
			lgd.add(gwHttpDetailData);
		}
		try {
			DataParserHelper.insertDataDetailList(lgd);
		} catch (Exception e) {
			System.out.println("批量保存" + taskTypeName + "的数据出现错误，父ID = " + srcRecord.getString("id") + "，待保存的JSON = " + srcRecord.getString("detail")+"/n Error:"+e.getMessage());
		}
	}
	public abstract void fillEachDetailRecord(GwData record);
}
