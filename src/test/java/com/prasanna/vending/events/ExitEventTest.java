package com.prasanna.vending.events;

import com.prasanna.vending.handler.EventHandler;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

public class ExitEventTest {

    ExitEvent exitEvent;
    Mockery context = new Mockery();
    EventHandler handler;

    @Before
    public void setUp() throws Exception {
        exitEvent = new ExitEvent();
    }

    @Test
    public void exitEventShouldExitVendingMachine() throws Exception {
        handler = context.mock(EventHandler.class);

        context.checking(new Expectations() {
            {
                one(handler).exit();

            }
        });

        exitEvent.applyEvent(handler);


        context.assertIsSatisfied();
    }
}