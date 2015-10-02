package com.bicrement.cassandra.play.context;

import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableBiMap.Builder;

public class DefaultDataTypeMapper implements DataTypeMapper {

    public static enum DataType {
        BIGINT(Long.class),
        BLOB(ByteBuffer.class),
        BOOLEAN(Boolean.class),
        DECIMAL(BigDecimal.class),
        DOUBLE(Double.class),
        FLOAT(Float.class),
        INET(InetAddress.class),
        INT(Integer.class),
        TEXT(String.class),
        TIMESTAMP(Date.class),
        VARINT(BigInteger.class),
        TIMEUUID(UUID.class);

        private final Class<?> javaClass;

        DataType(Class<?> javaClass) {
            this.javaClass = javaClass;
        }

        public Class<?> javaClass() {
            return javaClass;
        }
    }

    private final BiMap<String, Class<?>> DATA_TYPE_MAP;

    public DefaultDataTypeMapper() {
        Builder<String, Class<?>> builder = new ImmutableBiMap.Builder<>();

        for (DataType dt : DataType.values()) {
            builder.put(dt.name(), dt.javaClass());
        }

        DATA_TYPE_MAP = builder.build();
    }

    @Override
	public Class<?> asJavaClass(String type) {
        Class<?> javaClass = DATA_TYPE_MAP.get(type);
        checkNotNull(javaClass, String.format("Type %s cannot map to a valid Java class", type));
        return javaClass;
    }

    @Override
	public String asDataType(Class<?> javaClass) {
        String dataType = DATA_TYPE_MAP.inverse().get(javaClass);
        checkNotNull(dataType, String.format("Java class %s cannot map to a valid Database type", javaClass));
        return dataType;
    }

}