package zte.net.ecsord.params.busiopen.ordinfo.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 收货信息
 */
public class CarryInfo implements Serializable {
	
	private static final long serialVersionUID = 6555609208534131201L;
	
	@ZteSoftCommentAnnotationParam(name = "收货人姓名", type = "String", isNecessary = "Y", desc = "收货人姓名")
	private String consigneeName;
	
	@ZteSoftCommentAnnotationParam(name = "收货邮编", type = "String", isNecessary = "N", desc = "收货邮编")
	private String receiveZip;
	
	@ZteSoftCommentAnnotationParam(name = "收货人电话", type = "String", isNecessary = "N", desc = "收货人电话")
	private String telNum;
	
	@ZteSoftCommentAnnotationParam(name = "收货人手机", type = "String", isNecessary = "N", desc = "收货人手机")
	private String telMobileNum;
	
	@ZteSoftCommentAnnotationParam(name = "收货地址", type = "String", isNecessary = "Y", desc = "收货地址")
	private String carryAddress;
	
	@ZteSoftCommentAnnotationParam(name = "配送方式", type = "String", isNecessary = "Y", desc = "配送方式--")
	private String carryMode;
	
	@ZteSoftCommentAnnotationParam(name = "主键", type = "String", isNecessary = "Y", desc = "主键")
	private String carryId;
	
	@ZteSoftCommentAnnotationParam(name = "配送类型", type = "String", isNecessary = "Y", desc = "配送类型,0:正常发货；1：补发;2:重发")
	private String postType;

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getReceiveZip() {
		return receiveZip;
	}

	public void setReceiveZip(String receiveZip) {
		this.receiveZip = receiveZip;
	}

	public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getTelMobileNum() {
		return telMobileNum;
	}

	public void setTelMobileNum(String telMobileNum) {
		this.telMobileNum = telMobileNum;
	}

	public String getCarryAddress() {
		return carryAddress;
	}

	public void setCarryAddress(String carryAddress) {
		this.carryAddress = carryAddress;
	}

	public String getCarryMode() {
		return carryMode;
	}

	public void setCarryMode(String carryMode) {
		this.carryMode = carryMode;
	}

	public String getCarryId() {
		return carryId;
	}

	public void setCarryId(String carryId) {
		this.carryId = carryId;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

}
