package com.ztesoft.inf.communication.client;

import java.util.Map;

import com.ztesoft.common.util.JsonUtil;

public class HttpJsonInvoker extends HttpClientInvoker {

	@Override
	protected String generateRequestString(InvokeContext context) {

		Object params = context.getParameters();
		String str = JsonUtil.toJson(params);
		logger.debug("json===>" + str);

		return str;
	}

	@Override
	protected Object dealResult(InvokeContext context) {
		String resultStr = context.getResponeString();
		Map result = JsonUtil.fromJson(resultStr, Map.class);
		logger.debug("result===>" + result.toString());
		return result;
	}
}