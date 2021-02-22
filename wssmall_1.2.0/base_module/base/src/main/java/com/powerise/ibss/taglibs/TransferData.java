package com.powerise.ibss.taglibs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
public class TransferData extends TagSupport {
	private String  m_name     = null ;//变量名称
	private String  m_item     = null ;//数据条目
	private String  m_from_scope = null ;//值来源scope
	private String  m_to_scope   = null ;//值保存
	
	private String  m_f_name     = null ;//名称字段
	private String  m_f_value    = null ;//值字段
	private String  m_f_desc     = null ;//描述值字段
	private static Logger logger = Logger.getLogger(TransferData.class);
	public TransferData() {
		super();
		init();
	}
	public void init() {
		m_item      = null;
		m_name      = null;
		m_to_scope  = null;
		m_from_scope= null;
		m_f_name    = null;
		m_f_value   = null;
	}
	@Override
	public void release() {
		init();
	}
	@Override
	public int doEndTag() throws JspException {
		JspWriter    jspWriter = null;
		Object       objValue  = null;
		Object       obj1      = null;
		Object       obj2      = null;
		Object       obj3      = null;
		
		Object       objTmp    = null;
		boolean      bValue    = false;
		String       class_name= null;
		String       s_name     = null;
		HashMap      aHash     = null;
		HashMap      bHash     = null;
		ArrayList    aList     = null;
		int          iLoop     = 0;
		int          iLoopNum  = 0;
		String       s_value_type = null;
		
		HttpServletRequest _httpRequest  = null;
		try {
			_httpRequest = (HttpServletRequest)pageContext.getRequest();
			if(_httpRequest==null) return EVAL_PAGE;
			
			
			if(m_to_scope==null) m_to_scope = "";
			m_to_scope = m_to_scope.trim().toLowerCase();
			
			if(m_to_scope.equals("ses") || m_to_scope.equals("session")){
				pageContext.getSession().removeAttribute(m_name);
			}else{
				_httpRequest.removeAttribute(m_name);
			}
			if(m_f_name ==null || m_f_name.trim().equals("")) return EVAL_PAGE;
			if(m_f_value==null || m_f_value.trim().equals("")) return EVAL_PAGE;
			
			m_f_name  = m_f_name.trim();
			m_f_value = m_f_value.trim();
			bValue = false;
			if(m_item.equals("`IP")){
				objValue = _httpRequest.getRemoteAddr();
				bValue   = true;
			}
			if(m_item.equals("`HOST")){
				objValue = _httpRequest.getRemoteHost();
				bValue   = true;
			}
			if(!bValue) {
				objValue = TagFunc.getObject(pageContext,pageContext,m_item,m_from_scope);
			}
			if(objValue==null) return EVAL_PAGE;
			class_name = objValue.getClass().getName();
			if(class_name.indexOf("ArrayList")<0 ) return EVAL_PAGE;
			aList = (ArrayList)objValue;
			
			
			iLoopNum = aList.size();
			for (iLoop=0;iLoop<iLoopNum;iLoop++){
				objTmp = aList.get(iLoop);
				if(objTmp==null || objTmp.getClass().getName().indexOf("HashMap")<0) continue;
				aHash = (HashMap)objTmp;
				obj1  = aHash.get(m_f_name);
				obj2  = aHash.get(m_f_value);
				obj3  = aHash.get(m_f_desc);
				
				
				if(obj1==null || (obj2==null && obj3==null)) continue;
				if(bHash==null) bHash = new HashMap();
				s_value_type=(String)aHash.get("FIELD_VALUE_TYPE");
				
				if(obj1.getClass().getName().indexOf("String")<0) continue;
				if(s_value_type!=null && s_value_type.trim().equals("1")) {
					bHash.put(obj1,obj2);
					s_name = (String)obj1;
					s_name = s_name.trim()+"_TEXT";
					bHash.put(s_name,obj3);
				}else{
					bHash.put(obj1,obj3);
				}
			}			
			if(m_to_scope.equals("") || m_to_scope.equals("req") || m_to_scope.equals("request")){
				_httpRequest.setAttribute(m_name,bHash);
			}
			if(m_to_scope.equals("ses") || m_to_scope.equals("session")){
				pageContext.getSession().setAttribute(m_name,bHash);
			}
		}catch(Exception e){
			logger.info("执行【TransferData】异常"+e.getMessage());
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
   
    //缺省值
    public void setVar(String s_name) {
    	if(s_name==null) s_name = "";
		this.m_name = s_name.trim();
    }

	//值来源scope
    public void setScope(String s_scope) {
    	if(s_scope==null) s_scope = "";
		this.m_from_scope = s_scope.trim();
    }
    //值保存
    public void setToscope(String s_scope) {
    	if(s_scope==null) s_scope = "";
		this.m_to_scope = s_scope.trim();
    }
    //名称字段
    public void setFname(String s_name) {
		this.m_f_name = s_name;
    }
    //值字段
    public void setFvalue(String s_name) {
		this.m_f_value = s_name;
    }
    //描述值字段
    public void setFdesc(String s_name) {
		this.m_f_desc = s_name;
    }
    
}