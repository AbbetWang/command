package org.abbet;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

class SingleValueOptionParser<T> implements OptionParse<T> {

    Function<String, T> valueParser;
    T defaultValue;

    public SingleValueOptionParser(T defaultValue, Function<String, T> valueParser) {
        this.valueParser = valueParser;
        this.defaultValue = defaultValue;
    }

    @Override
    public T parse(List<String> arguments, Option option) {
        int expectedSize = 1;
        int index = arguments.indexOf("-" + option.value());
        if (index == -1) {
            return defaultValue;
        }
        List<String> values = values(arguments, index);
        if (values.size() < expectedSize) {
            throw new InsufficientArgumentException(option.value());
        }
        if (values.size() > expectedSize) {
            throw new TooManyArgumentsException(option.value());
        }
        return valueParser.apply(values.get(0));
    }

    static List<String> values(List<String> arguments, int index) {
        int followingFlagIndex = IntStream.range(index + 1, arguments.size())
                .filter(it -> arguments.get(it).startsWith("-"))
                .findFirst()
                .orElse(arguments.size());
        List<String> values = arguments.subList(index + 1, followingFlagIndex);
        return values;
    }

}
