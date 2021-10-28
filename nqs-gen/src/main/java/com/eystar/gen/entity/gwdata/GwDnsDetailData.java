package com.eystar.gen.entity.gwdata;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class GwDnsDetailData extends  GwData implements Serializable {

    @JSONField(name="parentId")
    private String parent_id;

    @JSONField(name="dnsDomain")
    private String dns_domain;

    @JSONField(name="dnsCost")
    private Double dns_cost;

    @JSONField(name="dnsIp")
    private String dns_ip;

    @JSONField(name="ttl")
    private Double ttl;

    @JSONField(name="success_rate")
    private Double successRate;

    @JSONField(name="record_count")
    private Long recordCount;

    @JSONField(name="time_cost")
    private Double timeCost;









    @JSONField(name="ipList")
    private String ip_list;



    @JSONField(name="detail")
    private String detail;





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

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public Double getTtl() {
        return ttl;
    }

    public void setTtl(Double ttl) {
        this.ttl = ttl;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getIp_list() {
        return ip_list;
    }

    public void setIp_list(String ip_list) {
        this.ip_list = ip_list;
    }

    public Double getDns_cost() {
        return dns_cost;
    }

    public void setDns_cost(Double dns_cost) {
        this.dns_cost = dns_cost;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDns_domain() {
        return dns_domain;
    }

    public void setDns_domain(String dns_domain) {
        this.dns_domain = dns_domain;
    }

    public String getDns_ip() {
        return dns_ip;
    }

    public void setDns_ip(String dns_ip) {
        this.dns_ip = dns_ip;
    }
}
