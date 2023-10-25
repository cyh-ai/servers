package com.integration.core.service;

/**
 * @author cyh
 * redis锁 各个方法可用于对应场景
 */
public interface RedisLock {

    /**
     * 是否已调用成功
     *
     * @return 是否
     */
    boolean successGet();

    /**
     * 释放锁
     */
    void releaseLock();

    /**
     * 续期，一个周期
     */
    default void lease() {
        lease(0);
    }

    /**
     * 指定时间
     *
     * @param leaseSec
     */
    void lease(long leaseSec);
}
