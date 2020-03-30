package com.company.exceptions;

public class DuplicateClientException extends Exception {
    public DuplicateClientException(String cause){
        super(cause);
    }
}
