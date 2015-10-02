package com.bicrement.cassandra.play.context;

/**
 * A simple namelizer that do not perform any conversion
 *
 * @author Zhuochun
 *
 */
public class SimpleNamelizer implements Namelizer {

    @Override
    public String getTableName(String packageName, String className) {
        return className;
    }

    @Override
    public String getColumnName(String fieldName) {
        return fieldName;
    }

    @Override
    public String getFieldName(String columnName) {
        return columnName;
    }

}
