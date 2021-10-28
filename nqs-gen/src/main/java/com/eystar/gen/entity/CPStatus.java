package com.eystar.gen.entity;

import java.io.Serializable;
import java.util.Date;

public class CPStatus implements Serializable {
    private String id;

    private Double cpuRate;

    private String probeId;

    private Double ramRate;

    private String runTime;

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

    public Double getCpuRate() {
        return cpuRate;
    }

    public void setCpuRate(Double cpuRate) {
        this.cpuRate = cpuRate;
    }

    public String getProbeId() {
        return probeId;
    }

    public void setProbeId(String probeId) {
        this.probeId = probeId;
    }

    public Double getRamRate() {
        return ramRate;
    }

    public void setRamRate(Double ramRate) {
        this.ramRate = ramRate;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
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
        sb.append(", cpuRate=").append(cpuRate);
        sb.append(", probeId=").append(probeId);
        sb.append(", ramRate=").append(ramRate);
        sb.append(", runTime=").append(runTime);
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