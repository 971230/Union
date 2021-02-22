package com.ztesoft.inf.communication.client;

import java.util.Map;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.inf.framework.commons.CodedException;


public class RMIInvoker extends Invoker {

	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(InvokeContext context) throws Exception {
		Object retObj = null;
		try {
			context.setRequestTime(DateUtil.currentTime());
			context.setRequestString(generateRequestString(context));
			RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
			factory.setServiceInterface(CrmRemoteCall.class);
			factory.setServiceUrl(endpoint);
			factory.afterPropertiesSet();
			CrmRemoteCall service = (CrmRemoteCall) factory.getObject();
			Map parameters = (Map) context.getParameters();
			String serviceName = (String) parameters.get("serviceName");
			String methodName = (String) parameters.get("methodName");
			Object[] param = (Object[]) parameters.get("param");
			CrmCallBack callBack =  (CrmCallBack)parameters.get("crmCallBack");
			retObj = service.remoteCall(serviceName, methodName, param, callBack);
			return retObj;
		} catch (Exception e) {
			context.setFailure(e.getMessage());
			throw new CodedException("9003", "RMI请求["+context.getOperationCode()+"]失败:"+e.getMessage(), e);
		}finally {
			if(retObj!=null){
				context.setResponeString(retObj.toString());
			}
			context.setResponseTime(DateUtil.currentTime());
		}
	}
	
	@Override
	protected String generateRequestString(InvokeContext context) {
		return context.getParameters().toString();
	}

}
