package com.plexustech.parser;

import com.plexustech.hbase.DAOContainer;
import com.plexustech.hbase.HbaseDAO;

/**
 *
 */
public class ApiParser {

    private DAOContainer daoContainer;
    private String value;

    private HbaseDAO hbaseDAO;
    private Parser parser;

    private static CycleHireParser cycleHireParser;
    private static TubeLineStatusParser tubeLineStatusParser;

    public ApiParser(DAOContainer daoContainer, String value){
        this.daoContainer = daoContainer;
        this.value = value;

        tubeLineStatusParser = new TubeLineStatusParser();
        cycleHireParser = new CycleHireParser();

        checkParser();
    }

    private void checkParser(){
        if( new TubeLineStatusParser().checkParser(value) ){
            hbaseDAO = daoContainer.getTubeStatusDAO();
            parser = tubeLineStatusParser;
        } else if( new CycleHireParser().checkParser(value) ){
            hbaseDAO = daoContainer.getCycleHireDAO();
            parser = cycleHireParser;
        }
    }

    public HbaseDAO getHbaseDAO() {
        return hbaseDAO;
    }

    public Parser getParser() {
        return parser;
    }
}
