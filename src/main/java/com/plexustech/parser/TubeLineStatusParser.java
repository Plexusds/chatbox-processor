package com.plexustech.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plexustech.model.TubeStatusVO;
import com.plexustech.pojos.ConsumerRecordValue;
import com.plexustech.pojos.TubeLineStatus;
import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 *
 */
public class TubeLineStatusParser implements Serializable{

    final static Logger LOGGER = Logger.getLogger(TubeLineStatusParser.class);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static TubeStatusVO[] parse(String recordValue){
        ConsumerRecordValue consumerRecordValue = GSON.fromJson(recordValue, ConsumerRecordValue.class);
        TubeLineStatus[] tubeLineStatusArray = GSON.fromJson(consumerRecordValue.getPayload(), TubeLineStatus[].class);

        TubeStatusVO[] out = new TubeStatusVO[tubeLineStatusArray.length];
        int pos = 0;
        for(TubeLineStatus tubeLineStatus : tubeLineStatusArray){
            out[pos] = new TubeStatusVO(
                    tubeLineStatus.getId(),
                    tubeLineStatus.getName(),
                    tubeLineStatus.getLineStatuses()[0].getStatusSeverityDescription(),
                    tubeLineStatus.getLineStatuses()[0].getId(),
                    tubeLineStatus.getLineStatuses()[0].getReason(),
                    tubeLineStatus.getLineStatuses()[0].getStatusSeverity());
            pos++;
        }

        return out;
    }

}
