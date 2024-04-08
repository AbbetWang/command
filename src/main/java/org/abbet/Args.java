package org.abbet;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {
    public static <T> T parse(Class<T> optionsClass, String... args) {
        try {
            List<String> arguments = Arrays.asList(args);
            Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];
            Object[] values = Arrays.stream(constructor.getParameters()).map(it -> parseOption(it, arguments)).toArray();
            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object parseOption(Parameter parameter, List<String> arguments) {
        Option option = parameter.getAnnotation(Option.class);

        return getOptionParse(parameter.getType()).parse(arguments, option);
    }

    private static OptionParse getOptionParse(Class<?> type) {
        OptionParse parser = null;

        if (type == boolean.class) {
            parser = new BooleanOptionParser();
        }
        if (type == int.class) {
            parser = new IntOptionParser();
        }
        if (type == String.class) {
            parser = new StringOptionParser();
        }
        return parser;
    }

    interface OptionParse {
        Object parse(List<String> arguments, Option option);
    }

    static class StringOptionParser implements OptionParse {

        @Override
        public Object parse(List<String> arguments, Option option) {
            int index = arguments.indexOf("-" + option.value());
            return String.valueOf(arguments.get(index + 1));
        }
    }

    static class IntOptionParser implements OptionParse {

        @Override
        public Object parse(List<String> arguments, Option option) {
            int index = arguments.indexOf("-" + option.value());
            return Integer.parseInt(arguments.get(index + 1));
        }
    }

    static class BooleanOptionParser implements OptionParse {

        @Override
        public Object parse(List<String> arguments, Option option) {
            return arguments.contains("-" + option.value());
        }
    }
}
