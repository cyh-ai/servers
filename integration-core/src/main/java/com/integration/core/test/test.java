package com.integration.core.test;

import com.integration.core.util.DateUtil;
import com.integration.core.util.TimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cyh
 * 测试util工具类中的方法
 */
public class test {

    public static void main(String[] args) throws ParseException {

        String format = DateUtil.normalizeDateStr("1997-05-05");
        System.out.println(format);

        String c = "1997/05/05";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date parse = simpleDateFormat.parse(c);
        Date date = TimeUtils.parseDate(c, "yyyy/MM/dd");
        Date format1 = DateUtil.format(c, "yyyy/MM/dd");
        System.out.println("data:"+date);
        System.out.println("format1:"+format1);


        String datetimepattern = TimeUtils.format(parse, "yyyy/MM/dd");
        System.out.println(parse);

        System.out.println("---------------");
        System.out.println();


        System.out.println("判断年龄是否大于某岁");
        boolean b = TimeUtils.getSpanInDays(date, new Date()) > 18;
        System.out.println(b);
        Date a = DateUtil.format("1992/05/03", "yyyy/MM/dd");
        Long spanInDays = TimeUtils.getSpanInDays(a,date);
        System.out.println(spanInDays);

    }

}
