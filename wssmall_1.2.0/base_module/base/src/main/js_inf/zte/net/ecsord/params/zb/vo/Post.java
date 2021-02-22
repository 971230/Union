package zte.net.ecsord.params.zb.vo;

import java.io.Serializable;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Post implements Serializable {
	@ZteSoftCommentAnnotationParam(name = "收件人姓名", type = "String", isNecessary = "Y", desc = "ConsiName：收件人姓名")
	private String ConsiName;

	@ZteSoftCommentAnnotationParam(name = "收件人证件类型", type = "String", isNecessary = "N", desc = "ConsiCertType：收件人证件类型")
	private String ConsiCertType;

	@ZteSoftCommentAnnotationParam(name = "收件人证件号码", type = "String", isNecessary = "N", desc = "ConsiCertNum：收件人证件号码")
	private String ConsiCertNum;

	@ZteSoftCommentAnnotationParam(name = "收件人联系电话1", type = "String", isNecessary = "Y", desc = "ConsiPhone：收件人联系电话1")
	private String ConsiPhone;

	@ZteSoftCommentAnnotationParam(name = "收件人联系电话2支持填写固话", type = "String", isNecessary = "N", desc = "ConsiTelPhone:收件人联系电话2支持填写固话")
	private String ConsiTelPhone;

	@ZteSoftCommentAnnotationParam(name = "收件人EMAIL", type = "String", isNecessary = "N", desc = "ConsiEmail:收件人EMAIL")
	private String ConsiEmail;

	@ZteSoftCommentAnnotationParam(name = "收货区/县", type = "String", isNecessary = "N", desc = "ConsiGoodsDist:收货区/县")
	private String ConsiGoodsDist;

	@ZteSoftCommentAnnotationParam(name = "买家留言", type = "String", isNecessary = "N", desc = "BuyerMessage:买家留言")
	private String BuyerMessage;

	@ZteSoftCommentAnnotationParam(name = "收货省", type = "String", isNecessary = "Y", desc = "ConsiGoodsProv:收货省")
	private String ConsiGoodsProv;

	@ZteSoftCommentAnnotationParam(name = "收货市", type = "String", isNecessary = "Y", desc = "ConsiGoodsCity:收货市")
	private String ConsiGoodsCity;

	@ZteSoftCommentAnnotationParam(name = "邮政编码", type = "String", isNecessary = "N", desc = "ConsiPostCode:邮政编码")
	private String ConsiPostCode;

	@ZteSoftCommentAnnotationParam(name = "邮寄地址", type = "String", isNecessary = "Y", desc = "ConsiPostAddress:邮寄地址")
	private String ConsiPostAddress;

	@ZteSoftCommentAnnotationParam(name = "邮寄备注", type = "String", isNecessary = "N", desc = "ConsiPostRemark:邮寄备注")
	private String ConsiPostRemark;

	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderIdOrderId：订单编号")
	private String notNeedReqStrOrderId;

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getConsiName() {
		return ConsiName;
	}

	public void setConsiName(String consiName) {
		ConsiName = consiName;
	}

	public String getConsiCertType() {
		return ConsiCertType;
	}

	public void setConsiCertType(String consiCertType) {
		ConsiCertType = consiCertType;
	}

	public String getConsiCertNum() {
		return ConsiCertNum;
	}

	public void setConsiCertNum(String consiCertNum) {
		ConsiCertNum = consiCertNum;
	}

	public String getConsiPhone() {
		return ConsiPhone;
	}

	public void setConsiPhone(String consiPhone) {
		ConsiPhone = consiPhone;
	}

	public String getConsiTelPhone() {
		return ConsiTelPhone;
	}

	public void setConsiTelPhone(String consiTelPhone) {
		ConsiTelPhone = consiTelPhone;
	}

	public String getConsiEmail() {
		return ConsiEmail;
	}

	public void setConsiEmail(String consiEmail) {
		ConsiEmail = consiEmail;
	}

	public String getConsiGoodsDist() {
		return ConsiGoodsDist;
	}

	public void setConsiGoodsDist(String consiGoodsDist) {
		ConsiGoodsDist = consiGoodsDist;
	}

	public String getBuyerMessage() {
		return BuyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		BuyerMessage = buyerMessage;
	}

	public String getConsiGoodsProv() {
		return ConsiGoodsProv;
	}

	public void setConsiGoodsProv(String consiGoodsProv) {
		ConsiGoodsProv = consiGoodsProv;
	}

	public String getConsiGoodsCity() {
		return ConsiGoodsCity;
	}

	public void setConsiGoodsCity(String consiGoodsCity) {
		ConsiGoodsCity = consiGoodsCity;
	}

	public String getConsiPostCode() {
		return ConsiPostCode;
	}

	public void setConsiPostCode(String consiPostCode) {
		ConsiPostCode = consiPostCode;
	}

	public String getConsiPostAddress() {
		return ConsiPostAddress;
	}

	public void setConsiPostAddress(String consiPostAddress) {
		ConsiPostAddress = consiPostAddress;
	}

	public String getConsiPostRemark() {
		return ConsiPostRemark;
	}

	public void setConsiPostRemark(String consiPostRemark) {
		ConsiPostRemark = consiPostRemark;
	}

}
