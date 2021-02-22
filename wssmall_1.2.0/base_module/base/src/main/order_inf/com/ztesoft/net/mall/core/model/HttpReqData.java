package com.ztesoft.net.mall.core.model;


/**
 * Http请求数据
 * 
 * @author wui
 */
public class HttpReqData implements java.io.Serializable {
	private String method_name;
	private String version;
	private String http_req_exjson;
	private String http_resp_exjson;
	private String http_req_json;
	private String http_resp_json;
	private String create_time;
	public String getMethod_name() {
		return method_name;
	}
	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getHttp_req_exjson() {
		return http_req_exjson;
	}
	public void setHttp_req_exjson(String http_req_exjson) {
		this.http_req_exjson = http_req_exjson;
	}
	public String getHttp_resp_exjson() {
		return http_resp_exjson;
	}
	public void setHttp_resp_exjson(String http_resp_exjson) {
		this.http_resp_exjson = http_resp_exjson;
	}
	public String getHttp_req_json() {
		return http_req_json;
	}
	public void setHttp_req_json(String http_req_json) {
		this.http_req_json = http_req_json;
	}
	public String getHttp_resp_json() {
		return http_resp_json;
	}
	public void setHttp_resp_json(String http_resp_json) {
		this.http_resp_json = http_resp_json;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
}