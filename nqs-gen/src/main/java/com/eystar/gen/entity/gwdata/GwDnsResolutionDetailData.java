package com.eystar.gen.entity.gwdata;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class GwDnsResolutionDetailData extends  GwData implements Serializable  {


    @JSONField(name="t_id")
    private String tId;

    @JSONField(name="query_count")
    private Long queryCount;

    @JSONField(name="success_rate")
    private Double successRate;

    @JSONField(name="time_cost")
    private Double timeCost;

    @JSONField(name="ttl")
    private Double ttl;

    @JSONField(name="ipList")
    private String ip_list;

    @JSONField(name="dns_server_ip")
    private String dnsServerIp;

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public Long getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(Long queryCount) {
        this.queryCount = queryCount;
    }

    public Double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Double successRate) {
        this.successRate = successRate;
    }

    public Double getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(Double timeCost) {
        this.timeCost = timeCost;
    }

    public Double getTtl() {
        return ttl;
    }

    public void setTtl(Double ttl) {
        this.ttl = ttl;
    }

    public String getIp_list() {
        return ip_list;
    }

    public void setIp_list(String ip_list) {
        this.ip_list = ip_list;
    }

    public String getDnsServerIp() {
        return dnsServerIp;
    }

    public void setDnsServerIp(String dnsServerIp) {
        this.dnsServerIp = dnsServerIp;
    }
}
