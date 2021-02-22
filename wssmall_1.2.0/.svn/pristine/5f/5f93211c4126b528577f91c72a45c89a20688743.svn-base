package zte.net.ecsord.params.nd.vo;

import java.io.Serializable;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class PostInfo implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1811127270348792703L;

	@ZteSoftCommentAnnotationParam(name="物流公司编码",type="String",isNecessary="Y",desc="postId：物流公司编码")
	private String postId;

	@ZteSoftCommentAnnotationParam(name="配送人",type="String",isNecessary="Y",desc="carryPerson：配送人")
	private String carryPerson;	

	@ZteSoftCommentAnnotationParam(name="配送时间",type="String",isNecessary="Y",desc="carryTime：配送时间")
	private String carryTime;

	@ZteSoftCommentAnnotationParam(name="物流单号",type="String",isNecessary="Y",desc="postNo：物流单号")
	private String postNo;

	@ZteSoftCommentAnnotationParam(name="保费",type="String",isNecessary="Y",desc="protectFee：存的是百分比如：0.005")
	private String protectFee;

	@ZteSoftCommentAnnotationParam(name="运费",type="String",isNecessary="Y",desc="carryFee：以分为单位")
	private String carryFee;

	@ZteSoftCommentAnnotationParam(name="取物流公司电话或取件人电话",type="String",isNecessary="Y",desc="carryPersonTel：取物流公司电话或取件人电话")
	private String carryPersonTel;

	@ZteSoftCommentAnnotationParam(name="收货时间",type="String",isNecessary="Y",desc="receiptTime：格式：YYYY-MM-DD HH:mm:dd")
	private String receiptTime;
	
	

	public String getPostId() {
		//String post_id = CommonDataFactory.getInstance().getAttrFieldValue(OrderId, AttrConsts.SHIPPING_COMPANY);
		//return CommonDataFactory.getInstance().getLogiCompanyCode(post_id);
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getCarryPerson() {
		return carryPerson;
	}

	public void setCarryPerson(String carryPerson) {
		this.carryPerson = carryPerson;
	}

	public String getCarryTime() {
		//return CommonDataFactory.getInstance().getAttrFieldValue(OrderId, AttrConsts.SHIPPING_TIME);
		return carryTime;
	}

	public void setCarryTime(String carryTime) {
		this.carryTime = carryTime;
	}

	public String getPostNo() {
		//return CommonDataFactory.getInstance().getAttrFieldValue(OrderId, AttrConsts.LOGI_NO);
		return postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getProtectFee() {
		return protectFee;
	}

	public void setProtectFee(String protectFee) {
		this.protectFee = protectFee;
	}

	public String getCarryFee() {
		return carryFee;
	}

	public void setCarryFee(String carryFee) {
		this.carryFee = carryFee;
	}

	public String getCarryPersonTel() {
		//return CommonDataFactory.getInstance().getAttrFieldValue(OrderId, AttrConsts.CARRY_PERSON_MOBILE);
		return carryPersonTel;
	}

	public void setCarryPersonTel(String carryPersonTel) {
		this.carryPersonTel = carryPersonTel;
	}

	public String getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(String receiptTime) {
		this.receiptTime = receiptTime;
	}
	
	
}
