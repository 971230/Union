package zte.params.order.req;

import params.ZteError;
import params.ZteRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.params.order.resp.ReissueGoodsShippingAddResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

public class ReissueGoodsShippingAddReq extends ZteRequest<ReissueGoodsShippingAddResp> {
	
	private String [] deliveri_item_idArray;
	private OrderDeliveryBusiRequest delivery;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(deliveri_item_idArray==null)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "deliveri_item_idArray不能为空"));
		if(delivery==null)CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "delivery不能为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.delivery.reissue.goods.shipping.add";
	}

	public String[] getDeliveri_item_idArray() {
		return deliveri_item_idArray;
	}

	public void setDeliveri_item_idArray(String[] deliveri_item_idArray) {
		this.deliveri_item_idArray = deliveri_item_idArray;
	}

	public OrderDeliveryBusiRequest getDelivery() {
		return delivery;
	}

	public void setDelivery(OrderDeliveryBusiRequest delivery) {
		this.delivery = delivery;
	}

}
