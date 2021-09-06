package com.eystar.gen.entity;

import java.io.Serializable;
import java.util.Date;

public class CPTraffic implements Serializable {
    private String id;

    private String accessTypeName;

    private Long createTime;

    private Double downAvg;

    private Double downMax;

    private String ip;

    private String probeId;

    private String trafficType;

    private Double upAvg;

    private Double upMax;

    private Long timesheet;

    private Date monthTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessTypeName() {
        return accessTypeName;
    }

    public void setAccessTypeName(String accessTypeName) {
        this.accessTypeName = accessTypeName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Double getDownAvg() {
        return downAvg;
    }

    public void setDownAvg(Double downAvg) {
        this.downAvg = downAvg;
    }

    public Double getDownMax() {
        return downMax;
    }

    public void setDownMax(Double downMax) {
        this.downMax = downMax;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getProbeId() {
        return probeId;
    }

    public void setProbeId(String probeId) {
        this.probeId = probeId;
    }

    public String getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(String trafficType) {
        this.trafficType = trafficType;
    }

    public Double getUpAvg() {
        return upAvg;
    }

    public void setUpAvg(Double upAvg) {
        this.upAvg = upAvg;
    }

    public Double getUpMax() {
        return upMax;
    }

    public void setUpMax(Double upMax) {
        this.upMax = upMax;
    }

    public Long getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Long timesheet) {
        this.timesheet = timesheet;
    }

    public Date getMonthTime() {
        return monthTime;
    }

    public void setMonthTime(Date monthTime) {
        this.monthTime = monthTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", accessTypeName=").append(accessTypeName);
        sb.append(", createTime=").append(createTime);
        sb.append(", downAvg=").append(downAvg);
        sb.append(", downMax=").append(downMax);
        sb.append(", ip=").append(ip);
        sb.append(", probeId=").append(probeId);
        sb.append(", trafficType=").append(trafficType);
        sb.append(", upAvg=").append(upAvg);
        sb.append(", upMax=").append(upMax);
        sb.append(", timesheet=").append(timesheet);
        sb.append(", monthTime=").append(monthTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}