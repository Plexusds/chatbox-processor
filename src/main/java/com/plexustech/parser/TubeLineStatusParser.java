package com.plexustech.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plexustech.pojos.ConsumerRecordValue;
import com.plexustech.pojos.TubeLineStatus;
import org.apache.log4j.Logger;

/**
 *
 */
public class TubeLineStatusParser implements Parser<TubeLineStatus>{

    final static Logger LOGGER = Logger.getLogger(TubeLineStatusParser.class);

    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public TubeLineStatus parse(String content){
        ConsumerRecordValue consumerRecordValue = GSON.fromJson(content, ConsumerRecordValue.class);
        TubeLineStatus.TubeStatus[] tubeStatusesArray = GSON.fromJson(consumerRecordValue.getPayload(), TubeLineStatus.TubeStatus[].class);
        TubeLineStatus tubeLineStatus = new TubeLineStatus();
        tubeLineStatus.setTubeStatuses(tubeStatusesArray);
        return tubeLineStatus;
    }

    @Override
    public boolean checkParser(String content) {
        return content != null && content.contains("Tfl.Api.Presentation.Entities.Line");
    }

}
