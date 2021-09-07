package com.eystar.common.util;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.gen.entity.TPdcRegion;
import com.eystar.gen.service.IpRegionService;
import com.eystar.gen.service.PdcRegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPHelper {

	protected static final Logger logger = LoggerFactory.getLogger(IPHelper.class);
	private  static RedisUtils redisUtils;
	private static PdcRegionService pdcRegionService;
	private  static IpRegionService ipRegionService;

	public static final String DEFAULT_REGION_NAME = "其他";
	public static final String DEFAULT_REGION_CODE = "000000";
	public static final String DEFAULT_REGION_ISP = "其他";

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
		ipInfo.put("province_name", DEFAULT_REGION_NAME);// 省
		ipInfo.put("province_code", DEFAULT_REGION_CODE);
		ipInfo.put("city_name", DEFAULT_REGION_NAME);// 市
		ipInfo.put("city_code", DEFAULT_REGION_CODE);
		ipInfo.put("district_name", DEFAULT_REGION_NAME);// 市
		ipInfo.put("district_code", DEFAULT_REGION_CODE);
		ipInfo.put("operator", DEFAULT_REGION_ISP);// 运营商
		if (StrUtil.isBlank(ip) || !IPIPUtil.checkIp(ip)) {
			return ipInfo;
		}
		if (NetUtil.isInnerIP(ip)) {
			ipInfo.put("province_name", "局域网");
			return ipInfo;
		}
		String ip_json = redisUtils.hget(Constants.REDIS_KEY_IP_REGION, ip);
		if (StrUtil.isBlank(ip_json)) {
			JSONObject object = getIpIpInfo(ip);
			String[] codes = getAreaCodes(ip, object.getString("code"), object.getString("province_name"), object.getString("city_name"), object.getString("district_name"));
			object.put("province_code", codes[0]);
			object.put("city_code", codes[1]);
			object.put("district_code", codes[2]);
			object.remove("code");
			RedisModifyHelper.updateIpRegion(ip, object.toJSONString());
			return object;
		} else {
			return JSONObject.parseObject(ip_json);
		}
	}

	/**
	 *
	 * 离线的方式查询ip的地址信息，这个可以单独使用，返回json格式。<br>
	 * <pre>
	 * 	{
	 ip:"IP地址",
	 province_name:"省名称|其他",
	 city_name:"时名称|其他",
	 district_name:"区名称|其他",
	 operator:"运营商名称|其他",
	 code:"地区编码|''",
	 longitude:"经度|'0'",
	 latitude:"纬度|'0'",
	 }
	 * </pre>
	 * 添加: Administrator - 2021年8月21日 下午10:57:42<br>
	 * 修改: Administrator - 2021年8月21日 下午10:57:42<br>
	 * @param ip
	 * @return
	 */
	public static JSONObject getIpIpInfo(String ip) {
		String[] array = IPIPUtil.getIpAddress(ip); // ipip库查询
		JSONObject object = new JSONObject();
		object.put("ip", ip);
		object.put("province_name", (array.length > 1 && StrUtil.isNotBlank(array[1]) && !StrUtil.equals(array[1], "中国")) ? array[1] : DEFAULT_REGION_NAME);
		object.put("city_name", (array.length > 2 && StrUtil.isNotBlank(array[2])) ? array[2] : DEFAULT_REGION_NAME);
		object.put("district_name", (array.length > 3 && StrUtil.isNotBlank(array[3])) ? array[3] : DEFAULT_REGION_NAME);
		object.put("operator", (array.length > 4 && StrUtil.isNotBlank(array[4])) ? array[4] : DEFAULT_REGION_NAME);
		object.put("code", (array.length > 5 && StrUtil.isNotBlank(array[5])) ? array[5] : "");
		object.put("longitude", (array.length > 6 && StrUtil.isNotBlank(array[6])) ? array[6] : "0");
		object.put("latitude", (array.length > 7 && StrUtil.isNotBlank(array[7])) ? array[7] : "0");
		return object;
	}

	/**
	 *
	 * 根据省名称，地市名称，区名称返回对应的code，以数组形式返回。数组长度为3位，此方法不能单独使用，需要调用DB的方法，必须在Jfinal框架中运行<br>
	 * 添加: 张俭 - 2020年9月23日 下午10:08:10<br>
	 * 修改: 张俭 - 2020年9月23日 下午10:08:10<br>
	 *
	 * @param province_name
	 * @param city_name
	 * @param district_name
	 * @return [province_code,city_code,district_code]
	 */
	public static String[] getAreaCodes(String ip, String code, String province_name, String city_name, String district_name) {
		String region_json = "";
		// 如果IPIP库解析出IP的Code，使用Code反查地域
		if (StrUtil.isNotBlank(code)) {
			if (StrUtil.equals(code, "110000") && isBlankRegion(district_name)) { // 北京
				code = "110100";
			}else if (StrUtil.equals(code, "120000") && isBlankRegion(district_name)) { // 天津
				code = "120100";
			}else if (StrUtil.equals(code, "310000") && isBlankRegion(district_name)) { // 上海
				code = "310100";
			}else if (StrUtil.equals(code, "500000") && isBlankRegion(district_name)) { // 重庆
				code = "500100";
			}
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

				String province_name_db = namePaths.length > 1 ? namePaths[1] : DEFAULT_REGION_NAME;
				String province_code_db = codePaths.length > 1 ? codePaths[1] : DEFAULT_REGION_CODE;
				String city_name_db = namePaths.length > 2 ? namePaths[2] : DEFAULT_REGION_NAME;
				String city_code_db = codePaths.length > 2 ? codePaths[2] : DEFAULT_REGION_CODE;
				String district_name_db = namePaths.length > 3 ? namePaths[3] : DEFAULT_REGION_NAME;
				String district_code_db = codePaths.length > 3 ? codePaths[3] : DEFAULT_REGION_CODE;

				//TODO 比较IPIP库中的省、市、区的名称和数据库的名称是否一致，如果不一致，需要更新数据库，并且同步redis
//				if (namePaths.length > 1 && !isBlankRegion(province_name_db) && !isBlankRegion(province_name) && !StrUtil.equalsIgnoreCase(province_name, province_name_db)) { // 更新省名称
//					Db.update("update t_pdc_region set r_name = ? where r_name = ? and r_level = 1", province_name, province_name_db);
//					Db.update("update t_pdc_region set region_name_path = replace(region_name_path,'/全国/" + province_name_db + "/','/全国/" + province_name + "/')" + " where region_name_path like '/全国/" + province_name_db + "/%'");
//					// 修改完成后，更新redis
//					List<Record> records = Db.find("select * from t_pdc_region where region_name_path like '/全国/" + province_name + "/%'");
//					for (Record record : records) {
//						RedisModifyHelper.updateRegion(record.getStr("r_code"), record.toJson());
//						RedisModifyHelper.updateRegion(record.getStr("region_name_path"), record.toJson());
//					}
//					logger.warn("ipip解析出省名称和数据库中名称不一致，以ipip解析出来的更新，ip = " + ip + ",code = " + code + ",db = " + province_name_db + "，ip = " + province_name);
//				}
//				if (namePaths.length > 2 && !isBlankRegion(city_name_db) && !isBlankRegion(city_name) && !StrUtil.equalsIgnoreCase(city_name, city_name_db)) { // 更新市名称
//					Db.update("update t_pdc_region set r_name = ? where r_name = ? and r_level = 2", city_name, city_name_db);
//					Db.update("update t_pdc_region set region_name_path = replace(region_name_path,'/全国/" + province_name + "/" + city_name_db + "/','/全国/" + province_name + "/" + city_name + "/')" + " where region_name_path like '/全国/" + province_name + "/" + city_name_db + "/%'");
//					// 修改完成后，更新redis
//					List<Record> records = Db.find("select * from t_pdc_region where region_name_path like '/全国/" + province_name + "/" + city_name + "/%'");
//					for (Record record : records) {
//						RedisModifyHelper.updateRegion(record.getStr("r_code"), record.toJson());
//						RedisModifyHelper.updateRegion(record.getStr("region_name_path"), record.toJson());
//					}
//					logger.warn("ipip解析出市名称和数据库中名称不一致，以ipip解析出来的更新，ip = " + ip + ",code = " + code + ",db = " + city_name_db + "，ip = " + city_name);
//				}
//				if (namePaths.length > 3 && !isBlankRegion(district_name_db) && !isBlankRegion(district_name) && !StrUtil.equalsIgnoreCase(district_name, district_name_db)) { // 更新区名称
//					Db.update("update t_pdc_region set r_name = ? where r_name = ? and r_level = 3", district_name, district_name_db);
//					Db.update("update t_pdc_region set region_name_path = replace(region_name_path,'/全国/" + province_name + "/" + city_name + "/" + district_name_db + "/','/全国/" + province_name + "/" + city_name + "/" + district_name + "/')" + " where region_name_path like '/全国/" + province_name + "/" + city_name + "/" + district_name_db + "/%'");
//					// 修改完成后，更新redis
//					List<Record> records = Db.find("select * from t_pdc_region where region_name_path like '/全国/" + province_name + "/" + city_name + "/" + district_name + "/%'");
//					for (Record record : records) {
//						RedisModifyHelper.updateRegion(record.getStr("r_code"), record.toJson());
//						RedisModifyHelper.updateRegion(record.getStr("region_name_path"), record.toJson());
//					}
//					logger.warn("ipip解析出区名称和数据库中名称不一致，以ipip解析出来的更新，ip = " + ip + ",code = " + code + ",db = " + district_name_db + "，ip = " + district_name);
//				}
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

	private static boolean isBlankRegion(String value) {
		return StrUtil.isBlank(value) || StrUtil.equals(value, "null") || StrUtil.equals(DEFAULT_REGION_NAME, value);
	}

}
