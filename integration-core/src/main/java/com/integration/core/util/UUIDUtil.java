package com.integration.core.util;

import java.util.UUID;

/**
 * @author cyh
 * UUID
 */
public class UUIDUtil {

    private static final int NUM = 8;

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};


    /**
     * 八位随机码
     *
     * @return 返回八位随机码
     */
    public static String generateShortUuid() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < NUM; i++) {
            String str = UUID.randomUUID().toString().replace("-", "").substring(i * 4, (i + 1) * 4);
            int x = Integer.parseInt(str, 16);
            sb.append(chars[x % 0x3E]);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        String s = generateShortUuid();
        System.out.println(s);
    }
}
