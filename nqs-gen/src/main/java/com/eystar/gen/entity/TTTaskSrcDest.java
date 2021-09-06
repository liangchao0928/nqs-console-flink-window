package com.eystar.gen.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class TTTaskSrcDest implements Serializable {
    @ApiModelProperty(value = "表ID")
    private String id;

    @JSONField(name="probe_id")
    @ApiModelProperty(value = "探针ID")
    private String probeId;

    @JSONField(name="probe_alias")
    @ApiModelProperty(value = "探针名称")
    private String probeAlias;

    @JSONField(name="access_type_name")
    @ApiModelProperty(value = "测试端口")
    private String accessTypeName;

    @JSONField(name="province_code")
    @ApiModelProperty(value = "测试源探针省编码ID")
    private String provinceCode;

    @JSONField(name="province_name")
    @ApiModelProperty(value = "测试源探针省名称")
    private String provinceName;

    @JSONField(name="city_code")
    @ApiModelProperty(value = "测试源探针地市编码ID")
    private String cityCode;

    @JSONField(name="city_name")
    @ApiModelProperty(value = "测试源探针地市名称")
    private String cityName;

    @JSONField(name="district_code")
    @ApiModelProperty(value = "测试源探针地市编码ID")
    private String districtCode;

    @JSONField(name="district_name")
    @ApiModelProperty(value = "测试源探针地市名称")
    private String districtName;

    @JSONField(name="probe_below")
    @ApiModelProperty(value = "探针归属域编码")
    private String probeBelow;

    @JSONField(name="probe_below_type")
    @ApiModelProperty(value = "探针归属域类型（省、市、区、探针组、探针）")
    private Short probeBelowType;

    @JSONField(name="dest_id")
    @ApiModelProperty(value = "目的ID")
    private String destId;

    @JSONField(name="dest_addr")
    @ApiModelProperty(value = "目的地址")
    private String destAddr;

    @JSONField(name="dest_name")
    @ApiModelProperty(value = "目的名称")
    private String destName;

    @JSONField(name="dest_group_id")
    @ApiModelProperty(value = "目的组ID")
    private String destGroupId;

    @JSONField(name="host_city")
    @ApiModelProperty(value = "目的归属市")
    private String hostCity;

    @JSONField(name="host_province")
    @ApiModelProperty(value = "目的归属省")
    private String hostProvince;

    @ApiModelProperty(value = "目的运营商")
    private String operator;

    @ApiModelProperty(value = "任务状态（10-启动，20-暂停）")
    private Short status;

    @JSONField(name="task_from")
    @ApiModelProperty(value = "任务来源平台（10-本平台）")
    private Short taskFrom;

    @JSONField(name="task_md5")
    @ApiModelProperty(value = "任务MD5")
    private String taskMd5;


    @JSONField(name="task_param_id")
    @ApiModelProperty(value = "任务参数ID")
    private String taskParamId;

    @JSONField(name="template_id")
    @ApiModelProperty(value = "任务模板ID")
    private String templateId;

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

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProbeId() {
        return probeId;
    }

    public void setProbeId(String probeId) {
        this.probeId = probeId;
    }

    public String getProbeAlias() {
        return probeAlias;
    }

    public void setProbeAlias(String probeAlias) {
        this.probeAlias = probeAlias;
    }

    public String getAccessTypeName() {
        return accessTypeName;
    }

    public void setAccessTypeName(String accessTypeName) {
        this.accessTypeName = accessTypeName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getProbeBelow() {
        return probeBelow;
    }

    public void setProbeBelow(String probeBelow) {
        this.probeBelow = probeBelow;
    }

    public Short getProbeBelowType() {
        return probeBelowType;
    }

    public void setProbeBelowType(Short probeBelowType) {
        this.probeBelowType = probeBelowType;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public String getDestAddr() {
        return destAddr;
    }

    public void setDestAddr(String destAddr) {
        this.destAddr = destAddr;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    public String getDestGroupId() {
        return destGroupId;
    }

    public void setDestGroupId(String destGroupId) {
        this.destGroupId = destGroupId;
    }

    public String getHostCity() {
        return hostCity;
    }

    public void setHostCity(String hostCity) {
        this.hostCity = hostCity;
    }

    public String getHostProvince() {
        return hostProvince;
    }

    public void setHostProvince(String hostProvince) {
        this.hostProvince = hostProvince;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getTaskFrom() {
        return taskFrom;
    }

    public void setTaskFrom(Short taskFrom) {
        this.taskFrom = taskFrom;
    }

    public String getTaskMd5() {
        return taskMd5;
    }

    public void setTaskMd5(String taskMd5) {
        this.taskMd5 = taskMd5;
    }

    public String getTaskParamId() {
        return taskParamId;
    }

    public void setTaskParamId(String taskParamId) {
        this.taskParamId = taskParamId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", probeId=").append(probeId);
        sb.append(", probeAlias=").append(probeAlias);
        sb.append(", accessTypeName=").append(accessTypeName);
        sb.append(", provinceCode=").append(provinceCode);
        sb.append(", provinceName=").append(provinceName);
        sb.append(", cityCode=").append(cityCode);
        sb.append(", cityName=").append(cityName);
        sb.append(", districtCode=").append(districtCode);
        sb.append(", districtName=").append(districtName);
        sb.append(", probeBelow=").append(probeBelow);
        sb.append(", probeBelowType=").append(probeBelowType);
        sb.append(", destId=").append(destId);
        sb.append(", destAddr=").append(destAddr);
        sb.append(", destName=").append(destName);
        sb.append(", destGroupId=").append(destGroupId);
        sb.append(", hostCity=").append(hostCity);
        sb.append(", hostProvince=").append(hostProvince);
        sb.append(", operator=").append(operator);
        sb.append(", status=").append(status);
        sb.append(", taskFrom=").append(taskFrom);
        sb.append(", taskMd5=").append(taskMd5);
        sb.append(", taskParamId=").append(taskParamId);
        sb.append(", templateId=").append(templateId);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}