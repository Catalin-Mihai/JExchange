package com.company.exceptions;

public class DuplicateExchangeRateException extends RuntimeException {
    public DuplicateExchangeRateException(String cause) {
        super(cause);
    }
}
