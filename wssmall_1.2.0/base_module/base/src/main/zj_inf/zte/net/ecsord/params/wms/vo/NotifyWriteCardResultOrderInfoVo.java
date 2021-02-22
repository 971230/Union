package zte.net.ecsord.params.wms.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 写卡完成通知WMS
 * 订单信息对象
 */
public class NotifyWriteCardResultOrderInfoVo implements Serializable{
	
	private static final long serialVersionUID = 5290212126145757844L;
	
	@ZteSoftCommentAnnotationParam(name="内部订单Id",type="String",isNecessary="Y",desc="内部订单Id")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="明细信息",type="List<NotifyWriteCardResultGoodInfoVo>",isNecessary="Y",desc="明细信息")
	private List<NotifyWriteCardResultGoodInfoVo> goodInfo;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<NotifyWriteCardResultGoodInfoVo> getGoodInfo() {
		return goodInfo;
	}

	public void setGoodInfo(List<NotifyWriteCardResultGoodInfoVo> goodInfo) {
		this.goodInfo = goodInfo;
	}
	
}
