package zte.params.orderctn.resp;

import java.util.List;
import java.util.Map;

import params.ZteResponse;
import params.orderqueue.resp.OrderCollectionResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.OpenServiceCfg;

public class OrderXmlToMapResp extends ZteResponse{
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name="返回订单归集结果集合",type="Map",isNecessary="Y",desc="返回订单归集结果集合")
	private List<OrderCollectionResp> orderCollectList;
	
	@ZteSoftCommentAnnotationParam(name="请求报文xml",type="String",isNecessary="Y",desc="请求报文xml")
	private String inReq;	
	
	@ZteSoftCommentAnnotationParam(name="队列id",type="String",isNecessary="Y",desc="队列id")
	private String base_co_id;
	
	@ZteSoftCommentAnnotationParam(name="请求报文解析Map",type="Map",isNecessary="Y",desc="请求报文解析Map")
	private Map inParams;
	
	@ZteSoftCommentAnnotationParam(name="接口编码",type="String",isNecessary="Y",desc="接口编码")
	private String out_service_code;
	
	@ZteSoftCommentAnnotationParam(name="订单单号",type="String",isNecessary="Y",desc="订单单号")
	private String base_order_id;
	
	@ZteSoftCommentAnnotationParam(name="动作",type="String",isNecessary="Y",desc="add新增")
	private String action_code;
	
	@ZteSoftCommentAnnotationParam(name="ES_OPEN_SERVICE_CFG对应配置信息",type="OpenServiceCfg",isNecessary="Y",desc="ES_OPEN_SERVICE_CFG对应配置信息")
	private OpenServiceCfg cfg;
	
	
	public List<OrderCollectionResp> getOrderCollectList() {
		return orderCollectList;
	}
	public void setOrderCollectList(List<OrderCollectionResp> orderCollectList) {
		this.orderCollectList = orderCollectList;
	}
	public Map getInParams() {
		return inParams;
	}
	public void setInParams(Map inParams) {
		this.inParams = inParams;
	}
	public String getOut_service_code() {
		return out_service_code;
	}
	public void setOut_service_code(String out_service_code) {
		this.out_service_code = out_service_code;
	}
	public String getAction_code() {
		return action_code;
	}
	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}
	public String getInReq() {
		return inReq;
	}
	public void setInReq(String inReq) {
		this.inReq = inReq;
	}
	public OpenServiceCfg getCfg() {
		return cfg;
	}
	public void setCfg(OpenServiceCfg cfg) {
		this.cfg = cfg;
	}
	public String getBase_co_id() {
		return base_co_id;
	}
	public void setBase_co_id(String base_co_id) {
		this.base_co_id = base_co_id;
	}
	public String getBase_order_id() {
		return base_order_id;
	}
	public void setBase_order_id(String base_order_id) {
		this.base_order_id = base_order_id;
	}
}
