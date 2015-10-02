package com.bicrement.cassandra.play.query;

import java.util.ArrayList;
import java.util.List;

import com.bicrement.cassandra.play.BatchableQuery;
import com.bicrement.cassandra.play.Context;
import com.bicrement.cassandra.play.Entity;
import com.bicrement.cassandra.play.clause.Clause;
import com.datastax.driver.core.BoundStatement;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class UpdateQuery extends AbstractQuery implements BatchableQuery {

    private final ImmutableMap<String, Object> columns;
    private final ImmutableList<Clause> clauses;

    public UpdateQuery(Entity<?> entity, Context context, ImmutableMap<String, Object> columns,
            ImmutableList<Clause> clauses) {
        super(entity, context);

        this.columns = columns;
        this.clauses = clauses;
    }

    public UpdateQuery where(Clause... moreClauses) {
        return new UpdateQuery(entity, context, columns, appendList(clauses, moreClauses));
    }

    @Override
    public String getQuery() {
        StringBuilder sb = new StringBuilder();

        sb.append("UPDATE ");
        appendTableName(sb);

        sb.append(" SET ");
        Joiner.on("=?,").appendTo(sb, columns.keySet()).append("=?");

        sb.append(" WHERE ");
        Joiner.on(" AND ").appendTo(sb, clauses);

        sb.append(";");
        return sb.toString();
    }

    @Override
    public BoundStatement getCompleteStatement() {
        List<Object> values = new ArrayList<>(entity.fieldSize());
        // TODO
        return getPreparedStatement().bind(values.toArray());
    }

    @Override
    public <T> T execute() {
        // TODO Auto-generated method stub
        return null;
    }

}
