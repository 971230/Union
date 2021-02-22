package com.ztesoft.net.eop.processor.facade.support.widget;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import com.sun.xml.messaging.saaj.util.ByteOutputStream;
import com.ztesoft.net.eop.processor.widget.IWidgetCfgHtmlParser;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.utils.FreeMarkerUtil;
import com.ztesoft.net.eop.sdk.widget.IWidget;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 本地挂件配置html解析器
 * @see com.ztesoft.net.eop.sdk.widget.IWidget
 * @author kingapex
 * 2010-2-15下午01:48:18
 */
public class LocalWidgetCfgHtmlPaser implements IWidgetCfgHtmlParser{
 
	@Override
	public String pase(Map<String,String> widgetParams){
		
		String type = widgetParams.get("type");
	
		//挂件类
		IWidget widget =SpringContextHolder.getBean(type);
		if(widget==null) throw new RuntimeException("widget["+type+"]not found");
 		//解析出挂件config的html
 		String content = widget.setting(widgetParams);
 		widgetParams.put("content", content);

		try {
			String fld = EopSetting.EOP_PATH + "/eop/";
			Configuration cfg = FreeMarkerUtil.getFolderCfg(fld);
			Template temp = cfg.getTemplate("widget_setting.html");
			ByteOutputStream stream = new ByteOutputStream();
			Writer out = new OutputStreamWriter(stream);
			temp.process(widgetParams, out);
			out.flush();
			content = stream.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	
 
 
	
	
}
