package com.eystar.console.handler.thread;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.common.util.Constants;
import com.eystar.common.util.NumberKit;

import com.eystar.console.util.InfoLoader;
import com.eystar.gen.entity.TPProbe;
import com.eystar.gen.service.ProbeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ProbeInfoThread {

    private final static Logger logger = LoggerFactory.getLogger(ProbeInfoThread.class);


    private  static RedisUtils redisUtils;

    private  static ProbeService probeService;



    public static void init(RedisUtils ru,ProbeService ps){
        redisUtils=ru;
        probeService=ps;
    }

    public static void run( JSONObject probeInfoJson) {
        try {
            if (null == probeInfoJson) {
                return;
            }
            System.out.println("开始更新探针信息，探针ID = " + probeInfoJson.getString("id"));
            TPProbe probe = new TPProbe();
            String id = probeInfoJson.getString("id"); // 探针Id
            String loid = probeInfoJson.getString("loid"); // 获取设备认证号
            String pppoe_username = probeInfoJson.getString("pppoe_username"); // 探针pppoe_username
            int type = NumberKit.valueOfInt(probeInfoJson.getInteger("type"), 10);
            int hb_interval = NumberKit.valueOfInt(probeInfoJson.getInteger("hb_interval"), 120); // 心跳间隔（单位秒）
            int info_interval = NumberKit.valueOfInt(probeInfoJson.getInteger("info_interval"), 600); // 网关信息上报间隔
            int flow_interval = NumberKit.valueOfInt(probeInfoJson.getInteger("flow_interval"), 600); // 流量采集间隔
            int log_interval = NumberKit.valueOfInt(probeInfoJson.getInteger("log_interval"), 1800); // 上报日志时间间隔
            int data_interval = NumberKit.valueOfInt(probeInfoJson.getInteger("data_interval"), 60); // 上报数据时间间隔
            String ip = probeInfoJson.getString("ip"); // ip
            String sn = probeInfoJson.getString("sn"); // 序列号
            String pc = probeInfoJson.getString("pc"); // 设备型号（软）
            String vendor = probeInfoJson.getString("vendor"); // 厂商（软）
            String cpu = probeInfoJson.getString("cpu"); // 光猫CPU（固件）
            String hard_ver = probeInfoJson.getString("hard_ver"); // 光猫固件硬件版本（固件）
            String firm_ver = probeInfoJson.getString("firm_ver"); // 光猫固件软件版本（固件）
            String mac = StrUtil.isBlank(probeInfoJson.getString("mac")) ? UUID.randomUUID().toString() : probeInfoJson.getString("mac"); // 获取设备MAC
            double flash_size = NumberKit.valueOfDouble(probeInfoJson.getDouble("flash_size"), 0d); // Flash
            double ram_size = NumberKit.valueOfDouble(probeInfoJson.getDouble("ram_size"), 0d); // 内存大小
            String auth_url = probeInfoJson.getString("auth_url"); // "http://192.168.1.176:9889/auth",
            String communicate_url = probeInfoJson.getString("communicate_url"); // "http://192.168.1.176:5557/chan",#通信地址
            String mq_url = probeInfoJson.getString("mq_url"); // "tcp://192.168.1.171:1883",
            String flash_server_url = probeInfoJson.getString("flash_server_url"); // "http://sgw.eystar.com:28428/"#flash代理地址
            double ram_rate = NumberKit.valueOfDouble(probeInfoJson.getDouble("ram_rate"), 0d);
            double cpu_rate = NumberKit.valueOfDouble(probeInfoJson.getDouble("cpu_rate"), 0d);
            String probe_name = probeInfoJson.getString("probe_name");
            // 获取探针的信息（Reids中，如果Reids没有从数据库中获取，并且存入Reids中）
            JSONObject probeInfo = InfoLoader.loadProbe(id);
            probe.setId(id);
            probe.setSn(sn);
            probe.setHbInterval(hb_interval);
            probe.setInfoInterval(info_interval);
            probe.setFlowInterval(flow_interval);
            probe.setLogInterval(log_interval);
            probe.setDataInterval(data_interval);
            probe.setType((short)type);
            if ("ÿÿÿÿ".equals(pc)) {
                pc = "DT741-csf";
            }
            probe.setPc(pc);
            probe.setVendor(vendor);
            probe.setMac(mac);
            probe.setRamSize(Float.valueOf(ram_size+""));
            probe.setFlashSize(Float.valueOf(flash_size+""));
            probe.setIp(ip);
            probe.setFirmVer(firm_ver);
            probe.setHardVer(hard_ver);
            probe.setCpu(cpu);
            probe.setAuthUrl(auth_url);
            probe.setCommunicateUrl(communicate_url);
            probe.setMqUrl(mq_url);
            probe.setFlashServerUrl(flash_server_url);
            probe.setPppoeUsername(pppoe_username);
            probe.setRamRate(Float.valueOf(ram_rate+""));
            probe.setCpuRate(Float.valueOf(cpu_rate+""));
            probe.setProbeName(probe_name);
            probe.setLoid(loid);
            probe.setUpdateTime(System.currentTimeMillis() / 1000);
            try{
                probe.setBrasIp(redisUtils.hget(Constants.REDIS_KEY_BRAS_IPS_DATA, ip));
            }catch (Exception e){

            }

            if (StrUtil.isNotBlank(probe_name) && probeInfo.getIntValue("probe_alias_modified") == 0) { // 如果探针的别名没有被修改过，探针别名和探针名称相同
                probe.setProbeAlias(probe_name);
            } else {
                probe.setProbeAlias( probeInfo.getString("probe_alias"));
            }
            try {
                probeService.updateProbe(probe);
                System.out.println("更新探针信息到MySQL完成，探针ID = " + id);
            } catch (Exception e) {
                System.out.println("更新MySQL表中探针的信息出现异常，可能由于探针已经被删除，等待下一次探针心跳注册，探针ID = " + id+e.getMessage());
            }

            if (!probeInfo.isEmpty()) {
                probeInfo.put("ip", ip);
                probeInfo.put("hb_interval", hb_interval);
                probeInfo.put("sn", sn);
                probeInfo.put("loid", loid);
                probeInfo.put("pppoe_username", pppoe_username);
                probeInfo.put("probe_name", probe_name);
                probeInfo.put("probe_alias", probe.getProbeAlias());
                probeInfo.put("type", probe.getType());
                redisUtils.setex(Constants.REDIS_KEY_PROBE + id, Constants.REDIS_KEY_EXPIRE_ONE_HOUR, probeInfo.toJSONString());
                System.out.println("更新探针ID = " + id + "，redis信息完成，" + probeInfo.toJSONString());
            }
        } catch (Exception e) {
            System.out.println("更新探针信息出现异常，待更新的探针信息 = " + probeInfoJson+e.getMessage());
        }
    }




}
