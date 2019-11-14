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
    String aesDecodeStr = aesDecodeStr("zQo0gb5EvqS/T8qsBaWWP7rbGXkTgymQg12JNpFl7k561nHe79nMNFZ52bo0fjGEGObDgDZP7oMgSwCKRqjwNtS+K12nr9HaLL5CPxJ4UXms1P8c3cjHl4d+AUEiip6hVOfIUda24oTinlVokvDx1bqcSPqtsebhHFwNxPzZ1j+anI1a+cY1/tk+kd6OlORO3WQCht9xxWpJdT1KEcLRZvxY6+WzApnklQcDWqeOZaPsSba0P1XdZpAIO319aPU61p5+Baq09OJrS489UsBE+DSKH4ZRu/nxDSNBoH+Sivom3vsLwnsJz/a3HGzyWhYKXiQRXiEVdT6easeSOdy6dUv9SlmDp8BA1jLpxQUW2pYdbA+DfgQ20XgV3sAUvzPGGzsWvY1F53SRo3pgJWOD1j3ARwb+WaBVImZCafShEC+M9UL+XY8UJabKrkgrPNxQIjDuyto1UVcsPq5H5qWV2qzbOpMA+r4+yCIJupe/vJbECWtFkToEkIIEKWmMmNd6/9xYU/qaVWXmdfEL/tUKRyIBanQNh2aOdIbjgIJORVIXWPqC3kgZBjNUDohOUovbBxQHlEcsEyZpf3+JRog7BU7XwW42R/qtYVcjALp0yIYikvJ3qlpihQwL0u16mb7fvNwO/UTHtQh7k8z1qyWvu9yZdDeX+SbkXH1qRYkFHys8uhCXT/1/DKQQ1rA/1XwB24zb4Zwitdj+34+GmngoUuNjisZJTK5p1MbqusMgzZmBZKZ1m6nD2UiMX1spxNvybCZ0wr5yWhK81MdiG6FjMcXeZucmSvSGcbQIU26e+3S4T7ahDV8ZLVbDJR/N/rKLWpLGJC4j4oH6q/2GusrZczdv6dQn6sMx2ctHZV8SigKNdFB2C/liCr3nIZWtLAHkPI8+9Yg3YDLzS7QHgRSgpo1KDyM+USY3mzHOadDb8S7PzyoaXoypyhsLG0D15NCJ+ixnLZvWot2pE0wv+xuhzGXa7CUD8+pNDnGv0oBl74og8YozJFcbyYqFW1diRrsx5j/WfKPBUWdKVxJKvf26kHEw9Lc1AIl9LsMtFs9dOkO5npN4nCm9UA4+a1w3eGNJkwj8vZDPe3SEn7IUdd58YVM4oXF00jjftSUWGKOd7s0qHo+g4idl4JzFcrU+/+VfjTfHwmUyiURZHYE5vUlPQWXHsRE71FGgiFvPjryCxGHBiGsplZghI8CKZDdACeLRD/VmZoQXOP3fuM2Df2RkfuoZmbs1l7PzZvb2bxybY4clMH35j8qaR1kOzJ4uXG5AgOhIOwTaxvh6nPiXoC9WlA==");
        System.out.println("解密报文:" + aesDecodeStr);
    }

}
