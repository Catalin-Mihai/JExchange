package com.company.exceptions;

public class InvalidCurrencyNameException extends RuntimeException {
    public InvalidCurrencyNameException(String cause) {
        super(cause);
    }
}
