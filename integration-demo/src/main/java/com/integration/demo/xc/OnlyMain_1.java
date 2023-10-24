package com.integration.demo.xc;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author cyh
 * 练习线程系统管理接口获取方法
 */
public class OnlyMain_1 {

    public static void main(String[] args) {
        //获取Java虚拟机线程系统管理接口
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //仅获取线程和线程的堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        //遍历线程信息获取线程id和名称
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(threadInfo.getThreadId());
            System.out.println(threadInfo.getThreadName());
        }
    }
}
