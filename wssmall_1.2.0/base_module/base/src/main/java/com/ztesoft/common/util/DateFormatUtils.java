package com.ztesoft.common.util;

import java.security.InvalidParameterException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.DAOUtils;

/**
 * <p>
 * Description: 时间以及时间格式相关的处理功能
 * </p>
 * <p>
 * Copyright 2006 ZTEsoft Corp.
 * </p>
 * 
 * @Create Date : 2006-4-19
 * @Version : 1.0
 */
public class DateFormatUtils {
	private static Logger logger = Logger.getLogger(DateFormatUtils.class);
	private static final String DATEFORMAT_STR_ORACLEDB = "yyyy-mm-dd hh24:mi:ss";
	private static final String DATEFORMAT_STR_INFORMIXDB = "%Y-%m-%d %H:%M:%S";
	private static String DATEFORMAT_STR_DB = null;
	private static final String DATEFORMAT_STR_ORACLEDB_SHORT = "yyyy-mm-dd";
	private static final String DATEFORMAT_STR_INFORMIXDB_SHORT = "%Y-%m-%d";
	private static String DATEFORMAT_STR_DB_SHORT = null;

	/**
	 * 得到应用服务器当前日期，以默认格式显示。
	 * 
	 * @return
	 */
	public static String getFormatedDate() {
		Date date = getCurrentDate();
		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_FORMAT);
		return dateFormator.format(date);

	}

	/**
	 * 得到应用服务器当前日期时间，以默认格式显示。
	 * 
	 * @return
	 */
	public static String getFormatedDateTime() {

		Date date = getCurrentDate();
		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		return dateFormator.format(date);

	}
	/**
	 * 得到应用服务器当前日期时间，带毫秒，以毫秒格式显示。
	 * 
	 * @return
	 */
	public static String getFormatedDateTime23() {
		
		Date date = getCurrentDate();
		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT23);
		return dateFormator.format(date);
		
	}
	
	/**
	 * 得到应用服务器当前日期时间，带毫秒，以毫秒格式显示。
	 * 
	 * @return
	 */
	public static String getFormatedDateTime8() {
		
		Date date = getCurrentDate();
		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_FORMAT_8);
		return dateFormator.format(date);
	}
	
	/**
	 * 得到应用服务器当前日期时间，带毫秒，以毫秒格式显示。
	 * 
	 * @return
	 */
	public static String getFormatedDateTime14() {
		
		Date date = getCurrentDate();
		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT_14);
		return dateFormator.format(date);
	}

	/**
	 * 得到应用服务器的当前时间
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 得到应用服务器的当前时间，毫秒。
	 * 
	 * @return
	 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 得到应用服务器当前日期 按照指定的格式返回。
	 * 
	 * @param pattern
	 *            格式类型，通过系统常量中定义，如：CrmConstants.DATE_FORMAT_8
	 * @return
	 */
	public static String formatDate(String pattern) {

		Date date = new Date();
		SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);
		String str = dateFormator.format(date);

		return str;
	}

	/**
	 * 转换输入日期 按照指定的格式返回。
	 * 
	 * @param date
	 * @param pattern
	 *            格式类型，通过系统常量中定义，如：CrmConstants.DATE_FORMAT_8
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {

		if (date == null)
			return "";

		SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);
		String str = dateFormator.format(date);

		return str;
	}

	/**
	 * 转换输入日期 按照指定的格式返回。
	 * 
	 * @param date
	 * @param pattern
	 *            格式类型，通过系统常量中定义，如：CrmConstants.DATE_FORMAT_8
	 * @param loc
	 *            locale
	 * @return
	 */
	public static String formatDate(Date date, String pattern, Locale loc) {
		if (pattern == null || date == null) {
			return "";
		}
		String newDate = "";
		if (loc == null)
			loc = Locale.getDefault();
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern, loc);
			newDate = sdf.format(date);
		}
		return newDate;
	}

	/**
	 * 将字符时间从一个格式转换成另一个格式。时间的格式，最好通过系统常量定义。 如：
	 * String dateStr = "2006-10-9 12:09:08";
	 * DateFormatUtils.convertDateFormat(dateStr,
	 * CrmConstants.DATE_TIME_FORMAT,
	 * CrmConstants.DATE_FORMAT_8);
	 * 
	 * @param sdate
	 * @param patternFrom
	 *            格式类型，通过系统常量中定义，如：CrmConstants.DATE_FORMAT_8
	 * @param patternTo
	 *            格式类型，通过系统常量中定义，如：CrmConstants.DATE_FORMAT_8
	 * @return
	 */
	public static String convertDateFormat(String dateStr, String patternFrom, String patternTo) {

		if (dateStr == null || patternFrom == null || patternTo == null) {
			return "";
		}

		String newDate = "";

		try {

			Date dateFrom = parseStrToDate(dateStr, patternFrom);
			newDate = formatDate(dateFrom, patternTo);

		} catch (Exception e) {
		}

		return newDate;
	}
	
	public static String convertDate1(String date){
		if(date == null || "".equals(date)){
			return "";
		}
		String retDate = "";
		retDate =  convertDateFormat(date,CrmConstants.DATE_TIME_FORMAT,CrmConstants.DATE_TIME_FORMAT_14);
		if("".equals(retDate)){
			retDate =  convertDateFormat(date,CrmConstants.DATE_FORMAT,CrmConstants.DATE_TIME_FORMAT_14);
		}
		return retDate;
	}
	
	public static java.sql.Date convertDate2(String date){
		if(date == null || "".equals(date)){
			return null;
		}
		java.sql.Date retDate = null;
		Date dateTemp = parseStrToDate(date, CrmConstants.DATE_TIME_FORMAT_14);
		
		retDate = new java.sql.Date(dateTemp.getTime());
		return retDate;
	}

	/**
	 * 将时间串按照默认格式CrmConstants.DATE_FORMAT，格式化成Date。
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseStrToDate(String dateStr) {

		if (null == dateStr || "".equals(dateStr))
			return null;

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_FORMAT);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		return tDate;
	}

	public static String parseDateStrToDateTimeStr(String dateStr) {

		if (null == dateStr || "".equals(dateStr))
			return null;

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_FORMAT);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));
		return formatDate(tDate, CrmConstants.DATE_TIME_FORMAT);

	}

	/**
	 * 将时间串按照默认格式CrmConstants.DATE_FORMAT，格式化成Date。
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Calendar parseStrToCalendar(String dateStr) {

		if (null == dateStr || "".equals(dateStr))
			return null;

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_FORMAT);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		Locale loc = Locale.getDefault();
		Calendar cal = new GregorianCalendar(loc);
		cal.setTime(tDate);

		return cal;
	}

	/**
	 * 将时间串按照默认格式CrmConstants.DATE_TIME_FORMAT，格式化成Date。
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseStrToDateTime(String dateStr) {

		if (null == dateStr || "".equals(dateStr))
			return null;

		SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		return tDate;
	}

	/**
	 * 将时间串按照指定格式，格式化成Date。
	 * 
	 * @param date
	 * @param pattern
	 *            格式类型，通过系统常量中定义，如：CrmConstants.DATE_FORMAT_8
	 * @return
	 */

	public static Date parseStrToDate(String dateStr, String pattern) {
		if (null == dateStr || "".equals(dateStr))
			return null;

		SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		return tDate;
	}

	/**
	 * 按时间格式相加：
	 * 输入要相加的时间基点（字符串型或时间类型），相加的时长（整型），格式（"year"年、"month"月、"day"日、”hour“时、”minute“分、”second“秒、"week"周）
	 * 输出相加后的时间（字符串型或时间类型）
	 * 
	 * @param dateStr
	 * @param pattern
	 * @param step
	 * @param type
	 *            "year"年、"month"月、"day"日、”hour“时、”minute“分、”second“秒、"week"周
	 *            通过常量DateFormatUtils.YEAR等来设置.
	 * @return
	 */
	public static String addDate(String dateStr, String pattern, int step, String type) {
		if (dateStr == null) {
			return dateStr;
		} else {
			Date date1 = DateFormatUtils.parseStrToDate(dateStr, pattern);

			Locale loc = Locale.getDefault();
			Calendar cal = new GregorianCalendar(loc);
			cal.setTime(date1);

			if (DateFormatUtils.WEEK.equals(type)) {

				cal.add(Calendar.WEEK_OF_MONTH, step);

			} else if (DateFormatUtils.YEAR.equals(type)) {

				cal.add(Calendar.YEAR, step);

			} else if (DateFormatUtils.MONTH.equals(type)) {

				cal.add(Calendar.MONTH, step);

			} else if (DateFormatUtils.DAY.equals(type)) {

				cal.add(Calendar.DAY_OF_MONTH, step);

			} else if (DateFormatUtils.HOUR.equals(type)) {

				cal.add(Calendar.HOUR, step);

			} else if (DateFormatUtils.MINUTE.equals(type)) {

				cal.add(Calendar.MINUTE, step);

			} else if (DateFormatUtils.SECOND.equals(type)) {

				cal.add(Calendar.SECOND, step);

			}

			return DateFormatUtils.formatDate(cal.getTime(), pattern);
		}
	}

	/**
	 * 按时间格式相减：
	 * 输入要相加的时间基点（字符串型或时间类型），相加的时长（整型），格式（"year"年、"month"月、"day"日、”hour“时、”minute“分、”second“秒、"week"周）
	 * 输出相加后的时间（字符串型或时间类型）
	 * 
	 * @param dateStr
	 * @param pattern
	 * @param step
	 * @param type
	 *            "year"年、"month"月、"day"日、”hour“时、”minute“分、”second“秒、"week"周
	 * @return
	 */
	public static String minusDate(String dateStr, String pattern, int step, String type) {

		return addDate(dateStr, pattern, (0 - step), type);

	}

	/**
	 * 日期增加天数
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDay(Date date, int days) {
		if (date == null) {
			return date;
		} else {
			Locale loc = Locale.getDefault();
			Calendar cal = new GregorianCalendar(loc);
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, days);
			return cal.getTime();
		}
	}

	public static int getDays(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return 0;
		else
			return (int) ((date2.getTime() - date1.getTime()) / 0x5265c00L);
	}

	/**
	 * 日期比较大小
	 * 
	 * @param dateStr1
	 * @param dateStr2
	 * @param pattern
	 * @return
	 */
	public static int compareDate(String dateStr1, String dateStr2, String pattern) {

		Date date1 = DateFormatUtils.parseStrToDate(dateStr1, pattern);
		Date date2 = DateFormatUtils.parseStrToDate(dateStr2, pattern);

		return date1.compareTo(date2);

	}

	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getFirstDayInMonth(String dateStr, String pattern) {
		Calendar cal = DateFormatUtils.parseStrToCalendar(dateStr);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		cal.add(Calendar.DAY_OF_MONTH, (1 - day));

		return DateFormatUtils.formatDate(cal.getTime(), pattern);
	}

	/**
	 * @param dateStr
	 * @param pattern
	 * @author mzp
	 * @time 20080717
	 * @return
	 */
	public static String getFirstDayInMonth(String dateStr, String pattern,String isZeroSecond) {
		Calendar cal = DateFormatUtils.parseStrToCalendar(dateStr);
		int year=cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour=cal.get(Calendar.HOUR);
		int minute=cal.get(Calendar.MINUTE);
		int second=cal.get(Calendar.SECOND);
		day=1;hour=0;minute=0;second=0;
		cal.clear();
		cal.set(year, month, day, hour, minute, second);
		return DateFormatUtils.formatDate(cal.getTime(), pattern);
	}
	
	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getLastDayInMonth(String dateStr, String pattern) {
		Calendar cal = DateFormatUtils.parseStrToCalendar(dateStr);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		int maxDayInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int step = maxDayInMonth - day;

		cal.add(Calendar.DAY_OF_MONTH, step);

		return DateFormatUtils.formatDate(cal.getTime(), pattern);
	}
	
	/***
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return the last day of provided month
	 * @throws InvalidParameterException
	 */
	public static String getLastDayInMonthWithoutDefaultFormat(String dateStr,String pattern) throws InvalidParameterException
	{
		if(null==dateStr || "".equalsIgnoreCase(dateStr) || null==pattern || "".equalsIgnoreCase(pattern))
			throw new InvalidParameterException("the parameters must not be null or empty");
		
		SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);

		java.util.Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		Locale loc = Locale.getDefault();
		Calendar cal = new GregorianCalendar(loc);
		cal.setTime(tDate);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		int maxDayInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int step = maxDayInMonth - day;

		cal.add(Calendar.DAY_OF_MONTH, step);

		return DateFormatUtils.formatDate(cal.getTime(), pattern);
	}

	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getFirstDayInWeek(String dateStr, String pattern) {
		Calendar cal = DateFormatUtils.parseStrToCalendar(dateStr);
		int day = cal.get(Calendar.DAY_OF_WEEK);

		cal.add(Calendar.DAY_OF_MONTH, (1 - day));

		return DateFormatUtils.formatDate(cal.getTime(), pattern);
	}

	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static String getLastDayInWeek(String dateStr, String pattern) {
		Calendar cal = DateFormatUtils.parseStrToCalendar(dateStr);
		int day = cal.get(Calendar.DAY_OF_WEEK);

		cal.add(Calendar.DAY_OF_MONTH, (6 - day));

		return DateFormatUtils.formatDate(cal.getTime(), pattern);
	}

	public static long calendarDayPlus(String dateStr1, String dateStr2) {
		
		if (dateStr1 == null || dateStr2 == null||dateStr1.equals("")||dateStr2.equals(""))
			return 0;
		Date date1 = DateFormatUtils.parseStrToDate(dateStr1);
		Date date2 = DateFormatUtils.parseStrToDate(dateStr2);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		long t1 = c1.getTimeInMillis();
		long t2 = c2.getTimeInMillis();
		long day = (t2 - t1) / (1000 * 60 * 60 * 24);
		return day;
	}
	
	/**
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static int calendarPlus(String dateStr1, String dateStr2) {
		
		if (dateStr1 == null || dateStr2 == null||dateStr1.equals("")||dateStr2.equals(""))
			return 0;
		
		Calendar cal1 = DateFormatUtils.parseStrToCalendar(dateStr1);
		
		Calendar cal2 = DateFormatUtils.parseStrToCalendar(dateStr2);
		
		int dataStr1Year = cal1.get(Calendar.YEAR);
		int dataStr2Year = cal2.get(Calendar.YEAR);
		
		int dataStr1Month = cal1.get(Calendar.MONTH);
		int dataStr2Month = cal2.get(Calendar.MONTH);


		return ((dataStr2Year*12+dataStr2Month+1)-(dataStr1Year*12+dataStr1Month));
		
	}
	
	/**
	 * 获取数据库的当前时间
	 * @return
	 * @throws FrameException
	 */
	public static String getCurrentFromDB() throws FrameException{
		String sql="";
		if(CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.CRM_DATABASE_TYPE)){
			sql = "select to_char(current,'%Y-%m-%d %H:%M:%S') curentDate from dual"  ;
		}else if(CrmConstants.DB_TYPE_ORACLE.equalsIgnoreCase(CrmConstants.CRM_DATABASE_TYPE)){
			sql = "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') curentDate from dual" ;
		}else {
			//根据不同数据进行修改
			sql = "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') curentDate from dual" ;
		}
		return Base.query_string(JNDINames.DEFAULT_DATASOURCE, sql, -210020502);
	}
	
	public static String getAddDaysToDBDateString(int addedDays) {
		if (DAOUtils.isInformixDB()) {
			return " " + addedDays + " UNITS day ";
		} else {
			return  " " + addedDays + " ";
		}
	}


	public static void main(String[] argv) {

		String dateStr = "2004-2-18 12:13:34";
		String dateStr1 = "2006-7-18 12:13:34";
		String dateStr2 = "2006-7-18 12:13:34";
		// add
		//Date date = DateFormatUtils.addDay(DateFormatUtils.parseStrToDate(dateStr, CrmConstants.DATE_TIME_FORMAT), 20);

		//logger.debug("after add " + DateFormatUtils.formatDate(date, CrmConstants.DATE_TIME_FORMAT));
		// add
 
		// sub
        logger.info(DateFormatUtils.convertDate1("2006-7-18"));

		// get day
		// logger.debug(DateFormatUtils.parseStrToCalendar(dateStr).get(
		// Calendar.DAY_OF_MONTH));
		// get month 要加1才是当前月数
		// logger.debug(DateFormatUtils.parseStrToCalendar(dateStr).get(
		// Calendar.MONTH) + 1);
		// // get year
		// logger.debug(DateFormatUtils.parseStrToCalendar(dateStr).get(
		// Calendar.YEAR));
		// // get hour
		// logger.debug(DateFormatUtils.parseStrToCalendar(dateStr).get(
		// Calendar.HOUR_OF_DAY));
		// // get minute
		// logger.debug(DateFormatUtils.parseStrToCalendar(dateStr).get(
		// Calendar.MINUTE));
		// // get second
		// logger.debug(DateFormatUtils.parseStrToCalendar(dateStr).get(
		// Calendar.SECOND));
		//
		// // compareDate
		// logger.debug(DateFormatUtils.compareDate(dateStr1,
		// dateStr2,
		// CrmConstants.DATE_TIME_FORMAT));

		// first or last
		// logger.debug(DateFormatUtils.getFirstDayInWeek(dateStr,
		// CrmConstants.DATE_TIME_FORMAT));
		// logger.debug(DateFormatUtils.getLastDayInWeek(dateStr,
		// CrmConstants.DATE_TIME_FORMAT));
		// logger.debug(DateFormatUtils.getFirstDayInMonth(dateStr,
		// CrmConstants.DATE_TIME_FORMAT));
		// logger.debug(DateFormatUtils.getLastDayInMonth(dateStr,
		// CrmConstants.DATE_TIME_FORMAT));
		//
		// logger.debug(DateFormatUtils.parseStrToCalendar(dateStr)
		// .getActualMaximum(Calendar.DAY_OF_MONTH));

	}
	
	public static String getDBDateFormatStr() {
		if (DATEFORMAT_STR_DB == null) {
			if (DAOUtils.isOracleDB()) {
				DATEFORMAT_STR_DB = DATEFORMAT_STR_ORACLEDB;
			} else if(DAOUtils.isInformixDB()){
				DATEFORMAT_STR_DB = DATEFORMAT_STR_INFORMIXDB;
			}
		}		
		return DATEFORMAT_STR_DB;
	}
	
	public static String getDBDateFormatStr_Short() {
		if (DATEFORMAT_STR_DB_SHORT == null) {
			if (DAOUtils.isOracleDB()) {
				DATEFORMAT_STR_DB_SHORT = DATEFORMAT_STR_ORACLEDB_SHORT;
			} else if(DAOUtils.isInformixDB()){
				DATEFORMAT_STR_DB_SHORT = DATEFORMAT_STR_INFORMIXDB_SHORT;
			}
		}		
		return DATEFORMAT_STR_DB_SHORT;
	}
	
	
	/**
	 * 计算时间差 
	 * added by cai.zhengluan @ 2013/9/25
	 * 
	 * @param date1
	 * @param date2
	 * @param type "year"年、"month"月、"day"日、”hour“时、”minute“分、”second“秒、"week"周
	 * @return
	 */
	public static long cal4Dates(Date date1, Date date2, String type) {
		if (date1 != null && date2 != null) {
			if (YEAR.equals(type)) {//计算时间的年数差
				Calendar c1 = Calendar.getInstance();
				c1.setTime(date1);
				Calendar c2 = Calendar.getInstance();
				c2.setTime(date2);
				int year1 = c1.get(Calendar.YEAR);
				int year2 = c2.get(Calendar.YEAR);
				int y = year2 - year1;
				c1.add(Calendar.YEAR, y);
				return c1.after(c2) ? y - 1 : y;
			} else if (MONTH.equals(type)) {//计算时间的月数差
				Calendar c1 = Calendar.getInstance();
				c1.setTime(date1);
				Calendar c2 = Calendar.getInstance();
				c2.setTime(date2);
				int year1 = c1.get(Calendar.YEAR);
				int month1 = c1.get(Calendar.MONTH);
				int year2 = c2.get(Calendar.YEAR);
				int month2 = c2.get(Calendar.MONTH);
				int m = 12 * (year2 - year1) + month2 - month1;
				c1.add(Calendar.MONTH, m);
				return c1.after(c2) ? m - 1 : m;
			} else if (WEEK.equals(type)) {//计算时间的周数差
				long days = (date2.getTime() - date1.getTime()) / 0x5265C00L;//(1000 * 60 * 60 * 24)
				return days / 7;
			} else if (DAY.equals(type)) {//计算时间的天数差
				return (date2.getTime() - date1.getTime()) / 0x5265C00L;//(1000 * 60 * 60 * 24)
			} else if (HOUR.equals(type)) {//计算时间的小时数差
				return (date2.getTime() - date1.getTime()) / 0x36EE80L;//(1000 * 60 * 60)
			} else if (MINUTE.equals(type)) {//计算时间的分钟数差
				return (date2.getTime() - date1.getTime()) / 0xEA60L;//(1000 * 60)
			} else if (SECOND.equals(type)) {//计算时间的秒钟数差
				return (date2.getTime() - date1.getTime()) / 1000;
			}
		}
		return 0L;
	}
	
	public final static String YEAR = "year";

	public final static String MONTH = "month";

	public final static String DAY = "day";

	public final static String HOUR = "hour";

	public final static String MINUTE = "minute";

	public final static String SECOND = "second";

	public final static String WEEK = "week";

}
