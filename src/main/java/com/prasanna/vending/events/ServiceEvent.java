package com.prasanna.vending.events;

import com.prasanna.vending.handler.EventHandler;

public class ServiceEvent implements VendingMachineEvent {

    public void applyEvent(EventHandler handler) {
        handler.service(this);
    }
}
