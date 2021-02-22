package com.ztesoft.inf.communication.client.util;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

public class SFUtil {

//	private final static String checkWord = "zlXIzIaYGOWekScm";
	
	public static String genVerifyCode(String reqString,String checkWord) throws Exception {
		if (reqString == null || reqString.equals("")) {
			
		}
		String xml = SFUtil.fetchXML(reqString);
		String xc = xml + checkWord;
		String verifyCode = SFUtil.md5EncryptAndBase64(xc);
		return verifyCode;
	}

	public static String fetchXML(String reqString) throws Exception {
		String startStr = "<arg0><![CDATA[";
		String endStr = "]]></arg0>";
		int startIdx = reqString.indexOf(startStr);
		int endIdx = reqString.indexOf(endStr);
		if (startIdx == -1 || endIdx == -1) {
			throw new Exception("REQ_TPL format is incorrect!");
		}
		return reqString.substring(startIdx + startStr.length(), endIdx).trim();
	}

	public static String loadFile(String fileName) {
		InputStream fis;
		try {
			fis = new FileInputStream(fileName);
			byte[] bs = new byte[fis.available()];
			fis.read(bs);
			String res = new String(bs, "utf8");
			fis.close();
			return res;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String md5EncryptAndBase64(String str) {
		return encodeBase64(md5Encrypt(str));
	}

	private static byte[] md5Encrypt(String encryptStr) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(encryptStr.getBytes("utf8"));
			return md5.digest();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String encodeBase64(byte[] b) {
		sun.misc.BASE64Encoder base64Encode = new BASE64Encoder();
		String str = base64Encode.encode(b);
		return str;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(SFUtil.fetchXML("<arg0><![CDATA[   123    \n]]></arg0>"));
	}
}
