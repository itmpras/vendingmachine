package com.prasanna.vending.exception;

/**
 * Created by pgopal on 27/07/2017.
 */
public class ProductNotAvailableException extends RuntimeException {

    public ProductNotAvailableException(String message) {
        super(message);
    }
}
