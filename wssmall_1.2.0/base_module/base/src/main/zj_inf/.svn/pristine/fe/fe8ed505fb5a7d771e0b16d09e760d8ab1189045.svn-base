package zte.net.ecsord.params.wyg.resp;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

@JsonIgnoreProperties(value = { "error_code","error_msg",
		"userSessionId","error_cause","body","resp","exec_path","rule_id","rule_name","item","result" })
public class ChargebackApplyWYGResponse  extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="唯一的接口流水号",type="String",isNecessary="Y",desc="ActiveNo：流水号)")
	private String ActiveNo;
	
	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="Y",desc="RespCode：返回代码   0000：成功 0001：失败")
	private String RespCode;
	
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="RespMsg：返回描述")
	private String RespMsg;

	public String getActiveNo() {
		return ActiveNo;
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	public String getRespCode() {
		return RespCode;
	}

	public void setRespCode(String respCode) {
		RespCode = respCode;
	}

	public String getRespMsg() {
		return RespMsg;
	}

	public void setRespMsg(String respMsg) {
		RespMsg = respMsg;
	}
	
	
}
