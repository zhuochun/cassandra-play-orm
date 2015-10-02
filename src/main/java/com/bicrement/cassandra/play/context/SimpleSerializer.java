package com.bicrement.cassandra.play.context;


import com.bicrement.cassandra.play.Entity;
import com.datastax.driver.core.ResultSet;

public class SimpleSerializer implements Serializer {

    @Override
    public <T> T toJavaObject(ResultSet rs, Entity<T> entity) {
        return null; // TODO
    }

    @Override
    public Object toDbObject(Object javaObj) {
        return null; // TODO
    }

}
