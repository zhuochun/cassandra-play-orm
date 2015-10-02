package com.bicrement.cassandra.play.context;

import com.bicrement.cassandra.play.Entity;
import com.datastax.driver.core.ResultSet;

public interface Serializer {

    public <T> T toJavaObject(ResultSet rs, Entity<T> entity);

    public Object toDbObject(Object javaObj);

}
