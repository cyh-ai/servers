package com.integration.core.util;

import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtils {
    private static final Logger LOG = LoggerFactory.getLogger(StringUtils.class);
    public static final String REGEX1 = "[^0-9+-]";
    public static final String REGEX2 = "[^0-9]";
    public static final String REGEX3 = "[^0-9+\\.]";

    private StringUtils() {
    }

    public static boolean isEmpty(String str) {
        return org.apache.commons.lang3.StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        return org.apache.commons.lang3.StringUtils.isBlank(str);
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String trim(String str) {
        return org.apache.commons.lang3.StringUtils.trim(str);
    }

    public static String strip(String str) {
        return strip(str, (String) null);
    }

    public static String strip(String str, String stripChars) {
        return org.apache.commons.lang3.StringUtils.strip(str, stripChars);
    }

    public static String stripStart(String str, String stripChars) {
        return org.apache.commons.lang3.StringUtils.stripStart(str, stripChars);
    }

    public static String stripEnd(String str, String stripChars) {
        return org.apache.commons.lang3.StringUtils.stripEnd(str, stripChars);
    }

    public static String[] stripAll(String... strs) {
        return stripAll(strs, (String) null);
    }

    public static String[] stripAll(String[] strs, String stripChars) {
        return org.apache.commons.lang3.StringUtils.stripAll(strs, stripChars);
    }

    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return org.apache.commons.lang3.StringUtils.equalsIgnoreCase(str1, str2);
    }

    public static boolean equals(CharSequence str1, CharSequence str2) {
        return org.apache.commons.lang3.StringUtils.equals(str1, str2);
    }

    public static int indexOf(String str, int searchChar) {
        return org.apache.commons.lang3.StringUtils.indexOf(str, searchChar);
    }

    public static int indexOf(String str, int searchChar, int startPos) {
        return org.apache.commons.lang3.StringUtils.indexOf(str, searchChar, startPos);
    }

    public static int indexOf(String str, String searchStr) {
        return org.apache.commons.lang3.StringUtils.indexOf(str, searchStr);
    }

    public static int indexOf(String str, String searchStr, int startPos) {
        return org.apache.commons.lang3.StringUtils.indexOf(str, searchStr, startPos);
    }

    public static int ordinalIndexOf(String str, String searchStr, int ordinal) {
        return org.apache.commons.lang3.StringUtils.ordinalIndexOf(str, searchStr, ordinal);
    }

    public static int indexOfIgnoreCase(String str, String searchStr) {
        return org.apache.commons.lang3.StringUtils.indexOfIgnoreCase(str, searchStr);
    }

    public static int indexOfIgnoreCase(String str, String searchStr, int startPos) {
        return org.apache.commons.lang3.StringUtils.indexOfIgnoreCase(str, searchStr, startPos);
    }

    public static int lastIndexOf(String str, int searchChar) {
        return org.apache.commons.lang3.StringUtils.lastIndexOf(str, searchChar);
    }

    public static int lastIndexOf(String str, int searchChar, int startPos) {
        return org.apache.commons.lang3.StringUtils.lastIndexOf(str, searchChar, startPos);
    }

    public static int lastIndexOf(String str, String searchStr) {
        return org.apache.commons.lang3.StringUtils.lastIndexOf(str, searchStr);
    }

    public static int lastIndexOf(String str, String searchStr, int startPos) {
        return org.apache.commons.lang3.StringUtils.lastIndexOfIgnoreCase(str, searchStr, startPos);
    }

    public static int lastOrdinalIndexOf(String str, String searchStr, int ordinal) {
        return org.apache.commons.lang3.StringUtils.lastOrdinalIndexOf(str, searchStr, ordinal);
    }

    public static int lastIndexOfIgnoreCase(String str, String searchStr) {
        return org.apache.commons.lang3.StringUtils.lastIndexOfIgnoreCase(str, searchStr);
    }

    public static int lastIndexOfIgnoreCase(String str, String searchStr, int startPos) {
        return org.apache.commons.lang3.StringUtils.lastIndexOfIgnoreCase(str, searchStr, startPos);
    }

    public static boolean contains(String str, int searchChar) {
        return org.apache.commons.lang3.StringUtils.contains(str, searchChar);
    }

    public static boolean contains(String str, String searchStr) {
        return org.apache.commons.lang3.StringUtils.contains(str, searchStr);
    }

    public static boolean containsIgnoreCase(String str, String searchStr) {
        return org.apache.commons.lang3.StringUtils.containsIgnoreCase(str, searchStr);
    }

    public static boolean containsWhitespace(String str) {
        return org.apache.commons.lang3.StringUtils.containsWhitespace(str);
    }

    public static boolean containsAny(String str, char... searchChars) {
        return org.apache.commons.lang3.StringUtils.containsAny(str, searchChars);
    }

    public static boolean containsAny(String str, String searchChars) {
        return org.apache.commons.lang3.StringUtils.containsAny(str, searchChars);
    }

    public static int indexOfAny(String str, char... searchChars) {
        return org.apache.commons.lang3.StringUtils.indexOfAny(str, searchChars);
    }

    public static int indexOfAny(String str, String searchChars) {
        return org.apache.commons.lang3.StringUtils.indexOfAny(str, searchChars);
    }

    public static int indexOfAnyBut(String str, char... searchChars) {
        return org.apache.commons.lang3.StringUtils.indexOfAnyBut(str, searchChars);
    }

    public static int indexOfAnyBut(String str, String searchChars) {
        return org.apache.commons.lang3.StringUtils.indexOfAnyBut(str, searchChars);
    }

    public static boolean containsOnly(String str, char... valid) {
        return org.apache.commons.lang3.StringUtils.containsOnly(str, valid);
    }

    public static boolean containsOnly(String str, String validChars) {
        return org.apache.commons.lang3.StringUtils.containsOnly(str, validChars);
    }

    public static boolean containsNone(String str, char... searchChars) {
        return org.apache.commons.lang3.StringUtils.containsNone(str, searchChars);
    }

    public static boolean containsNone(String str, String invalidChars) {
        return org.apache.commons.lang3.StringUtils.containsNone(str, invalidChars);
    }

    public static int indexOfAny(String str, String... searchStrs) {
        return org.apache.commons.lang3.StringUtils.indexOfAny(str, searchStrs);
    }

    public static int lastIndexOfAny(String str, String... searchStrs) {
        return org.apache.commons.lang3.StringUtils.lastIndexOfAny(str, searchStrs);
    }

    public static String toString(byte[] bytes, String charsetName) throws UnsupportedEncodingException {
        return org.apache.commons.lang3.StringUtils.toString(bytes, charsetName);
    }

    public static String[] subString(String str, int length, int max) throws UnsupportedEncodingException {
        String[] result = new String[max];
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        int count = 0;
        int arrayCount = 0;

        for (int i = 0; i < charArray.length; ++i) {
            char ch = charArray[i];
            int chLength = Character.toString(ch).getBytes("UTF-8").length;
            count += chLength;
            if (count > length) {
                result[arrayCount] = sb.toString();
                ++arrayCount;
                if (arrayCount > max - 1) {
                    break;
                }

                sb = new StringBuilder();
                count = chLength;
            }

            sb.append(ch);
        }

        if (arrayCount < max) {
            result[arrayCount] = sb.toString();
        }

        return result;
    }

    public static String substring(String str, int start) {
        return org.apache.commons.lang3.StringUtils.substring(str, start);
    }

    public static String substring(String str, int start, int end) {
        return org.apache.commons.lang3.StringUtils.substring(str, start, end);
    }

    public static String left(String str, int len) {
        return org.apache.commons.lang3.StringUtils.left(str, len);
    }

    public static String right(String str, int len) {
        return org.apache.commons.lang3.StringUtils.right(str, len);
    }

    public static String mid(String str, int pos, int len) {
        return org.apache.commons.lang3.StringUtils.mid(str, pos, len);
    }

    public static String substringBefore(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringBefore(str, separator);
    }

    public static String substringAfter(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringAfter(str, separator);
    }

    public static String substringBeforeLast(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringBeforeLast(str, separator);
    }

    public static String substringAfterLast(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.substringAfterLast(str, separator);
    }

    public static String substringBetween(String str, String tag) {
        return substringBetween(str, tag, tag);
    }

    public static String substringBetween(String str, String open, String close) {
        return org.apache.commons.lang3.StringUtils.substringBetween(str, open, close);
    }

    public static String[] substringsBetween(String str, String open, String close) {
        return org.apache.commons.lang3.StringUtils.substringsBetween(str, open, close);
    }

    public static String[] split(String str) {
        return split(str, (String) null, -1);
    }

    public static String[] split(String str, char separatorChar) {
        return org.apache.commons.lang3.StringUtils.split(str, separatorChar);
    }

    public static String[] split(String str, String separatorChars) {
        return org.apache.commons.lang3.StringUtils.split(str, separatorChars);
    }


    public static String[] split(String str, String separatorChars, int max) {
        return org.apache.commons.lang3.StringUtils.split(str, separatorChars, max);
    }

    public static String[] splitByWholeSeparator(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.splitByWholeSeparator(str, separator);
    }

    public static String[] splitByWholeSeparator(String str, String separator, int max) {
        return org.apache.commons.lang3.StringUtils.splitByWholeSeparator(str, separator, max);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
        return org.apache.commons.lang3.StringUtils.splitByWholeSeparatorPreserveAllTokens(str, separator);
    }

    public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max) {
        return org.apache.commons.lang3.StringUtils.splitByWholeSeparatorPreserveAllTokens(str, separator, max);
    }

    public static String[] splitPreserveAllTokens(String str) {
        return org.apache.commons.lang3.StringUtils.splitPreserveAllTokens(str);
    }

    public static String[] splitPreserveAllTokens(String str, char separatorChar) {
        return org.apache.commons.lang3.StringUtils.splitPreserveAllTokens(str, separatorChar);
    }

    public static String[] splitPreserveAllTokens(String str, String separatorChars) {
        return org.apache.commons.lang3.StringUtils.splitPreserveAllTokens(str, separatorChars);
    }

    public static String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
        return org.apache.commons.lang3.StringUtils.splitPreserveAllTokens(str, separatorChars, max);
    }

    public static String[] splitByCharacterType(String str) {
        return org.apache.commons.lang3.StringUtils.splitByCharacterType(str);
    }

    public static String[] splitByCharacterTypeCamelCase(String str) {
        return org.apache.commons.lang3.StringUtils.splitByCharacterTypeCamelCase(str);
    }

    public static <T> String join(T... elements) {
        return org.apache.commons.lang3.StringUtils.join(elements);
    }

    public static String join(Object[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(Object[] array, char separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(Object[] array, String separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        return org.apache.commons.lang3.StringUtils.join(array, separator, startIndex, endIndex);
    }

    public static String join(Iterator<?> iterator, char separator) {
        return org.apache.commons.lang3.StringUtils.join(iterator, separator);
    }

    public static String join(Iterator<?> iterator, String separator) {
        return org.apache.commons.lang3.StringUtils.join(iterator, separator);
    }

    public static String deleteWhitespace(String str) {
        return org.apache.commons.lang3.StringUtils.deleteWhitespace(str);
    }

    public static String removeStart(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.removeStart(str, remove);
    }

    public static String removeStartIgnoreCase(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.removeStartIgnoreCase(str, remove);
    }

    public static String removeEnd(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.removeEnd(str, remove);
    }

    public static String removeEndIgnoreCase(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.removeEndIgnoreCase(str, remove);
    }

    public static String remove(String str, String remove) {
        return org.apache.commons.lang3.StringUtils.remove(str, remove);
    }

    public static String remove(String str, char remove) {
        return org.apache.commons.lang3.StringUtils.remove(str, remove);
    }

    public static String replaceOnce(String text, String searchString, String replacement) {
        return org.apache.commons.lang3.StringUtils.replaceOnce(text, searchString, replacement);
    }

    public static String replace(String text, String searchString, String replacement) {
        return org.apache.commons.lang3.StringUtils.replace(text, searchString, replacement);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        return org.apache.commons.lang3.StringUtils.replace(text, searchString, replacement, max);
    }

    public static String replaceEach(String text, String[] searchList, String[] replacementList) {
        return org.apache.commons.lang3.StringUtils.replaceEach(text, searchList, replacementList);
    }

    public static String replaceEachRepeatedly(String text, String[] searchList, String[] replacementList) {
        return org.apache.commons.lang3.StringUtils.replaceEachRepeatedly(text, searchList, replacementList);
    }

    public static String replaceChars(String str, char searchChar, char replaceChar) {
        return org.apache.commons.lang3.StringUtils.replaceChars(str, searchChar, replaceChar);
    }

    public static String replaceChars(String str, String searchChars, String replaceChars) {
        return org.apache.commons.lang3.StringUtils.replaceChars(str, searchChars, replaceChars);
    }

    public static String overlay(String str, String overlay, int start, int end) {
        return org.apache.commons.lang3.StringUtils.overlay(str, overlay, start, end);
    }

    public static String chomp(String str) {
        return org.apache.commons.lang3.StringUtils.chomp(str);
    }

    public static String chop(String str) {
        return org.apache.commons.lang3.StringUtils.chop(str);
    }

    public static String repeat(String str, int repeat) {
        return org.apache.commons.lang3.StringUtils.repeat(str, repeat);
    }

    public static String repeat(String str, String separator, int repeat) {
        return org.apache.commons.lang3.StringUtils.repeat(str, separator, repeat);
    }

    public static String repeat(char ch, int repeat) {
        return org.apache.commons.lang3.StringUtils.repeat(ch, repeat);
    }

    public static String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }

    public static String rightPad(String str, int size, char padChar) {
        return org.apache.commons.lang3.StringUtils.rightPad(str, size, padChar);
    }

    public static String rightPad(String str, int size, String padStr) {
        return org.apache.commons.lang3.StringUtils.rightPad(str, size, padStr);
    }

    public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }

    public static String leftPad(String str, int size, char padChar) {
        return org.apache.commons.lang3.StringUtils.leftPad(str, size, padChar);
    }

    public static String leftPad(String str, int size, String padStr) {
        return org.apache.commons.lang3.StringUtils.leftPad(str, size, padStr);
    }

    public static int length(String str) {
        return org.apache.commons.lang3.StringUtils.length(str);
    }

    public static String center(String str, int size) {
        return center(str, size, ' ');
    }

    public static String center(String str, int size, char padChar) {
        return org.apache.commons.lang3.StringUtils.center(str, size, padChar);
    }

    public static String center(String str, int size, String padStr) {
        return org.apache.commons.lang3.StringUtils.center(str, size, padStr);
    }

    public static String upperCase(String str) {
        return org.apache.commons.lang3.StringUtils.upperCase(str);
    }

    public static String upperCase(String str, Locale locale) {
        return org.apache.commons.lang3.StringUtils.upperCase(str, locale);
    }

    public static String lowerCase(String str) {
        return org.apache.commons.lang3.StringUtils.lowerCase(str);
    }

    public static String lowerCase(String str, Locale locale) {
        return org.apache.commons.lang3.StringUtils.lowerCase(str, locale);
    }

    public static String capitalize(String str) {
        return org.apache.commons.lang3.StringUtils.capitalize(str);
    }

    public static String uncapitalize(String str) {
        return org.apache.commons.lang3.StringUtils.uncapitalize(str);
    }

    public static String swapCase(String str) {
        return org.apache.commons.lang3.StringUtils.swapCase(str);
    }

    public static int countMatches(String str, String sub) {
        return org.apache.commons.lang3.StringUtils.countMatches(str, sub);
    }


    public static boolean isAlpha(String str) {
        return org.apache.commons.lang3.StringUtils.isAlpha(str);
    }

    public static boolean isAlphaSpace(String str) {
        return org.apache.commons.lang3.StringUtils.isAlphaSpace(str);
    }

    public static boolean isAlphanumeric(String str) {
        return org.apache.commons.lang3.StringUtils.isAlphanumeric(str);
    }

    public static boolean isAlphanumericSpace(String str) {
        return org.apache.commons.lang3.StringUtils.isAlphanumericSpace(str);
    }

    public static boolean isAsciiPrintable(String str) {
        return org.apache.commons.lang3.StringUtils.isAsciiPrintable(str);
    }

    public static boolean isNumeric(String str) {
        return org.apache.commons.lang3.StringUtils.isNumeric(str);
    }

    public static boolean isNumericSpace(String str) {
        return org.apache.commons.lang3.StringUtils.isNumericSpace(str);
    }

    public static boolean isWhitespace(String str) {
        return org.apache.commons.lang3.StringUtils.isWhitespace(str);
    }

    public static boolean isAllLowerCase(String str) {
        return org.apache.commons.lang3.StringUtils.isAllLowerCase(str);
    }

    public static boolean isAllUpperCase(String str) {
        return org.apache.commons.lang3.StringUtils.isAllUpperCase(str);
    }

    public static String reverse(String str) {
        return org.apache.commons.lang3.StringUtils.reverse(str);
    }

    public static String reverseDelimited(String str, char separatorChar) {
        return org.apache.commons.lang3.StringUtils.reverseDelimited(str, separatorChar);
    }

    public static String abbreviate(String str, int maxWidth) {
        return abbreviate(str, 0, maxWidth);
    }

    public static String abbreviate(String str, int offset, int maxWidth) {
        return org.apache.commons.lang3.StringUtils.abbreviate(str, offset, maxWidth);
    }

    public static String abbreviateMiddle(String str, String middle, int length) {
        return org.apache.commons.lang3.StringUtils.abbreviateMiddle(str, middle, length);
    }

    public static String difference(String str1, String str2) {
        return org.apache.commons.lang3.StringUtils.difference(str1, str2);
    }

    private static String regexMatcher(String str, String regex) {
        return Pattern.compile(regex).matcher(str).replaceAll("").trim();
    }

    public static byte getByte(String str) {
        str = regexMatcher(str, "[^0-9+-]");
        str = str.charAt(0) == '-' ? '-' + regexMatcher(str.substring(1), "[^0-9]") : regexMatcher(str, "[^0-9]");
        return Byte.parseByte(str);
    }

    public static short getShort(String str) {
        str = regexMatcher(str, "[^0-9+-]");
        str = str.charAt(0) == '-' ? '-' + regexMatcher(str.substring(1), "[^0-9]") : regexMatcher(str, "[^0-9]");
        return Short.parseShort(str);
    }

    public static int getInt(String str) {
        str = regexMatcher(str, "[^0-9+-]");
        str = str.charAt(0) == '-' ? '-' + regexMatcher(str.substring(1), "[^0-9]") : regexMatcher(str, "[^0-9]");
        return Integer.parseInt(str);
    }

    public static long getLong(String str) {
        str = regexMatcher(str, "[^0-9+-]");
        str = str.charAt(0) == '-' ? '-' + regexMatcher(str.substring(1), "[^0-9]") : regexMatcher(str, "[^0-9]");
        return Long.parseLong(str);
    }

    public static float getFloat(String str) {
        str = regexMatcher(str, "[^0-9+\\.+-]");
        int d = str.indexOf(46);
        str = d == -1 ? str : str.substring(0, d + 1) + remove(str.substring(d), ".");
        str = str.charAt(0) == '-' ? '-' + regexMatcher(str.substring(1), "[^0-9+\\.]") : regexMatcher(str, "[^0-9+\\.]");
        return Float.parseFloat(str);
    }

    public static double getDouble(String str) {
        str = regexMatcher(str, "[^0-9+\\.+-]");
        int d = str.indexOf(46);
        str = d == -1 ? str : str.substring(0, d + 1) + remove(str.substring(d), ".");
        str = str.charAt(0) == '-' ? '-' + regexMatcher(str.substring(1), "[^0-9+\\.]") : regexMatcher(str, "[^0-9+\\.]");
        return Double.parseDouble(str);
    }

    public static <T extends Serializable> String toString(T t) {
        return t == null ? null : t.toString();
    }
}
