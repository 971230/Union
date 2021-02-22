package zte.net.ecsord.params.wms.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 接收写卡机编号
 * 订单信息对象
 */
public class SynCardInfoOrderInfoVo implements Serializable{
	
	private static final long serialVersionUID = -6241174708029045711L;
	
	@ZteSoftCommentAnnotationParam(name="内部订单Id",type="String",isNecessary="Y",desc="内部订单Id")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="明细信息",type="List<SynCardInfoGoodInfoVo>",isNecessary="Y",desc="明细信息")
	private List<SynCardInfoGoodInfoVo> goodInfo;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<SynCardInfoGoodInfoVo> getGoodInfo() {
		return goodInfo;
	}

	public void setGoodInfo(List<SynCardInfoGoodInfoVo> goodInfo) {
		this.goodInfo = goodInfo;
	}
	
}
