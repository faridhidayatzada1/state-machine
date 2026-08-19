package com.statemachine.sm.errors;


import org.springframework.http.HttpStatus;

public enum Errors implements ErrorResponse {
    ACCOUNT_NOT_FOUND("Account not found", HttpStatus.NOT_FOUND, "Account with {id} not found"),
    INVALID_ACCOUNT_STATUS("Invalid account status", HttpStatus.BAD_REQUEST, "Account with {id} has invalid status"),
    INVALID_TRANSACTION("Invalid transaction", HttpStatus.BAD_REQUEST, "Transaction with {id} is invalid");

    String message;
    String key;
    HttpStatus httpStatus;


    Errors(String key, HttpStatus httpStatus, String message) {
        this.message = message;
        this.key = key;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getKey() {
            return key;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return null;
    }



}
