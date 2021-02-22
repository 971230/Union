package params.resp;

import java.util.List;
import java.util.Map;

import params.ZteResponse;

public class ZbQueryOrderDetailResp extends ZteResponse{

	private String stepOnArea;//当前停留环节
	
	private String stepOnAreaDate;//当前环节流转时间
	
	private String zbLastModifyTime;//最后操作时间
	
	private String cancellationStatus;//退单状态
	
	private String dealDesc;//退单原因
	
	private List<Map<String, String>> operationRecord;

	public List<Map<String, String>> getOperationRecord() {
		return operationRecord;
	}

	public void setOperationRecord(List<Map<String, String>> operationRecord) {
		this.operationRecord = operationRecord;
	}

	public String getStepOnArea() {
		return stepOnArea;
	}

	public void setStepOnArea(String stepOnArea) {
		this.stepOnArea = stepOnArea;
	}

	public String getStepOnAreaDate() {
		return stepOnAreaDate;
	}

	public void setStepOnAreaDate(String stepOnAreaDate) {
		this.stepOnAreaDate = stepOnAreaDate;
	}

	public String getZbLastModifyTime() {
		return zbLastModifyTime;
	}

	public void setZbLastModifyTime(String zbLastModifyTime) {
		this.zbLastModifyTime = zbLastModifyTime;
	}

	public String getCancellationStatus() {
		return cancellationStatus;
	}

	public void setCancellationStatus(String cancellationStatus) {
		this.cancellationStatus = cancellationStatus;
	}

	public String getDealDesc() {
		return dealDesc;
	}

	public void setDealDesc(String dealDesc) {
		this.dealDesc = dealDesc;
	}
	
}
