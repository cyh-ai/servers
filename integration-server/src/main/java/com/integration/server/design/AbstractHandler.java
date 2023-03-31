package com.integration.server.design;

import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractHandler implements InitializingBean {

    public void AA(String nikeName) {
        throw new UnsupportedOperationException();
    }


    public void BB(String nikeName) {
        System.out.println("我没有值");;
    }


}
