package com.prasanna.vending;

import com.prasanna.vending.domain.UserAction;
import com.prasanna.vending.events.AddCoinEvent;
import com.prasanna.vending.events.SellProductEvent;
import com.prasanna.vending.events.ServiceEvent;
import com.prasanna.vending.handler.EventHandler;
import com.prasanna.vending.listner.UserActionPublisherListner;
import com.prasanna.vending.events.VendingMachineEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VendingMachineCollaborator implements UserActionPublisherListner, EventHandler {

    static final Logger LOG = LoggerFactory.getLogger(VendingMachineCollaborator.class);
    private VendingMachine vendingMachine;

    public VendingMachineCollaborator(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void process(UserAction actions) {
        VendingMachineEvent event = actions.getEventForAction();
        if (event != null) {
            event.applyEvent(this);
        } else {
            LOG.info("No Operation required for action :" + actions);
        }

        vendingMachine.printStatus();

    }

    public void exit() {
        vendingMachine.cleanUp();
        vendingMachine.exit();
    }

    public void inputCoin(AddCoinEvent event) {
        vendingMachine.inputCoin(event);
    }

    public void returnUserCoins(VendingMachineEvent event) {
        vendingMachine.returnUserCoins(event);
    }

    public void vendProduct(SellProductEvent event) {
        vendingMachine.vendProduct(event);
    }

    public void start() {
        vendingMachine.start();
    }

    public void service(ServiceEvent serviceEvent) {
        vendingMachine.service(serviceEvent);
    }
}
