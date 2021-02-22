package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.OrderDeliveryListResp;

public class OrderDeliveryListReq extends ZteRequest<OrderDeliveryListResp> {
	
	public static final int DELIVERY_TYPE_1 = 1;//发货单据列表
	public static final int DELIVERY_TYPE_2 = 2;//退货单据列表
	public static final int DELIVERY_TYPE_3 = 3;//换货单据列表
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="N",desc="订单ID")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="单据类型 1、发货单据2、退货单据3、换货单据",type="String",isNecessary="Y",desc="单据类型 1、发货单据2、退货单据3、换货单据")
	private int type;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.orderService.order.deliverylist";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
