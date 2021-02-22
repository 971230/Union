package zte.params.order.req;

import java.util.List;

import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.model.OrderOuter;

public class OrderSyncReq extends OrderAddReq {

	private List<OrderOuter> orderOuterList;
	
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.order.syncadd";
	}

	public List<OrderOuter> getOrderOuterList() {
		return orderOuterList;
	}

	public void setOrderOuterList(List<OrderOuter> orderOuterList) {
		this.orderOuterList = orderOuterList;
	}

	
}
