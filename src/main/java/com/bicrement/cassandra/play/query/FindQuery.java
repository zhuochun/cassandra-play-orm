package com.bicrement.cassandra.play.query;

import java.util.ArrayList;
import java.util.List;

import com.bicrement.cassandra.play.Context;
import com.bicrement.cassandra.play.Entity;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ResultSet;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;

public class FindQuery<T> extends AbstractQuery {

    private final ImmutableMap<String, Object> kvMap;

    public FindQuery(Entity<T> entity, Context context, ImmutableMap<String, Object> kvMap) {
        super(entity, context);
        this.kvMap = kvMap;
    }

    @Override
    public String getQuery() {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT * FROM ");
        appendTableName(sb);

        sb.append(" WHERE ");
        Joiner.on("=? AND ").appendTo(sb, entity.getPartitionNames()).append("=?");
        if (entity.hasClusteringField()) {
            sb.append(" AND ");
            Joiner.on("=? AND ").appendTo(sb, entity.getClusteringNames()).append("=?");
        }

        sb.append(";");
        return sb.toString();
    }

    @Override
    public BoundStatement getCompleteStatement() {
        List<Object> values = new ArrayList<>();

        entity.getPartitionNames().forEach(name -> values.add(kvMap.get(name)));
        entity.getClusteringNames().forEach(name -> values.add(kvMap.get(name)));

        return getPreparedStatement().bind(values.toArray());
    }

    @SuppressWarnings("unchecked")
    @Override
    public T execute() {
        ResultSet rs = exec();
        return (T)context.getSerializer().toJavaObject(rs.one(), entity);
    }

}
