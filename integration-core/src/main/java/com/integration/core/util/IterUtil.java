package com.integration.core.util;


import java.util.Iterator;

public class IterUtil {

    public static boolean isNotEmpty(Iterable<?> iterator) {
        return null != iterator && isNotEmpty(iterator.iterator());
    }


    public static boolean isNotEmpty(Iterator<?> iterator) {
        return null != iterator && iterator.hasNext();
    }
}
