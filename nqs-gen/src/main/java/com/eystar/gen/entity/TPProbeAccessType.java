package com.eystar.gen.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class TPProbeAccessType implements Serializable {
    private String id;

    @JSONField(name="probe_id")
    @ApiModelProperty(value = "探针id")
    private String probeId;

    @JSONField(name="access_type_name")
    @ApiModelProperty(value = "接入类型名称")
    private String accessTypeName;

    @JSONField(name="is_default")
    @ApiModelProperty(value = "探针默认测试端口 1-是，0-否")
    private Short isDefault;

    @ApiModelProperty(value = "端口MAC")
    private String mac;

    @ApiModelProperty(value = "端口IP")
    private String ip;

    @ApiModelProperty(value = "子网掩码")
    private String mask;

    @ApiModelProperty(value = "DNs")
    private String dns;

    @ApiModelProperty(value = "网关")
    private String gateway;

    @JSONField(name="link_type")
    @ApiModelProperty(value = "网口连接方式")
    private String linkType;

    @ApiModelProperty(value = "网口号")
    private String no;

    @JSONField(name="connect_status")
    @ApiModelProperty(value = "连接状态")
    private String connectStatus;

    @ApiModelProperty(value = "端口速率，单位M")
    private Double speed;

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

    public String getAccessTypeName() {
        return accessTypeName;
    }

    public void setAccessTypeName(String accessTypeName) {
        this.accessTypeName = accessTypeName;
    }

    public Short getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Short isDefault) {
        this.isDefault = isDefault;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(String connectStatus) {
        this.connectStatus = connectStatus;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", probeId=").append(probeId);
        sb.append(", accessTypeName=").append(accessTypeName);
        sb.append(", isDefault=").append(isDefault);
        sb.append(", mac=").append(mac);
        sb.append(", ip=").append(ip);
        sb.append(", mask=").append(mask);
        sb.append(", dns=").append(dns);
        sb.append(", gateway=").append(gateway);
        sb.append(", linkType=").append(linkType);
        sb.append(", no=").append(no);
        sb.append(", connectStatus=").append(connectStatus);
        sb.append(", speed=").append(speed);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}