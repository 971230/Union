package com.ztesoft.net.cms.plugin;

import com.ztesoft.net.cms.core.model.DataField;
import com.ztesoft.net.cms.core.plugin.AbstractFieldPlugin;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 复选框字段插件
 * 
 * @author kingapex 2010-7-6下午05:23:47
 */
public class CheckboxFieldPlugin extends AbstractFieldPlugin {

	
	@Override
	public int getHaveSelectValue() {

		return 1;
	}

	
	@Override
	public String onDisplay(DataField field, Object value) {
		StringBuffer html = new StringBuffer();

		String[] haveValue = null;

		if (value != null)
			haveValue = value.toString().split(",");

		String values = field.getSave_value();
		int i = 0;
		if (values != null) {
			String[] valueAr = values.split(",");
			for (String v : valueAr) {
				html.append("<input type=\"checkbox\"");
				html.append(" name=\"");
				html.append(field.getEnglish_name());
				html.append("\" value=\"");
				html.append(i);
				html.append("\"");

				if (haveValue != null) {
					if (checkValue(haveValue, ""+i)) {
						html.append(" checked=\"true\"");
					}
				}
				html.append(" />");
				html.append(v);
				i++;
			}

		}

		return html.toString();
	}

	
	@Override
	public void onSave(Map article, DataField field) {
		HttpServletRequest request = ThreadContextHolder.getHttpRequest();
		String[] values = request.getParameterValues(field.getEnglish_name());
		article.put(field.getEnglish_name(), StringUtil.arrayToString(values,
				","));
	}

	
	@Override
	public Object onShow(DataField field, Object value) {
		String valueStr = field.getSave_value();
		if (!StringUtil.isEmpty((String)value)  && !StringUtil.isEmpty(valueStr)) {
			StringBuffer result = new StringBuffer();
			String[] haveValues = value.toString().split(",");
			int i = 0;
			for (String v : haveValues) {
				if (i != 0)
					result.append("、");

				String[] values = valueStr.split(",");
				
				result.append(values[Integer.valueOf(v)]);
				
				i++;
			}

			return result.toString();
		} else
			return "";
	}

	private boolean checkValue(String[] haveValue, String value) {
		if (haveValue == null)
			return false;
		for (String v : haveValue) {
			if (value.equals(v))
				return true;
		}
		return false;
	}

	
	@Override
	public String getAuthor() {

		return "kingapex";
	}

	
	@Override
	public String getId() {

		return "checkbox";
	}

	
	@Override
	public String getName() {

		return "复选框";
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
