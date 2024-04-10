package org.abbet;

import java.util.List;

class BooleanOptionParser implements OptionParse<Boolean> {

    @Override
    public Boolean parse(List<String> arguments, Option option) {
        return SingleValueOptionParser.values(arguments, option, 0)
                .map(it -> true).orElse(false);
    }
}
