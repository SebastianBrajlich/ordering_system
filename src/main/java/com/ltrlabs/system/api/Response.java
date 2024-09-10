package com.ltrlabs.system.api;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Response <T> {

    private T value;
    private boolean isError;
    private String errorMessage;
    private ErrorCode errorCode;

    public Optional<T> getValue() {
        return Optional.ofNullable(value);
    }
}
