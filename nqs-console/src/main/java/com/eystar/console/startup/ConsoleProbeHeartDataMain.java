package com.eystar.console.startup;


import com.eystar.console.env.BaseFlink;
import com.eystar.console.handler.message.HeartBeatMessage;
import com.eystar.console.sink.HeartClickHouseSink;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


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
        streamHeart.addSink(new HeartClickHouseSink());

    }



}
