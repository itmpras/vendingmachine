package com.prasanna.vending.exception;

public class InSufficientBalanceAvailableForChange extends RuntimeException {
    public InSufficientBalanceAvailableForChange(String message) {
        super(message);
    }
}
