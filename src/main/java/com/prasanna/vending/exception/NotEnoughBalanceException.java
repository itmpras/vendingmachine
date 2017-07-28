package com.prasanna.vending.exception;

/**
 * Created by pgopal on 27/07/2017.
 */
public class NotEnoughBalanceException extends RuntimeException {

    public NotEnoughBalanceException(String message) {
        super(message);
    }
}
