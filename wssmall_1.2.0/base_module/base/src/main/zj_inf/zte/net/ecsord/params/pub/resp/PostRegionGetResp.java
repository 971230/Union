package zte.net.ecsord.params.pub.resp;

import java.util.Map;

import params.ZteResponse;

public class PostRegionGetResp extends ZteResponse {

	private Map postRegion;

	public Map getPostRegion() {
		return postRegion;
	}

	public void setPostRegion(Map postRegion) {
		this.postRegion = postRegion;
	}
}
