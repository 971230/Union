package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class DeliveryFlow implements Serializable {

	@ZteSoftCommentAnnotationParam(name="详细信息ID",type="String",isNecessary="Y",desc="详细信息ID")
	private String flow_id;
	@ZteSoftCommentAnnotationParam(name="物流ID",type="String",isNecessary="Y",desc="物流ID")
	private String delivery_id;
	@ZteSoftCommentAnnotationParam(name="物流公司名称",type="String",isNecessary="Y",desc="物流公司名称")
	private String logi_name;
	@ZteSoftCommentAnnotationParam(name="创建时间",type="String",isNecessary="Y",desc="创建时间")
	private String create_time;
	private String op_id;
	private String op_name;
	@ZteSoftCommentAnnotationParam(name="描述",type="String",isNecessary="Y",desc="描述")
	private String description;
	public String getFlow_id() {
		return flow_id;
	}
	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}
	public String getDelivery_id() {
		return delivery_id;
	}
	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}
	public String getLogi_name() {
		return logi_name;
	}
	public void setLogi_name(String logi_name) {
		this.logi_name = logi_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getOp_id() {
		return op_id;
	}
	public void setOp_id(String op_id) {
		this.op_id = op_id;
	}
	public String getOp_name() {
		return op_name;
	}
	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
