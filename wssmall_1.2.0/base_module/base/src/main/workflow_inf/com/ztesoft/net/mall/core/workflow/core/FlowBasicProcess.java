package com.ztesoft.net.mall.core.workflow.core;

import java.util.Map;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.service.IFlowManager;

public abstract class FlowBasicProcess  extends BaseSupport  implements IProcess{
	
	private IFlowManager flowManager;
	
	private boolean ifFinalStep = false;
	
	protected String audit_state ;
	

	//查询下一步环节
	@Override
	public Map qryNextStep(Map map){
		
		Map result = flowManager.qryNextItem(map);
		
		return result;
	}
	
	//查询下一步人
	@Override
	public String qryNextPeron(Map map){
		
		String result = flowManager.qryNextPerson(map);
		
		return result;
	}
	
	
	/**
	 * 
	 * 业务处理
	 * 
	 */
	@Override
	public abstract void doBusinisees(Integer flow_inst_id, String ref_obj_id) ;
	
	//public abstract String handleCond(Integer flow_inst_id,String ref_obj_id);
	
	public boolean isIfFinalStep() {
		return ifFinalStep;
	}

	public void setIfFinalStep(boolean ifFinalStep) {
		this.ifFinalStep = ifFinalStep;
	}
	
	public IFlowManager getFlowManager() {
		return flowManager;
	}

	public void setFlowManager(IFlowManager flowManager) {
		this.flowManager = flowManager;
	}

	public String getAudit_state() {
		return audit_state;
	}

	public void setAudit_state(String audit_state) {
		this.audit_state = audit_state;
	}

	
}
