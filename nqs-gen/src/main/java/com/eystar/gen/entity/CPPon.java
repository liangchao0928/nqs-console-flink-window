package com.eystar.gen.entity;

import java.io.Serializable;
import java.util.Date;

public class CPPon implements Serializable {
    private String id;

    private Long createTime;

    private Double current;

    private String probeId;

    private Double rxPower;

    private Double temperature;

    private Double txPower;

    private Double voltage;

    private Long timesheet;

    private Date monthTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public String getProbeId() {
        return probeId;
    }

    public void setProbeId(String probeId) {
        this.probeId = probeId;
    }

    public Double getRxPower() {
        return rxPower;
    }

    public void setRxPower(Double rxPower) {
        this.rxPower = rxPower;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getTxPower() {
        return txPower;
    }

    public void setTxPower(Double txPower) {
        this.txPower = txPower;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
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
        sb.append(", createTime=").append(createTime);
        sb.append(", current=").append(current);
        sb.append(", probeId=").append(probeId);
        sb.append(", rxPower=").append(rxPower);
        sb.append(", temperature=").append(temperature);
        sb.append(", txPower=").append(txPower);
        sb.append(", voltage=").append(voltage);
        sb.append(", timesheet=").append(timesheet);
        sb.append(", monthTime=").append(monthTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}