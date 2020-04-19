package com.company.exceptions;

public class ForbiddenExchangeException extends RuntimeException {
    public ForbiddenExchangeException(String cause){ super(cause); }
}
