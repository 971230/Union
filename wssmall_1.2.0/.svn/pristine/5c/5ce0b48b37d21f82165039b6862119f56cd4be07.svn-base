package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 优惠券表
 * @author lzf<br/>
 * 2010-3-23 上午10:41:55<br/>
 * version 1.0<br/>
 */
public class Coupons implements Serializable {
	@ZteSoftCommentAnnotationParam(name="优惠券ID",type="String",isNecessary="Y",desc="优惠券ID")
	private String cpns_id;
	@ZteSoftCommentAnnotationParam(name="优惠券名称",type="String",isNecessary="Y",desc="优惠券名称")
	private String cpns_name;
	private String pmt_id;
	@ZteSoftCommentAnnotationParam(name="优惠券序号",type="String",isNecessary="Y",desc="优惠券序号")
	private String cpns_prefix;
	@ZteSoftCommentAnnotationParam(name="数量",type="String",isNecessary="Y",desc="数量")
	private int cpns_gen_quantity;
	private String cpns_key;
	private String cpns_status;
	private int cpns_type;
	private int cpns_point;
	private String disabled;//enum('true','false') default 'false'
	private String userid;
	
	private String memc_code;
	@NotDbField
	public String getMemc_code() {
		return memc_code;
	}
	public void setMemc_code(String memc_code) {
		this.memc_code = memc_code;
	}
	public String getCpns_id() {
		return cpns_id;
	}
	public void setCpns_id(String cpnsId) {
		cpns_id = cpnsId;
	}
	public String getCpns_name() {
		return cpns_name;
	}
	public void setCpns_name(String cpnsName) {
		cpns_name = cpnsName;
	}
	public String getPmt_id() {
		return pmt_id;
	}
	public void setPmt_id(String pmtId) {
		pmt_id = pmtId;
	}
	public String getCpns_prefix() {
		return cpns_prefix;
	}
	public void setCpns_prefix(String cpnsPrefix) {
		cpns_prefix = cpnsPrefix;
	}
	public int getCpns_gen_quantity() {
		return cpns_gen_quantity;
	}
	public void setCpns_gen_quantity(int cpnsGenQuantity) {
		cpns_gen_quantity = cpnsGenQuantity;
	}
	public String getCpns_key() {
		return cpns_key;
	}
	public void setCpns_key(String cpnsKey) {
		cpns_key = cpnsKey;
	}
	public int getCpns_type() {
		return cpns_type;
	}
	public void setCpns_type(int cpnsType) {
		cpns_type = cpnsType;
	}
	public int getCpns_point() {
		return cpns_point;
	}
	public void setCpns_point(int cpnsPoint) {
		cpns_point = cpnsPoint;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCpns_status() {
		return cpns_status;
	}
	public void setCpns_status(String cpns_status) {
		this.cpns_status = cpns_status;
	}
	
	
}
