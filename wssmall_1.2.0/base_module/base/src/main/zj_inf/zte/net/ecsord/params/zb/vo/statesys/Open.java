package zte.net.ecsord.params.zb.vo.statesys;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Open implements Serializable {
	private static final long serialVersionUID = -3929217846456273610L;

	@ZteSoftCommentAnnotationParam(name = "开户流水号", type = "String", isNecessary = "Y", desc = "OpenOrderId：开户流水号")
	private String OpenOrderId;	
	
	@ZteSoftCommentAnnotationParam(name = "开户ICCID", type = "String", isNecessary = "Y", desc = "OpenIccid：开户ICCID")
	private String OpenIccid;

	public String getOpenOrderId() {
		return OpenOrderId;
	}

	public void setOpenOrderId(String openOrderId) {
		OpenOrderId = openOrderId;
	}

	public String getOpenIccid() {
		return OpenIccid;
	}

	public void setOpenIccid(String openIccid) {
		OpenIccid = openIccid;
	}
}
