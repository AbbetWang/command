package org.abbet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.abbet.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.*;

public class OptionParsersTest {

    @Test
    public void should_not_accept_extra_argument_for_single_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
            OptionParsers.unary(0, Integer::parseInt).parse(asList("-p", "8080", "9080"), option("p"));
        });

        assertEquals("p", e.getOption());
    }

    @ParameterizedTest()
    @MethodSource("ListProvider")
    public void should_not_accept_insufficient_argument_for_single_option(List<String> arguments) {
        InsufficientArgumentException e = assertThrows(InsufficientArgumentException.class, () -> {
            OptionParsers.unary(0, Integer::parseInt).parse(arguments, option("p"));
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
    public void should_parse_value_if_flag_present() {
        Object parsed = new Object();
        Function<String, Object> parser = (it) -> parsed;
        Object whatever = new Object();
        Object got = OptionParsers.unary(whatever, parser).parse(asList("-p", "8080"), option("p"));
        assertSame(parsed, got);
    }

    @Test
    public void should_set_default_value_for_single_option() {
        Object defaultValue = new Object();
        Function<String, Object> whatever = (it) -> null;
        assertSame(defaultValue, OptionParsers.unary(defaultValue, whatever).parse(asList(), option("p")));
    }

    @Test
    public void should_throw_illegal_value_exception_if_value_not_parsed() {
        Object whatever = new Object();
        assertThrows(IllegalValueException.class, () -> {
            OptionParsers.unary(whatever, Integer::parseInt).parse(asList("-p", "a"), option("p"));
        });
    }
}