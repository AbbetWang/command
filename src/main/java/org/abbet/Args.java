package org.abbet;

import java.lang.reflect.InvocationTargetException;

public class Args {
    public static <T> T parse(Class<?> T, Object ...args) {
        try {
            return (T) T.getConstructors()[0].newInstance(args);
        }catch (Exception err){
            throw new RuntimeException(err);
        }
    }
}
