package zte.net.ecsord.params.busiopen.ordinfo.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 支付信息
 */
public class PayInfo implements Serializable{
	
	private static final long serialVersionUID = -1889585094831405271L;

	@ZteSoftCommentAnnotationParam(name = "支付方式", type = "String", isNecessary = "N", desc = "支付方式--")
	private String payType;

	@ZteSoftCommentAnnotationParam(name = "支付类型", type = "String", isNecessary = "N", desc = "支付类型--")
	private String payMode;

	@ZteSoftCommentAnnotationParam(name = "支付状态", type = "String", isNecessary = "Y", desc = "支付状态--")
	private String payStatus;

	@ZteSoftCommentAnnotationParam(name = "付款时间", type = "String", isNecessary = "N", desc = "付款时间，格式YYYY-MM-DD HH:mm:ss")
	private String payDatetime;

	@ZteSoftCommentAnnotationParam(name = "优惠金额，单位分", type = "String", isNecessary = "N", desc = "优惠金额，单位分")
	private String discountFee;

	@ZteSoftCommentAnnotationParam(name = "调整金额，单位分", type = "String", isNecessary = "N", desc = "调整金额，单位分")
	private String adjustFee;

	@ZteSoftCommentAnnotationParam(name = "订单金额，单位分", type = "String", isNecessary = "Y", desc = "订单金额，单位分")
	private String orderFee;

	@ZteSoftCommentAnnotationParam(name = "实付金额，单位分", type = "String", isNecessary = "Y", desc = "实付金额，单位分")
	private String payFee;
	
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayDatetime() {
		return payDatetime;
	}

	public void setPayDatetime(String payDatetime) {
		this.payDatetime = payDatetime;
	}

	public String getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}

	public String getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(String adjustFee) {
		this.adjustFee = adjustFee;
	}

	public String getOrderFee() {
		return orderFee;
	}

	public void setOrderFee(String orderFee) {
		this.orderFee = orderFee;
	}

	public String getPayFee() {
		return payFee;
	}

	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}

}
