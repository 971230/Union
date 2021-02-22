package zte.params.process.req;

import params.ZteRequest;
import zte.params.process.resp.UosFlowResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class UosFlowInstanceReq extends ZteRequest<UosFlowResp>{

	@ZteSoftCommentAnnotationParam(name = "流程实例ID", type = "String", isNecessary = "N", desc = "流程实例ID")
	private String processInstanceId;
	
	@ZteSoftCommentAnnotationParam(name = "流程定义ID", type = "String", isNecessary = "N", desc = "流程定义ID")
	private String processId;

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.uosService.process.flowInstance";
	}

}
