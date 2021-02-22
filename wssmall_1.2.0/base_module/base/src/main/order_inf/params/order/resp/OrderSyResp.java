package params.order.resp;

import java.util.ArrayList;
import java.util.List;

import params.ZteResponse;

import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderOuter;

public class OrderSyResp extends ZteResponse {
	
	List<Order> orders =new ArrayList<Order>();
	private List<OrderOuter> orderOuters  = new ArrayList<OrderOuter>();
	 
	
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<OrderOuter> getOrderOuters() {
		return orderOuters;
	}

	public void setOrderOuters(List<OrderOuter> orderOuters) {
		this.orderOuters = orderOuters;
	}

	
}
