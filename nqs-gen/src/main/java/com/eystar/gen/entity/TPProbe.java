package com.eystar.gen.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class TPProbe implements Serializable {

    private String id;

    @JSONField(name="probe_name")
    @ApiModelProperty(value = "探针名称(探针上报)")
    private String probeName;

    @JSONField(name="probe_alias")
    @ApiModelProperty(value = "探针别名（页面展示所用）")
    private String probeAlias;

    @ApiModelProperty(value = "探针类型（10-硬探针，11-软探针，20-win探针，12-便携探针，30-安卓探针，31-苹果探针，40-插件探针）")
    private Short type;

    @ApiModelProperty(value = "IP（插件）")
    private String ip;

    @JSONField(name="internet_ip")
    @ApiModelProperty(value = "网关通信接口携带ip（webs中获取的）")
    private String internetIp;

    @ApiModelProperty(value = "探针状态（10-在线，20-离线）")
    private Short status;

    @ApiModelProperty(value = "运营商编码（10-联通，20-移动，30-电信）（插件）")
    private Short operator;

    @JSONField(name="soft_ver")
    @ApiModelProperty(value = "软件版本（插件）")
    private String softVer;

    @JSONField(name="hb_interval")
    @ApiModelProperty(value = "心跳间隔（秒）（插件）")
    private Integer hbInterval;

    @JSONField(name="log_interval")
    @ApiModelProperty(value = "日志上报间隔（秒）")
    private Integer logInterval;

    @JSONField(name="data_interval")
    @ApiModelProperty(value = "数据上报间隔（秒）")
    private Integer dataInterval;

    @JSONField(name="flow_interval")
    @ApiModelProperty(value = "流量采集间隔（秒）")
    private Integer flowInterval;

    @JSONField(name="info_interval")
    @ApiModelProperty(value = "设备信息上报间隔（秒）")
    private Integer infoInterval;

    @JSONField(name="last_regist_time")
    @ApiModelProperty(value = "最后注册时间（秒）（插件）")
    private Long lastRegistTime;

    @JSONField(name="last_heartbeat_time")
    @ApiModelProperty(value = "最后心跳时间（秒）")
    private Long lastHeartbeatTime;

    @JSONField(name="mq_url")
    @ApiModelProperty(value = "mq地址")
    private String mqUrl;

    @JSONField(name="communicate_url")
    @ApiModelProperty(value = "通信地址")
    private String communicateUrl;


    @JSONField(name="flash_server_url")
    @ApiModelProperty(value = "flash代理地址")
    private String flashServerUrl;

    @JSONField(name="auth_url")
    @ApiModelProperty(value = "认证地址")
    private String authUrl;

    @ApiModelProperty(value = "描述（插件）")
    private String memo;

    @JSONField(name="task_num")
    @ApiModelProperty(value = "探针上的任务数（插件），平台计算的")
    private Integer taskNum;

    @ApiModelProperty(value = "设备认证号（固件）")
    private String loid;

    @ApiModelProperty(value = "厂商（固件）")
    private String vendor;

    @ApiModelProperty(value = "序列号（固件）")
    private String sn;

    @ApiModelProperty(value = "设备型号（固件）")
    private String pc;

    @JSONField(name="ram_size")
    @ApiModelProperty(value = "内存大小，单位（Mbytes）（固件）")
    private Float ramSize;

    @JSONField(name="flash_size")
    @ApiModelProperty(value = "Flash 大小，单位（Mbytes）（固件）")
    private Float flashSize;

    @ApiModelProperty(value = "获取设备MAC（可作为设备标识）（固件）")
    private String mac;

    @JSONField(name="firm_ver")
    @ApiModelProperty(value = "光猫软件版本（固件）")
    private String firmVer;

    @JSONField(name="hard_ver")
    @ApiModelProperty(value = "光猫硬件版本（固件）")
    private String hardVer;

    @ApiModelProperty(value = "cpu厂商和型号（固件）")
    private String cpu;

    @JSONField(name="province_code")
    @ApiModelProperty(value = "省编码")
    private Long provinceCode;

    @JSONField(name="province_name")
    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @JSONField(name="city_code")
    @ApiModelProperty(value = "市编码")
    private Long cityCode;

    @JSONField(name="city_name")
    @ApiModelProperty(value = "市名称")
    private String cityName;

    @JSONField(name="district_code")
    @ApiModelProperty(value = "区县编码")
    private Long districtCode;

    @JSONField(name="district_name")
    @ApiModelProperty(value = "区县名称")
    private String districtName;

    @JSONField(name="town_code")
    @ApiModelProperty(value = "乡镇编码")
    private Long townCode;

    @JSONField(name="town_name")
    @ApiModelProperty(value = "乡镇名称")
    private String townName;

    @JSONField(name="region_path")
    @ApiModelProperty(value = "地域path路径")
    private String regionPath;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "精度")
    private Float lat;

    @ApiModelProperty(value = "维度")
    private Float lng;

    @JSONField(name="bras_ip")
    private String brasIp;

    @JSONField(name="cr_ip")
    private String crIp;

    @JSONField(name="nat_ip")
    @ApiModelProperty(value = "网关NAT的IP地址")
    private String natIp;

    @JSONField(name="olt_ip")
    @ApiModelProperty(value = "网关OLT的IP地址")
    private String oltIp;

    @JSONField(name="so_ver")
    @ApiModelProperty(value = "网关插件so版本")
    private String soVer;

    @JSONField(name="task_queue_size")
    @ApiModelProperty(value = "当前网关插件待执行的任务数量")
    private Integer taskQueueSize;

    @JSONField(name="task_size")
    @ApiModelProperty(value = "当前网关插件所有任务数，探针上报的")
    private Integer taskSize;

    @JSONField(name="cpu_rate")
    @ApiModelProperty(value = "cpu使用率")
    private Float cpuRate;

    @JSONField(name="ram_rate")
    @ApiModelProperty(value = "内存利用率")
    private Float ramRate;

    @JSONField(name="pppoe_username")
    @ApiModelProperty(value = "拨号用户帐号")
    private String pppoeUsername;

    @JSONField(name="create_user")
    @ApiModelProperty(value = "创建用户名")
    private String createUser;

    @JSONField(name="update_user")
    @ApiModelProperty(value = "修改用户名")
    private String updateUser;

    @JSONField(name="create_time")
    @ApiModelProperty(value = "创建时间（Unix时间戳）")
    private Long createTime;

    @JSONField(name="update_time")
    @ApiModelProperty(value = "修改时间（Unix时间戳）")
    private Long updateTime;

    @JSONField(name="delete_flag")
    @ApiModelProperty(value = "是否删除（1-是，0-否）")
    private Short deleteFlag;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProbeName() {
        return probeName;
    }

    public void setProbeName(String probeName) {
        this.probeName = probeName;
    }

    public String getProbeAlias() {
        return probeAlias;
    }

    public void setProbeAlias(String probeAlias) {
        this.probeAlias = probeAlias;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getInternetIp() {
        return internetIp;
    }

    public void setInternetIp(String internetIp) {
        this.internetIp = internetIp;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getOperator() {
        return operator;
    }

    public void setOperator(Short operator) {
        this.operator = operator;
    }

    public String getSoftVer() {
        return softVer;
    }

    public void setSoftVer(String softVer) {
        this.softVer = softVer;
    }

    public Integer getHbInterval() {
        return hbInterval;
    }

    public void setHbInterval(Integer hbInterval) {
        this.hbInterval = hbInterval;
    }

    public Integer getLogInterval() {
        return logInterval;
    }

    public void setLogInterval(Integer logInterval) {
        this.logInterval = logInterval;
    }

    public Integer getDataInterval() {
        return dataInterval;
    }

    public void setDataInterval(Integer dataInterval) {
        this.dataInterval = dataInterval;
    }

    public Integer getFlowInterval() {
        return flowInterval;
    }

    public void setFlowInterval(Integer flowInterval) {
        this.flowInterval = flowInterval;
    }

    public Integer getInfoInterval() {
        return infoInterval;
    }

    public void setInfoInterval(Integer infoInterval) {
        this.infoInterval = infoInterval;
    }

    public Long getLastRegistTime() {
        return lastRegistTime;
    }

    public void setLastRegistTime(Long lastRegistTime) {
        this.lastRegistTime = lastRegistTime;
    }

    public Long getLastHeartbeatTime() {
        return lastHeartbeatTime;
    }

    public void setLastHeartbeatTime(Long lastHeartbeatTime) {
        this.lastHeartbeatTime = lastHeartbeatTime;
    }

    public String getMqUrl() {
        return mqUrl;
    }

    public void setMqUrl(String mqUrl) {
        this.mqUrl = mqUrl;
    }

    public String getCommunicateUrl() {
        return communicateUrl;
    }

    public void setCommunicateUrl(String communicateUrl) {
        this.communicateUrl = communicateUrl;
    }

    public String getFlashServerUrl() {
        return flashServerUrl;
    }

    public void setFlashServerUrl(String flashServerUrl) {
        this.flashServerUrl = flashServerUrl;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(Integer taskNum) {
        this.taskNum = taskNum;
    }

    public String getLoid() {
        return loid;
    }

    public void setLoid(String loid) {
        this.loid = loid;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public Float getRamSize() {
        return ramSize;
    }

    public void setRamSize(Float ramSize) {
        this.ramSize = ramSize;
    }

    public Float getFlashSize() {
        return flashSize;
    }

    public void setFlashSize(Float flashSize) {
        this.flashSize = flashSize;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getFirmVer() {
        return firmVer;
    }

    public void setFirmVer(String firmVer) {
        this.firmVer = firmVer;
    }

    public String getHardVer() {
        return hardVer;
    }

    public void setHardVer(String hardVer) {
        this.hardVer = hardVer;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
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

    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public String getBrasIp() {
        return brasIp;
    }

    public void setBrasIp(String brasIp) {
        this.brasIp = brasIp;
    }

    public String getCrIp() {
        return crIp;
    }

    public void setCrIp(String crIp) {
        this.crIp = crIp;
    }

    public String getNatIp() {
        return natIp;
    }

    public void setNatIp(String natIp) {
        this.natIp = natIp;
    }

    public String getOltIp() {
        return oltIp;
    }

    public void setOltIp(String oltIp) {
        this.oltIp = oltIp;
    }

    public String getSoVer() {
        return soVer;
    }

    public void setSoVer(String soVer) {
        this.soVer = soVer;
    }

    public Integer getTaskQueueSize() {
        return taskQueueSize;
    }

    public void setTaskQueueSize(Integer taskQueueSize) {
        this.taskQueueSize = taskQueueSize;
    }

    public Integer getTaskSize() {
        return taskSize;
    }

    public void setTaskSize(Integer taskSize) {
        this.taskSize = taskSize;
    }

    public Float getCpuRate() {
        return cpuRate;
    }

    public void setCpuRate(Float cpuRate) {
        this.cpuRate = cpuRate;
    }

    public Float getRamRate() {
        return ramRate;
    }

    public void setRamRate(Float ramRate) {
        this.ramRate = ramRate;
    }

    public String getPppoeUsername() {
        return pppoeUsername;
    }

    public void setPppoeUsername(String pppoeUsername) {
        this.pppoeUsername = pppoeUsername;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", probeName=").append(probeName);
        sb.append(", probeAlias=").append(probeAlias);
        sb.append(", type=").append(type);
        sb.append(", ip=").append(ip);
        sb.append(", internetIp=").append(internetIp);
        sb.append(", status=").append(status);
        sb.append(", operator=").append(operator);
        sb.append(", softVer=").append(softVer);
        sb.append(", hbInterval=").append(hbInterval);
        sb.append(", logInterval=").append(logInterval);
        sb.append(", dataInterval=").append(dataInterval);
        sb.append(", flowInterval=").append(flowInterval);
        sb.append(", infoInterval=").append(infoInterval);
        sb.append(", lastRegistTime=").append(lastRegistTime);
        sb.append(", lastHeartbeatTime=").append(lastHeartbeatTime);
        sb.append(", mqUrl=").append(mqUrl);
        sb.append(", communicateUrl=").append(communicateUrl);
        sb.append(", flashServerUrl=").append(flashServerUrl);
        sb.append(", authUrl=").append(authUrl);
        sb.append(", memo=").append(memo);
        sb.append(", taskNum=").append(taskNum);
        sb.append(", loid=").append(loid);
        sb.append(", vendor=").append(vendor);
        sb.append(", sn=").append(sn);
        sb.append(", pc=").append(pc);
        sb.append(", ramSize=").append(ramSize);
        sb.append(", flashSize=").append(flashSize);
        sb.append(", mac=").append(mac);
        sb.append(", firmVer=").append(firmVer);
        sb.append(", hardVer=").append(hardVer);
        sb.append(", cpu=").append(cpu);
        sb.append(", provinceCode=").append(provinceCode);
        sb.append(", provinceName=").append(provinceName);
        sb.append(", cityCode=").append(cityCode);
        sb.append(", cityName=").append(cityName);
        sb.append(", districtCode=").append(districtCode);
        sb.append(", districtName=").append(districtName);
        sb.append(", townCode=").append(townCode);
        sb.append(", townName=").append(townName);
        sb.append(", regionPath=").append(regionPath);
        sb.append(", address=").append(address);
        sb.append(", lat=").append(lat);
        sb.append(", lng=").append(lng);
        sb.append(", brasIp=").append(brasIp);
        sb.append(", crIp=").append(crIp);
        sb.append(", natIp=").append(natIp);
        sb.append(", oltIp=").append(oltIp);
        sb.append(", soVer=").append(soVer);
        sb.append(", taskQueueSize=").append(taskQueueSize);
        sb.append(", taskSize=").append(taskSize);
        sb.append(", cpuRate=").append(cpuRate);
        sb.append(", ramRate=").append(ramRate);
        sb.append(", pppoeUsername=").append(pppoeUsername);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}