package com.company.exceptions;

public class InvalidExchangeRateException extends RuntimeException {
    public InvalidExchangeRateException(String cause) {
        super(cause);
    }
}

