package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Station implements Serializable {

	@ZteSoftCommentAnnotationParam(name="sysTime",type="String",isNecessary="Y",desc="返回时的系统时间")
	private String sysTime;
	
	@ZteSoftCommentAnnotationParam(name="status",type="String",isNecessary="Y",desc="反馈写卡机当前状态的编码：1-正常2-写卡中3-空闲4-异常")
	private String status;
	
	@ZteSoftCommentAnnotationParam(name="exceptionMsg",type="String",isNecessary="Y",desc="当写卡机状态为4,即异常时的详细信息,非异常情况,该参数为0具体请参考,附录-写卡机异常信息定义")
	private String exceptionMsg;
	
	@ZteSoftCommentAnnotationParam(name="machineNo",type="String",isNecessary="Y",desc="写卡机编号")
	private String machineNo;

	public String getSysTime() {
		return sysTime;
	}

	public void setSysTime(String sysTime) {
		this.sysTime = sysTime;
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

	public String getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}
}
