package com.integration.core.Enum;

import ch.qos.logback.core.util.TimeUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author cyh
 * redis枚举类
 * TimeUnit:DAYS 天,HOURS 小时,MINUTES 分钟,SECONDS 秒,MILLISECONDS 毫秒
 */
public enum RedisKeyEnum {

    /**
     * 用户信息
     */
    USER_INFO(Business.USER, "userInfo", 1, TimeUnit.DAYS),

    CYH(Business.A, "b", 1, TimeUnit.HOURS),

    NAME(Business.A, "b", 1, TimeUnit.HOURS),
    MAP(Business.A, "map", 1, TimeUnit.HOURS),

    DOUBLE(Business.A,"double",1,TimeUnit.DAYS);


    /**
     * 业务大类
     */
    private String business;

    /**
     * 业务子类
     */
    private String subBusiness;

    /**
     * 时长
     */
    private long time;

    /**
     * 时长单位
     */
    private TimeUnit timeUnit;

    RedisKeyEnum(String business, String subBusiness, long time, TimeUnit timeUnit) {
        this.business = business;
        this.subBusiness = subBusiness;
        this.time = time;
        this.timeUnit = timeUnit;
    }

    public String getBusiness() {
        return business;
    }

    public String getSubBusiness() {
        return subBusiness;
    }

    public long getTime() {
        return time;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    private class Business {
        public static final String BASIS = "basis";
        public static final String USER = "user";
        public static final String ORDER = "order";
        public static final String A = "A";
    }
}
