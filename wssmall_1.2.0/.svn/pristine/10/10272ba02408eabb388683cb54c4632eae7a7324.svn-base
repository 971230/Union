package com.ztesoft.inf.communication.client;

import java.util.HashMap;

import com.ztesoft.common.application.AppClass;
import com.ztesoft.common.util.convert.ObjectSerialUtils;
import com.ztesoft.inf.communication.client.CrmCallBack;

/**
 * @author AyaKoizumi
 * @date 101026
 * @desc 服务端:响应crm远程调用
 * 
 * */
public class CrmLocalCallClient {

	// crm代码本地调用,方法入参是HashMap的key的object[]类型,通过转换成xml串传播(因为接口需要封装成xml,只能把对象转换成字符串)
	public void localCall(String serviceName, String methodName,
			String methodParam) throws Exception {
		this.localCall(serviceName, methodName, methodParam, null);
	}

	// crm代码本地调用,方法入参是HashMap的key的object[]类型,通过转换成xml串传播(因为接口需要封装成xml,只能把对象转换成字符串)
	public void localCall(String serviceName, String methodName,
			String methodXml, CrmCallBack callBack) throws Exception {
		AppClass aps = new AppClass();
		aps.loadFromClass(serviceName, null);
		// 转换入参
		// 对象反序列化
		ObjectSerialUtils objSerial = new ObjectSerialUtils();
		HashMap val = (HashMap) objSerial.readStringToObject(methodXml);

		Object[] _param = (Object[]) val.get("____ROOT");// 调用方法的数组对象
		Object result = aps.executeMethodNoAop(methodName, _param, null);
		if (callBack != null) {
			callBack.setResult(result);
			callBack.execute();
		}
	}
}
