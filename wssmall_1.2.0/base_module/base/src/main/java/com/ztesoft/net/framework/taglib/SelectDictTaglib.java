package com.ztesoft.net.framework.taglib;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.service.IDictManager;
import com.ztesoft.net.mall.core.service.impl.cache.DictManagerCacheProxy;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import java.util.List;
import java.util.Map;

public class SelectDictTaglib extends EnationTagSupport {
	private String attr_code;
	private String curr_val;
	private String filter_val; // 过滤值，用逗号分割
	private String id;
	private String name;
	private String style;
	private String disabled;
	private String appen_options;
	private IDictManager dictManager;
	@Override
	@SuppressWarnings("unchecked")
	public int doEndTag() throws JspException { //
		IDictManager dictManager = SpringContextHolder.getBean("dictManager");
		DictManagerCacheProxy dc=new DictManagerCacheProxy(dictManager);
		List<Map> staticDatas=dc.loadData(attr_code);
		
		if (StringUtil.isEmpty(style)) {
			this.setStyle("min-width:80px;");
		}
		String app_disabled = "";
		if("true".equals(disabled)){
			app_disabled = " disabled='disabled'";
		}
		StringBuffer selectBuffer = new StringBuffer("<select class='ipttxt' ").append(
				" attr_code=").append("'").append(attr_code).append("'").append(" name=").append("'").append(name).append("'")
				.append(" id=").append("'").append(id).append("'").append(" style= '").append(style).append("'").append(app_disabled)
				.append(">");
		
		if(!StringUtil.isEmpty(appen_options)){
			selectBuffer.append(appen_options);
		}
		if(staticDatas!=null && staticDatas.size()>0){
			for (Map date : staticDatas) {
				String selected_str = "";
				String value = (String) date.get("value");
				String value_desc = (String) date.get("value_desc");
				
				//filter_val
				
				if (!StringUtil.isEmpty(curr_val) && value !=null && value.equals(curr_val)) {
					selected_str = " selected ";
				}
				
				
				selectBuffer.append(optionHtml(value, value_desc, selected_str));
			}
		}
		selectBuffer.append("</select>");
		this.print(selectBuffer.toString());

		return Tag.EVAL_BODY_INCLUDE;
	}

	private String optionHtml(String value, String desc, String selected) {
		return "<option value=" + value + selected + ">" + desc + "</option>";
	}

	@Override
	public int doStartTag() throws JspException {
		return Tag.EVAL_PAGE;
	}

	public String getAttr_code() {
		return attr_code;
	}

	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}

	public String getCurr_val() {
		return curr_val;
	}

	public void setCurr_val(String curr_val) {
		this.curr_val = curr_val;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getAppen_options() {
		return appen_options;
	}

	public void setAppen_options(String appen_options) {
		this.appen_options = appen_options;
	}

	public IDictManager getDictManager() {
		return dictManager;
	}

	public void setDictManager(IDictManager dictManager) {
		this.dictManager = dictManager;
	}

	public String getFilter_val() {
		return filter_val;
	}

	public void setFilter_val(String filter_val) {
		this.filter_val = filter_val;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	
	

}
