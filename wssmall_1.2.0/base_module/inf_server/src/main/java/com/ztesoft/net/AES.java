package com.ztesoft.net;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/*******************************************************************************
 * AES加解密算法
 * 
 * 
 */

public class AES
{
	private static Logger logger = Logger.getLogger(AES.class);
	// 加密
	public static String encrypt(String sSrc, String sKey, String charset) throws Exception {
		if (sKey == null) {
			System.out.print("Key为空");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes("ASCII");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
		IvParameterSpec iv = new IvParameterSpec("0102030405060709".getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes(charset));
		return Base64.encodeBase64URLSafeString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用
	}

	// 解密
//	public static String decrypt(String sSrc, String sKey, String charset) throws Exception {
//		try {
//			// 判断Key是否正确
//			if (sKey == null) {
//				System.out.print("Key为空");
//				return null;
//			}
//			// 判断Key是否为16位
//			if (sKey.length() != 16) {
//				System.out.print("Key长度不是16位");
//				return null;
//			}
//			byte[] raw = sKey.getBytes("ASCII");
//			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//			IvParameterSpec iv = new IvParameterSpec("0102030405060709"	.getBytes());
//			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//			byte[] encrypted = Base64.decodeBase64(sSrc);//先用base64解密
//			try {
//				byte[] original = cipher.doFinal(encrypted);
//				String originalString = new String(original, charset);
//				return originalString;
//			} catch (Exception e) {
//				logger.info(e.toString());
//				return null;
//			}
//		} catch (Exception ex) {
//			logger.info(ex.toString());
//			return null;
//		}
//	}

	public static void main(String[] args) throws Exception {
		/*
		 * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
		 * 此处使用AES-128-CBC加密模式，key需要为16位。
		 */
		String cKey = "123456789ABCDEFG";
		// 需要加密的字串
		String cSrc = "Email : arix04@xxx.com" + "退款";
		logger.info(cSrc);
		// 加密
		long lStart = System.currentTimeMillis();
		String enString = AES.encrypt(cSrc, cKey, "GBK");
		logger.info("加密后的字串是：" + enString);

		long lUseTime = System.currentTimeMillis() - lStart;
		logger.info("加密耗时：" + lUseTime + "毫秒");
		// 解密
//		lStart = System.currentTimeMillis();
//		String DeString = AES.decrypt(enString, cKey, "GBK");
//		logger.info("解密后的字串是：" + DeString);
		lUseTime = System.currentTimeMillis() - lStart;
		logger.info("解密耗时：" + lUseTime + "毫秒");
	}
}