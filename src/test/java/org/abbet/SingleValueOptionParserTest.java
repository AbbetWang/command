package org.abbet;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.abbet.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleValueOptionParserTest {
    // SingleValueOptionParserTest
    // sad path:
    //TODO: - int -p 8080 9080
    @Test
    public void should_not_accept_extra_argument_for_integer_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new SingleValueOptionParser<>(Integer::parseInt).parse(asList("-p", "8080", "9080"), option("p"));
        });

        assertEquals("p", e.getOption());
    }
    //TODO: -string -d /-d /usr/logs /usr/vars
    // default value
    //TODO: -int :0
    //TODO: - string: ""
}
