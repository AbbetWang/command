package org.abbet;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.abbet.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleValueOptionParserTest {

    @Test
    public void should_not_accept_extra_argument_for_integer_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            ((SingleValueOptionParser<? extends Serializable>) new SingleValueOptionParser<Serializable>(Integer::parseInt, 0)).parse(asList("-p", "8080", "9080"), option("p"));
        });

        assertEquals("p", e.getOption());
    }

    @Test
    public void should_set_default_value_for_integer_option() {
        assertEquals(0, new SingleValueOptionParser<Integer>(Integer::parseInt, (Integer) 0).parse(asList(), option("p")));
    }

    // default value
    //TODO: - string: ""
    @Test
    public void should_set_default_value_for_string_option() {
        assertEquals("", new SingleValueOptionParser<String>(String::valueOf, "").parse(asList(), option("d")));
    }
}
