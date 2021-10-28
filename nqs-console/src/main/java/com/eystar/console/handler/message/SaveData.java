package com.eystar.console.handler.message;

import java.util.List;

import com.eystar.gen.entity.gwdata.GwData;

public class SaveData {
	private String taskTypeName;
	private String tableName;
	private List<GwData> records;
	private String detailTaskTypeName;
	private String detailTableName;
	private List<GwData> detailRecords;

	public String getTaskTypeName() {
		return taskTypeName;
	}

	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}

	public List<GwData> getRecords() {
		return records;
	}

	public void setRecords(List<GwData> records) {
		this.records = records;
	}

	public String getDetailTaskTypeName() {
		return detailTaskTypeName;
	}

	public void setDetailTaskTypeName(String detailTaskTypeName) {
		this.detailTaskTypeName = detailTaskTypeName;
	}

	public List<GwData> getDetailRecords() {
		return detailRecords;
	}

	public void setDetailRecords(List<GwData> detailRecords) {
		this.detailRecords = detailRecords;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDetailTableName() {
		return detailTableName;
	}

	public void setDetailTableName(String detailTableName) {
		this.detailTableName = detailTableName;
	}

	@Override
	public String toString() {
		return "SaveData [taskTypeName=" + taskTypeName + ", tableName=" + tableName + ", records.size=" + records.size() + ", detailTaskTypeName=" + detailTaskTypeName + ", detailTableName=" + detailTableName + ", detailRecords.size=" + detailRecords.size() + "]";
	}

}
