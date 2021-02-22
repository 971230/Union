package com.ztesoft.net.cms.plugin;

import com.ztesoft.net.cms.core.model.DataField;
import com.ztesoft.net.cms.core.plugin.AbstractFieldPlugin;

/**
 * 日期控件字段插件
 * @author kingapex
 * 2010-7-7上午11:04:30
 */
public class DateFieldPlugin extends AbstractFieldPlugin {

	
	@Override
	public int getHaveSelectValue() {
		
		return 0;
	}

	
	@Override
	public String onDisplay(DataField field, Object value) {
		StringBuffer html = new StringBuffer();
		 
		html.append("<input type=\"text\" name=\"");
		html.append(field.getEnglish_name());
		html.append("\" readonly=\"true\"");
		if(value!=null){
			html.append("value=\"");
			html.append(value);
			html.append("\"");
		}
		html.append(" class=\"dateinput\" ");
		html.append(">");
	 
		return html.toString();
	}

	
	
	@Override
	public String getAuthor() {
		
		return "kingapex";
	}

	
	@Override
	public String getId() {
		
		return "dateinput";
	}

	
	@Override
	public String getName() {
		
		return "日期控件";
	}

	
	@Override
	public String getType() {
		
		return "field";
	}

	
	@Override
	public String getVersion() {
		
		return "1.0";
	}

}
