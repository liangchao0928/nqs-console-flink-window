package com.eystar.common.util;


import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.gen.entity.TMIpRegion;
import com.eystar.gen.entity.TPdcRegion;
import com.eystar.gen.service.IpRegionService;
import com.eystar.gen.service.PdcRegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class IPHelper {
	protected static final Logger logger = LoggerFactory.getLogger(IPHelper.class);
	private  static RedisUtils redisUtils;
	private static PdcRegionService pdcRegionService;
	private  static IpRegionService ipRegionService;


	public static void init(RedisUtils ru,IpRegionService irs,PdcRegionService prs) {
		redisUtils=ru;
		ipRegionService=irs;
		pdcRegionService=prs;
	}

	/**
	 * 
	 * 根据IP返回省市区json，先从redis中获取，在从IPIP中获取，此方法不能单独使用，需要调用DB的方法，必须在Jfinal框架中运行<br>
	 */
	public static JSONObject getIpInfo(String ip) {
		// 封装默认json
		JSONObject ipInfo = new JSONObject();
		ipInfo.put("ip", ip);
		ipInfo.put("province_name", "");// 省
		ipInfo.put("province_code", "000000");
		ipInfo.put("city_name", "");// 市
		ipInfo.put("city_code", "000000");
		ipInfo.put("district_name", "");// 市
		ipInfo.put("district_code", "000000");
		ipInfo.put("operator", "");// 运营商
		if (StrUtil.isBlank(ip) || !IPIPUtil.checkIp(ip)) {
			return ipInfo;
		}
		if (NetUtil.isInnerIP(ip)) {
			ipInfo.put("province_name", "局域网");
			return ipInfo;
		}
		String ip_json = redisUtils.hget(Constants.REDIS_KEY_IP_REGION, ip);
		if (StrUtil.isBlank(ip_json)) {
			TMIpRegion record =ipRegionService.findByIP(ip);
			String province, province_code, city, city_code, district, district_code, operator, longitude, latitude;
			if (record != null) {
				province = record.getProvinceName();
				province_code = record.getProvinceCode()+"";
				city = record.getCityName();
				city_code = record.getCityCode()+"";
				district = record.getDistrictName();
				district_code = record.getDistrictCode()+"";
				operator = record.getOperator();
				longitude = record.getLongitude();
				latitude = record.getLatitude();
			} else {
				String[] array = IPIPUtil.getIpAddress(ip); // ipip库查询
				System.out.println(ip+"IPIPUtil"+ Arrays.toString(array));
				province = (array.length > 1 && StrUtil.isNotBlank(array[1])) ? array[1] : "其他";
				city = (array.length > 2 && StrUtil.isNotBlank(array[2])) ? array[2] : "其他";
				district = (array.length > 3 && StrUtil.isNotBlank(array[3])) ? array[3] : "其他";
				operator = (array.length > 4 && StrUtil.isNotBlank(array[4])) ? array[4] : "其他";
				String code = (array.length > 5 && StrUtil.isNotBlank(array[5])) ? array[5] : "";
				longitude = (array.length > 6 && StrUtil.isNotBlank(array[6])) ? array[6] : "0";
				latitude = (array.length > 7 && StrUtil.isNotBlank(array[7])) ? array[7] : "0";
				String[] codes = getAreaCodes(ip,code, province, city, district);
				province_code = codes[0];
				city_code = codes[1];
				district_code = codes[2];
			}
			// 将ip信息存入redis中
			JSONObject ipRe = new JSONObject();
			ipRe.put("ip", ip);
			ipRe.put("province_name", province);
			ipRe.put("province_code", province_code);
			ipRe.put("city_name", city);
			ipRe.put("city_code", city_code);
			ipRe.put("district_name", district);
			ipRe.put("district_code", district_code);
			ipRe.put("operator", operator);
			ipRe.put("longitude", longitude);
			ipRe.put("latitude", latitude);
			RedisModifyHelper.updateIpRegion(ip, ipRe.toJSONString());
			return ipRe;
		} else {
			return JSONObject.parseObject(ip_json);
		}

	}


		public static String[] getAreaCodes(String ip,String code, String province_name, String city_name, String district_name) {
		String region_json = "";
		if (StrUtil.isNotBlank(code)) {
			region_json = redisUtils.hget(Constants.REDIS_KEY_REGION, code);
			if (StrUtil.isBlank(region_json)) {
				TPdcRegion record = pdcRegionService.findByRcode(Long.valueOf(code));
				if (record != null) {
					region_json = JSON.toJSONString(record);
				}
			}
			if (StrUtil.isNotBlank(region_json)) {
				JSONObject regionJson = JSONObject.parseObject(region_json);
				String pname = regionJson.getString("region_name_path"); // 得到 名称路径格式
				String pcode = regionJson.getString("region_path");
				String[] namePaths = pname.substring(1, pname.length() - 1).split("/");
				String[] codePaths = pcode.substring(1, pcode.length() - 1).split("/");

				String province_name_db = namePaths.length > 1 ? namePaths[1] : "其他";
				String province_code_db = codePaths.length > 1 ? codePaths[1] : "000000";
				String city_name_db = namePaths.length > 2 ? namePaths[2] : "其他";
				String city_code_db = codePaths.length > 2 ? codePaths[2] : "000000";
				String district_name_db = namePaths.length > 3 ? namePaths[3] : "其他";
				String district_code_db = codePaths.length > 3 ? codePaths[3] : "000000";

				//TODO 比较IPIP库中的省、市、区的名称和数据库的名称是否一致，如果不一致，需要更新数据库，并且同步redis
				/*// 比较IPIP库中的省、市、区的名称和数据库的名称是否一致，如果不一致，需要更新数据库，并且同步redis
				if (namePaths.length > 1 && !StrUtil.equalsIgnoreCase(province_name, province_name_db)) { // 更新省名称
					pdcRegionService.updateRname(province_name_db,province_name,1);
					pdcRegionService.updateRnameLike("/全国/"+province_name_db,"/全国/"+province_name);
					// 修改完成后，更新redis
					List<TPdcRegion> lt=pdcRegionService.selectByPathNameLike("/全国/"+province_name);
					for (TPdcRegion record : lt) {
						RedisModifyHelper.updateRegion(record.getrCode()+"",JSON.toJSONString(record));
						RedisModifyHelper.updateRegion(record.getRegionNamePath(), JSON.toJSONString(record));
					}
					logger.warn("ipip解析出省名称和数据库中名称不一致，以ipip解析出来的更新，ip = " + ip + ",code = " + code + ",db = " + province_name_db + "，ip = " + province_name);
				}
				if (namePaths.length > 2 && !StrUtil.equalsIgnoreCase(city_name, city_name_db)) { // 更新市名称
					pdcRegionService.updateRname(city_name_db,city_name,2);
					pdcRegionService.updateRnameLike("/全国/" + province_name + "/"+city_name_db,"/全国/" + province_name + "/"+city_name);
//					// 修改完成后，更新redis
					List<TPdcRegion> lt=pdcRegionService.selectByPathNameLike("/全国/"+province_name+"/"+city_name);
					for (TPdcRegion record : lt) {
						RedisModifyHelper.updateRegion(record.getrCode()+"",JSON.toJSONString(record));
						RedisModifyHelper.updateRegion(record.getRegionNamePath(), JSON.toJSONString(record));
					}
					logger.warn("ipip解析出市名称和数据库中名称不一致，以ipip解析出来的更新，ip = " + ip + ",code = " + code + ",db = " + city_name_db + "，ip = " + city_name);
				}
				if (namePaths.length > 3 && !StrUtil.equalsIgnoreCase(district_name, district_name_db)) { // 更新区名称
					pdcRegionService.updateRname(district_name_db,district_name,3);
					pdcRegionService.updateRnameLike("/全国/"+province_name+"/"+city_name+"/"+district_name_db,"/全国/"+province_name+"/"+city_name+"/"+district_name);
//					// 修改完成后，更新redis
					List<TPdcRegion> lt=pdcRegionService.selectByPathNameLike("/全国/"+province_name+"/"+city_name+"/"+district_name);
					for (TPdcRegion record : lt) {
						RedisModifyHelper.updateRegion(record.getrCode()+"",JSON.toJSONString(record));
						RedisModifyHelper.updateRegion(record.getRegionNamePath(), JSON.toJSONString(record));
					}
					logger.warn("ipip解析出区名称和数据库中名称不一致，以ipip解析出来的更新，ip = " + ip + ",code = " + code + ",db = " + district_name_db + "，ip = " + district_name);
				}*/
				System.out.println(Arrays.toString(new String[] { province_code_db, city_code_db, district_code_db }));
				return new String[] { province_code_db, city_code_db, district_code_db };
			}
		}

		logger.warn("没有传入code或者根据code没有查到信息，code=" + code + "，省=" + province_name + "，市=" + city_name + "，区=" + city_name);
		String[] rets = new String[] { "000000", "000000", "000000" };
		if (StrUtil.isBlank(province_name)) {
			return rets;
		}
		// 省
		province_name = StrUtil.replace(province_name, "省", "");
		province_name = StrUtil.replace(province_name, "市", "");
		region_json = redisUtils.hget(Constants.REDIS_KEY_REGION, "/全国/" + province_name + "/");
		if (StrUtil.isBlank(region_json)) {
			return rets;
		}
		JSONObject object = JSONObject.parseObject(region_json);
		rets[0] = object.getString("r_code");
		// 市
		if (StrUtil.isBlank(city_name)) {
			return rets;
		}
		city_name = StrUtil.replace(city_name, "市", "");
		region_json = redisUtils.hget(Constants.REDIS_KEY_REGION, "/全国/" + province_name + "/" + city_name + "/");
		if (StrUtil.isBlank(region_json)) {
			return rets;
		}
		object = JSONObject.parseObject(region_json);
		rets[1] = object.getString("r_code");
		// 区
		if (StrUtil.isBlank(district_name)) {
			return rets;
		}
		region_json = redisUtils.hget(Constants.REDIS_KEY_REGION, "/全国/" + province_name + "/" + city_name + "/" + district_name + "/");
		if (StrUtil.isBlank(region_json)) {
			return rets;
		}
		object = JSONObject.parseObject(region_json);
		rets[2] = object.getString("r_code");
		return rets;
	}




}
