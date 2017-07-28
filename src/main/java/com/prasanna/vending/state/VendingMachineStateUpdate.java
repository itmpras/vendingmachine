package com.prasanna.vending.state;


import com.prasanna.vending.events.AddCoinEvent;
import com.prasanna.vending.events.SellProductEvent;
import com.prasanna.vending.events.ServiceEvent;

public interface VendingMachineStateUpdate extends VendingMachineState {
    VendingMachineStateUpdate addUserBalance(AddCoinEvent bigDecimal);

    VendingMachineStateUpdate returnUserCoins();

    VendingMachineStateUpdate processSell(SellProductEvent event);

    VendingMachineStateUpdate performService(ServiceEvent event);

}
