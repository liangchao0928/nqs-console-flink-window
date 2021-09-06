package com.eystar.console.handler.probe;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.common.util.Constants;
import com.eystar.console.util.InfoLoader;
import com.eystar.gen.entity.TPProbeAccessType;
import com.eystar.gen.service.ProbeAccessTypeService;


import java.util.List;

public class ProbeAccessTypeHelper {

    private  static RedisUtils redisUtils;

    private static ProbeAccessTypeService probeAccessTypeService;

    public static  void init(RedisUtils ru,ProbeAccessTypeService pat){
        redisUtils=ru;
        probeAccessTypeService=pat;
    }

    public  static JSONObject  findDefaultAccessTypeFromRedis(String probeId) {
        InfoLoader.loadProbeAccessType(probeId); // 加载探针到端口到redis中
        String defaultName =redisUtils.hget(Constants.REDIS_KEY_ACCESS_TYPE + probeId, "default_name");
        if (StrUtil.isBlank(defaultName)) {
            return null;
        }
        String json = redisUtils.hget(Constants.REDIS_KEY_ACCESS_TYPE + probeId, defaultName);
        return JSONObject.parseObject(json);
    }

    public  static List<TPProbeAccessType> findByProbeId(String probeId){
       return  probeAccessTypeService.selectByProbeId(probeId);
    }
}
