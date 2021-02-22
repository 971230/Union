package com.ztesoft.net.cms.plugin;

import com.ztesoft.net.cms.core.model.DataField;
import com.ztesoft.net.cms.core.plugin.AbstractFieldPlugin;

/**
 * 多行文本框插件
 * @author kingapex
 * 2010-7-5下午03:10:04
 */
public class TextareaFieldPlugin extends AbstractFieldPlugin {
   
	
	@Override
	public String onDisplay(DataField field, Object value) {
		StringBuffer html = new StringBuffer();
		html.append("<textarea");
		html.append(" name=\"");
		html.append(field.getEnglish_name());
		html.append("\" style=\"width:250px;height:100px\" ");
		html.append(this.wrappValidHtml(field));
		html.append(">");
		if(value!=null){
			html.append(value);
		}
	
		html.append("</textarea>");
		
		return html.toString();
	}

	@Override
	public String getDataType() {
		 
		return "text";
	}
	@Override
	public String getAuthor() {
		
		return "kingapex";
	}

	
	@Override
	public String getId() {
		
		return "textarea";
	}

	
	@Override
	public String getName() {
		
		return "多行文本框";
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
	public int getHaveSelectValue() {
		return 0;
	}

}
