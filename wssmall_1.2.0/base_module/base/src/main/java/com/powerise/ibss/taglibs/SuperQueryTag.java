package com.powerise.ibss.taglibs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.WebContextFactory.WebContextBuilder;
import org.directwebremoting.impl.DefaultContainer;
import org.directwebremoting.impl.DefaultWebContextBuilder;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.util.SysSet;
import com.ztesoft.common.util.ContextHelper;

public class SuperQueryTag extends BodyTagSupport {
	private String  m_name   = null;
	private String  m_func_id= null;//功能号
	private boolean m_remote = false;//远程调用标志
	private boolean m_trace  = false;//调试标志
	private boolean m_execute= true ;//执行标志
	private boolean m_dsql   = true ;//动态SQL标志

	private DynamicDict m_dict = null;//请求数据
	
	private WebContextBuilder contextBuilder = null;
	
	//物理分页增加属性  add by qin.guoquan 2011-09
	private String m_pageindex;//当前页数
	private String m_pagesize;//每页条数

	public SuperQueryTag() {
		super();
		init();
	}
	public void init() {
		m_remote  = false;
		m_dict    = null;
		m_name    = null;
		m_trace   = false;
		m_dsql    = true ;
		m_execute = true ;
		
		m_pageindex = null;
		m_pagesize = null;
	}
	@Override
	public void release() {
		init();
		if(m_dict!=null) m_dict.destroy();
		m_dict = null;

	}
	public void destroy() {
		init();
		Object obj = pageContext.getRequest().getAttribute(m_name);
		if(obj!=null && obj instanceof DynamicDict){
			DynamicDict aDict = (DynamicDict)obj;
			aDict.destroy();
			aDict.clear();
			aDict = null;
		}
	}
	@Override
	public int doStartTag() throws JspException {
		/*变量定义*/
		HttpServletRequest _httpRequest  = null;
		HashMap aHash = null;
		Object  obj   = null;
		try {
			_httpRequest = (HttpServletRequest)pageContext.getRequest();
			if(_httpRequest==null) return EVAL_PAGE;
			aHash  = new HashMap();
			m_dict = new DynamicDict(1);
			m_dict.setValueByName("PARAMETER",aHash);
			obj = _httpRequest.getAttribute(m_name);
			if(obj!=null && obj instanceof DynamicDict){
				DynamicDict aDict = (DynamicDict)obj;
				aDict.destroy();
				aDict.clear();
				aDict = null;
			}
			_httpRequest.removeAttribute(m_name);

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//设置当前线程变量
			setRequestContext();
		}
		return EVAL_PAGE;
	}
	@Override
	public int doAfterBody(){
		return SKIP_BODY;
	}
	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest _httpRequest  = null;

