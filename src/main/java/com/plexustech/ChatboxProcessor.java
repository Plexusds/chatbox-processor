package com.plexustech;

import com.plexustech.parser.TubeLineStatusParser;
import com.plexustech.spark.SparkStreaming;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 */
public class ChatboxProcessor {

    final static Logger LOGGER = Logger.getLogger(ChatboxProcessor.class);


    public static void main(String[] args) throws InterruptedException, JAXBException {
        SparkStreaming sparkStreaming = new SparkStreaming();

        String topic = "tfl-tube-status";
        if( args != null && args.length > 0 && args[0] != null){
            topic = args[0];
        }
        sparkStreaming.setTopic(topic);

        boolean fromBeginning = false;
        if( args != null && args.length > 1 && args[1] != null){
            fromBeginning = Boolean.parseBoolean(args[1]);
        }
        sparkStreaming.setFromBeginning(fromBeginning);

        sparkStreaming.setLocalExecution(true);
        sparkStreaming.executeSpark();

        /*File file = new File("/home/usuario/Escritorio/livecyclehireupdates.xml");
        JAXBContext jaxbContext = JAXBContext.newInstance(CycleHire.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        CycleHire cycleHire = (CycleHire) jaxbUnmarshaller.unmarshal(file);
        if( cycleHire != null && cycleHire.getStations() != null ){
            for( Object sta : cycleHire.getStations() ){
                CycleHire.Station station = (CycleHire.Station) sta;
                if(station != null && station.getName() != null){
                    LOGGER.info(" Station -> " + station.getName());
                }
            }
        }*/
    }

    private void readFromFile(){
        try {
            String fileData = new String(Files.readAllBytes(Paths
                    .get("/home/usuario/Escritorio/tmp_borrar/tfl-tube.json")));
            TubeLineStatusParser.parse(fileData);
        } catch (IOException e) {
            LOGGER.error("Leyendo archivo tubeLine.json");
        }
    }


}
