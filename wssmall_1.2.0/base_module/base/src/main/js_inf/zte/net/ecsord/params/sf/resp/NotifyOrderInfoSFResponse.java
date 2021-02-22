package zte.net.ecsord.params.sf.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class NotifyOrderInfoSFResponse  extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="Y",desc="orderid：订单号")
	private String orderid;
	
	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="Y",desc="mailno：可多个单号，如子母件，以逗号分隔")
	private String mailno;
	
	@ZteSoftCommentAnnotationParam(name="签单返还运单号",type="String",isNecessary="Y",desc="return_tracking_no：签单返还运单号")
	private String return_tracking_no;
	
	@ZteSoftCommentAnnotationParam(name="代理运单号",type="String",isNecessary="Y",desc="agent_mailno：代理运单号")
	private String agent_mailno;
	
	@ZteSoftCommentAnnotationParam(name="原寄地代码",type="String",isNecessary="Y",desc="origincode：原寄地代码")
	private String origincode;
	
	@ZteSoftCommentAnnotationParam(name="原寄地代码",type="String",isNecessary="Y",desc="destcode：目的地代码")
	private String destcode;
	
	@ZteSoftCommentAnnotationParam(name="筛单结果",type="String",isNecessary="Y",desc="filter_result：1-人工确认，2-可收派3-不可以收派")
	private String filter_result;
	
	@ZteSoftCommentAnnotationParam(name="备注",type="String",isNecessary="Y",desc="remark：1-收方超范围，2-派方超范围，3-其他原因")
	private String remark;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getMailno() {
		return mailno;
	}

	public void setMailno(String mailno) {
		this.mailno = mailno;
	}

	public String getReturn_tracking_no() {
		return return_tracking_no;
	}

	public void setReturn_tracking_no(String return_tracking_no) {
		this.return_tracking_no = return_tracking_no;
	}

	public String getAgent_mailno() {
		return agent_mailno;
	}

	public void setAgent_mailno(String agent_mailno) {
		this.agent_mailno = agent_mailno;
	}

	public String getOrigincode() {
		return origincode;
	}

	public void setOrigincode(String origincode) {
		this.origincode = origincode;
	}

	public String getDestcode() {
		return destcode;
	}

	public void setDestcode(String destcode) {
		this.destcode = destcode;
	}

	public String getFilter_result() {
		return filter_result;
	}

	public void setFilter_result(String filter_result) {
		this.filter_result = filter_result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
