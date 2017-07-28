package com.prasanna.vending.events;


import com.prasanna.vending.domain.UserAction;
import com.prasanna.vending.domain.VendableProduct;
import com.prasanna.vending.handler.EventHandler;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;


public class SellProductEventTest {

    Mockery context = new Mockery();
    EventHandler handler;
    SellProductEvent event;

    @Before
    public void setUp() throws Exception {
        event = new SellProductEvent(VendableProduct.A);
        handler = context.mock(EventHandler.class);
    }


    @Test
    public void shouldCallEventHandlerWhenSellEventReceived() throws Exception {

        context.checking( new Expectations() {
            {
                oneOf(handler).vendProduct(event);
            }
        });

        event.applyEvent(handler);
        context.assertIsSatisfied();
    }
}