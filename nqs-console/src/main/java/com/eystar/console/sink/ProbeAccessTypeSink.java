package com.eystar.console.sink;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.common.util.Constants;
import com.eystar.common.util.RedisModifyHelper;
import com.eystar.common.util.UUIDKit;
import com.eystar.console.env.BeanFactory;
import com.eystar.console.handler.message.GwInfoMessage;
import com.eystar.gen.entity.TPProbeAccessType;
import com.eystar.gen.service.ProbeAccessTypeService;
import com.eystar.gen.service.impl.ProbeAccessTypeServiceImpl;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public class ProbeAccessTypeSink  extends RichSinkFunction<GwInfoMessage> {

    private static final long serialVersionUID = 1L;
    private final static Logger logger = LoggerFactory.getLogger(ProbeAccessTypeSink.class);

    protected ApplicationContext beanFactory;

    private RedisUtils redisUtils;

    private ProbeAccessTypeService probeAccessTypeService;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
                .getExecutionConfig().getGlobalJobParameters();
        beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);
        redisUtils = beanFactory.getBean(RedisUtils.class);
        probeAccessTypeService = beanFactory.getBean(ProbeAccessTypeServiceImpl.class);

        RedisModifyHelper.init(redisUtils);
    }

    public void invoke(GwInfoMessage message, Context context) throws Exception {
        JSONObject gwInfoJson = message.getMsgJson();
        String probeId = message.getProbeId();
        JSONArray array = gwInfoJson.getJSONArray("access_type_info");
        String key = Constants.REDIS_KEY_ACCESS_TYPE + probeId;
        Map<String, String> map = redisUtils.hgetAll(key);
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            String accessTypeName = object.getString("access_type_name");
            String access_type_json = redisUtils.hget(key, accessTypeName);
            map.remove(accessTypeName);
            if (StrUtil.isBlank(access_type_json)) {
                TPProbeAccessType pat_old = probeAccessTypeService.findByAccessType(probeId, accessTypeName); // 数据库中是否存在该端口
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
//                    // 入库存在问题，UNIQUE KEY `probe_id_access_type_name` (`probe_id`,`access_type_name`) 提示重复
//                    System.out.println("insert ignore into t_p_probe_access_type ");
                    if(probeAccessTypeService.insert(pat)>0){
                        RedisModifyHelper.updateProbeAccess(probeId, accessTypeName, JSON.toJSONString(pat));
                        System.out.println("探针ID = " + probeId + " ，插入端口信息，" + JSON.toJSONString(pat));
                    }
                } else {
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
            // 不删除key为default_name的键
            if (redisKey.toString().equals("default_name")) {
                continue;
            }
            redisUtils.hdel(key, redisKey);
            probeAccessTypeService.deleteAccessTypeByName(probeId, redisKey);
        }



    }
}
