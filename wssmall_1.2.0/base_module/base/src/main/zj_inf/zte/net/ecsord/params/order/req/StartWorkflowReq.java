package zte.net.ecsord.params.order.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import zte.net.ecsord.params.workCustom.po.ES_ORDER_INTENT;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CFG;

@SuppressWarnings("rawtypes")
public class StartWorkflowReq extends ZteRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7407846099270421719L;
	
	private String order_id;
	
	private String cfg_type;
	
	private ES_ORDER_INTENT orderIntent;
	
	private ES_WORK_CUSTOM_CFG cfg;
	
	public String getCfg_type() {
		return cfg_type;
	}

	public void setCfg_type(String cfg_type) {
		this.cfg_type = cfg_type;
	}	
	
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public ES_WORK_CUSTOM_CFG getCfg() {
		return cfg;
	}

	public void setCfg(ES_WORK_CUSTOM_CFG cfg) {
		this.cfg = cfg;
	}

	public ES_ORDER_INTENT getOrderIntent() {
		return orderIntent;
	}

	public void setOrderIntent(ES_ORDER_INTENT orderIntent) {
		this.orderIntent = orderIntent;
	}

	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "zte.orderService.orderstandingplan.startWorkflow";
	}
}
