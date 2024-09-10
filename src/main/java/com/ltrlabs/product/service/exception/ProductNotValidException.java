package com.ltrlabs.product.service.exception;

public class ProductNotValidException extends RuntimeException {

    public ProductNotValidException() {
    }

    public ProductNotValidException(String message) {
        super(message);
    }
}
