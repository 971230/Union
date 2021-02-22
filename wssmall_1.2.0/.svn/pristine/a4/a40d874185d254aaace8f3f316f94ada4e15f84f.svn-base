package com.ztesoft.net.mall.core.utils;


public class PayUtils {
	
	private final static String[] hexDigits = { 
	      "0", "1", "2", "3", "4", "5", "6", "7", 
	      "8", "9", "A", "B", "C", "D", "E", "F"};
		
	public static String md5Digest(String src) throws Exception {
		return byteArrayToHexString(md5Digest(src.getBytes()));
	}
	
	public static String byteArrayToHexString(byte[] b) { 
	    StringBuffer resultSb = new StringBuffer(); 
	    for (int i = 0; i < b.length; i++) { 
	      resultSb.append(byteToHexString(b[i])); 
	    } 
	    return resultSb.toString(); 
	  }
	
	private static String byteToHexString(byte b) { 
	    int n = b; 
	    if (n < 0) 
	      n = 256 + n; 
	    int d1 = n / 16; 
	    int d2 = n % 16; 
	    return hexDigits[d1] + hexDigits[d2]; 
	  } 

	 public static byte[] md5Digest(byte[] src) throws Exception {
			java.security.MessageDigest alg = java.security.MessageDigest
					.getInstance("MD5"); // MD5 is 16 bit message digest

			return alg.digest(src);
	 }	 
}
