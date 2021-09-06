package com.eystar.gen.entity.gwdata;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class GwData   implements Serializable {

    private static final long serialVersionUID = 1L;


    private String id;

    @JSONField(name="probe_id")
    private String probeId;

    @JSONField(name="probe_name")
    private String probeName;

    @JSONField(name="probe_ip")
    private String probeIp;

    @JSONField(name="probe_alias")
    private String probeAlias;

    @JSONField(name="pppoe_username")
    private String pppoeUsername;

    private String vendor;
    private String pc;
    private String loid;

    @JSONField(name="serial_num")
    private String serialNum;

    @JSONField(name="task_param_id")
    private String taskParamId;

    @JSONField(name="task_param_name")
    private String taskParamName;

    @JSONField(name="task_type_name")
    private String taskTypeName;

    @JSONField(name="task_id")
    private String taskId;

    @JSONField(name="task_md5")
    private String taskMd5;

    @JSONField(name="access_type_name")
    private String accessTypeName;

    @JSONField(name="test_time")
    private Long testTime;

    @JSONField(name="test_time_d")
    private Long testTimeD;

    @JSONField(name="test_time_h")
    private Long testTimeH;

    @JSONField(name="test_time_m")
    private Long testTimeM;

    @JSONField(name="test_time_w")
    private Long testTimeW;

    @JSONField(name="dest_id")
    private String destId;

    @JSONField(name="dest_name")
    private String destName;

    @JSONField(name="dest_addr")
    private String destAddr;

    @JSONField(name="province_code")
    private Long provinceCode;

    @JSONField(name="province_name")
    private String provinceName;

    @JSONField(name="city_code")
    private Long cityCode;

    @JSONField(name="city_name")
    private String cityName;

    @JSONField(name="district_code")
    private Long districtCode;

    @JSONField(name="district_name")
    private String districtName;

    @JSONField(name="town_code")
    private Long townCode;

    @JSONField(name="town_name")
    private String townName;

    @JSONField(name="task_from")
    private Integer taskFrom;

    @JSONField(name="host_province")
    private String hostProvince;

    @JSONField(name="host_city")
    private String hostCity;

    private String operator;

    @JSONField(name="net_type")
    private Integer netType;

    @JSONField(name="is_alarm")
    private Integer isAlarm;

    @JSONField(name="alarm_info")
    private String alarmInfo;
    private Double score;

    @JSONField(name="host_ip")
    private String hostIp;


    @JSONField(name="error_code")
    private Integer errorCode;


    @JSONField(name="create_time")
    private Long createTime;

    @JSONField(name="month_time")
    private Date monthTime;

    @Override
    public String toString() {
        return "GwData{" +
                "id='" + id + '\'' +
                ", accessTypeName='" + accessTypeName + '\'' +
                ", testTime=" + testTime +
                ", destId='" + destId + '\'' +
                ", destAddr='" + destAddr + '\'' +
                ", provinceCode=" + provinceCode +
                ", provinceName='" + provinceName + '\'' +
                ", cityCode=" + cityCode +
                ", cityName='" + cityName + '\'' +
                ", districtCode=" + districtCode +
                ", districtName='" + districtName + '\'' +
                ", probeId='" + probeId + '\'' +
                ", townCode=" + townCode +
                ", townName='" + townName + '\'' +
                ", taskFrom=" + taskFrom +
                ", testTimeH=" + testTimeH +
                ", probeAlias='" + probeAlias + '\'' +
                ", testTimeD=" + testTimeD +
                ", destName='" + destName + '\'' +
                ", probeIp='" + probeIp + '\'' +
                ", probeName='" + probeName + '\'' +
                ", pppoeUsername='" + pppoeUsername + '\'' +
                ", loid='" + loid + '\'' +
                ", hostCity='" + hostCity + '\'' +
                ", serialNum='" + serialNum + '\'' +
                ", taskParamId='" + taskParamId + '\'' +
                ", testTimeW=" + testTimeW +
                ", taskParamName='" + taskParamName + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskTypeName='" + taskTypeName + '\'' +
                ", alarmInfo='" + alarmInfo + '\'' +
                ", vendor='" + vendor + '\'' +
                ", taskMd5='" + taskMd5 + '\'' +
                ", hostProvince='" + hostProvince + '\'' +
                ", pc='" + pc + '\'' +
                ", operator='" + operator + '\'' +
                ", netType=" + netType +
                ", testTimeM=" + testTimeM +
                ", isAlarm=" + isAlarm +
                ", createTime=" + createTime +
                ", monthTime=" + monthTime +
                ", hostIp='" + hostIp + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getAccessTypeName() {
        return accessTypeName;
    }

    public void setAccessTypeName(String accessTypeName) {
        this.accessTypeName = accessTypeName;
    }

    public Long getTestTime() {
        return testTime;
    }

    public void setTestTime(Long testTime) {
        this.testTime = testTime;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public String getDestAddr() {
        return destAddr;
    }

    public void setDestAddr(String destAddr) {
        this.destAddr = destAddr;
    }

    public Long getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Long provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Long getCityCode() {
        return cityCode;
    }

    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Long districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getProbeId() {
        return probeId;
    }

    public void setProbeId(String probeId) {
        this.probeId = probeId;
    }

    public Long getTownCode() {
        return townCode;
    }

    public void setTownCode(Long townCode) {
        this.townCode = townCode;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public Integer getTaskFrom() {
        return taskFrom;
    }

    public void setTaskFrom(Integer taskFrom) {
        this.taskFrom = taskFrom;
    }

    public Long getTestTimeH() {
        return testTimeH;
    }

    public void setTestTimeH(Long testTimeH) {
        this.testTimeH = testTimeH;
    }

    public String getProbeAlias() {
        return probeAlias;
    }

    public void setProbeAlias(String probeAlias) {
        this.probeAlias = probeAlias;
    }

    public Long getTestTimeD() {
        return testTimeD;
    }

    public void setTestTimeD(Long testTimeD) {
        this.testTimeD = testTimeD;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    public String getProbeIp() {
        return probeIp;
    }

    public void setProbeIp(String probeIp) {
        this.probeIp = probeIp;
    }

    public String getProbeName() {
        return probeName;
    }

    public void setProbeName(String probeName) {
        this.probeName = probeName;
    }

    public String getPppoeUsername() {
        return pppoeUsername;
    }

    public void setPppoeUsername(String pppoeUsername) {
        this.pppoeUsername = pppoeUsername;
    }

    public String getLoid() {
        return loid;
    }

    public void setLoid(String loid) {
        this.loid = loid;
    }

    public String getHostCity() {
        return hostCity;
    }

    public void setHostCity(String hostCity) {
        this.hostCity = hostCity;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getTaskParamId() {
        return taskParamId;
    }

    public void setTaskParamId(String taskParamId) {
        this.taskParamId = taskParamId;
    }

    public Long getTestTimeW() {
        return testTimeW;
    }

    public void setTestTimeW(Long testTimeW) {
        this.testTimeW = testTimeW;
    }

    public String getTaskParamName() {
        return taskParamName;
    }

    public void setTaskParamName(String taskParamName) {
        this.taskParamName = taskParamName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTypeName() {
        return taskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        this.taskTypeName = taskTypeName;
    }

    public String getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(String alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getTaskMd5() {
        return taskMd5;
    }

    public void setTaskMd5(String taskMd5) {
        this.taskMd5 = taskMd5;
    }

    public String getHostProvince() {
        return hostProvince;
    }

    public void setHostProvince(String hostProvince) {
        this.hostProvince = hostProvince;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getNetType() {
        return netType;
    }

    public void setNetType(Integer netType) {
        this.netType = netType;
    }

    public Long getTestTimeM() {
        return testTimeM;
    }

    public void setTestTimeM(Long testTimeM) {
        this.testTimeM = testTimeM;
    }

    public Integer getIsAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(Integer isAlarm) {
        this.isAlarm = isAlarm;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Date getMonthTime() {
        return monthTime;
    }

    public void setMonthTime(Date monthTime) {
        this.monthTime = monthTime;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }
}
