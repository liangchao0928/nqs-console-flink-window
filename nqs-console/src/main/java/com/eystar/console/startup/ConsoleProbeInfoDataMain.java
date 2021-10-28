package com.eystar.console.startup;


import com.eystar.console.env.BaseFlink;
import com.eystar.console.handler.message.GwInfoMessage;
import com.eystar.console.handler.probe.*;
import com.eystar.console.sink.*;
import com.eystar.console.time.TimeCountMessageTrigger;
import com.eystar.gen.entity.CPPinfo;
import com.eystar.gen.entity.CPPon;
import com.eystar.gen.entity.CPStatus;
import com.eystar.gen.entity.CPTraffic;
import com.xxl.conf.core.XxlConfClient;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.OutputTag;

import java.util.List;


public class ConsoleProbeInfoDataMain extends BaseFlink {

    public static void main(String[] args) throws Exception {
        ConsoleProbeInfoDataMain topo = new ConsoleProbeInfoDataMain();
        topo.run(ParameterTool.fromArgs(args));
    }



    @Override
    public String getJobName() {
        return "ConsoleProbeInfoDataMain";
    }

    @Override
    public String getConfigName() {
        return "spring-container.xml";
    }

    @Override
    public String getPropertiesName() {
        return "config.properties";
    }

    @Override
    public void createTopology(StreamExecutionEnvironment builder) {

        DataStream<String> stream = getKafkaSpout("gateway_info");

        DataStream<GwInfoMessage> streamMessage = stream.map(new MapFunction<String, GwInfoMessage>() {
            public GwInfoMessage map(String msg) throws Exception {
                GwInfoMessage message = new GwInfoMessage(msg);
                return message;
            }
        });

        // 筛选算子(去除不完整数据)
        DataStream<GwInfoMessage> streamInfo = streamMessage.filter(new FilterFunction<GwInfoMessage>() {
            public boolean filter(GwInfoMessage message) throws Exception {
                return !message.isBadMsg();
            }
        });


        // 4、获取需要注册的消息--注意OutputTag必须是内部类形式
        OutputTag<GwInfoMessage> accessTag = new OutputTag<GwInfoMessage>("accessTag") {
            private static final long serialVersionUID = 1L;
        };
        OutputTag<GwInfoMessage> trafficTag = new OutputTag<GwInfoMessage>("trafficTag") {
            private static final long serialVersionUID = 1L;
        };
        OutputTag<GwInfoMessage> statusTag = new OutputTag<GwInfoMessage>("statusTag") {
            private static final long serialVersionUID = 1L;
        };
        OutputTag<GwInfoMessage> ponTag = new OutputTag<GwInfoMessage>("ponTag") {
            private static final long serialVersionUID = 1L;
        };

        SingleOutputStreamOperator<GwInfoMessage> processStream = streamInfo.process(new ProbeInfoProcess(accessTag, trafficTag, statusTag, ponTag));

        DataStream<GwInfoMessage> accessTagStream = processStream.getSideOutput(accessTag);
        DataStream<GwInfoMessage> trafficTagStream = processStream.getSideOutput(trafficTag);
        DataStream<GwInfoMessage> statusTagStream = processStream.getSideOutput(statusTag);
        DataStream<GwInfoMessage> ponTagStream = processStream.getSideOutput(ponTag);

        // 端口信息执行入的mysql，不用时间窗口
        accessTagStream.addSink(new ProbeAccessTypeSink());

//---------------------------------------------------------------窗口模式------------------------------------
        // 探针信息sink
        DataStream<List<CPPinfo>> processListStream = processStream.keyBy(GwInfoMessage::isBadMsg).window(TumblingProcessingTimeWindows.of(Time.seconds(10))).trigger(new TimeCountMessageTrigger(1000)).process(new WindowProbeInfoProcessFunction());
        processListStream.addSink(new ProbeWindowSink());
        // 其他信息的sink
        DataStream<List<CPTraffic>> trafficTagListStream = trafficTagStream.keyBy(GwInfoMessage::isBadMsg).window(TumblingProcessingTimeWindows.of(Time.seconds(10))).trigger(new TimeCountMessageTrigger(1000)).process(new WindowProbeTrafficProcessFunction());
        trafficTagListStream.addSink(new ProbeTrafficWindowSink());

        DataStream<List<CPStatus>> statusTagListStream = statusTagStream.keyBy(GwInfoMessage::isBadMsg).window(TumblingProcessingTimeWindows.of(Time.seconds(10))).trigger(new TimeCountMessageTrigger(1000)).process(new WindowProbeStatusProcessFunction());
        statusTagListStream.addSink(new ProbeStatusWindowSink());

        DataStream<List<CPPon>> ponTagListStream = ponTagStream.keyBy(GwInfoMessage::isBadMsg).window(TumblingProcessingTimeWindows.of(Time.seconds(10))).trigger(new TimeCountMessageTrigger(1000)).process(new WindowProbePonProcessFunction());
        ponTagListStream.addSink(new ProbePonWindowSink());


//---------------------------------------------------------------队列模式------------------------------------
//        processStream.addSink(new ProbeClickHouseSink());
//        accessTagStream.addSink(new ProbeAccessTypeSink());
//        trafficTagStream.addSink(new ProbeTrafficSink());
//        statusTagStream.addSink(new ProbeStatusSink());
//        ponTagStream.addSink(new ProbePonSink());



    }

}
