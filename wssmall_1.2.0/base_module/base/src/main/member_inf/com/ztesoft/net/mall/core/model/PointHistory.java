package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 会员积分历史记录
 * 
 * @author lzf<br/>
 *         2010-3-22 上午11:01:31<br/>
 *         version 1.0<br/>
 */
public class PointHistory  {
	private String id;
	@ZteSoftCommentAnnotationParam(name="会员ID",type="String",isNecessary="N",desc="会员ID")
	private String member_id;
	private int point;
	@ZteSoftCommentAnnotationParam(name="获取时间",type="String",isNecessary="N",desc="获取时间")
	private String time;
	private String reason;
	private String cnreason;
	private String related_id;
	private int type;
	@ZteSoftCommentAnnotationParam(name="积分类型",type="String",isNecessary="N",desc="积分类型[0等级积分1消费积分]")
	private int point_type;
	private String operator;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String memberId) {
		member_id = memberId;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
 
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@NotDbField
	public String getCnreason() {
		if(reason.equals("order_pay_use")) cnreason = "订单消费积分";
		if(reason.equals("order_pay_get")) cnreason = "订单获得积分";
		if(reason.equals("order_refund_use")) cnreason = "退还订单消费积分";
		if(reason.equals("order_refund_get")) cnreason = "扣掉订单所得积分";
		if(reason.equals("order_cancel_refund_consume_gift")) cnreason = "Score deduction for gifts refunded for order cancelling.";
		if(reason.equals("exchange_coupon")) cnreason = "兑换优惠券";
		if(reason.equals("operator_adjust")) cnreason = "管理员改变积分";
		if(reason.equals("consume_gift")) cnreason = "积分换赠品";
		if(reason.equals("recommend_member")) cnreason = "发表评论奖励积分";
		return cnreason;
	}
	public void setCnreason(String cnreason) {
		this.cnreason = cnreason;
	}
	public String getRelated_id() {
		return related_id;
	}
	public void setRelated_id(String relatedId) {
		related_id = relatedId;
	}
	public int getPoint_type() {
		return point_type;
	}
	public void setPoint_type(int point_type) {
		this.point_type = point_type;
	}
	
	
}
