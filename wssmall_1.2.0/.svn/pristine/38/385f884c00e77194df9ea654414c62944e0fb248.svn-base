package params.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;
import zte.net.ecsord.params.workCustom.po.ES_ORDER_INTENT;

@SuppressWarnings("rawtypes")
public class StdWorkflowMatchReq extends ZteRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -178163141021854749L;

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
		return "zte.net.orderstd.workCustom.workflowMatch";
	}
}
