/**
 * 
 */
package params.order.req;

import java.io.Serializable;

/**
 * @author ZX
 * OrderHandleLogsReq.java
 * 2014-10-14
 */
public class OrderExceptionLogsReq implements Serializable {
	
	private String order_id; // 订单ID
	private String flow_id; // 流程ID
	private String flow_trace_id; // 环节ID
	private String mark_time; // 标记时间
	private String mark_op_id; // 标记人（人工标记异常才会有值）
	private String mark_op_name; // 标记人名称（人工标记异常才会有值）
	private String abnormal_type;// 异常分类
	private String exception_type;// 异常类型
	private String exception_desc;// 异常描述
	private String deal_time; // 处理时间
	private String deal_op_id; // 处理人
	private String deal_op_name; // 处理人名称
	private String is_deal; // 是否处理
	private String source_from; // 来源
	private String exception_id;
	private String need_customer_visit;//是否需要客户回访:1.是;0.否
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
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
	public String getMark_time() {
		return mark_time;
	}
	public void setMark_time(String mark_time) {
		this.mark_time = mark_time;
	}
	public String getMark_op_id() {
		return mark_op_id;
	}
	public void setMark_op_id(String mark_op_id) {
		this.mark_op_id = mark_op_id;
	}
	public String getMark_op_name() {
		return mark_op_name;
	}
	public void setMark_op_name(String mark_op_name) {
		this.mark_op_name = mark_op_name;
	}
	public String getAbnormal_type() {
		return abnormal_type;
	}
	public void setAbnormal_type(String abnormal_type) {
		this.abnormal_type = abnormal_type;
	}
	public String getException_type() {
		return exception_type;
	}
	public void setException_type(String exception_type) {
		this.exception_type = exception_type;
	}
	public String getException_desc() {
		return exception_desc;
	}
	public void setException_desc(String exception_desc) {
		this.exception_desc = exception_desc;
	}
	public String getDeal_time() {
		return deal_time;
	}
	public void setDeal_time(String deal_time) {
		this.deal_time = deal_time;
	}
	public String getDeal_op_id() {
		return deal_op_id;
	}
	public void setDeal_op_id(String deal_op_id) {
		this.deal_op_id = deal_op_id;
	}
	public String getDeal_op_name() {
		return deal_op_name;
	}
	public void setDeal_op_name(String deal_op_name) {
		this.deal_op_name = deal_op_name;
	}
	public String getIs_deal() {
		return is_deal;
	}
	public void setIs_deal(String is_deal) {
		this.is_deal = is_deal;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getException_id() {
		return exception_id;
	}
	public void setException_id(String exception_id) {
		this.exception_id = exception_id;
	}
	public String getNeed_customer_visit() {
		return need_customer_visit;
	}
	public void setNeed_customer_visit(String need_customer_visit) {
		this.need_customer_visit = need_customer_visit;
	}
}
