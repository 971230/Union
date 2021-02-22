package zte.params.order.resp;

import java.util.List;

import params.ZteResponse;
import params.order.req.OrderExceptionCollectReq;

public class OrderExceptionLogsListResp extends ZteResponse {

	private List<OrderExceptionCollectReq> exceptionList;

	public List<OrderExceptionCollectReq> getExceptionList() {
		return exceptionList;
	}

	public void setExceptionList(List<OrderExceptionCollectReq> exceptionList) {
		this.exceptionList = exceptionList;
	}
	
}
