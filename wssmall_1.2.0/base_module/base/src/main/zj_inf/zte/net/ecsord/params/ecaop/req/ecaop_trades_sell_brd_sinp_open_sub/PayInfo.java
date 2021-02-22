package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub.PaymentInfoSyncReq;

public class PayInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "支付金额，单位：厘", type = "String", isNecessary = "N", desc = "支付金额，单位：厘")
	private String payFee;

	@ZteSoftCommentAnnotationParam(name = "支付方式", type = "String", isNecessary = "Y", desc = "支付方式")
	private String payType;

	@ZteSoftCommentAnnotationParam(name = "支付机构名称", type = "String", isNecessary = "N", desc = "支付机构名称")
	private String payOrg;

	@ZteSoftCommentAnnotationParam(name = "支付账号", type = "String", isNecessary = "N", desc = "支付账号")
	private String payNum;

	@ZteSoftCommentAnnotationParam(name = "MISPOS支付信息（支付方式为MISPOS时该节点必传）", type = "PaymentInfoSyncReq", isNecessary = "N", desc = "MISPOS支付信息（支付方式为MISPOS时该节点必传）")
	private List<PaymentInfoSyncReq> paymentInfoSyncReq;

	@ZteSoftCommentAnnotationParam(name = "0：在线支付", type = "String", isNecessary = "Y", desc = "0：在线支付")
	private String payMode;

	public String getPayFee() {
		return this.payFee;
	}

	public void setPayFee(String v) {
		this.payFee = v;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String v) {
		this.payType = v;
	}

	public String getPayOrg() {
		return this.payOrg;
	}

	public void setPayOrg(String v) {
		this.payOrg = v;
	}

	public String getPayNum() {
		return this.payNum;
	}

	public void setPayNum(String v) {
		this.payNum = v;
	}

	public List<PaymentInfoSyncReq> getPaymentInfoSyncReq() {
		return this.paymentInfoSyncReq;
	}

	public void setPaymentInfoSyncReq(List<PaymentInfoSyncReq> v) {
		this.paymentInfoSyncReq = v;
	}

	public String getPayMode() {
		return this.payMode;
	}

	public void setPayMode(String v) {
		this.payMode = v;
	}

}
