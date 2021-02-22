
package com.powerise.ibss.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

public class ForEachTag extends BodyTagSupport{
	private String   m_item     = "" ;//数据条目
	private String   m_name     = "" ;//变量名称
	private int      m_step     = 1  ;//步长
	private int      m_start    = 0  ;//开始
	private int      m_end      = 0  ;//结束
	private int      m_min      = 0  ;//至少行数
	
	private int      m_page_size= 0  ;//每页大小
	private String   m_page_no  = null;//当前页序号名
	private boolean  m_trace    = false;//调试标志
	private int      m_cur      = 0  ;//当前值
	private StringBuffer  sbHtml  = null  ;//当前值
	
	
	private Iterator m_iterator= null;
	
	public ForEachTag() {
		super();
		init();
	}
	public void init() {
		m_item    = null;
		m_iterator= null;
		m_step    = 1   ;
		m_start   = 0;
		m_end     = 0;
		m_cur     = 0;
		m_min     = 0;
		m_trace   = false;
		if(sbHtml!=null) sbHtml.setLength(0);
		sbHtml = null;
	}
	@Override
	public void release() {
		init();
	}
	
	@Override
	public int doStartTag() throws JspException {
		Object    objItem    = null;
		String    class_name = null;
		ArrayList aList      = null;
		HashMap   aHash      = null;
		Hashtable bHash      = null;
		
		if(m_trace) sbHtml = new StringBuffer("");
		
		pageContext.getRequest().removeAttribute(m_name);
		
		objItem = TagFunc.getObject(pageContext,pageContext,m_item,null);
		if(objItem!=null) {
			class_name = objItem.getClass().getName();
			if(class_name.equals("java.util.ArrayList")){
				aList      = (ArrayList)objItem;
				m_iterator = aList.iterator();
			}
			if(class_name.equals("java.util.HashMap")){
				aHash      = (HashMap)objItem;
				m_iterator = (Iterator)aHash;
			}
			if(class_name.equals("java.util.Hashtable")){
				bHash      = (Hashtable)objItem;
				m_iterator = (Iterator)aHash;
			}
		}
		
		Object obj     = null;
		int    iPageNo = 0;
		String sPageNo = null;
		if(this.m_page_size>0){
			if(m_page_no!=null && !m_page_no.equals("")){
				sPageNo = pageContext.getRequest().getParameter(m_page_no);
			}else{
				sPageNo = pageContext.getRequest().getParameter("page_no");
				if(sPageNo==null || sPageNo.trim().equals("")) sPageNo = pageContext.getRequest().getParameter("PAGE_NO");
				if(sPageNo==null || sPageNo.trim().equals("")) sPageNo = pageContext.getRequest().getParameter("PARA_PAGE_NO");
				if(sPageNo==null || sPageNo.trim().equals("")) sPageNo = pageContext.getRequest().getParameter("PAGENO");
				if(sPageNo==null || sPageNo.trim().equals("")) sPageNo = pageContext.getRequest().getParameter("pageno");
				if(sPageNo==null || sPageNo.trim().equals("")) sPageNo = pageContext.getRequest().getParameter("para_pageno");
			
			}
			try{
				if(sPageNo!=null && !sPageNo.trim().equals("")){
					iPageNo = Integer.parseInt(sPageNo);
				}
			}catch(Exception e){
				
			}
			if(iPageNo  <1) iPageNo  = 1;
			m_start= (iPageNo-1)* this.m_page_size +1;
			m_end  = (iPageNo)  * this.m_page_size ;
		}
		if(m_min>0 && m_min<m_end) m_min = m_end;
		
		m_cur = 1;
		
		
		while(m_start>0 && m_cur<m_start){
			if(m_iterator!=null && m_iterator.hasNext()) m_iterator.next();
			m_cur ++;
		}
		
		if(sbHtml!=null) sbHtml.append("m_cur="+m_cur+"<br>"+ " m_min="+m_min + "<br>");
		if(m_iterator!=null && m_iterator.hasNext()) obj = m_iterator.next();
		if(obj != null ) {
			pageContext.getRequest().setAttribute(m_name, obj) ;
			if(sbHtml!=null) sbHtml.append("obj="+obj+"<br>");
		}
		return EVAL_PAGE ;
	}

	@Override
	public int doAfterBody(){
		int  iTime = 0;
		Object obj = null;
		//pageContext.getRequest().removeAttribute(m_name) ;
		m_cur ++;
		
		if(this.m_step<1) this.m_step = 1;
		
		//if(m_cur>100) return SKIP_BODY;
		iTime = 0;
		if(m_iterator!=null){
			while(m_iterator.hasNext() && iTime<this.m_step-1 ){
				iTime ++;
				m_iterator.next();
				if(iTime<this.m_step) break;
			}
			if(m_iterator.hasNext()) obj = m_iterator.next(); 
		}
		
		
		if(m_min<1 && obj  ==null  ) return SKIP_BODY;
		if(m_end>0 && m_cur> m_end ) return SKIP_BODY;
		
		if(sbHtml!=null) sbHtml.append("obj="+obj+"<br>");
		
		pageContext.getRequest().setAttribute(m_name, obj) ;
		return EVAL_BODY_AGAIN;
	}
	@Override
	public int doEndTag() throws JspException{
		JspWriter    jspWriter = null;
		pageContext.getRequest().removeAttribute(m_name);
		try{
			jspWriter = pageContext.getOut();
			if(bodyContent !=null && bodyContent.getString()!=null){
				jspWriter.print(bodyContent.getString()) ;
			}
			if(sbHtml!=null) jspWriter.print("<tr><td>"+sbHtml.toString()+"</td></tr>");
		}catch(IOException eIO ){
		}finally{
			init();
		}
		return EVAL_PAGE ;
	}
	//数据条目
    public void setItem(String s_item) {
    	if(s_item==null) s_item = "";
		this.m_item = s_item.trim();
    }
    
    public void setVar(String s_name) {
    	if(s_name==null) s_name = "";
		this.m_name = s_name.trim();
    }
    //间隔
    public void setStep(int step) {
		this.m_step = step;
    }
    
    public void setStart(int iStart) {
		this.m_start = iStart;
    }
    public void setEnd(int iEnd) {
		this.m_end = iEnd;
    }
    //每页大小
    public void setPagesize(int iPageSzie) {
    	if(iPageSzie<0) iPageSzie = 0;
		this.m_page_size = iPageSzie;
    }
    //当前页序号名
    public void setPageno(String s_page_no) {
    	if(s_page_no==null) s_page_no = "";
    	s_page_no = s_page_no.trim();
		this.m_page_no = s_page_no;
    }
    //至少行数
    public void setMin(int iMin) {
		this.m_min = iMin;
    }
    //调试标志
    public void setTrace(boolean bTrace) {
		this.m_trace = bTrace;
    }
}
