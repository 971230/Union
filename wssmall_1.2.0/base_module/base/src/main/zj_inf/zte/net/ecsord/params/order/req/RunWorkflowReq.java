package zte.net.ecsord.params.order.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class RunWorkflowReq extends ZteRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1977175063114594473L;
	
	private String instance_id;
	
	private boolean isGoNextManual;
	
	private String webCondition;
	
	private String remark;
	
	private String json_param;

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.orderService.orderstandingplan.runWorkflow";
	}

	public String getInstance_id() {
		return instance_id;
	}

	public void setInstance_id(String instance_id) {
		this.instance_id = instance_id;
	}

	public boolean isGoNextManual() {
		return isGoNextManual;
	}

	public void setGoNextManual(boolean isGoNextManual) {
		this.isGoNextManual = isGoNextManual;
	}

	public String getWebCondition() {
		return webCondition;
	}

	public void setWebCondition(String webCondition) {
		this.webCondition = webCondition;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getJson_param() {
		return json_param;
	}

	public void setJson_param(String json_param) {
		this.json_param = json_param;
	}

}
