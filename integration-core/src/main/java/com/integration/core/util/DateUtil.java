package com.integration.core.util;


import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author cyh
 * 待补充 todo
 */
public class DateUtil {

    public static final String BIRTH_PATTERN = "yyyy年MM月dd日";

    public static final DateTimeFormatter FORMATTER_FULL_DATE_NO_PATTERN = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public static final String FORMATTER_AI_CERT_DATE_NO_PATTERN = "yyyy/MM/dd";

    public static final DateTimeFormatter FORMATTER_YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final String DATETIMEPATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final String DATETIME_SLASH = "yyyy/MM/dd HH:mm:ss";

    public static final String PLM_TO_DATE = "yyyy/MM/ddHH:mm:ss";

    public static final String DATE_PATTERN_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_PATTERN = "yyyy/MM/dd";
    public static final String HH_MM_SS_PATTERN = "HH:mm:ss";

    public static final DateTimeFormatter DTF_DATE = DateTimeFormatter.ofPattern("yyyyMMddHH:mm:ss");
    public static final DateTimeFormatter DATE_DTF = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 获取当前时间,消除毫秒
     *
     * @return
     */
    public static LocalDateTime getNowTime() {
        return LocalDateTime.now().withNano(0);
    }

    /**
     * 获取时间间隔(秒)
     *
     * @param start
     * @param end
     * @return
     */
    public static long getBetweenTimes(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        long milli = duration.toMillis();
        return milli / 1000;
    }

    /**
     * 日期字符串转换为yyyyMMdd
     *
     * @param dateStr
     * @return
     */
    public static String normalizeDateStr(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return dateStr;
        } else {
            return dateStr.replaceAll("[\\/.\\-]", "");
        }
    }

    /**
     * 获取int年后的日期, 可以为负数
     */
    public static String getSubYear(Date date, int year, String BIRTH_PATTERN) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        //return com.cpic.caf.compon.tech.utils.date.DateUtil.format(calendar.getTime(), BIRTH_PATTERN);
        return TimeUtils.format(calendar.getTime(), BIRTH_PATTERN);
    }


    /**
     * 获取int年之前的日期-1
     */
    public static String getBeforeYear(int year, String BIRTH_PATTERN) {
        Date date = new Date();//获取当前时间
        return getSubYear(date, year, BIRTH_PATTERN);
    }

    /**
     * 获取int年之前的日期-1天
     */
    public static String getBeforeYearTimes(int year, int day) {
        Date date = new Date();//获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);//当前时间减去一年，即一年前的时间
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.getTime();//获取一年前的时间
        return TimeUtils.format(calendar.getTime(), BIRTH_PATTERN);
    }


    /**
     * 判断两个日期月日是否一致
     *
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return boolean
     */
    public static boolean isSameDateOfMMdd(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        return calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DATE) == calendar2.get(Calendar.DATE);
    }

    /**
     * 获取当天零时
     *
     * @return
     */
    public static Date getZeroTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定时间24点
     *
     * @param time
     * @return
     */
    public static Date getSpecifiedTime(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * @param aiCertDateStr
     * @description: 将ai返回的证件有效期字符串yyyyMMdd格式化yyyy/MM/dd
     * @author ChenYFan
     * @date: 2021/8/26 18:01
     */
//    public static String formatAiCertDate(String aiCertDateStr) {
//        try {
//
//            //把字符串转成对应时间
//            Date aiCertDate = DateUtil.parse(aiCertDateStr);
//            //
//            return TimeUtils.format(aiCertDate, FORMATTER_AI_CERT_DATE_NO_PATTERN);
//        } catch (Exception e) {
//            return aiCertDateStr;
//        }
//    }


    /**
     * 是否为成年人 true为成年 false非成年
     *
     * @param birthday 出生日期
     * @return String
     */
    public static Boolean isAdult(Date birthday) {
        Date now = new Date();
        long age = TimeUtils.getSpanInYears(birthday, now);
        return age >= 18 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 获取int年后的日期, 可以为负数
     * 返回 yyyy-MM-dd HH:mm:ss
     */
    public static String getSubMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return TimeUtils.format(calendar.getTime(), DATETIMEPATTERN);
    }

    /**
     * 获取int分钟后的日期
     */
    public static Date getSubMinuteDate(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 获取int天后的日期
     */
    public static Date getSubDayDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }


    /**
     * 获取一个月有几天
     *
     * @param year
     * @param month
     * @return
     */
    public static int monthDays(String year, String month) {
        month = month.replace("0", "");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }

    public static Date format(String date) {
        Date result = null;
        try {
            result = TimeUtils.parseDate(date, FORMATTER_AI_CERT_DATE_NO_PATTERN);
        } catch (ParseException e) {
            return null;
        }
        return result;
    }

    public static Date getPolicyEndDate(Date validDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(validDate);
        cal.add(Calendar.YEAR, 1); //增加一年
        cal.add(Calendar.DATE, -1); //减1天
        return cal.getTime();
    }
}