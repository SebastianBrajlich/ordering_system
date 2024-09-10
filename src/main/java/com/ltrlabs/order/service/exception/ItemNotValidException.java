package com.ltrlabs.order.service.exception;

public class ItemNotValidException extends RuntimeException {

    public ItemNotValidException() {
    }

    public ItemNotValidException(String message) {
        super(message);
    }
}
