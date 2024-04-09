package org.abbet;

import java.util.List;
import java.util.function.Function;

class SingleValueOptionParser<T> implements OptionParse<T> {

    Function<String, T> valueParser;

    public SingleValueOptionParser(Function<String, T> valueParser) {
        this.valueParser = valueParser;
    }

    @Override
    public T parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        if (index == -1) {
            return valueParser.apply("0");
        }
        if (index + 2 < arguments.size() && !arguments.get(index + 2).startsWith("-")) {
            throw new TooManyArgumentsException(option.value());
        }
        String value = arguments.get(index + 1);
        return valueParser.apply(value);
    }

}
