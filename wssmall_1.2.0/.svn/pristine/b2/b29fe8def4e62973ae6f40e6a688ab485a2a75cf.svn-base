package com.powerise.ibss.wb;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.apache.log4j.Logger;

import java.security.Security;
public class EncryptFunc{
	private static Logger logger = Logger.getLogger(EncryptFunc.class);
	//采用密钥对原始信息加密
    public static String doEncrypt(String s_key,String my_info) throws Exception {
		SecretKey deskey = null;
		javax.crypto.spec.SecretKeySpec destmp= null;
		byte[] desEncode=null;
		String Algorithm="DES"; //定义 加密算法,可用 DES,DESede,Blowfish
		try{
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			desEncode = hex2byte(s_key);
			destmp =new javax.crypto.spec.SecretKeySpec(desEncode,Algorithm);
			deskey =destmp;
			
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE,deskey);
			byte[] cipherByte=c1.doFinal(my_info.getBytes());
			return byte2hex(cipherByte);
			
		}catch (java.security.NoSuchAlgorithmException e1) {
			throw new Exception("加密出错(NoSuchAlgorithmException)"+e1.getMessage());
		}catch (javax.crypto.NoSuchPaddingException e2) {
			throw new Exception("加密出错(NoSuchPaddingException)"+e2.getMessage());
		}catch (java.lang.Exception e3) {
			throw new Exception("加密出错"+e3.getMessage());
		}finally{
		
		}
	}
	
	//采用密钥对加密信息进行解密
	public static String doDecrypt(String s_key,String my_info) throws Exception {
		SecretKey deskey = null;
		javax.crypto.spec.SecretKeySpec destmp= null;
		byte[] desEncode=null;
		String Algorithm="DES"; //定义 加密算法,可用 DES,DESede,Blowfish
		
		
		if(my_info==null) return null;
		
		try{
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			desEncode = hex2byte(s_key);
			destmp =new javax.crypto.spec.SecretKeySpec(desEncode,Algorithm);
			deskey =destmp;
			
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE,deskey);
			
			byte[] cipherByte = hex2byte(my_info);
			byte[] clearByte=c1.doFinal(cipherByte);
			logger.info("解密后的二进串:"+byte2hex(clearByte));
			logger.info("解密后的信息:"+(new String(clearByte)));
			
			return new String(clearByte);
		}catch (java.security.NoSuchAlgorithmException e1) {
			throw new Exception("加密出错(NoSuchAlgorithmException)"+e1.getMessage());
		}catch (javax.crypto.NoSuchPaddingException e2) {
			throw new Exception("加密出错(NoSuchPaddingException)"+e2.getMessage());
		}catch (java.lang.Exception e3) {
			throw new Exception("加密出错"+e3.getMessage());
		}finally{
		
		}
	}
	//二行制转字符串
	public static String byte2hex(byte[] b) 
	{
		String hs="";
		String stmp="";
		for (int n=0;n<b.length;n++)
		{
			stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length()==1) {
				hs=hs+"0"+stmp;
			}else {
				hs=hs+stmp;
			}
			if (n<b.length-1)  hs=hs+":";
		}
		return hs.toUpperCase();
	}
	//字符串转二行制
	public static byte[] hex2byte(String sValue) {
    	if(sValue==null) return null;
    	int  iLen  = sValue.length();
    	int  iPos  = 0;
    	int  iCur  = 0;
    	String str = null;
    	StringBuffer sb = new StringBuffer("");
    	
    	byte[] retBytes= new byte[iLen];
    	
    	while(!sValue.equals("")){
    		iPos = sValue.indexOf(":");
    		if(iPos<0){
    			str    = sValue;
    			sValue = "";
    		}else{
    			str    = sValue.substring(0,iPos);
    			sValue = sValue.substring(iPos+1);
    		}
    		if(str.equals("")) continue;
    		if(str.length()>2) return null;
    		retBytes[iCur] = (byte)Integer.parseInt(str,16);
    		iCur++;
    	}
    	byte[] tmpBytes= new byte[iCur];
    	for(iPos=0;iPos<iCur;iPos++) tmpBytes[iPos] = retBytes[iPos];
    	return tmpBytes;
    }
}       
