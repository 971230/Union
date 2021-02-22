package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name="accessPwd",type="String",isNecessary="Y",desc="accessPwd：参数key,固定是：F77AF8CE01CE4A908C2ADB9B23FC3ABF")
	private String accessPwd;
	
	@ZteSoftCommentAnnotationParam(name="sequence",type="String",isNecessary="Y",desc="sequence：第三方订单系统自动生成的,唯一的流水号")
	private String sequence;

	@ZteSoftCommentAnnotationParam(name="systemCode",type="String",isNecessary="Y",desc="systemCode：业务系统编码。CBSS，BSS,ESS")
	private String systemCode;

	@ZteSoftCommentAnnotationParam(name="businessCode",type="String",isNecessary="Y",desc="businessCode：业务类型编码")
	private String businessCode;

	@ZteSoftCommentAnnotationParam(name="psPtId",type="String",isNecessary="N",desc="psPtId：客户身份证，SP产品需要首页验证")
	private String psPtId;

	@ZteSoftCommentAnnotationParam(name="serialNumber",type="String",isNecessary="Y",desc="serialNumber：开户号码")
	private String serialNumber;

	@ZteSoftCommentAnnotationParam(name="productInfo",type="String",isNecessary="Y",desc="productInfo：套餐信息")
	private List<ProductInfo> productInfo;

	@ZteSoftCommentAnnotationParam(name="activityInfo",type="String",isNecessary="N",desc="activityInfo：活动信息")
	private List<ActivityInfo> activityInfo;

	@ZteSoftCommentAnnotationParam(name="attachedInfo",type="String",isNecessary="N",desc="attachedInfo：附加产品信息")
	private List<AttachedInfo> attachedInfo;

	@ZteSoftCommentAnnotationParam(name="spInfo",type="String",isNecessary="N",desc="spInfo：SP产品节点")
	private List<SpInfo> spInfo;

	@ZteSoftCommentAnnotationParam(name="firstMonBillMode",type="String",isNecessary="Y",desc="firstMonBillMode：01:标准资费(免首月月租);02：全月套餐;03：套餐减半")
	private String firstMonBillMode;
	
	@ZteSoftCommentAnnotationParam(name="deviceType",type="String",isNecessary="Y",desc="deviceType：终端类型编码")
	private String deviceType;
	
	@ZteSoftCommentAnnotationParam(name="deviceAttach",type="String",isNecessary="Y",desc="deviceAttach：终端归属编码")
	private String deviceAttach;

	@ZteSoftCommentAnnotationParam(name="deviceImei",type="String",isNecessary="Y",desc="deviceImei：终端串号")
	private String deviceImei;

	@ZteSoftCommentAnnotationParam(name="payType",type="String",isNecessary="Y",desc="payType：默认订单系统编码:10 现金支付")
	private String payType;

	@ZteSoftCommentAnnotationParam(name="payPrice",type="String",isNecessary="Y",desc="payPrice：支付金额")
	private String payPrice;

	public String getAccessPwd() {
		return accessPwd;
	}

	public void setAccessPwd(String accessPwd) {
		this.accessPwd = accessPwd;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getPsPtId() {
		return psPtId==null?"":psPtId;
	}

	public void setPsPtId(String psPtId) {
		this.psPtId = psPtId;
	}

	public String getSerialNumber() {
		return serialNumber==null?"":serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public List<ProductInfo> getProductInfo() {
		return productInfo==null?(new ArrayList<ProductInfo>()):productInfo;
	}

	public void setProductInfo(List<ProductInfo> productInfo) {
		this.productInfo = productInfo;
	}

	public List<ActivityInfo> getActivityInfo() {
		return activityInfo==null?(new ArrayList<ActivityInfo>()):activityInfo;
	}

	public void setActivityInfo(List<ActivityInfo> activityInfo) {
		this.activityInfo = activityInfo;
	}

	public List<AttachedInfo> getAttachedInfo() {
		return attachedInfo==null?(new ArrayList<AttachedInfo>()):attachedInfo;
	}

	public void setAttachedInfo(List<AttachedInfo> attachedInfo) {
		this.attachedInfo = attachedInfo;
	}

	public List<SpInfo> getSpInfo() {
		return spInfo==null?(new ArrayList<SpInfo>()):spInfo;
	}

	public void setSpInfo(List<SpInfo> spInfo) {
		this.spInfo = spInfo;
	}

	public String getFirstMonBillMode() {
		return firstMonBillMode==null?"":firstMonBillMode;
	}

	public void setFirstMonBillMode(String firstMonBillMode) {
		this.firstMonBillMode = firstMonBillMode;
	}

	public String getDeviceType() {
		return deviceType==null?"":deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getDeviceAttach() {
		return deviceAttach==null?"":deviceAttach;
	}

	public void setDeviceAttach(String deviceAttach) {
		this.deviceAttach = deviceAttach;
	}

	public String getDeviceImei() {
		return deviceImei==null?"":deviceImei;
	}

	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}

	public String getPayType() {
		return payType==null?"":payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPayPrice() {
		return payPrice==null?"":payPrice;
	}

	public void setPayPrice(String payPrice) {
		this.payPrice = payPrice;
	}
}
