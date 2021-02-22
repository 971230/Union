package zte.net.ecsord.params.nd.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Order implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3125916351250009651L;

	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="origOrderId：外部订单号")
	private String origOrderId;

	@ZteSoftCommentAnnotationParam(name="内部订单号",type="String",isNecessary="Y",desc="orderId：内部订单号")
	private String orderId;	

	@ZteSoftCommentAnnotationParam(name="订单来源",type="String",isNecessary="Y",desc="TBFX:淘宝分销、 ZBWO:总部商城、 WO:沃商城、" +
			" PP:拍拍商城、 WBDR:外部导入、 JK:网盟集团客户、 TB:淘宝商城、 WAP:沃商城WAP、" +
			" VIP:VIP商城、 UHZ:U惠站、 WMTY:网盟统一接口、 WMQT:网盟其他来源")
	private String orderOrigin;	
	
	@ZteSoftCommentAnnotationParam(name="是否需要揽件",type="String",isNecessary="Y",desc="takeCode：是否需要揽件  01是（商品包为合约机订单）02否（商品包为号卡订单）")
	private String takeCode;	

	@ZteSoftCommentAnnotationParam(name="归属地市",type="String",isNecessary="Y",desc="orderCity：归属地市")
	private String orderCity;	

	@ZteSoftCommentAnnotationParam(name="用户下单时间",type="String",isNecessary="Y",desc="orderCreateTime：格式YYYY-MM-DD HH:mm:ss")
	private String orderCreateTime;

	@ZteSoftCommentAnnotationParam(name="订单生成时间",type="String",isNecessary="Y",desc="createTime：格式YYYY-MM-DD HH:mm:ss")
	private String createTime;

	@ZteSoftCommentAnnotationParam(name="闪电送开始时间",type="String",isNecessary="Y",desc="lightTime：格式YYYY-MM-DD HH:mm:ss")
	private String lightTime;

	@ZteSoftCommentAnnotationParam(name="外部订单状态",type="String",isNecessary="Y",desc="extOrderStatus：外部订单状态")
	private String extOrderStatus;

	@ZteSoftCommentAnnotationParam(name="买家留言",type="String",isNecessary="N",desc="buyerMessage：买家留言")
	private String buyerMessage;

	@ZteSoftCommentAnnotationParam(name="卖家留言",type="String",isNecessary="N",desc="sellerMessage：卖家留言")
	private String sellerMessage;

	@ZteSoftCommentAnnotationParam(name="订单备注",type="String",isNecessary="N",desc="memo：订单备注")
	private String memo;

	@ZteSoftCommentAnnotationParam(name="支付方式",type="String",isNecessary="Y",desc="payMode：支付方式")
	private String payMode;

	@ZteSoftCommentAnnotationParam(name="支付状态",type="String",isNecessary="Y",desc="payStatus：支付状态")
	private String payStatus;

	@ZteSoftCommentAnnotationParam(name="付款时间",type="String",isNecessary="N",desc="payDatetime：格式YYYY-MM-DD HH:mm:ss")
	private String payDatetime;

	@ZteSoftCommentAnnotationParam(name="优惠金额",type="String",isNecessary="N",desc="discountFee：单位分")
	private String discountFee;

	@ZteSoftCommentAnnotationParam(name="调整金额",type="String",isNecessary="N",desc="adjustFee：单位分")
	private String adjustFee;

	@ZteSoftCommentAnnotationParam(name="订单金额",type="String",isNecessary="Y",desc="orderFee：单位分")
	private String orderFee;

	@ZteSoftCommentAnnotationParam(name="实付金额",type="String",isNecessary="Y",desc="payFee：单位分")
	private String payFee;
	
	@ZteSoftCommentAnnotationParam(name="收货人信息",type="String",isNecessary="Y",desc="carryInfo：收货人信息(取postType为0的记录)")
	private List<Carry> carryInfo;
	
	@ZteSoftCommentAnnotationParam(name="收货人信息",type="String",isNecessary="Y",desc="goodInfo：商品信息")
	private List<Good> goodInfo;
	
	@ZteSoftCommentAnnotationParam(name="收货人信息",type="String",isNecessary="Y",desc="giftInfo：赠品信息")
	private List<Gift> giftInfo;

	public String getOrigOrderId() {
		return origOrderId;
	}

	public void setOrigOrderId(String origOrderId) {
		this.origOrderId = origOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderOrigin() {
		return orderOrigin;
	}

	public void setOrderOrigin(String orderOrigin) {
		this.orderOrigin = orderOrigin;
	}

	public String getOrderCity() {
		return orderCity;
	}

	public void setOrderCity(String orderCity) {
		this.orderCity = orderCity;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLightTime() {
		return lightTime;
	}

	public void setLightTime(String lightTime) {
		this.lightTime = lightTime;
	}

	public String getExtOrderStatus() {
		return extOrderStatus;
	}

	public void setExtOrderStatus(String extOrderStatus) {
		this.extOrderStatus = extOrderStatus;
	}

	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public String getSellerMessage() {
		return sellerMessage;
	}

	public void setSellerMessage(String sellerMessage) {
		this.sellerMessage = sellerMessage;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayDatetime() {
		return payDatetime;
	}

	public void setPayDatetime(String payDatetime) {
		this.payDatetime = payDatetime;
	}

	public String getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}

	public String getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(String adjustFee) {
		this.adjustFee = adjustFee;
	}

	public String getOrderFee() {
		return orderFee;
	}

	public void setOrderFee(String orderFee) {
		this.orderFee = orderFee;
	}

	public String getPayFee() {
		return payFee;
	}

	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}

	public List<Carry> getCarryInfo() {
		return carryInfo;
	}

	public void setCarryInfo(List<Carry> carryInfo) {
		this.carryInfo = carryInfo;
	}

	public List<Good> getGoodInfo() {
		return goodInfo;
	}

	public void setGoodInfo(List<Good> goodInfo) {
		this.goodInfo = goodInfo;
	}

	public List<Gift> getGiftInfo() {
		return giftInfo;
	}

	public void setGiftInfo(List<Gift> giftInfo) {
		this.giftInfo = giftInfo;
	}
	
	
	
}
