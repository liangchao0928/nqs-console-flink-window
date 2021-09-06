package com.eystar.console.score;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ParserPQXml {

	private Document document;

	public ParserPQXml(Document document) {
		this.document = document;
	}

	public Criteria getCriteria(String id) {
		Element element = document.getElementById(id);
		Criteria criteria = getCriteria(element);
		return criteria;
	}

	public Criteria getCriteria(Element element) {
		Criteria criteria = new Criteria();
		criteria.setId(Long.valueOf(element.attr("id")));
		criteria.setProtocol(element.attr("protocol"));
		criteria.setAlias(element.attr("protocol_alias"));
		criteria.setMetrics(getMetrics(element));
		return criteria;
	}

	public List<Metric> getMetrics(Element parentElement) {
		List<Metric> metrics = new ArrayList<Metric>();
		Elements elements = parentElement.select("metric");
		for (Element element : elements) {
			metrics.add(getMetric(element));
		}
		return metrics;
	}

	public Metric getMetric(Element element) {
		Metric metric = new Metric();
		metric.setName(element.attr("name"));
		metric.setDirection(element.attr("direction"));
		metric.setWeight(Float.valueOf(element.attr("weight")));
		metric.setUnit(element.attr("unit"));
		metric.setMetricAlias(element.attr("metric_alias"));
		metric.setOutlier(element.attr("outlier"));
		metric.setScores(getScores(element));
		return metric;
	}

	public List<Score> getScores(Element parentElement) {
		List<Score> scores = new ArrayList<Score>();
		Elements elements = parentElement.select("score");
		for (Element element : elements) {
			scores.add(getScore(element));
		}
		return scores;
	}

	public Score getScore(Element element) {
		Score score = new Score();
		score.setLevel(element.attr("level"));
		score.setUpper(Float.valueOf(element.attr("upper")));
		score.setLower(Float.valueOf(element.attr("lower")));
		score.setValue(element.text());
		return score;
	}

	public Document getDocument() {
		return document;
	}

}
