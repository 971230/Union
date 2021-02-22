/**
 * 
 */
package params.order.req;

import java.io.Serializable;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

/**
 * @author ZX
 * OrderExceptionCollectReq.java
 * 2014-10-15
 */
public class OrderExceptionCollectReq extends ZteRequest implements Serializable {

	private String exception_id;
	private String order_id;
	private String create_date;
	private String comments;
	private String flow_id;
	private String flow_trace_id;
	
	private String exception_name; // 异常类型名
	private String trace_name; // 环节名称
	private String need_customer_visit; // 是否需要客户回访
	
	@NotDbField
	public String getException_name() {
		return exception_name;
	}
	public void setException_name(String exception_name) {
		this.exception_name = exception_name;
	}
	@NotDbField
	public String getTrace_name() {
		return trace_name;
	}

	public void setTrace_name(String trace_name) {
		this.trace_name = trace_name;
	}

	public String getException_id() {
		return exception_id;
	}

	public void setException_id(String exception_id) {
		this.exception_id = exception_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

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

	public String getNeed_customer_visit() {
		return need_customer_visit;
	}
	public void setNeed_customer_visit(String need_customer_visit) {
		this.need_customer_visit = need_customer_visit;
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

}
