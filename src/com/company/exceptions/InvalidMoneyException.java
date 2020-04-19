package com.company.exceptions;

public class InvalidMoneyException extends RuntimeException {
    public InvalidMoneyException(String cause) {
        super(cause);
    }
}
