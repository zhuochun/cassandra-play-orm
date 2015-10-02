package com.bicrement.cassandra.play.context;

public interface Namelizer {

    public String getTableName(String packageName, String className);

    public String getColumnName(String fieldName);

    public String getFieldName(String columnName);

}
