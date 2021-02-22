package rule.params.coqueue.resp;

import params.ZteResponse;
import zte.params.order.resp.OrderAddResp;

/**
 * 消息队列规则出参
 * @author wu.i
 *
 */
public class CoQueueRuleResp extends ZteResponse {
	
	private String co_id;  //消息队列标识
	private String resp_code;  //成功失败标识：000-成功；其他为失败
	private String resp_msg;  //失败描述
	private OrderAddResp orderAddResp;
	
	public String getCo_id() {
		return co_id;
	}
	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}
	public String getResp_code() {
		return resp_code;
	}
	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}
	public String getResp_msg() {
		return resp_msg;
	}
	public void setResp_msg(String resp_msg) {
		this.resp_msg = resp_msg;
	}
	public OrderAddResp getOrderAddResp() {
		return orderAddResp;
	}
	public void setOrderAddResp(OrderAddResp orderAddResp) {
		this.orderAddResp = orderAddResp;
	}
	
}
