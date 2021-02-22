package zte.params.order.resp;

import java.util.List;

import params.ZteResponse;
import params.order.req.OutcallLogsReq;

public class OutcallLogsListResp extends ZteResponse {
	private List<OutcallLogsReq> outcalllogList;

	public List<OutcallLogsReq> getOutcalllogList() {
		return outcalllogList;
	}

	public void setOutcallLogList(List<OutcallLogsReq> outcalllogList) {
		this.outcalllogList = outcalllogList;
	}
	
}