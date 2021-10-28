package com.eystar.gen.entity;

import java.io.Serializable;
import java.util.Date;

public class CPPon implements Serializable {
    private String id;

    private Double current;

    private String probeId;

    private Double rxPower;

    private Double temperature;

    private Double txPower;

    private Long timesheet;

    private Double voltage;

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

    public Long getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Long timesheet) {
        this.timesheet = timesheet;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
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
        sb.append(", current=").append(current);
        sb.append(", probeId=").append(probeId);
        sb.append(", rxPower=").append(rxPower);
        sb.append(", temperature=").append(temperature);
        sb.append(", txPower=").append(txPower);
        sb.append(", timesheet=").append(timesheet);
        sb.append(", voltage=").append(voltage);
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