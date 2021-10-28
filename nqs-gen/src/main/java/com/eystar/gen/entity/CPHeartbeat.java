package com.eystar.gen.entity;

import java.io.Serializable;
import java.util.Date;

public class CPHeartbeat implements Serializable {
    private String id;

    private Long heartbeatTime;

    private String internetIp;

    private String probeId;

    private String soVer;

    private String softVer;

    private Long taskQueueSize;

    private Long taskSize;

    private String probeName;

    private String probeType;

    private Long type;

    private Date heartbeatTimePar;

    private Long heartbeatTimeD;

    private Long heartbeatTimeH;

    private Long heartbeatTimeM;

    private Long heartbeatTimeW;

    private Long createTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(Long heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

    public String getInternetIp() {
        return internetIp;
    }

    public void setInternetIp(String internetIp) {
        this.internetIp = internetIp;
    }

    public String getProbeId() {
        return probeId;
    }

    public void setProbeId(String probeId) {
        this.probeId = probeId;
    }

    public String getSoVer() {
        return soVer;
    }

    public void setSoVer(String soVer) {
        this.soVer = soVer;
    }

    public String getSoftVer() {
        return softVer;
    }

    public void setSoftVer(String softVer) {
        this.softVer = softVer;
    }

    public Long getTaskQueueSize() {
        return taskQueueSize;
    }

    public void setTaskQueueSize(Long taskQueueSize) {
        this.taskQueueSize = taskQueueSize;
    }

    public Long getTaskSize() {
        return taskSize;
    }

    public void setTaskSize(Long taskSize) {
        this.taskSize = taskSize;
    }

    public String getProbeName() {
        return probeName;
    }

    public void setProbeName(String probeName) {
        this.probeName = probeName;
    }

    public String getProbeType() {
        return probeType;
    }

    public void setProbeType(String probeType) {
        this.probeType = probeType;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Date getHeartbeatTimePar() {
        return heartbeatTimePar;
    }

    public void setHeartbeatTimePar(Date heartbeatTimePar) {
        this.heartbeatTimePar = heartbeatTimePar;
    }

    public Long getHeartbeatTimeD() {
        return heartbeatTimeD;
    }

    public void setHeartbeatTimeD(Long heartbeatTimeD) {
        this.heartbeatTimeD = heartbeatTimeD;
    }

    public Long getHeartbeatTimeH() {
        return heartbeatTimeH;
    }

    public void setHeartbeatTimeH(Long heartbeatTimeH) {
        this.heartbeatTimeH = heartbeatTimeH;
    }

    public Long getHeartbeatTimeM() {
        return heartbeatTimeM;
    }

    public void setHeartbeatTimeM(Long heartbeatTimeM) {
        this.heartbeatTimeM = heartbeatTimeM;
    }

    public Long getHeartbeatTimeW() {
        return heartbeatTimeW;
    }

    public void setHeartbeatTimeW(Long heartbeatTimeW) {
        this.heartbeatTimeW = heartbeatTimeW;
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
        sb.append(", heartbeatTime=").append(heartbeatTime);
        sb.append(", internetIp=").append(internetIp);
        sb.append(", probeId=").append(probeId);
        sb.append(", soVer=").append(soVer);
        sb.append(", softVer=").append(softVer);
        sb.append(", taskQueueSize=").append(taskQueueSize);
        sb.append(", taskSize=").append(taskSize);
        sb.append(", probeName=").append(probeName);
        sb.append(", probeType=").append(probeType);
        sb.append(", type=").append(type);
        sb.append(", heartbeatTimePar=").append(heartbeatTimePar);
        sb.append(", heartbeatTimeD=").append(heartbeatTimeD);
        sb.append(", heartbeatTimeH=").append(heartbeatTimeH);
        sb.append(", heartbeatTimeM=").append(heartbeatTimeM);
        sb.append(", heartbeatTimeW=").append(heartbeatTimeW);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}