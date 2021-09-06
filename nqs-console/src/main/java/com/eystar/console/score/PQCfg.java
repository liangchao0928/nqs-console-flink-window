package com.eystar.console.score;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class PQCfg {
	protected static final Logger logger = LoggerFactory.getLogger(PQCfg.class);
	static ParserPQXml parser;

	static {
		Document doc = null;
		try {
			doc = Jsoup.parse(PQCfg.class.getResourceAsStream("/perceived_quality.xml"), "utf-8", "");
		} catch (Exception e) {
			logger.error("读取perceived_quality.xml异常", e);
			throw new RuntimeException("读取perceived_quality.xml异常", e);
		}
		parser = new ParserPQXml(doc);
	}

	public static Map<String, Criteria> getCriteriasMap() {
		Map<String, Criteria> retMap = new HashMap<String, Criteria>();
		Elements elements = parser.getDocument().select("criteria");
		for (Element element : elements) {
			Criteria criteria = parser.getCriteria(element.attr("id"));
			retMap.put(criteria.getProtocol(), criteria);
		}
		return retMap;
	}
}
