package com.ztesoft.net.cms.plugin;

import com.ztesoft.net.cms.core.model.DataField;
import com.ztesoft.net.cms.core.plugin.AbstractFieldPlugin;

import freemarker.template.utility.StringUtil;

/**
 * 单行文本框插件
 * @author kingapex
 * 2010-7-5下午02:55:26
 */
public class InputFieldPlugin extends AbstractFieldPlugin {

  
	
	@Override
	public String getAuthor() {
		
		return "kingapex";
	}

	
	@Override
	public String getId() {
		
		return "input";
	}

	
	@Override
	public String getName() {
		
		return "单行文本框";
	}

	
	@Override
	public String getType() {
		
		return "field";
	}

	
	@Override
	public String getVersion() {
		
		return "1.0";
	}

 

	
	@Override
	public String onDisplay(DataField field, Object value) {
		StringBuffer html = new StringBuffer("<input type=\"text\" style=\"width:450px\" name=\"");
		html.append(field.getEnglish_name());
		html.append("\""); 
		
		if(value!=null){
			html.append(" value=\"");
			value=StringUtil.HTMLEnc(value.toString());
			html.append(value);
			html.append("\"");
		}

		html.append(this.wrappValidHtml(field));
		
		html.append(" />");
		
		return html.toString();
	}

	
	@Override
	public int getHaveSelectValue() {
		 
		return 0;
	}



}
