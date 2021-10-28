package com.eystar.console.startup;


import com.eystar.console.env.BaseFlink;
import com.eystar.console.handler.message.HeartBeatMessage;
import com.eystar.console.handler.probe.ProbeExistProcess;
import com.eystar.console.handler.probe.WindowHeartbeatProcessFunction;
import com.eystar.console.handler.probe.WindowRegisterProcessFunction;
import com.eystar.console.sink.ProbeHeartbeatSink;
import com.eystar.console.sink.ProbeRegistSink;
import com.eystar.console.time.TimeCountMessageTrigger;
import com.eystar.gen.entity.CPHeartbeat;
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


public class ConsoleProbeHeartDataMain extends BaseFlink {

    public static void main(String[] args) throws Exception {
        ConsoleProbeHeartDataMain topo = new ConsoleProbeHeartDataMain();
        topo.run(ParameterTool.fromArgs(args));
    }

    @Override
    public String getJobName() {
        return "ConsoleProbeHeartDataMain";
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

        DataStream<String> stream = getKafkaSpout("heartbeat_info");


        DataStream<HeartBeatMessage> streamMessage = stream.map(new MapFunction<String, HeartBeatMessage>() {
            public HeartBeatMessage map(String msg) throws Exception {
                HeartBeatMessage message = new HeartBeatMessage(msg);
                return message;
            }
        });

        DataStream<HeartBeatMessage> streamHeart = streamMessage.filter(new FilterFunction<HeartBeatMessage>() {
            public boolean filter(HeartBeatMessage message) throws Exception {
                return !message.isBadMsg();
            }
        });

        // 4、获取需要注册的消息--注意OutputTag必须是内部类形式
        OutputTag<HeartBeatMessage> registerTag = new OutputTag<HeartBeatMessage>("register") {
            private static final long serialVersionUID = 1L;
        };
        OutputTag<HeartBeatMessage> heartbeatTag = new OutputTag<HeartBeatMessage>("heartbeat") {
            private static final long serialVersionUID = 1L;
        };
        SingleOutputStreamOperator<HeartBeatMessage> processStream = streamHeart.process(new ProbeExistProcess(registerTag, heartbeatTag));

        DataStream<HeartBeatMessage> registerStream = processStream.getSideOutput(registerTag);

        DataStream<HeartBeatMessage> heartbeatStream = processStream.getSideOutput(heartbeatTag);


        DataStream<List<CPHeartbeat>> heartbeatListStream = heartbeatStream.keyBy(HeartBeatMessage::isBadMsg).window(TumblingProcessingTimeWindows.of(Time.seconds(10))).trigger(new TimeCountMessageTrigger(1000)).process(new WindowHeartbeatProcessFunction());

//        DataStream<List<CPHeartbeat>> registerListStream = registerStream.keyBy(HeartBeatMessage::isBadMsg).window(TumblingProcessingTimeWindows.of(Time.seconds(XxlConfClient.getInt("gw-bigdata.window.seconds")))).trigger(new TimeCountMessageTrigger(XxlConfClient.getInt("gw-bigdata.window.count"))).process(new WindowRegisterProcessFunction());
        DataStream<List<CPHeartbeat>> registerListStream = registerStream.keyBy(HeartBeatMessage::isBadMsg).window(TumblingProcessingTimeWindows.of(Time.seconds(10))).trigger(new TimeCountMessageTrigger(1000)).process(new WindowRegisterProcessFunction());

        registerListStream.addSink(new ProbeHeartbeatSink());
        heartbeatListStream.addSink(new ProbeHeartbeatSink());


    }



}
