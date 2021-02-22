package com.powerise.ibss.taglibs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
public class ParaTag extends TagSupport {
	private String  m_name   = null;//变量名称
	private String  m_rs_name= null;//记录集名
	private String  m_value  = null;//变量值
	private String  m_item   = null;//值来源于条目
	private String  m_def    = null;//缺省值
	private String  m_scope  = null;//范围
	private boolean m_trace  = false;//调试标志
	private String  m_crypt  = null ;//加密版本
	private String  m_type   = null;//值类型
	private boolean m_empty  = false;//是否可以为空标志标志 可以为 true 不可以为false
	private static Logger logger = Logger.getLogger(ParaTag.class);
	public ParaTag() {
		super();
		init();
	}
	public void init() {
		m_name    = null;
		m_rs_name = null;
		m_value   = null;
		m_scope   = null;
		m_item    = null;
		m_def     = null;
		m_crypt   = null;
		m_type    = null;
	}
	@Override
	public void release() {
		init();
	}
	@Override
	public int doEndTag() throws JspException {
		Tag      pTag       = null;
		String   class_name = null;
		String   s_value    = null;
		Object   obj        = null;
		
		SuperQueryTag pQuery  = null;
		ComboTag      pCombo  = null;
		Enumeration   keys    = null;
		boolean       bValue  = false;
		String        sContentType = null;
		HashMap       hashPara = null;
		ArrayList     aList    = null;
		HttpServletRequest _httpRequest  = null;
		Iterator it = null;
		
		int   iLoop    = 0;
		int   iLoopNum = 0;
		try {
			pTag     = getParent();
			if(pTag==null){
				return EVAL_PAGE;
			}
			while(pTag!=null){
				if(pTag!=null) class_name = pTag.getClass().getName();
				if(class_name!=null && class_name.endsWith(".SuperQueryTag")){
					pQuery = (SuperQueryTag)pTag;
					break;
				}
				if(class_name!=null && class_name.endsWith(".ComboTag")){
					pCombo = (ComboTag)pTag;
					break;
				}
				pTag     = getParent();
			}
			_httpRequest = (HttpServletRequest)pageContext.getRequest();
			
			if(m_name==null) m_name = "";
			m_name = m_name.trim();
			
			if(m_name.trim().equals("ALL") || m_name.trim().equals("multipart") ){
				sContentType = _httpRequest.getContentType();
				//如果是上传文件
				keys = pageContext.getRequest().getParameterNames();
				while(keys!=null && keys.hasMoreElements()){
					m_name = (String ) keys.nextElement();
					if(m_name==null) continue;
					s_value= pageContext.getRequest().getParameter(m_name);
					if(pQuery!=null) pQuery.pushPara(m_rs_name,m_name,s_value,m_crypt,m_empty);
					if(pCombo!=null) pCombo.pushPara(m_rs_name,m_name,s_value,m_crypt,m_empty);
					if(m_name.endsWith("_LIST_")){
						m_name = m_name.substring(0,m_name.length()-1);
						aList  = TagFunc.getDataFromStr(s_value);
						if(aList!=null) if(pQuery!=null) pQuery.pushPara(m_name,aList);
					}
					
				}
				if(sContentType!=null && sContentType.indexOf("multipart")>-1){
					hashPara  =  TagFunc.getRequestData(pageContext);
					if(hashPara!=null) it= hashPara.keySet().iterator();
					while(it!=null && it.hasNext()){
						m_name  = (String)it.next();
						s_value = (String)hashPara.get(m_name);
						if(m_name==null || s_value==null)continue;
						if(pQuery!=null) pQuery.pushPara(m_rs_name,m_name,s_value,m_crypt,m_empty);
						if(pCombo!=null) pCombo.pushPara(m_rs_name,m_name,s_value,m_crypt,m_empty);
						if(m_name.endsWith("_LIST_")){
							m_name = m_name.substring(0,m_name.length()-1);
							aList  = TagFunc.getDataFromStr(s_value);
							if(aList!=null) if(pQuery!=null) pQuery.pushPara(m_name,aList);
						}
					}
				}
			}else{
				bValue = false;
				if(m_type!=null && m_type.equals("list")){
					String s_tmp[]=_httpRequest.getParameterValues(m_item);
					if(s_tmp==null) {
						if(m_trace) logger.info("ParaTag -->"+m_item+" is null");
						return EVAL_PAGE;
					}
					aList = new ArrayList();
					iLoopNum = s_tmp.length;
					for (iLoop=0;iLoop<iLoopNum;iLoop++){
						aList.add(s_tmp[iLoop]);
					}
					if(m_trace) logger.info("ParaTag -->"+m_item+"="+aList);
					if(pQuery!=null) pQuery.pushPara(m_name,aList);	
					return EVAL_PAGE;
				}
				s_value = this.getParamValue();
				if(pQuery!=null) pQuery.pushPara(m_rs_name,m_name,s_value,m_crypt,m_empty);	
				if(pCombo!=null) pCombo.pushPara(m_rs_name,m_name,s_value,m_crypt,m_empty);
			}
		}catch(Exception e){
			//logger.info("ParaTag -->pushPara " + e.getMessage());
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	//变量名称
    private String getParamValue() {
		//取得相关的值
		String   s_value    = null;
		String   s_name     = null;
		Object   obj        = null;
		boolean  bValue     = false;
		s_value = m_value;
		HttpServletRequest _httpRequest = (HttpServletRequest)pageContext.getRequest();
		
		if(m_scope!=null && !m_scope.trim().equals("")){
			//依据范围取得数据值
			s_value = TagFunc.getValueFromRequest(pageContext ,m_value,m_scope);
		}
		if(m_item!=null && !m_item.trim().equals("")){
			bValue = false;
			if(m_item.equals("`IP")){ 
				//s_value = pageContext.getRequest().getRemoteAddr();
				/**
				 * 取代理地址
				 */
				s_value = _httpRequest.getHeader("x-forwarded-for");
				if (s_value == null || s_value.length() == 0 || "unknown".equalsIgnoreCase(s_value)) {
					s_value = _httpRequest.getHeader("Proxy-Client-IP");
				}
				if (s_value == null || s_value.length() == 0 || "unknown".equalsIgnoreCase(s_value)) {
					s_value = _httpRequest.getHeader("WL-Proxy-Client-IP");
				}
				if (s_value == null || s_value.length() == 0 || "unknown".equalsIgnoreCase(s_value)) {
					s_value = _httpRequest.getRemoteAddr();
				}
				//s_value = Scsfun.getIpAddr(_httpRequest);
				bValue  = true;
			}
			if(m_item.equals("`HOST")){
				s_value = pageContext.getRequest().getRemoteHost();
				bValue  = true;
			}
			if(m_item.startsWith("`SYS-") && !m_item.equals("`SYS-")){
				s_name   = m_item.substring(5);
				try{
					s_value  = com.powerise.ibss.util.SysSet.getSystemSet("System", s_name, "");
				}catch(Exception e){
				}
				if(s_value==null || s_value.trim().equals("")) s_value ="";
				bValue   = true;
			}
			if(!bValue){
				s_value = m_value;
				obj     = TagFunc.getObject(pageContext,pageContext ,m_item,m_scope);
				if(obj!=null && obj.getClass().getName().equals("java.lang.String")){
					s_value = (String)obj;
				}
			}
			if(m_trace) logger.info(m_item+"="+s_value+"\nobj="+obj);
		}
		if(s_value==null) s_value= m_def;
		return s_value;
    }
    
	//变量名称
    public void setVar(String var) {
		this.m_name = var;
    }
    //变量值
    public void setValue(String value) {
		this.m_value = value;
    }
    //记录集名
    public void setRs(String rs_name) {
		this.m_rs_name = rs_name;
    }
    //记录集名
    public void setItem(String s_item) {
		this.m_item = s_item;
    }
    //调试标志
    public void setTrace(boolean bTrace) {
		this.m_trace = bTrace;
    }
    //缺省值
    public void setDef(String sDef) {
		this.m_def = sDef;
    }
    //范围
    public void setScope(String sScope) {
		this.m_scope = sScope;
    }
    //加密版本 算法：简单的字符、位置变换。版本：云南，山东ver=2

    public void setCrypt(String s_crypt) {
		this.m_crypt = s_crypt;
    }
    //值类型
    public void setType(String s_type) {
    	if(s_type!=null) s_type = s_type.trim().toLowerCase();
		this.m_type = s_type;
    }
    //是否可以为空标志标志
    public void setEmpty(boolean bEmpty) {
		this.m_empty = bEmpty;
    }
}