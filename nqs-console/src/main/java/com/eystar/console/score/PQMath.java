package com.eystar.console.score;

import cn.hutool.core.util.StrUtil;

public class PQMath {

	public static boolean compare(String start, String end, float compareNum) {
		if (StrUtil.isBlank(start)) { // 格式为(+,end] 说明开始时间为空
			String[] endStr = StrUtil.split(end, "_");
			float endNum = Float.valueOf(endStr[0]);
			int endType = Integer.valueOf(endStr[1]);
			return endType == 0 ? compareNum < endNum : compareNum <= endNum;
		}

		if (StrUtil.isBlank(end)) { // 如果格式为(start,+] 说明结束时间为空
			String[] startStr = StrUtil.split(start, "_");
			float startNum = Float.valueOf(startStr[0]);
			int startType = Integer.valueOf(startStr[1]);
			return startType == 0 ? compareNum > startNum : compareNum >= startNum;
		}

		String[] startStr = StrUtil.split(start, "_");
		float startNum = Float.valueOf(startStr[0]);
		int startType = Integer.valueOf(startStr[1]);

		String[] endStr = StrUtil.split(end, "_");
		float endNum = Float.valueOf(endStr[0]);
		int endType = Integer.valueOf(endStr[1]);

		if (startType == 1 && endType == 1) {
			return compareNum >= startNum && compareNum <= endNum;
		} else if (startType == 1 && endType == 0) {
			return compareNum >= startNum && compareNum < endNum;
		} else if (startType == 0 && endType == 1) {
			return compareNum > startNum && compareNum <= endNum;
		} else {
			return compareNum > startNum && compareNum < endNum;
		}
	}

	/**
	 * 
	 * <p>
	 * 是否包含异常值，包含返回true，否则返回false
	 * </p>
	 * 
	 * @since 2021年7月14日 上午11:20:00
	 * @author zhangjian
	 * @param outlier
	 * @param compareNum
	 * @return
	 */
	public static boolean eqOutlier(String outlier, float compareNum) {
		int i = 0;
		if (StrUtil.isNotBlank(outlier)) {
			for (String value : outlier.split(",")) {
				String symbol = StrUtil.split(value, ":")[0];
				String val = StrUtil.split(value, ":")[1];
				switch (symbol) {
				case ">":
					if (compareNum > Float.valueOf(val))
						i++;
					break;
				case "<":
					if (compareNum < Float.valueOf(val))
						i++;
					break;
				case ">=":
					if (compareNum >= Float.valueOf(val))
						i++;
					break;
				case "<=":
					if (compareNum <= Float.valueOf(val))
						i++;
					break;
				default:
					if (compareNum == Float.valueOf(val))
						i++;
					break;
				}
			}
		}
		return i > 0;
	}

	/**
	 * 上线分数比较，表示值越小越好
	 * 
	 * @param sectionMin
	 * @param sectionMax
	 * @param resutlMin
	 * @param resultMax
	 * @param factNum
	 * @return
	 */
	public static float compScoreUp(float sectionMin, float sectionMax, float resutlMin, float resultMax, float factNum) {
		return sectionMin + (sectionMax - sectionMin) * (resultMax - factNum) / (resultMax - resutlMin);
	}

	/**
	 * 下线分数比较，表示值越大越好
	 * 
	 * @param sectionMin
	 * @param sectionMax
	 * @param resutlMin
	 * @param resultMax
	 * @param factNum
	 * @return
	 */
	public static float compScoreDown(float sectionMin, float sectionMax, float resutlMin, float resultMax, float factNum) {
		return sectionMin + (sectionMax - sectionMin) * (factNum - resutlMin) / (resultMax - resutlMin);
	}
}
