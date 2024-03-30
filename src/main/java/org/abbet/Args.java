package org.abbet;

import java.lang.reflect.InvocationTargetException;

public class Args {
    public static <T> T parse(Class<T> optionsClass, String ...args) {
        try {
            return (T) optionsClass.getDeclaredConstructors()[0].newInstance(true);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
