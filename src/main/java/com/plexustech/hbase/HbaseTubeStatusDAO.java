package com.plexustech.hbase;

import com.plexustech.model.TubeStatusVO;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.log4j.Logger;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * DAO to read/write status data
 */
public class HbaseTubeStatusDAO extends HbaseClient {

    private static Logger LOGGER = Logger.getLogger(HbaseTubeStatusDAO.class);

    private static String TABLE_NAME = "tfl-tube";
    private static final String COL_FAMILY = "status";
    private static final String ID = "id";
    private static final String STATUS_SEVERITY_DESCRIPTION = "statusSeverityDescription";
    private static final String STATUS_SEVERITY = "statusSeverity";
    private static final String REASON = "reason";

    private Connection connection;

    public HbaseTubeStatusDAO(Connection connection) {
        this.connection = connection;
    }

    public void createTableIfNotExists(){
        HbaseClient.createHbaseTable(connection, HbaseTubeStatusDAO.TABLE_NAME, Arrays.asList(COL_FAMILY));
    }

    public void saveTubeStatus(TubeStatusVO[] tubeStatusVO){

        LOGGER.debug("-------- trying to save in hbase ---------------");

        // put values

        for(int i = 0; i < tubeStatusVO.length; i++){
            HbaseClient.putRow(connection, TABLE_NAME, COL_FAMILY, tubeStatusVO[i].getTubeLineId(),
                    STATUS_SEVERITY_DESCRIPTION, tubeStatusVO[i].getStatusSeverityDescription());
            HbaseClient.putRow(connection, TABLE_NAME, COL_FAMILY, tubeStatusVO[i].getTubeLineId(),
                    STATUS_SEVERITY, String.valueOf(tubeStatusVO[i].getStatusSeverity()));
            HbaseClient.putRow(connection, TABLE_NAME, COL_FAMILY, tubeStatusVO[i].getTubeLineId(),
                    REASON, tubeStatusVO[i].getReason());
            HbaseClient.putRow(connection, TABLE_NAME, COL_FAMILY, tubeStatusVO[i].getTubeLineId(),
                    ID, String.valueOf(tubeStatusVO[i].getStatusId()));

            LOGGER.debug(MessageFormat.format("Row inserted in Hbase, tubeLine {0}", tubeStatusVO[i].getTubeLineId()));
        }

        LOGGER.debug("-------- After saving in hbase ---------------");
    }
}
