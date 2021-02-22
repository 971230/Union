package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.mall.core.annotation.Id;
import com.ztesoft.net.mall.core.annotation.Table;

@Table(name="ES_PRODUCT_CO")
public class Producto  implements java.io.Serializable {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 货品标识
	 */
	private String product_id;

	/**
	 * 销售组织
	 */
	private String org_id;

	/**
	 * 发布工号
	 */
	private String oper_id;

	/**
	 * 发布时间-页面点发布的时间
	 */
	private String created_date;

	/**
	 * 发布状态:0 未发布； 1 已发布，2发布中，3发布失败
	 */
	private String status;

	/**
	 * 状态时间
	 */
	private String status_date;

	/**
	 * 数据来源
	 */
	private String source_from;

	private String batch_id;
	
	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getOper_id() {
		return oper_id;
	}

	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus_date() {
		return status_date;
	}

	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	
	

}