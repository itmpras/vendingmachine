package com.prasanna.vending.events;


import com.prasanna.vending.domain.UserAction;
import com.prasanna.vending.handler.EventHandler;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

public class UserCoinReturnEventTest {

    Mockery context = new Mockery();
    EventHandler handler;
    VendingMachineEvent userCoinReturnEvent;

    @Before
    public void setUp() throws Exception {
        userCoinReturnEvent = new UserCoinReturnEvent();
        handler = context.mock(EventHandler.class);
    }

    @Test
    public void shouldReturnUserCoins() throws Exception {

        context.checking(new Expectations() {
            {

                one(handler).returnUserCoins(userCoinReturnEvent);
            }
        });

        userCoinReturnEvent.applyEvent(handler);
        context.assertIsSatisfied();
    }
}