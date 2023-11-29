package com.integration.core.util;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cyh
 * 校验
 */
public class RegUtil {

    private static Logger logger = LoggerFactory.getLogger(RegUtil.class);

    public static final String MOBILE_REG = "^1[3-9]\\d{9}$";
    public static final String EMAIL_REC = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public static final String ZIP_REG = "^[1-9]\\d{5}$";
    public static final Pattern emailPattern = Pattern.compile(EMAIL_REC);
    public static final Pattern mobilePattern = Pattern.compile(MOBILE_REG);
    public static final Pattern zipPattern = Pattern.compile(ZIP_REG);

    /**
     * 校验手机号是否正确
     *
     * @param str
     * @return
     */
    public static boolean isMobilePhone(String str) {
        Matcher m = mobilePattern.matcher(str);
        return m.matches();
    }

    /**
     * 校验邮箱是否正确
     *
     * @param str
     * @return
     */
    public static boolean isEffectiveEmail(String str) {
        Matcher m = emailPattern.matcher(str);
        return m.matches();
    }

    /**
     * 校验邮编是否正确
     *
     * @param str
     * @return
     */
    public static boolean isEffectiveZipCode(String str) {
        Matcher m = zipPattern.matcher(str);
        return m.matches();
    }


    public static void main(String[] args) {
        boolean mobilePhone = isMobilePhone("15993219701");
        System.out.println(mobilePhone);
        boolean effectiveEmail = isEffectiveEmail("cyh970511@163.com");
        System.out.println(effectiveEmail);
    }
}
