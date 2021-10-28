package com.eystar.console.util;

public class ValKit {

	/**
	 * 
	 * <p>
	 * 当值为null的时候，返回空字符串，兼容clickhouse
	 * </p>
	 * 
	 * @since 2021年9月23日 下午4:21:38
	 * @author zhangjian
	 * @param value
	 * @return
	 */
	public static String defStr(String value) {
		return value == null || "null".equals(value) ? "" : value;
	}

	/**
	 * 
	 * <p>
	 * 当值为null的时候，返回0，兼容clickhouse
	 * </p>
	 * 
	 * @since 2021年9月23日 下午4:21:38
	 * @author zhangjian
	 * @param value
	 * @return
	 */
	public static long defLong(Long value) {
		return value == null ? 0 : value.longValue();
	}

	/**
	 * 
	 * <p>
	 * 当值为null的时候，返回0，兼容clickhouse
	 * </p>
	 * 
	 * @since 2021年9月23日 下午4:21:38
	 * @author zhangjian
	 * @param value
	 * @return
	 */
	public static int defInt(Integer value) {
		return value == null ? 0 : value.intValue();
	}

	/**
	 * 
	 * <p>
	 * 当值为null的时候，返回0，兼容clickhouse
	 * </p>
	 * 
	 * @since 2021年9月23日 下午4:21:38
	 * @author zhangjian
	 * @param value
	 * @return
	 */
	public static double defDouble(Double value) {
		return value == null ? 0 : value.doubleValue();
	}
}
