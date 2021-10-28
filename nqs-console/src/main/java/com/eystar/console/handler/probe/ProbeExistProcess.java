package com.eystar.console.handler.probe;

import com.eystar.common.cache.redis.util.RedisUtils;
import com.eystar.console.env.BeanFactory;
import com.eystar.console.util.InfoLoader;
import com.eystar.gen.service.ProbeService;
import com.eystar.gen.service.impl.ProbeServiceImpl;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.eystar.console.handler.message.HeartBeatMessage;
import org.springframework.context.ApplicationContext;

/**
 * 判断探针是否存在，如果不存在则是注册，否则是心跳<br>
 */
public class ProbeExistProcess extends ProcessFunction<HeartBeatMessage, HeartBeatMessage> {


	private RedisUtils redisUtils;

	private ProbeService probeService;

	protected ApplicationContext beanFactory;

	private static final long serialVersionUID = -4790600550007034325L;
	protected static final Logger logger = LoggerFactory.getLogger(ProbeExistProcess.class);

	private OutputTag<HeartBeatMessage> registerTag;
	private OutputTag<HeartBeatMessage> heartbeatTag;

	public ProbeExistProcess(OutputTag<HeartBeatMessage> registerTag, OutputTag<HeartBeatMessage> heartbeatTag) {
		this.registerTag = registerTag;
		this.heartbeatTag = heartbeatTag;
	}

	@Override
	public void open(Configuration parameters) throws Exception {
		ExecutionConfig.GlobalJobParameters globalJobParameters = getRuntimeContext()
				.getExecutionConfig().getGlobalJobParameters();
		beanFactory = BeanFactory.getBeanFactory((Configuration) globalJobParameters);
		redisUtils = beanFactory.getBean(RedisUtils.class);
		probeService = beanFactory.getBean(ProbeServiceImpl.class);

		//初始化工具类数据
		InfoLoader.init(redisUtils,probeService);

	}

	@Override
	public void processElement(HeartBeatMessage message, Context ctx, Collector<HeartBeatMessage> out) throws Exception {
		String probeId = message.getProbeId();
		JSONObject probeInfo = InfoLoader.loadProbe(probeId);
		if (probeInfo == null || probeInfo.isEmpty()) {
			logger.debug("探针ID = " + probeId + "，开始第一次心跳，需要现进行注册");
			ctx.output(registerTag, message);
		} else {
			logger.debug("探针ID = " + probeId + "，开始心跳");
			message.setProbeJson(probeInfo);
			ctx.output(heartbeatTag, message);
		}
	}

	@Override
	public void close() throws Exception {
	}
}
