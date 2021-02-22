package zte.params.order.resp;

import java.util.List;

import params.ZteResponse;
import params.order.req.OrderHandleLogsReq;

public class OrderHandleLogsListResp extends ZteResponse {

	private List<OrderHandleLogsReq> logList;

	public List<OrderHandleLogsReq> getLogList() {
		return logList;
	}

	public void setLogList(List<OrderHandleLogsReq> logList) {
		this.logList = logList;
	}
 	
}
