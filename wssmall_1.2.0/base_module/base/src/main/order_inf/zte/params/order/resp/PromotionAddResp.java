package zte.params.order.resp;

import params.ZteResponse;

public class PromotionAddResp extends ZteResponse {
	private String pmtid;

	public String getPmtid() {
		return pmtid;
	}

	public void setPmtid(String pmtid) {
		this.pmtid = pmtid;
	}
}
