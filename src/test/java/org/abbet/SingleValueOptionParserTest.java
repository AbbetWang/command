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

    private Object whatever = new Object();
    private Function<String, Object> parser = (it) -> null;

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
    public void should_set_int_option_if_use_p() {
        ArgTest.IntegerOption option = Args.parse(ArgTest.IntegerOption.class, "-p", "8080");
        assertEquals(8080, option.port());
    }

    @Test
    public void should_set_default_value_for_single_option() {
        assertSame(whatever, new SingleValueOptionParser<>(parser, whatever).parse(asList(), option("p")));
    }
}
