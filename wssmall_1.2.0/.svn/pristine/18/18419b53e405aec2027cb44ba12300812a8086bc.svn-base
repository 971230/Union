package zte.net.ecsord.params.wms.vo;

import java.io.Serializable;
import java.util.List;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 同步订单信息到WMS系统
 * 收货人信息对象
 */
public class CarryInfoVo implements Serializable{
	
	private static final long serialVersionUID = 227406457008046667L;
	
	@ZteSoftCommentAnnotationParam(name="商品Id",type="String",isNecessary="Y",desc="商品Id")
	private String packageId;
	@ZteSoftCommentAnnotationParam(name="收货人姓名",type="String",isNecessary="Y",desc="收货人姓名")
	private String consigneeName;
	@ZteSoftCommentAnnotationParam(name="收货邮编",type="String",isNecessary="N",desc="收货邮编")
	private String receiveZip;
	@ZteSoftCommentAnnotationParam(name="收货人电话",type="String",isNecessary="N",desc="收货人电话")
	private String receiveTelNum;
	@ZteSoftCommentAnnotationParam(name="收货人手机",type="String",isNecessary="N",desc="收货人手机")
	private String receiveMobileNum;
	@ZteSoftCommentAnnotationParam(name="收货地址",type="String",isNecessary="Y",desc="收货地址")
	private String receiveAddress;
	@ZteSoftCommentAnnotationParam(name="配送方式",type="String",isNecessary="Y",desc="配送方式")
	private String carryMode;
	@ZteSoftCommentAnnotationParam(name="物流公司",type="String",isNecessary="Y",desc="物流公司")
	private String logistics;
	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "OrderId：订单编号")
	private String OrderId;
	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getPackageId() {
		return this.packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getConsigneeName() {
		return this.consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getReceiveZip() {
		return this.receiveZip;
	}

	public void setReceiveZip(String receiveZip) {
		this.receiveZip = receiveZip;
	}

	public String getReceiveTelNum() {
		return this.receiveTelNum;
	}

	public void setReceiveTelNum(String receiveTelNum) {
		this.receiveTelNum = receiveTelNum;
	}

	public String getReceiveMobileNum() {
		return this.receiveMobileNum;
	}

	public void setReceiveMobileNum(String receiveMobileNum) {
		this.receiveMobileNum = receiveMobileNum;
	}

	public String getReceiveAddress() {
		return this.receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getCarryMode() {
		return this.carryMode;
	}

	public void setCarryMode(String carryMode) {
		this.carryMode = carryMode;
	}

	public String getLogistics() {
		return this.logistics;
	}

	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}
}
