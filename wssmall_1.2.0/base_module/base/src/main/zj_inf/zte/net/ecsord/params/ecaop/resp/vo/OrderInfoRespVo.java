package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderInfoRespVo  implements Serializable{
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "ordersId:订单编号")
	private String ordersId;	
	@ZteSoftCommentAnnotationParam(name = "订单状态", type = "String", isNecessary = "Y", desc = "orderCode:订单状态")
	private String orderCode;	
	@ZteSoftCommentAnnotationParam(name = "号码", type = "String", isNecessary = "Y", desc = "serialNumber:号码")
	private String serialNumber;	
	@ZteSoftCommentAnnotationParam(name = "卡号", type = "String", isNecessary = "Y", desc = "simId:卡号")
	private String simId;
	@ZteSoftCommentAnnotationParam(name = "客户信息", type = "String", isNecessary = "Y", desc = "customerInfo:客户信息")
	private List<CustomerInfoRespVO> customerInfo;
	@ZteSoftCommentAnnotationParam(name = "用户信息", type = "String", isNecessary = "Y", desc = "userInfo:用户信息")
	private List<UserInfoRespVO> userInfo;
	@ZteSoftCommentAnnotationParam(name = "收费信息", type = "String", isNecessary = "Y", desc = "feeInfo:收费信息")
	private List<FeeInfoRspVo> feeInfo;
	@ZteSoftCommentAnnotationParam(name = "总费用", type = "String", isNecessary = "Y", desc = "totalFee:总费用正整数单位：分")
	private String totalFee;
	
	public String getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getSimId() {
		return simId;
	}
	public void setSimId(String simId) {
		this.simId = simId;
	}
	public List<CustomerInfoRespVO> getCustomerInfo() {
		return customerInfo;
	}
	public void setCustomerInfo(List<CustomerInfoRespVO> customerInfo) {
		this.customerInfo = customerInfo;
	}
	public List<UserInfoRespVO> getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(List<UserInfoRespVO> userInfo) {
		this.userInfo = userInfo;
	}
	public List<FeeInfoRspVo> getFeeInfo() {
		return feeInfo;
	}
	public void setFeeInfo(List<FeeInfoRspVo> feeInfo) {
		this.feeInfo = feeInfo;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
}
