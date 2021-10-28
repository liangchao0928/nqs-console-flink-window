package com.eystar.gen.entity;

import java.io.Serializable;
import java.util.Date;

public class CPPinfo implements Serializable {
    private String id;

    private String accessTypeInfo;

    private String neighborInfo;

    private String probeId;

    private String probeInfo;

    private String sgwInfo;

    private String statusInfo;

    private String trafficInfo;

    private Long timesheet;

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

    public String getAccessTypeInfo() {
        return accessTypeInfo;
    }

    public void setAccessTypeInfo(String accessTypeInfo) {
        this.accessTypeInfo = accessTypeInfo;
    }

    public String getNeighborInfo() {
        return neighborInfo;
    }

    public void setNeighborInfo(String neighborInfo) {
        this.neighborInfo = neighborInfo;
    }

    public String getProbeId() {
        return probeId;
    }

    public void setProbeId(String probeId) {
        this.probeId = probeId;
    }

    public String getProbeInfo() {
        return probeInfo;
    }

    public void setProbeInfo(String probeInfo) {
        this.probeInfo = probeInfo;
    }

    public String getSgwInfo() {
        return sgwInfo;
    }

    public void setSgwInfo(String sgwInfo) {
        this.sgwInfo = sgwInfo;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public String getTrafficInfo() {
        return trafficInfo;
    }

    public void setTrafficInfo(String trafficInfo) {
        this.trafficInfo = trafficInfo;
    }

    public Long getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Long timesheet) {
        this.timesheet = timesheet;
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
        sb.append(", accessTypeInfo=").append(accessTypeInfo);
        sb.append(", neighborInfo=").append(neighborInfo);
        sb.append(", probeId=").append(probeId);
        sb.append(", probeInfo=").append(probeInfo);
        sb.append(", sgwInfo=").append(sgwInfo);
        sb.append(", statusInfo=").append(statusInfo);
        sb.append(", trafficInfo=").append(trafficInfo);
        sb.append(", timesheet=").append(timesheet);
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