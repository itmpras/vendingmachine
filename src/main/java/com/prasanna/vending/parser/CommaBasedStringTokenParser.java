package com.prasanna.vending.parser;

import com.prasanna.vending.domain.UserAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class CommaBasedStringTokenParser implements InputParser {
    static final Logger LOG = LoggerFactory.getLogger(CommaBasedStringTokenParser.class);
    public static final String DELIM = ",";

    public CommaBasedStringTokenParser() {
    }

    public List<UserAction> parseInput(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Invalid input to process");
        }
        List<UserAction> tokens = parseTokens(s);
        return tokens;

    }

    public List<UserAction> parseTokens(String s) {
        StringTokenizer tokenizer = new StringTokenizer(s, DELIM);
        List<UserAction> tokens = new LinkedList<UserAction>();
        while (tokenizer.hasMoreTokens()) {
            try {
                tokens.add(UserAction.getUserActionFor(tokenizer.nextToken()));
            } catch (IllegalArgumentException e) {
                LOG.error("Invalid user input : "+ s);
                throw e;
            }
        }
        return tokens;
    }
}