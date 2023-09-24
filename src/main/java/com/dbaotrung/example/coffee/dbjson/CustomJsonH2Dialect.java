package com.dbaotrung.example.coffee.dbjson;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;

public class CustomJsonH2Dialect extends H2Dialect {

    public CustomJsonH2Dialect() {
        registerColumnType(Types.OTHER, StandardBasicTypes.TEXT.getName());
        registerColumnType(Types.STRUCT, StandardBasicTypes.TEXT.getName());
        registerColumnType( Types.LONGVARCHAR, String.format( "varchar(%d)", 1048576 ) );
    }
}
