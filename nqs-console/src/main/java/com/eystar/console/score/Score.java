package com.eystar.console.score;

import cn.hutool.core.util.StrUtil;

public class Score {

	private String level;
	private float upper;
	private float lower;
	private String value; // 格式是(100,200]

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public float getUpper() {
		return upper;
	}

	public void setUpper(float upper) {
		this.upper = upper;
	}

	public float getLower() {
		return lower;
	}

	public void setLower(float lower) {
		this.lower = lower;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMinVal() {
		String min = StrUtil.split(value, ",")[0];
		if (min.contains("+")) {
			return String.valueOf(Float.MIN_VALUE);
		} else if (min.contains("(")) {
			min = StrUtil.removePrefix(min, "(");
		} else if (min.contains("[")) {
			min = StrUtil.removePrefix(min, "[");
		}
		return min;
	}

	public String getMaxVal() {
		String max = StrUtil.split(value, ",")[1];
		if (max.contains("+")) {
			return String.valueOf(Float.MAX_VALUE / 100);
		} else if (max.contains(")")) {
			max = StrUtil.removeSuffix(max, ")");
		} else if (max.contains("]")) {
			max = StrUtil.removeSuffix(max, "]");
		}
		return max;
	}

	public String getScoreMin() {
		String min = StrUtil.split(value, ",")[0];
		if (min.contains("+")) {
			return null;
		} else if (min.contains("(")) {
			min = StrUtil.removePrefix(min, "(") + "_0";
		} else if (min.contains("[")) {
			min = StrUtil.removePrefix(min, "[") + "_1";
		}
		return min;
	}

	public String getScoreMax() {
		String max = StrUtil.split(value, ",")[1];
		if (max.contains("+")) {
			return null;
		} else if (max.contains(")")) {
			max = StrUtil.removeSuffix(max, ")") + "_0";
		} else if (max.contains("]")) {
			max = StrUtil.removeSuffix(max, "]") + "_1";
		}
		return max;
	}
}
