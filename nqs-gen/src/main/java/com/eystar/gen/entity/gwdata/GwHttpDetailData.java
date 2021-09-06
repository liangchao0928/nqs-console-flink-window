package com.eystar.gen.entity.gwdata;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class GwHttpDetailData extends GwData implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(name="parent_id")
    private String parentId;

    @JSONField(name="element_name")
    private String elementName;

    @JSONField(name="element_num")
    private Integer elementNum;

    @JSONField(name="element_url")
    private String elementUrl;

    @JSONField(name="dns_cost")
    private Double dnsCost;

    @JSONField(name="conn_cost")
    private Double connCost;

    @JSONField(name="element_download_cost")
    private Double elementDownloadCost;

    @JSONField(name="first_byte_cost")
    private Double firstByteCost;

    @JSONField(name="element_total_cost")
    private Double elementTotalCost;

    @JSONField(name="thread_num")
    private Long threadNum;

    @JSONField(name="element_status")
    private Integer elementStatus;

    @JSONField(name="element_size")
    private Double elementSize;

    @JSONField(name="avg_speed")
    private Double avgSpeed;

    @JSONField(name="element_http_code")
    private Integer elementHttpCode;

    @JSONField(name="is_localnet")
    private Integer isLocalnet;

    @JSONField(name="tcp_cost")
    private Double tcpCost;

    @JSONField(name="ssl_cost")
    private Double sslCost;



    @Override
    public String toString() {
        return "GwHttpDetailData{" +
                "parentId='" + parentId + '\'' +
                ", elementName='" + elementName + '\'' +
                ", elementNum=" + elementNum +
                ", elementUrl='" + elementUrl + '\'' +
                ", dnsCost=" + dnsCost +
                ", connCost=" + connCost +
                ", elementDownloadCost=" + elementDownloadCost +
                ", firstByteCost=" + firstByteCost +
                ", elementTotalCost=" + elementTotalCost +
                ", threadNum=" + threadNum +
                ", elementStatus=" + elementStatus +
                ", elementSize=" + elementSize +
                ", avgSpeed=" + avgSpeed +
                ", elementHttpCode=" + elementHttpCode +
                ", isLocalnet=" + isLocalnet +
                ", tcpCost=" + tcpCost +
                ", sslCost=" + sslCost +
                '}';
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public Integer getElementNum() {
        return elementNum;
    }

    public void setElementNum(Integer elementNum) {
        this.elementNum = elementNum;
    }

    public String getElementUrl() {
        return elementUrl;
    }

    public void setElementUrl(String elementUrl) {
        this.elementUrl = elementUrl;
    }

    public Double getDnsCost() {
        return dnsCost;
    }

    public void setDnsCost(Double dnsCost) {
        this.dnsCost = dnsCost;
    }

    public Double getConnCost() {
        return connCost;
    }

    public void setConnCost(Double connCost) {
        this.connCost = connCost;
    }

    public Double getElementDownloadCost() {
        return elementDownloadCost;
    }

    public void setElementDownloadCost(Double elementDownloadCost) {
        this.elementDownloadCost = elementDownloadCost;
    }

    public Double getFirstByteCost() {
        return firstByteCost;
    }

    public void setFirstByteCost(Double firstByteCost) {
        this.firstByteCost = firstByteCost;
    }

    public Double getElementTotalCost() {
        return elementTotalCost;
    }

    public void setElementTotalCost(Double elementTotalCost) {
        this.elementTotalCost = elementTotalCost;
    }

    public Long getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(Long threadNum) {
        this.threadNum = threadNum;
    }

    public Integer getElementStatus() {
        return elementStatus;
    }

    public void setElementStatus(Integer elementStatus) {
        this.elementStatus = elementStatus;
    }

    public Double getElementSize() {
        return elementSize;
    }

    public void setElementSize(Double elementSize) {
        this.elementSize = elementSize;
    }

    public Double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public Integer getElementHttpCode() {
        return elementHttpCode;
    }

    public void setElementHttpCode(Integer elementHttpCode) {
        this.elementHttpCode = elementHttpCode;
    }

    public Integer getIsLocalnet() {
        return isLocalnet;
    }

    public void setIsLocalnet(Integer isLocalnet) {
        this.isLocalnet = isLocalnet;
    }

    public Double getTcpCost() {
        return tcpCost;
    }

    public void setTcpCost(Double tcpCost) {
        this.tcpCost = tcpCost;
    }

    public Double getSslCost() {
        return sslCost;
    }

    public void setSslCost(Double sslCost) {
        this.sslCost = sslCost;
    }


}