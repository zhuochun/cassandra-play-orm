package com.bicrement.cassandra.play;

import com.bicrement.cassandra.play.context.DataTypeMapper;
import com.bicrement.cassandra.play.context.DefaultDataTypeMapper;
import com.bicrement.cassandra.play.context.Namelizer;
import com.bicrement.cassandra.play.context.Serializer;
import com.bicrement.cassandra.play.context.SimpleNamelizer;
import com.bicrement.cassandra.play.context.SimpleSerializer;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Session;

/**
 * Context is an immutable aggregation of Database settings
 *
 * @author Zhuochun
 *
 */
public class Context {

    private final Session session;
    private final String keyspace;
    private final boolean enableTracing;
    private final ConsistencyLevel consistencyLevel;
    private final Namelizer namelizer;
    private final Serializer serializer;
    private final DataTypeMapper dataTypeMapper;

    public Context() { // TODO
        this.session = null;
        this.keyspace = "demo";
        this.enableTracing = false;
        this.consistencyLevel = ConsistencyLevel.ONE;
        this.namelizer = new SimpleNamelizer();
        this.serializer = new SimpleSerializer();
        this.dataTypeMapper = new DefaultDataTypeMapper();
    }

    public Context(Session session, String keyspace,
            boolean enableTracing, ConsistencyLevel consistencyLevel,
            Namelizer namelizer, Serializer serializer,
            DataTypeMapper dataTypeMapper) {
        this.session = session;
        this.keyspace = keyspace;
        this.enableTracing = enableTracing;
        this.consistencyLevel = consistencyLevel;
        this.namelizer = namelizer;
        this.serializer = serializer;
        this.dataTypeMapper = dataTypeMapper;
    }

    public Session getSession() {
        return session;
    }

    public Object getKeyspace() {
        return keyspace;
    }

    public Context useKeyspace(String keyspace) {
        return new Context(); // TODO
    }

    public Namelizer getNamelizer() {
        return namelizer;
    }

    public Context useNamelizer(Namelizer namelizer) {
        return new Context(); // TODO
    }

    public Serializer getSerializer() {
        return serializer;
    }

    public Context useSerializer(Serializer serializer) {
        return new Context(); // TODO
    }

    public DataTypeMapper getDataTypeMapper() {
        return dataTypeMapper;
    }

    public Context useDataTypeMapper(DataTypeMapper dataTypeMapper) {
        return new Context(); // TODO
    }

    public ConsistencyLevel getConsistency() {
        return consistencyLevel;
    }

    public Context withConsistency(ConsistencyLevel consistency) {
        return new Context(); // TODO
    }

    public boolean enableTracing() {
        return enableTracing;
    }

    public Context withTracing(boolean isEnable) {
        return new Context(); // TODO
    }

}
