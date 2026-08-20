package com.statemachine.sm.errors;

import org.springframework.http.HttpStatus;

public interface ErrorResponse {

    String getMessage();
    String getKey();
    HttpStatus getHttpStatus();
}
