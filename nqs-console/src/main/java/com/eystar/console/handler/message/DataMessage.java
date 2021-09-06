package com.eystar.console.handler.message;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;

public class DataMessage extends Message {

	private String taskId;
	private String taskTypeName;
	private int taskFrom = 10;

	public DataMessage(JSONObject object) {
		try {
			setMsgJson(object);
			Long time = getMsgJson().getLongValue("test_time"); // 这个时间是探针上报的时间
			// 如果时间是3天前的数据，可能就是探针上时间不准导致，这个时候将上报的时间改为当前时间
			if (Math.abs(System.currentTimeMillis() / 1000 - time) > 108000) {
				time = System.currentTimeMillis() / 1000;
			}
			setTestTime(time);
			String probeId = object.getString("probe_id");
			if (StrUtil.isBlank(probeId)) {
				System.out.println("此次上报的数据信息中没有探针id，此次上报的内容 = \r\n" + object.toJSONString());
				setBadMsg(true);
			} else {
				setProbeId(probeId);
			}
			String taskId = object.getString("task_id");
			if (StrUtil.isBlank(taskId)) {
				System.out.println("此次上报的数据信息中没有任务id，此次上报的内容 = \r\n" + object.toJSONString());
				setBadMsg(true);
			} else {
				setTaskId(taskId);
			}
			String taskTypeName = object.getString("task_type_name");
			if (StrUtil.isBlank(taskTypeName)) {
				System.out.println("此次上报的数据信息中没有任务类型名称，此次上报的内容 = \r\n" + object.toJSONString());
				setBadMsg(true);
			} else {
				setTaskTypeName(taskTypeName);
			}
			this.taskFrom = object.getIntValue("task_from");
		} catch (Exception e) {
			System.out.println("消息格式不正确 ，此次上报的内容 = \r\n" + object.toJSONString()+e.getMessage());
			setBadMsg(true);
		}
	}

	/**
	 * taskId 的get方法
	 * 
	 * @return String
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * taskId 的set方法
	 * 
	 * @param taskId
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * taskTypeName 的get方法
	 * 
	 * @return String
	 */
	public String getTaskTypeName() {
		return taskTypeName;
	}

	/**
	 * taskTypeName 的set方法
	 * 
	 * @param taskTypeName
	 */
	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}

	/**
	 * taskFrom 的get方法
	 * 
	 * @return int
	 */
	public int getTaskFrom() {
		return taskFrom;
	}

	/**
	 * taskFrom 的set方法
	 * 
	 * @param taskFrom
	 */
	public void setTaskFrom(int taskFrom) {
		this.taskFrom = taskFrom;
	}

	@Override
	public String toString() {
		return "DataMessage [taskId=" + taskId + ", taskTypeName=" + taskTypeName + ", taskFrom=" + taskFrom + ", getTestTime()=" + getTestTime() + ", getProbeId()=" + getProbeId() + ", isBadMsg()=" + isBadMsg() + ", getMsgJson()=" + getMsgJson() + "]";
	}

}
