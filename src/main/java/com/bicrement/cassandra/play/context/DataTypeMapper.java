package com.bicrement.cassandra.play.context;

public interface DataTypeMapper {

    public Class<?> asJavaClass(String type);

    public String asDataType(Class<?> javaClass);

}
