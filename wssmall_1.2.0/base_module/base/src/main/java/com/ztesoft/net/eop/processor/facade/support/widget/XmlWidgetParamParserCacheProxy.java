package com.ztesoft.net.eop.processor.facade.support.widget;

import java.util.Map;

import com.ztesoft.net.eop.processor.widget.IWidgetParamParser;
import com.ztesoft.net.framework.cache.AbstractCacheProxy;

public class XmlWidgetParamParserCacheProxy extends AbstractCacheProxy implements
		IWidgetParamParser {
	private static String cacheName = "widget_key";
	private IWidgetParamParser xmlWidgetParamParserImpl;
	
	public XmlWidgetParamParserCacheProxy(IWidgetParamParser _xmlWidgetParamParserImpl) {
		super(cacheName);
		xmlWidgetParamParserImpl =  _xmlWidgetParamParserImpl;
	
	}

	@Override
	public Map<String, Map<String, Map<String, String>>> parse() {
	
		Object obj = this.cache.get("obj");
		
		if(obj==null){
			  obj  =this.xmlWidgetParamParserImpl.parse();
			  this.cache.put("obj", obj);
		//	  logger.info("read from disc");
		} else{
		//	logger.info("read from cache");
		}
		
		return ( Map<String, Map<String, Map<String, String>>>) obj;
	}

}
