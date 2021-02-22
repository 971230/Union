package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;

/**
 * 工单更新参数
 * 
 * @author 宋琪
 * @date 2017年12月1日
 */
public class OrderWorksUpdateInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String work_order_id;
	private String order_id;
	private String type;
	private String status;

	public String getWork_order_id() {
		return work_order_id;
	}

	public void setWork_order_id(String work_order_id) {
		this.work_order_id = work_order_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
