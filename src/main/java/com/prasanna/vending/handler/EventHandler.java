package com.prasanna.vending.handler;

import com.prasanna.vending.events.AddCoinEvent;
import com.prasanna.vending.events.SellProductEvent;
import com.prasanna.vending.events.ServiceEvent;
import com.prasanna.vending.events.VendingMachineEvent;

public interface EventHandler {
    void exit();

    void start();

    void inputCoin(AddCoinEvent event);

    void returnUserCoins(VendingMachineEvent event);

    void vendProduct(SellProductEvent event);

    void service(ServiceEvent serviceEvent);
}
