package com.ztesoft.rop.webservice;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.rop.webservice.processor.WSJsonProcessor;
import com.ztesoft.rop.webservice.processor.WSXMLHSProcessor;
import com.ztesoft.rop.webservice.processor.WSXMLProcessor;

/**
 * @author Reason.Yea 
 * @version 创建时间：2014-5-26
 */
public abstract class IWSProcess {
	public abstract String getMsg(String code,String msg);
	public abstract String getMsgToHs(String code,String msg);
	public abstract ZteRequest getZteRequest(String req, Class zteReqClass);
	public abstract String getZteResponse(ZteResponse res);
	public abstract String getVerifyCode(String str);
	
	public static final String TYPE_JSON="json";
	public static final String TYPE_XML="xml";
	public static final String TYPE_XMLHS="xmlhs";
	
	public static final String MSG_CODE_ERROR="-1";
	public static final String MSG_CODE_SUCCESS="1";
	
	public static final String MSG_CODE_ERROR_HS="E";
	public static final String MSG_CODE_SUCCESS_HS="S";
	
	public static final String ERROR_REQ_PARAM="param can not be empty";
	public static final String ERROR_SERV_NOT_FOUND="serv not found";
	public static final String ERROR_EXCEPTION="exception unkown";
	public static final String ERROR_REQ_FORMAT="req format error";
	public static final String ERROR_VERIFY_CODE_FORMAT="unauthorized";
	public static final String SUCCESS_OK="req ok";
	
	
	public static IWSProcess getProcessor(String type){
		if(StringUtils.isEmpty(type)|| type.equals(TYPE_JSON)){
			return new WSJsonProcessor();
		}else if(TYPE_XMLHS.equals(type)){
			return new WSXMLHSProcessor();
		}else{
			return new WSXMLProcessor();
		}
	}
	
}
