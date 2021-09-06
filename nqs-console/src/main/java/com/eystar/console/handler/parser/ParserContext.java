package com.eystar.console.handler.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class ParserContext {

	private static Logger logger = LoggerFactory.getLogger(ParserContext.class);

	private static Map<String, Class<? extends AbstractDataParser>> parserDepot = new HashMap<>();


	public static void init() {
		registerParser("HTTP", HttpDataParser.class);
		registerParser("DNS", DnsDataParser.class);
		registerParser("TRACE", TraceDataParser.class);
		registerParser("GAME", GameDataParser.class);
//		InfoLoader.loadIpRegion(); // 将IP缓存到redis中
	}


	public static void registerParser(String typeName, Class<? extends AbstractDataParser> clazz) {
		parserDepot.put(typeName, clazz);
	}



	public synchronized static AbstractDataParser getDataItemParser(String typeName) {
		if (parserDepot.get(typeName.toUpperCase()) != null) {
			try {
				return parserDepot.get(typeName).newInstance();
			} catch (InstantiationException e) {
				logger.error("根据类型名称" + typeName + "创建解析对象出错", e);
			} catch (IllegalAccessException e) {
				logger.error("实例化" + typeName + "的解析对象时出现异常，可能不存在构造方法", e);
			}
		}
		return new DefaultDataParser();
	}

}