package com.integration.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 工具类：实现ApplicationContextAware，注入bean，用于策略模式解决多if判断
 */
@Component
public class SpringUtil implements ApplicationContextAware {


    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (this.applicationContext==null){
            this.applicationContext = applicationContext;
        }
    }

    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    public static Object getBeanOfType(Class clz){
        return applicationContext.getBean(clz);
    }

    public static Map getBeansOfType(Class clz){
        return applicationContext.getBeansOfType(clz);
    }
}
