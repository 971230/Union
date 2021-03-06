package com.ztesoft.net.mall.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.XmlUtils;
import com.ztesoft.net.mall.service.IFormatProcess;

/**
 * @author Reason.Yea 
 * @version 创建时间：2014-5-26
 */
public class FormatXMLProcessor  extends IFormatProcess {

	public String XML_TEMPLATE=//"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			"<root><code>replace_code</code><msg>replace_msg</msg></root>";
	@Override
	public String getMsg(String code,String msg) {
		return XML_TEMPLATE.replaceAll("replace_code", code).replaceAll("replace_msg", msg);
	}
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ZteRequest getZteRequest(String req, Class zteReqClass) {
		req = req.replaceAll("//", "");
		String regStartSpace = "^[　 ]*"; String regEndSpace = "[　 ]*$"; // 第一个是去掉前端的空格， 第二个是去掉后端的空格
		req = req.replaceAll(regStartSpace, "").replaceAll(regEndSpace, "");
		
		Map paramMap= XmlUtils.xmlToMap(req);
		try {
			paramMap.put("class", zteReqClass);
			ZteRequest<ZteResponse> obj = (ZteRequest<ZteResponse>)ConstructorUtils.invokeConstructor((Class)paramMap.get("class"), null);
			BeanUtils.mapToBean(paramMap, obj);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String getZteResponse(ZteResponse res) {
		Map<String,String> map = new LinkedHashMap<String,String>();
		try {
			BeanUtils.beanToMap(map, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return XmlUtils.mapToXml(map);
	}
	
	
	

}
