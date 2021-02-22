package zte.net.ecsord.params.wms.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 接收WMS稽核数据通知
 * 订单信息对象
 */
public class SynCheckFromWMSOrderInfoVo implements Serializable{
	
	private static final long serialVersionUID = -2067600430658290167L;
	
	@ZteSoftCommentAnnotationParam(name="内部订单Id",type="String",isNecessary="Y",desc="内部订单Id")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="明细信息",type="List<SynCheckFromWMSGoodInfoVo>",isNecessary="Y",desc="明细信息")
	private List<SynCheckFromWMSGoodInfoVo> goodInfo;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<SynCheckFromWMSGoodInfoVo> getGoodInfo() {
		return goodInfo;
	}

	public void setGoodInfo(List<SynCheckFromWMSGoodInfoVo> goodInfo) {
		this.goodInfo = goodInfo;
	}
}
