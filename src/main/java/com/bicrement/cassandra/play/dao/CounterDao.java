package com.bicrement.cassandra.play.dao;

import com.bicrement.cassandra.play.Context;
import com.bicrement.cassandra.play.Entity;

/**
 * dao = CounterDao.of(Counter.class);
 *
 * dao.getDto("key", "key1").sync();
 *
 * dao.update("key", "key1").by(1).sync();
 *
 * dao.reset("key", "key1").sync();
 *
 * @author Zhuochun
 *
 * @param <T>
 */
public class CounterDao<T> extends AbstractDao<T> {

    public static <T> CounterDao<T> of(Class<T> dtoClass) {
        return new CounterDao<T>(Entity.of(dtoClass), new Context());
    }

    CounterDao(Entity<T> entity, Context context) {
        super(entity, context);
    }

    /*
     * ==================================================
     *
     * Get APIs
     */

    /*
     * ==================================================
     *
     * Update APIs
     */

    /*
     * ==================================================
     *
     * Special APIs
     */

}
