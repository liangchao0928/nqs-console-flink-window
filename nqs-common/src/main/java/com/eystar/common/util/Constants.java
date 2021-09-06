package com.eystar.common.util;

/**
 * 常量类
 * 
 * @author auth
 *
 */
public class Constants {

	/**
	 * 默认平台归属地域
	 */
	public static int DEFAULT_REGION_CODE = 100000;
	/**
	 * 默认平台归属地域
	 */
	public static String DEFAULT_REGION_NAME = "集团";

	/**
	 * 10-配置更新
	 */
	public static final int SEG_CONFIG = 10;
	/**
	 * 20-探针重置
	 */
	public static final int SEG_RESET = 20;
	/**
	 * 30-任务更新
	 */
	public static final int SEG_TASK = 30;
	/**
	 * 40-网关http流量抓取规则
	 */
	public static final int http_tracffic_rule = 40;
	/**
	 * 50-网关黑名单地址列表
	 */
	public static final int url_blank_list = 50;
	/**
	 * 60-探针升级
	 */
	public static final int SEG_UPGRADE = 60;
	/**
	 * 1月
	 */
	public final static int REDIS_KEY_EXPIRE_ONE_MONTH = 2592000;
	/**
	 * 1天
	 */
	public final static int REDIS_KEY_EXPIRE_ONE_DAY = 86400;
	/**
	 * 1小时
	 */
	public final static int REDIS_KEY_EXPIRE_ONE_HOUR = 3600;
	/**
	 * 10分钟
	 */
	public final static int REDIS_KEY_EXPIRE_TEN_MINUTES = 600;
	/**
	 * 地区编码对应的区域全量任务
	 */
	public static final String REDIS_KEY_REGION_PROBE = "TASK_PARAM:REGION_PROBE:";
	/**
	 * 所有探针的任务
	 */
	public static final String REDIS_KEY_ALL_PROBE = "TASK_PARAM:ALL_PROBE";
	/**
	 * redis中存放的探针key
	 */
	public static final String REDIS_KEY_PROBE = "PROBE:";
	/**
	 * redis中存放的探针配置key
	 */
	public static final String REDIS_KEY_PROBE_CONFIG = "PROBE_CONFIG:";

	/**
	 * redis中存放的探针端口类型
	 */
	public final static String REDIS_KEY_ACCESS_TYPE = "PROBE_ACCESS_TYPE:";
	/**
	 * IP和地域对应关系
	 */
	public final static String REDIS_KEY_IP_REGION = "IP_REGION";
	/**
	 * redis中存放的任务参数
	 */
	public final static String REDIS_KEY_TASK_PARAM = "TASK_PARAM:";
	/**
	 * redis中存放的任务关系
	 */
	public final static String REDIS_KEY_TASK_SRC_DEST = "TASK_SRC_DEST:";
	/**
	 * redis中给webs下发指令
	 */
	public final static String REDIS_KEY_CMD = "pd:";
	/**
	 * redis中任务模板的指令
	 */
	public final static String REDIS_KEY_TPL = "tpl:";

	public static final String REDIS_KEY_TASK_MD5_TPIDS = "TASK_MD5_TPIDS:";
	/**
	 * redis中存放的地域key
	 */
	public static final String REDIS_KEY_REGION = "REGION";
	/**
	 * redis中存放的实时测试key
	 */
	public static final String REDIS_KEY_REALTEST = "realtest:";
	/**
	 * 限制ip
	 */
	public static final String REDIS_KEY_RESTRICT_IP = "RESTRICT_IP:";
	/**
	 * 限制用户
	 */
	public static final String REDIS_KEY_RESTRICT_USER = "RESTRICT_USER:";
	/**
	 * redis中存放的告警模板key
	 */
	public static final String REDIS_KEY_ALARM_TEMPLATE = "ALARM_TEMPLATE:";
	/**
	 * redis中存放的告警模板key
	 */
	public static final String REDIS_KEY_BRAS_IPS_DATA = "BRAS_IPS_Data";
	/**
	 * redis中存放修改的端口的探针
	 */
	public static final String PROBE_ACCESS_AMEND = "PROBE_ACCESS_AMEND:";
	/**
	 * redis中存放割接的实时测试结果
	 */
	public static final String CUT_REALRIME_TEST = "CUT_REALTIME_TEST:";
	/**
	 * 0-所有探针
	 */
	public static final int ALLOT_ALL = 0;
	/**
	 * 10-按组分配
	 */
	public static final int ALLOT_GROUP = 10;
	/**
	 * 11-按探针分配
	 */
	public static final int ALLOT_PROBE = 11;
	/**
	 * 12-所在Bras的IP分配
	 */
	public static final int ALLOT_BRASIP = 12;
	/**
	 * 13-外网IP分配
	 */
	public static final int ALLOT_NATIP = 13;
	/**
	 * 14-所在CP的IP分配
	 */
	public static final int ALLOT_CRIP = 14;
	/**
	 * 15-所在OLT的IP分配
	 */
	public static final int ALLOT_OLTIP = 15;
	/**
	 * 20-按省分配
	 */
	public static final int ALLOT_PROVINCE = 20;
	/**
	 * 30-地市分配
	 */
	public static final int ALLOT_CITY = 30;
	/**
	 * 40-区县分配
	 */
	public static final int ALLOT_DISTRICT = 40;
	/**
	 * 50-乡镇分配
	 */
	public static final int ALLOT_TOWN = 50;
}
