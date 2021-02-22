package zte.params.orderctn.resp;

import java.util.List;
import java.util.Map;

import params.ZteResponse;
import params.orderqueue.resp.OrderCollectionResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.OpenServiceCfg;

public class OrderCtnResp extends ZteResponse {

	private static final long serialVersionUID = -2666482501625376190L;

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "N", desc = "0000：成功/其他编码：失败")
	private String resp_code;

	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "结果描述信息")
	private String resp_msg;
	
	@ZteSoftCommentAnnotationParam(name = "消费类型", type = "String", isNecessary = "N", desc = "d:dubbo调用/m:mq")
	private String rpc_type;

	@ZteSoftCommentAnnotationParam(name = "返回订单归集结果集合", type = "Map", isNecessary = "Y", desc = "返回订单归集结果集合")
	private List<OrderCollectionResp> orderCollectList;

	@ZteSoftCommentAnnotationParam(name = "请求报文xml", type = "String", isNecessary = "Y", desc = "请求报文xml")
	private String reqMsgStr;

	@ZteSoftCommentAnnotationParam(name = "队列id", type = "String", isNecessary = "Y", desc = "队列id")
	private String base_co_id;

	@ZteSoftCommentAnnotationParam(name = "请求报文解析Map", type = "Map", isNecessary = "Y", desc = "请求报文解析Map")
	private Map reqParamsMap;

	@ZteSoftCommentAnnotationParam(name = "接口编码", type = "String", isNecessary = "Y", desc = "接口编码")
	private String out_service_code;

	@ZteSoftCommentAnnotationParam(name = "订单单号", type = "String", isNecessary = "Y", desc = "订单单号")
	private String base_order_id;

	@ZteSoftCommentAnnotationParam(name = "动作", type = "String", isNecessary = "Y", desc = "add新增")
	private String action_code;

	@ZteSoftCommentAnnotationParam(name = "ES_OPEN_SERVICE_CFG对应配置信息", type = "OpenServiceCfg", isNecessary = "Y", desc = "ES_OPEN_SERVICE_CFG对应配置信息")
	private OpenServiceCfg cfg;
	
	@ZteSoftCommentAnnotationParam(name = "内部订单单号", type = "String", isNecessary = "Y", desc = "内部订单单号")
	private String order_id;
	

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public List<OrderCollectionResp> getOrderCollectList() {
		return orderCollectList;
	}

	public void setOrderCollectList(List<OrderCollectionResp> orderCollectList) {
		this.orderCollectList = orderCollectList;
	}

	public String getReqMsgStr() {
		return reqMsgStr;
	}

	public void setReqMsgStr(String reqMsgStr) {
		this.reqMsgStr = reqMsgStr;
	}

	public String getBase_co_id() {
		return base_co_id;
	}

	public void setBase_co_id(String base_co_id) {
		this.base_co_id = base_co_id;
	}

	public Map getReqParamsMap() {
		return reqParamsMap;
	}

	public void setReqParamsMap(Map reqParamsMap) {
		this.reqParamsMap = reqParamsMap;
	}

	public String getOut_service_code() {
		return out_service_code;
	}

	public void setOut_service_code(String out_service_code) {
		this.out_service_code = out_service_code;
	}

	public String getBase_order_id() {
		return base_order_id;
	}

	public void setBase_order_id(String base_order_id) {
		this.base_order_id = base_order_id;
	}

	public String getAction_code() {
		return action_code;
	}

	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}

	public OpenServiceCfg getCfg() {
		return cfg;
	}

	public void setCfg(OpenServiceCfg cfg) {
		this.cfg = cfg;
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

	public String getRpc_type() {
		return rpc_type;
	}

	public void setRpc_type(String rpc_type) {
		this.rpc_type = rpc_type;
	}
	
}
