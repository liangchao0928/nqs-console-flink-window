package com.eystar.console.handler.message;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;

public class GwInfoMessage extends Message {

	public GwInfoMessage(String msg) {
		try {
			setMsgJson(JSONObject.parseObject(msg));
			Long time = getMsgJson().getLongValue("time"); // 这个时间是探针上报的时间
			// 如果时间是3天前的数据，可能就是探针上时间不准导致，这个时候将上报的时间改为当前时间
			if (Math.abs(System.currentTimeMillis() / 1000 - time) > 108000) {
				time = System.currentTimeMillis() / 1000;
			}
			setTestTime(time);
			String probeId = getMsgJson().getString("probe_id");
			if (StrUtil.isBlank(probeId)) {
				logger.error("此次上报的网关基础信息中没有探针id，此次上报的内容 = \r\n" + msg);
				setBadMsg(true);
			} else {
				setProbeId(probeId);
			}
		} catch (Exception e) {
			logger.error("消息格式不正确 ，此次上报的内容 = \r\n" + msg, e);
			setBadMsg(true);
		}
	}

}
