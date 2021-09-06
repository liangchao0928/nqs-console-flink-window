package com.eystar.gen.entity;

import java.io.Serializable;
import java.util.Date;

public class CPHeartbeat implements Serializable {
    private String id;

    private Integer type;

    private String probeType;

    private String probeName;

    private Integer taskSize;

    private Integer taskQueueSize;

    private String softVer;

    private String soVer;

    private String probeId;

    private String internetIp;

    private Long heartbeatTime;

    private Date monthTime;

    private Long createTime;

    private Long createHour;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getProbeType() {
        return probeType;
    }

    public void setProbeType(String probeType) {
        this.probeType = probeType;
    }

    public String getProbeName() {
        return probeName;
    }

    public void setProbeName(String probeName) {
        this.probeName = probeName;
    }

    public Integer getTaskSize() {
        return taskSize;
    }

    public void setTaskSize(Integer taskSize) {
        this.taskSize = taskSize;
    }

    public Integer getTaskQueueSize() {
        return taskQueueSize;
    }

    public void setTaskQueueSize(Integer taskQueueSize) {
        this.taskQueueSize = taskQueueSize;
    }

    public String getSoftVer() {
        return softVer;
    }

    public void setSoftVer(String softVer) {
        this.softVer = softVer;
    }

    public String getSoVer() {
        return soVer;
    }

    public void setSoVer(String soVer) {
        this.soVer = soVer;
    }

    public String getProbeId() {
        return probeId;
    }

    public void setProbeId(String probeId) {
        this.probeId = probeId;
    }

    public String getInternetIp() {
        return internetIp;
    }

    public void setInternetIp(String internetIp) {
        this.internetIp = internetIp;
    }

    public Long getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(Long heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

    public Date getMonthTime() {
        return monthTime;
    }

    public void setMonthTime(Date monthTime) {
        this.monthTime = monthTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getCreateHour() {
        return createHour;
    }

    public void setCreateHour(Long createHour) {
        this.createHour = createHour;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", probeType=").append(probeType);
        sb.append(", probeName=").append(probeName);
        sb.append(", taskSize=").append(taskSize);
        sb.append(", taskQueueSize=").append(taskQueueSize);
        sb.append(", softVer=").append(softVer);
        sb.append(", soVer=").append(soVer);
        sb.append(", probeId=").append(probeId);
        sb.append(", internetIp=").append(internetIp);
        sb.append(", heartbeatTime=").append(heartbeatTime);
        sb.append(", monthTime=").append(monthTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", createHour=").append(createHour);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}