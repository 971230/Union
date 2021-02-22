package params.order.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.model.OrderOuter;

public class ActionRuleReq extends ZteRequest {

	private String order_id;
	private String payment_id;
	private List<OrderItem>  orderItemList;
	
	private List<OrderOuter> orderOuters;
	
	private List<Map> paramsList= new ArrayList<Map>();
	
	private Order order;
	
	private ZteRequest zteRequest;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
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
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
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
