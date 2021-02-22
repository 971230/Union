package zte.net.card.params.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.card.params.resp.vo.MachineGroupStateVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class QueryQueueAlowCountResp  extends ZteResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name="写卡机状态",type="String",isNecessary="Y",desc="写卡机编号")
	private List<MachineGroupStateVo> machineGroupStateList;

	public List<MachineGroupStateVo> getMachineGroupStateList() {
		return machineGroupStateList;
	}

	public void setMachineGroupStateList(
			List<MachineGroupStateVo> machineGroupStateList) {
		this.machineGroupStateList = machineGroupStateList;
	}

	
	
	
}
