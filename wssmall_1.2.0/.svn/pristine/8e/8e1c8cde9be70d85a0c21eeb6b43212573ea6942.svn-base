package zte.net.ecsord.params.busiopen.ordinfo.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 订单信息和支付信息
 */
public class OrderInfo implements Serializable {

	private static final long serialVersionUID = 1505240838646386223L;

	@ZteSoftCommentAnnotationParam(name = "外部订单号", type = "String", isNecessary = "Y", desc = "外部订单号")
	private String origOrderId;

	@ZteSoftCommentAnnotationParam(name = "内部订单号", type = "String", isNecessary = "Y", desc = "内部订单号")
	private String orderId;

	@ZteSoftCommentAnnotationParam(name = "订单来源", type = "String", isNecessary = "Y", desc = "订单来源--")
	private String orderOrigin;

	@ZteSoftCommentAnnotationParam(name = "归属地市", type = "String", isNecessary = "Y", desc = "归属地市，--")
	private String orderCity;

	@ZteSoftCommentAnnotationParam(name = "当前环节", type = "String", isNecessary = "Y", desc = "当前环节--")
	private String flowNode;

	@ZteSoftCommentAnnotationParam(name = "用户下单时间", type = "String", isNecessary = "Y", desc = "用户下单时间,格式YYYY-MM-DD HH:mm:ss")
	private String orderCreateTime;

	@ZteSoftCommentAnnotationParam(name = "订单生成时间", type = "String", isNecessary = "Y", desc = "订单生成时间,格式YYYY-MM-DD HH:mm:ss")
	private String createTime;

	@ZteSoftCommentAnnotationParam(name = "闪电送开始时间", type = "String", isNecessary = "Y", desc = "闪电送开始时间")
	private String lightTime;

	@ZteSoftCommentAnnotationParam(name = "外部订单状态", type = "String", isNecessary = "Y", desc = "外部订单状态")
	private String extOrderStatus;

	@ZteSoftCommentAnnotationParam(name = "买家留言", type = "String", isNecessary = "N", desc = "买家留言")
	private String buyerMessage;

	@ZteSoftCommentAnnotationParam(name = "卖家留言", type = "String", isNecessary = "N", desc = "卖家留言")
	private String sellerMessage;

	@ZteSoftCommentAnnotationParam(name = "订单备注", type = "String", isNecessary = "N", desc = "订单备注")
	private String memo;

	@ZteSoftCommentAnnotationParam(name = "支付信息", type = "String", isNecessary = "N", desc = "支付信息")
	private List<PayInfo> payInfo;
	
	@ZteSoftCommentAnnotationParam(name = "收货信息", type = "String", isNecessary = "N", desc = "收货信息")
	private List<CarryInfo> carryInfo;

	@ZteSoftCommentAnnotationParam(name = "配送信息", type = "String", isNecessary = "N", desc = "配送信息")
	private List<PostInfo> postInfo;

	@ZteSoftCommentAnnotationParam(name = "商品包信息", type = "String", isNecessary = "N", desc = "商品包信息")
	private List<PackInfo> packInfo;

	

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

	public String getFlowNode() {
		return flowNode;
	}

	public void setFlowNode(String flowNode) {
		this.flowNode = flowNode;
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


	public List<PayInfo> getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(List<PayInfo> payInfo) {
		this.payInfo = payInfo;
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

	public List<CarryInfo> getCarryInfo() {
		return carryInfo;
	}

	public void setCarryInfo(List<CarryInfo> carryInfo) {
		this.carryInfo = carryInfo;
	}

	public List<PostInfo> getPostInfo() {
		return postInfo;
	}

	public void setPostInfo(List<PostInfo> postInfo) {
		this.postInfo = postInfo;
	}

	public List<PackInfo> getPackInfo() {
		return packInfo;
	}

	public void setPackInfo(List<PackInfo> packInfo) {
		this.packInfo = packInfo;
	}

}
