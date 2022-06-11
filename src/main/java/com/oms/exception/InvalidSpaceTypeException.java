package com.oms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidSpaceTypeException extends RuntimeException {

    private static final long serialVersionUID = 3L;

    public InvalidSpaceTypeException(String message) {
        super(message);
    }
}
