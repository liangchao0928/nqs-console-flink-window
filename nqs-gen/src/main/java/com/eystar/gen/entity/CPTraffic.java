package com.eystar.gen.entity;

import java.io.Serializable;
import java.util.Date;

public class CPTraffic implements Serializable {
    private String id;

    private String accessTypeName;

    private Double downAvg;

    private Double downMax;

    private String ip;

    private String probeId;

    private Long timesheet;

    private String trafficType;

    private Double upAvg;

    private Double upMax;

    private Date timesheetPar;

    private Long timesheetD;

    private Long timesheetH;

    private Long timesheetM;

    private Long timesheetW;

    private Long createTime;

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

    public Long getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Long timesheet) {
        this.timesheet = timesheet;
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

    public Date getTimesheetPar() {
        return timesheetPar;
    }

    public void setTimesheetPar(Date timesheetPar) {
        this.timesheetPar = timesheetPar;
    }

    public Long getTimesheetD() {
        return timesheetD;
    }

    public void setTimesheetD(Long timesheetD) {
        this.timesheetD = timesheetD;
    }

    public Long getTimesheetH() {
        return timesheetH;
    }

    public void setTimesheetH(Long timesheetH) {
        this.timesheetH = timesheetH;
    }

    public Long getTimesheetM() {
        return timesheetM;
    }

    public void setTimesheetM(Long timesheetM) {
        this.timesheetM = timesheetM;
    }

    public Long getTimesheetW() {
        return timesheetW;
    }

    public void setTimesheetW(Long timesheetW) {
        this.timesheetW = timesheetW;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", accessTypeName=").append(accessTypeName);
        sb.append(", downAvg=").append(downAvg);
        sb.append(", downMax=").append(downMax);
        sb.append(", ip=").append(ip);
        sb.append(", probeId=").append(probeId);
        sb.append(", timesheet=").append(timesheet);
        sb.append(", trafficType=").append(trafficType);
        sb.append(", upAvg=").append(upAvg);
        sb.append(", upMax=").append(upMax);
        sb.append(", timesheetPar=").append(timesheetPar);
        sb.append(", timesheetD=").append(timesheetD);
        sb.append(", timesheetH=").append(timesheetH);
        sb.append(", timesheetM=").append(timesheetM);
        sb.append(", timesheetW=").append(timesheetW);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}