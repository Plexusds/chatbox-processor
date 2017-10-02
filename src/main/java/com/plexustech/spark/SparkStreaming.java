package com.plexustech.spark;

import com.plexustech.hbase.HbaseClient;
import com.plexustech.hbase.HbaseTubeStatusDAO;
import static com.plexustech.parser.TubeLineStatusParser.*;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 *
 */
public class SparkStreaming {

    final static Logger LOGGER = Logger.getLogger(SparkStreaming.class);

    private String[] topics = null;
    private String bootstrapserver = null;
    private SparkConf sparkConf = null;
    private final int SECONDS = 5;
    private boolean localExecution;
    private boolean fromBeginning;
    private String topic;


    public void setLocalExecution(boolean localExecution){
        this.localExecution = localExecution;
    }
    public void setFromBeginning(boolean fromBeginning){ this.fromBeginning = fromBeginning; }
    public void setTopic(String topic){ this.topic = topic; }


    private void setLocalExecution(){
        this.sparkConf = new SparkConf()
                .setAppName("ChatboxProcessor")
                .setMaster("local[*]");
        topics = new String[]{topic};
        bootstrapserver = "kafka:9092";
    }

    private void setHortonworksExecution(){
        this.sparkConf = new SparkConf()
                .setAppName("ChatboxProcessor");
        topics = new String[]{topic};
        bootstrapserver = "hortonworks21:6667";
    }

    public void executeSpark() throws InterruptedException {
        if(localExecution){
            setLocalExecution();
        } else{
            setHortonworksExecution();
        }

        createTubeTableIfNotExists(localExecution);

        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(SECONDS));
        readSparkFromKafka(jssc);

        jssc.start();
        jssc.awaitTermination();
    }

    private static Connection getHBaseConnection(boolean localExecution) throws IOException {
        return ConnectionFactory.createConnection(localExecution ? HbaseClient.getTestConnection()
                : HbaseClient.getUnsecureConnection());
    }

    private void createTubeTableIfNotExists(boolean localExecution) {
        try(Connection connection = getHBaseConnection(localExecution)) {
            HbaseTubeStatusDAO dao = new HbaseTubeStatusDAO(connection);
            if(localExecution)
                dao.createTableIfNotExists();
        } catch (IOException e) {
            LOGGER.error("Error creando la conexion con Hbase en rdd.foreachPartition", e);
        }
    }

    private Map<String, Object> getKafkaConsumerConfig(){
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapserver);
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "test_group");
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        if(fromBeginning)
            consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        else
            consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        return consumerConfig;
    }

    private Collection<String> getKafkaTopics(){
        return Arrays.asList(topics);
    }

    private void readSparkFromKafka(JavaStreamingContext jssc){
        Map<String, Object> consumerConfig = getKafkaConsumerConfig();

        final JavaInputDStream<ConsumerRecord<String, String>> directKafkaStream =
                KafkaUtils.createDirectStream(
                        jssc,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.<String, String>Subscribe(getKafkaTopics(), consumerConfig)
                );

        boolean localExecution = this.localExecution;

        directKafkaStream.foreachRDD(rdd -> {
            // Aqui estoy en master
            rdd.foreachPartition(partition -> {
                // Abrir conexion
                try(Connection connection = getHBaseConnection(localExecution)) {
                    HbaseTubeStatusDAO dao = new HbaseTubeStatusDAO(connection);
                    partition.forEachRemaining(record -> {
                    try{
                        dao.saveTubeStatus(parse(record.value()));
                    }catch(Throwable e){
                        LOGGER.error(String.format("Error salvando el mensaje %s", record.value()), e);
                    }});
                } catch (Throwable e) {
                    LOGGER.error("Error creando la conexion con Hbase en rdd.foreachPartition", e);
                }
            });
        });
    }
}
