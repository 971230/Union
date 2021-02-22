package zte.net.card.params.req;

import params.ZteRequest;
import zte.net.card.params.resp.PCAutoOrderICCIDResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class PCAutoOrderICCIDReq extends ZteRequest<PCAutoOrderICCIDResp>{

	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="Y",desc="订单号")
	private String oredrId;
	@ZteSoftCommentAnnotationParam(name="iccid",type="String",isNecessary="Y",desc="iccid")
	private String iccid;
	@ZteSoftCommentAnnotationParam(name="自动写卡机队列编码",type="String",isNecessary="Y",desc="自动写卡机队列编码")
	private String queueCode;
	@ZteSoftCommentAnnotationParam(name="写卡机编码",type="String",isNecessary="Y",desc="写卡机编码")
	private String machineNo;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 584256917232632458L;

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.ecsord.setPCAutoOrderICCID";
	}

	public String getOredrId() {
		return oredrId;
	}

	public void setOredrId(String oredrId) {
		this.oredrId = oredrId;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getQueueCode() {
		return queueCode;
	}

	public void setQueueCode(String queueCode) {
		this.queueCode = queueCode;
	}

	public String getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}

	


}
