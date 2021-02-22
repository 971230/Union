package com.ztesoft.inf.communication.client.vo;

import java.io.Serializable;
import java.util.Map;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;

public class ClientEndPoint  implements Serializable{

	private String endpointId;
	private String endpointAddress;
	private String endpointDesc;
	private Integer timeout;
	private Integer httpPostTimeOut;//http请求超时时间
	private Integer httpReadTimeOut;//http读取超时时间
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getHttpPostTimeOut() {
		return httpPostTimeOut;
	}

	public void setHttpPostTimeOut(Integer httpPostTimeOut) {
		this.httpPostTimeOut = httpPostTimeOut;
	}

	public Integer getHttpReadTimeOut() {
		return httpReadTimeOut;
	}

	public void setHttpReadTimeOut(Integer httpReadTimeOut) {
		this.httpReadTimeOut = httpReadTimeOut;
	}

	public ClientEndPoint() {
	}

	public ClientEndPoint(Map map) {
		setValues(map);
	}

	private void setValues(Map map) {
		endpointId = (String) map.get("ep_id");
		endpointAddress = (String)map.get("ep_address");
 			 //"http://localhost:8080/CrmWeb/services/exchangeSOAP";
		endpointDesc = (String) map.get("ep_desc");
		type = (String) map.get("ep_type");
		String _timeout = map.get("timeout").toString();
		if (!StringUtil.isEmpty(_timeout))
			timeout = Integer.parseInt(_timeout);
		String _httpPostTimeOut = Const.getStrValue(map, "http_post_time_out");
		if (!StringUtil.isEmpty(_httpPostTimeOut))
			httpPostTimeOut = Integer.parseInt(_httpPostTimeOut);
		String _httpReadTimeOut = Const.getStrValue(map, "http_read_time_out");
		if (!StringUtil.isEmpty(_httpReadTimeOut))
			httpReadTimeOut = Integer.parseInt(_httpReadTimeOut);
	}

	public String getEndpointId() {
		return endpointId;
	}

	public void setEndpointId(String endpointId) {
		this.endpointId = endpointId;
	}

	public String getEndpointAddress() {
		return endpointAddress;
	}

	public void setEndpointAddress(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	public String getEndpointDesc() {
		return endpointDesc;
	}

	public void setEndpointDesc(String endpointDesc) {
		this.endpointDesc = endpointDesc;
	}
}
