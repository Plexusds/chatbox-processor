package com.plexustech;

import com.plexustech.spark.SparkStreaming;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class ChatboxProcessor {

    private final static Logger LOGGER = Logger.getLogger(ChatboxProcessor.class);

    private final String ARG_TOPIC = "topic";
    private final String ARG_FROM_BEGINNING = "fromBeginning";


    public static void main(String[] args){
       new ChatboxProcessor(args);
    }

    public ChatboxProcessor(String[] args){
        SparkStreaming sparkStreaming = new SparkStreaming();
        configSparkStreaming(args, sparkStreaming);

        // local test
        sparkStreaming.setTestExecution();

        sparkStreaming.executeSpark();
    }



    private void configSparkStreaming(String[] args, SparkStreaming sparkStreaming){
        Map<String, String> values = parseArgs(args);
        Set<String> kafkaTopicSet = new HashSet<>();
        kafkaTopicSet.add("tfl-tube-status");
        boolean fromBeginning = false;

        if(values.containsKey(ARG_TOPIC)){
            kafkaTopicSet = new HashSet<>();
            for( String topic : values.get(ARG_TOPIC).split(",")){
                kafkaTopicSet.add(topic.trim());
            }
        }

        if(values.containsKey(ARG_FROM_BEGINNING)){
            fromBeginning = Boolean.parseBoolean(values.get(ARG_FROM_BEGINNING));
        }

        LOGGER.info(MessageFormat.format("kafkaTopic {0} fromBeginning {1}", kafkaTopicSet.toString(), fromBeginning));

        sparkStreaming.setTopicSet(kafkaTopicSet);
        sparkStreaming.setFromBeginning(fromBeginning);
    }

    private Map<String, String> parseArgs(String[] args) {
        Map<String, String> result = new HashMap<>();

        Options options = new Options();
        options.addOption(ARG_TOPIC, true, "Kafka topic to read");
        options.addOption(ARG_FROM_BEGINNING, true, "Read Kafka topic from beginning");
        options.addOption("h", "help", false, "Print help information");

        CommandLineParser parser = new BasicParser();
        try {
            CommandLine cmdLine = parser.parse(options, args);
            if(cmdLine.hasOption("h")){
                new HelpFormatter().printHelp(ChatboxProcessor.class.getCanonicalName(), options );
            }

            if(cmdLine.hasOption(ARG_TOPIC)){
                result.put(ARG_TOPIC, cmdLine.getOptionValue(ARG_TOPIC));
            }

            if(cmdLine.hasOption(ARG_FROM_BEGINNING)){
                result.put(ARG_FROM_BEGINNING, cmdLine.getOptionValue(ARG_FROM_BEGINNING));
            }

        } catch (Exception e){
            LOGGER.error("Error parsing input args", e);
        }


        return result;
    }
}
