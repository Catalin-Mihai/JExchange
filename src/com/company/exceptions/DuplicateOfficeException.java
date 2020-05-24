package com.company.exceptions;

public class DuplicateOfficeException extends RuntimeException {
    public DuplicateOfficeException(String cause) {
        super(cause);
    }
}
