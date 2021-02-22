package params.order.req;

import java.util.List;
import java.util.Map;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderOuter;

/**
 * 支付日志
* @作者 MoChunrun 
* @创建日期 2013-10-10 
* @版本 V 1.0
 */
public class PaymentLogReq extends ZteRequest<ZteResponse>{

	private String order_id;
	private String pay_type;
	
	private List<OrderOuter> orderOuters;
	
	//order 为 null时order_id必传
	private Order order;
	
	private Map params;
	
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
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
