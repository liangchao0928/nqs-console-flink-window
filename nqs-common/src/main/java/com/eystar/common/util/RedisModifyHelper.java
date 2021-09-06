package com.eystar.common.util;

import cn.hutool.core.util.StrUtil;
import com.eystar.common.cache.redis.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RedisModifyHelper {
	
	protected static final Logger logger = LoggerFactory.getLogger(RedisModifyHelper.class);

	private static RedisUtils redisUtils;

	public  static void init(RedisUtils ru){
		redisUtils=ru;
	}



	public static void updateIpRegion(String ip,String json) {
		String key = Constants.REDIS_KEY_IP_REGION;
		if (StrUtil.isNotBlank(ip)) {
			Long ttl = redisUtils.pttl(key);
			Long left = Constants.REDIS_KEY_EXPIRE_ONE_MONTH * 1000L;
			if (ttl > 0)  {// 说明没有过期时间或者不存在key，-1表示永不过期，-2表示不存在key
				left = ttl;
			}
			logger.debug("更新ipregion信息key = " + key + " , left = " + left + " , ttl = " + ttl);
			if (left / 1000 == 0) { // 转成秒
				redisUtils.del(key);
			}else {
				redisUtils.hset(key, ip, json);
				redisUtils.pexpire(key, left);
			}
		}
	}

		public static void updateRegion(String field,String json) {
		String key = Constants.REDIS_KEY_REGION;
		if (StrUtil.isNotBlank(field)) {
			Long ttl = redisUtils.pttl(key);
			Long left = Constants.REDIS_KEY_EXPIRE_ONE_MONTH * 1000L;
			if (ttl > 0)  {// 说明没有过期时间或者不存在key，-1表示永不过期，-2表示不存在key
				left = ttl;
			}
			logger.debug("更新region信息key = " + key + " , left = " + left + " , ttl = " + ttl);
			if (left / 1000 == 0) { // 转成秒
				redisUtils.del(key);
			}else {
				redisUtils.hset(key, field, json);
				redisUtils.pexpire(key, left);
			}
		}
	}



	public static void updateProbe(String probeId, String json) {
		String key = Constants.REDIS_KEY_PROBE + probeId;
		Long ttl = redisUtils.pttl(key);
		Long left = Constants.REDIS_KEY_EXPIRE_ONE_HOUR * 1000L;
		if (ttl > 0) { // 说明没有过期时间或者不存在key，-1表示永不过期，-2表示不存在key
			left = ttl;
		}
		logger.debug("更新探针key = " + key + " , left = " + left + " , ttl = " + ttl);
		if (left / 1000 == 0) { // 转成秒
			redisUtils.del(key);
		}else {
			redisUtils.set(key, json);
			redisUtils.pexpire(key, left);
		}
	}


	public static void updateProbeAccess(String probeId,String accessTypeName,String json) {
		String key = Constants.REDIS_KEY_ACCESS_TYPE + probeId;
		Long ttl =redisUtils.pttl(key);
		Long left = Constants.REDIS_KEY_EXPIRE_TEN_MINUTES * 1000L;
		if (ttl > 0) { // 说明没有过期时间或者不存在key，-1表示永不过期，-2表示不存在key
			left = ttl;
		}
		logger.debug("更新探针端口类型key = " + key + " , left = " + left + " , ttl = " + ttl);
		if (left / 1000 == 0) { // 转成秒
			redisUtils.del(key);
		}else {
			redisUtils.hset(key, accessTypeName, json);
			redisUtils.pexpire(key, left);
		}
	}

	public static void updateTaskSrcDest(String taskId, String json) {
		String key = Constants.REDIS_KEY_TASK_SRC_DEST + taskId;
		Long ttl =redisUtils.pttl(key);
		Long left = Constants.REDIS_KEY_EXPIRE_TEN_MINUTES * 1000L;
		if (ttl > 0) { // 说明没有过期时间或者不存在key，-1表示永不过期，-2表示不存在key
			left = ttl;
		}
		logger.debug("更新任务key = " + key + " , left = " + left + " , ttl = " + ttl);
		if (left / 1000 == 0) { // 转成秒
			redisUtils.del(key);
		}else {
			redisUtils.set(key, json);
			redisUtils.pexpire(key, left);
		}
	}


	public static void updateTaskParam(String taskParamId, String json) {
		String key = Constants.REDIS_KEY_TASK_PARAM + taskParamId;
		Long ttl = redisUtils.pttl(key);
		Long left = Constants.REDIS_KEY_EXPIRE_ONE_HOUR * 1000L;
		if (ttl > 0) { // 说明没有过期时间或者不存在key，-1表示永不过期，-2表示不存在key
			left = ttl;
		}
		System.out.println("更新任务参数key = " + key + " , left = " + left + " , ttl = " + ttl);
		if (left / 1000 == 0) { // 转成秒
			redisUtils.del(key);
		}else {
			redisUtils.set(key, json);
			redisUtils.pexpire(key, left);
		}
	}



//
//	/**
//	 *
//	 * 修改Redis中参数的值<br>
//	 * 添加: Administrator - 2021年7月20日 上午12:28:22<br>
//	 * 修改: Administrator - 2021年7月20日 上午12:28:22<br>
//	 * @param taskParamId
//	 * @param json
//	 */

//
//	/**
//	 *
//	 * 修改Redis中参数的值<br>
//	 * 添加: 张俭 - 2020年10月9日 上午7:53:22<br>
//	 * 修改: 张俭 - 2020年10月9日 上午7:53:22<br>
//	 *
//	 * @param model
//	 */
//	public static void updateTaskParam(Record model) {
//		model.keep("id", "task_param_name", "task_type_name", "task_from", "interval", "alarm_template_id", "template_id");
//		updateTaskParam(model.getStr("id"), model.toJson());
//	}
//
//	/**
//	 *
//	 * 修改Redis中参数的值<br>
//	 * 添加: 张俭 - 2020年10月9日 上午7:53:22<br>
//	 * 修改: 张俭 - 2020年10月9日 上午7:53:22<br>
//	 *
//	 * @param model
//	 */
//	public static void updateTaskParam(Model<?> model) {
//		updateTaskParam(model.toRecord());
//	}
//
//	/**
//	 *
//	 * 修改redis中探针的值<br>
//	 * 添加: Administrator - 2021年7月22日 下午2:08:34<br>
//	 * 修改: Administrator - 2021年7月22日 下午2:08:34<br>
//	 * @param probeId
//	 * @param json
//	 */

//
//	/**
//	 *
//	 * 修改redis中探针的值<br>
//	 * 添加: 张俭 - 2020年10月9日 上午7:53:43<br>
//	 * 修改: 张俭 - 2020年10月9日 上午7:53:43<br>
//	 *
//	 * @param model
//	 */


//
//	/**
//	 *
//	 * 更新探针配置<br>
//	 * 添加: Administrator - 2021年7月26日 下午6:16:55<br>
//	 * 修改: Administrator - 2021年7月26日 下午6:16:55<br>
//	 * @param key
//	 */
//	public static void updateProbeConfig(String key,String json) {
//		Long ttl = Redis.use().pttl(key);
//		Long left = Constants.REDIS_KEY_EXPIRE_ONE_DAY * 1000L;
//		if (ttl > 0) { // 说明没有过期时间或者不存在key，-1表示永不过期，-2表示不存在key
//			left = ttl;
//		}
//		logger.debug("更新探针配置key = " + key + " , left = " + left + " , ttl = " + ttl);
//		if (left / 1000 == 0) { // 转成秒
//			Redis.use().del(key);
//		}else {
//			Redis.use().set(key, json);
//			Redis.use().pexpire(key, left);
//		}
//	}
//
//	/**
//	 *
//	 * 更新告警模板<br>
//	 * 添加: Administrator - 2021年7月26日 下午6:48:10<br>
//	 * 修改: Administrator - 2021年7月26日 下午6:48:10<br>
//	 * @param alarmTemplateId
//	 * @param json
//	 */
//	public static void updateAlarmTemplate(String alarmTemplateId,String json) {
//		String key = Constants.REDIS_KEY_ALARM_TEMPLATE + alarmTemplateId;
//		Long ttl = Redis.use().pttl(key);
//		Long left = Constants.REDIS_KEY_EXPIRE_ONE_HOUR * 1000L;
//		if (ttl > 0) { // 说明没有过期时间或者不存在key，-1表示永不过期，-2表示不存在key
//			left = ttl;
//		}
//		logger.debug("更新告警模板key = " + key + " , left = " + left + " , ttl = " + ttl);
//		if (left / 1000 == 0) { // 转成秒
//			Redis.use().del(key);
//		}else {
//			Redis.use().set(key, json);
//			Redis.use().pexpire(key, left);
//		}
//	}
//
//	/**
//	 *
//	 * 更新ip的信息到redis中<br>
//	 * 添加: Administrator - 2021年7月29日 下午3:46:47<br>
//	 * 修改: Administrator - 2021年7月29日 下午3:46:47<br>
//	 */
//	public static void updateIpRegion(String ip,String json) {
//		String key = Constants.REDIS_KEY_IP_REGION;
//		if (StrUtil.isNotBlank(ip)) {
//			Long ttl = Redis.use().pttl(key);
//			Long left = Constants.REDIS_KEY_EXPIRE_ONE_MONTH * 1000L;
//			if (ttl > 0)  {// 说明没有过期时间或者不存在key，-1表示永不过期，-2表示不存在key
//				left = ttl;
//			}
//			logger.debug("更新ipregion信息key = " + key + " , left = " + left + " , ttl = " + ttl);
//			if (left / 1000 == 0) { // 转成秒
//				Redis.use().del(key);
//			}else {
//				Redis.use().hset(key, ip, json);
//				Redis.use().pexpire(key, left);
//			}
//		}
//	}
//
//	/**
//	 *
//	 * 设置地域code或者region_path_name到redis中<br>
//	 * 添加: Administrator - 2021年8月5日 下午3:33:17<br>
//	 * 修改: Administrator - 2021年8月5日 下午3:33:17<br>
//	 * @param key
//	 * @param json
//	 */

}
