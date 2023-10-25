package com.integration.core.util;

import java.util.Collection;

/**
 * @author cyh
 * 集合空判断
 */
public class CollectionUtils {

    public static boolean isNotEmpty(Iterable<?> iterable) {
        return IterUtil.isNotEmpty(iterable);
    }



    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }


    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
