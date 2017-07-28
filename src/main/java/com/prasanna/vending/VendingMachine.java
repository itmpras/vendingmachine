package com.prasanna.vending;


import com.prasanna.vending.handler.EventHandler;

public interface VendingMachine extends EventHandler {
    void cleanUp();

    void printStatus();

}
