package com.bicrement.cassandra.play.context;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

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
    }

    private static final Map<String, Class<?>> JAVA_CLASS_MAP = null; // TODO
    private static final Map<Class<?>, String> DATA_TYPE_MAP = null; // TODO

    @Override
    public Class<?> asJavaClass(String type) {
        return String.class;
        // return JAVA_CLASS_MAP.getOrDefault(type, null);
    }

    @Override
    public String asDataType(Class<?> javaClass) {
        return "text";
        // return DATA_TYPE_MAP.getOrDefault(javaClass, "blob");
    }

}
