package com.bicrement.cassandra.play.context;


import com.bicrement.cassandra.play.Entity;
import com.datastax.driver.core.Row;

public class SimpleSerializer implements Serializer {

    @Override
    public <T> T toJavaObject(Row row, Entity<T> entity) {
        return null; // TODO
    }

    @Override
    public Object toDataObject(Object javaObj) {
        return null; // TODO
    }

}
