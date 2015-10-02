package com.bicrement.cassandra.play.query;

import com.bicrement.cassandra.play.BatchableQuery;
import com.bicrement.cassandra.play.Context;
import com.bicrement.cassandra.play.Entity;
import com.bicrement.cassandra.play.clause.Clause;
import com.datastax.driver.core.BoundStatement;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

public class DeleteQuery extends AbstractQuery implements BatchableQuery {

    private final ImmutableList<String> columns;
    private final ImmutableList<Clause> clauses;
    private final boolean ifExists;

    public DeleteQuery(Entity<?> entity, Context context,
            ImmutableList<String> columns, ImmutableList<Clause> clauses) {
        this(entity, context, columns, clauses, false);
    }

    public DeleteQuery(Entity<?> entity, Context context,
            ImmutableList<String> columns, ImmutableList<Clause> clauses,
            boolean ifExists) {
        super(entity, context);

        this.columns = columns;
        this.clauses = clauses;
        this.ifExists = ifExists;
    }

    public DeleteQuery column(String... moreColumns) {
        return new DeleteQuery(entity, context, appendList(columns, moreColumns), clauses, ifExists);
    }

    public DeleteQuery where(Clause... moreClauses) {
        return new DeleteQuery(entity, context, columns, appendList(clauses, moreClauses), ifExists);
    }

    public DeleteQuery ifExists() {
        return new DeleteQuery(entity, context, columns, clauses, true);
    }

    @Override
    public String getQuery() {
        StringBuilder sb = new StringBuilder();

        sb.append("DELETE ");
        if (!columns.isEmpty()) {
            Joiner.on(",").appendTo(sb, columns).append(" ");
        }
        sb.append("FROM ");
        appendTableName(sb);

        sb.append(" WHERE ");
        Joiner.on(" AND ").appendTo(sb, clauses);

        sb.append(";");
        return sb.toString();
    }

    @Override
    public BoundStatement getCompleteStatement() {
        return getPreparedStatement().bind(); // TODO
    }

    public void sync() {
    }

    @Override
    public <T> T execute() {
        // TODO Auto-generated method stub
        return null;
    }

}
