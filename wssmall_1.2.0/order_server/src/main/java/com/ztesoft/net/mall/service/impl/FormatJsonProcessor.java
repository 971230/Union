package com.ztesoft.net.mall.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.mall.service.IFormatProcess;

import commons.CommonTools;

/**
 * @author Reason.Yea 
 * @version 创建时间：2014-5-26
 */
public class FormatJsonProcessor extends IFormatProcess {

	@Override
	public String getMsg(String code,String msg) {
		return "{code:"+code+",msg:"+msg+"}";
	}

	@Override
	public ZteRequest getZteRequest(String req, Class zteReqClass) {
		return CommonTools.jsonToBean(req, zteReqClass);
	}

	@Override
	public String getZteResponse(ZteResponse res) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(res);
		} catch (Exception outerErr) {
			Map<String,String> map = new LinkedHashMap<String,String>();
			try {
				BeanUtils.beanToMap(map, res);
			} catch (Exception InnerErr) {
				InnerErr.printStackTrace();
			}
			return CommonTools.beanToJson(map);	
		} 
	}

	

}
