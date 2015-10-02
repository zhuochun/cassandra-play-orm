package com.bicrement.cassandra.play;

import java.lang.reflect.Field;
import java.util.List;

import com.bicrement.cassandra.play.annotation.Column;

/**
 * Entity is a representation of the DTO in database
 *
 * @author Zhuochun
 *
 * @param <T>
 */
public interface Entity<T> {

    public static <T1> Entity<T1> of(Class<T1> dtoClass) {
        return new EntityImpl<>(dtoClass);
    }

    public Object getName();

    public Class<T> getEntityClass();

    public List<FieldEntry> getPartitionFields();

    public List<String> getPartitionNames();

    public boolean hasClusteringField();

    public List<FieldEntry> getClusteringFields();

    public List<String> getClusteringNames();

    public List<FieldEntry> getNormalFields();

    public List<String> getNormalNames();

    public int fieldSize();

    public static class FieldEntry {
        private final Field field;
        private final Column column;

        public FieldEntry(Field field, Column column) {
            this.field = field;
            this.field.setAccessible(true);
            this.column = column;
        }

        public String getName() {
            return field.getName();
        }

        public Class<?> getType() {
            return field.getType();
        }

        public Object getValue(Object instance) {
            try {
                return field.get(instance);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public void setValue(Object instance, Object value) {
            try {
                field.set(instance, value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
