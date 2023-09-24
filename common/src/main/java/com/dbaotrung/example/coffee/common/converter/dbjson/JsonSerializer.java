package com.dbaotrung.example.coffee.common.converter.dbjson;

public interface JsonSerializer {

    /**
     * Clone JSON object.
     *
     * @param jsonObject JSON object
     * @param <T> JSON object parameterized type
     * @return cloned JSON object
     */
    <T> T clone(T jsonObject);

}
