package com.eystar.gen.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class TMIpRegion implements Serializable {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "ip")
    private String ip;

    @JSONField(name="province_code")
    @ApiModelProperty(value = "省")
    private String provinceCode;

    @JSONField(name="province_name")
    @ApiModelProperty(value = "省")
    private String provinceName;

    @JSONField(name="city_code")
    @ApiModelProperty(value = "市")
    private String cityCode;

    @JSONField(name="city_name")
    @ApiModelProperty(value = "市")
    private String cityName;

    @JSONField(name="district_code")
    @ApiModelProperty(value = "区")
    private String districtCode;

    @JSONField(name="district_name")
    @ApiModelProperty(value = "区")
    private String districtName;

    @ApiModelProperty(value = "运营商")
    private String operator;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    private String address;

    @ApiModelProperty(value = "0-添加或者倒入，10-brasip")
    private Short type;

    @JSONField(name="attachment_id")
    @ApiModelProperty(value = "附件ID")
    private String attachmentId;

    @JSONField(name="create_user")
    @ApiModelProperty(value = "创建人")
    private String createUser;

    @JSONField(name="update_user")
    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @JSONField(name="create_time")
    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @JSONField(name="update_time")
    @ApiModelProperty(value = "更新时间")
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
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
        sb.append(", ip=").append(ip);
        sb.append(", provinceCode=").append(provinceCode);
        sb.append(", provinceName=").append(provinceName);
        sb.append(", cityCode=").append(cityCode);
        sb.append(", cityName=").append(cityName);
        sb.append(", districtCode=").append(districtCode);
        sb.append(", districtName=").append(districtName);
        sb.append(", operator=").append(operator);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", address=").append(address);
        sb.append(", type=").append(type);
        sb.append(", attachmentId=").append(attachmentId);
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