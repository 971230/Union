package zte.net.ecsord.params.wms.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 接收WMS货品信息同步接口
 * 订单信息对象
 */
public class SynSerialOrderInfoVo implements Serializable{
	
	private static final long serialVersionUID = 7457064213037096572L;
	
	@ZteSoftCommentAnnotationParam(name="内部订单Id",type="String",isNecessary="Y",desc="内部订单Id")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="明细信息",type="List<SynSerialGoodInfoVo>",isNecessary="Y",desc="明细信息")
	private List<SynSerialGoodInfoVo> goodInfo;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<SynSerialGoodInfoVo> getGoodInfo() {
		return goodInfo;
	}

	public void setGoodInfo(List<SynSerialGoodInfoVo> goodInfo) {
		this.goodInfo = goodInfo;
	}
	
}
