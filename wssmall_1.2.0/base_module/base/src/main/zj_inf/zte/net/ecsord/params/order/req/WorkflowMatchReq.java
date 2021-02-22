package zte.net.ecsord.params.order.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.workCustom.po.ES_ORDER_INTENT;

@SuppressWarnings("rawtypes")
public class WorkflowMatchReq extends ZteRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2692765541880715155L;

	private ES_ORDER_INTENT orderIntent;
	
	private String cfg_type;
	
	private String flow_code;
	
	private OrderTreeBusiRequest orderTree;
	
	public ES_ORDER_INTENT getOrderIntent() {
		return orderIntent;
	}

	public void setOrderIntent(ES_ORDER_INTENT orderIntent) {
		this.orderIntent = orderIntent;
	}

	public String getCfg_type() {
		return cfg_type;
	}

	public void setCfg_type(String cfg_type) {
		this.cfg_type = cfg_type;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.orderService.orderstandingplan.workflowMatch";
	}

	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}

	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}

	public String getFlow_code() {
		return flow_code;
	}

	public void setFlow_code(String flow_code) {
		this.flow_code = flow_code;
	}
}
