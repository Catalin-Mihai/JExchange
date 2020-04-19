package com.company.exceptions;

public class DuplicateCurrencyException extends RuntimeException {
    public DuplicateCurrencyException(String cause) {
        super(cause);
    }
}
