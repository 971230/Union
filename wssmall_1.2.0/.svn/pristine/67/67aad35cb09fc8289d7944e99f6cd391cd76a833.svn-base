package com.ztesoft.inf.communication.client;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.common.util.web.HttpUtils;
import com.ztesoft.inf.framework.commons.CodedException;


public class HttpUrlInvoker extends Invoker {

	@Override
	public Object invoke(InvokeContext context) throws Exception {
		try {
			context.setEndpoint(endpoint);
			context.setRequestTime(DateUtil.currentTime());
			String reqString = generateRequestString(context);
			context.setRequestString(reqString);
			String url = context.getEndpoint() + "?" + reqString;
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
