package com.ztesoft.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	public static String compute(String inStr) throws NoSuchAlgorithmException {
		/*char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];*/
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] md5Bytes = md5.digest(inStr.getBytes());
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = (md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static void main(String[] args) throws NoSuchAlgorithmException{
		String current = DateFormatUtils.getFormatedDateTime();
 
	}

	public final static String MD5(String s) throws NoSuchAlgorithmException {
		return MD5Util.MD5(s, null);
	}
	
	public final static String MD5(String s, String charsetName) throws NoSuchAlgorithmException {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
			byte[] strTemp = null;
			if (StringUtils.isEmpty(charsetName)) {
				strTemp = s.getBytes();
			} else {
				try {
					strTemp = s.getBytes(charsetName);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(strTemp);
			byte[] md = md5.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
	}
}
