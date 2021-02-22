package params.orderqueue.resp;

import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class AsynExecuteMsgWriteMqResp extends ZteResponse {
	/** 
	 * serialVersionUID: TODO(用一句话描述这个变量表示什么) 
	 */ 
	private static final long serialVersionUID = 6027266309938044239L;

	@ZteSoftCommentAnnotationParam(name = "返回报文", type = "String", isNecessary = "Y", desc = "返回报文")
	private String respStr;

	@ZteSoftCommentAnnotationParam(name = "返回报文", type = "Map", isNecessary = "Y", desc = "返回报文")
	private Map<String, Object> infOutMap;

	@ZteSoftCommentAnnotationParam(name = "队列id", type = "String", isNecessary = "Y", desc = "队列id")
	private String co_id;

	@ZteSoftCommentAnnotationParam(name = "订单id", type = "String", isNecessary = "Y", desc = "订单id")
	private String order_id;

	@ZteSoftCommentAnnotationParam(name = "是否DB挂掉并直接返回成功到外系统", type = "boolean", isNecessary = "Y", desc = "是否DB挂掉并需要返回成功到外系统,默认否")
	private boolean isCacheDisasterTolerant = false;

	public String getRespStr() {
		return respStr;
	}

	public void setRespStr(String respStr) {
		this.respStr = respStr;
	}

	public Map<String, Object> getInfOutMap() {
		return infOutMap;
	}

	public void setInfOutMap(Map<String, Object> infOutMap) {
		this.infOutMap = infOutMap;
	}

	public String getCo_id() {
		return co_id;
	}

	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public boolean isCacheDisasterTolerant() {
		return isCacheDisasterTolerant;
	}

	public void setCacheDisasterTolerant(boolean isCacheDisasterTolerant) {
		this.isCacheDisasterTolerant = isCacheDisasterTolerant;
	}

}
