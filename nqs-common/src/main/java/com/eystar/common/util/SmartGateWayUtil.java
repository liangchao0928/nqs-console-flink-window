package com.eystar.common.util;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

public class SmartGateWayUtil {


	public static Double parsePower(String type, Double source) {
		if (StrUtil.isBlank(type)) {
			return source;
		}
		if ("DT741-csf".equalsIgnoreCase(type)) {
			return NumberUtil.div(source.doubleValue(), 10000d);
		}
		return source;
	}

}
