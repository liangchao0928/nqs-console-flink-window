package com.eystar.gen.entity.gwdata;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class GwPingData extends GwData implements Serializable {

    private Double rtt;

    private Double jitter;

    @JSONField(name="lost_rate")
    private Double lostRate;




    public Double getRtt() {
        return rtt;
    }

    public void setRtt(Double rtt) {
        this.rtt = rtt;
    }

    public Double getJitter() {
        return jitter;
    }

    public void setJitter(Double jitter) {
        this.jitter = jitter;
    }

    public Double getLostRate() {
        return lostRate;
    }

    public void setLostRate(Double lostRate) {
        this.lostRate = lostRate;
    }




    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(super.getId());
        sb.append(", probeId=").append(super.getProbeId());
        sb.append(", probeName=").append(super.getProbeName());
        sb.append(", rtt=").append(rtt);
        sb.append(", jitter=").append(jitter);
        sb.append(", lostRate=").append(lostRate);
        sb.append(", hostIp=").append(super.getHostIp());
        sb.append("]");
        return sb.toString();
    }
}