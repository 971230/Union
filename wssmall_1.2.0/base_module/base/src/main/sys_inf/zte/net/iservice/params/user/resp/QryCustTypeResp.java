package zte.net.iservice.params.user.resp;

import java.util.Map;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class QryCustTypeResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="系统参数",type="Map",isNecessary="Y",desc="resp_no:\"响应流水号,格式：时间戳（14位，格式：yyyymmddhh24miss）+渠道编码(2位，01:IVR客服系统；02:53客服；03：LBS系统；00：转售系统)+4位序列号\" " +
				" resp_time : \"响求时间，格式：yyyymmddhh24miss \" " +
				" resp_code : \"响应结果编码，0000：成功\"  " +
				" resp_msg : 响应结果描述 ")
	private Map<String, Object> base_resp;
	@ZteSoftCommentAnnotationParam(name="业务参数",type="Map",isNecessary="Y",desc="业务参数")
	private Map<String, Object>serv_resp;
	
	public Map<String, Object> getBase_resp() {
		return base_resp;
	}
	public void setBase_resp(Map<String, Object> base_resp) {
		this.base_resp = base_resp;
	}
	public Map<String, Object> getServ_resp() {
		return serv_resp;
	}
	public void setServ_resp(Map<String, Object> serv_resp) {
		this.serv_resp = serv_resp;
	}
}
