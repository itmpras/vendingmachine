package com.prasanna.vending.parser;

import com.prasanna.vending.domain.UserAction;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class CommaBasedStringTokenParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldHandleNullStringInput() throws Exception {
        InputParser machine = new CommaBasedStringTokenParser();
        machine.parseInput(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void shouldReturnEmptyListIfUnableToParse() throws Exception {
        InputParser machine = new CommaBasedStringTokenParser();
        List<UserAction> inputs = machine.parseInput("INVALID_INPUT");
        assertThat("Parsed Input is not null", inputs, is(notNullValue()));
        assertThat("Parsed Input has size 1", inputs, hasSize(0));
    }

    @Test
    public void shouldParseUserInput() throws Exception {
        InputParser machine = new CommaBasedStringTokenParser();
        List<UserAction> inputs = machine.parseInput("D,D,GET_A");
        assertThat("Parsed Input is not null", inputs, is(notNullValue()));
        assertThat("Parsed Input has size 3", inputs, hasSize(3));
    }

    @Test
    public void shouldParseForOneUserInput() throws Exception {
        InputParser machine = new CommaBasedStringTokenParser();
        List<UserAction> inputs = machine.parseInput("D");
        assertThat("Parsed Input is not null", inputs, is(notNullValue()));
        assertThat("Parsed Input has size 3", inputs, hasSize(1));
    }

}