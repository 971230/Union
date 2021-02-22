package com.ztesoft.net.service.impl;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
public final class SecurityTool {
    public static final String ENCODE = "UTF-8";
    public static final String CIPHER_ALGORITHM = "AES";
    private static final String KEY_MAC = "HmacMD5";
    /**
     * AES加密
     * @param keyStr 密钥
     * @param dataStr 原始数据
     */
    public static String encrypt(String keyStr,String dataStr) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        SecretKey secretKey = new SecretKeySpec(decoder.decodeBuffer(keyStr), CIPHER_ALGORITHM);
        //Cipher完成加密或解密工作类
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //对Cipher初始化，解密模式
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        //加密data
        byte[] cipherByte = cipher.doFinal(dataStr.getBytes(ENCODE));
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return  base64Encoder.encode(cipherByte);
    }
    /**
     * AES解密
     * @param keyStr 密钥
     * @param dataStr 加密数据
     */
    public static String decode(String keyStr,String dataStr) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        //恢复密钥
        SecretKey secretKey = new SecretKeySpec(decoder.decodeBuffer(keyStr), CIPHER_ALGORITHM);
        //Cipher完成加密或解密工作类
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //对Cipher初始化，解密模式
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        //解密data
        byte[] cipherByte = cipher.doFinal(decoder.decodeBuffer(dataStr));
        return new String(cipherByte,ENCODE);
    }
    /**
     * 签名
     * @param key 密钥
     * @param plaintext 原文
     */
    public static String sign(String key,String plaintext) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        SecretKey secretKey = new SecretKeySpec(decoder.decodeBuffer(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        byte[] ret = mac.doFinal(plaintext.getBytes(ENCODE));
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return  base64Encoder.encode(ret);
    }
}
