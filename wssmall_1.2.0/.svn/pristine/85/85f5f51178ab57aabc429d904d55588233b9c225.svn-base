package com.ztesoft.api.internal.utils;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-01-22 16:10
 * To change this template use File | Settings | File Templates.
 */
public class ApiUtils {

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !ApiUtils.isEmpty(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }


    public static boolean isNotBlank(CharSequence cs) {
        return !ApiUtils.isBlank(cs);
    }



}
