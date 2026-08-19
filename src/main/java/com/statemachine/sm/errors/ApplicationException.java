package com.statemachine.sm.errors;

import java.util.Map;

public class ApplicationException extends RuntimeException {

    private final ErrorResponse error;
    private final Map<String, Object> params;

    public ApplicationException(ErrorResponse error, Map<String, Object> params) {
        super(error.getMessage());
        this.error = error;
        this.params = params;
    }

    public ErrorResponse getError() {
        return error;
    }

    public Map<String, Object> getParams() {
        return params;
    }


}
