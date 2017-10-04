package com.plexustech.hbase;


import com.plexustech.pojos.CycleHire;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 *
 */
public class CycleHireDAO extends HbaseClient implements HbaseDAO<CycleHire> {

    private static final Logger LOGGER = Logger.getLogger(CycleHireDAO.class);

    private static String TABLE_NAME = "cycleHire";
    private static final String COL_FAMILY = "status";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TERMINAL_NAME = "terminalName";
    private static final String LAT = "lat";
    private static final String LONG = "long";
    private static final String INSTALLED = "installed";
    private static final String LOCKED = "locked";
    private static final String INSTALL_DATE = "installDate";
    private static final String REMOVAL_DATE = "removalDate";
    private static final String TEMPORARY = "temporary";
    private static final String NB_BIKES = "nbBikes";
    private static final String NB_EMPTY_DOCKS = "nbEmptyDocks";
    private static final String NB_DOCKS = "nbDocks";

    private Connection connection;

    public CycleHireDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public void createTable(){
        HbaseClient.createHbaseTable(connection, TABLE_NAME, Arrays.asList(COL_FAMILY));
    }

    @Override
    public void save(CycleHire cycleHire){
        if( cycleHire != null && cycleHire.getStations() != null ){
            cycleHire.getStations().stream().forEach( station -> {
                saveAllRows(station);
            });
        }
    }

    private void saveAllRows(CycleHire.Station station) {
        insertRow(String.valueOf(station.getId()), NAME, station.getName() );
        insertRow(String.valueOf(station.getId()), TERMINAL_NAME, station.getTerminalName());
        insertRow(String.valueOf(station.getId()), LAT, String.valueOf(station.getLat()));
        insertRow(String.valueOf(station.getId()), LONG, String.valueOf(station.getLon()));
        insertRow(String.valueOf(station.getId()), INSTALLED, String.valueOf(station.isInstalled()));
        insertRow(String.valueOf(station.getId()), LOCKED, String.valueOf(station.isLocked()));
        insertRow(String.valueOf(station.getId()), INSTALL_DATE, String.valueOf(station.getInstallDate()));
        insertRow(String.valueOf(station.getId()), REMOVAL_DATE, String.valueOf(station.getRemovalDate()));
        insertRow(String.valueOf(station.getId()), TEMPORARY, String.valueOf(station.isTemporary()));
        insertRow(String.valueOf(station.getId()), NB_BIKES, String.valueOf(station.getNbBikes()));
        insertRow(String.valueOf(station.getId()), NB_EMPTY_DOCKS, String.valueOf(station.getNbEmptyDocks()));
        insertRow(String.valueOf(station.getId()), NB_DOCKS, String.valueOf(station.getNbDocks()));
    }

    private void insertRow(String rowKey, String colQualifier, String value){
        HbaseClient.putRow(connection, TABLE_NAME, COL_FAMILY, rowKey, colQualifier, value);
    }

}
