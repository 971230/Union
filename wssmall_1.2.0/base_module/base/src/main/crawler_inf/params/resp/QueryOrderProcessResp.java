package params.resp;

import java.util.List;

import params.ZteResponse;

public class QueryOrderProcessResp extends ZteResponse {

	List<String> orderIdList;
	public List<String> getOrderIdList() {
		return orderIdList;
	}

	public void setOrderIdList(List<String> orderIdList) {
		this.orderIdList = orderIdList;
	}
}
