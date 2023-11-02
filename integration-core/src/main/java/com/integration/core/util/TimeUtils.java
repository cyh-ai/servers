package com.integration.core.util;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * @author cyh
 * 时间转换工具类，待补充...
 */
public class TimeUtils {
    private TimeUtils() {
    }

    public static String format(long millis, String pattern) {
        return DateFormatUtils.format(millis, pattern);
    }

    public static String format(long millis, String pattern, Locale locale) {
        return DateFormatUtils.format(millis, pattern, locale);
    }

    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    public static String format(Date date, String pattern, Locale locale) {
        return DateFormatUtils.format(date, pattern, locale);
    }

    public static String format(Calendar calendar, String pattern) {
        return DateFormatUtils.format(calendar, pattern);
    }

    public static String format(Calendar calendar, String pattern, Locale locale) {
        return DateFormatUtils.format(calendar, pattern, locale);
    }

    public static Date parseDate(String string, String... pattern) throws ParseException {
        return DateUtils.parseDate(string, pattern);
    }

    public static Date parseDate(String date, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(date);
    }

    public static Calendar parseCalendar(String string, String... pattern) throws ParseException {
        return DateUtils.toCalendar(DateUtils.parseDate(string, pattern));
    }

    public static Calendar toCalendar(Date date) {
        return DateUtils.toCalendar(date);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        return DateUtils.isSameDay(date1, date2);
    }

    public static boolean isSameDay(Calendar calendar1, Calendar calendar2) {
        return DateUtils.isSameDay(calendar1, calendar2);
    }

    public static boolean isSameInstant(Date date1, Date date2) {
        return DateUtils.isSameDay(date1, date2);
    }

    public static boolean isSameInstant(Calendar calendar1, Calendar calendar2) {
        return DateUtils.isSameDay(calendar1, calendar2);
    }

    public static Date addYears(Date date, int amount) {
        return DateUtils.addYears(date, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return DateUtils.addMonths(date, amount);
    }

    public static Date addDays(Date date, int amount) {
        return DateUtils.addDays(date, amount);
    }

    public static Date addWeeks(Date date, int amount) {
        return DateUtils.addWeeks(date, amount);
    }

    public static Date addHours(Date date, int amount) {
        return DateUtils.addHours(date, amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return DateUtils.addMinutes(date, amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return DateUtils.addSeconds(date, amount);
    }

    public static Date addMilliseconds(Date date, int amount) {
        return DateUtils.addMilliseconds(date, amount);
    }

    public static Date set(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, 0, 0, 0);
        c.set(14, 0);
        return c.getTime();
    }

    public static Date set(Date date, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, getHour(date), getMinute(date), getSecond(date));
        c.set(14, getMillisecond(date));
        return c.getTime();
    }

    public static Date set(int year, int month, int day, int hour, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute, second);
        c.set(14, 0);
        return c.getTime();
    }

