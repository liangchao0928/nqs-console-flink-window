package com.eystar.console.handler.probe;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.util.IPHelper;
import com.eystar.gen.entity.TPProbe;

public class ProbeHelper {

	/**
	 * 根据探针IP，填充探针的地域和经纬度。封装完成后，探针中会添加
	 * province_code,province_name,city_code,city_name,district_code,district_name,region_path,lat,lng
	 * 等属性
	 * 
	 * @param ip
	 * @return
	 */
	public static void setProbeAreaByIp(TPProbe probe, String ip) {
		JSONObject object = IPHelper.getIpInfo(ip);
		probe.setProvinceCode(Long.valueOf( object.getString("province_code")));
		probe.setProvinceName( object.getString("province_name"));
		probe.setCityCode( Long.valueOf(object.getString("city_code")));
		probe.setCityName( object.getString("city_name"));
		probe.setDistrictCode(Long.valueOf( object.getString("district_code")));
		probe.setDistrictName( object.getString("district_name"));
		probe.setLat(Float.valueOf(  StrUtil.isNotBlank(object.getString("latitude"))?object.getString("latitude"):0+""));
		probe.setLng(Float.valueOf( StrUtil.isNotBlank(object.getString("longitude"))?object.getString("longitude"):0+""));
		probe.setRegionPath( "/100000/" + (StrUtil.isBlank(object.getString("province_code")) ? "" : (object.getString("province_code") + "/" + (StrUtil.isBlank(object.getString("city_code")) ? "" : (object.getString("city_code") + "/" + (StrUtil.isBlank(object.getString("district_code")) ? "" : (object.getString("district_code") + "/")))))));
	}

}