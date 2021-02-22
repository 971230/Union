package zte.params.orderctn.req;

import java.util.List;
import java.util.Map;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.util.StringUtil;

import params.ZteRequest;
import zte.params.orderctn.resp.OrderCollectionCtnResp;

public class OrderCollectionCtnReq extends ZteRequest<OrderCollectionCtnResp>{

	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name="请求报文",type="Map",isNecessary="Y",desc="请求报文")
	private Map inParams;
	
	@ZteSoftCommentAnnotationParam(name="请求报文",type="String",isNecessary="Y",desc="请求报文")
	private String inReq;
	
	@ZteSoftCommentAnnotationParam(name="接口编码",type="String",isNecessary="Y",desc="接口编码")
	private String out_service_code;
	
	@ZteSoftCommentAnnotationParam(name="接口编码",type="String",isNecessary="Y",desc="接口编码")
	private String service_code;
	
	@ZteSoftCommentAnnotationParam(name="订单单号",type="String",isNecessary="Y",desc="订单单号")
	private String order_id;
	
	@ZteSoftCommentAnnotationParam(name="动作",type="String",isNecessary="Y",desc="add新增")
	private String action_code;
	
	@ZteSoftCommentAnnotationParam(name="队列id",type="String",isNecessary="Y",desc="队列id,订单队列数据转工作数据需要传入")
	private String co_id;
	
	@ZteSoftCommentAnnotationParam(name="批次号",type="String",isNecessary="Y",desc="批次号")
	private String batch_id;
	
	@ZteSoftCommentAnnotationParam(name="队列存储业务类型",type="String",isNecessary="Y",desc="队列存储业务类型(ADD 下单写队列  SPLIT拆单写队列)")
	private String busi_type;
	
	@ZteSoftCommentAnnotationParam(name="订单拆分数据",type="List",isNecessary="Y",desc="订单拆分数据")
	private List<Map<String, Object>> orderList;
	
	@Override
	public void check() throws ApiRuleException {
		
	}
	
	@Override
	public String getApiMethodName() {
		if(StringUtil.isEmpty(co_id))
			return "zte.service.orderqueue.orderCollectionQueue";
		return "zte.service.orderqueue.orderCollectionWork";
	}

	public Map getInParams() {
		return inParams;
	}

	public void setInParams(Map inParams) {
		this.inParams = inParams;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public String getOut_service_code() {
		return out_service_code;
	}

	public void setOut_service_code(String out_service_code) {
		this.out_service_code = out_service_code;
	}

	public String getInReq() {
		return inReq;
	}

	public void setInReq(String inReq) {
		this.inReq = inReq;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getAction_code() {
		return action_code;
	}

	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}

	public String getCo_id() {
		return co_id;
	}

	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}

	public String getBusi_type() {
		return busi_type;
	}

	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}

	public List<Map<String, Object>> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Map<String, Object>> orderList) {
		this.orderList = orderList;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

}
