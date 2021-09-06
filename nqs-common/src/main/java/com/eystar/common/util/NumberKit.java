package com.eystar.common.util;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 数字工具类<br>
 */
public class NumberKit extends NumberUtil{

	/**
	 * 
	 * 转成int类型<br>
	 * 添加: zhangjian - 2020年1月14日 上午11:23:17<br>
	 * 修改: zhangjian - 2020年1月14日 上午11:23:17<br>
	 * @param number
	 * @param defaultValue
	 * @return
	 * @throws NumberFormatException
	 */
	public static Integer valueOfInt(String number,Integer defaultValue) {
		return StrUtil.isBlank(number) ? defaultValue : Integer.valueOf(number);
	}
	
	/**
	 * 
	 * 转成int类型<br>
	 * 添加: zhangjian - 2020年1月14日 上午11:23:17<br>
	 * 修改: zhangjian - 2020年1月14日 上午11:23:17<br>
	 * @param number
	 * @param defaultValue
	 * @return
	 * @throws NumberFormatException
	 */
	public static Integer valueOfInt(Integer number,Integer defaultValue) throws NumberFormatException {
		return number == null ? defaultValue : number;
	}
	
	/**
	 * 
	 * 转成long类型<br>
	 * 添加: zhangjian - 2020年1月14日 上午11:23:17<br>
	 * 修改: zhangjian - 2020年1月14日 上午11:23:17<br>
	 * @param number
	 * @param defaultValue
	 * @return
	 * @throws NumberFormatException
	 */
	public static Long valueOfLong(String number,Long defaultValue) throws NumberFormatException {
		return StrUtil.isBlank(number) ? defaultValue : Long.valueOf(number);
	}
	
	/**
	 * 
	 * 转成long类型<br>
	 * 添加: zhangjian - 2020年1月14日 上午11:23:17<br>
	 * 修改: zhangjian - 2020年1月14日 上午11:23:17<br>
	 * @param number
	 * @param defaultValue
	 * @return
	 * @throws NumberFormatException
	 */
	public static Long valueOfLong(Long number,Long defaultValue) throws NumberFormatException {
		return number == null ? defaultValue : number;
	}
	
	/**
	 * 
	 * 转成double类型<br>
	 * 添加: zhangjian - 2020年1月14日 上午11:23:17<br>
	 * 修改: zhangjian - 2020年1月14日 上午11:23:17<br>
	 * @param number
	 * @param defaultValue
	 * @return
	 * @throws NumberFormatException
	 */
	public static Double valueOfDouble(String number,Double defaultValue) throws NumberFormatException {
		return StrUtil.isBlank(number) ? defaultValue : Double.valueOf(number);
	}
	
	/**
	 * 
	 * 转成long类型<br>
	 * 添加: zhangjian - 2020年1月14日 上午11:23:17<br>
	 * 修改: zhangjian - 2020年1月14日 上午11:23:17<br>
	 * @param number
	 * @param defaultValue
	 * @return
	 * @throws NumberFormatException
	 */
	public static double valueOfDouble(Double number,Double defaultValue) throws NumberFormatException {
		return number == null ? defaultValue : number;
	}
}
