package com.eystar.gen.entity.gwdata;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class GwFlashData extends  GwData implements Serializable {


    @JSONField(name="real_url")
    private String realUrl;

    @JSONField(name="conn_cost")
    private Double connCost;

    @JSONField(name="dns_cost")
    private Double dnsCost;

    @JSONField(name="buffer_cost")
    private Double bufferCost;

    @JSONField(name="content_size")
    private Double contentSize;

    @JSONField(name="avg_speed")
    private Double avgSpeed;

    @JSONField(name="first_byte_cost")
    private Double firstByteCost;

    @JSONField(name="first_pause_cost")
    private Double firstPauseCost;

    private Double bitrate;

    @JSONField(name="play_duration")
    private Double playDuration;

    @JSONField(name="pause_cost")
    private Double pauseCost;

    @JSONField(name="pause_count")
    private Double pauseCount;

    @JSONField(name="carlton_rate")
    private Double carltonRate;


    @Override
    public String toString() {
        return "GwFlashData{" +
                "realUrl='" + realUrl + '\'' +
                ", connCost=" + connCost +
                ", dnsCost=" + dnsCost +
                ", bufferCost=" + bufferCost +
                ", contentSize=" + contentSize +
                ", avgSpeed=" + avgSpeed +
                ", firstByteCost=" + firstByteCost +
                ", firstPauseCost=" + firstPauseCost +
                ", bitrate=" + bitrate +
                ", playDuration=" + playDuration +
                ", pauseCost=" + pauseCost +
                ", pauseCount=" + pauseCount +
                ", carltonRate=" + carltonRate +
                '}';
    }

    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }

    public Double getConnCost() {
        return connCost;
    }

    public void setConnCost(Double connCost) {
        this.connCost = connCost;
    }

    public Double getDnsCost() {
        return dnsCost;
    }

    public void setDnsCost(Double dnsCost) {
        this.dnsCost = dnsCost;
    }

    public Double getBufferCost() {
        return bufferCost;
    }

    public void setBufferCost(Double bufferCost) {
        this.bufferCost = bufferCost;
    }

    public Double getContentSize() {
        return contentSize;
    }

    public void setContentSize(Double contentSize) {
        this.contentSize = contentSize;
    }

    public Double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(Double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public Double getFirstByteCost() {
        return firstByteCost;
    }

    public void setFirstByteCost(Double firstByteCost) {
        this.firstByteCost = firstByteCost;
    }

    public Double getFirstPauseCost() {
        return firstPauseCost;
    }

    public void setFirstPauseCost(Double firstPauseCost) {
        this.firstPauseCost = firstPauseCost;
    }

    public Double getBitrate() {
        return bitrate;
    }

    public void setBitrate(Double bitrate) {
        this.bitrate = bitrate;
    }

    public Double getPlayDuration() {
        return playDuration;
    }

    public void setPlayDuration(Double playDuration) {
        this.playDuration = playDuration;
    }

    public Double getPauseCost() {
        return pauseCost;
    }

    public void setPauseCost(Double pauseCost) {
        this.pauseCost = pauseCost;
    }

    public Double getPauseCount() {
        return pauseCount;
    }

    public void setPauseCount(Double pauseCount) {
        this.pauseCount = pauseCount;
    }

    public Double getCarltonRate() {
        return carltonRate;
    }

    public void setCarltonRate(Double carltonRate) {
        this.carltonRate = carltonRate;
    }



}