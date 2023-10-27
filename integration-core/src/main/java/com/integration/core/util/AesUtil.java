package com.integration.core.util;

import org.springframework.util.Assert;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

/**
 * @author cyh
 * 加解密工具类
 */
public class AesUtil {

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * AES CBC PKCS5Padding 加解密秘钥
     */
    public static final String DEFAULT_KEY = "0102030405060708";
    /**
     * AES/CBC/PKCS5Padding 加解密方式
     */
    private static final String ALGORITHM_CBC = "AES/CBC/PKCS5Padding";


    private static final String CHARACTER_CODING = "UTF-8";
    // 透明化密钥
    private static final String TOUMINGHUA_KEY = "sdalflkwerqdfsa1";


    /**
     * 加密
     *
     * @param str      需要加密的报文
     * @param fromBase 判断加密方式
     * @return 加密后的报文
     */
    public static String aesCbcPKCS5PaddingEncrypt(String str, boolean fromBase) {
        return aesCbcPKCS5PaddingEncrypt(str, DEFAULT_KEY, DEFAULT_KEY, fromBase);
    }

    /**
     * 解密
     *
     * @param str      需要解密的报文
     * @param fromBase 判断解密的方式
     * @return 解密后的报文
     */
    public static String aesCbcPKCS5PaddingDecrypt(String str, boolean fromBase) {
        return aesCbcPKCS5PaddingDecrypt(str, DEFAULT_KEY, DEFAULT_KEY, fromBase);
    }


    /**
     * 加密
     *
     * @param str      需要加密的报文
     * @param sKey     秘钥
     * @param iv       秘钥
     * @param formBase 判断加密的方式
     * @return 加密后的报文
     */
    public static String aesCbcPKCS5PaddingEncrypt(String str, String sKey, String iv, boolean formBase) {
        try {
            byte[] result = aesCbcPKCS5PaddingEncrypt(str.getBytes(), sKey.getBytes(), iv.getBytes());
            if (formBase) {
                return new String(Base64.getEncoder().encode(result));
            } else {
                return bytes2hex(result);
            }
        } catch (Exception e) {
            throw new RuntimeException("AES CBC 加密出错", e);
        }
    }


    /**
     * 解密
     *
     * @param str      需要解密的报文
     * @param sKey     秘钥
     * @param iv       秘钥
     * @param fromBase 判断解密的方式
     * @return 解密后的报文
     */
    public static String aesCbcPKCS5PaddingDecrypt(String str, String sKey, String iv, boolean fromBase) {
        try {
            byte[] source;
            if (fromBase) {
                source = Base64.getDecoder().decode(str.getBytes());
            } else {
                source = hex2bytes(str);
            }
            return new String(aesCbcPKCS5PaddingDecrypt(source, sKey.getBytes(), iv.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("AES CBC 解密出错", e);
        }
    }


    /**
     * 加密
     *
     * @param str  需要加密的报文
     * @param sKey 秘钥
     * @param iv   秘钥
     * @return 加密后的报文
     */
    public static byte[] aesCbcPKCS5PaddingEncrypt(byte[] str, byte[] sKey, byte[] iv) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(sKey, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM_CBC);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
            return cipher.doFinal(str);
        } catch (Exception e) {
            throw new RuntimeException("AES CBC 加密出错", e);
        }
    }

