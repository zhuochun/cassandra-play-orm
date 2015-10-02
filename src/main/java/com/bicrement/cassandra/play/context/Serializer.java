package com.bicrement.cassandra.play.context;

import com.bicrement.cassandra.play.Entity;
import com.datastax.driver.core.Row;

public interface Serializer {

    public <T> T toJavaObject(Row row, Entity<T> entity);

    public Object toDataObject(Object javaObj);

}
