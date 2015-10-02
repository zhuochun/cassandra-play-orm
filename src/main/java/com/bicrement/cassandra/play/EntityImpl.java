package com.bicrement.cassandra.play;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.bicrement.cassandra.play.annotation.Column;
import com.bicrement.cassandra.play.annotation.Column.ColumnType;

/**
 * Entity is a representation of DTO in database based on the annotations
 *
 * entityDao = Entity.of(Dto.class).getDao(context);
 *
 * @author Zhuochun
 *
 */
public class EntityImpl<T> implements Entity<T> {

    private final Class<T> dtoClass;
    private final String dtoName;
    private final String dtoPackage;

    private final List<FieldEntry> partitionFields;
    private final List<FieldEntry> clusteringFields;
    private final List<FieldEntry> normalFields;

    protected EntityImpl(Class<T> dtoClass) {
        // get table globals
        this.dtoClass = dtoClass;
        this.dtoName = dtoClass.getSimpleName();
        this.dtoPackage = dtoClass.getPackage().getName();

        // TODO use priority queue that sort based on column's order?
        List<FieldEntry> partitionList = new ArrayList<>();
        List<FieldEntry> clusteringList = new ArrayList<>();
        List<FieldEntry> normalList = new ArrayList<>();

        // TODO for simplification, now just parse annotated Column
        Field[] fields = dtoClass.getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(Column.class)) {
                continue;
            }

            Column column = field.getAnnotation(Column.class);

            if (column.type() == ColumnType.Partition) {
                partitionList.add(new FieldEntry(field, column));
            } else if (column.type() == ColumnType.Clustering) {
                clusteringList.add(new FieldEntry(field, column));
            } else if (column.type() == ColumnType.Normal) {
                normalList.add(new FieldEntry(field, column));
            } else if (column.type() == ColumnType.Counter) {
                normalList.add(new FieldEntry(field, column)); // TODO handle counter
            }
        }

        this.partitionFields = Collections.unmodifiableList(partitionList);
        this.clusteringFields = Collections.unmodifiableList(clusteringList);
        this.normalFields = Collections.unmodifiableList(normalList);
    }

    @Override
    public String getName() {
        return dtoName;
    }

    @Override
    public Class<T> getEntityClass() {
        return dtoClass;
    }

    @Override
    public List<FieldEntry> getPartitionFields() {
        return partitionFields;
    }

    @Override
    public List<String> getPartitionNames() {
        return partitionFields.stream().map(FieldEntry::getName).collect(Collectors.toList());
    }

    @Override
    public boolean hasClusteringField() {
        return !clusteringFields.isEmpty();
    }

    @Override
    public List<FieldEntry> getClusteringFields() {
        return clusteringFields;
    }

    @Override
    public List<String> getClusteringNames() {
        return clusteringFields.stream().map(FieldEntry::getName).collect(Collectors.toList());
    }

    @Override
    public List<FieldEntry> getNormalFields() {
        return normalFields;
    }

    @Override
    public List<String> getNormalNames() {
        return normalFields.stream().map(FieldEntry::getName).collect(Collectors.toList());
    }

    @Override
    public int fieldSize() {
        return partitionFields.size() + clusteringFields.size() + normalFields.size();
    }

}
