package com.integration.core.anno;

import javax.xml.bind.Element;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author cyh
 * 自定义导出模板注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelExport {

    /**
     * 字段名称
     */
    String value();

    /**
     * 导出映射，格式：0未知 1男 2女
     */
    String kv() default "";

    /**
     * 是否为必填字段
     */
    boolean required() default false;

    /**
     * 最大长度
     */
    int maxLength() default 255;

    /**
     * 导入唯一性验证 多个字段则取联合验证
     */
    boolean unique() default false;

    /**
     * 导出排序先后：数字越小越靠前
     */
    int sort() default 0;

    /**
     * 导出模板实例值
     */
    String example() default "";
}
