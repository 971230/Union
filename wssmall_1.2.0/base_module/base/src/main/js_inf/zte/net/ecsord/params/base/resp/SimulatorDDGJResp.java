package zte.net.ecsord.params.base.resp;

import params.ZteResponse;


public class SimulatorDDGJResp extends ZteResponse {
	private String order_id;
	private String result_str;

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getResult_str() {
		return result_str;
	}

	public void setResult_str(String result_str) {
		this.result_str = result_str;
	}

}
