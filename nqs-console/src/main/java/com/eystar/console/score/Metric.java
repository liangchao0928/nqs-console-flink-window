package com.eystar.console.score;

import cn.hutool.core.util.NumberUtil;

import java.util.ArrayList;
import java.util.List;

public class Metric {
	private String name;
	private String direction;
	private Float weight;
	private String unit;
	private Float score;
	private String metricAlias;
	private String outlier;
	private List<Score> scores = new ArrayList<Score>();

	public float getMinScores() {
		float[] mins = new float[scores.size()];
		int i = 0;
		for (Score score : scores) {
			mins[i] = score.getLower();
			i++;
		}
		return NumberUtil.min(mins);
	}

	public float getMaxScores() {
		float[] maxs = new float[scores.size()];
		int i = 0;
		for (Score score : scores) {
			maxs[i] = score.getUpper();
			i++;
		}
		return NumberUtil.max(maxs);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getMetricAlias() {
		return metricAlias;
	}

	public void setMetricAlias(String metricAlias) {
		this.metricAlias = metricAlias;
	}

	public List<Score> getScores() {
		return scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}

	public String getOutlier() {
		return outlier;
	}

	public void setOutlier(String outlier) {
		this.outlier = outlier;
	}

	@Override
	public String toString() {
		return name;
	}
}
