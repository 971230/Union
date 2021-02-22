package zte.net.ecsord.params.trace.req;

import params.ZteRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author wu.i 
 * 订单环节对象
 * 
 */
public class TraceRequest extends ZteRequest {

	private OrderTreeBusiRequest orderTreeRequest;
	
	public OrderTreeBusiRequest getOrderTreeRequest() {
		return orderTreeRequest;
	}

	public void setOrderTreeRequest(OrderTreeBusiRequest orderTreeRequest) {
		this.orderTreeRequest = orderTreeRequest;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

}
