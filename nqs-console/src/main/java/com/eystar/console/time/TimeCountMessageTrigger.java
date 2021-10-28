package com.eystar.console.time;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.typeutils.base.LongSerializer;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import com.eystar.console.handler.message.Message;

/**
 * 
 * <pre>
 * 当时数据大于X条的时候，触发。
 * 
 * trigger接口有五个方法允许trigger对不同的事件做出反应：
	onElement():进入窗口的每个元素都会调用该方法。
	onEventTime():事件时间timer触发的时候被调用。
	onProcessingTime():处理时间timer触发的时候会被调用。
	onMerge():有状态的触发器相关，并在它们相应的窗口合并时合并两个触发器的状态，例如使用会话窗口。
	clear():该方法主要是执行窗口的删除操作。

	关于上述方法需要注意两点：
	1).前三方法决定着如何通过返回一个TriggerResult来操作输入事件。
	CONTINUE:什么都不做。
	FIRE:触发计算。
	PURE:清除窗口的元素。
	FIRE_AND_PURE:触发计算和清除窗口元素。
	2).这些方法中的任何一个都可用于为将来的操作注册处理或事件时间计时器
	内置和自定义触发器 
	Flink内部有一些内置的触发器:
	EventTimeTrigger：基于事件时间和watermark机制来对窗口进行触发计算。
	ProcessingTimeTrigger:基于处理时间触发。
	CountTrigger:窗口元素数超过预先给定的限制值的话会触发计算。
	PurgingTrigger作为其它trigger的参数，将其转化为一个purging触发器。
	 针对实际开发中的业务场景我们需要根据不同的业务去触发计算，这个时候我们就需要自己手动去定义触发器：
	原文链接：https://blog.csdn.net/qq_31866793/article/details/97917175
 * </pre>
 * 
 * @since 2021年10月8日 下午3:20:28
 * @author zhangjian
 * @version V1.0
 */
public class TimeCountMessageTrigger extends Trigger<Message, TimeWindow> {
	private static final long serialVersionUID = 5029003025558291319L;
	private int maxCount;
	private ReducingStateDescriptor<Long> countStateDescriptor = new ReducingStateDescriptor<Long>("counter", new Sum(), LongSerializer.INSTANCE);

	public TimeCountMessageTrigger(int maxCount) {
		this.maxCount = maxCount;
	}

	private TriggerResult fireAndPurge(TimeWindow window, TriggerContext ctx) throws Exception {
		clear(window, ctx);
		return TriggerResult.FIRE_AND_PURGE;
	}

	@Override
	public void clear(TimeWindow window, TriggerContext ctx) throws Exception {
		ctx.getPartitionedState(countStateDescriptor).clear();
	}

	@Override
	public TriggerResult onElement(Message element, long timestamp, TimeWindow window, TriggerContext ctx) throws Exception {
		ReducingState<Long> countState = ctx.getPartitionedState(countStateDescriptor);
		countState.add(1L);
		if (countState.get() >= maxCount) {
			return fireAndPurge(window, ctx);
		}
		// 当窗口关闭时，会触发一次onProcessingTime 这时候， 可能还没到trigger满足条件，所以需要手动触发一次
		if (timestamp >= window.getEnd()) {
			return fireAndPurge(window, ctx);
		} else {
			return TriggerResult.CONTINUE;
		}
	}

	@Override
	public TriggerResult onEventTime(long time, TimeWindow window, TriggerContext ctx) throws Exception {
		return TriggerResult.CONTINUE;
	}

	@Override
	public TriggerResult onProcessingTime(long time, TimeWindow window, TriggerContext ctx) throws Exception {
		if (time >= window.getEnd()) {
			return TriggerResult.CONTINUE;
		} else {
			return fireAndPurge(window, ctx);
		}
	}

	/**
	 * 计数方法
	 */
	class Sum implements ReduceFunction<Long> {
		private static final long serialVersionUID = 5892860477667271011L;

		@Override
		public Long reduce(Long value1, Long value2) throws Exception {
			return value1 + value2;
		}
	}
}