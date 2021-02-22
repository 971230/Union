package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class RecycleCardDataRG implements Serializable {

	@ZteSoftCommentAnnotationParam(name="machineNo",type="String",isNecessary="Y",desc="machineNo：写卡机编号")
	private String machineNo;

	@ZteSoftCommentAnnotationParam(name = "orderId", type = "String", isNecessary = "N", desc = "orderId：内部订单编号")
	private String orderId;
	
	@ZteSoftCommentAnnotationParam(name = "iccid", type = "String", isNecessary = "Y", desc = "iccid：ICCID")
	private String iccid;

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

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	
}
