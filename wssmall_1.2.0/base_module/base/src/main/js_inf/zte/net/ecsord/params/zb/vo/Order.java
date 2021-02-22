package zte.net.ecsord.params.zb.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Order implements Serializable {
	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "OrderId：订单编号")
	private String OrderId;
	
	@ZteSoftCommentAnnotationParam(name = "变更描述", type = "String", isNecessary = "Y", desc = "StateChgDesc：变更描述")
	private String RespDesc;
	
	@ZteSoftCommentAnnotationParam(name = "状态标记", type = "String", isNecessary = "Y", desc = "StateTag：状态标记")
	private String StateTag;
	
	@ZteSoftCommentAnnotationParam(name = "变更时间", type = "String", isNecessary = "Y", desc = "StateChgTime：变更时间")
	private String StateChgTime;
	
	@ZteSoftCommentAnnotationParam(name = "变更原因", type = "String", isNecessary = "Y", desc = "StateChgReason：变更原因")
	private String StateChgReason;
	
	@ZteSoftCommentAnnotationParam(name = "变更描述", type = "String", isNecessary = "Y", desc = "StateChgDesc：变更描述")
	private String StateChgDesc;
	
	@ZteSoftCommentAnnotationParam(name = "归档信息", type = "FetchInfo", isNecessary = "Y", desc = "FetchInfo：归档信息")
	private List<Fetch> FetchInfo;

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public String getRespDesc() {
		return RespDesc;
	}

	public void setRespDesc(String respDesc) {
		RespDesc = respDesc;
	}

	public String getStateTag() {
		return StateTag;
	}

	public void setStateTag(String stateTag) {
		StateTag = stateTag;
	}

	public String getStateChgTime() {
		return StateChgTime;
	}

	public void setStateChgTime(String stateChgTime) {
		StateChgTime = stateChgTime;
	}

	public String getStateChgReason() {
		return StateChgReason;
	}

	public void setStateChgReason(String stateChgReason) {
		StateChgReason = stateChgReason;
	}

	public String getStateChgDesc() {
		return StateChgDesc;
	}

	public void setStateChgDesc(String stateChgDesc) {
		StateChgDesc = stateChgDesc;
	}

	public List<Fetch> getFetchInfo() {
		return FetchInfo;
	}

	public void setFetchInfo(List<Fetch> fetchInfo) {
		FetchInfo = fetchInfo;
	}

}
