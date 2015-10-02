package com.bicrement.cassandra.play.query;

import java.util.Arrays;

import com.bicrement.cassandra.play.BaseQuery;
import com.bicrement.cassandra.play.Context;
import com.bicrement.cassandra.play.Entity;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public abstract class AbstractQuery implements BaseQuery {

    protected Entity<?> entity;
    protected Context context;

    public AbstractQuery(Entity<?> entity, Context context) {
        this.entity = entity;
        this.context = context;
    }

    @Override
    public PreparedStatement getPreparedStatement() {
        return context.getSession().prepare(getQuery()); // TODO cache
    }

    ResultSet exec() {
        return context.getSession().execute(getCompleteStatement());
    }

    ResultSetFuture execAsync() {
        return context.getSession().executeAsync(getCompleteStatement());
    }

    /**
     * Execute the query statement
     *
     * @return
     */
    public abstract <T> T execute();

    protected StringBuilder appendTableName(StringBuilder sb) {
        return sb.append(context.getKeyspace()).append('.').append(entity.getName());
    }

    protected <T> ImmutableList<T> appendList(ImmutableList<T> oldList, T[] newItems) {
        Builder<T> builder = ImmutableList.builder();
        return builder.addAll(oldList).addAll(Arrays.asList(newItems)).build();
    }

}
