package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class MachineNoRG implements Serializable {
	
	@ZteSoftCommentAnnotationParam(name="machineNo",type="String",isNecessary="Y",desc="写卡机编号")
	private String machineNo;
	public String getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}
}
