package com.integration.server.Helper;


import org.springframework.stereotype.Component;

@Component
public class VP123 extends CyhInd {

    @Override
    public void cyh(String name) {
        cyhA(name);
    }

    @Override
    protected String cyhA(String name) {
        return super.cyhA(name);
    }


    public void cyhaa(){

    }
}
