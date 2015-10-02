package com.bicrement.cassandra.play.query;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.bicrement.cassandra.play.Context;
import com.bicrement.cassandra.play.Entity;
import com.bicrement.cassandra.play.clause.Clause;
import com.datastax.driver.core.BoundStatement;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

public class SelectQuery<T> extends AbstractQuery {

    private final ImmutableList<Clause> clauses;

    public SelectQuery(Entity<T> entity, Context context, ImmutableList<Clause> clauses) {
        super(entity, context);
        this.clauses = clauses;
    }

    @SuppressWarnings("unchecked")
    public SelectQuery<T> where(Clause... moreClauses) {
        return new SelectQuery<>((Entity<T>)entity, context, appendList(clauses, moreClauses));
    }

    @Override
    public String getQuery() {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT * FROM ");
        appendTableName(sb);

        if (!clauses.isEmpty()) {
            sb.append(" WHERE ");
            Joiner.on(" AND ").appendTo(sb, clauses);
        }

        sb.append(";");
        return sb.toString();
    }

    @Override
    public BoundStatement getCompleteStatement() {
        List<Object> values = clauses.stream().flatMap(c -> Arrays.stream(c.getValues())).collect(Collectors.toList());
        return getPreparedStatement().bind(values.toArray());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> execute() {
        // TODO Auto-generated method stub
        return null;
    }

}
