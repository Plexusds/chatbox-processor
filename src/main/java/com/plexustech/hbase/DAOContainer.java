package com.plexustech.hbase;

import com.plexustech.parser.ApiParser;
import com.plexustech.parser.Parser;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 *
 */
public class DAOContainer implements Serializable {

    private final static Logger LOGGER = Logger.getLogger(DAOContainer.class);

    private TubeStatusDAO tubeStatusDAO;
    private CycleHireDAO cycleHireDAO;



    public DAOContainer(Connection connection){
        tubeStatusDAO = new TubeStatusDAO(connection);
        cycleHireDAO = new CycleHireDAO(connection);
    }

    public void save(String value){
        try{
            ApiParser apiParser = new ApiParser(this, value);
            HbaseDAO dao = apiParser.getHbaseDAO();
            Parser parser = apiParser.getParser();
            dao.save(parser.parse(value));
        }catch(Throwable e){
            LOGGER.error(String.format("Error salvando el mensaje %s ...", value.substring(0, 200)), e);
        }
    }

    public TubeStatusDAO getTubeStatusDAO() {
        return tubeStatusDAO;
    }

    public CycleHireDAO getCycleHireDAO() {
        return cycleHireDAO;
    }

}
