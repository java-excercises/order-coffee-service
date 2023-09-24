package com.dbaotrung.example.coffee.common.converter.dbjson;

import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

public class CustomJsonbPostgresDialect extends PostgreSQL10Dialect {

    /**
     * Constructs a CustomPostgresDialect
     */
    public CustomJsonbPostgresDialect() {
        super();
        this.registerColumnType(Types.OTHER, "jsonb");
        this.registerColumnType(Types.STRUCT, "json");
        registerFunction("jsonbExtractPath", new StandardSQLFunction("jsonb_extract_path", StandardBasicTypes.SERIALIZABLE));
        registerFunction("jsonbExtractPathText", new StandardSQLFunction("jsonb_extract_path_text", StandardBasicTypes.STRING));
        registerFunction("jsonExtractPath", new StandardSQLFunction("json_extract_path", StandardBasicTypes.SERIALIZABLE));
        registerFunction("jsonExtractPathText", new StandardSQLFunction("json_extract_path_text", StandardBasicTypes.STRING));
    }

}
