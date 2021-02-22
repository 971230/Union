package com.ztesoft.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class DataUtil {
	private static Pattern WEAKPASSWORDP = Pattern.compile("[0-9]+|[a-zA-Z]+");
	
	public static boolean ifEmpty(String str ){
		return str == null || "".equals(str.trim()) ;
	}

    public static java.sql.Date getNextDate(String date, int n) {
         Calendar gc = Calendar.getInstance();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dtime = df.parse(date);
            gc.setTime(dtime);
            gc.add(Calendar.DAY_OF_MONTH, n);
            gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new java.sql.Date(gc.getTime().getTime());
    }
    

    /**
     * 对日期进行格式化
     * 格式为：yyyyMMdd
     * @param date 需格式化的日期
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date) {
    	SimpleDateFormat obj = obj = new SimpleDateFormat("yyyyMMdd");
        return obj.format(date);
    }
    
    /**
     * 对日期进行格式化
     * 格式为：yyyy-MM-dd
     * @param date 需格式化的日期
     * @return 格式化后的字符串
     */
    public static String formatDate2(Date date) {
    	SimpleDateFormat obj = obj = new SimpleDateFormat("yyyy-MM-dd");
        return obj.format(date);
    }
    
    public static boolean isWeakPassword(String password) {
    	if (password == null || password.length() < 5) {
			return true;
		}
    	
		return WEAKPASSWORDP.matcher(password).matches();
	}
    
}
