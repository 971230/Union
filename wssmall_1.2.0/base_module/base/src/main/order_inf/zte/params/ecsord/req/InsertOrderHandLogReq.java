package zte.params.ecsord.req;

import params.ZteRequest;
import zte.params.ecsord.resp.InsertOrderHandLogResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 
 * @author ZX
 * InsertOrderHandLogReq.java
 * 2014-12-25
 */
public class InsertOrderHandLogReq extends ZteRequest<InsertOrderHandLogResp>{
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="订单编号")
	private String order_id;
	private String flow_id; // 流程ID
	private String flow_trace_id; // 环节ID
	private String comments; // 描述
	private String create_time; // 处理时间
	private String op_id; // 处理人
	private String op_name; // 处理人名称
	private String source_from; // 来源
	private String type_code; // 业务处理类型编码
	private String handler_type; // 处理类型
	
	private String type_name;//跟type_code对应
	private String trace_name;//环节名称
	
	public String getFlow_id() {
		return flow_id;
	}

	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}

	public String getFlow_trace_id() {
		return flow_trace_id;
	}

	public void setFlow_trace_id(String flow_trace_id) {
		this.flow_trace_id = flow_trace_id;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getOp_id() {
		return op_id;
	}

	public void setOp_id(String op_id) {
		this.op_id = op_id;
	}

	public String getOp_name() {
		return op_name;
	}

	public void setOp_name(String op_name) {
		this.op_name = op_name;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public String getHandler_type() {
		return handler_type;
	}

	public void setHandler_type(String handler_type) {
		this.handler_type = handler_type;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getTrace_name() {
		return trace_name;
	}

	public void setTrace_name(String trace_name) {
		this.trace_name = trace_name;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.params.ecsord.req.InsertOrderHandLogReq";
	}
}
