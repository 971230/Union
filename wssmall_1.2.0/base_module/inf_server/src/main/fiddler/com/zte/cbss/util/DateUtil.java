package com.zte.cbss.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class DateUtil {
	private static Logger logger = Logger.getLogger(DateUtil.class);
	public static final String SIMPLE_PATTERN = "yyyy-MM-dd";
	public static final String FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String SHORT_PATTERN = "yyyy-MM-dd hh:mm";
	public static final String HOURS_PATTERN = "HH:mm";
	
	public static String getFormatString(Date currentDate,String pattern){
		if(currentDate == null) {
			return null;
		}else {
			SimpleDateFormat dateformat =  new SimpleDateFormat(pattern);
			return dateformat==null?"":dateformat.format(currentDate);
		}
	}
	
	
	public static Date parseToDate(String dateStr) throws ParseException {
		return parseToDate(dateStr, FULL_PATTERN);
	}
	
	public static Date parseToDate(String dateStr, String pattern) {
		String orginalPattern = pattern;
		if(StringUtils.isEmpty(dateStr)) {
			return new Date();
		}
		if(StringUtils.isEmpty(pattern)) {
			pattern = SIMPLE_PATTERN;
		}
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat();
		try {
			format.applyPattern(pattern);
			date = format.parse(dateStr);
		} catch (ParseException e) {
			//nothing to do
		}
		if(date == null) {
			if(FULL_PATTERN.equals(pattern)) {
				format.applyPattern(SIMPLE_PATTERN);
			} else {
				format.applyPattern(FULL_PATTERN);
			}
			try {
				date = format.parse(dateStr);
			} catch (ParseException e) {
				throw new UnsupportedOperationException("DateUtil doesn't support the date pattern : " + orginalPattern);
			}
		}
		return date;
	}
	
	public static String format(Date date) {
		return format(date, SIMPLE_PATTERN);
	}

	public static String format(Date date, String pattern) {
		if(date == null) {
			return "";
		}
		if(StringUtils.isEmpty(pattern)) {
			pattern = SIMPLE_PATTERN;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	public static Date getCurrentDate() {
		return new Date();
	}
	
	/**
	 * 获取当前时间正负N天后的时间
	 * @Author:laigengbiao
	 * @Description: 
	 * @param days 正数为前推N天，负数为后推N天
	 * @return   
	 * @throws
	 */
	public static Date addDateByDays(int days){
		return addDateByDays(getCurrentDate(),days);
	}
	/**
	 * 获取指定时间的正负N天后的时间
	 * @Author:laigengbiao
	 * @Description: 
	 * @param source	指定时间
	 * @param days		正数为前推N天，负数为后推N天
	 * @return   
	 * @throws
	 */
	public static Date addDateByDays(Date source, int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(source);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}
	
	/**
	 * 获取当前时间正负N月后的时间
	 * @Author:laigengbiao
	 * @Description: 
	 * @param months	正数为前推N月，负数为后推N月
	 * @return   
	 * @throws
	 */
	public static Date addDateByMonths(int months){
		return addDateByMonths(new Date(),months);
	}
	/**
	 * 获取指定时间的正负N月后的时间
	 * @Author:laigengbiao
	 * @Description: 
	 * @param source	指定时间
	 * @param days		正数为前推N月，负数为后推N月
	 * @return   
	 * @throws
	 */
	public static Date addDateByMonths(Date source, int months){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(source);
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}
	/**
	 * @throws ParseException 
	 * 获取短日期（2012-12-21）
	 * @Author:laigengbiao
	 * @Description: 
	 * @param date
	 * @return   
	 * @throws
	 */
	public static Date getShortDate(Date date) throws ParseException {
		return parseToDate(format(date, SIMPLE_PATTERN)); 
	}
	
	public static String[] getPreviousNDays(int n) {
		String[] days = new String[Math.abs(n)];
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		int i = 0;
		
		for (int j = n + 1; j <= -1; j++) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, j);
			
			days[i] = df.format(calendar.getTime());
			
			i++;
		}
		
		days[days.length - 1] = df.format(new Date());
		
		return days;
	}
	
	/**
	 * @author LiJingYing(lijy@cf-ec.cn)
	 * @Description 获取与当前时间间隔days天的时间字符串表示
	 * @createTime 2013-12-10 下午1:42:59 
	 * @updateTime 2013-12-10 下午1:42:59  
	 * @param days		间隔时间
	 * @param pattern	时间格式
	 * @return   
	 * @return String    返回类型 
	 * @throws
	 */
	public static String getPreviousDay(Integer days,String pattern){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, days);
		String day = format(calendar.getTime(),pattern);
		return day;
	}
	
	public static Date strToDate(String input, String format) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			
			return df.parse(input);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获得Year年份列表。根据参数增量来，正整数获取从今年起(包括今年)后面多少年，附整数从今年(包括今年)算起的前多少年。
	 * 
	 * @param increment
	 * @return
	 */
	public static List<Integer> getYearList(int increment, boolean isContainThisYear){
		
		List<Integer> result = new ArrayList<Integer>();

		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		
		if(isContainThisYear){
			result.add(thisYear);
		}else{
			increment = increment > 0 ? increment + 1 : increment - 1; 
		}
			
		
		if(increment > 0 ){
			
			for(int x = 1; x < increment; x++){
				result.add(++thisYear);
			}
			
		}else{
			
			for(int x = -1; x > increment; x--){
				result.add(--thisYear);
			}
			
		}
		
		
		return result;
		
	}
	
	/**
	 * 获得newDate - oldDate的时间差，单位为秒
	 * @param newDate
	 * @param oldDate
	 * @return
	 */
	public static int getTimeDifference(Date newDate,Date oldDate){
		long timeDifference = newDate.getTime() - oldDate.getTime();
		return (int)(timeDifference/1000);
	}
	
	/**
	 * 比较日期大小
	 * 
	 * @param srcDate
	 * @param targetDate
	 * @return
	 */
	public static int compareDate(String srcDate, String targetDate, String format) {
     try {
    	 DateFormat df = new SimpleDateFormat(format);
    	 Date dt1 = df.parse(srcDate);
         Date dt2 = df.parse(targetDate);
         if (dt1.getTime() < dt2.getTime()) {
             return 1;
         } else if (dt1.getTime() > dt2.getTime()) {
             return -1;
         } else {
             return 0;
         }
     } catch (Exception exception) {
         exception.printStackTrace();
     }
     return 0;
    }
	
	/**
	 * 比较日期大小
	 * 
	 * @param srcDate
	 * @param targetDate
	 * @return
	 */
	public static int compareDate(Date srcDate, Date targetDate, String format) {
		DateFormat df = new SimpleDateFormat(format);
		return compareDate(df.format(srcDate), df.format(targetDate), format);
	}
	
	/**
	 * 将过期时间转化成剩余多少 D(天) 多少小时(H)
	 * @return
	 */
	public static String getSurplusTime(Date date){
		long between=(date.getTime() - new Date().getTime())/1000;//除以1000，转换成秒
		long days=between/(24*3600);
		long hours=between%(24*3600)/3600;
		if (days < 0) {
			days = 0;
		}
		if (hours < 0) {
			hours = 0;
		}
		return days+"D "+hours+"H";
	}
	
	/**
	 * 获取指定时间的正负N小时后的时间
	 * @Author:zl
	 * @Description: 
	 * @param source	指定时间
	 * @param hours		正数为前推N小时，负数为后推N小时
	 * @return   
	 * @throws
	 */
	public static Date addDateByHours(Date source, int hours){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(source);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		return calendar.getTime();
	}
	
	/**
	 * @author LiJingYing(lijy@cf-ec.cn)
	 * @Description 计算当前时间和date相隔多少个小时 ，结果向上取整。
	 * @createTime 2013-12-6 下午4:20:32 
	 * @updateTime 2013-12-6 下午4:20:32  
	 * @param date	要比较的时间
	 * @return   
	 * @return Integer    返回相差的时间，单位小时 。
	 * @throws
	 */
	public static Integer getTimeDifference(String date){
		Long currentTime = new Date().getTime();
		Long comparedTime = DateUtil.parseToDate(date, DateUtil.FULL_PATTERN).getTime();
		Long differenceTime = currentTime - comparedTime;
		Integer hour = (int)Math.ceil((differenceTime/(1000*60*60)));
		return hour;
	}
	
	public static void main(String[] args) {
		logger.info(getTimeDifference("2013-12-6 13:20:26"));
		logger.info(Integer.valueOf(format(new Date(), "yyyyMMdd")));
		logger.info(Math.floor(1.2));
		logger.info(Math.ceil(1.2));
		logger.info(Arrays.toString(getPreviousNDays(30)));
		logger.info(getPreviousDay(30,"yyyyMMdd"));
	}
}
