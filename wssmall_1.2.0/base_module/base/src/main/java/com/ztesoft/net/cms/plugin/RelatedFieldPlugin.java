package com.ztesoft.net.cms.plugin;

import com.sun.xml.messaging.saaj.util.ByteOutputStream;
import com.ztesoft.net.cms.core.model.DataField;
import com.ztesoft.net.cms.core.plugin.AbstractFieldPlugin;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 相关文章插件
 * @author kingapex
 *
 */
public class RelatedFieldPlugin extends AbstractFieldPlugin {

	
	@Override
	public int getHaveSelectValue() {
		
		return 0;
	}

	
	@Override
	public String onDisplay(DataField field, Object value) {
	try{
			
			Map data = new HashMap();
			data.put("fieldname", field.getEnglish_name());
			data.put("value", value);
			Configuration cfg = new Configuration();
			cfg.setObjectWrapper(new DefaultObjectWrapper());	
			cfg.setDefaultEncoding("UTF-8");
			cfg.setLocale(java.util.Locale.CHINA);
			cfg.setEncoding(java.util.Locale.CHINA, "UTF-8");
			
		 
			cfg.setClassForTemplateLoading(this.getClass(), "");
			Template temp = cfg.getTemplate("RelatedFieldPlugin.html");
			ByteOutputStream stream = new ByteOutputStream();
			
			Writer out = new OutputStreamWriter(stream);
			temp.process(data, out);
			
			out.flush();
			String html = stream.toString();		
			
			return html;
		}
		catch(Exception e){
		 
			return "挂件解析出错"+e.getMessage();
		}
	}

	
	@Override
	public String getAuthor() {
		
		return "kingapex";
	}

	
	@Override
	public String getId() {
		
		return "related";
	}

	
	@Override
	public String getName() {
		
		return "相关文章";
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
