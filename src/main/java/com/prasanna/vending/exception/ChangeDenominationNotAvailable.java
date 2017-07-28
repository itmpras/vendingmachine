package com.prasanna.vending.exception;

public class ChangeDenominationNotAvailable extends RuntimeException {
    public ChangeDenominationNotAvailable(String message) {
        super(message);
    }
}
