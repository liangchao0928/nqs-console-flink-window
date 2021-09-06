package com.eystar.console.handler.probe;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.common.util.Constants;
import com.eystar.common.util.RedisModifyHelper;
import com.eystar.common.util.SmartGateWayUtil;
import com.eystar.common.util.UUIDKit;
import com.eystar.gen.entity.CPPon;
import com.eystar.gen.entity.CPStatus;
import com.eystar.gen.entity.CPTraffic;
import com.eystar.gen.entity.TPProbeAccessType;
import com.eystar.gen.service.PPonService;
import com.eystar.gen.service.PStatusService;
import com.eystar.gen.service.ProbeAccessTypeService;
import com.eystar.gen.service.TrafficService;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProbeClickHouseHelper {

    private static RedisUtils redisUtils;

    private static ProbeAccessTypeService probeAccessTypeService;

    private static TrafficService trafficService;

    private static PPonService pPonService;

    private static PStatusService pStatusService;



    public static  void init(RedisUtils ru,ProbeAccessTypeService pat,TrafficService ts,PPonService pp,PStatusService ps){
        redisUtils=ru;
        probeAccessTypeService = pat;
         trafficService = ts;
        pPonService=pp;
        pStatusService=ps;
    }


    public static void saveAccessTypeInfo(String probeId, JSONArray array, long time) {
        if (array == null || array.size() <= 0) {
            return;
        }
        //  防止修改探针配置和 dns 被网关数据冲掉
        if (redisUtils.exists(Constants.PROBE_ACCESS_AMEND + probeId)) {
            System.out.println("探针id = " + probeId + "，已经修改了端口信息，本次上报端口信息无法入口");
            return;
        }
        String key = Constants.REDIS_KEY_ACCESS_TYPE + probeId;
        Map<String, String> map = redisUtils.hgetAll(key);
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            String accessTypeName = object.getString("access_type_name");
            String access_type_json = redisUtils.hget(key, accessTypeName);
            map.remove(accessTypeName);
            if (StrUtil.isBlank(access_type_json)) {
               TPProbeAccessType pat_old=probeAccessTypeService.findByAccessType(probeId, accessTypeName); // 数据库中是否存在该端口
                if (null != pat_old) {
                    access_type_json = JSON.toJSONString(pat_old);
                }
            }
            TPProbeAccessType pat = new TPProbeAccessType();
            pat.setId(UUIDKit.nextShortUUID());
            pat.setProbeId(probeId);
            pat.setAccessTypeName(accessTypeName);
            pat.setConnectStatus(object.getString("connect_status"));
            pat.setIsDefault( object.getShort("is_default"));
            pat.setMac(object.getString("mac"));
            pat.setIp(object.getString("ip"));
            pat.setMask(object.getString("mask"));
            pat.setDns(object.getString("dns"));
            pat.setGateway(object.getString("gateway"));
            pat.setLinkType(object.getString("link_type"));
            pat.setAccessTypeName( object.getString("no"));
            pat.setSpeed(object.getDoubleValue("speed"));// 端口协商速率，单位M
            try {
                if (StrUtil.isBlank(access_type_json)) { // 说明redis和数据库中都没有该端口信息，这时候需要插入端口
                    //TODO 入库存在问题，UNIQUE KEY `probe_id_access_type_name` (`probe_id`,`access_type_name`) 提示重复
                    System.out.println("insert ignore into t_p_probe_access_type ");
                    if(probeAccessTypeService.insert(pat)>0){
                        RedisModifyHelper.updateProbeAccess(probeId, accessTypeName, JSON.toJSONString(pat));
                        System.out.println("探针ID = " + probeId + " ，插入端口信息，" + JSON.toJSONString(pat));
                    }
                } else {
                    System.out.println("update");
                    JSONObject object2 = JSONObject.parseObject(access_type_json);
                    pat.setId(object2.getString("id"));
                    probeAccessTypeService.update(pat);
                    System.out.println("探针ID = " + probeId + " ，更新端口信息，" + JSON.toJSONString(pat));
                }
            } catch (Exception e) {
                System.out.println("探针ID = " + probeId + "，更新探针端口 = " + accessTypeName + " 信息出错"+ e.getMessage());
            }
        }
        System.out.println("redisMap=" + map);
        for (String redisKey : map.keySet()) { // --- 端口删除的情况为少数，所以直接用db删除了
            redisUtils.hdel(key, redisKey);
            probeAccessTypeService.deleteAccessTypeByName(probeId, redisKey);
        }
    }


    public static void saveTrafficInfo(String probeId, JSONArray array, long time, String trafficType) {
        if (array == null || array.size() <= 0) {
            return;
        }
        List<CPTraffic> records = new ArrayList<CPTraffic>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            CPTraffic record = new CPTraffic();
            record.setId( UUIDKit.nextShortUUID());
            record.setProbeId(probeId);
            record.setAccessTypeName(object.getString("access_type_name"));
            record.setIp(object.getString("ip"));
            record.setTimesheet(time);
            record.setUpAvg(object.getDouble("up_avg"));
            record.setUpMax(object.getDouble("up_max"));
            record.setDownAvg( object.getDouble("down_avg"));
            record.setDownMax(object.getDouble("down_max"));
            record.setTrafficType(trafficType);
            record.setMonthTime((DateUtil.beginOfMonth(new Date(time * 1000L)).toJdkDate()));
            record.setCreateTime(Long.valueOf(time));
            records.add(record);
        }
        try {
            trafficService.insertList(records);
            System.out.println("探针ID = " + probeId + "，插入流量历史记录到BigDataDb");
        } catch (Exception e) {
            System.out.println("探针ID = " + probeId + "，插入流量历史记录到BigDataDb出错"+e.getMessage());
        }
    }

    public static void saveStatusInfo(String probeId, JSONObject object, long time) {
        if (object == null || object.isEmpty()) {
            return;
        }
        CPStatus model = new CPStatus();
        model.setId(UUIDKit.nextShortUUID());
        model.setProbeId(probeId);
        model.setTimesheet(time);
        model.setCpuRate(object.getDouble("cpu_rate"));
        model.setCreateTime(time);
        model.setMonthTime((DateUtil.beginOfMonth(new Date(time * 1000L)).toJdkDate()));
        model.setRamRate( object.getDouble("ram_rate"));
        model.setRunTime(object.getString("run_time"));

        try {
            pStatusService.insert(model);
            System.out.println("探针ID = " + probeId + "，插入探针状态信息到BigDataDb");
        } catch (Exception e) {
            System.out.println("探针ID = " + probeId + "，插入探针状态信息到BigDataDb出错"+ e.getMessage());
        }
    }

    public static void savePonInfo(String probeId, String pc, JSONObject pon_info, long time) {
        if (pon_info == null || pon_info.isEmpty()) {
            return;
        }
        CPPon model = new CPPon();
        model.setId( UUIDKit.nextShortUUID());
        model.setCreateTime(time);
        model.setCurrent( pon_info.getDouble("current"));
        model.setMonthTime((DateUtil.beginOfMonth(new Date(time * 1000L)).toJdkDate()));
        model.setProbeId(probeId);
        Double rx_power = SmartGateWayUtil.parsePower(pc, pon_info.getDouble("rx_power"));
        model.setRxPower(rx_power);
        model.setTimesheet(time);
        model.setTemperature(pon_info.getDouble("temperature"));
        model.setTxPower(pon_info.getDouble("tx_power"));
        model.setVoltage(pon_info.getDouble("voltage"));

        try {
            pPonService.insert(model);
            System.out.println("探针ID = " + probeId + "，插入探针PON口信息到BigDataDb");
        } catch (Exception e) {
            System.out.println("探针ID = " + probeId + "，插入探针PON口信息到BigDataDb出错"+ e.getMessage());
        }
    }


}
