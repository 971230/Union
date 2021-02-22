package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 2.3.8. 订单收单结果查询接口
 * 
 * @author 宋琪
 * @date 2017年6月29日
 */
public class OrderResultQueryInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	// 外系统单号，如果没有外系统单号，则填业务发起时的流水号
	@ZteSoftCommentAnnotationParam(name = "mall_order_id", type = "String", isNecessary = "N", desc = "out_order_id：外系统单号")
	private String mall_order_id;
	
	public String getMall_order_id() {
		return mall_order_id;
	}
	public void setMall_order_id(String mall_order_id) {
		this.mall_order_id = mall_order_id;
	}


}
