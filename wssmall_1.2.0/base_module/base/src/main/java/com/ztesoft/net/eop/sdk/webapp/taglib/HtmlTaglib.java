package com.ztesoft.net.eop.sdk.webapp.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

public abstract class HtmlTaglib extends BaseTaglibSupport {
	
	protected String startHtml = "";
	protected String endHtml = "";
	
	protected void startAppend(String html){
		startHtml += html;
	}
	protected void endAppend(String html){
		endHtml += html;
	}
	
	@Override
	public int doStartTag() throws JspException {//<
		startHtml = "";
		this.print(this.postStart());		
		return Tag.EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doAfterBody() { ///>
		String content = this.postEnd();
		this.print(content) ;
		return Tag.EVAL_PAGE;
	}

	protected abstract String postStart();
	protected abstract String postEnd();
	
}

