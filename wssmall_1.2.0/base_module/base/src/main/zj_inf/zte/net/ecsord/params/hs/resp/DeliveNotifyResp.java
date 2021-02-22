package zte.net.ecsord.params.hs.resp;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 *  出库信息回传SAP
 * @作者 Rapon
 * @创建日期 2016-07-23
 * @版本 V 1.0
 */
@JsonIgnoreProperties(value = { "error_code","error_msg",
		"userSessionId","error_cause","body","resp","exec_path","rule_id","rule_name","item","result" }) 
public class DeliveNotifyResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name = "‘S’成功；’E’失败", type = "String", isNecessary = "Y", desc = "‘S’成功；’E’失败")
	private String TYPE;
	@ZteSoftCommentAnnotationParam(name = "返回信息描述", type = "String", isNecessary = "Y", desc = "返回信息描述")
	private String MESSAGE;
	@ZteSoftCommentAnnotationParam(name = "预留字段1", type = "String", isNecessary = "Y", desc = "预留字段1")
	private String RESERVE1;
	@ZteSoftCommentAnnotationParam(name = "预留字段2", type = "String", isNecessary = "Y", desc = "预留字段2")
	private String RESERVE2;
	
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getMESSAGE() {
		return MESSAGE;
	}
	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}
	public String getRESERVE1() {
		return RESERVE1;
	}
	public void setRESERVE1(String rESERVE1) {
		RESERVE1 = rESERVE1;
	}
	public String getRESERVE2() {
		return RESERVE2;
	}
	public void setRESERVE2(String rESERVE2) {
		RESERVE2 = rESERVE2;
	}
}
