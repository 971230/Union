package zte.net.ecsord.params.order.resp;

import params.ZteResponse;
import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CFG;

public class WorkflowMatchRsp extends ZteResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -443440750829677210L;
	
	private ES_WORK_CUSTOM_CFG cfg;

	public ES_WORK_CUSTOM_CFG getCfg() {
		return cfg;
	}

	public void setCfg(ES_WORK_CUSTOM_CFG cfg) {
		this.cfg = cfg;
	}
	
}
