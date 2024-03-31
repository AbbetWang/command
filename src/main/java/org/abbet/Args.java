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
            Parameter parameter = parameters[0];
            Object value = null;
            Option option = parameter.getAnnotation(Option.class);
            List<String> arguments = Arrays.asList(args);
            if(parameter.getType() == boolean.class){
                value = arguments.contains("-" + option.value());
            }
            if(parameter.getType() == int.class){
                int index = arguments.indexOf("-"+option.value());
                value = Integer.parseInt(arguments.get(index+1));
            }
            if(parameter.getType() == String.class){
                int index = arguments.indexOf("-"+option.value());
                value = String.valueOf(arguments.get(index+1));
            }
            return (T) constructor.newInstance(value);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
