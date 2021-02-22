package com.powerise.ibss.taglibs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import java.io.File;
public class FileTag extends TagSupport {
	private String  m_item     = null ;//数据条目
	private String  m_root_dir = null ;//目录
	private String  m_oper     = null ;//操作类型
	public FileTag() {
		super();
		init();
	}
	private static Logger logger = Logger.getLogger(FileTag.class);
	public void init() {
		m_item   = null;
	}
	@Override
	public void release() {
		init();
	}
	@Override
	public int doEndTag() throws JspException {
		JspWriter    jspWriter = null;
		Object       objValue  = null;
		String       s_value   = null;
		String       class_name= null;
		HttpServletRequest _httpRequest  = null;
		try {
			_httpRequest = (HttpServletRequest)pageContext.getRequest();
			if(m_root_dir==null) m_root_dir = "";
			m_root_dir = m_root_dir.trim();
			if(m_root_dir.equals("")){
				m_root_dir = _httpRequest.getRealPath("/");
			}
			objValue = null;//TagFunc.getObject(pageContext,m_item,m_scope);
			if(objValue==null || !objValue.getClass().getName().endsWith("String"))return EVAL_PAGE; 
			s_value = (String)objValue;
			File file = new File(m_root_dir+"/"+s_value);
			
		}catch(Exception e){
			//logger.info("执行【OutTag】异常"+e.getMessage());
			logger.info("执行【OutTag】异常"+e.getMessage());
		}finally{
			init();
		}
		return EVAL_PAGE;
	}
	//数据条目
    public void setItem(String s_item) {
    	if(s_item==null) s_item = "";
		this.m_item = s_item.trim();
    }
    public void setRoot_dir(String s_root_dir) {
		this.m_root_dir = s_root_dir;
    }
    //操作类型
    public void setOper(String s_oper) {
		this.m_oper = s_oper;
    }
}
