package com.prasanna.vending;

import com.prasanna.vending.events.ServiceEvent;
import com.prasanna.vending.state.VendingMachineStateUpdate;
import com.prasanna.vending.state.VendingMachineState;
import com.prasanna.vending.events.AddCoinEvent;
import com.prasanna.vending.events.SellProductEvent;
import com.prasanna.vending.events.VendingMachineEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChangeProvidingVendingMachine implements VendingMachine {
    static final Logger LOG = LoggerFactory.getLogger(ChangeProvidingVendingMachine.class);
    private volatile VendingMachineStateUpdate state;

    public ChangeProvidingVendingMachine(VendingMachineStateUpdate state) {
        this.state = state;
    }

    public void cleanUp() {
        LOG.info("Cleaning up");
    }

    public void exit() {
        LOG.info("Exiting Vending Machine");
        System.exit(0);
    }

    public void start() {
        printStatus();
    }

    public void inputCoin(AddCoinEvent event) {
        LOG.info("Consuming Event :" + event.toString());
        LOG.info("Updating Vending Machine State  :" + state);
        VendingMachineStateUpdate vendingMachineState = state.addUserBalance(event);
        state = vendingMachineState;
    }

    public VendingMachineState getCurrentState() {
        return state;
    }

    public void returnUserCoins(VendingMachineEvent event) {
        LOG.info("Consuming Event :" + event.toString());
        LOG.info("Updating Vending Machine State  :" + state);
        VendingMachineStateUpdate vendingMachineState = state.returnUserCoins();
        state = vendingMachineState;

    }

    public void vendProduct(SellProductEvent event) {
        LOG.info("Consuming Event :" + event.toString());
        LOG.info("Updating Vending Machine State  :" + state);
        VendingMachineStateUpdate vendingMachineStateUpdate = state.processSell(event);
        state = vendingMachineStateUpdate;

    }

    public void service(ServiceEvent serviceEvent) {
        LOG.info("Consuming Event :" + serviceEvent.toString());
        state = state.performService(serviceEvent);
    }

    public void printStatus() {
        LOG.info("Current State :" + state.toString());
    }
}