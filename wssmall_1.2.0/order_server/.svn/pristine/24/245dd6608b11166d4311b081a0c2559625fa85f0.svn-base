package com.ztesoft.net.mall.service;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.mall.service.impl.FormatJsonProcessor;
import com.ztesoft.net.mall.service.impl.FormatXMLProcessor;


/**
 * @author Reason.Yea 
 * @version 创建时间：2014-5-26
 */
public abstract class IFormatProcess {
	public abstract String getMsg(String code,String msg);
	public abstract ZteRequest getZteRequest(String req, Class zteReqClass);
	public abstract String getZteResponse(ZteResponse res) ;
	
	public static final String TYPE_JSON="json";
	public static final String TYPE_XML="xml";
	
	public static final String MSG_CODE_ERROR="-1";
	public static final String MSG_CODE_SUCCESS="0";
	
	public static final String ERROR_REQ_PARAM="param can not be empty";
	public static final String ERROR_SERV_NOT_FOUND="serv not found";
	public static final String ERROR_EXCEPTION="exception unkown";
	public static final String ERROR_REQ_FORMAT="req format error";
	public static final String SUCCESS_OK="req ok";
	
	
	public static IFormatProcess getProcessor(String type){
		if(StringUtils.isEmpty(type)|| type.equals(TYPE_JSON)){
			return new FormatJsonProcessor();
		}else{
			return new FormatXMLProcessor();
		}
	}
	
}
