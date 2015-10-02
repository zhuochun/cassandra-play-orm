package com.bicrement.cassandra.play.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.bicrement.cassandra.play.Entity;

public class EntityCache {

    private static final Map<Class<?>, Entity<?>> entityCache = new ConcurrentHashMap<>();

    public static Entity<?> getEntity(Class<?> dtoClass) {
        entityCache.computeIfAbsent(dtoClass, (clazz) -> Entity.of(clazz));
        return entityCache.get(dtoClass);
    }

}
