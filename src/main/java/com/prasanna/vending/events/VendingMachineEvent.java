package com.prasanna.vending.events;


import com.prasanna.vending.handler.EventHandler;

public interface VendingMachineEvent {
    void applyEvent(EventHandler handler);
}
