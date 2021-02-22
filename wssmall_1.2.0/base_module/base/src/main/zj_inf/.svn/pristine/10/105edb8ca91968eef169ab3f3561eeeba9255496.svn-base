package zte.net.ecsord.params.zb.vo;

import java.io.Serializable;
import java.util.List;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class RouteOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2128268531401507008L;

	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "OrderNo：订单编号")
	private String OrderNo;
	
	@ZteSoftCommentAnnotationParam(name = "物流单号", type = "String", isNecessary = "Y", desc = "MailNo：物流单号")
	private String MailNo;
	
	@ZteSoftCommentAnnotationParam(name = "具体物流步骤的父节点", type = "String", isNecessary = "Y", desc = "Steps：具体物流步骤的父节点")
	private List<RouteSteps> Steps;

	public String getOrderNo() {
		return OrderNo;
	}

	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}

	public String getMailNo() {
		return MailNo;
	}

	public void setMailNo(String mailNo) {
		MailNo = mailNo;
	}

	public List<RouteSteps> getSteps() {
		return Steps;
	}

	public void setSteps(List<RouteSteps> steps) {
		Steps = steps;
	}
	
}
