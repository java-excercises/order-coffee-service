package com.dbaotrung.example.coffee.dbjson;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class JsonStringSqlTypeDescriptor extends AbstractJsonSqlTypeDescriptor {

    /**
     * Generated!
     */
    private static final long serialVersionUID = 4436498945309785917L;
    
    public static final JsonStringSqlTypeDescriptor INSTANCE = new JsonStringSqlTypeDescriptor();

    @Override
    public int getSqlType() {
        return Types.STRUCT;
    }

    @Override
    public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicBinder<X>(javaTypeDescriptor, this) {
            @Override
            protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
                st.setObject(index, javaTypeDescriptor.unwrap(value, JsonNode.class, options), Types.OTHER);
            }

            @Override
            protected void doBind(CallableStatement st, X value, String name, WrapperOptions options) throws SQLException {
                st.setObject(name, javaTypeDescriptor.unwrap(value, JsonNode.class, options), Types.OTHER);
            }
        };
    }
}
