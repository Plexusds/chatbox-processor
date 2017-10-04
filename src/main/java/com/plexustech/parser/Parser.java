package com.plexustech.parser;

import com.plexustech.pojos.TubeLineStatus;

import java.io.Serializable;

/**
 *
 */
public interface Parser <T> extends Serializable{
    public T parse(String content);
    public boolean checkParser(String content);
}
