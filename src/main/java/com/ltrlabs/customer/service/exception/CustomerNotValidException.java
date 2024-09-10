package com.ltrlabs.customer.service.exception;

import com.ltrlabs.user.service.exception.UserNotValidException;

public class CustomerNotValidException extends UserNotValidException {

  public CustomerNotValidException() {
  }

  public CustomerNotValidException(String message) {
        super(message);
    }
}
