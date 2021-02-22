package com.powerise.ibss.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 */


public final class NumberTools {

    public final static short getShort(byte[] buf) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }
        if (buf.length > 2) {
            throw new IllegalArgumentException("byte array size > 2 !");
        }
        short r = 0;
        for (int i = buf.length - 1; i >= 0; i--) {
            r <<= 8;
            r |= (buf[i] & 0x00ff);
        }
        return r;
    }

    public final static short getNetShort(byte[] buf) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }
        if (buf.length > 2) {
            throw new IllegalArgumentException("byte array size > 2 !");
        }
        short r = 0;
        for (int i =  0; i < buf.length; i++) {
            r <<= 8;
            r |= (buf[i] & 0x00ff);
        }
        return r;
    }

    public final static byte[] getBytes(short s) {
        byte[] buf = new byte[2];
        for (int i = 0; i < buf.length; i ++) {
            buf[i] = (byte)(s & 0x00ff);
            s >>= 8;
        }
        return buf;
    }

    public final static byte[] getNetBytes(short s) {
        byte[] buf = new byte[2];
        for (int i = buf.length-1; i >= 0 ; i --) {
            buf[i] = (byte)(s & 0x00ff);
            s >>= 8;
        }
        return buf;
    }

    public final static int getInt(byte[] buf) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }
        if (buf.length > 4) {
            throw new IllegalArgumentException("byte array size > 4 !");
        }
        int r = 0;
        for (int i = buf.length - 1; i >= 0; i--) {
            r <<= 8;
            r |= (buf[i] & 0x000000ff);
        }
        return r;
    }

    public final static int getNetInt(byte[] buf) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }
        if (buf.length > 4) {
            throw new IllegalArgumentException("byte array size > 4 !");
        }
        int r = 0;
        for (int i = 0; i < buf.length; i++) {
            r <<= 8;
            r |= (buf[i] & 0x000000ff);
        }
        return r;
    }

    public final static byte[] getBytes(int s) {
        byte[] buf = new byte[4];
        for (int i = 0; i < buf.length; i ++) {
            buf[i] = (byte)(s & 0x000000ff);
            s >>= 8;
        }
        return buf;
    }

    public final static byte[] getNetBytes(int s) {
        byte[] buf = new byte[4];
        for (int i = buf.length - 1; i >= 0; i --) {
            buf[i] = (byte)(s & 0x000000ff);
            s >>= 8;
        }
        return buf;
    }

    public final static long getLong(byte[] buf) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }
        if (buf.length > 8) {
            throw new IllegalArgumentException("byte array size > 8 !");
        }
        long r = 0;
        for (int i = buf.length - 1; i >= 0; i--) {
            r <<= 8;
            r |= (buf[i] & 0x00000000000000ff);
        }
        return r;
    }

    public final static long getNetLong(byte[] buf) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }
        if (buf.length > 8) {
            throw new IllegalArgumentException("byte array size > 8 !");
        }
        long r = 0;
        for (int i =  0; i<buf.length; i++) {
            r <<= 8;
            r |= (buf[i] & 0x00000000000000ff);
        }
        return r;
    }

    public final static byte[] getBytes(long s) {
        byte[] buf = new byte[8];
        for (int i = 0; i < buf.length; i ++) {
            buf[i] = (byte)(s & 0x00000000000000ff);
            s >>= 8;
        }
        return buf;
    }

    public final static byte[] getNetBytes(long s) {
        byte[] buf = new byte[8];
        for (int i = buf.length - 1; i >= 0 ; i-- ) {
            buf[i] = (byte)(s & 0x00000000000000ff);
            s >>= 8;
        }
        return buf;
    }
}