    /**
     * 解密
     *
     * @param str  需要解密的报文
     * @param sKey 秘钥
     * @param iv   秘钥
     * @return 解密后的报文
     */
    public static byte[] aesCbcPKCS5PaddingDecrypt(byte[] str, byte[] sKey, byte[] iv) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(sKey, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM_CBC);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
            return cipher.doFinal(str);
        } catch (Exception e) {
            throw new RuntimeException("AES CBC 解密出错", e);
        }
    }

    /**
     * 2进制转16进制
     *
     * @param data a byte[] to convert to Hex characters
     * @return A char[] containing hexadecimal characters
     */
    public static String bytes2hex(final byte[] data) {
        Assert.notNull(data, "data不能为null");
        final int l = data.length;
        final char[] out = new char[l << 1];
        for (int i = 0; i < l; i++) {
            out[i * 2] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[i * 2 + 1] = DIGITS_LOWER[0x0F & data[i]];
        }
        return new String(out);
    }

    /**
     * 16进制转2进制
     *
     * @param hexStr 需要解密的报文
     * @return 需要解密的报文
     */
    public static byte[] hex2bytes(String hexStr) {
        Assert.notNull(hexStr, "hexStr不能为空");
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println("----------测试加解密方法1----------");
        String jiami1 = aesCbcPKCS5PaddingEncrypt("123456789", false);
        System.out.println("加密方法 1 加密数据：" + jiami1);
        String jiemi1 = aesCbcPKCS5PaddingDecrypt(jiami1, false);
        System.out.println("解密方法 1 解密数据" + jiemi1);

        System.out.println("----------测试加解密方法2----------");

        //把key进行加密后再进行加解密 也可以直接使用指定key，不用加密
        SecretKeySpec cyh = getAesKey("cyh");
        System.out.println("加密的秘钥：" + cyh);
        //加密解密方法参数类型为：String byte boolean
        String jiami2 = encryptAESECB("987654321", cyh.getEncoded(), false);
        System.out.println("加密方法 2 加密数据：" + jiami2);
        String jiemi2 = decryptAESECB(jiami2, cyh.getEncoded(), false);
        System.out.println("解密方法 2 解密数据" + jiemi2);

        System.out.println("----------测试加解密方法3----------");

        //不加密的key要满足16位
        String c = "asdfghjklzxcvbnm";
        //加密解密方法参数类型为：String String boolean
        String jiami3 = encryptAESECB("111222333", c, false);
        System.out.println("加密方法 3 加密数据：" + jiami3);
        String jiemi3 = decryptAESECB(jiami3, c, false);
        System.out.println("解密方法 3 解密数据" + jiemi3);

        System.out.println("----------方法测试解密----------");
        String name = "a2c7fc5201aefdd3f26233b49501f221b98e94339df60030ffadb4a95f504d5affd6566f59ba184bcfdd763b5132a7aa11ee5217c5b7567241594399c8ff43a9";
        String jiemi = aesCbcPKCS5PaddingDecrypt(name, false);
        System.out.println("解密方法对应解密的数据:" + jiemi);
        List list = JsonUtil.toObject(jiemi, List.class);
        System.out.println(list);
    }


    /**
     * 生成加密秘钥
     *
     * @param key 需要加密的key
     * @return 加密的key
     */
    public static SecretKeySpec getAesKey(final String key) {
        // 返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg;
        try {
            kg = KeyGenerator.getInstance("AES");
            // AES 要求密钥长度为 128
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            kg.init(128, secureRandom);
            // 生成一个密钥
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("AES CBC 加密出错", e);
        }
    }


    /**
     * AES ECB加密
     *
     * @param str      需要加密的报文
     * @param key      加密需要的key
     * @param fromBase 加密后是否做base64转换
     * @return 加密的报文
     */
    public static String encryptAESECB(String str, byte[] key, boolean fromBase) {
        try {
            byte[] result = encryptAESECB(str.getBytes(), key);
            if (fromBase) {
                return new String(Base64.getEncoder().encode(result));
            } else {
                return bytes2hex(result);
            }
        } catch (Exception e) {
            throw new RuntimeException("AES CBC 加密出错", e);
        }
    }

    /**
     * AES ECB加密
     *
     * @param str      需要解密的报文
     * @param key      解密需要的key
     * @param fromBase 解密后是否做base64转换
     * @return 解密的报文
     */
    public static String decryptAESECB(String str, byte[] key, boolean fromBase) {
        byte[] src = (fromBase ? Base64.getDecoder().decode(str) : hex2bytes(str));
        return new String(decryptAESECB(src, key));
    }


    /**
     * AES ECB加密
     *
     * @param str      需要加密的报文
     * @param key      加密需要的key
     * @param fromBase 加密后是否做base64转换
     * @return 加密的报文
     */
    public static String encryptAESECB(String str, String key, boolean fromBase) {
        try {
            byte[] result = encryptAESECB(str.getBytes(), key.getBytes());
            if (fromBase) {
                return new String(Base64.getEncoder().encode(result));
            } else {
                return bytes2hex(result);
            }
        } catch (Exception e) {
            throw new RuntimeException("AES CBC 加密出错", e);
        }
    }

    /**
     * AES ECB加密
     *
     * @param str      需要解密的报文
     * @param key      解密需要的key
     * @param fromBase 解密后是否做base64转换
     * @return 解密的报文
     */
    public static String decryptAESECB(String str, String key, boolean fromBase) {
        byte[] src = (fromBase ? Base64.getDecoder().decode(str) : hex2bytes(str));
        return new String(decryptAESECB(src, key.getBytes()));
    }


    /**
     * AES加密 128 AES/ECB/PKCS5Padding
     *
     * @param source 需要加密的字符串
     * @param key    需要加密的秘钥
     * @return 返回加密结果
     */
    public static byte[] encryptAESECB(byte[] source, byte[] key) {
        Assert.isTrue(null != key && key.length == 16, "key的长度需要16");
        try {
            return encrypt(source, key);
        } catch (Exception e) {
            throw new RuntimeException("AES ECB 加密出错", e);
        }
    }

    public static byte[] decryptAESECB(byte[] source, byte[] key) {
        Assert.isTrue(null != key && key.length == 16, "key的长度需要16");
        try {
            return decrypt(source, key);
        } catch (Exception e) {
            throw new RuntimeException("AES CBC 加密出错", e);
        }
    }


    /**
     * 太好店对接透明化加密
     *
     * @param str 需要加密的报文
     * @return 加密的报文
     */
    public static String encodingByAES(String str) {
        try {
            byte[] raw = TOUMINGHUA_KEY.getBytes(CHARACTER_CODING);
            byte[] encrypt = encrypt(str, raw);
            return Base64.getEncoder().encodeToString(encrypt);
        } catch (Exception e) {
            throw new RuntimeException("加密报错：", e);
        }
    }


    public static byte[] encrypt(String data, byte[] key) throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        return encrypt(data.getBytes(), key, "AES/ECB/PKCS5Padding");
    }

    public static byte[] encrypt(byte[] data, byte[] key) throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        return encrypt(data, key, "AES/ECB/PKCS5Padding");
    }

    public static byte[] decrypt(byte[] data, byte[] key) throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        return decrypt(data, key, "AES/ECB/PKCS5Padding");
    }


    public static Key toKey(byte[] key) {
        return new SecretKeySpec(key, "AES");
    }

    public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Key k = toKey(key);
        return encrypt(data, k, cipherAlgorithm);
    }

    public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Key k = toKey(key);
        return decrypt(data, k, cipherAlgorithm);
    }


    public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(1, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(2, key);
        return cipher.doFinal(data);
    }

}
