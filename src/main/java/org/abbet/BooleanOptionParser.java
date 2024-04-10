package org.abbet;

import java.util.List;

class BooleanOptionParser implements OptionParse<Boolean> {

    @Override
    public Boolean parse(List<String> arguments, Option option) {
        int expectedSize = 0;
        int index = arguments.indexOf("-" + option.value());
        if (index == -1) {
            return false;
        }
        List<String> values = SingleValueOptionParser.values(arguments, index);
        if (values.size() < expectedSize) {
            throw new InsufficientArgumentException(option.value());
        }
        if (values.size() > expectedSize) {
            throw new TooManyArgumentsException(option.value());
        }
        return true;
    }
}
