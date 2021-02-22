package zte.net.ecsord.params.order.resp;

import params.ZteResponse;

public class StartWorkflowRsp extends ZteResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6954318603837535505L;
	
	/**
	 * 流程实例编号
	 * -1   流程启动失败；0  未匹配到流程配置； 其它   流程实例编号
	 */
	private String workflow_id;

	public String getWorkflow_id() {
		return workflow_id;
	}

	public void setWorkflow_id(String workflow_id) {
		this.workflow_id = workflow_id;
	}
}
