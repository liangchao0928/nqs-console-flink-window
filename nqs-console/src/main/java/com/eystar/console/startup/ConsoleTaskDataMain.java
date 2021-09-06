package com.eystar.console.startup;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.eystar.console.env.BaseFlink;
import com.eystar.console.handler.message.DataMessage;
import com.eystar.console.handler.parser.ParserContext;
import com.eystar.console.sink.TaskDataClickHouseSink;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;


public class ConsoleTaskDataMain extends BaseFlink {

    public static void main(String[] args) throws Exception {
        ConsoleTaskDataMain topo = new ConsoleTaskDataMain();
        topo.run(ParameterTool.fromArgs(args));
    }


    @Override
    public String getJobName() {
        return "ConsoleTaskDataMain";
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

        ParserContext.init();

        DataStream<String> stream = getKafkaSpout("data_upload");

        DataStream<DataMessage> dataMessageStream = stream.flatMap(new FlatMapFunction<String, DataMessage>() {
            private static final long serialVersionUID = -6986783354031993339L;
            @Override
            public void flatMap(String msg, Collector<DataMessage> out) throws Exception {
                try {
                    if (StrUtil.isBlank(msg))
                        return;
                    JSONArray array = JSONArray.parseArray(msg);
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        DataMessage message = new DataMessage(object);
                        if (message.isBadMsg()) {
                            continue;
                        }
                        out.collect(message);
                    }
                } catch (Exception e) {
                    System.out.println("解析探针上报数据出错，本次上报的信息为 = \r\n" + msg+ e.getMessage());
                }
            }
        });

        dataMessageStream.addSink(new TaskDataClickHouseSink());
    }

}
