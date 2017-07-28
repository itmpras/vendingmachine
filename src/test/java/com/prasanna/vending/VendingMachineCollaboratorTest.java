package com.prasanna.vending;

import com.prasanna.vending.domain.UserAction;
import com.prasanna.vending.events.AddCoinEvent;
import com.prasanna.vending.events.SellProductEvent;
import com.prasanna.vending.events.UserCoinReturnEvent;
import com.prasanna.vending.events.VendingMachineEvent;
import com.prasanna.vending.translator.UserActionTranslator;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pgopal on 26/07/2017.
 */
public class VendingMachineCollaboratorTest {

    static final Logger LOG = LoggerFactory.getLogger(VendingMachineCollaboratorTest.class);
    Mockery context = new Mockery();
    VendingMachineCollaborator vendingMachineCollaborator;
    private UserActionTranslator eventTranslator;
    private VendingMachine vendingMachine;

    @Before
    public void setUp() throws Exception {
        eventTranslator = context.mock(UserActionTranslator.class);
        vendingMachine = context.mock(VendingMachine.class);
        vendingMachineCollaborator = new VendingMachineCollaborator(vendingMachine);
    }


    @Test
    public void shouldProcessCleanupWhenExitEventReceived() throws Exception {
        final UserAction actions = UserAction.EXIT;


        context.checking(new Expectations() {
                             {
                                 oneOf(vendingMachine).cleanUp();
                                 oneOf(vendingMachine).exit();
                                 allowing(vendingMachine).printStatus();
                             }
                         }

        );

        vendingMachineCollaborator.process(actions);
        context.assertIsSatisfied();

    }

    @Test
    public void shouldProcessAddCoinEvent() throws Exception {
        final UserAction actions = UserAction.ADD_NICKEL;

        context.checking(new Expectations() {
                             {
                                 allowing(vendingMachine).printStatus();
                                 oneOf(vendingMachine).inputCoin((AddCoinEvent) actions.getEventForAction());

                             }
                         }

        );

        vendingMachineCollaborator.process(actions);
        context.assertIsSatisfied();

    }

    @Test
    public void shouldProcessUserCoinReturnEvent() throws Exception {

        final UserAction actions = UserAction.COIN_RETURN;
        final VendingMachineEvent eventForAction = actions.getEventForAction();

        context.checking(new Expectations() {
                             {
                                 oneOf(vendingMachine).returnUserCoins(eventForAction);
                                 allowing(vendingMachine).printStatus();

                             }
                         }

        );

        vendingMachineCollaborator.process(actions);
        context.assertIsSatisfied();
    }

    @Test
    public void shouldProcessSellProductEvent() throws Exception {
      final   UserAction userAction = UserAction.GET_A;



        context.checking(new Expectations() {
                             {
                                 oneOf(vendingMachine).vendProduct((SellProductEvent) userAction.getEventForAction());
                                 allowing(vendingMachine).printStatus();

                             }
                         }

        );

        vendingMachineCollaborator.process(userAction);
        context.assertIsSatisfied();
    }

}
