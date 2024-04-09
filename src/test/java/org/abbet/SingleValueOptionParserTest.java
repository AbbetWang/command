package org.abbet;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.abbet.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleValueOptionParserTest {

    @Test
    public void should_not_accept_extra_argument_for_integer_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new SingleValueOptionParser<>(Integer::parseInt, "0").parse(asList("-p", "8080", "9080"), option("p"));
        });

        assertEquals("p", e.getOption());
    }

    @Test
    public void should_set_default_value_for_integer_option() {
        Integer got = new SingleValueOptionParser<>(Integer::parseInt, "0").parse(asList(), option("p"));
        assertEquals(0, got);
    }

    // default value
    //TODO: - string: ""
    @Test
    public void should_set_default_value_for_string_option() {
        String got = new SingleValueOptionParser<>(String::valueOf, "").parse(asList(), option("d"));
        assertEquals("", got);
    }
}
