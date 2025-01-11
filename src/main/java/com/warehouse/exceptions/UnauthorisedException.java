package com.warehouse.exceptions;

public class UnauthorisedException extends RuntimeException {

    public UnauthorisedException(String message, Exception e) {
        super(message, e);
    }
}
