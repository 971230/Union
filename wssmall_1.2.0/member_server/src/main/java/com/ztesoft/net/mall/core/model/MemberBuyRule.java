package com.ztesoft.net.mall.core.model;

import java.io.Serializable;

import com.ztesoft.net.framework.database.NotDbField;

/**
 * 会员规则
 * @作者 MoChunrun
 * @创建日期 2013-10-15 
 * @版本 V 1.0
 */
public class MemberBuyRule implements Serializable {

	private String goods_id;
	private String lv_id;
	private int num;
	private int kind;
	private String kind_name;
	private String memberName;
	
	@NotDbField
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getLv_id() {
		return lv_id;
	}
	public void setLv_id(String lv_id) {
		this.lv_id = lv_id;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public String getKind_name() {
		return kind_name;
	}
	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}
	
}
