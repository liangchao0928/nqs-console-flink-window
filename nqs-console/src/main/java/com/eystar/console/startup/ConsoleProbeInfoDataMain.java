package com.eystar.console.startup;


import com.eystar.console.env.BaseFlink;
import com.eystar.console.handler.message.GwInfoMessage;
import com.eystar.console.sink.ProbeClickHouseSink;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


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

        streamInfo.addSink(new ProbeClickHouseSink());

    }

}
