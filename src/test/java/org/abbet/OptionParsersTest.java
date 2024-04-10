package org.abbet;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

public class OptionParsersTest {

    @Nested
    class UnaryOptionParser {

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
            Function<String, Object> parser = (it) -> {
                throw new RuntimeException();
            };
            IllegalValueException e = assertThrows(IllegalValueException.class, () -> {
                OptionParsers.unary(whatever, parser).parse(asList("-p", "8080"), option("p"));
            });
            assertEquals("p", e.getOption());
            assertEquals("8080", e.getValue());
        }
    }

    @Nested
    class BooleanOptionParser {
        @Test
        public void should_not_accept_extra_argument_for_boolean_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
                OptionParsers.bool().parse(asList("-l", "t"), option("l"));
            });

            assertEquals("l", e.getOption());
        }

        @Test
        public void should_set_default_value_to_false_if_option_not_present() {
            assertFalse(OptionParsers.bool().parse(asList(), option("l")));
        }

        @Test
        public void should_set_boolean_option_to_true_if_flag_present() {
            assertTrue(OptionParsers.bool().parse(asList("-l"), option("l")));
        }


    }

    @Nested
    class ListOptionParser {
        @Test
        public void should_parse_list_value() {
            assertArrayEquals(new String[]{"this", "is"}, OptionParsers.list(String[]::new, String::valueOf).parse(asList("-g", "this", "is"), option("g")));

        }

        @Test
        public void should_set_default_value_to_emptyArray_as_default_value() {
            String[] value = OptionParsers.list(String[]::new, String::valueOf).parse(asList(), option("g"));
            assertEquals(0, value.length);
        }


        @Test
        public void should_throw_illegal_value_exception_if_value_can_not_parsed() {
            assertThrows(IllegalValueException.class, () -> {
                OptionParsers.list(Integer[]::new, Integer::parseInt).parse(asList("-d", "a"), option("d"));
            });
        }
    }

    static Option option(String value) {
        return new Option() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return value;
            }
        };
    }
}
