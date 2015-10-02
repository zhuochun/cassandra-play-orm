package com.bicrement.cassandra.play.dao;

import java.util.List;
import java.util.Map;

import com.bicrement.cassandra.play.Context;
import com.bicrement.cassandra.play.Entity;
import com.bicrement.cassandra.play.clause.Clause;
import com.bicrement.cassandra.play.clause.Clause.EqualityClause;
import com.bicrement.cassandra.play.query.CreateTableQuery;
import com.bicrement.cassandra.play.query.DeleteQuery;
import com.bicrement.cassandra.play.query.FindQuery;
import com.bicrement.cassandra.play.query.InsertQuery;
import com.bicrement.cassandra.play.query.SelectQuery;
import com.bicrement.cassandra.play.query.UpdateQuery;
import com.datastax.driver.core.ConsistencyLevel;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/**
 * dao = EntityDao.of(KeyValueDto.class);
 *
 * dao.getDto("key", "key1").sync();
 *
 * dao.insert(new KeyValueDto("key", "val")).sync();
 *
 * @author Zhuochun
 *
 * @param <T>
 */
public class EntityDao<T> extends AbstractDao<T> {

    public static <T1> EntityDao<T1> of(Class<T1> dtoClass) {
        return new EntityDao<>(Entity.of(dtoClass), new Context());
    }

    public static <T1> EntityDao<T1> of(Entity<T1> entity) {
        return new EntityDao<>(entity, new Context());
    }

    EntityDao(Entity<T> entity, Context context) {
        super(entity, context);
    }

    /*
     * ==================================================
     *
     * Context APIs
     */

    public EntityDao<T> withConsistency(ConsistencyLevel consistency) {
        return new EntityDao<>(entity, context.withConsistency(consistency));
    }

    public EntityDao<T> withTracing(boolean isEnable) {
        return new EntityDao<>(entity, context.withTracing(isEnable));
    }

    /*
     * ==================================================
     *
     * Find APIs
     */

    public FindQuery<T> find(String k1, Object v1) {
        return new FindQuery<T>(entity, context, ImmutableMap.of(k1, v1));
    }

    public FindQuery<T> find(String k1, Object v1, String k2, Object v2) {
        return new FindQuery<T>(entity, context, ImmutableMap.of(k1, v1, k2, v2));
    }

    public FindQuery<T> find(String k1, Object v1, String k2, Object v2, String k3, Object v3) {
        return new FindQuery<T>(entity, context, ImmutableMap.of(k1, v1, k2, v2, k3, v3));
    }

    public FindQuery<T> find(Map<String, Object> kvMap) {
        return new FindQuery<T>(entity, context, ImmutableMap.copyOf(kvMap));
    }

    /*
     * ==================================================
     *
     * Select APIs
     */

    public SelectQuery<T> where() {
        return new SelectQuery<T>(entity, context, ImmutableList.of());
    }

    public SelectQuery<T> where(EqualityClause clause, Clause... clauses) {
        return new SelectQuery<T>(entity, context, clauseList(clause, clauses));
    }

    public SelectQuery<T> where(List<Clause> clauses) {
        return new SelectQuery<T>(entity, context, ImmutableList.copyOf(clauses));
    }

    /*
     * ==================================================
     *
     * Insert APIs
     */

    public InsertQuery<T> insert(T dto) {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();

        appendFieldMap(builder, entity.getPartitionFields(), dto);
        appendFieldMap(builder, entity.getClusteringFields(), dto);
        appendFieldMap(builder, entity.getNormalFields(), dto);

        return new InsertQuery<T>(entity, context, builder.build());
    }

    public InsertQuery<T> insert(Map<String, Object> columns) {
        return new InsertQuery<T>(entity, context, ImmutableMap.copyOf(columns));
    }

    /*
     * ==================================================
     *
     * Update APIs
     */

    public UpdateQuery update(T dto) {
        ImmutableMap.Builder<String, Object> columns = ImmutableMap.builder();
        appendFieldMap(columns, entity.getNormalFields(), dto);

        ImmutableList.Builder<Clause> clauses = ImmutableList.<Clause> builder();
        appendFieldClause(clauses, entity.getPartitionFields(), dto);
        appendFieldClause(clauses, entity.getClusteringFields(), dto);

        return new UpdateQuery(entity, context, columns.build(), clauses.build());
    }

    public UpdateQuery update(String k1, Object v1, EqualityClause clause, EqualityClause... clauses) {
        return new UpdateQuery(entity, context, ImmutableMap.of(k1, v1), clauseList(clause, clauses));
    }

