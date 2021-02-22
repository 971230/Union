package zte.params.member.req;

import params.ZteRequest;
import zte.params.member.resp.AskAddResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.Ask;
import com.ztesoft.net.framework.database.NotDbField;

public class AskAddReq extends ZteRequest<AskAddResp>{

	@ZteSoftCommentAnnotationParam(name="用户提问对象",type="String",isNecessary="Y",desc="用户提问对象",hasChild=true)
	private Ask ask;

	public Ask getAsk() {
		return ask;
	}

	public void setAsk(Ask ask) {
		this.ask = ask;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.ask.add";
	}
	
}
