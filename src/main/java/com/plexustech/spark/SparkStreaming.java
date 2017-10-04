package com.plexustech.spark;

import com.plexustech.hbase.*;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 *
 */
public class SparkStreaming {

    private final static Logger LOGGER = Logger.getLogger(SparkStreaming.class);

    private Set<String> topicSet = null;
    private String bootstrapserver = "hortonworks21:6667";
    private SparkConf sparkConf = null;
    private int SECONDS = 120;
    private boolean fromBeginning;

    public SparkStreaming(){
        this.sparkConf = new SparkConf()
                .setAppName("ChatboxProcessor");
        HbaseClient.setConfig();
    }

    public void setTestExecution(){
        this.sparkConf.setMaster("local[*]");
        this.bootstrapserver = "kafka:9092";
        this.SECONDS = 5;
        HbaseClient.setTestConfig();
        createLocalTables();
    }

    public void setFromBeginning(boolean fromBeginning){ this.fromBeginning = fromBeginning; }
    public void setTopicSet(Set<String> topicSet){ this.topicSet = topicSet; }

    public void executeSpark(){
        //sparkConf.set("spark.streaming.concurrentJobs", "2");
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(SECONDS));
        readSparkFromKafka(jssc);

        jssc.start();
        try {
            jssc.awaitTermination();
        } catch (InterruptedException e) {
            LOGGER.error("Error when spark was awaiting termination ", e);
        }
    }

    private static Connection getHBaseConnection() throws IOException {
        return ConnectionFactory.createConnection(HbaseClient.getConfiguration());
    }

    private void createLocalTables() {
        try(Connection connection = getHBaseConnection()) {
            HbaseDAO dao = new TubeStatusDAO(connection);
            dao.createTable();
            dao = new CycleHireDAO(connection);
            dao.createTable();
        } catch (Throwable e) {
            LOGGER.error("Error creating local tables in Hbase", e);
        }
    }



    private void readSparkFromKafka(JavaStreamingContext jssc){
        Map<String, Object> consumerConfig = getKafkaConsumerConfig();

        final JavaInputDStream<ConsumerRecord<String, String>> directKafkaStream =
                KafkaUtils.createDirectStream(
                        jssc,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.<String, String>Subscribe(topicSet, consumerConfig)
                );

        directKafkaStream.foreachRDD(rdd -> {
            // Aqui estoy en master
            rdd.foreachPartition(partition -> {
                // Abrir conexion
                try(Connection connection = getHBaseConnection()) {
                    // Se crean los dao, se instancia por particion
                    DAOContainer daoContainer = new DAOContainer(connection);
                    partition.forEachRemaining(record ->
                        daoContainer.save(record.value())
                    );
                } catch (Throwable e) {
                    LOGGER.error("Error creando la conexion con Hbase en rdd.foreachPartition", e);
                }
            });
        });
    }

    private Map<String, Object> getKafkaConsumerConfig(){
        Map<String, Object> consumerConfig = new HashMap<>();
        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapserver);
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "test_group");
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, fromBeginning ? "earliest" : "latest");

        return consumerConfig;
    }




}
