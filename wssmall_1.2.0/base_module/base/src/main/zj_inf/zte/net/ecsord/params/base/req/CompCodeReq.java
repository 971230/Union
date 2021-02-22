package zte.net.ecsord.params.base.req;

import params.ZteRequest;
import zte.net.ecsord.params.base.resp.CompCodeResp;

import com.ztesoft.api.ApiRuleException;

public class CompCodeReq extends ZteRequest<CompCodeResp>{
	private String channel_id;
	
	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.ecsord.compCodeReq";
	}
}
