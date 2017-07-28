package com.prasanna.vending.domain;


import com.prasanna.vending.events.ServiceEvent;
import com.prasanna.vending.events.VendingMachineEvent;
import com.prasanna.vending.provider.ChangeProvider;
import com.prasanna.vending.state.VendingMachineStateUpdateHandler;
import com.prasanna.vending.state.inventory.InventoryStateUpdate;
import com.prasanna.vending.state.machinebalance.MachineBalance;
import com.prasanna.vending.state.machinebalance.MachineBalanceStateUpdate;
import com.prasanna.vending.state.userbalance.UserBalanceStateUpdate;
import com.prasanna.vending.events.AddCoinEvent;
import com.prasanna.vending.events.SellProductEvent;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineStateHandlerTest {

    private InventoryStateUpdate inventoryState;
    private UserBalanceStateUpdate userState;
    private VendingMachineStateUpdateHandler initialState;
    private MachineBalanceStateUpdate machineBalanceStateUpdate;
    private ChangeProvider changeProvider;

    Mockery context = new Mockery();

    @Before
    public void setUp() throws Exception {
        inventoryState = context.mock(InventoryStateUpdate.class);
        userState = context.mock(UserBalanceStateUpdate.class);
        machineBalanceStateUpdate = context.mock(MachineBalanceStateUpdate.class);
        changeProvider  = context.mock(ChangeProvider.class);
        initialState = new VendingMachineStateUpdateHandler(userState, inventoryState, machineBalanceStateUpdate,changeProvider);
    }

    @Test
    public void shouldUserBalanceWhenAddCoinEventReceived() throws Exception {
        final AddCoinEvent eventForAction = (AddCoinEvent) UserAction.ADD_DIME.getEventForAction();

        context.checking(new Expectations() {
            {
                oneOf(userState).addUserBalance(eventForAction);
            }
        });

        initialState.addUserBalance(eventForAction);
        context.assertIsSatisfied();
    }

    @Test
    public void shouldUpdateUserStateWhenReturnCoinRequested() throws Exception {

        context.checking(new Expectations() {
            {
                oneOf(userState).returnUserCoinsAndRestBalance();
            }
        });


        initialState.returnUserCoins();
        context.assertIsSatisfied();
    }

    @Test
    public void shouldUpdateAllStateWhenProcessingSellEvent() throws Exception {
        final SellProductEvent eventForAction = (SellProductEvent) UserAction.GET_A.getEventForAction();

        context.checking(new Expectations() {
            {
                oneOf(userState).getUserBalance();
                will(returnValue(BigDecimal.ZERO));
                oneOf(inventoryState).updateInventory(eventForAction);
                oneOf(userState).updateUserBalance(eventForAction);
                will(returnValue(userState));
                oneOf(userState).resetUserBalance();
                oneOf(userState).getUserCoins();
                oneOf(changeProvider).provideChangeFor(with(any(MachineBalance.class)),with(any(BigDecimal.class)));
                oneOf(machineBalanceStateUpdate).updateMachineBalance(with(any(List.class)));
                will(returnValue(machineBalanceStateUpdate));
                oneOf(machineBalanceStateUpdate).getMachineBalance();
                will(returnValue(new MachineBalance()));
                oneOf(machineBalanceStateUpdate).reduceMachineBalance(with(any(List.class)));
                will(returnValue(machineBalanceStateUpdate));

            }
        });
        initialState.processSell(eventForAction);
        context.assertIsSatisfied();
    }

    @Test
    public void shouldUpdateBothInventoryAndMachineBalanceDuringServiceEvent() throws Exception {
        final ServiceEvent eventForAction = (ServiceEvent) UserAction.SERVICE.getEventForAction();

        context.checking( new Expectations() {
            {
               one(inventoryState).performService(eventForAction);
                one(machineBalanceStateUpdate).performService(eventForAction);

            }
        });

        initialState.performService(eventForAction);
        context.assertIsSatisfied();
    }
}