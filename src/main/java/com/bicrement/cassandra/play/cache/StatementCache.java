package com.bicrement.cassandra.play.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.datastax.driver.core.PreparedStatement;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * in-memory cache of preparedStatement
 *
 * @author Zhuochun
 *
 */
public class StatementCache {

    private static final Cache<String, PreparedStatement> cache = CacheBuilder.newBuilder().maximumSize(100).build();

    public static PreparedStatement get(String key) {
        return cache.getIfPresent(key);
    }

    public static PreparedStatement getOrCompute(String key, Callable<? extends PreparedStatement> statementLoader)
            throws ExecutionException {
        return cache.get(key, statementLoader);
    }

    public static void put(String key, PreparedStatement statement) {
        cache.put(key, statement);
    }

}
