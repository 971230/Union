package zte.net.ecsord.params.wms.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 订单业务完成状态通知
 * 订单信息对象
 */
public class NotifyAccountOrderInfoVo implements Serializable{
		
	private static final long serialVersionUID = 2511673785670251748L;
	
	@ZteSoftCommentAnnotationParam(name="内部订单Id",type="String",isNecessary="Y",desc="内部订单Id")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="明细信息",type="String",isNecessary="Y",desc="明细信息")
	private List<NotifyAccountGoodInfoVo> goodInfo;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<NotifyAccountGoodInfoVo> getGoodInfo() {
		return goodInfo;
	}

	public void setGoodInfo(List<NotifyAccountGoodInfoVo> goodInfo) {
		this.goodInfo = goodInfo;
	}
	
}
