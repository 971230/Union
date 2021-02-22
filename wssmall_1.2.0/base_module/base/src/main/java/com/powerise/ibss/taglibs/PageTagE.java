package com.powerise.ibss.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Enumeration;
public class PageTagE extends TagSupport {
	private String  m_item     = null ;//数据条目
	private String  m_scope    = null ;//缺省值
	private int     m_rec_num  = 0   ;//缺省值
	private int     m_pageSize = 6   ; //每页行数
	private int     m_pageNum  = 5   ; //显示页数
	private String  m_type     = null; //分页类型
	private static Logger logger = Logger.getLogger(PageTagE.class);
	public PageTagE() {
		super();
		init();
	}
	public void init() {
		m_item    = null;
		m_rec_num = 0 ;
		m_pageSize= 6;
		m_type    = null;
	}
	@Override
	public void release() {
		init();
	}
	@Override
	public int doEndTag() throws JspException {
		JspWriter    jspWriter = null;
		Object       objValue  = null;
		String       class_name= null;
		ArrayList    aList     = null;
		String       strHTML   = null;
		int          ipageNo   = 0;
		String       spageNo   = null;
		try {
			if(m_item!=null && !m_item.trim().equals("")){
				objValue = TagFunc.getObject(pageContext,pageContext,m_item,m_scope);
				if(objValue==null) return EVAL_PAGE;

				class_name = objValue.getClass().getName();
				if(class_name.indexOf("ArrayList")<0 ) return EVAL_PAGE;

				aList = (ArrayList)objValue;
				m_rec_num = aList.size();
			}
			if(m_rec_num<1) return EVAL_PAGE;
			spageNo = pageContext.getRequest().getParameter("page_no");
			if(spageNo==null || spageNo.trim().equals("")) spageNo = pageContext.getRequest().getParameter("PAGE_NO");
			if(spageNo==null || spageNo.trim().equals("")) spageNo = pageContext.getRequest().getParameter("PARA_PAGE_NO");
			if(spageNo==null || spageNo.trim().equals("")) spageNo = pageContext.getRequest().getParameter("pageno");
			if(spageNo==null || spageNo.trim().equals("")) spageNo = pageContext.getRequest().getParameter("para_pageno");
			
			if(spageNo!=null && !spageNo.trim().equals("")){
				try{
					ipageNo = Integer.parseInt(spageNo);
				}catch(Exception e){
				}
			}
			if(this.m_type==null) this.m_type = "";
			this.m_type = this.m_type.trim().toLowerCase();
			
			strHTML = getPageInfo(pageContext,m_type , m_pageSize,m_pageNum,ipageNo,m_rec_num);
			if(strHTML!=null){
				jspWriter = pageContext.getOut();
				jspWriter.print(strHTML);
			}
		}catch(Exception e){
			logger.info("执行【 PageTag 】异常"+e.getMessage());
		}finally{
			init();
		}
		return EVAL_PAGE;
	}
	public static String getPageInfo(PageContext pc,String s_type,int iSize,int iNum,int iNo,int iRecNum){
		
		//入参数校验
		if (iRecNum<1){
			return "共 "+iRecNum+" 行记录";
		}
		if (iSize  <1) iSize  = 8;
		if (iNum   <1) iNum   = 5;
		if (iNo    <1) iNo    = 1;
		if (iRecNum<0) iRecNum= 0;
		
		//主要变量定义
		int iPages  = 0; //总页数
		int iTop    = 0; //当前区域最大页序号
		int iBottom = 0; //当前区域最小页序号
		int iArea   = 0; //当前区域
		int iAreas  = 0; //当前区域
		
		//求总页数
		iPages = (iRecNum + iSize -1 )/iSize;
		if (iNo > iPages) iNo = iPages;
		//求当前页所在区域号
		iArea  = (iNo   + iNum  - 1)/iNum ;
		iAreas = (iPages+ iNum  - 1)/iNum ;
		iTop   = (iArea ) * (iNum) ; 
		iBottom= (iArea - 1) * (iNum) + 1;
		if ( iBottom< 1     ) iBottom = 1;
		if ( iTop   >iPages ) iTop    = iPages;
		
		//按照要求输出结果
		int iLoop = 0;
		StringBuffer sbResult = new StringBuffer("");		
		//存在多个该标签时，只打印一次javascript包含
		if (null == pc.findAttribute("hasPage.js")) {
			pc.setAttribute("hasPage.js", "true");
			sbResult.append("<script language=javascript src='/ress/js/page.js'></script>");
		}
		sbResult.append("<div id=\"page_bar\"> \n");
		if(!s_type.equals("no_total")){
			sbResult.append("\t共 "+iRecNum+" 条记录");
			if(iPages>1) sbResult.append("&nbsp &nbsp 页次 &nbsp "+iNo+"/"+iPages+"(每页 &nbsp "+ iSize +" &nbsp 条)");
		}
		sbResult.append("\n");
		if(iPages>1){
			/*
			 * firfox不支持webdings字体 
			 * 
			//显示当前区域最后页号与首页信息
			if (iArea<2) {
				sbResult.append("<font face=webdings >9</font>\n");
				sbResult.append("<font face=webdings >7</font>\n");
			}else{
				sbResult.append("<a href=javascript:fucHrefPage(event,this,1"             +")><font face=webdings >9</font></a>\n");
				sbResult.append("<a href=javascript:fucHrefPage(event,this,"+(iBottom-1)  +")><font face=webdings >7</font></a>\n");
			}
			
			//显示当前区域页码信息
			for (iLoop=iBottom;iLoop<=iTop;iLoop++) {
				//sbResult.append(" " + iLoop );
				if (iNo==iLoop){
					sbResult.append("\t<b><font color=\"#FF0000\">" + iLoop + "</font></b>\n");
				}else{
					sbResult.append("\t<a href=\"javascript:fucHrefPage(event,this,"+ iLoop +")\"><b>" + iLoop +"</b></a>\n");
				}
			}
			if (iArea==iAreas) {
				sbResult.append("<font face=webdings >8</font>\n");
				sbResult.append("<font face=webdings >:</font>|\n\n");
			}else{
				sbResult.append("<a href=\"javascript:fucHrefPage(event,this," + (iTop+1)  +")\"><font face=webdings >8</font></a>\n");
				sbResult.append("<a href=\"javascript:fucHrefPage(event,this," +  iPages   +")\"><font face=webdings >:</font></a>\n");
			}
			*/
			//显示当前区域最后页号与首页信息
			if (iArea<2) {
				sbResult.append("<img src=\"/ress/image/arrow_first.png\" title=\"\" style=\"margin-bottom:-3px\"/>\n");
				sbResult.append("<img src=\"/ress/image/arrow_pre.png\" title=\"\" style=\"margin-bottom:-3px\"/>\n");
			}else{
				sbResult.append("<a href=javascript:fucHrefPage(this,1"+")><img src=\"/ress/image/arrow_first.png\" title=\"\" style=\"margin-bottom:-3px\"/></a>\n");
				sbResult.append("<a href=javascript:fucHrefPage(this,"+(iBottom-1)+")><img src=\"/ress/image/arrow_pre.png\" title=\"\" style=\"margin-bottom:-3px\"/></a>\n");
			}
			//显示当前区域页码信息
			for (iLoop=iBottom;iLoop<=iTop;iLoop++) {
				//sbResult.append(" " + iLoop );
				if (iNo==iLoop){
					sbResult.append("\t<b><font color=\"#FF0000\">" + iLoop + "</font></b>\n");
				}else{
					sbResult.append("\t<a href=\"javascript:fucHrefPage(this,"+ iLoop +")\"><b>" + iLoop +"</b></a>\n");
				}
			}
			if (iArea==iAreas) {
				sbResult.append("<img src=\"/ress/image/arrow_next.png\" title=\"\" style=\"margin-bottom:-3px\"/>\n");
				sbResult.append("<img src=\"/ress/image/arrow_last.png\" title=\"\" style=\"margin-bottom:-3px\"/>\n\n");
			}else{
				sbResult.append("<a href=\"javascript:fucHrefPage(this," + (iTop+1)  +")\"><img src=\"/ress/image/arrow_next.png\" title=\"\" style=\"margin-bottom:-3px\"/></a>\n");
				sbResult.append("<a href=\"javascript:fucHrefPage(this," +  iPages   +")\"><img src=\"/ress/image/arrow_last.png\" title=\"\" style=\"margin-bottom:-3px\"/></a>\n");
			}
			
			if(!s_type.equals("no_input")){
				sbResult.append("\t<input type=\"text\"   name=\"pageno\"   class=\"INPUT_STYLE\" value=\"" + iNo   + "\" maxLength=2 size=2 >\n");
				//sbResult.append("\t<input type=\"button\" class=\"btnSolid\" \n\tonClick=\"javascript:fucHrefPage(this,null)\" value=\"go\">\n");
				sbResult.append("\t<a href=\"javascript:fucHrefPage(this,null)\" tilte=\"跳转\"><img src=\"/ress/image/go.gif\" class=\"king-bai\" align=\"absmiddle\"></a>\n");
			}
			String vExcept = ";pagesize;page_size;para_pagesize;para_page_size;PAGE_NO;pageno;page_no;para_pageno;para_page_no;REFRESH_LIST;";
			sbResult.append(getHTMLFromRequest(pc,vExcept));
		}
		sbResult.append("</div> \n");
		return sbResult.toString();
	}
	public static String getHTMLFromRequest(PageContext pc, String vExcept){
		if (pc==null) return "";
		if (vExcept!=null){
			vExcept = vExcept.trim().toLowerCase();
			if(vExcept.equals("")) vExcept = null;
		}
		Enumeration paranames =pc.getRequest().getParameterNames();
		StringBuffer sbHTML = new StringBuffer("");
		while(paranames.hasMoreElements()){
			String paraName = (String)paranames.nextElement();
			if (paraName==null || paraName.trim().equals("")) continue;
			paraName = paraName.trim();
			String str = ";"+paraName.toLowerCase()+";";
			if ( vExcept.indexOf(str)>-1) continue;
			String paraValue = pc.getRequest().getParameter(paraName);
			if (paraValue==null) continue;
			
			sbHTML.append("<input type=\"hidden\" name=\"");
			sbHTML.append(paraName);
			sbHTML.append("\" value=\"");
			sbHTML.append(paraValue);
			sbHTML.append("\">\n");
		}
		return sbHTML.toString();
	}
	
	//数据条目
    public void setItem(String s_item) {
    	if(s_item==null) s_item = "";
		this.m_item = s_item.trim();
    }
    //缺省值
    public void setScope(String s_scope) {
		this.m_scope = s_scope;
    }
    //每页行数
	public void setPagesize(int pageSize) {
		this.m_pageSize = pageSize;
    }
    //分页类型
    public void setType(String type) {
		this.m_type = type;
    }
     public void setPagenum(int pageNum) {
		this.m_pageNum = pageNum;
    }
}
