package com.ztesoft.common.util;



public class TEA
{

    public static int encryptKey[] = {
        867, 742, 12, 452
    };

    public TEA()
    {
    }
    public static void main(String[] args) {
		//System.out.println(Decrypt("81c60f880c79194f"));
		//System.out.println(Encrypt("123456").equals("81c60f880c79194f"));
    	String string="6a796e756bd9c1eacd34c884aea81113";
		
    	System.out.println(Decrypt(string));
    	
	}
    private static char[] TEADecrypt(char ac[], char ac1[])
    {
        int k = 0xc6ef3720;
        int l = 0x9e3779b9;
        int i1 = encryptKey[0];
        int j1 = encryptKey[1];
        int k1 = encryptKey[2];
        int l1 = encryptKey[3];
        int i2 = 32;
        int ai[] = new int[8];
        for(int j2 = 0; j2 < 8; j2++)
            ai[j2] = ac[j2];

        int i = (ai[0] << 24) + (ai[1] << 16) + (ai[2] << 8) + ai[3];
        int j = (ai[4] << 24) + (ai[5] << 16) + (ai[6] << 8) + ai[7];
        while(i2-- > 0) 
        {
            j -= (i << 4) + k1 ^ i + k ^ (i >> 5) + l1;
            i -= (j << 4) + i1 ^ j + k ^ (j >> 5) + j1;
            k -= l;
        }
        ac1[3] = (char)(i & 0xff);
        ac1[2] = (char)(i >> 8 & 0xff);
        ac1[1] = (char)(i >> 16 & 0xff);
        ac1[0] = (char)(i >> 24 & 0xff);
        ac1[7] = (char)(j & 0xff);
        ac1[6] = (char)(j >> 8 & 0xff);
        ac1[5] = (char)(j >> 16 & 0xff);
        ac1[4] = (char)(j >> 24 & 0xff);
        return ac1;
    }

    /**
     * 解密
     * @param s
     * @return
     */
    public static String Decrypt(String s)
    {
        int k = s.length();
        String s1 = "";
        //boolean flag = false;
        char ac[] = new char[2];
        char ac1[] = new char[8];
        char ac2[] = new char[8];
        int j = 0;
        for(int i = 0; i < k;)
        {
            for(int l = 0; l < 8; l++)
            {
                ac[0] = s.charAt(i + 2 * l);
                ac[1] = s.charAt(i + 2 * l + 1);
                ac1[l] = (char)getHexValue(ac);
            }

            TEADecrypt(ac1, ac2);
            s1 = s1 + new String(ac2);
            i += 16;
            j += 2;
        }

        return s1.trim();
    }

    public static char[] TEAEncrypt(char ac[], char ac1[])
    {
        int k = 0;
        int l = 0x9e3779b9;
        int i1 = encryptKey[0];
        int j1 = encryptKey[1];
        int k1 = encryptKey[2];
        int l1 = encryptKey[3];
        int i2 = 32;
        int ai[] = new int[8];
        for(int j2 = 0; j2 < 8; j2++)
            ai[j2] = ac[j2];

        int i = (ai[0] << 24) + (ai[1] << 16) + (ai[2] << 8) + ai[3];
        int j;
        for(j = (ai[4] << 24) + (ai[5] << 16) + (ai[6] << 8) + ai[7]; i2-- > 0; j += (i << 4) + k1 ^ i + k ^ (i >> 5) + l1)
        {
            k += l;
            i += (j << 4) + i1 ^ j + k ^ (j >> 5) + j1;
        }

        ac1[3] = (char)(i & 0xff);
        ac1[2] = (char)(i >> 8 & 0xff);
        ac1[1] = (char)(i >> 16 & 0xff);
        ac1[0] = (char)(i >> 24 & 0xff);
        ac1[7] = (char)(j & 0xff);
        ac1[6] = (char)(j >> 8 & 0xff);
        ac1[5] = (char)(j >> 16 & 0xff);
        ac1[4] = (char)(j >> 24 & 0xff);
        return ac1;
    }
    /**
     * 加密
     * @param s
     * @return
     */
    public static String Encrypt(String s)
    {
        int j1 = s.length();
        String s1 = "";
        char ac[] = new char[8];
        char ac1[] = new char[8];
        int j = 0;
        for(int i = 0; i < j1;)
        {
            for(int k = 0; k < 8; k++)
                ac[k] = '\0';

            for(int l = 0; l < 8; l++)
                if(j1 > i + l)
                    ac[l] = s.charAt(i + l);

            TEAEncrypt(ac, ac1);
            for(int i1 = 0; i1 < 8; i1++)
                s1 = s1 + getHex(ac1[i1]);

            i += 8;
            j += 2;
        }

        return s1;
    }

    public static String getHex(char c)
    {
        StringBuffer stringbuffer = new StringBuffer();
        char ac[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
            'a', 'b', 'c', 'd', 'e', 'f'
        };
        int i = c >> 4 & 0xf;
        stringbuffer.append(ac[i]);
        i = c & 0xf;
        stringbuffer.append(ac[i]);
        return stringbuffer.toString();
    }

    public static int getHexValue(char ac[])
    {
        //StringBuffer stringbuffer = new StringBuffer();
        char ac1[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
            'a', 'b', 'c', 'd', 'e', 'f'
        };
        int i = 0;
        //boolean flag = false;
        for(int j = 0; j < ac1.length; j++)
        {
            if(ac1[j] != ac[0])
                continue;
            i = j << 4;
            break;
        }

        for(int k = 0; k < ac1.length; k++)
        {
            if(ac1[k] != ac[1])
                continue;
            i += k;
            break;
        }

        return i;
    }

    
 
    
}
