package com.company.exceptions;

public class DuplicateClientException extends RuntimeException {
    public DuplicateClientException(String cause) {
        super(cause);
    }
}