    public UpdateQuery update(String k1, Object v1, List<EqualityClause> clauses) {
        return new UpdateQuery(entity, context, ImmutableMap.of(k1, v1), ImmutableList.copyOf(clauses));
    }

    public UpdateQuery update(String k1, Object v1, String k2, Object v2,
            EqualityClause clause, EqualityClause... clauses) {
        return new UpdateQuery(entity, context, ImmutableMap.of(k1, v1, k2, v2), clauseList(clause, clauses));
    }

    public UpdateQuery update(String k1, Object v1, String k2, Object v2, List<EqualityClause> clauses) {
        return new UpdateQuery(entity, context, ImmutableMap.of(k1, v1, k2, v2), ImmutableList.copyOf(clauses));
    }

    public UpdateQuery update(String k1, Object v1, String k2, Object v2, String k3, Object v3,
            EqualityClause clause, EqualityClause... clauses) {
        return new UpdateQuery(entity, context, ImmutableMap.of(k1, v1, k2, v2, k3, v3), clauseList(clause, clauses));
    }

    public UpdateQuery update(String k1, Object v1, String k2, Object v2, String k3, Object v3,
            List<EqualityClause> clauses) {
        return new UpdateQuery(entity, context, ImmutableMap.of(k1, v1, k2, v2, k3, v3), ImmutableList.copyOf(clauses));
    }

    public UpdateQuery update(Map<String, Object> columns, EqualityClause clause, EqualityClause... clauses) {
        return new UpdateQuery(entity, context, ImmutableMap.copyOf(columns), clauseList(clause, clauses));
    }

    public UpdateQuery update(Map<String, Object> columns, List<EqualityClause> clauses) {
        return new UpdateQuery(entity, context, ImmutableMap.copyOf(columns), ImmutableList.copyOf(clauses));
    }

    /*
     * ==================================================
     *
     * Delete APIs
     */

    public DeleteQuery delete(T dto) {
        ImmutableList.Builder<Clause> builder = ImmutableList.<Clause> builder();

        appendFieldClause(builder, entity.getPartitionFields(), dto);
        appendFieldClause(builder, entity.getClusteringFields(), dto);

        return new DeleteQuery(entity, context, ImmutableList.of(), builder.build());
    }

    public DeleteQuery delete(EqualityClause clause, EqualityClause... clauses) {
        return new DeleteQuery(entity, context, ImmutableList.of(), clauseList(clause, clauses));
    }

    public DeleteQuery delete(List<EqualityClause> clauses) {
        return new DeleteQuery(entity, context, ImmutableList.of(), ImmutableList.copyOf(clauses));
    }

    public DeleteQuery delete(String c1, EqualityClause clause, EqualityClause... clauses) {
        return new DeleteQuery(entity, context, ImmutableList.of(c1), clauseList(clause, clauses));
    }

    public DeleteQuery delete(String c1, List<EqualityClause> clauses) {
        return new DeleteQuery(entity, context, ImmutableList.of(c1), ImmutableList.copyOf(clauses));
    }

    public DeleteQuery delete(String c1, String c2, EqualityClause clause, EqualityClause... clauses) {
        return new DeleteQuery(entity, context, ImmutableList.of(c1, c2), clauseList(clause, clauses));
    }

    public DeleteQuery delete(String c1, String c2, List<EqualityClause> clauses) {
        return new DeleteQuery(entity, context, ImmutableList.of(c1, c2), ImmutableList.copyOf(clauses));
    }

    public DeleteQuery delete(String c1, String c2, String c3, EqualityClause clause, EqualityClause... clauses) {
        return new DeleteQuery(entity, context, ImmutableList.of(c1, c2, c3), clauseList(clause, clauses));
    }

    public DeleteQuery delete(String c1, String c2, String c3, List<EqualityClause> clauses) {
        return new DeleteQuery(entity, context, ImmutableList.of(c1, c2, c3), ImmutableList.copyOf(clauses));
    }

    public DeleteQuery delete(List<String> columns, EqualityClause clause, EqualityClause... clauses) {
        return new DeleteQuery(entity, context, ImmutableList.copyOf(columns), clauseList(clause, clauses));
    }

    public DeleteQuery delete(List<String> columns, List<EqualityClause> clauses) {
        return new DeleteQuery(entity, context, ImmutableList.copyOf(columns), ImmutableList.copyOf(clauses));
    }

    /*
     * ==================================================
     *
     * Special APIs
     */

    public CreateTableQuery createTable() {
        return new CreateTableQuery(entity, context);
    }

    public void truncateTable() {
        // TODO
    }

    public void dropTable() {
        // TODO
    }

}
