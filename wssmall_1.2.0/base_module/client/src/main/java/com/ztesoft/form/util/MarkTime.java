package com.ztesoft.form.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 标记时间工具类
 * @author zhaoc
 *
 */
public class MarkTime {
	private Logger log = Logger.getLogger(this.getClass());
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private Date start = new Date();
	
	private String pre_fix = "MarkTime";
	
	/**
	 * 初始化对象标记开始时间
	 */
	public MarkTime(){
		log.error(this.pre_fix+" start time:"+format.format(start));
	}
	
	/**
	 * 初始化对象标记开始时间
	 * @param pre_fix 前戳
	 */
	public MarkTime(String pre_fix){
		this.pre_fix = pre_fix;
		
		log.error(this.pre_fix+" start time:"+format.format(start));
	}
	
	/**
	 * 标记结束时间
	 */
	public void markEndTime(){
		Date end = new Date();
		
		log.error(this.pre_fix+" end time:"+format.format(end));
		
		log.error(this.pre_fix+" cost time:"+(end.getTime()-this.start.getTime())+"ms");
	}
	
	/**
	 * 标记结束时间
	 * @param pre_fix 前戳
	 */
	public void markEndTime(String pre_fix){
		this.pre_fix = pre_fix;
		
		Date end = new Date();
		
		log.error(this.pre_fix+" end time:"+format.format(end));
		
		log.error(this.pre_fix+" cost time:"+(end.getTime()-this.start.getTime())+"ms");
	}


}
