package com.powerise.ibss.util;

public class TEA {
	public static int encryptKey[] = { 867, 742, 12, 452 };

	public static char[] TEADecrypt(char v[], char w[]) {
		int y;
		int z;
		int sum = 0xC6EF3720;
		int delta = 0x9E3779B9;
		int a = encryptKey[0];
		int b = encryptKey[1];
		int c = encryptKey[2];
		int d = encryptKey[3];
		int n = 32;

		int tmpLong[] = new int[8];

		for (int i = 0; i < 8; i++) {
			tmpLong[i] = v[i];
		}

		y = (tmpLong[0] << 24) + (tmpLong[1] << 16) + (tmpLong[2] << 8) + tmpLong[3];
		z =	(tmpLong[4] << 24) + (tmpLong[5] << 16)	+ (tmpLong[6] << 8) + tmpLong[7];

		// sum = delta<<5, in general sum = delta * n>>
		while (n-- > 0) {
			z -= (y << 4)+c ^ y+sum ^ (y >> 5) + d;
			y -= (z << 4)+a ^ z+sum ^ (z >> 5) + b;
			sum -= delta;
		}

		w[3] = (char) (y & 0xff);
		w[2] = (char) ((y >> 8)&0xff);
		w[1] = (char) ((y >> 16)&0xff);
		w[0] = (char) ((y >> 24)&0xff);

		w[7] = (char) (z&0xff);
		w[6] = (char) ((z >> 8)&0xff);
		w[5] = (char) ((z >> 16)&0xff);
		w[4] = (char) ((z >> 24)&0xff);

		return w;
	}

	public static String Decrypt(String ciperBuffer) {
		int j, i;
		int bLen = ciperBuffer.length();
		String plainBuffer = "";
		int k=0;

		char tmpStr[] = new char[2];
		char in[] = new char[8];
		char out[] = new char[8];

		for (i = 0, j = 0; j < bLen; j += 16, i += 2) {
			for(k=0; k<8; k++) {
				tmpStr[0] = ciperBuffer.charAt(j + 2*k);
				tmpStr[1] = ciperBuffer.charAt(j + 2*k + 1);
				in[k] = (char)getHexValue(tmpStr);
			}

			TEADecrypt(in, out);
			plainBuffer += new String(out);
		}

		return plainBuffer.trim();
	}

	public static char[] TEAEncrypt(char v[], char w[]) {
		int y;
		int z;
		int sum=0;
		int delta=0x9E3779B9;
		int a = encryptKey[0];
		int b = encryptKey[1];
		int c = encryptKey[2];
		int d = encryptKey[3];
		int n = 32;

		int tmpLong[] = new int[8];

		for (int i = 0; i < 8; i++) {
			tmpLong[i] = v[i];
		}

		y = (tmpLong[0] << 24)  + (tmpLong[1] << 16) + (tmpLong[2] << 8) + tmpLong[3];
		z = (tmpLong[4] << 24)  + (tmpLong[5] << 16) + (tmpLong[6] << 8) + tmpLong[7];

		while (n-- > 0) {
			sum += delta;
			y += (z << 4)+a ^ z + sum ^ (z >> 5) + b;
			z += (y << 4)+c ^ y+ sum ^ (y >> 5) + d;
		}

		w[3] = (char) (y & 0xff);
		w[2] = (char) ((y >> 8) & 0xff);
		w[1] = (char) ((y >> 16) & 0xff);
		w[0] = (char) ((y >> 24) & 0xff);

		w[7] = (char) (z & 0xff);
		w[6] = (char) ((z >> 8) & 0xff);
		w[5] = (char) ((z >> 16) & 0xff);
		w[4] = (char) ((z >> 24) & 0xff);

		return w;
	}

	public static String Encrypt(String plainBuffer) {
		int j, i, k;
		int bLen = plainBuffer.length();
		String ciperBuffer = "";

		char in[] = new char[8];
		char out[] = new char[8];

		/* Auto padding plainBuffer if need	*/
		for (i = 0, j = 0; j < bLen; j += 8, i += 2) {
			for(k=0; k<8; k++)
			{
				in[k] = 0;
			}
			for (k = 0; k < 8; k++) {
				if (bLen > j + k) {
					in[k] = plainBuffer.charAt(j+k);
				}
			}

			TEAEncrypt(in, out);

			for(k=0; k<8; k++)
			{
				ciperBuffer += getHex(out[k]);
			}
		}

		return ciperBuffer;
	}

	/**
	* Display bytes in HEX.
	* @param b bytes to display.
	* @return string representation of the bytes.
	*/
	public static String getHex(char b)
	{
		StringBuffer r = new StringBuffer();
		final char hex[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		int c = (b >> 4) & 0xf;
		r.append(hex[c]);
		c = (b & 0xf);
		r.append(hex[c]);

		return r.toString();
	}

	/**
	* Display bytes in HEX.
	* @param b bytes to display.
	* @return string representation of the bytes.
	*/
	public static int getHexValue(char b[])
	{
		StringBuffer r = new StringBuffer();
		final char hex[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		int hexValue = 0;
		int i=0;

		for(i=0; i<hex.length; i++)
		{
			if(hex[i] == b[0]) { hexValue = ( i << 4); break; }
		}

		for(i=0; i<hex.length; i++)
		{
			if(hex[i] == b[1]) { hexValue = hexValue + i; break; }
		}

		return hexValue;
	}
}
