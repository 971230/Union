package params.orderqueue.resp;

import java.util.Map;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderCollectionResp extends ZteResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	public String getRespStr() {
		return respStr;
	}

	public void setRespStr(String respStr) {
		this.respStr = respStr;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderCollectionResp [respStr=").append(respStr).append(", infOutMap=").append(infOutMap)
				.append(", co_id=").append(co_id).append(", order_id=").append(order_id)
				.append(", isCacheDisasterTolerant=").append(isCacheDisasterTolerant).append("]");
		return builder.toString();
	}

}
