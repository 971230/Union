package zte.net.card.params.resp.vo;

public class CardMachineVo {
	private static final long serialVersionUID = 1L;
	private String machineNo;//写卡机编号
	private String status;//状态
	private String errorMsg;//异常描述
	
	
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
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	};
	
}

