package zte.net.ecsord.params.base.resp;

import params.ZteResponse;


public class QueryUrlByOrderIdResp extends ZteResponse {
	private String action_url;

	public String getAction_url() {
		return action_url;
	}

	public void setAction_url(String action_url) {
		this.action_url = action_url;
	}

}
