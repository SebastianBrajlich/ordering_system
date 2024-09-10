package com.ltrlabs.customer.service.exception;

import com.ltrlabs.user.service.exception.NoSuchUserException;

public class NoSuchCustomerException extends NoSuchUserException {

  public NoSuchCustomerException() {
  }

  public NoSuchCustomerException(String message) {
        super(message);
    }
}
