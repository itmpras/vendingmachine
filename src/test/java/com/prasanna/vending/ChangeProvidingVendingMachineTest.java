package com.prasanna.vending;


import com.prasanna.vending.domain.VendableProduct;
import com.prasanna.vending.state.VendingMachineStateUpdate;
import com.prasanna.vending.domain.UserAction;
import com.prasanna.vending.events.AddCoinEvent;

import com.prasanna.vending.events.SellProductEvent;
import com.prasanna.vending.events.UserCoinReturnEvent;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;


public class ChangeProvidingVendingMachineTest {

    ChangeProvidingVendingMachine changeProvidingVendingMachine;
    VendingMachineStateUpdate vendingMachineState;
    Mockery context = new Mockery();

    @Before
    public void setUp() throws Exception {
        vendingMachineState = context.mock(VendingMachineStateUpdate.class);
        changeProvidingVendingMachine = new ChangeProvidingVendingMachine(vendingMachineState);
    }

    @Test
    public void shouldUpdateUserBalaceForAddCointEvent() throws Exception {
        final AddCoinEvent addNickelEvent = (AddCoinEvent) UserAction.ADD_NICKEL.getEventForAction();

        context.checking(new Expectations() {
            {
                one(vendingMachineState).addUserBalance(addNickelEvent);
            }
        });

        changeProvidingVendingMachine.inputCoin(addNickelEvent);

        context.assertIsSatisfied();

    }

    @Test
    public void shouldUpdateUserBalanceForCoinReturnEvent() throws Exception {
        final UserCoinReturnEvent userCoinReturnEvent = new UserCoinReturnEvent();

        context.checking(new Expectations() {
            {
                one(vendingMachineState).returnUserCoins();
            }
        });

        changeProvidingVendingMachine.returnUserCoins(userCoinReturnEvent);

        context.assertIsSatisfied();
    }

    @Test
    public void shouldUpdateInventoryAndUserBalanceForSellEvent() throws Exception {
        final SellProductEvent event = new SellProductEvent(VendableProduct.A);

        context.checking(new Expectations() {
            {
                one(vendingMachineState).processSell(event);
            }
        });


        changeProvidingVendingMachine.vendProduct(event);

        context.assertIsSatisfied();
    }
}