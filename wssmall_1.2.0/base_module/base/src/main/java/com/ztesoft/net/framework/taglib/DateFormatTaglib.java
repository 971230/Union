package com.ztesoft.net.framework.taglib;

import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.net.framework.util.DateUtil;
import com.ztesoft.net.framework.util.StringUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import java.util.Date;

public class DateFormatTaglib extends EnationTagSupport {
	private Long time;
	private String times;
	
	private String d_time;
	private String pattern;
	
	@Override
	public int doEndTag() throws JspException {
		
//		if( time==null || time.equals("") ){
//			this.print("");
//			return this.EVAL_BODY_INCLUDE;
//		}
		String str="";
		if(!StringUtil.isEmpty(d_time)) {
			if (d_time.lastIndexOf(".0")>-1) d_time = d_time.substring(0,d_time.length()-2);
			if (null != d_time && d_time.length() == 10) d_time = d_time + " 00:00:00";
			 str  = DateFormatUtils.formatDate(DateFormatUtils.formatStringToDate(d_time) ,pattern);
		}else{
			if(StringUtil.isEmpty(times))
				times ="0";
			time = times== null?time:Long.valueOf(times);
			if(time ==0){
				this.print("");
				return Tag.EVAL_BODY_INCLUDE;
			}
			
			Date date = new Date(time);
			str  = DateUtil.toString(date, pattern);
			
		}
		this.print(str);
		return Tag.EVAL_BODY_INCLUDE;
	}

	
	@Override
	public int doStartTag() throws JspException {
		return Tag.EVAL_PAGE;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}


	public String getD_time() {
		return d_time;
	}


	public void setD_time(String d_time) {
		this.d_time = d_time;
	}
	
	
}
