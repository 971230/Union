package com.ztesoft.net.app.base.core.service.solution.impl;

import com.ztesoft.net.app.base.core.service.solution.IProfileLoader;
import com.ztesoft.net.eop.sdk.context.EopSetting;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ProfileLoaderImpl implements IProfileLoader {

	protected final Logger logger = Logger.getLogger(getClass());
	@Override
	public Document load(String productId) {
		String xmlFile = EopSetting.PRODUCTS_STORAGE_PATH+"/"+productId +"/profile.xml"; 
		try {
		    DocumentBuilderFactory factory = 
		    DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document document = builder.parse(xmlFile);
		    return document;
		}
		catch (Exception e) {
			logger.info(e);
			e.printStackTrace();
			throw new RuntimeException("load ["+productId +"] profile error" );
		} 	 
		 
	}

}
