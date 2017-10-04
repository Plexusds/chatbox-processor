package com.plexustech.hbase;

/**
 *
 */
public interface HbaseDAO<T> {

    public void createTable();
    public void save(T object);

}
