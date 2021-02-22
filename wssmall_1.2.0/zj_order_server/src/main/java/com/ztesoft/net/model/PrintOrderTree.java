package com.ztesoft.net.model;

import java.io.Serializable;

import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class PrintOrderTree extends AutoOrderTree implements Serializable {
	
	private OrderTreeBusiRequest orderTree;
	private String acceptance_html;//受理单模板内容
	private String status;//0未打印、1已打印
	private String receipt_code;//受理单打印模板编码
	private String receipt_mode;//受理单打印模式(0：套打；1：白打)
	
	@Override
	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}

	@Override
	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}

	public String getAcceptance_html() {
		return acceptance_html;
	}

	public void setAcceptance_html(String acceptance_html) {
		this.acceptance_html = acceptance_html;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReceipt_code() {
		return receipt_code;
	}

	public void setReceipt_code(String receipt_code) {
		this.receipt_code = receipt_code;
	}

	public String getReceipt_mode() {
		return receipt_mode;
	}

	public void setReceipt_mode(String receipt_mode) {
		this.receipt_mode = receipt_mode;
	}
	
	
}
