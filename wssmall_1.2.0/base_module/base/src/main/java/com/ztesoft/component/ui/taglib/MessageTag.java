package com.ztesoft.component.ui.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.log4j.Logger;

//import com.ztesoft.component.i18n.I18NHelper;
//import com.ztesoft.component.i18n.I18NResourceLoader;

public class MessageTag extends BodyTagSupport {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MessageTag.class);
	
	private String id;
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public int doEndTag() throws JspException {
		
		
		String msg = this.getBodyContent().getString();
		
		try {
			pageContext.getOut().println(msg);
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("Message解析失败", e);
		}

		return super.doEndTag();
	}
	
	@Override
	public void release() {
		super.release();	
		
		this.id = null;	
	}	
}
