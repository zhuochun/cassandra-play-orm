package com.bicrement.cassandra.play.result;

import com.datastax.driver.core.ResultSetFuture;
import com.bicrement.cassandra.play.Entity;

public class EntityQueryResultFuture<T> extends BaseQueryResultFuture {

    private final Entity<T> entity;

    public EntityQueryResultFuture(Entity<T> entity, ResultSetFuture rsFuture) {
        super(rsFuture);
        this.entity = entity;
    }

}
