package com.bicrement.cassandra.play.query;

import com.bicrement.cassandra.play.Context;
import com.bicrement.cassandra.play.Entity;
import com.bicrement.cassandra.play.Entity.FieldEntry;
import com.bicrement.cassandra.play.context.DataTypeMapper;
import com.datastax.driver.core.BoundStatement;
import com.google.common.base.Joiner;

public class CreateTableQuery extends AbstractQuery {

    private final DataTypeMapper mapper;
    private final boolean ifNotExists;

    public CreateTableQuery(Entity<?> entity, Context context) {
        this(entity, context, false);
    }

    public CreateTableQuery(Entity<?> entity, Context context, boolean ifNotExists) {
        super(entity, context);

        this.mapper = context.getDataTypeMapper();
        this.ifNotExists = ifNotExists;
    }

    public CreateTableQuery ifNotExists() {
        return new CreateTableQuery(entity, context, true);
    }

    @Override
    public String getQuery() {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE ");
        if (ifNotExists) {
            sb.append("IF NOT EXISTS ");
        }
        appendTableName(sb);

        sb.append(" (");
        entity.getPartitionFields().forEach(f -> appendColumn(sb, f));
        entity.getClusteringFields().forEach(f -> appendColumn(sb, f));

        sb.append(" PRIMARY KEY ((");
        Joiner.on(",").appendTo(sb, entity.getPartitionNames());
        if (entity.hasClusteringField()) {
            sb.append("),");
            Joiner.on(",").appendTo(sb, entity.getClusteringNames());
        }
        sb.append("));");

        return sb.toString();
    }

    private StringBuilder appendColumn(StringBuilder sb, FieldEntry field) {
        return sb.append(field.getName()).append(' ').append(mapper.asDataType(field.getType())).append(',');
    }

    @Override
    public BoundStatement getCompleteStatement() {
        return getPreparedStatement().bind();
    }

    public void sync() {
    }

    @Override
    public <T> T execute() {
        // TODO Auto-generated method stub
        return null;
    }

}
