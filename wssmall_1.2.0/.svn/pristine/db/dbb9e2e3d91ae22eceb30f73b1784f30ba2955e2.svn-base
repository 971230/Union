package zte.params.member.req;

import params.ZteRequest;
import zte.params.member.resp.AskGetResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class AskGetReq extends ZteRequest<AskGetResp> {

	@ZteSoftCommentAnnotationParam(name="问题ID",type="String",isNecessary="Y",desc="问题ID")
	private String ask_id;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.ask.get";
	}

	public String getAsk_id() {
		return ask_id;
	}

	public void setAsk_id(String ask_id) {
		this.ask_id = ask_id;
	}


}
