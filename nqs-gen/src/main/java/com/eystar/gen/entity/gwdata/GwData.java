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
    private Long taskFrom;

    @JSONField(name="host_province")
    private String hostProvince;

    @JSONField(name="host_city")
    private String hostCity;

    private String operator;

    @JSONField(name="net_type")
    private Long netType;

    @JSONField(name="is_alarm")
    private Long isAlarm;

    @JSONField(name="alarm_info")
    private String alarmInfo;
    private Double score;

    @JSONField(name="host_ip")
    private String hostIp;


    @JSONField(name="error_code")
    private Long errorCode;


    @JSONField(name="create_time")
    private Long createTime;

    @JSONField(name="test_time_par")
    private Date testTimePar;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProbeId() {
        return probeId;
    }

    public void setProbeId(String probeId) {
        this.probeId = probeId;
    }

    public String getProbeName() {
        return probeName;
    }

    public void setProbeName(String probeName) {
        this.probeName = probeName;
    }

    public String getProbeIp() {
        return probeIp;
    }

    public void setProbeIp(String probeIp) {
        this.probeIp = probeIp;
    }

    public String getProbeAlias() {
        return probeAlias;
    }

    public void setProbeAlias(String probeAlias) {
        this.probeAlias = probeAlias;
    }

    public String getPppoeUsername() {
        return pppoeUsername;
    }

    public void setPppoeUsername(String pppoeUsername) {
        this.pppoeUsername = pppoeUsername;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getLoid() {
        return loid;
    }

    public void setLoid(String loid) {
        this.loid = loid;
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

    public String getTaskParamName() {
        return taskParamName;
    }

    public void setTaskParamName(String taskParamName) {
        this.taskParamName = taskParamName;
    }

    public String getTaskTypeName() {
        return taskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        this.taskTypeName = taskTypeName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskMd5() {
        return taskMd5;
    }

    public void setTaskMd5(String taskMd5) {
        this.taskMd5 = taskMd5;
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

    public Long getTestTimeD() {
        return testTimeD;
    }

    public void setTestTimeD(Long testTimeD) {
        this.testTimeD = testTimeD;
    }

    public Long getTestTimeH() {
        return testTimeH;
    }

    public void setTestTimeH(Long testTimeH) {
        this.testTimeH = testTimeH;
    }

    public Long getTestTimeM() {
        return testTimeM;
    }

    public void setTestTimeM(Long testTimeM) {
        this.testTimeM = testTimeM;
    }

    public Long getTestTimeW() {
        return testTimeW;
    }

    public void setTestTimeW(Long testTimeW) {
        this.testTimeW = testTimeW;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
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

    public Long getTaskFrom() {
        return taskFrom;
    }

    public void setTaskFrom(Long taskFrom) {
        this.taskFrom = taskFrom;
    }

    public String getHostProvince() {
        return hostProvince;
    }

    public void setHostProvince(String hostProvince) {
        this.hostProvince = hostProvince;
    }

    public String getHostCity() {
        return hostCity;
    }

    public void setHostCity(String hostCity) {
        this.hostCity = hostCity;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Long getNetType() {
        return netType;
    }

    public void setNetType(Long netType) {
        this.netType = netType;
    }

    public Long getIsAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(Long isAlarm) {
        this.isAlarm = isAlarm;
    }

    public String getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(String alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public Long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Date getTestTimePar() {
        return testTimePar;
    }

    public void setTestTimePar(Date testTimePar) {
        this.testTimePar = testTimePar;
    }
}
