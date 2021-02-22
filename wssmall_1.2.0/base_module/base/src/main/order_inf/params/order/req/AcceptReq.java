package params.order.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.model.OrderOuter;

/**
 * 受理请求表
* @作者 wu.i 
* @创建日期 2013-10-10 
* @版本 V 1.0
 */
public class AcceptReq extends ZteRequest{

	OrderItem orderItem;
	
	List<OrderOuter> orderOuters;
	
	private List<Map> paramsList= new ArrayList<Map>();
	
	Order order;
	
	private ZteRequest zteRequest;

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public ZteRequest getZteRequest() {
		return zteRequest;
	}

	public void setZteRequest(ZteRequest zteRequest) {
		this.zteRequest = zteRequest;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<OrderOuter> getOrderOuters() {
		return orderOuters;
	}

	public void setOrderOuters(List<OrderOuter> orderOuters) {
		this.orderOuters = orderOuters;
	}

	public List<Map> getParamsList() {
		return paramsList;
	}

	public void setParamsList(List<Map> paramsList) {
		this.paramsList = paramsList;
	}
	
	
}
