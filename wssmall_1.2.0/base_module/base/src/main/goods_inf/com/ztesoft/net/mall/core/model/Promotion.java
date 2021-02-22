package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 优惠规则?
 * 
 * @author lzf<br/>
 *         2010-3-23 上午11:09:37<br/>
 *         version 1.0<br/>
 */
public class Promotion implements java.io.Serializable {
	@ZteSoftCommentAnnotationParam(name="规则ID",type="String",isNecessary="N",desc="规则ID")
	private String pmt_id;
	private String pmts_id;
	private String pmta_id;
	@ZteSoftCommentAnnotationParam(name="开始时间",type="String",isNecessary="N",desc="开始时间")
	private String pmt_time_begin;
	@ZteSoftCommentAnnotationParam(name="结束时间",type="String",isNecessary="N",desc="结束时间")
	private String pmt_time_end;
	@ZteSoftCommentAnnotationParam(name="订单金额最小值",type="String",isNecessary="N",desc="订单金额最小值")
	private Double order_money_from;
	@ZteSoftCommentAnnotationParam(name="订单金额最大值",type="String",isNecessary="N",desc="订单金额最大值")
	private Double order_money_to;
	private int seq;
	private String pmt_type;// enum('0','1','2') not null default '0'
	private int pmt_belong;// enum('0','1') not null default '0'
	private int pmt_bond_type;// enum('0','1','2') not null
	@ZteSoftCommentAnnotationParam(name="优惠详细信息",type="String",isNecessary="N",desc="优惠详细信息")
	private String pmt_describe;
	private String pmt_solution;
	private int pmt_ifcoupon;
	private Long pmt_update_time;
	private String pmt_basic_type;// enum('goods','order') default 'goods'
	private String disabled;// enum('true','false') default 'false'
	private String pmt_ifsale;// enum('true','false') not null default 'true'
	private String promotion_type ;
	private int pmt_distype;

	private String if_limit;
	private String limit_num;
	
	public String getPmt_id() {
		return pmt_id;
	}

	public void setPmt_id(String pmtId) {
		pmt_id = pmtId;
	}

	public String getPmts_id() {
		return pmts_id;
	}

	public void setPmts_id(String pmtsId) {
		pmts_id = pmtsId;
	}

	public String getPmta_id() {
		return pmta_id;
	}

	public void setPmta_id(String pmtaId) {
		pmta_id = pmtaId;
	}

	public String getPmt_time_begin() {
		return pmt_time_begin;
	}

	public void setPmt_time_begin(String pmtTimeBegin) {
		pmt_time_begin = pmtTimeBegin;
	}

	public String getPmt_time_end() {
		return pmt_time_end;
	}

	public void setPmt_time_end(String pmtTimeEnd) {
		pmt_time_end = pmtTimeEnd;
	}

	public Double getOrder_money_from() {
		return order_money_from;
	}

	public void setOrder_money_from(Double orderMoneyFrom) {
		order_money_from = orderMoneyFrom;
	}

	public Double getOrder_money_to() {
		return order_money_to;
	}

	public void setOrder_money_to(Double orderMoneyTo) {
		order_money_to = orderMoneyTo;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}



	public String getPmt_type() {
		return pmt_type;
	}

	public void setPmt_type(String pmt_type) {
		this.pmt_type = pmt_type;
	}

	public int getPmt_belong() {
		return pmt_belong;
	}

	public void setPmt_belong(int pmtBelong) {
		pmt_belong = pmtBelong;
	}

	public int getPmt_bond_type() {
		return pmt_bond_type;
	}

	public void setPmt_bond_type(int pmtBondType) {
		pmt_bond_type = pmtBondType;
	}

	public String getPmt_describe() {
		return pmt_describe;
	}

	public void setPmt_describe(String pmtDescribe) {
		pmt_describe = pmtDescribe;
	}

	public String getPmt_solution() {
		return pmt_solution;
	}

	public void setPmt_solution(String pmtSolution) {
		pmt_solution = pmtSolution;
	}

	public int getPmt_ifcoupon() {
		return pmt_ifcoupon;
	}

	public void setPmt_ifcoupon(int pmtIfcoupon) {
		pmt_ifcoupon = pmtIfcoupon;
	}

	public Long getPmt_update_time() {
		return pmt_update_time;
	}

	public void setPmt_update_time(Long pmtUpdateTime) {
		pmt_update_time = pmtUpdateTime;
	}

	public String getPmt_basic_type() {
		return pmt_basic_type;
	}

	public void setPmt_basic_type(String pmtBasicType) {
		pmt_basic_type = pmtBasicType;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getPmt_ifsale() {
		return pmt_ifsale;
	}

	public void setPmt_ifsale(String pmtIfsale) {
		pmt_ifsale = pmtIfsale;
	}

	public int getPmt_distype() {
		return pmt_distype;
	}

	public void setPmt_distype(int pmtDistype) {
		pmt_distype = pmtDistype;
	}

	public String getPromotion_type() {
		return promotion_type;
	}

	public void setPromotion_type(String promotion_type) {
		this.promotion_type = promotion_type;
	}

	/**
	 * @return the if_limit
	 */
	public String getIf_limit() {
		return if_limit;
	}

	/**
	 * @param if_limit the if_limit to set
	 */
	public void setIf_limit(String if_limit) {
		this.if_limit = if_limit;
	}

	/**
	 * @return the limit_num
	 */
	public String getLimit_num() {
		return limit_num;
	}

	/**
	 * @param limit_num the limit_num to set
	 */
	public void setLimit_num(String limit_num) {
		this.limit_num = limit_num;
	}

}
