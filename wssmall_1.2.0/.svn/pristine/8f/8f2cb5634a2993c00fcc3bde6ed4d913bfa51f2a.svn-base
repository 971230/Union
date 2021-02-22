package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.WorkFlowCallBackResp;

public class WorkFlowCallBackReq extends ZteRequest<WorkFlowCallBackResp> {
	
	@ZteSoftCommentAnnotationParam(name="订单ID",type="List",isNecessary="Y",desc="订单ID")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="流程ID",type="List",isNecessary="Y",desc="流程ID")
	private String flow_id;
	@ZteSoftCommentAnnotationParam(name="当前环节ID",type="List",isNecessary="Y",desc="当前环节ID")
	private String curr_trace_id;
	@ZteSoftCommentAnnotationParam(name="下一环节ID",type="List",isNecessary="N",desc="下一环节ID")
	private String next_trace_id;
	@ZteSoftCommentAnnotationParam(name="当前环节执行状态",type="List",isNecessary="Y",desc="当前环节执行状态")
	private String flow_status;
	@ZteSoftCommentAnnotationParam(name="是否为最后一环节",type="List",isNecessary="Y",desc="是否为最后一环节")
	private boolean endFlow;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.workflow.callback";
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getCurr_trace_id() {
		return curr_trace_id;
	}

	public void setCurr_trace_id(String curr_trace_id) {
		this.curr_trace_id = curr_trace_id;
	}

	public String getNext_trace_id() {
		return next_trace_id;
	}

	public void setNext_trace_id(String next_trace_id) {
		this.next_trace_id = next_trace_id;
	}

	public String getFlow_id() {
		return flow_id;
	}

	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}

	public String getFlow_status() {
		return flow_status;
	}

	public void setFlow_status(String flow_status) {
		this.flow_status = flow_status;
	}

	public boolean isEndFlow() {
		return endFlow;
	}

	public void setEndFlow(boolean endFlow) {
		this.endFlow = endFlow;
	}

}
