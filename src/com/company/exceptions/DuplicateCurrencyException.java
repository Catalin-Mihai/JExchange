package com.company.exceptions;

public class DuplicateCurrencyException extends Exception{
    public DuplicateCurrencyException(String cause){
        super(cause);
    }
}