		DynamicDict  recvDict  = null;
		StringBuffer sbTrace   = null;
		JspWriter    jspWriter = null;
		Object       obj       = null;
		Object       objTmp    = null;
		String       s_func_id = null;
		try {
			jspWriter = pageContext.getOut();
			_httpRequest = (HttpServletRequest)pageContext.getRequest();
			if(_httpRequest==null) return EVAL_PAGE;
			if(this.m_func_id==null) this.m_func_id = "";
			this.m_func_id = this.m_func_id.trim();

			//add by wui
			String P_FUNC_ID = (String) m_dict.getValueByName("P_FUNC_ID",false);
        	if(P_FUNC_ID!=null && !P_FUNC_ID.equals("")){
        		m_dict.m_ActionId=P_FUNC_ID;
        		this.m_func_id = P_FUNC_ID;
        	}
        	
        	
			s_func_id = this.m_func_id;
			if(this.m_func_id.startsWith("`")){
				s_func_id      = s_func_id.substring(1);
				this.m_func_id = _httpRequest.getParameter(s_func_id);
			}
			
			if(this.m_func_id.equals("")){
				this.m_func_id = _httpRequest.getParameter("_FUNC_ID_");
				if(this.m_func_id==null || this.m_func_id.trim().equals("")){
					objTmp = pageContext.getAttribute("_FUNC_ID_");
					if(objTmp==null || !objTmp.getClass().getName().endsWith(".String")){
						objTmp = _httpRequest.getAttribute("_FUNC_ID_");
					}
					if(objTmp!=null && objTmp.getClass().getName().endsWith(".String")){
						this.m_func_id = (String)objTmp;
					}
				}
			}

			if(this.m_func_id==null || this.m_func_id.trim().equals("")){
				return EVAL_PAGE;
			}
			_httpRequest.removeAttribute(m_name);

			//组织请求数据
			if(m_dict==null) m_dict = new DynamicDict(1);
			m_dict.m_ActionId = this.m_func_id;
			
			//m_dict.setRequest(_httpRequest);//add by qin.guoquan 2011-03-23
			
			m_dict.flag = 0;
			if(this.m_dsql){//true:sql操作时flag=1
				m_dict.flag	  = 1;
				try{
					obj = m_dict.getValueByName("parameter");
				}catch(Exception e){
				}
				if(obj==null){
					HashMap aHash = new HashMap();
					m_dict.setValueByName("parameter",aHash);
				}
			}
        	
			if(m_trace){
				sbTrace = new StringBuffer("");
				sbTrace.append("<table border='1'>\n");
				sbTrace.append("<tr><td width='1%' nowrap>执行功能</td>\n<td nowrap>");
				sbTrace.append(m_dict.m_ActionId+"---"+s_func_id);
				sbTrace.append("</td><tr>");

				sbTrace.append("<tr><td width='1%' nowrap>执行方式</td>\n<td nowrap>");
				if(!m_remote) sbTrace.append("本地");
				if( m_remote) sbTrace.append("远程");
				if( this.m_dsql)sbTrace.append("-->SQL");
				if(!this.m_dsql)sbTrace.append("-->服务");
				sbTrace.append("</td><tr>");

				sbTrace.append("<tr><td width='1%' nowrap>是否执行</td>\n<td nowrap>");
				if(!this.m_execute) sbTrace.append("否");
				if( this.m_execute) sbTrace.append("是");
				sbTrace.append("</td><tr>");

				sbTrace.append("<tr><td width='1%' nowrap>请求数据</td>\n<td nowrap>\n<textarea rows='2' name='SupperQueryResult' cols='90'>");
				sbTrace.append(m_dict.m_Values);
				sbTrace.append("</textarea></td><tr>");
			}
			if(!this.m_execute) return EVAL_PAGE;
			
			//物理分页增加属性  add by qin.guoquan 2011-09-------------------START
			m_dict.setValueByName("PAGE_INDEX", m_pageindex);
			m_dict.setValueByName("PAGE_SIZE", m_pagesize);
			m_dict.setValueByName("ip", ContextHelper.getIp());
			//物理分页增加属性  add by qin.guoquan 2011-09-------------------END
		
	
			if(m_remote){
				m_dict = SysSet.callRemoteService(m_dict);
			}else{
				m_dict = ActionDispatch.dispatch(m_dict);
			}
			if(m_trace && m_dict!=null){
				if(sbTrace==null) sbTrace = new StringBuffer("");
				sbTrace.append("<table border='1'>\n");
				sbTrace.append("<tr><td width='1%' nowrap>返回标志</td>\n<td nowrap>");
				sbTrace.append(m_dict.flag);
				sbTrace.append("</td><tr>");
				sbTrace.append("<tr><td width='1%' nowrap>返回消息</td>\n<td >");
				sbTrace.append(m_dict.msg);
				sbTrace.append("</td><tr>");
				sbTrace.append("<tr><td width='1%' nowrap>返回数据</td>\n<td nowrap>\n<textarea rows='3' name='SupperQueryResult' cols='90'>");
				sbTrace.append(m_dict.m_Values);
				sbTrace.append("</textarea></td><tr>");
			}
			_httpRequest.setAttribute(m_name,m_dict);
		}catch(Exception e){
			//e.printStackTrace();
			e.printStackTrace();
			if(sbTrace!=null) {
				sbTrace.append("<tr><td width='1%' nowrap>异常消息</td>\n<td nowrap>\n<textarea rows='6' name='StackTrace' cols='90'>");
				sbTrace.append(e.toString());
				sbTrace.append("\n");
				sbTrace.append( TagFunc.getStackInfo(e));
				sbTrace.append("</textarea></td><tr>");
			}
		}finally{
			if(sbTrace!=null) {
				sbTrace.append("</table>\n");
				try{
					if(jspWriter!=null) jspWriter.print(sbTrace.toString());
				}catch(IOException eIO ){
				}
				sbTrace.delete(0,sbTrace.length());
				sbTrace.setLength(0);
				sbTrace = null;
			}
			//清除当前线程变量
			unsetRequestContext();
		}
		return EVAL_PAGE;
	}
	/**
	 * Reaaon.Yea add 2012.2.23
	 * 设置当前线程变量
	 */
	private void setRequestContext() {
		ServletContext servletContext = pageContext.getServletContext();
		ServletConfig servletConfig = pageContext.getServletConfig();
		HttpServletRequest httpRequest = (HttpServletRequest)pageContext.getRequest();
		HttpServletResponse httpResponse = (HttpServletResponse) pageContext.getResponse();
		if(contextBuilder==null){
			contextBuilder=new DefaultWebContextBuilder();
		}
		contextBuilder.set(httpRequest, httpResponse, servletConfig, servletContext, new DefaultContainer());
		WebContextFactory.setWebContextBuilder(contextBuilder);
	}
	/**
	 * Reaaon.Yea add 2012.2.23
	 * 清除当前线程变量
	 */
	private void unsetRequestContext(){
		if(contextBuilder!=null){
			contextBuilder.unset();
		}
	}
	//变量名称
    public void setVar(String var) {
		this.m_name = var;
    }
    //远程调用标志
    public void setRemote(boolean bRemote) {
		this.m_remote = bRemote;
    }
    //调试标志
    public void setTrace(boolean bTrace) {
		this.m_trace = bTrace;
    }
    //动态SQL标志
    public void setDsql(boolean bSQL) {
		this.m_dsql = bSQL;
    }
    //功能号
    public void setFunc_id(String sFuncID) {
		this.m_func_id = sFuncID;
    }
    
    //当前页数 (物理分页增加属性  add by qin.guoquan 2011-09)
	public void setPageindex(String pageindex) {
		this.m_pageindex = pageindex;
	}
	
	//每页条数 (物理分页增加属性  add by qin.guoquan 2011-09)
	public void setPagesize(String pagesize) {
		this.m_pagesize = pagesize;
	}
	public void pushPara(String rs_name,ArrayList aList) {
		if(rs_name==null || aList==null) return ;
    	if(m_dict==null) m_dict = new DynamicDict(1);
    	try{
    		m_dict.setValueByName( rs_name,aList);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
	//执行标志
	public void setExecute(boolean bExec) {
		this.m_execute = bExec;
    }
    public void pushPara(String rs_name,String paraName,String paraValue,String m_crypt,boolean m_empty) {
    	if(paraName==null ) return ;
    	if(paraValue==null){
    		if(m_empty) return ;
    		this.m_execute = false;
    		return ;
    	}

		HashMap aHash = null;
    	try{
			if(m_dict==null) {
	    		aHash  = new HashMap();
	    		m_dict = new DynamicDict(1);
	    		m_dict.setValueByName("PARAMETER",aHash);
	    	}
			if(paraName.equals("_FUNC_ID_")){
				if(this.m_func_id==null || this.m_func_id.trim().equals("")){
					this.m_func_id = paraValue;
				}
			}else{

				if(m_crypt!=null && !m_crypt.trim().equals("")){
					paraValue = TagFunc.crypt(paraValue,m_crypt);
				}
				TagFunc.pushPara(m_dict, rs_name, paraName, paraValue);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    }
}
