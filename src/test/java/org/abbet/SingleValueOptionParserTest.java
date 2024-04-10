package org.abbet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.abbet.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleValueOptionParserTest {

    @Test
    public void should_not_accept_extra_argument_for_single_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            ((SingleValueOptionParser<? extends Serializable>) new SingleValueOptionParser<Serializable>(Integer::parseInt, 0)).parse(asList("-p", "8080", "9080"), option("p"));
        });

        assertEquals("p", e.getOption());
    }

    // TODO: insufficientArguments
    @ParameterizedTest()
    @MethodSource("ListProvider")
    public void should_not_accept_insufficient_argument_for_single_option(List<String> arguments) {
        InsufficientArgumentException e = assertThrows(InsufficientArgumentException.class, () -> {
            new SingleValueOptionParser<>(Integer::parseInt, 0).parse(arguments, option("p"));
        });
        assertEquals("p", e.getOption());
    }

    static Stream ListProvider() {
        return Stream.of(
                asList("-p"),
                asList("-p", "-l")
        );
    }

    @Test
    public void should_set_default_value_for_integer_option() {
        assertEquals(0, new SingleValueOptionParser<Integer>(Integer::parseInt, (Integer) 0).parse(asList(), option("p")));
    }

    @Test
    public void should_set_default_value_for_string_option() {
        assertEquals("", new SingleValueOptionParser<String>(String::valueOf, "").parse(asList(), option("d")));
    }
}
