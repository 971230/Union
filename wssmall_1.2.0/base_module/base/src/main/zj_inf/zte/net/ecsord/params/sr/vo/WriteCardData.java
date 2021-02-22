package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class WriteCardData implements Serializable {

	@ZteSoftCommentAnnotationParam(name="simCardNo",type="String",isNecessary="Y",desc="simCardNo：白卡卡号")
	private String simCardNo;

	@ZteSoftCommentAnnotationParam(name="imsi",type="String",isNecessary="Y",desc="imsi：imsi号")
	private String imsi;	

	@ZteSoftCommentAnnotationParam(name="number",type="String",isNecessary="Y",desc="number:号码")
	private String number;		

	@ZteSoftCommentAnnotationParam(name="option",type="String",isNecessary="Y",desc="option:读写卡脚本")
	private String option;		

	@ZteSoftCommentAnnotationParam(name="machineNo",type="String",isNecessary="Y",desc="machineNo:写卡机编号")
	private String machineNo;

	@ZteSoftCommentAnnotationParam(name = "orderId", type = "String", isNecessary = "Y", desc = "orderId：内部订单编号")
	private String orderId;

	public String getSimCardNo() {
		return simCardNo;
	}

	public void setSimCardNo(String simCardNo) {
		this.simCardNo = simCardNo;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

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
