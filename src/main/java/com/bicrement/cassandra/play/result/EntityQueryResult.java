package com.bicrement.cassandra.play.result;

import java.util.Iterator;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.bicrement.cassandra.play.Entity;

public class EntityQueryResult<T> extends BaseQueryResult {

    private final Entity<T> entity;

    public EntityQueryResult(Entity<T> entity, ResultSet rs) {
        super(rs);
        this.entity = entity;
    }

    /**
     * Get the first entity
     *
     * @throws
     * @return
     */
    public T one() {
        return null;
    }

    /**
     * Get all the entities in list
     *
     * @return
     */
    public List<T> list() {
        return null;
    }

    /**
     * Get an iterator that could iterate all entities
     *
     * @return
     */
    public Iterator<T> iterator() {
        return null;
    }

}
