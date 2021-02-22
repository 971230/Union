package params.order.req;

import java.util.List;
import java.util.Map;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderOuter;

/**
 * 物流请求表
* @作者 MoChunrun 
* @创建日期 2013-10-10 
* @版本 V 1.0
 */
public class DeliveryReq extends ZteRequest{

	private String order_id;
	//order 为 null时order_id必传
	private Order order;
	
	private List<OrderOuter> orderOuters;
 	
	private Map params;
	

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	

	public List<OrderOuter> getOrderOuters() {
		return orderOuters;
	}

	public void setOrderOuters(List<OrderOuter> orderOuters) {
		this.orderOuters = orderOuters;
	}
	
}
