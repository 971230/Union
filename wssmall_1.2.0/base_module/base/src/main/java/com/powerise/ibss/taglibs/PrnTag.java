package com.powerise.ibss.taglibs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
public class PrnTag extends TagSupport {
	private String  m_item     = null ;//数据条目
	private String  m_value    = null ;//值
	private String  m_def      = null ;//缺省值
	private boolean m_is_html  = false ;//是否输出HTML
	private String  m_scope    = null ;//缺省值
	private String  m_pattern  = null ;//匹配模式
	private String  m_cond     = null ;//条件 格式 `f_name=lgm`tel=13607435681 此不为空时间，要求有m_item取得的值为ArrayList 同输出的值有m-value指定
	
	public PrnTag() {
		super();
		init();
	}
	public void init() {
		m_item   = null;
		m_value  = null;
		m_def    = null;
		m_is_html= false;
		m_cond   = null;
	}
	@Override
	public void release() {
		init();
	}
	
	/**
	 * 遇到标签结束时呼叫的方法
	 */
	@Override
	public int doEndTag() throws JspException {
		JspWriter    jspWriter = null;
		Object       objValue  = null;
		String       s_value   = null;
		String       s_name    = null;
		String       class_name= null;
		HashMap      aHash     = null;
		HashMap      bHash     = null;
		ArrayList    aList     = null;
		boolean      bValue    = false;
		HttpServletRequest _httpRequest  = null;
		try {
			_httpRequest = (HttpServletRequest)pageContext.getRequest();
			if(_httpRequest==null) return EVAL_PAGE;//返回值是EVAL_PAGE表示处理完标签后继续执行以下的JSP网页
			if(m_item!=null && !m_item.trim().equals("")){
				bValue = false;
				if(m_item.equals("`IP")){
					objValue = _httpRequest.getRemoteAddr();
					bValue   = true;
				}
				if(m_item.equals("`YEAR") ||m_item.equals("`MONTH") ||m_item.equals("`DAY") || m_item.equals("`WEEK")|| m_item.equals("`CWEEK")){
					objValue = getDatePart(m_item);
					bValue   = true;
				}
				if(m_item.equals("`HOST")){
					objValue = _httpRequest.getRemoteHost();
					bValue   = true;
				}
				if(m_item.equals("`SYS_NAME")){
					s_value  = com.powerise.ibss.util.SysSet.getSystemSet("System", "SystemName", "");
					if(s_value==null || s_value.trim().equals("")) s_value ="综合业务支撑系统";
					objValue = s_value;
					bValue   = true;
				}
				if(m_item.startsWith("`SYS-") && !m_item.equals("`SYS-")){
					s_name   = m_item.substring(5);
					s_value  = com.powerise.ibss.util.SysSet.getSystemSet("System", s_name, "");
					if(s_value==null || s_value.trim().equals("")) s_value ="";
					objValue = s_value;
					bValue   = true;
				}
				
				if(!bValue) {
					objValue = TagFunc.getObject(pageContext,pageContext,m_item,m_scope);
				}
			}
			if(objValue!=null) {
				class_name = objValue.getClass().getName();
				if(class_name.indexOf("String")>-1){
					s_value = (String)objValue;
				}else{
					if(m_value!=null && class_name.indexOf("ArrayList")>-1 ){
						aHash = TagFunc.getValues(m_cond);
						aList = (ArrayList)objValue;
						if(aHash!=null){
							bHash = TagFunc.getFirstValue(aList,aHash);
							if(bHash!=null) s_value  = (String)bHash.get(m_value);
							aHash.clear();
							aHash = null;
						}
					}else{
						s_value  = objValue.toString();
					}
				}
			}
			if(objValue==null && m_cond==null) s_value = m_value;
			if(s_value ==null) s_value = m_def;
			if(s_value ==null || s_value.equals("")) {
				return EVAL_PAGE;
			}
			if(m_pattern!=null){
				try{
					s_value = s_value.trim();
					double dValue = Double.parseDouble(s_value);
					s_value = TagFunc.patternPrint(m_pattern,dValue);
				}catch(Exception e){
					
				}
			}
			if(m_is_html){
				s_value = TagFunc.replStr(s_value," "  , "&nbsp;");
				s_value = TagFunc.replStr(s_value,"<"  , "&lt;");
				s_value = TagFunc.replStr(s_value,">"  , "&gt;");
				s_value = TagFunc.replStr(s_value,"\"" , "&quot;");	
				s_value = TagFunc.replStr(s_value,"\n" , "<br>");
			}
			jspWriter = pageContext.getOut();
			jspWriter.print(s_value);
		}catch(Exception e){
			//logger.info("执行【OutTag】异常"+e.getMessage());
		}finally{
			init();
		}
		return EVAL_PAGE;
	}
	public static String getDatePart(String s_part) {
		if(s_part==null || s_part.trim().equals("")) return "";
		java.util.Calendar cal = java.util.Calendar.getInstance();
		
		int  iNum = 0;
		s_part = s_part.trim().toLowerCase();
		
		if(s_part.equals("`year")) return String.valueOf(cal.get(Calendar.YEAR));
		if(s_part.equals("`month")) {
			iNum = cal.get(Calendar.MONTH)+1;
			if(iNum>9) return String.valueOf(iNum);
			return "0"+String.valueOf(iNum);
		}
		if(s_part.equals("`day")) {
			iNum = cal.get(Calendar.DAY_OF_MONTH);
			if(iNum>9) return String.valueOf(iNum);
			return "0"+String.valueOf(iNum);
		}
		if(s_part.equals("`week")) {
			iNum = cal.get(Calendar.DAY_OF_WEEK);
			if(iNum>9) return String.valueOf(iNum);
			return "0"+String.valueOf(iNum);
		}
		if(s_part.equals("`cweek")) {
			iNum = cal.get(Calendar.DAY_OF_WEEK)-1;
			if(iNum==1) return "星期一";
			if(iNum==2) return "星期二";
			if(iNum==3) return "星期三";
			if(iNum==4) return "星期四";
			if(iNum==5) return "星期五";
			if(iNum==6) return "星期六";
			if(iNum==7) return "星期天";
		}
		return "";		
	}
		
	//数据条目
    public void setItem(String s_item) {
    	if(s_item==null) s_item = "";
		this.m_item = s_item.trim();
    }
    //值
    public void setValue(String s_value) {
    	if(s_value==null) s_value = "";
		this.m_value = s_value.trim();
    }
    //缺省值
    public void setDef(String s_def) {
    	if(s_def==null) s_def = "";
		this.m_def = s_def.trim();
    }
    //缺省值
    public void setHtml(boolean bHTML) {
		this.m_is_html = bHTML;
    }
    //缺省值
    public void setScope(String s_scope) {
		this.m_scope = s_scope;
    }
	//条件 格式 `f_name=lgm`tel=13607435681
    public void setCond(String s_cond) {
		this.m_cond = s_cond;
    }
    //匹配模式
    public void setPattern(String s) {
		this.m_pattern = s;
    }    
}
