package com.company.exceptions;

public class InvalidCurrencyNameException extends Exception {
    public InvalidCurrencyNameException(String cause){
        super(cause);
    }
}
