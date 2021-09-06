package com.eystar.console.util;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.util.ChangeChar;
import com.eystar.console.score.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/***
 * 
 * @desc 数据评分处理
 *
 */
public class ScoreHelper {

	protected static final Logger logger = LoggerFactory.getLogger(ScoreHelper.class);
	private static Map<String, Criteria> criteriaMap;

	static {
		criteriaMap = PQCfg.getCriteriasMap();
	}

	public synchronized static void fillScore(JSONObject  gdTaskJson) {
		if (StrUtil.equals("SPEED",gdTaskJson.getString("taskTypeName"))){
			gdTaskJson.put("score",0);
			return;
		}
		Criteria criteria = criteriaMap.get(gdTaskJson.getString("taskTypeName"));
		double sum = 0d;
		if (criteria != null) {
			List<Metric> metrics = criteria.getMetrics();
			for (Metric metric : metrics) {
				String field = StrUtil.trim(metric.getName());
				Double factNum = 0d;
				try {
					factNum = gdTaskJson.getDouble(ChangeChar.underlineToCamel(field));
				} catch (Exception e) {
					System.out.println("根据类型计算某个指标得分出现错误，类型 = " +gdTaskJson.getString("taskTypeName") + ", 指标 = " + field + ", 对象记录 = " + gdTaskJson.toJSONString()+ e.getMessage());
					continue;
				}
				float metricNum = getMetricScore(metric, factNum.floatValue());
				if (metricNum == -1) { // 说明达到阈值了，直接记录0分
					sum = 0d;
					break;
				}
				sum += metricNum;
			}
			if (sum > 100) {
				sum = 100d;
			} else if (sum < 0) {
				sum = 0d;
			}
		}
		gdTaskJson.put("score",NumberUtil.round(sum, 2).doubleValue());
	}

	/**
	 * 根据实际的数字计算每一个指标的得到的分数，如果指标触发
	 * 
	 * @param metric
	 * @param factNum
	 * @throws Exception
	 * 
	 */
	private static float getMetricScore(Metric metric, float factNum) {
		if (PQMath.eqOutlier(metric.getOutlier(), factNum)) { // 说明这个指标是错误的值，直接指标返回-1
			return -1;
		}
		Score score = null;
		float retVal = 0;
		// 找到区间对象
		for (Score s : metric.getScores()) {
			if (PQMath.compare(s.getScoreMin(), s.getScoreMax(), factNum)) {
				score = s;
				break;
			}
		}
		if (score == null) {
			// 不论是他是上线还是下线，没有在任何一个区间，都是取最差成绩。
			score = metric.getScores().get(metric.getScores().size() - 1);
			return score.getLower() * metric.getWeight();
		}
		if (StrUtil.equals(metric.getDirection(), "down")) {
			retVal = PQMath.compScoreDown(score.getLower(), score.getUpper(), Float.valueOf(score.getMinVal()), Float.valueOf(score.getMaxVal()), factNum) * metric.getWeight();
		} else {
			retVal = PQMath.compScoreUp(score.getLower(), score.getUpper(), Float.valueOf(score.getMinVal()), Float.valueOf(score.getMaxVal()), factNum) * metric.getWeight();
		}
		return retVal;
	}

}
