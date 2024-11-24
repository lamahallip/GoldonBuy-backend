package com.goldonbuy.goldonbackend.catalogContext.exceptions;

public class AlreadyExistingException extends RuntimeException {
    public AlreadyExistingException(String message) {
        super(message);
    }
}
