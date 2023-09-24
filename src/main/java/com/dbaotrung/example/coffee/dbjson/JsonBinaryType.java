package com.dbaotrung.example.coffee.dbjson;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Properties;

public class JsonBinaryType extends AbstractSingleColumnStandardBasicType<Object> implements DynamicParameterizedType, Serializable {

    /**
     * Generated!
     */
    private static final long serialVersionUID = -1671700335100624524L;

    public static final JsonBinaryType INSTANCE = new JsonBinaryType();

    public JsonBinaryType() {
        super(JsonBinarySqlTypeDescriptor.INSTANCE, new JsonTypeDescriptor());
    }

    public JsonBinaryType(Type javaType) {
        super(JsonBinarySqlTypeDescriptor.INSTANCE, new JsonTypeDescriptor(javaType));
    }

    public String getName() {
        return "jsonb";
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((JsonTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }

}
