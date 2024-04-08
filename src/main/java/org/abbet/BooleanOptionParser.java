package org.abbet;

import java.util.List;

class BooleanOptionParser implements OptionParse {

    @Override
    public Object parse(List<String> arguments, Option option) {
        return arguments.contains("-" + option.value());
    }
}
