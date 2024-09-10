package com.ltrlabs.user.service.exception;

public class UserNotValidException extends RuntimeException {

  public UserNotValidException() {
  }

  public UserNotValidException(String message) {
        super(message);
    }
}
