package com.bicrement.cassandra.play.result;

import com.datastax.driver.core.ResultSetFuture;
import com.bicrement.cassandra.play.QueryResultFuture;

public class BaseQueryResultFuture implements QueryResultFuture {

    private final ResultSetFuture rs;

    public BaseQueryResultFuture(ResultSetFuture rs) {
        this.rs = rs;
    }

    // TODO fix the rest of APIs

}
