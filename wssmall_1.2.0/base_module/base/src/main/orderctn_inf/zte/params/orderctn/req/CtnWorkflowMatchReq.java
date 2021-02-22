package zte.params.orderctn.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import zte.net.ecsord.params.workCustom.po.ES_ORDER_INTENT;

@SuppressWarnings("rawtypes")
public class CtnWorkflowMatchReq extends ZteRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8736988337738297716L;

	private ES_ORDER_INTENT orderIntent;
	
	private String cfg_type;
	
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
		return "zte.net.iservice.impl.ZteOrderCtnOpenService.workflowMatch";
	}
}
