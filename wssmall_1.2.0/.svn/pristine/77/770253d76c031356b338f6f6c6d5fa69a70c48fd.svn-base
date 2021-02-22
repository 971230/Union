package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.SmsSendResp;

public class SmsSendReq extends ZteRequest<SmsSendResp>{
	@ZteSoftCommentAnnotationParam(name="短信接收号码",type="String",isNecessary="Y",desc="短信接收号码")
	private String acc_nbr;
	
	@ZteSoftCommentAnnotationParam(name="短信接收内容",type="String",isNecessary="Y",desc="内容为空走预警短信，不为空直接发送")
	private String smsContent; 
	
	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.service.wssmall.ecs.smsSend";
	}

}
