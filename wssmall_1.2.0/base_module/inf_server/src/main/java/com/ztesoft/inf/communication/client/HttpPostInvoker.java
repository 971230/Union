package com.ztesoft.inf.communication.client;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.common.util.web.HttpUtils;
import com.ztesoft.inf.framework.commons.CodedException;

public class HttpPostInvoker extends Invoker {
	private static Logger logger = Logger.getLogger(HttpPostInvoker.class);
	@Override
	public Object invoke(InvokeContext context) throws Exception {
		try {
			context.setEndpoint(endpoint);
			context.setRequestTime(DateUtil.currentTime());
			Object params = context.getParameters();
			String param_value = JsonUtil.toJson(params);
			Map paramMap = JsonUtil.fromJson(param_value, Map.class);
			String reqString = "";
			Set<String> key = paramMap.keySet();
	    	for(Iterator it = key.iterator(); it.hasNext();){
	    		String mapKey = (String)it.next();   	
	    		String desc = paramMap.get(mapKey).toString();
	    		reqString = mapKey + "=" + desc + "&";
	    	}
	    	reqString = reqString.substring(0, reqString.length()-1);
			context.setRequestString(reqString);
			String url = context.getEndpoint() + "?" + reqString;
			logger.info(url);
			context.setResponeString(HttpUtils.getContentByUrl(url, timeout));
			StringUtils.printInfo("\n\n reqStr=\n" + reqString
					+ "\n\n rspStr=\n" + context.getResponeString());
		} catch (Exception e) {
			context.setFailure(e.getMessage());
			throw new CodedException("9003", "HTTP请求["
					+ context.getOperationCode() + "]失败", e);
		} finally {
			context.setResponseTime(DateUtil.currentTime());
		}
		return context.getResponeString();
	}

}
