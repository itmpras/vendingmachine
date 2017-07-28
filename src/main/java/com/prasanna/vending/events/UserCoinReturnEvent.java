package com.prasanna.vending.events;

import com.prasanna.vending.handler.EventHandler;

public class UserCoinReturnEvent implements VendingMachineEvent {

    String desc = "UserCoinReturnEvent";

    public void applyEvent(EventHandler handler) {
        handler.returnUserCoins(this);
    }

    @Override
    public String toString() {
        return "UserCoinReturnEvent{" +
                "desc='" + desc + '\'' +
                '}';
    }
}
