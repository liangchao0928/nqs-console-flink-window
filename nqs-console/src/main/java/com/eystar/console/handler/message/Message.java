package com.eystar.console.handler.message;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Message {
	
	public final Logger logger = LoggerFactory.getLogger(getClass());

	private Long testTime = 0L;
	private String probeId;
	private JSONObject msgJson;
	private boolean badMsg = false;

	/**
	 * testTime 的get方法
	 * @return Long
	 */
	public Long getTestTime() {
		return testTime;
	}
	/** 
	 * testTime 的set方法
	 * @param testTime
	 */
	public void setTestTime(Long testTime) {
		this.testTime = testTime;
	}
	/**
	 * probeId 的get方法
	 * @return String
	 */
	public String getProbeId() {
		return probeId;
	}
	/** 
	 * probeId 的set方法
	 * @param probeId
	 */
	public void setProbeId(String probeId) {
		this.probeId = probeId;
	}
	/**
	 * badMsg 的get方法
	 * @return boolean
	 */
	public boolean isBadMsg() {
		return badMsg;
	}
	/** 
	 * badMsg 的set方法
	 * @param badMsg
	 */
	public void setBadMsg(boolean badMsg) {
		this.badMsg = badMsg;
	}
	/**
	 * msgJson 的get方法
	 * @return JSONObject
	 */
	public JSONObject getMsgJson() {
		return msgJson;
	}
	/** 
	 * msgJson 的set方法
	 * @param msgJson
	 */
	public void setMsgJson(JSONObject msgJson) {
		this.msgJson = msgJson;
	}
	
}
