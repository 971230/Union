package com.ztesoft.face.frame;

import com.ztesoft.common.util.ContextHelper;
import com.ztesoft.form.processor.Processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;

/**
 * @author Reason.Yea
 * @version Created Feb 17, 2013
 */
public class ServDict implements Serializable {

	/**
	 * generate sid
	 */
	private static final long serialVersionUID = -8288281491291386031L;

	public HashMap m_values=null;
	private Connection conn = null;
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private HttpSession session = null;
	public ServDict(){
		m_values=new HashMap();
	}
	
	public ServDict(String handle,String method,HttpServletResponse httpResponse,HttpServletRequest httpRequest,Processor processor){
		m_values=new HashMap();
		m_values.put("MOD",handle);
		m_values.put("MET",method);
		m_values.put("processor", processor);
		this.request = httpRequest;
		this.response = httpResponse;
		
		ContextHelper.setRequest(request, response);
		
	}
	
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public void setValue(String name,Object val){
		this.m_values.put(name, val);
	}
	
	public Object getValue(String name){
		return this.m_values.get(name);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	
}
