package org.abbet;

import java.util.List;

interface OptionParse<T> {
    T parse(List<String> arguments, Option option);
}
