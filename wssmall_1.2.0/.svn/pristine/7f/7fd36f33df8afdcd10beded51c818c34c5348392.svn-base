package zte.params.ecsord.req;

import java.util.List;

import params.ZteRequest;
import zte.params.ecsord.resp.EMSLogisticsInfoResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class EMSLogisticsInfoReq extends ZteRequest<EMSLogisticsInfoResp> {
	
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name="物流单号",type="String",isNecessary="Y",desc="物流单号")
	private String mail_num;

	private List list;
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.ems.getEmsLogisticsInfo";
	}

	public String getMail_num() {
		
		return mail_num;
	}

	public void setMail_num(String mail_num) {
		this.mail_num = mail_num;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	
	
}
