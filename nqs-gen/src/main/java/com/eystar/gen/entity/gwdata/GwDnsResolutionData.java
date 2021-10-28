package com.eystar.gen.entity.gwdata;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class GwDnsResolutionData extends  GwData implements Serializable {


    @JSONField(name="ip_list")
    private String ipList;

    @JSONField(name="time_cost")
    private Double timeCost;

    @JSONField(name="success_rate")
    private Double successRate;


    public String getIpList() {
        return ipList;
    }

    public void setIpList(String ipList) {
        this.ipList = ipList;
    }

    public Double getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(Double timeCost) {
        this.timeCost = timeCost;
    }

    public Double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Double successRate) {
        this.successRate = successRate;
    }
}
