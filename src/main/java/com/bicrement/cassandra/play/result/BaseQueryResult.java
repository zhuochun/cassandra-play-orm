package com.bicrement.cassandra.play.result;

import java.util.List;

import com.datastax.driver.core.ExecutionInfo;
import com.datastax.driver.core.ResultSet;
import com.bicrement.cassandra.play.QueryResult;

public class BaseQueryResult implements QueryResult {

    private final ResultSet rs;

    public BaseQueryResult(ResultSet rs) {
        this.rs = rs;
    }

    @Override
    public ExecutionInfo getExecutionInfo() {
        return rs.getExecutionInfo();
    }

    @Override
    public List<ExecutionInfo> getAllExecutionInfo() {
        return rs.getAllExecutionInfo();
    }

    @Override
    public boolean wasApplied() {
        return rs.wasApplied();
    }

}
