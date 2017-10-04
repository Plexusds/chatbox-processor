package com.plexustech.parser;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plexustech.pojos.ConsumerRecordValue;
import com.plexustech.pojos.CycleHire;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

/**
 *
 */
public class CycleHireParser implements Parser<CycleHire> {

    private final static Logger LOGGER = Logger.getLogger(CycleHireParser.class);
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public CycleHire parse(String content){
        LOGGER.debug("-----------  Parseando cicleHire ------------");
        ConsumerRecordValue consumerRecordValue = GSON.fromJson(content, ConsumerRecordValue.class);
        StringReader stringReader = new StringReader(consumerRecordValue.getPayload());
        CycleHire cycleHire = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CycleHire.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            cycleHire = (CycleHire) jaxbUnmarshaller.unmarshal(stringReader);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return cycleHire;


    }

    @Override
    public boolean checkParser(String content) {
        return content != null && content.contains("stations") && content.contains("nbBikes");
    }
}
