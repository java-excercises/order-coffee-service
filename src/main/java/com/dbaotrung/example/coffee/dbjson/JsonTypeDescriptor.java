package com.dbaotrung.example.coffee.dbjson;

import org.hibernate.HibernateException;
import org.hibernate.annotations.common.reflection.XProperty;
import org.hibernate.annotations.common.reflection.java.JavaXMember;
import org.hibernate.engine.jdbc.BinaryStream;
import org.hibernate.engine.jdbc.internal.BinaryStreamImpl;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.BlobTypeDescriptor;
import org.hibernate.type.descriptor.java.DataHelper;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;
import org.hibernate.usertype.DynamicParameterizedType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;
import java.util.Properties;

public class JsonTypeDescriptor extends AbstractTypeDescriptor<Object> implements DynamicParameterizedType {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonTypeDescriptor.class);

    /**
     * Generated!
     */
    private static final long serialVersionUID = 1641118604581427107L;

    private Type propertyType;

    private Class<?> propertyClass;

    private ObjectMapperWrapper objectMapperWrapper;

    public JsonTypeDescriptor(Type type) {
        this();
        setPropertyClass(type);
    }

    public JsonTypeDescriptor() {
        super(Object.class, new CustomMutableMutabilityPlan(new ObjectMapperWrapper()));
        this.objectMapperWrapper = ((CustomMutableMutabilityPlan) getMutabilityPlan()).objectMapperWrapper;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        final XProperty xProperty = (XProperty) parameters.get(DynamicParameterizedType.XPROPERTY);
        Type type = (xProperty instanceof JavaXMember) ? ReflectionUtils.invokeGetter(xProperty, "javaType")
                : ((ParameterType) parameters.get(PARAMETER_TYPE)).getReturnedClass();
        setPropertyClass(type);
    }

    @Override
    public boolean areEqual(Object one, Object another) {
        if (one == another) {
            return true;
        }
        if (one == null || another == null) {
            return false;
        }
        if (one instanceof String && another instanceof String) {
            return one.equals(another);
        }
        if (one instanceof Collection && another instanceof Collection) {
            return Objects.equals(one, another);
        }
        if (one.getClass().equals(another.getClass())
                && ReflectionUtils.getDeclaredMethodOrNull(one.getClass(), "equals", Object.class) != null) {
            return one.equals(another);
        }
        return objectMapperWrapper.toJsonNode(objectMapperWrapper.toString(one))
                .equals(objectMapperWrapper.toJsonNode(objectMapperWrapper.toString(another)));
    }

    @Override
    public String toString(Object value) {
        return objectMapperWrapper.toString(value);
    }

    @Override
    public Object fromString(String string) {
        if (String.class.isAssignableFrom(propertyClass)) {
            return string;
        }
        return objectMapperWrapper.fromString(string, propertyType);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <X> X unwrap(Object value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }

        LOGGER.debug("Value {}::{} to {}", value, value.getClass(), type);
        if (String.class.isAssignableFrom(type)) {
            return value instanceof String ? (X) value : (X) toString(value);
        } else if (BinaryStream.class.isAssignableFrom(type) || byte[].class.isAssignableFrom(type)) {
            String stringValue = (value instanceof String) ? (String) value : toString(value);

            return (X) new BinaryStreamImpl(
                    DataHelper.extractBytes(new ByteArrayInputStream(stringValue.getBytes(StandardCharsets.UTF_8))));
        } else if (Blob.class.isAssignableFrom(type)) {
            String stringValue = (value instanceof String) ? (String) value : toString(value);

            final Blob blob = BlobTypeDescriptor.INSTANCE.fromString(stringValue);
            return (X) blob;
        } else if (Object.class.isAssignableFrom(type)) {
            String stringValue = (value instanceof String) ? (String) value : toString(value);
            return (X) objectMapperWrapper.toJsonNode(stringValue);
        }

        throw unknownUnwrap(type);
    }

    @Override
    public <X> Object wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        LOGGER.debug("Value {}::{}", value, value.getClass());
        Blob blob = null;
        try {
            if (Clob.class.isAssignableFrom(value.getClass())) {
                InputStream inputStream = ((Clob) value).getAsciiStream();
                blob = options.getLobCreator().createBlob(inputStream, inputStream.available());
            } else if (Blob.class.isAssignableFrom(value.getClass())) {
                blob = options.getLobCreator().wrap((Blob) value);
            } else if (byte[].class.isAssignableFrom(value.getClass())) {
                blob = options.getLobCreator().createBlob((byte[]) value);
            } else if (InputStream.class.isAssignableFrom(value.getClass())) {
                InputStream inputStream = (InputStream) value;
                blob = options.getLobCreator().createBlob(inputStream, inputStream.available());
            }
        } catch (IOException | SQLException e) {
            LOGGER.debug("Error when parsing data from DB!", e);
            throw unknownWrap(value.getClass());
        }

        String stringValue;
        try {
            stringValue =
                    (blob != null) ? new String(DataHelper.extractBytes(blob.getBinaryStream()), StandardCharsets.UTF_8) : value.toString();
        } catch (SQLException e) {
            throw new HibernateException("Unable to extract binary stream from Blob", e);
        }

        return fromString(stringValue);
    }

    private void setPropertyClass(Type type) {
        this.propertyType = type;
        if (type instanceof ParameterizedType) {
            type = ((ParameterizedType) type).getRawType();
        } else if (type instanceof TypeVariable) {
            type = ((TypeVariable<?>) type).getGenericDeclaration().getClass();
        }
        this.propertyClass = (Class<?>) type;
    }

    private static class CustomMutableMutabilityPlan extends MutableMutabilityPlan<Object> {
        /**
         * Generated!
         */
        private static final long serialVersionUID = 4096315174609032341L;

        private ObjectMapperWrapper objectMapperWrapper;

        private CustomMutableMutabilityPlan(ObjectMapperWrapper objectMapperWrapper) {
            this.objectMapperWrapper = objectMapperWrapper;
        }

        @Override
        protected Object deepCopyNotNull(Object value) {
            return objectMapperWrapper.clone(value);
        }
    };
}
