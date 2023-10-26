package com.integration.server.Helper;

/**
 * @author cyh
 * 个性化处理,写一些公共处理方法
 */
public interface CyhIndInterface {

    default void cyh(String name) {
        System.out.println("我叫" + name);
    }
}
