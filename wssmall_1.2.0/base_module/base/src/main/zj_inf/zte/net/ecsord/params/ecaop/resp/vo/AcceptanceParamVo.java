package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


public class AcceptanceParamVo implements Serializable {	
	@ZteSoftCommentAnnotationParam(name="受理单模板编码",type="String",isNecessary="N",desc="acceptanceTp：受理单模板编码")
	private String acceptanceTp;
	@ZteSoftCommentAnnotationParam(name="0：套打；1：白打",type="String",isNecessary="N",desc="acceptanceMode：0：套打；1：白打")
	private String acceptanceMode;
	@ZteSoftCommentAnnotationParam(name="受理单打印内容",type="String",isNecessary="N",desc="acceptanceForm：受理单打印内容")
	private String acceptanceForm;
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;
	public String getAcceptanceTp() {
		return acceptanceTp;
	}
	public void setAcceptanceTp(String acceptanceTp) {
		this.acceptanceTp = acceptanceTp;
	}
	public String getAcceptanceMode() {
		return acceptanceMode;
	}
	public void setAcceptanceMode(String acceptanceMode) {
		this.acceptanceMode = acceptanceMode;
	}
	public String getAcceptanceForm() {
		return acceptanceForm;
	}
	public void setAcceptanceForm(String acceptanceForm) {
		this.acceptanceForm = acceptanceForm;
	}
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

}