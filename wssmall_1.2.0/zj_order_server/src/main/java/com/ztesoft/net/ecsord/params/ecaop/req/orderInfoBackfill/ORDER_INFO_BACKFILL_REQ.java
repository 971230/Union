package com.ztesoft.net.ecsord.params.ecaop.req.orderInfoBackfill;

import java.util.Map;

public class ORDER_INFO_BACKFILL_REQ {

	private Map<String, String> REQ_HEAD;
	private Map<String, Object> REQ_DATA;

	public Map<String, String> getREQ_HEAD() {
		return REQ_HEAD;
	}

	public void setREQ_HEAD(Map<String, String> rEQ_HEAD) {
		REQ_HEAD = rEQ_HEAD;
	}

	public Map<String, Object> getREQ_DATA() {
		return REQ_DATA;
	}

	public void setREQ_DATA(Map<String, Object> rEQ_DATA) {
		REQ_DATA = rEQ_DATA;
	}

}
