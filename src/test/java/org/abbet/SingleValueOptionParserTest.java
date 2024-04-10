package org.abbet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.abbet.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.*;

public class SingleValueOptionParserTest {

    @Test
    public void should_not_accept_extra_argument_for_single_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            new SingleValueOptionParser<Serializable>(Integer::parseInt, 0).parse(asList("-p", "8080", "9080"), option("p"));
        });

        assertEquals("p", e.getOption());
    }

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
    public void should_set_default_value_for_single_option() {
        Object whatever = new Object();
        Function<String, Object> parser = (it) -> null;
        Object got = new SingleValueOptionParser<>(parser, whatever).parse(asList(), option("p"));
        assertSame(whatever, got);
    }
}
