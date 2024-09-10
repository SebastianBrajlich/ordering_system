package com.ltrlabs.order.service.exception;

public class OrderInvalidException extends RuntimeException {

    public OrderInvalidException() {
    }

    public OrderInvalidException(String message) {
        super(message);
    }
}
