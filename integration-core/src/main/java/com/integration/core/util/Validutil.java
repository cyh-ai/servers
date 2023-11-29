package com.integration.core.util;

/**
 * @author cyh
 * 校验
 */
public class Validutil {
    /**
     * 手机号校验
     *
     * @param phoneNo
     * @return
     */
    public static boolean isValidMobilePhone(String phoneNo) {
        return StringUtils.isEmpty(phoneNo) ? false : phoneNo.matches("^1[3-9]\\d{9}$");
    }

    /**
     * @description 校验方案名称合法性 仅支持中文，英文，数字，加号命名
     * @author york
     * @date 2021/9/122
     */
    public static boolean isValidOrderGroupName(String orderGroupName) {
        return StringUtils.isNotEmpty(orderGroupName) && orderGroupName.matches("^[A-Za-z0-9\u4e00-\u9fa5+()（）]+$");
    }

}
