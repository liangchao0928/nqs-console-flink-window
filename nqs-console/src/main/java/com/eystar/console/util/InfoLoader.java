package com.eystar.console.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.common.util.Constants;
import com.eystar.common.util.RedisModifyHelper;
import com.eystar.console.handler.probe.ProbeAccessTypeHelper;
import com.eystar.gen.entity.TPProbe;
import com.eystar.gen.entity.TPProbeAccessType;
import com.eystar.gen.entity.TTTaskParam;
import com.eystar.gen.entity.TTTaskSrcDest;
import com.eystar.gen.service.ProbeService;
import com.eystar.gen.service.TaskParamService;
import com.eystar.gen.service.TaskSrcDesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;


public class InfoLoader {

	protected static final Logger logger = LoggerFactory.getLogger(InfoLoader.class);
	private static RedisUtils redisUtils;


	private static ProbeService probeService;
	private static TaskSrcDesService taskSrcDesService;
	private static TaskParamService taskParamService;
	public static void  init(RedisUtils ru, ProbeService ps){
		redisUtils=ru;
		probeService=ps;
	}


	public static void  taskInit(TaskSrcDesService tsd,TaskParamService tp){
		taskSrcDesService=tsd;
		taskParamService=tp;
	}


	public synchronized static JSONObject loadProbe(String probeId) {
		String key = Constants.REDIS_KEY_PROBE + probeId;
		String probeJson = redisUtils.get(key);
		if (StrUtil.isBlank(probeJson)) {
			TPProbe probe = probeService.findById(probeId);
			if (probe != null) {
				probeJson = JSON.toJSONString(probe);
				RedisModifyHelper.updateProbe(probeId,probeJson);
			} else {
				return new JSONObject();
			}
		}
		return JSONObject.parseObject(probeJson);
	}


	public synchronized static JSONObject loadProbeAccessType(String probeId) {
		String key = Constants.REDIS_KEY_ACCESS_TYPE + probeId;
		JSONObject object = new JSONObject();
		if (!redisUtils.exists(key)) {
			List<TPProbeAccessType> accessTypes = ProbeAccessTypeHelper.findByProbeId(probeId);
			for (TPProbeAccessType accessType : accessTypes) {
				object.put(accessType.getAccessTypeName(), JSONObject.parseObject(JSON.toJSONString(accessType)));
					RedisModifyHelper.updateProbeAccess(probeId, accessType.getAccessTypeName(), JSON.toJSONString(accessType));
					if (accessType.getIsDefault() == 1) {
						redisUtils.hset(key, "default_name", accessType.getAccessTypeName());
						RedisModifyHelper.updateProbeAccess(probeId, "default_name", accessType.getAccessTypeName());
					}
			}
			return object;
		} else {
			Set<String> fields = redisUtils.hkeys(key);
			for (String field : fields) {
				if (StrUtil.equals(field, "default_name")) {
					continue;
				}
				object.put(field, JSONObject.parseObject(redisUtils.hget(key, field)));
			}
			return object;
		}
	}

    public synchronized static JSONObject loadTaskSrcDest(String taskId) {
        String key = Constants.REDIS_KEY_TASK_SRC_DEST + taskId;
        String taskJson = redisUtils.get(key);
        if (StrUtil.isBlank(taskJson)) {
            TTTaskSrcDest taskSrcDest = taskSrcDesService.findById(taskId);
            if (taskSrcDest != null) {
				RedisModifyHelper.updateTaskSrcDest(taskId,JSON.toJSONString(taskSrcDest));
            } else {
                return new JSONObject();
            }
        }
        return JSONObject.parseObject(taskJson);
    }

	public synchronized static JSONObject loadTaskParam(String taskParamId) {
		String key = Constants.REDIS_KEY_TASK_PARAM + taskParamId;
		String task_param_json = redisUtils.get(key);
		if (StrUtil.isBlank(task_param_json)) {
			TTTaskParam tp = taskParamService.findById(taskParamId);
			if (tp != null) {
				task_param_json = JSON.toJSONString(tp) ;
					RedisModifyHelper.updateTaskParam(taskParamId,task_param_json);
			} else {
				return new JSONObject();
			}
		}
		return JSONObject.parseObject(task_param_json);
	}

	public synchronized static JSONObject loadAlarmTemplate(String alarmTemplateId) {
		String key = Constants.REDIS_KEY_ALARM_TEMPLATE + alarmTemplateId;
		String alarm_template_json = redisUtils.get(key);
//		if (StrUtil.isBlank(alarm_template_json)) {
//			AlarmTemplate template = AlarmTemplate.dao.findAlarmTemplateById(alarmTemplateId);
//			if (template != null) {
//				alarm_template_json = template.toJson();
//				if (OperationConfig.isReplace) {
//					RedisModifyHelper.updateAlarmTemplate(alarmTemplateId, alarm_template_json);
//				}
//			} else {
//				return new JSONObject();
//			}
//		}
//		return JSONObject.parseObject(alarm_template_json);
	return new JSONObject();
	}

}
