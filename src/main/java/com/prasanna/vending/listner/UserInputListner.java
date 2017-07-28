package com.prasanna.vending.listner;


import com.prasanna.vending.domain.UserAction;
import com.prasanna.vending.listner.ActionListner;
import com.prasanna.vending.publisher.UserActionPublisher;
import com.prasanna.vending.parser.InputParser;

import java.util.List;

public class UserInputListner implements ActionListner {

    InputParser parser;
    UserActionPublisher publisher;

    public UserInputListner(InputParser parser, UserActionPublisher publisher) {
        this.parser = parser;
        this.publisher = publisher;
    }

    public void processInput(String input) {
        List<UserAction> userActions = parser.parseInput(input);
        for (UserAction action : userActions) {
            publisher.broadCast(action);
        }
    }
}
