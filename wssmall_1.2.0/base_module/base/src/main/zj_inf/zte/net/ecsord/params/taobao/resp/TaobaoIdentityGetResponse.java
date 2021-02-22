package zte.net.ecsord.params.taobao.resp;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class TaobaoIdentityGetResponse extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="bizOrderId：订单ID")
	private Long bizOrderId;
	@ZteSoftCommentAnnotationParam(name="姓名",type="String",isNecessary="Y",desc="name：姓名")
	private String name;
	@ZteSoftCommentAnnotationParam(name="身份证号码",type="String",isNecessary="Y",desc="cardNum：身份证号码")
	private String cardNum;
	@ZteSoftCommentAnnotationParam(name="身份证正面图片",type="String",isNecessary="Y",desc="frontImageUrl：身份证正面图片")
	private String frontImageUrl;
	@ZteSoftCommentAnnotationParam(name="身份证背面图片",type="String",isNecessary="Y",desc="backImageUrl：身份证背面图片")
	private String backImageUrl;
	@ZteSoftCommentAnnotationParam(name="手持证件照",type="String",isNecessary="Y",desc="holdImageUrl：手持证件照")
	private String holdImageUrl;
	public Long getBizOrderId() {
		return bizOrderId;
	}
	public void setBizOrderId(Long bizOrderId) {
		this.bizOrderId = bizOrderId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCardNum() {
		if(StringUtils.isNotBlank(cardNum)) {
			cardNum = cardNum.toUpperCase();
		}
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getFrontImageUrl() {
		return frontImageUrl;
	}
	public void setFrontImageUrl(String frontImageUrl) {
		this.frontImageUrl = frontImageUrl;
	}
	public String getBackImageUrl() {
		return backImageUrl;
	}
	public void setBackImageUrl(String backImageUrl) {
		this.backImageUrl = backImageUrl;
	}
	public String getHoldImageUrl() {
		return holdImageUrl;
	}
	public void setHoldImageUrl(String holdImageUrl) {
		this.holdImageUrl = holdImageUrl;
	}
	
}
