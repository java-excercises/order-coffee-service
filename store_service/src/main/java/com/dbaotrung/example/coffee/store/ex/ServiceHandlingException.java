package com.dbaotrung.example.coffee.store.ex;

import com.dbaotrung.example.coffee.store.dto.GeneralApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServiceHandlingException extends RuntimeException {

    /**
     * Generated.
     */
    private static final long serialVersionUID = -2963416786594466469L;

    /**
     * Should not use this constructor as general cases. Just use for special case only.
     *
     * @param message Error message
     * @param origin Origin exception
     */
    public ServiceHandlingException(String message, Throwable origin) {
        super(message, origin);
    }

    /**
     * Create exception with error message
     *
     * @param message Error message
     */
    public ServiceHandlingException(String message) {
        super(message);
    }

    /**
     * Create exception with error message
     *
     * @param apiResponse Error message
     * @throws JsonProcessingException
     */
    public ServiceHandlingException(GeneralApiResponse<?> apiResponse) throws JsonProcessingException {
        this(new ObjectMapper().writeValueAsString(apiResponse));
    }

    /**
     * Create exception with error message
     *
     * @param apiResponse Error message
     * @throws JsonProcessingException
     */
    public ServiceHandlingException(GeneralApiResponse<?> apiResponse, Throwable origin) throws JsonProcessingException {
        this(new ObjectMapper().writeValueAsString(apiResponse), origin);
    }
}
