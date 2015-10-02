package com.bicrement.cassandra.play.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    public enum ColumnType {
        Partition, Clustering, Counter, Normal, Ignored
    }

    ColumnType type();

    int order() default 0;

}
