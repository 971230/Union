package com.ztesoft.net.cms.plugin;

import com.ztesoft.net.cms.core.model.DataField;
import com.ztesoft.net.cms.core.plugin.AbstractFieldPlugin;

/**
 * 单选按钮插件
 * @author kingapex
 * 2010-7-5下午03:14:22
 */
public class RadioFieldPlugin extends AbstractFieldPlugin {



	
	@Override
	public String onDisplay(DataField field, Object value) {
		StringBuffer html = new StringBuffer();
		
		String values = field.getSave_value();
		int i=0;
		if(values!=null){
			String[] valueAr = values.split(",");
			for(String v :valueAr){
				html.append("<input type=\"radio\"");
				html.append(" name=\"");
				html.append(field.getEnglish_name());
				html.append("\" value=\"");
				html.append(i);
				html.append("\"");
				if(value==null && i==0){
					html.append(" checked=\"true\"");
				}
				if(value!=null && i==Integer.valueOf(""+value)){
					html.append(" checked=\"true\"");
				}
		 
				html.append(" />");	
				html.append(v);
				i++;
			}
			
		}
		

		
		return html.toString();
	}

	
	@Override
	public Object onShow(DataField field, Object value) {
		if(value!=null)
		{
			int index =  Integer.valueOf(value.toString());
			String valueStr = field.getSave_value();
			if(valueStr!=null){
				String[] values  = valueStr.split(",");
				return values[index];
			}
			return "";
		}
		else return "";
	}	
	
	@Override
	public String getAuthor() {
		
		return "kingapex";
	}

	
	@Override
	public String getId() {
		
		return "radio";
	}

	
	@Override
	public String getName() {
		
		return "单选按钮";
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
		return 1;
	}

}
