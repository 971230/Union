package com.ztesoft.net.framework.context.webcontext.impl;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.context.webcontext.WebSessionContext;

/**
 * @author kingapex
 */
public class WebSessionContextImpl implements WebSessionContext, Externalizable {

	private HttpSession session;
	
	
	private Map inf_session =null;
	
	private final Log logger=LogFactory.getLog(getClass());


	private Hashtable attributes;


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lenovo.labs.framework.util.sessioncontext.impl.FrameworkSessionContext#getSession()
	 */
	@Override
	public HttpSession getSession() {
		if(null == session){
			HttpServletRequest httpRequest = ThreadContextHolder.getHttpRequest();
			session = httpRequest.getSession();
		}
		return session;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 */
	@Override
	public void setSession(HttpSession session) {
		this.session = session;
		this.attributes = (Hashtable) this.session.getAttribute(sessionAttributeKey);
		if (attributes == null) {
			attributes = new Hashtable();
			this.onSaveSessionAttribute();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lenovo.labs.framework.util.sessioncontext.impl.FrameworkSessionContext#invalidateSession()
	 */
	@Override
	public void invalidateSession() {
		this.session.invalidate();
	}

	private void onSaveSessionAttribute() {
		if(this.session == null)
		{
			this.inf_session = new HashMap();
			this.inf_session.put(sessionAttributeKey, attributes);
		}else{
			this.session.setAttribute(sessionAttributeKey, attributes);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lenovo.labs.framework.util.sessioncontext.impl.FrameworkSessionContext#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public void setAttribute(String name, Object value) {
		
		if(attributes == null)
			attributes =  new Hashtable();
		if(attributes!=null){		
			attributes.put(name, value);
			onSaveSessionAttribute();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lenovo.labs.framework.util.sessioncontext.impl.FrameworkSessionContext#getAttribute(java.lang.String)
	 */
	@Override
	public Object getAttribute(String name) {
		if(attributes!=null)
		return attributes.get(name);
		
		else return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lenovo.labs.framework.util.sessioncontext.impl.FrameworkSessionContext#getAttributeNames()
	 */
	@Override
	public Set getAttributeNames() {
		return attributes.keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lenovo.labs.framework.util.sessioncontext.impl.FrameworkSessionContext#removeAttribute(java.lang.String)
	 */
	@Override
	public void removeAttribute(String name) {
		attributes.remove(name);
		onSaveSessionAttribute();
	}

	@Override
	public void readExternal(ObjectInput input) throws IOException,ClassNotFoundException {
		attributes = (Hashtable) input.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(attributes);
	}

	@Override
	public void destory() {
		this.attributes = null;
		this.session = null;
	}

	public Map getInf_session() {
		return inf_session;
	}

	public void setInf_session(Map inf_session) {
		this.inf_session = inf_session;
	}

	public Hashtable getAttributes() {
		return attributes;
	}

	public void setAttributes(Hashtable attributes) {
		this.attributes = attributes;
	}
	
	
}
