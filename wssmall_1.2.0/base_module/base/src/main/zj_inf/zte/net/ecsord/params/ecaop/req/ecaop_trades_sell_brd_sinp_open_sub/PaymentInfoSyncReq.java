package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class PaymentInfoSyncReq implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "mispos刷卡标识值为'MPOS'", type = "String", isNecessary = "Y", desc = "mispos刷卡标识值为'MPOS'")
	private String mposValueCode;

	@ZteSoftCommentAnnotationParam(name = "业务类型：01 营业支付信息同步；02 资源支付信息同步 ", type = "String", isNecessary = "Y", desc = "业务类型：01 营业支付信息同步；02 资源支付信息同步 ")
	private String tradeType;

	@ZteSoftCommentAnnotationParam(name = "消费（交易类型  1:扫码；2:刷卡;3:分期;4:微信扫码;5:支付宝扫码;6:招联信用付）；退货（原始交易类型  1:扫码；2:刷卡;3:分期;4:微信扫码;5:支付宝扫码;6:招联信用付） ", type = "String", isNecessary = "N", desc = "消费（交易类型  1:扫码；2:刷卡;3:分期;4:微信扫码;5:支付宝扫码;6:招联信用付）；退货（原始交易类型  1:扫码；2:刷卡;3:分期;4:微信扫码;5:支付宝扫码;6:招联信用付） ")
	private String originalTransType;

	@ZteSoftCommentAnnotationParam(name = "POS终端号  消费和退货", type = "String", isNecessary = "N", desc = "POS终端号  消费和退货")
	private String transPoint;

	@ZteSoftCommentAnnotationParam(name = "退货时间（退货时填写)", type = "String", isNecessary = "N", desc = "退货时间（退货时填写)")
	private String refundTime;

	@ZteSoftCommentAnnotationParam(name = "支付流水号（消费），退货流水号（退货）", type = "String", isNecessary = "Y", desc = "支付流水号（消费），退货流水号（退货）")
	private String busiOrderId;

	@ZteSoftCommentAnnotationParam(name = "消费，退货（TRANS_CARD_NO  交易卡号） ", type = "String", isNecessary = "N", desc = "消费，退货（TRANS_CARD_NO  交易卡号） ")
	private String transCardNo;

	@ZteSoftCommentAnnotationParam(name = "用户号码", type = "String", isNecessary = "N", desc = "用户号码")
	private String serialNumber;

	@ZteSoftCommentAnnotationParam(name = "消费（BUSI_AMT 交易金额 单位：分，）；退货（REFUND_AMT 本次退款交易金额）", type = "String", isNecessary = "Y", desc = "消费（BUSI_AMT 交易金额 单位：分，）；退货（REFUND_AMT 本次退款交易金额）")
	private String tradeAmt;

	@ZteSoftCommentAnnotationParam(name = "POS商户号  消费和退货", type = "String", isNecessary = "N", desc = "POS商户号  消费和退货")
	private String transMerchant;

	@ZteSoftCommentAnnotationParam(name = "原始交易总金额 单位：分", type = "String", isNecessary = "N", desc = "原始交易总金额 单位：分")
	private String originalBusiAmt;

	@ZteSoftCommentAnnotationParam(name = "POS系统检索号  消费和退货", type = "String", isNecessary = "N", desc = "POS系统检索号  消费和退货")
	private String transRetrieval;

	@ZteSoftCommentAnnotationParam(name = "接口类型（1：消费 2：退货 3：撤销 ）", type = "String", isNecessary = "Y", desc = "接口类型（1：消费 2：退货 3：撤销 ）")
	private String busiTransType;

	@ZteSoftCommentAnnotationParam(name = "支付订单 的交易状态：（消费 调用前）2是待处理 ，（消费 调用后） 0 成功，1失败 ，（退货接口调用成功后修改）3  已退货 （撤销接口调用成功后修改）4 已撤销      （冲正接口调用后）5 已冲正", type = "String", isNecessary = "Y", desc = "支付订单 的交易状态：（消费 调用前）2是待处理 ，（消费 调用后） 0 成功，1失败 ，（退货接口调用成功后修改）3  已退货 （撤销接口调用成功后修改）4 已撤销      （冲正接口调用后）5 已冲正")
	private String transStatus;

	@ZteSoftCommentAnnotationParam(name = "交易时间 （支付公司返回）刷卡，重订购，退货", type = "String", isNecessary = "Y", desc = "交易时间 （支付公司返回）刷卡，重订购，退货")
	private String transTime;

	@ZteSoftCommentAnnotationParam(name = "POS批次号  消费和退货", type = "String", isNecessary = "N", desc = "POS批次号  消费和退货")
	private String transBatch;

	@ZteSoftCommentAnnotationParam(name = "0 成功（消费，退货，撤销 调用后前存），1失败 （消费，退货，撤销 调用后前存），2是待处理（消费，退货，撤销 调用前存）", type = "String", isNecessary = "Y", desc = "0 成功（消费，退货，撤销 调用后前存），1失败 （消费，退货，撤销 调用后前存），2是待处理（消费，退货，撤销 调用前存）")
	private String modifyTag;

	@ZteSoftCommentAnnotationParam(name = "业务侧支付订单号(原始订单号)  --退货时填", type = "String", isNecessary = "N", desc = "业务侧支付订单号(原始订单号)  --退货时填")
	private String originalBusiOrderId;

	@ZteSoftCommentAnnotationParam(name = "CBSS系统业务订单号", type = "String", isNecessary = "Y", desc = "CBSS系统业务订单号")
	private String tradeId;

	@ZteSoftCommentAnnotationParam(name = "POS流水号  消费和退货", type = "String", isNecessary = "N", desc = "POS流水号  消费和退货")
	private String transTrace;

	public String getMposValueCode() {
		return this.mposValueCode;
	}

	public void setMposValueCode(String v) {
		this.mposValueCode = v;
	}

	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String v) {
		this.tradeType = v;
	}

	public String getOriginalTransType() {
		return this.originalTransType;
	}

	public void setOriginalTransType(String v) {
		this.originalTransType = v;
	}

	public String getTransPoint() {
		return this.transPoint;
	}

	public void setTransPoint(String v) {
		this.transPoint = v;
	}

	public String getRefundTime() {
		return this.refundTime;
	}

	public void setRefundTime(String v) {
		this.refundTime = v;
	}

	public String getBusiOrderId() {
		return this.busiOrderId;
	}

	public void setBusiOrderId(String v) {
		this.busiOrderId = v;
	}

	public String getTransCardNo() {
		return this.transCardNo;
	}

	public void setTransCardNo(String v) {
		this.transCardNo = v;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String v) {
		this.serialNumber = v;
	}

	public String getTradeAmt() {
		return this.tradeAmt;
	}

	public void setTradeAmt(String v) {
		this.tradeAmt = v;
	}

	public String getTransMerchant() {
		return this.transMerchant;
	}

	public void setTransMerchant(String v) {
		this.transMerchant = v;
	}

	public String getOriginalBusiAmt() {
		return this.originalBusiAmt;
	}

	public void setOriginalBusiAmt(String v) {
		this.originalBusiAmt = v;
	}

	public String getTransRetrieval() {
		return this.transRetrieval;
	}

	public void setTransRetrieval(String v) {
		this.transRetrieval = v;
	}

	public String getBusiTransType() {
		return this.busiTransType;
	}

	public void setBusiTransType(String v) {
		this.busiTransType = v;
	}

	public String getTransStatus() {
		return this.transStatus;
	}

	public void setTransStatus(String v) {
		this.transStatus = v;
	}

	public String getTransTime() {
		return this.transTime;
	}

	public void setTransTime(String v) {
		this.transTime = v;
	}

	public String getTransBatch() {
		return this.transBatch;
	}

	public void setTransBatch(String v) {
		this.transBatch = v;
	}

	public String getModifyTag() {
		return this.modifyTag;
	}

	public void setModifyTag(String v) {
		this.modifyTag = v;
	}

	public String getOriginalBusiOrderId() {
		return this.originalBusiOrderId;
	}

	public void setOriginalBusiOrderId(String v) {
		this.originalBusiOrderId = v;
	}

	public String getTradeId() {
		return this.tradeId;
	}

	public void setTradeId(String v) {
		this.tradeId = v;
	}

	public String getTransTrace() {
		return this.transTrace;
	}

	public void setTransTrace(String v) {
		this.transTrace = v;
	}

}
