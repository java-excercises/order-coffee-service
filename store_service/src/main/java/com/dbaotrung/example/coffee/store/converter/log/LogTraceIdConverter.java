package com.dbaotrung.example.coffee.store.converter.log;

import com.dbaotrung.example.coffee.store.context.TraceIdContext;

public class LogTraceIdConverter extends LogDataConverter {

    public LogTraceIdConverter() {
        super("Trace ID");
    }

    @Override
    protected String retrieveData() {
        var context = TraceIdContext.getInstance();
        if (context == null) {
            return null;
        }
        return context.getTraceId();
    }

}
