package com.integration.server.Helper;

/**
 * @author cyh
 * 个性化处理 ，除去接口中的公共接口，可以写一些，单独的方法来实现逻辑
 */
public class CyhInd implements CyhIndInterface {

    protected String cyhA(String name) {
        System.out.println("我叫" + name + "我有自己的方法");
        return "我叫" + name + "我有自己的方法";
    }
}
