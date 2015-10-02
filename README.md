# Cassandra Play ORM

An ORM for Cassandra (partially completed).

## Basic Idea

Declare an entity class with annotations:

``` java
public class ExampleEntity {
  @Column(type = ColumnType.Partition)
  String k1;
  @Column(type = ColumnType.Clustering)
  String k2;
  @Column(type = ColumnType.Clustering)
  String k3;
  @Column(type = ColumnType.Normal)
  String value;

  public ExampleEntity(String key, String val) {
    String[] keys = key.split(":");
    k1 = keys[0]; k2 = keys[1]; k3 = keys[2]; value = val;
  }
}
```

Create a `DAO` to access the entity rows from Cassandra (statements are in CQL):

``` java
public EntityDao<ExampleEntity> dao = EntityDao.of(ExampleEntity.class, new Context());

dao.createTable().ifNotExists().execute();
// CREATE TABLE IF NOT EXISTS demo.ExampleEntity (k1 TEXT,k2 TEXT,k3 TEXT, PRIMARY KEY ((k1),k2,k3));

dao.insert(new ExampleEntity("1:2:3", "value")).execute();
// INSERT INTO demo.ExampleEntity (k1,k2,k3,value) VALUES (?,?,?,?);

dao.update(new ExampleEntity("1:2:3", "value")).execute();
// UPDATE demo.ExampleEntity SET value=? WHERE k1=? AND k2=? AND k3=?;
dao.update("value", "new_val", eq("k1", "1")).execute();
// UPDATE demo.ExampleEntity SET value=? WHERE k1=?;

dao.selectWhere(eq("k1", "1"), eq("k2", "2")).execute();
// SELECT * FROM demo.ExampleEntity WHERE k1=? AND k2=?;

dao.delete(eq("k1", "1")).execute();
// DELETE FROM demo.ExampleEntity WHERE k1=?;
```

## Credits

[Zhuochun](https://github.com/zhuochun) @ 2015
