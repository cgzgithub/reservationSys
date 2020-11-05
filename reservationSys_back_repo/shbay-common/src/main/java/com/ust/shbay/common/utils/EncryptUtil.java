package com.ust.shbay.common.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * 加密算法（湾区系统自动注册登录）
 *
 * @author : Yao Gang
 * @date : 2020-09-21 13:38
 */
public class EncryptUtil {

    private static final String KEY = "chuangren@@shbay";
    private static final String ALGORITHM = "AES";

    public static void main(String[] args) throws Exception {

        String phone = "13255556666";
        System.out.println("加密前：" + phone);

        System.out.println("加密密钥：" + KEY);

        String encrypt = aesEncrypt(phone);
        System.out.println(encrypt.length() + ":加密后：" + encrypt);

    }

    public static String aesEncrypt(String content) throws Exception {
        return md5(base64Encode(aesEncryptToBytes(content, KEY)));
    }

    private static String md5(String dataStr) {
        try {
            dataStr = dataStr + KEY;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = m.digest();
            StringBuilder result = new StringBuilder();
            for (byte aByte : bytes) {
                result.append(Integer.toHexString((0x000000FF & aByte) | 0xFFFFFF00).substring(6));
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator aes = KeyGenerator.getInstance(ALGORITHM);
        aes.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), ALGORITHM));

        return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
    }


}