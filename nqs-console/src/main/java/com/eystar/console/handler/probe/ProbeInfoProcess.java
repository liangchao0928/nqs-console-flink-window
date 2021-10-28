package com.eystar.console.handler.probe;

import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.common.util.Constants;
import com.eystar.console.env.BeanFactory;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eystar.console.handler.message.GwInfoMessage;
import org.springframework.context.ApplicationContext;


/**
 * 判断探针是否存在，如果不存在则是注册，否则是心跳<br>
 */
public class ProbeInfoProcess extends ProcessFunction<GwInfoMessage, GwInfoMessage> {

	private static final long serialVersionUID = -4790600550007034325L;
	protected static final Logger logger = LoggerFactory.getLogger(ProbeInfoProcess.class);

	private RedisUtils redisUtils;

	protected ApplicationContext beanFactory;

	private OutputTag<GwInfoMessage> accessTag;
	private OutputTag<GwInfoMessage> trafficTag;
	private OutputTag<GwInfoMessage> statusTag;
	private OutputTag<GwInfoMessage> ponTag;

	public ProbeInfoProcess(OutputTag<GwInfoMessage> accessTag, OutputTag<GwInfoMessage> trafficTag, OutputTag<GwInfoMessage> statusTag, OutputTag<GwInfoMessage> ponTag) {
		this.accessTag = accessTag;
		this.trafficTag = trafficTag;
		this.statusTag = statusTag;
		this.ponTag = ponTag;
	}

	@Override
	public void open(Configuration parameters) throws Exception {
		ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
				.getExecutionConfig().getGlobalJobParameters();
		beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);
		redisUtils = beanFactory.getBean(RedisUtils.class);
	}

	@Override
	public void processElement(GwInfoMessage message, Context ctx, Collector<GwInfoMessage> out) throws Exception {
		JSONObject gwInfoJson = message.getMsgJson();
		String probeId = message.getProbeId();
		JSONArray access_type_info = gwInfoJson.getJSONArray("access_type_info");
		if (access_type_info == null || access_type_info.size() <= 0) {
			logger.debug("探针id = " + probeId + "，上报端口信息为空");
		} else if (redisUtils.exists(Constants.PROBE_ACCESS_AMEND + probeId)) {
			logger.debug("探针id = " + probeId + "，已经修改了端口信息，本次上报端口信息无法入口");
		} else {
			ctx.output(accessTag, message);
		}

		JSONArray traffic_info = gwInfoJson.getJSONArray("traffic_info");
		if (traffic_info != null && !traffic_info.isEmpty()) {
			ctx.output(trafficTag, message);
		}

		JSONObject status_info = gwInfoJson.getJSONObject("status_info"); // 网关状态信息
		if (status_info != null && !status_info.isEmpty()) {
			ctx.output(statusTag, message);
		}

		JSONObject sgw_info = gwInfoJson.getJSONObject("sgw_info");
		JSONObject pon_info = sgw_info.getJSONObject("pon_info");
		if (sgw_info != null && !(pon_info == null || pon_info.isEmpty())) {
			ctx.output(ponTag, message);
		}
		out.collect(message);
	}

	@Override
	public void close() throws Exception {
	}
}
