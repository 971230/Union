package com.ztesoft.inf.communication.client;

import java.util.HashMap;

import com.ztesoft.common.util.convert.ObjectSerialUtils;
import com.ztesoft.inf.framework.utils.ParseXml;

/**
 * @author AyaKoizumi
 * @date 101026
 * @desc 客户端:远程调用crm代码
 * 
 * */
public class CrmRemoteCallClient {

	// crm代码远程调用,方法入参是HashMap类型(里面只能存放ArrayList和String和HashMap3种类型的数据,可以嵌套),通过转换成xml串传播(因为接口需要封装成xml,只能把对象转换成字符串)
	public void remoteCall(String operationCode, String serviceName,
			String methodName, Object[] param, CrmCallBack callBack)
			throws Exception {
		CommCaller comCaller = new CommCaller();
		ParseXml parse = new ParseXml();
		HashMap _params = new HashMap();
		_params.put("serviceName", serviceName);
		_params.put("methodName", methodName);
		// 转换入参
		ObjectSerialUtils objSerial = new ObjectSerialUtils();
		if (param == null) {
			_params.put("param", null);
		} else {
			parse.setEncoding("");
			HashMap rootVo = new HashMap();
			rootVo.put("____ROOT", param);
			// 对象序列化
			String strObj = objSerial.writeObjectToString(rootVo);
			_params.put("param", strObj);
		}
		Object retObj = comCaller.invoke(operationCode, _params);
		if (callBack != null) {
			// 返回参数需要特殊处理
			// 原来的返回参数是这样的格式
			/*
			 * {Body={exchangeResponse={out=<?xml version="1.0"
			 * encoding="UTF-8"?>
			 * 
			 * <Root> <Header>
			 * <ExchangeId>Crm2#2201010262128080000000121</ExchangeId>
			 * <Response> <Code>0000</Code> <Message>成功</Message> </Response>
			 * </Header> <CrmRemoteCallResult>--调用返回的参数 <flag>-1</flag>
			 * <failure_desc>mzptest</failure_desc> </CrmRemoteCallResult>
			 * </Root> }}}
			 */
			// 综合上面的参数,需要获取CrmRemoteCallResult才是真正的调用返回参数,并且还要额外返回接口调用是否成功的标志
			// String _respone=(String)retObj;
			HashMap responeHashMap = (HashMap) retObj;
			HashMap body = (HashMap) responeHashMap.get("Body");
			HashMap exchangeResponse = (HashMap) body.get("exchangeResponse");
			String outStr = (String) exchangeResponse.get("out");
			HashMap out = parse.parseXml(outStr);
			HashMap root = (HashMap) out.get("Root");
			HashMap header = (HashMap) root.get("Header");
			HashMap interfaceRespone = (HashMap) header.get("Response");
			HashMap crmRemoteCallResult = (HashMap) root
					.get("CrmRemoteCallResult");
			HashMap _ret = new HashMap();
			String code = (String) interfaceRespone.get("Code");
			if (code == null || !code.equals("0000")) {
				String message = (String) interfaceRespone.get("Message");
				throw new Exception("远程调用失败!" + message);
			}
			_ret.putAll(crmRemoteCallResult);
			callBack.setResult(_ret);
			callBack.execute();
		}
	}

	// crm代码远程调用,方法入参是HashMap类型,通过转换成xml串传播(因为接口需要封装成xml,只能把对象转换成字符串)
	public void remoteCall(String serviceName, String methodName,
			Object[] param, CrmCallBack callBack) throws Exception {
		this.remoteCall("crm.CrmRemoteCall", serviceName, methodName, param,
				callBack);
	}

	// crm代码远程调用,方法入参是HashMap类型,通过转换成xml串传播(因为接口需要封装成xml,只能把对象转换成字符串)
	public void remoteCall(String serviceName, String methodName, Object[] param)
			throws Exception {
		this.remoteCall(serviceName, methodName, param, null);
	}
}
