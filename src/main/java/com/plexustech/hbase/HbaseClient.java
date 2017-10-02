package com.plexustech.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

/**
 *
 */
public class HbaseClient implements Serializable {

    public static final Logger LOGGER = Logger.getLogger(HbaseClient.class);

    public static Configuration getTestConnection(){
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.zookeeper.quorum", "hbase");

        return conf;
    }

    public static Configuration getUnsecureConnection(){
        Configuration config = HBaseConfiguration.create();
        config.set("zookeeper.znode.parent", "/hbase-unsecure");
        return config;
    }


    public static void createHbaseTable(Connection connection, String tableName, List<String> colFamilies) {
        try(Admin admin = connection.getAdmin()) {

            TableName table1 = TableName.valueOf(tableName);
            if (!admin.tableExists(table1)) {
                HTableDescriptor desc = new HTableDescriptor(table1);
                colFamilies.stream().forEach(cf -> desc.addFamily(new HColumnDescriptor(cf)));
                admin.createTable(desc);
            }
        } catch (IOException e) {
            String msg = MessageFormat.format("Error creating tableName {0} colFamily {1}", tableName, colFamilies);
            LOGGER.error(msg, e);
        }
    }

    protected static void putRow(Connection connection, String tableName, String colFamName, String rowKey, String colQualifier,
                       String value) {
        if(colFamName != null && value != null){
            try{
                byte[] row1 = Bytes.toBytes(rowKey);
                Put p = new Put(row1);
                p.addImmutable(colFamName.getBytes(), colQualifier.getBytes(), Bytes.toBytes(value));
                TableName table1 = TableName.valueOf(tableName);
                Table table = connection.getTable(table1);
                table.put(p);
            } catch (IOException e) {
                String msg = MessageFormat.format("Error putting row in tableName {0}, colFamName {1}, rowKey {2}, colQualifier {3}, value {4}",
                        tableName, colFamName, rowKey, colQualifier, value);
                LOGGER.error(msg, e);
            }
        }
    }

    protected static Result getRow(Connection connection, String tableName, String colFamName, String rowKey, String colQualifier){
        Result result = null;
        try{
            byte[] row1 = Bytes.toBytes(rowKey);
            Get g = new Get(row1);
            g.addColumn(Bytes.toBytes(colFamName), Bytes.toBytes(colQualifier));
            TableName table1 = TableName.valueOf(tableName);
            Table table = connection.getTable(table1);
            result = table.get(g);
        } catch (IOException e) {
            String msg = MessageFormat.format("Error getting row in tableName {0}, colFamName {1}, rowKey {2}, colQualifier {3}",
                    tableName, colFamName, rowKey, colQualifier);
            LOGGER.error(msg, e);
        }
        return result;
    }

}
