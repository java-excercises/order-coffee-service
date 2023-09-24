package com.dbaotrung.example.coffee.common.converter.dbjson;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractJsonSqlTypeDescriptor implements SqlTypeDescriptor {

    /**
     * Generated!
     */
    private static final long serialVersionUID = 2479824634284901504L;

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJsonSqlTypeDescriptor.class);

    @Override
    public boolean canBeRemapped() {
        return true;
    }

    @Override
    public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicExtractor<X>(javaTypeDescriptor, this) {

            @Override
            public X extract(ResultSet rs, int paramIndex, WrapperOptions options) throws SQLException {
                return javaTypeDescriptor.wrap(extractJson(rs, paramIndex), options);
            }

            @Override
            public X extract(CallableStatement statement, String paramName, WrapperOptions options)
                    throws SQLException {
                return doExtract(statement, paramName, options);
            }

            @Override
            protected X doExtract(ResultSet rs, String name, WrapperOptions options) throws SQLException {
                return javaTypeDescriptor.wrap(extractJson(rs, name), options);
            }

            @Override
            protected X doExtract(CallableStatement statement, int index, WrapperOptions options) throws SQLException {
                return javaTypeDescriptor.wrap(extractJson(statement, index), options);
            }

            @Override
            protected X doExtract(CallableStatement statement, String name, WrapperOptions options) throws SQLException {
                return javaTypeDescriptor.wrap(extractJson(statement, name), options);
            }
        };
    }

    protected Object extractJson(ResultSet rs, String name) throws SQLException {
        Object result = rs.getObject(name);
        LOGGER.debug("Extracted field [{}] from ResultSet [{}][{}]", name, result != null ? result.getClass() + ": " : "", result);
        return result;
    }

    protected Object extractJson(CallableStatement statement, int index) throws SQLException {
        return statement.getObject(index);
    }

    protected Object extractJson(ResultSet rs, int index) throws SQLException {
        return rs.getObject(index);
    }

    protected Object extractJson(CallableStatement statement, String name) throws SQLException {
        Object result = statement.getObject(name);
        LOGGER.debug("Extracted field [{}] from CallableStatement [{}][{}]", name, result != null ? result.getClass() + ": " : "", result);
        return result;
    }

    @Override
    public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
        return new BasicBinder<X>(javaTypeDescriptor, this) {
            @Override
            protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
                st.setObject(index, javaTypeDescriptor.unwrap(value, JsonNode.class, options), getSqlType());
            }

            @Override
            protected void doBind(CallableStatement st, X value, String name, WrapperOptions options) throws SQLException {
                st.setObject(name, javaTypeDescriptor.unwrap(value, JsonNode.class, options), getSqlType());
            }
        };
    }

}
