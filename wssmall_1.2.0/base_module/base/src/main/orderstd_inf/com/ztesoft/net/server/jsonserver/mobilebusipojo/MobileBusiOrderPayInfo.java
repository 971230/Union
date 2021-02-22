package com.ztesoft.net.server.jsonserver.mobilebusipojo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * pay_info节点（收单支付节点信息）
 * 
 * @author song.qi 2017年12月26日
 *
 */
public class MobileBusiOrderPayInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "pay_status", type = "String", isNecessary = "N", desc = "是否已支付")
	private String pay_status;// 0 –已支付；-1 – 未支付

	@ZteSoftCommentAnnotationParam(name = "pay_sequ", type = "String", isNecessary = "N", desc = "支付发起流水")
	private String pay_sequ;// 已支付必填

	@ZteSoftCommentAnnotationParam(name = "pay_back_sequ", type = "String", isNecessary = "N", desc = "支付返回流水")
	private String pay_back_sequ;// 已支付必填

	@ZteSoftCommentAnnotationParam(name = "pay_type", type = "String", isNecessary = "N", desc = "支付类型")
	private String pay_type;// 见支付类型

	@ZteSoftCommentAnnotationParam(name = "pay_method", type = "String", isNecessary = "N", desc = "支付方式")
	private String pay_method;// 详见支付方式

	@ZteSoftCommentAnnotationParam(name = "remark", type = "String", isNecessary = "N", desc = "备注")
	private String remark;//

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getPay_sequ() {
		return pay_sequ;
	}

	public void setPay_sequ(String pay_sequ) {
		this.pay_sequ = pay_sequ;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	public String getPay_back_sequ() {
		return pay_back_sequ;
	}

	public void setPay_back_sequ(String pay_back_sequ) {
		this.pay_back_sequ = pay_back_sequ;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
