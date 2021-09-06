package com.eystar.console.env;

import com.eystar.console.util.PropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.contrib.streaming.state.RocksDBStateBackend;
import org.apache.flink.runtime.state.StateBackend;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.util.Preconditions;
import org.joda.time.DateTime;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public abstract class BaseFlink {

    protected Configuration config = new Configuration();

    protected BeanFactory beanFactory;

    protected String configFile;

    protected EnvironmentSettings settings;

    protected StreamExecutionEnvironment env;


    protected Properties properties;


    public void init(ParameterTool params) throws IOException {
        env = StreamExecutionEnvironment.getExecutionEnvironment();
        settings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();


        this.properties = PropertiesUtils.getProperties(getPropertiesName());
        String parallelism = properties.getProperty("parallelism");
        if (StringUtils.isNotBlank(parallelism)) {
            env.setParallelism(Integer.valueOf(parallelism));
        }

        String restartStrategy = params.get("restartStrategy");
        if ("fixedDelayRestart".equals(restartStrategy)) {
            env.setRestartStrategy(RestartStrategies.fixedDelayRestart(
                    3,
                    Time.of(60, TimeUnit.SECONDS)
            ));
        } else if ("noRestart".equals(restartStrategy)) {
            env.setRestartStrategy(RestartStrategies.noRestart());
        } else if ("fallBackRestart".equals(restartStrategy)) {
            env.setRestartStrategy(RestartStrategies.fallBackRestart());
        } else {
            env.setRestartStrategy(RestartStrategies.failureRateRestart(
                    3,
                    Time.of(5, TimeUnit.MINUTES),
                    Time.of(60, TimeUnit.SECONDS)
            ));
        }

        String isLocal = "local";//params.get("isLocal");
        if (StringUtils.isBlank(isLocal)) {
            String isIncremental = params.get("isIncremental");
            Preconditions.checkNotNull(isIncremental, "isIncremental is null");
            StateBackend stateBackend;
            String hadoopIp = properties.getProperty("hadoopIp");
            if ("isIncremental".equals(isIncremental)) {
                //如果本地调试，必须指定hdfs的端口信息，且要依赖hadoop包，如果集群执行，flink与hdfs在同一集群，那么可以不指定hdfs端口信息，也不用将hadoop打进jar包。
                stateBackend = new RocksDBStateBackend("hdfs:///home/intsmaze/flink/" + getJobName(), true);
                env.setStateBackend(stateBackend);
            } else if ("full".equals(isIncremental)) {
                stateBackend = new RocksDBStateBackend("hdfs://" + hadoopIp + "/home/intsmaze/flink/" + getJobName(), false);
                env.setStateBackend(stateBackend);
            }

            env.enableCheckpointing(5000);
            env.getCheckpointConfig().setCheckpointTimeout(30000);
            env.getCheckpointConfig().setMinPauseBetweenCheckpoints(5000);
            env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
            env.getCheckpointConfig().setFailOnCheckpointingErrors(false);
            env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        }
    }


    protected void setupConfig() throws IOException {

        this.configFile = getConfigName();
        this.beanFactory = new BeanFactory(configFile);

        config.setString(BeanFactory.SPRING_BEAN_FACTORY_XML, beanFactory.getXml());
        config.setString(BeanFactory.SPRING_BEAN_FACTORY_NAME, this.configFile);
        env.getConfig().setGlobalJobParameters(config);

    }


    protected DataStream<String> getKafkaSpout(String topic) {

        String kafkaServers = properties.getProperty("kafka.servers", "");
        String kafkaPort = properties.getProperty("kafka.port", "");
        String kafkaUsername = properties.getProperty("kafka.username", "");
        String kafkaPassword = properties.getProperty("kafka.password", "");
        String kafkaSecurityProtocol = properties.getProperty("kafka.security.protocol", "");
        String kafkaSaslMechanism = properties.getProperty("kafka.sasl.mechanism", "");

        String id = StringUtils.join(getJobName(), "-", topic);
        Properties props = new Properties();
        props.put("group.id", id);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("bootstrap.servers", kafkaServers+":"+kafkaPort);
        props.put("sasl.jaas.config", "org.apache.kafka.common.security.scram.ScramLoginModule required username=\""+kafkaUsername+"\" password=\""+kafkaPassword+"\";");
        props.put("security.protocol", kafkaSecurityProtocol);
        props.put("sasl.mechanism", kafkaSaslMechanism);
        FlinkKafkaConsumer<String> stringFlinkKafkaConsumer = new FlinkKafkaConsumer<String>(topic, new SimpleStringSchema(), props);
        return env.addSource(stringFlinkKafkaConsumer);
    }


    public abstract String getJobName();


    public abstract String getConfigName();


    public abstract String getPropertiesName();


    public abstract void createTopology(StreamExecutionEnvironment builder) throws IOException;


    public void run(ParameterTool params) throws Exception {
        init(params);
        setupConfig();
        createTopology(env);
        String topoName = StringUtils.join(getJobName(), "-", new DateTime().toString("yyyyMMdd-HHmmss"));
        env.execute(topoName);
    }

}
