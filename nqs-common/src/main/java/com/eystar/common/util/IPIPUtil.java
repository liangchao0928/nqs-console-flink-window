package com.eystar.common.util;

import cn.hutool.core.util.StrUtil;
import net.ipip.ipdb.City;
import net.ipip.ipdb.CityInfo;
import net.ipip.ipdb.District;

import java.io.InputStream;

public class IPIPUtil {

	public static final String IP_SEPERATOR = "-";
	public static final String language = "CN";
	private static City cityDb;
	private static District districtDb;

	static {
		try {
			InputStream myin = IPIPUtil.class.getResourceAsStream("mydatavipday2.ipdb");

			if (myin == null) {
				myin = Thread.currentThread().getContextClassLoader().getResourceAsStream("mydatavipday2.ipdb");
			}
			// 可以加载jar包中的资源文件
			cityDb = new City(myin);
			InputStream qxin = IPIPUtil.class.getResourceAsStream("quxian.ipdb");
			if (qxin == null) {
				qxin = Thread.currentThread().getContextClassLoader().getResourceAsStream("quxian.ipdb");
			}
			districtDb = new District(qxin);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 *
	 * 查询IP的归属省市运营商-数组形式返回 添加: LiuQiang - 2020年1月16日 上午11:07:35<br>
	 * 修改: LiuQiang - 2020年1月16日 上午11:07:35<br>
	 *
	 * @param ip
	 * @return
	 */
	public static String[] getIpRegion(String ip) {
		try {
			String[] ss = cityDb.find(ip, language);
			String[] res = new String[4];
			res[0] = ss[0];
			res[1] = ss[1];
			res[2] = ss[2];
			res[3] = ss[4];
			return res;
		} catch (Exception e) {
//			LogKit.error(e.getMessage());
		}
		return null;
	}

	/**
	 *
	 * 查询IP的归属省市运营商-字符串形式返回 添加: LiuQiang - 2020年1月16日 上午11:07:35<br>
	 * 修改: LiuQiang - 2020年1月16日 上午11:07:35<br>
	 *
	 * @param ip
	 * @return
	 */
	public static String getIpRegionStr(String ip) {
		try {
			String[] ss = cityDb.find(ip, language);
			return ss[0] + " " + ss[1] + " " + ss[2] + " " + ss[4];
		} catch (Exception e) {
//			LogKit.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 获取ipdb数据 与使用datx库返回格式与原来一样
	 *
	 * @time 2019-10-17
	 * @author yxp
	 * @param ip
	 * @return
	 */
	public static String[] getIpAddress(String ip) {
		String[] arr = new String[8];
		try {
			CityInfo info = cityDb.findInfo(ip, language);
			if (null == info)
				return arr;
			String[] district = districtDb.find(ip, language);
			arr[0] = info.getCountryName();
			arr[1] = info.getRegionName();
			arr[2] = info.getCityName();
			arr[4] = info.getIspDomain();
			if (null != district) {
				arr[3] = district[3];
				arr[5] = district[4];
				arr[6] = district[6];
				arr[7] = district[7];
			} else {
				arr[3] = "";
				arr[5] = info.getChinaAdminCode();
				arr[6] = info.getLongitude();
				arr[7] = info.getLatitude();
			}
			return arr;
		} catch (Exception e) {
//			LogKit.error(e.getMessage());
		}
		return arr;
	}

	/**
	 *
	 * 检查是否是一个IP<br>
	 * 添加: 张俭 - 2020年7月21日 下午6:06:12<br>
	 * 修改: 张俭 - 2020年7月21日 下午6:06:12<br>
	 *
	 * @param ip
	 * @return
	 */
	public static boolean checkIp(String ip) {
		boolean result = ip.matches("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
		return result;
	}

	/**
	 * 根据ip 获取ip经纬度
	 *
	 */
	public static String getCoordinate(String ip) {
		try {
			CityInfo info = cityDb.findInfo(ip, language);
			if (StrUtil.isNotBlank(info.getLongitude()) && StrUtil.isNotBlank(info.getLatitude())) {
				return info.getLongitude() + "," + info.getLatitude();
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}

	public static void main(String[] args) {
		String[] ss = getIpAddress("106.11.251.76");
		for (String string : ss) {
			System.out.println(string);
		}
	}
}
