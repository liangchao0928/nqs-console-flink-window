package com.eystar.gen.entity;

import java.io.Serializable;
import java.util.Date;

public class CPStatus implements Serializable {
    private String id;

    private Double cpuRate;

    private Long createTime;

    private String probeId;

    private Double ramRate;

    private String runTime;

    private Long timesheet;

    private Date monthTime;

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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
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
        sb.append(", cpuRate=").append(cpuRate);
        sb.append(", createTime=").append(createTime);
        sb.append(", probeId=").append(probeId);
        sb.append(", ramRate=").append(ramRate);
        sb.append(", runTime=").append(runTime);
        sb.append(", timesheet=").append(timesheet);
        sb.append(", monthTime=").append(monthTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}