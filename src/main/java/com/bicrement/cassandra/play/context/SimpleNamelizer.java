package com.bicrement.cassandra.play.context;

public class SimpleNamelizer implements Namelizer {

    @Override
    public String getTableName(String packageName, String className) {
        return className;
    }

    @Override
    public String getColumnName(String fieldName) {
        return fieldName;
    }

}
