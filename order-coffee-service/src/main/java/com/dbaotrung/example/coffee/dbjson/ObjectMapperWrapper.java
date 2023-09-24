package com.dbaotrung.example.coffee.dbjson;

import com.dbaotrung.common.context.ApplicationContextHolder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Wraps a Jackson {@link ObjectMapper} so that you can supply your own {@link ObjectMapper}
 * reference.
 */
public class ObjectMapperWrapper {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectMapperWrapper.class);

    public static final ObjectMapperWrapper INSTANCE = new ObjectMapperWrapper();

    private JsonSerializer jsonSerializer = new ObjectMapperJsonSerializer(this);

    public ObjectMapper getObjectMapper() {
        return ApplicationContextHolder.getInstance().getApplicationContext().getBean(ObjectMapper.class);
    }

    public <T> T fromString(String string, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(string, clazz);
        } catch (IOException e) {
            throw new HibernateException(
                    new IllegalArgumentException("The given string value: " + string + " cannot be transformed to Json object", e));
        }
    }

    public <T> T fromString(String string, Type type) {
        try {
            try {
                return getObjectMapper().readValue(string, getObjectMapper().getTypeFactory().constructType(type));
            } catch (JsonParseException e) {
                // In case input string is hexa str
                try {
                    return getObjectMapper().readValue(hexStringToByteArray(string),
                            getObjectMapper().getTypeFactory().constructType(type));
                } catch (JsonParseException ex) {
                    LOGGER.debug("Err", ex);
                    throw e;
                }
            }
        } catch (IOException e) {
            throw new HibernateException(
                    new IllegalArgumentException("The given string value: " + string + " cannot be transformed to Json object", e));
        }
    }

    public <T> T fromBytes(byte[] value, Class<T> clazz) {
        try {
            return getObjectMapper().readValue(value, clazz);
        } catch (IOException e) {
            throw new HibernateException(new IllegalArgumentException("The given byte array cannot be transformed to Json object", e));
        }
    }

    public <T> T fromBytes(byte[] value, Type type) {
        try {
            return getObjectMapper().readValue(value, getObjectMapper().getTypeFactory().constructType(type));
        } catch (IOException e) {
            throw new HibernateException(new IllegalArgumentException("The given byte array cannot be transformed to Json object", e));
        }
    }

    public String toString(Object value) {
        try {
            return getObjectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new HibernateException(
                    new IllegalArgumentException("The given Json object value: " + value + " cannot be transformed to a String", e));
        }
    }

    public byte[] toBytes(Object value) {
        try {
            return getObjectMapper().writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            throw new HibernateException(
                    new IllegalArgumentException("The given Json object value: " + value + " cannot be transformed to a byte array", e));
        }
    }

    public JsonNode toJsonNode(String value) {
        try {
            return getObjectMapper().readTree(value);
        } catch (IOException e) {
            throw new HibernateException(new IllegalArgumentException(e));
        }
    }

    public <T> T clone(T value) {
        return jsonSerializer.clone(value);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
