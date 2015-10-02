package com.bicrement.cassandra.play.query;

import java.util.ArrayList;
import java.util.List;

import com.bicrement.cassandra.play.BatchableQuery;
import com.bicrement.cassandra.play.Context;
import com.bicrement.cassandra.play.Entity;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Row;
import com.google.common.collect.ImmutableMap;

public class InsertQuery<T> extends AbstractQuery implements BatchableQuery {

    private final ImmutableMap<String, Object> kvMap;
    private final boolean ifNotExists;

    public InsertQuery(Entity<T> entity, Context context, ImmutableMap<String, Object> kvMap) {
        this(entity, context, kvMap, false);
    }

    public InsertQuery(Entity<T> entity, Context context, ImmutableMap<String, Object> kvMap, boolean ifNotExists) {
        super(entity, context);
        this.kvMap = kvMap;
        this.ifNotExists = ifNotExists;
    }

    @SuppressWarnings("unchecked")
    public InsertQuery<T> ifNotExists() {
        return new InsertQuery<T>((Entity<T>)entity, context, kvMap, true);
    }

    @Override
    public String getQuery() {
        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO ");
        appendTableName(sb);

        sb.append(" (");
        entity.getPartitionNames().forEach(name -> sb.append(name).append(','));
        entity.getClusteringNames().forEach(name -> sb.append(name).append(','));
        entity.getNormalNames().forEach(name -> sb.append(name).append(','));
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");

        sb.append(" VALUES (");
        for (int i = 0; i < entity.fieldSize(); i++) {
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(");");

        return sb.toString();
    }

    @Override
    public BoundStatement getCompleteStatement() {
        List<Object> values = new ArrayList<>(entity.fieldSize());

        entity.getPartitionNames().forEach(name -> values.add(kvMap.get(name)));
        entity.getClusteringNames().forEach(name -> values.add(kvMap.get(name)));
        entity.getNormalNames().forEach(name -> values.add(kvMap.get(name)));

        return getPreparedStatement().bind(values.toArray());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean execute() {
        Row row = exec().one();
        return row == null || row.getBool(1);
    }

}
