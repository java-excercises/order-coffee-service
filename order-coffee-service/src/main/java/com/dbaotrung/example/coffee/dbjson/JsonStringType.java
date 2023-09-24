package com.dbaotrung.example.coffee.dbjson;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Properties;

public class JsonStringType extends AbstractSingleColumnStandardBasicType<Object> implements DynamicParameterizedType, Serializable {

    /**
     * Generated!
     */
    private static final long serialVersionUID = -1671700335100624524L;

    public JsonStringType() {
        super(JsonStringSqlTypeDescriptor.INSTANCE, new JsonTypeDescriptor());
    }

    public JsonStringType(Type javaType) {
        super(JsonStringSqlTypeDescriptor.INSTANCE, new JsonTypeDescriptor(javaType));
    }

    public String getName() {
        return "jsonstr";
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((JsonTypeDescriptor) getJavaTypeDescriptor()).setParameterValues(parameters);
    }

}
