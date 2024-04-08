package org.abbet;

import java.util.List;

class IntOptionParser implements OptionParse {

    @Override
    public Object parse(List<String> arguments, Option option) {
        int index = arguments.indexOf("-" + option.value());
        return Integer.parseInt(arguments.get(index + 1));
    }
}
