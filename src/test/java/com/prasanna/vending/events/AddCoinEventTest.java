package com.prasanna.vending.events;

import com.prasanna.vending.domain.Coin;
import com.prasanna.vending.domain.UserAction;
import com.prasanna.vending.handler.EventHandler;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;


public class AddCoinEventTest {

    Mockery context = new Mockery();
    EventHandler handler;
    AddCoinEvent event;

    @Before
    public void setUp() throws Exception {
        event = new AddCoinEvent(UserAction.ADD_NICKEL, Coin.NICKLE);
        handler = context.mock(EventHandler.class);

    }

    @Test
    public void shouldInvokeAddNicKelMethod() throws Exception {

        context.checking(new Expectations() {
            {
                one(handler).inputCoin(event);
            }
        });

        event.applyEvent(handler);

        context.assertIsSatisfied();
    }
}