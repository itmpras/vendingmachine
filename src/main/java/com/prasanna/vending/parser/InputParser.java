package com.prasanna.vending.parser;

import com.prasanna.vending.domain.UserAction;

import java.util.List;


public interface InputParser {
    List<UserAction> parseInput(String s);
}
