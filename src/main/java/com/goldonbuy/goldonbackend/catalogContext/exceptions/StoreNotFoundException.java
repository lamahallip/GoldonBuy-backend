package com.goldonbuy.goldonbackend.catalogContext.exceptions;

import org.springframework.data.jpa.repository.JpaRepository;

public class StoreNotFoundException extends RuntimeException {

    public StoreNotFoundException(String message) {
        super(message);
    }
}
