package com.ltrlabs.customer.service.exception;

import com.ltrlabs.user.service.exception.UserExistException;

public class CustomerExistException extends UserExistException {
    public CustomerExistException() {

    }
    public CustomerExistException(String message) {
        super(message);
    }
}
