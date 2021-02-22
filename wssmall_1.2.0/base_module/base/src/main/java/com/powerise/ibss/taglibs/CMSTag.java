package com.powerise.ibss.taglibs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

//import com.ztesoft.cms.page.cache.CmsCacheUtil;
import com.ztesoft.ibss.common.util.StringUtils;

/**
 * 
 * @author sguo
 * 
 * 设计文档：《http://10.45.7.46:89/svn/INCRDocuments/WSSNET/Public/4design/核心版本/网厅CMS功能配置概要设计V0.1.doc》
 * 
 * 简要说明： if(cms编辑状态){ Table(CMS_FUN):Filed(FUN_ID[入参]) ——>FILE_ID
 * Table(CMS_CONTENT_EDIT) ——>include:jsp打出来 }else{ Table(CMS_FUN):Filed(FUN_ID)
 * ——>FILE_ID Table(CMS_CONTENT) ——>include:jsp打出来 }
 * 
 */
public class CMSTag extends TagSupport {

	//对应cms_fun的fun_id
	private String value;
	
	//是否异步加载,默认是同步加载
	private boolean defer;

	//参数名
	private String def_name;
	//参数值
	private String def_val;

	public boolean getDefer() {
		return defer;
	}

	public void setDefer(boolean defer) {
		this.defer = defer;
	}

	public String getDef_name() {
		return def_name;
	}

	public void setDef_name(String def_name) {
		this.def_name = def_name;
	}

	public String getDef_val() {
		return def_val;
	}

	public void setDef_val(String def_val) {
		this.def_val = def_val;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		if(value.indexOf("$")==0){//表示传递的是对象
			String req=value.substring(2,value.length()-1);
			this.value = (String) pageContext.getAttribute(req);
		}else{
			this.value=value;
		}
	}


	@Override
	public int doStartTag() throws JspException {
		
		if ("".equals(value)) return EVAL_PAGE;
		String page = "";
		String param = "";
		HttpSession session = pageContext.getSession();
		try {
			HashMap cmsContent=null;//CmsCacheUtil.getCacheContent(value, session);
			if(cmsContent!=null && !cmsContent.isEmpty()){
				page = (String)cmsContent.get("path");
			}else{
				return EVAL_PAGE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		JspWriter out = pageContext.getOut();
//		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
//		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();

		//增加参数判断校验
		if (StringUtils.isNotEmpty(def_name) && StringUtils.isNotEmpty(def_val)) {
			String [] def_names = new String[] {def_name};
			if (def_name.indexOf(",") >= 0) {
				def_names = def_name.split(","); 
			}
			String [] def_vals = new String[] {def_val};
			if (def_name.indexOf(",") >= 0) {
				def_vals = def_val.split(","); 
			}
			
			if (def_names.length == def_vals.length) {
				int len = def_names.length;
				for (int i = 0; i < len; i++) {
					if (i == 0) {
						param += def_names[i] + "=" + def_vals[i];
					} else {
						param += "&" + def_names[i] + "=" + def_vals[i];
					}
				}
			}
		}
		
		if (StringUtils.isNotEmpty(param)) {
			page = page + "?" + param;
		}		
		
		if (!"".equals(page) && null != page) {
				try {
					if(defer){//异步加载
						JspWriter jspWriter = pageContext.getOut();
						StringBuffer sb = new StringBuffer("");
						sb.append(getDeferHtml(page));
						jspWriter.print(sb.toString());
					}else{//同步加载
						pageContext.include(page);
					}
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return (EVAL_PAGE);
	}

	private String getDeferHtml(String page) {
		String rand = new Random().nextInt(9999)+"";
		String appendId = value+"_"+rand;
		StringBuffer sb = new StringBuffer("");
		sb.append("<div id='"+appendId+"'>");
		sb.append("<script type='text/javascript'>CommonLoad.loadJSP('"+appendId+"','"+page+"','',true,'',true);</script>");
		sb.append("</div>");
		return sb.toString();
	}
}
