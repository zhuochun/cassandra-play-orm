package com.bicrement.cassandra.play.test;

import static com.bicrement.cassandra.play.clause.Where.eq;
import static com.bicrement.cassandra.play.clause.Where.in;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.bicrement.cassandra.play.Context;
import com.bicrement.cassandra.play.annotation.Column;
import com.bicrement.cassandra.play.annotation.Column.ColumnType;
import com.bicrement.cassandra.play.dao.EntityDao;

public class GetEntityTest {
	
	public class ExampleEntity {
		@Column(type = ColumnType.Partition)
		String k1;
		@Column(type = ColumnType.Clustering)
		String k2;
		@Column(type = ColumnType.Clustering)
		String k3;
		@Column(type = ColumnType.Normal)
		String value;
	}

    public EntityDao<ExampleEntity> dao = EntityDao.of(ExampleEntity.class, new Context());

    @Test
    public void testInit() {
        System.out.println(dao.createTable().ifNotExists().getQuery());
        System.out.println(dao.createTable().getQuery());
    }

    @Test
    public void testFind() {
        System.out.println(dao.find("key", "key").getQuery());
    }

    @Test
    public void testSelect() {
        System.out.println(dao.select().getQuery());
        System.out.println(dao.selectWhere(in("k3", "key3.0", "key3.1")).getQuery());
        System.out.println(dao.selectWhere(eq("k1", "key1"), eq("k2", "key2")).getQuery());
        System.out.println(dao.selectWhere(eq("k1", "key1"), eq("k2", "key2"),
                in("k3", "key3.0", "key3.1")).getQuery());
    }

    @Test
    public void testInsert() {
        System.out.println(dao.insert(new ExampleEntity()).getQuery());
        System.out.println(dao.insert(ImmutableMap.of("key", "k1", "val", "v1")).getQuery());
    }

    @Test
    public void testUpdate() {
        System.out.println(dao.update(new ExampleEntity()).getQuery());
        System.out.println(dao.update("val1", "val", "val2", "val", eq("key", "k1")).getQuery());
    }

    @Test
    public void testDelete() {
        System.out.println(dao.delete(new ExampleEntity()).getQuery());
        System.out.println(dao.delete(eq("key", "k1")).getQuery());
        System.out.println(dao.delete(in("key", "k1", "k2", "k3")).getQuery());
    }

    @Test
    public void testBatch() {
    	ExampleEntity dto = new ExampleEntity();
    	
        System.out.println(dao.batch(dao.insert(dto)).add(dao.insert(dto)).add(dao.delete(dto)).getQuery());
        System.out.println(dao.batch(dao.insert(dto), dao.insert(dto), dao.insert(dto), dao.insert(dto)).getQuery());
    }
}
