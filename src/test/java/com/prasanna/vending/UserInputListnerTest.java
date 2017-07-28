package com.prasanna.vending;


import com.prasanna.vending.domain.UserAction;
import com.prasanna.vending.listner.UserInputListner;
import com.prasanna.vending.publisher.UserActionPublisher;
import com.prasanna.vending.parser.InputParser;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


public class UserInputListnerTest {

    UserInputListner inputListner;
    Mockery context = new Mockery();

    private InputParser parser;
    private UserActionPublisher publisher;


    @Before
    public void setUp() throws Exception {
        parser = context.mock(InputParser.class);
        publisher = context.mock(UserActionPublisher.class);
        inputListner = new UserInputListner(parser, publisher);
    }

    @Test
    public void shouldPassUserActionsToInputListner() throws Exception {
        final String input = "D";
        final UserAction doller = UserAction.ADD_DOLLER;

        context.checking(new Expectations() {{
            oneOf(parser).parseInput(input);
            will(returnValue(new ArrayList() {
                {
                    add(doller);
                }
            }));
            oneOf(publisher).broadCast(doller);
        }});

        inputListner.processInput(input);
        context.assertIsSatisfied();
    }
}