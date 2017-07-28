package com.prasanna.vending.domain;

import com.prasanna.vending.events.VendingMachineEvent;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;


/**
 * Created by pgopal on 26/07/2017.
 */
public class UserActionsTest {


    @Test(expected = IllegalArgumentException.class)
    public void testInvalidName() throws Exception {
        UserAction test = UserAction.valueOf("TEST");
    }

    @Test
    public void testValidAction() throws Exception {
        UserAction dime = UserAction.getUserActionFor(UserAction.ADD_DIME.getCode());
        MatcherAssert.assertThat(dime, is(UserAction.ADD_DIME));
    }

    @Test
    public void testAllActionHaveValidEvent() throws Exception {

        UserAction[] values = UserAction.values();

        for (UserAction userAction : values) {
            VendingMachineEvent eventForAction = userAction.getEventForAction();
            MatcherAssert.assertThat("Action is not null", eventForAction, is(notNullValue()));
        }
    }
}