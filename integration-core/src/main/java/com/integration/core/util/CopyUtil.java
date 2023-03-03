package com.integration.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import sun.reflect.misc.ReflectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CopyUtil {

    private final static Logger logger = LoggerFactory.getLogger(CopyUtil.class);

    public final static String TYPE_SPLIT = "\\|";

    public static <T, R> R copyTo(T from, R to){
        if(from != null && to != null){
            BeanUtils.copyProperties(from, to);
        }
        return to;
    }

    public static <T, R> R convert(T from, Class<R> toClz)  {
        if(from != null && toClz != null){
            R to = null;
            try {
                to = (R) ReflectUtil.newInstance(toClz);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return copyTo(from, to);
        }
        return null;
    }

    public static <T, R> R convert(T from, Function<T, R> converter){
        if(from != null && converter != null){
            R to = converter.apply(from);
            return copyTo(from, to);
        }
        return null;
    }

    public static <T,R> List<R> convertList(List<T> list, Function<T, R> mapper) {
        if (list == null || mapper == null) {
            return new ArrayList<>();
        }
        return list.stream().map(mapper).collect(Collectors.toList());
    }

}
