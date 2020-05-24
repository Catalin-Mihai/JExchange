package com.company.exceptions;

public class InvalidClientException extends RuntimeException {
    public InvalidClientException(String cause) {
        super(cause);
    }
}
