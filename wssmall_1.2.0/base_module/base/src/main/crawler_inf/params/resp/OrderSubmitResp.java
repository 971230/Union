package params.resp;

import params.ZteResponse;

public class OrderSubmitResp extends ZteResponse {

	private String essOrderId;

	public String getEssOrderId() {
		return essOrderId;
	}

	public void setEssOrderId(String essOrderId) {
		this.essOrderId = essOrderId;
	}
}
