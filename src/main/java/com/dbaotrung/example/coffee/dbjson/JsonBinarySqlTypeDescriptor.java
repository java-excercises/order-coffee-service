package com.dbaotrung.example.coffee.dbjson;

import java.sql.Types;

public class JsonBinarySqlTypeDescriptor extends AbstractJsonSqlTypeDescriptor {

    /**
     * Generated!
     */
    private static final long serialVersionUID = 4436498945309785917L;
    
    public static final JsonBinarySqlTypeDescriptor INSTANCE = new JsonBinarySqlTypeDescriptor();

    @Override
    public int getSqlType() {
        return Types.OTHER;
    }

}
