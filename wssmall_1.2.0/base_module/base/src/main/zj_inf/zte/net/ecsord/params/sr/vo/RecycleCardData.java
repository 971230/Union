package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class RecycleCardData implements Serializable {

	@ZteSoftCommentAnnotationParam(name="machineNo",type="String",isNecessary="Y",desc="machineNo：写卡机编号")
	private String machineNo;

	@ZteSoftCommentAnnotationParam(name = "orderId", type = "String", isNecessary = "Y", desc = "orderId：内部订单编号")
	private String orderId;

	public String getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
