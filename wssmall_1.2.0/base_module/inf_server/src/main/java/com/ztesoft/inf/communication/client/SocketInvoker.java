package com.ztesoft.inf.communication.client;

import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import params.ZteResponse;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.common.util.date.DateUtil;
import com.ztesoft.common.util.web.SocketUtils;
import com.ztesoft.inf.extend.freemarker.TemplateUtils;
import com.ztesoft.inf.extend.xstream.XStream;
import com.ztesoft.inf.framework.commons.CodedException;

public class SocketInvoker extends Invoker {

	int port;
	private InetSocketAddress address;
	private static XStream xstream = XStream.instance();

	@Override
	public Object invoke(InvokeContext context) throws Exception {

		context.setEndpoint(endpoint);
		context.setRequestTime(DateUtil.currentTime());
		String reqString = generateRequestString(context);
		context.setRequestString(reqString);
		String rspString = sendRequest(reqString);
		Object result = rspString;
		context.setResponseTime(DateUtil.currentTime());
		context.setResultString(rspString);

		StringUtils.printInfo("\n\n reqStr=\n" + reqString + "\n\n rspStr=\n"
				+ rspString);

		if (transformTemplate != null) {

			try {
				String resultStr = rspString;
				Map rootMap = new HashMap();
				rootMap.put("rsp", rspString);
				TemplateUtils.addUtils(rootMap);
				StringWriter out = new StringWriter();
				transformTemplate.process(rootMap, out);
				resultStr = out.toString();
				Class<?> clazz = Class.forName(rspPath);
				ZteResponse resp = (ZteResponse) JsonUtil.fromJson(resultStr, clazz);
				return resp;
//				result = xstream.fromXML(resultStr, mapperContext);
			} catch (Exception e) {
				throw new CodedException("9003", "根据返回的消息转为目标格式时出错.", e);
			}
		}

		return result;

	}

	private synchronized SocketAddress getAddress() {
		if (address == null) {
			address = new InetSocketAddress(endpoint, port);
		}
		return address;
	}

	private String sendRequest(String reqString) throws Exception {
		Socket socket = null;
		try {
			socket = SocketUtils.newClient(2, timeout * 1000, 32 * 1024,
					32 * 1024);
			socket.connect(getAddress());
			return SocketUtils.sendAndRecieve(socket, reqString);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (Exception ex) {
				}
			}
		}
	}
}
