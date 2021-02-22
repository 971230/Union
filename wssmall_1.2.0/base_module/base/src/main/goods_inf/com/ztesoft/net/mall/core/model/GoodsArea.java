package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

/**
 * @author lqy
 *商品所属本地网实体
 */
public class GoodsArea implements Serializable {
	private String goods_id;
	private String lan_id;
	private String create_date;
	private String state;//0待审核,1上架,2审核不通过,3下架
	private String apply_id;//申请批次（商品发布，发起申请批次号）
	private String comments;//描述信息
	private String status_date;//处理时间
	private String audit_userid;//审核人
	private String apply_userid;//申请人
	private String apply_username;//申请人名
	
	
	
	
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getLan_id() {
		return lan_id;
	}
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getApply_id() {
		return apply_id;
	}
	public void setApply_id(String apply_id) {
		this.apply_id = apply_id;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getAudit_userid() {
		return audit_userid;
	}
	public void setAudit_userid(String audit_userid) {
		this.audit_userid = audit_userid;
	}
	public String getApply_userid() {
		return apply_userid;
	}
	public void setApply_userid(String apply_userid) {
		this.apply_userid = apply_userid;
	}
	public String getApply_username() {
		return apply_username;
	}
	public void setApply_username(String apply_username) {
		this.apply_username = apply_username;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((goods_id == null) ? 0 : goods_id.hashCode());
		result = prime * result + ((lan_id == null) ? 0 : lan_id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final GoodsArea other = (GoodsArea) obj;
		if (goods_id == null) {
			if (other.goods_id != null)
				return false;
		} else if (!goods_id.equals(other.goods_id))
			return false;
		if (lan_id == null) {
			if (other.lan_id != null)
				return false;
		} else if (!lan_id.equals(other.lan_id))
			return false;
		return true;
	}
	public GoodsArea() {
		super();
	}
	
	
}
