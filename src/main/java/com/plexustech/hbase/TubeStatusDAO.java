package com.plexustech.hbase;

import com.plexustech.pojos.TubeLineStatus;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * DAO to read/write status data
 */
public class TubeStatusDAO extends HbaseClient implements HbaseDAO<TubeLineStatus> {

    private static Logger LOGGER = Logger.getLogger(TubeStatusDAO.class);

    private static String TABLE_NAME = "tfl-tube";
    private static final String COL_FAMILY = "status";
    private static final String ID = "id";
    private static final String STATUS_SEVERITY_DESCRIPTION = "statusSeverityDescription";
    private static final String STATUS_SEVERITY = "statusSeverity";
    private static final String REASON = "reason";

    private Connection connection;

    public TubeStatusDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void createTable(){
        HbaseClient.createHbaseTable(connection, TABLE_NAME, Arrays.asList(COL_FAMILY));
    }

    @Override
    public void save(TubeLineStatus tubeLineStatus){

        TubeLineStatus.TubeStatus[] tubeStatusArray = tubeLineStatus.getTubeStatuses() ;
        for(TubeLineStatus.TubeStatus tubeStatus : tubeStatusArray){
            String tubeLineId = tubeStatus.getId();
            String lineName = tubeStatus.getName();
            String statusSeverityDescription = tubeStatus.getLineStatuses()[0].getStatusSeverityDescription();
            long statusId = tubeStatus.getLineStatuses()[0].getId();
            String reason = tubeStatus.getLineStatuses()[0].getReason();
            long statusSeverity = tubeStatus.getLineStatuses()[0].getStatusSeverity();

            insertRow(tubeLineId,
                    STATUS_SEVERITY_DESCRIPTION, statusSeverityDescription);
            insertRow(tubeLineId,
                    STATUS_SEVERITY, String.valueOf(statusSeverity));
            insertRow(tubeLineId,
                    REASON, reason);
            insertRow(tubeLineId,
                    ID, String.valueOf(statusId));
        }
    }

    private void insertRow(String rowKey, String colQualifier, String value){
        HbaseClient.putRow(connection, TABLE_NAME, COL_FAMILY, rowKey, colQualifier, value);
    }
}
