package com.ztesoft.remote.basic.params.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Order;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-13 11:09
 * To change this template use File | Settings | File Templates.
 */
public class ChargeFeeResponse extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="主订单信息",type="Order",isNecessary="Y",desc="主订单信息")
	private Order order;
	
	@ZteSoftCommentAnnotationParam(name="标示是否使用web支付",type="Order",isNecessary="Y",desc="使用预存款进行支付，如果前台界面录入金额小于冻结余额," +
			"则须要另外支付的金额为0;如果冻结余额<充值金额，就使用冻结余额来支付一部份话费，其它的走银通web支付;yes:使用了web支付，no:没用使用web支付")
	private String is_web_charge;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getIs_web_charge() {
		return is_web_charge;
	}

	public void setIs_web_charge(String is_web_charge) {
		this.is_web_charge = is_web_charge;
	}
	
	
}
