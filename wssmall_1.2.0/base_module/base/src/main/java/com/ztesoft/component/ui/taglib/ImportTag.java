package com.ztesoft.component.ui.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

public class ImportTag extends TagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ImportTag.class);

	private String library;
	private String root;

	public String getRoot() {
		return (root!=null ? root : ((HttpServletRequest)(this.pageContext.getRequest())).getContextPath()+"/" );
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getLibrary() {
		return library;
	}

	public void setLibrary(String library) {
		this.library = library;
	}
	
	
	
	public String _import(String type, String path, String className)
	{
	  String result = "";
	  
	  if(path!=null)
		  path = this.getRoot() + path;		  
	  
	  if ("js".equalsIgnoreCase(type))
		  result = "<script type='text/javascript' language='JavaScript' src='" + path + "' charset='GBK'></script>";
	  else if ("css".equalsIgnoreCase(type))
		  result = "<link type='text/css' rel='stylesheet' href='" + path + "' charset='GBK'/>";
	  else if ("behavior".equalsIgnoreCase(type))
	  {
	      result = "<style>" + className + "{behavior:url(" + path + "); }</style>";
	  }
	  else if ("namespace".equalsIgnoreCase(type)){
		  result = "<?import namespace='" + className+ "' implementation='" + path + "' />";
	  }	  
	  return result;
	}	
	
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
						
		StringBuffer sbuffer = new StringBuffer();			  
		sbuffer.append(this._import("css",      "public/skins/bsn/skinbsn.css", ""));	
		sbuffer.append(this._import("js",       "public/components/log4js.js", ""));
		
		sbuffer.append(this._import("js",       "public/components/prototype.js", ""));
		sbuffer.append(this._import("js",       "public/components/kernel.js", ""));	
		sbuffer.append(this._import("js",       "public/components/buffalo.js", ""));
		sbuffer.append(this._import("js",       "public/components/form.js", ""));
		sbuffer.append(this._import("js",       "public/components/datatable.js", ""));
		sbuffer.append(this._import("js",       "public/components/dataset.js", ""));
		sbuffer.append(this._import("js",       "public/components/dropdown.js", ""));
		//sbuffer.append(this._import("js",       "public/components/validator.js", ""));
		sbuffer.append(this._import("js",       "public/components/messageBox/messageBox.js", ""));		
		sbuffer.append(this._import("js",       "public/components/messageBox/dialog.js", ""));	
		
		sbuffer.append(this._import("js",       "public/components/localAction.js", ""));	
		
		//sbuffer.append(this._import("behavior", "public/components/components.htc", "DL, CODE"));
		/*.fieldlabel, .datalabel, .coolButton2, .coolButton, */
		
		try{		
			String result = sbuffer.toString();
			if(!ContentManage.isSetParentContent(this, result)){
				pageContext.getOut().println(result);	
			}
		}catch(Exception e){
			logger.info("Import标签解释失败", e);
		}
		return super.doEndTag();
	}
	
	@Override
	public void release() {
		// TODO Auto-generated method stub
		super.release();	
		
		this.library = null;
	
	}			
	
}
