package com.bicrement.cassandra.play.query;

import com.bicrement.cassandra.play.BatchableQuery;
import com.bicrement.cassandra.play.Context;
import com.bicrement.cassandra.play.Entity;
import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.google.common.collect.ImmutableList;

public class BatchQuery extends AbstractQuery {

    private final boolean isUnlogged;
    private final ImmutableList<BatchableQuery> queries;

    public BatchQuery(Entity<?> entity, Context context, ImmutableList<BatchableQuery> queries) {
        this(entity, context, queries, false);
    }

    public BatchQuery(Entity<?> entity, Context context, ImmutableList<BatchableQuery> queries, boolean isUnlogged) {
        super(entity, context);

        this.queries = queries;
        this.isUnlogged = isUnlogged;
    }

    public BatchQuery add(BatchableQuery... moreQueries) {
        return new BatchQuery(entity, context, appendList(queries, moreQueries), isUnlogged);
    }

    public BatchQuery setUnlogged(boolean unlogged) {
        return new BatchQuery(entity, context, queries, true);
    }

    @Override
    public String getQuery() {
        StringBuilder sb = new StringBuilder();

        sb.append("BEGIN ");
        if (isUnlogged) {
            sb.append("UNLOGGED ");
        } // TODO is counter
        sb.append("BATCH ");

        queries.forEach(q -> sb.append(q.getQuery()));

        sb.append(" APPLY BATCH;");

        return sb.toString();
    }

    @Override
    public PreparedStatement getPreparedStatement() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Statement getCompleteStatement() {
        BatchStatement batch = new BatchStatement();
        queries.forEach(q -> batch.add(q.getCompleteStatement()));
        return batch;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean execute() {
        Row row = exec().one();
        return row == null || row.getBool(1);
    }

}
