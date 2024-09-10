package com.ltrlabs.order.service.exception;

public class NoOrderFoundException extends RuntimeException {

    public NoOrderFoundException() {
    }

    public NoOrderFoundException(String message) {
        super(message);
    }
}
