package com.bicrement.cassandra.play.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.bicrement.cassandra.play.Context;
import com.bicrement.cassandra.play.Entity;
import com.bicrement.cassandra.play.Entity.FieldEntry;
import com.bicrement.cassandra.play.clause.Clause;
import com.bicrement.cassandra.play.clause.Clause.EqualityClause;
import com.bicrement.cassandra.play.clause.Where;
import com.bicrement.cassandra.play.query.BatchQuery;
import com.bicrement.cassandra.play.query.type.BatchableQuery;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * DAO is an API set to access DTOs
 *
 * @author Zhuochun
 *
 * @param <T>
 */
public abstract class AbstractDao<T> {

    protected final Entity<T> entity;
    protected final Context context;

    AbstractDao(Entity<T> entity, Context context) {
        this.entity = entity;
        this.context = context;
    }

    Entity<T> getEntity() {
        return entity;
    }

    public Context getContext() {
        return context;
    }

    /*
     * ==================================================
     *
     * Common APIs
     */

    public BatchQuery batch(BatchableQuery... queries) {
        return new BatchQuery(entity, context, ImmutableList.copyOf(queries));
    }

    public BatchQuery batch(List<BatchableQuery> queries) {
        return new BatchQuery(entity, context, ImmutableList.copyOf(queries));
    }

    /*
     * ==================================================
     *
     * Common Helpers
     */

    protected ImmutableList<Clause> clauseList(EqualityClause clause, Clause[] clauses) {
        ImmutableList.Builder<Clause> whereClauses = ImmutableList.<Clause> builder();

        whereClauses.add(clause);
        whereClauses.addAll(Arrays.asList(clauses));

        return whereClauses.build();
    }

    protected void appendFieldMap(ImmutableMap.Builder<String, Object> builder,
            List<FieldEntry> fields, Object dto) {
        fields.stream().filter(f -> Objects.nonNull(f.getValue(dto)))
                .forEach(f -> builder.put(f.getName(), f.getValue(dto)));
    }

    protected void appendFieldClause(ImmutableList.Builder<Clause> builder,
            List<FieldEntry> fields, Object dto) {
        fields.stream().map(f -> Where.eq(f.getName(), f.getValue(dto))).forEach(builder::add);
    }

}
