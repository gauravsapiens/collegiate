package com.collegiate.util;

/**
 * Created by gauravarora on 10/10/15.
 */
public class ClassUtils {

    public static boolean isSubclass(Class parentClass, Class clazz){
        if(parentClass == null || clazz == null){
            return false;
        }
        return clazz.isAssignableFrom(parentClass);
    }
}
