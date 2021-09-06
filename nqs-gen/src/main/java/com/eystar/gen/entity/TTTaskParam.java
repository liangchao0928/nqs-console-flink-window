package com.eystar.gen.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class TTTaskParam implements Serializable {
    @ApiModelProperty(value = "测试任务id")
    private String id;

    @JSONField(name="task_param_name")
    @ApiModelProperty(value = "测试任务名字")
    private String taskParamName;

    @JSONField(name="task_param_type")
    @ApiModelProperty(value = "参数类型（10-系统自动生成，20-用户自定义）")
    private Short taskParamType;

    @JSONField(name="task_type_name")
    @ApiModelProperty(value = "任务类型名称")
    private String taskTypeName;

    @JSONField(name="task_from")
    @ApiModelProperty(value = "任务来源标识（10-自有平台，20-拨测，30-cba）")
    private Short taskFrom;

    @JSONField(name="template_id")
    @ApiModelProperty(value = "计算出参数是md5，供任务下发时判断是否同一个任务，在参数修改后计算templdate_id")
    private String templateId;

    @JSONField(name="run_status")
    @ApiModelProperty(value = "参数状态（10-下发中，20-启动，30-下发失败，40-暂停，50-过期）")
    private Short runStatus;

    @JSONField(name="modify_type")
    @ApiModelProperty(value = "修改类型（000-无变化，010-参数不变源变化目的不变）")
    private String modifyType;

    @JSONField(name="test_date")
    @ApiModelProperty(value = "测试有效期，具体看字典，0-长期有效")
    private Short testDate;

    @JSONField(name="start_date")
    @ApiModelProperty(value = "开始时间，格式：yyyy-MM-dd")
    private String startDate;

    @JSONField(name="end_date")
    @ApiModelProperty(value = "结束时间，格式：yyyy-MM-dd")
    private String endDate;

    @JSONField(name="test_time")
    @ApiModelProperty(value = "0-自定义，10-闲时，20-忙时")
    private Short testTime;

    @JSONField(name="start_time")
    @ApiModelProperty(value = "格式：hh:mm")
    private String startTime;

    @JSONField(name="end_time")
    @ApiModelProperty(value = "格式：hh:mm")
    private String endTime;

    @ApiModelProperty(value = "时间间隔（单位秒）")
    private Integer interval;

    @ApiModelProperty(value = "任务优先级")
    private Short priority;

    @JSONField(name="exec_times")
    @ApiModelProperty(value = "测试次数(与调度时间互斥）")
    private Short execTimes;

    @JSONField(name="region_for")
    @ApiModelProperty(value = "10-区域随机（针对当前系统中有的探针不包括后注册探针），20-区域全量（包含后注册探针），30-全部探针（包含后注册探针）,40-最优分配")
    private Short regionFor;

    @JSONField(name="region_num")
    @ApiModelProperty(value = "区域随机个数")
    private Integer regionNum;

    @JSONField(name="user_region")
    @ApiModelProperty(value = "用户region")
    private Long userRegion;

    @JSONField(name="region_path")
    @ApiModelProperty(value = "用户所在的区域路径")
    private String regionPath;

    @JSONField(name="task_num")
    @ApiModelProperty(value = "分配任务数")
    private Integer taskNum;

    @JSONField(name="is_alarm")
    @ApiModelProperty(value = "是否设置告警")
    private Short isAlarm;

    @JSONField(name="alarm_template_id")
    @ApiModelProperty(value = "告警模板ID")
    private String alarmTemplateId;

    @ApiModelProperty(value = "任务描述")
    private String memo;

    @JSONField(name="create_user")
    @ApiModelProperty(value = "创建用户名")
    private String createUser;

    @JSONField(name="update_user")
    @ApiModelProperty(value = "修改用户名")
    private String updateUser;

    @JSONField(name="create_time")
    @ApiModelProperty(value = "创建时间（Unix时间戳）")
    private Long createTime;

    @JSONField(name="update_time")
    @ApiModelProperty(value = "修改时间（Unix时间戳）")
    private Long updateTime;

    @JSONField(name="delete_flag")
    @ApiModelProperty(value = "是否删除（1-是，0-否）")
    private Short deleteFlag;

    @JSONField(name="param_json")
    @ApiModelProperty(value = "测试任务参数配置json")
    private String paramJson;

    @JSONField(name="template_json")
    @ApiModelProperty(value = "该模板对应的json数据")
    private String templateJson;

    @JSONField(name="topo_json")
    @ApiModelProperty(value = "拓扑结构json")
    private String topoJson;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskParamName() {
        return taskParamName;
    }

    public void setTaskParamName(String taskParamName) {
        this.taskParamName = taskParamName;
    }

    public Short getTaskParamType() {
        return taskParamType;
    }

    public void setTaskParamType(Short taskParamType) {
        this.taskParamType = taskParamType;
    }

    public String getTaskTypeName() {
        return taskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        this.taskTypeName = taskTypeName;
    }

    public Short getTaskFrom() {
        return taskFrom;
    }

    public void setTaskFrom(Short taskFrom) {
        this.taskFrom = taskFrom;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Short getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(Short runStatus) {
        this.runStatus = runStatus;
    }

    public String getModifyType() {
        return modifyType;
    }

    public void setModifyType(String modifyType) {
        this.modifyType = modifyType;
    }

    public Short getTestDate() {
        return testDate;
    }

    public void setTestDate(Short testDate) {
        this.testDate = testDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Short getTestTime() {
        return testTime;
    }

    public void setTestTime(Short testTime) {
        this.testTime = testTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Short getPriority() {
        return priority;
    }

    public void setPriority(Short priority) {
        this.priority = priority;
    }

    public Short getExecTimes() {
        return execTimes;
    }

    public void setExecTimes(Short execTimes) {
        this.execTimes = execTimes;
    }

    public Short getRegionFor() {
        return regionFor;
    }

    public void setRegionFor(Short regionFor) {
        this.regionFor = regionFor;
    }

    public Integer getRegionNum() {
        return regionNum;
    }

    public void setRegionNum(Integer regionNum) {
        this.regionNum = regionNum;
    }

    public Long getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(Long userRegion) {
        this.userRegion = userRegion;
    }

    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }

    public Integer getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(Integer taskNum) {
        this.taskNum = taskNum;
    }

    public Short getIsAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(Short isAlarm) {
        this.isAlarm = isAlarm;
    }

    public String getAlarmTemplateId() {
        return alarmTemplateId;
    }

    public void setAlarmTemplateId(String alarmTemplateId) {
        this.alarmTemplateId = alarmTemplateId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    public String getTemplateJson() {
        return templateJson;
    }

    public void setTemplateJson(String templateJson) {
        this.templateJson = templateJson;
    }

    public String getTopoJson() {
        return topoJson;
    }

    public void setTopoJson(String topoJson) {
        this.topoJson = topoJson;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", taskParamName=").append(taskParamName);
        sb.append(", taskParamType=").append(taskParamType);
        sb.append(", taskTypeName=").append(taskTypeName);
        sb.append(", taskFrom=").append(taskFrom);
        sb.append(", templateId=").append(templateId);
        sb.append(", runStatus=").append(runStatus);
        sb.append(", modifyType=").append(modifyType);
        sb.append(", testDate=").append(testDate);
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append(", testTime=").append(testTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", interval=").append(interval);
        sb.append(", priority=").append(priority);
        sb.append(", execTimes=").append(execTimes);
        sb.append(", regionFor=").append(regionFor);
        sb.append(", regionNum=").append(regionNum);
        sb.append(", userRegion=").append(userRegion);
        sb.append(", regionPath=").append(regionPath);
        sb.append(", taskNum=").append(taskNum);
        sb.append(", isAlarm=").append(isAlarm);
        sb.append(", alarmTemplateId=").append(alarmTemplateId);
        sb.append(", memo=").append(memo);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", paramJson=").append(paramJson);
        sb.append(", templateJson=").append(templateJson);
        sb.append(", topoJson=").append(topoJson);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}