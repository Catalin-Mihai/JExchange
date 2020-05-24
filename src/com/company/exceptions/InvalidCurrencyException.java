package com.company.exceptions;

public class InvalidCurrencyException extends RuntimeException {
    public InvalidCurrencyException(String cause) {
        super(cause);
    }
}
