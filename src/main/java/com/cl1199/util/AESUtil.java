package com.cl1199.util;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * AES 解密
 * @author wdj
 */
public class AESUtil {
	/***默认向量常量**/
    public static final String IV = "mdapvv7uwtu0d9c4";
    public static final String pkey = "mdapvv7uwtu0d9c4";
 
    /**
     * 使用PKCS7Padding填充必须添加一个支持PKCS7Padding的Provider
     * 类加载的时候就判断是否已经有支持256位的Provider,如果没有则添加进去
     */
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }
 
    /**
     * @param content base64处理过的字符串
     * @param pkey    密匙
     * @return String    返回类型
     * @throws Exception
     * @throws
     * @Title: aesDecodeStr
     * @Description: 解密 失败将返回NULL
     */
    public static String aesDecodeStr(String content) throws Exception {
        try {
            byte[] base64DecodeStr = Base64.decodeBase64(content);
            byte[] aesDecode = aesDecode(base64DecodeStr, pkey);
            if (aesDecode == null) {
                return null;
            }
            String result;
            result = new String(aesDecode, "UTF-8");
            return result;
        } catch (Exception e) {
            System.out.println("Exception:" + e.getMessage());
            throw new Exception("解密异常");
        }
    }
 
    /**
     * 解密 128位
     *
     * @param content 解密前的byte数组
     * @param pkey    密匙
     * @return result  解密后的byte数组
     * @throws Exception
     */
    public static byte[] aesDecode(byte[] content, String pkey) throws Exception {
        SecretKeySpec skey = new SecretKeySpec(pkey.getBytes(), "AES");
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, skey, iv);// 初始化解密器
        byte[] result = cipher.doFinal(content);
        return result; // 解密
    }
 
    public static void main(String[] args) throws Exception {
        //密匙       
        String aesDecodeStr = aesDecodeStr("zQo0gb5EvqS/T8qsBaWWP7rbGXkTgymQg12JNpFl7k61fGtI+KxrhvUy34sMtxqSAfmO/jndiNxSSgsOnuMDlI+LWnRB50dlxDIo0MmWCb1nTFK6qh5WU/Z/awKnkcNVg7BVBlgQnIv4NLhSdr+h1o/G+CeNPZCGF8aJZutNUqEZllA1z4+cBMROeRpdVO/GCiGbEKT09+dyCiaopOtwbfmrB85v1Mu3cqGNv/FvKviYc+JJGkLmVplrP9OzURubskhqjalgMJv59QBQvV60I9oXoN6mtwDaXgKrN2i+9LjyOxge2tjYxHmbz8BF4e13Z22kIStXuG4EOxAfudUa0AsH1tt0iKO1H6E0XGDAaUSkpM19BvLpk5+yV+Va59zSiCjp34a9PECHoal4f8+WAoa1+FPTddXg1zJjMAsdkro=");
        System.out.println("解密报文:" + aesDecodeStr);
    }

}
