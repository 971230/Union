package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class MachineNoRGResp implements Serializable {
	
	@ZteSoftCommentAnnotationParam(name="machineNo",type="String",isNecessary="Y",desc="写卡机的唯一编号")
	private String machineNo;
	
	@ZteSoftCommentAnnotationParam(name="status",type="String",isNecessary="Y",desc="反馈写卡机当前状态的编码1-正常2-写卡中3-空闲4-异常")
	private String status;
	
	@ZteSoftCommentAnnotationParam(name="exceptionMsg",type="String",isNecessary="N",desc="当写卡机状态为4,即异常时的详细信息,非异常情况,该参数为0具体请参考,附录-写卡机异常信息定义")
	private String exceptionMsg;
	public String getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
}