    public static Date set(Date date, int year, int month, int day, int hour, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute, second);
        c.set(14, getMillisecond(date));
        return c.getTime();
    }

    public static Date set(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hour, minute, second);
        c.set(14, millisecond);
        return c.getTime();
    }

    public static Date setYear(Date date, int amount) {
        return DateUtils.setYears(date, amount);
    }

    public static Date setMonth(Date date, int amount) {
        return DateUtils.setMonths(date, amount);
    }

    public static Date setDay(Date date, int amount) {
        return DateUtils.setDays(date, amount);
    }

    public static Date setHour(Date date, int amount) {
        return DateUtils.setHours(date, amount);
    }

    public static Date setMinute(Date date, int amount) {
        return DateUtils.setMinutes(date, amount);
    }

    public static Date setSecond(Date date, int amount) {
        return DateUtils.setSeconds(date, amount);
    }

    public static Date setMillisecond(Date date, int amount) {
        return DateUtils.setMilliseconds(date, amount);
    }

    public static Date round(Date date, int field) {
        return DateUtils.round(date, field);
    }

    public static Calendar round(Calendar calendar, int field) {
        return DateUtils.round(calendar, field);
    }

    public static Date truncate(Date date, int field) {
        return DateUtils.truncate(date, field);
    }

    public static Calendar truncate(Calendar calendar, int field) {
        return DateUtils.truncate(calendar, field);
    }

    public static Date ceiling(Date date, int field) {
        return DateUtils.ceiling(date, field);
    }

    public static Calendar ceiling(Calendar calendar, int field) {
        return DateUtils.ceiling(calendar, field);
    }

    public static Long getFragmentInDays(Date date, int fragment) {
        return DateUtils.getFragmentInDays(date, fragment);
    }

    public static Long getFragmentInHours(Date date, int fragment) {
        return DateUtils.getFragmentInHours(date, fragment);
    }

    public static Long getFragmentInMinutes(Date date, int fragment) {
        return DateUtils.getFragmentInMinutes(date, fragment);
    }

    public static Long getFragmentInSeconds(Date date, int fragment) {
        return DateUtils.getFragmentInSeconds(date, fragment);
    }

    public static Long getFragmentInMilliseconds(Date date, int fragment) {
        return DateUtils.getFragmentInMilliseconds(date, fragment);
    }

    public static Long getFragmentInDays(Calendar calendar, int fragment) {
        return DateUtils.getFragmentInDays(calendar, fragment);
    }

    public static Long getFragmentInHours(Calendar calendar, int fragment) {
        return DateUtils.getFragmentInHours(calendar, fragment);
    }

    public static Long getFragmentInMinutes(Calendar calendar, int fragment) {
        return DateUtils.getFragmentInMinutes(calendar, fragment);
    }

    public static Long getFragmentInSeconds(Calendar calendar, int fragment) {
        return DateUtils.getFragmentInSeconds(calendar, fragment);
    }

    public static Long getFragmentInMilliseconds(Calendar calendar, int fragment) {
        return DateUtils.getFragmentInMilliseconds(calendar, fragment);
    }

    public static int getYear(Date date) {
        return toCalendar(date).get(1);
    }

    public static int getMonth(Date date) {
        return toCalendar(date).get(2);
    }

    public static int getWeekOfYear(Date date) {
        return toCalendar(date).get(3);
    }

    public static int getWeekOfMonth(Date date) {
        return toCalendar(date).get(4);
    }

    public static int getDayOfMonth(Date date) {
        return toCalendar(date).get(5);
    }

    public static int getDayOfYear(Date date) {
        return toCalendar(date).get(6);
    }

    public static int getDayOfWeek(Date date) {
        return toCalendar(date).get(7);
    }

    public static int getDayOfWeekInMonth(Date date) {
        return toCalendar(date).get(8);
    }

    public static int getHour(Date date) {
        return toCalendar(date).get(10);
    }

    public static int getHourOfDay(Date date) {
        return toCalendar(date).get(11);
    }

    public static int getMinute(Date date) {
        return toCalendar(date).get(12);
    }

    public static int getSecond(Date date) {
        return toCalendar(date).get(13);
    }

    public static int getMillisecond(Date date) {
        return toCalendar(date).get(14);
    }

    public static Long getSpanInYears(Date date1, Date date2) {
        return getSpanInYears(toCalendar(date1), toCalendar(date2));
    }

    public static Long getSpanInMonths(Date date1, Date date2) {
        return getSpanInMonths(toCalendar(date1), toCalendar(date2));
    }

    public static Long getSpanInDays(Date date1, Date date2) {
        return getSpanInDays(toCalendar(date1), toCalendar(date2));
    }

    public static Long getSpanInHours(Date date1, Date date2) {
        return getSpanInHours(toCalendar(date1), toCalendar(date2));
    }

    public static Long getSpanInMinutes(Date date1, Date date2) {
        return getSpanInMinutes(toCalendar(date1), toCalendar(date2));
    }

    public static Long getSpanInSeconds(Date date1, Date date2) {
        return getSpanInSeconds(toCalendar(date1), toCalendar(date2));
    }

    public static Long getSpanInMilliseconds(Date date1, Date date2) {
        return getSpanInMilliseconds(toCalendar(date1), toCalendar(date2));
    }

    public static Long getSpanInYears(Calendar calendar1, Calendar calendar2) {
        Long y = (long)(calendar2.get(1) - calendar1.get(1));
        return calendar2.get(2) <= calendar1.get(2) && (calendar2.get(2) != calendar1.get(2) || calendar2.get(5) < calendar1.get(5)) ? y - 1L : y;
    }

    public static Long getSpanInMonths(Calendar calendar1, Calendar calendar2) {
        Long m = (long)((calendar2.get(1) - calendar1.get(1)) * 12 + (calendar2.get(2) - calendar1.get(2)));
        return calendar2.get(5) >= calendar1.get(5) ? m : m - 1L;
    }

    public static Long getSpanInDays(Calendar calendar1, Calendar calendar2) {
        calendar1.set(11, 0);
        calendar2.set(11, 0);
        return getSpanInHours(calendar1, calendar2) / 24L;
    }

    public static Long getSpanInHours(Calendar calendar1, Calendar calendar2) {
        calendar1.set(12, 0);
        calendar2.set(12, 0);
        return getSpanInMinutes(calendar1, calendar2) / 60L;
    }

    public static Long getSpanInMinutes(Calendar calendar1, Calendar calendar2) {
        calendar1.set(13, 0);
        calendar2.set(13, 0);
        return getSpanInSeconds(calendar1, calendar2) / 60L;
    }

    public static Long getSpanInSeconds(Calendar calendar1, Calendar calendar2) {
        calendar1.set(14, 0);
        calendar2.set(14, 0);
        return getSpanInMilliseconds(calendar1, calendar2) / 1000L;
    }

    public static Long getSpanInMilliseconds(Calendar calendar1, Calendar calendar2) {
        return calendar2.getTimeInMillis() - calendar1.getTimeInMillis();
    }

    public static SimpleDateFormat getDateFormat(String partten) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(partten);
        return dateFormat;
    }
}
