package org.abbet;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Args {
    public static <T> T parse(Class<T> optionsClass, String... args) {
        try {

            Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];
            Parameter[] parameters = constructor.getParameters();
            Option option = parameters[0].getAnnotation(Option.class);
            List<String> arguments = Arrays.asList(args);
            return (T) constructor.newInstance(arguments.contains("-" + option.value()));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
