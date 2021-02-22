package com.ztesoft.net.model;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 1.1.1. 派单日志表（es_order_dispatch_log）实体类
 */
public class OrderDispatchLog implements Serializable {

	private static final long serialVersionUID = -4858034046843756253L;
	@ZteSoftCommentAnnotationParam(name = "工号池ID", type = "String", isNecessary = "Y", desc = "工号池ID")
	private String pool_id;
	@ZteSoftCommentAnnotationParam(name = "关联工号ID", type = "String", isNecessary = "Y", desc = "关联工号ID")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name = "订单ID 同一个订单在这张表中只有一条记录", type = "String", isNecessary = "Y", desc = "订单ID 同一个订单在这张表中只有一条记录")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name = "派单时间", type = "String", isNecessary = "Y", desc = "派单时间")
	private String create_time;
	@ZteSoftCommentAnnotationParam(name = "系统来源", type = "String", isNecessary = "Y", desc = "系统来源")
	private String source_from;

	public String getPool_id() {
		return pool_id;
	}

	public void setPool_id(String pool_id) {
		this.pool_id = pool_id;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

}
