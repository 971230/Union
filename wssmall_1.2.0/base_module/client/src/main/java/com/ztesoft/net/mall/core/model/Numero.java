package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.annotation.Table;

@Table(name="ES_NO")
public class Numero {

	/**
	 * ID
	 */
	private String no_id;

	/**
	 * 批次号
	 */
	private String batch_id;

	/**
	 * 手机号码
	 */
	private String dn_no;

	/**
	 * 网别
	 */
	private String no_gen;

	/**
	 * 号段
	 */
	private String seg_id;

	/**
	 * 地市编码
	 */
	private String region_id;

	/**
	 * 号码状态
	 */
	private String status;

	/**
	 * 号码分组
	 */
	private String group_id;

	/**
	 * 是否靓号
	 */
	private String is_lucky;

	/**
	 * 靓号分类
	 */
	private String no_classify;

	/**
	 * 靓号规则
	 */
	private String rule_id;

	/**
	 * 付费方式
	 */
	private String charge_type;

	/**
	 * 预存款
	 */
	private String deposit;

	/**
	 * 合约期
	 */
	private String period;

	/**
	 * 最低消费
	 */
	private String lowest;
	
	private String fee_adjust;

	/**
	 * 创建工号
	 */
	private String oper_id;

	/**
	 * 创建时间
	 */
	private String created_date;

	/**
	 * 状态时间
	 */
	private String status_date;
	

	/**
	 * 状态时间
	 */
	private String order_id;

	/**
	 * 发布状态
	 */
	private String distribute_status;

	/**
	 * 数据来源
	 */
	private String source_from;
	
	//以下不是数据库字段
	private String group_name;
	private String region_name;
	private String grupos;
	private String org_id_str;

	public String getNo_id() {
		return no_id;
	}

	public void setNo_id(String no_id) {
		this.no_id = no_id;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public String getDn_no() {
		return dn_no;
	}

	public void setDn_no(String dn_no) {
		this.dn_no = dn_no;
	}

	public String getNo_gen() {
		return no_gen;
	}

	public void setNo_gen(String no_gen) {
		this.no_gen = no_gen;
	}

	public String getSeg_id() {
		return seg_id;
	}

	public void setSeg_id(String seg_id) {
		this.seg_id = seg_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getIs_lucky() {
		return is_lucky;
	}

	public void setIs_lucky(String is_lucky) {
		this.is_lucky = is_lucky;
	}

	public String getNo_classify() {
		return no_classify;
	}

	public void setNo_classify(String no_classify) {
		this.no_classify = no_classify;
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	public String getCharge_type() {
		return charge_type;
	}

	public void setCharge_type(String charge_type) {
		this.charge_type = charge_type;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getLowest() {
		return lowest;
	}

	public void setLowest(String lowest) {
		this.lowest = lowest;
	}

	public String getFee_adjust() {
		return fee_adjust;
	}

	public void setFee_adjust(String fee_adjust) {
		this.fee_adjust = fee_adjust;
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

	public String getStatus_date() {
		return status_date;
	}

	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getDistribute_status() {
		return distribute_status;
	}

	public void setDistribute_status(String distribute_status) {
		this.distribute_status = distribute_status;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	
	@NotDbField
	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	@NotDbField
	public String getRegion_name() {
		return region_name;
	}

	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	
	@NotDbField
	public String getGrupos() {
		return grupos;
	}

	public void setGrupos(String grupos) {
		this.grupos = grupos;
	}
	
	@NotDbField
	public String getOrg_id_str() {
		return org_id_str;
	}

	public void setOrg_id_str(String org_id_str) {
		this.org_id_str = org_id_str;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
