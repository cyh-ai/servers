package com.integration.server.Helper;

import com.integration.core.util.SpringUtil;

/**
 * 调用getCyhIndByVP来判断是否满足条件，如果满足就进入对应已经注册的bean中实现逻辑
 */
public class CyhHelper {

    public static CyhInd getCyhIndByVP(String code){
        return getCyhIndByCode("VP"+code);
    }



    private static CyhInd getCyhIndByCode(String code){
        try {
            return (CyhInd) SpringUtil.getBean(code);
        }catch (Exception e){
            //防止报错
            return (CyhInd) SpringUtil.getBean("Cyh");
        }
    }


}
