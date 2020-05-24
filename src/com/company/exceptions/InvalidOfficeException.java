package com.company.exceptions;

public class InvalidOfficeException extends RuntimeException {
    public InvalidOfficeException(String cause) {
        super(cause);
    }
}
