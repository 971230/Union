package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class QueWriMachStaInBatchDataRGBodyReq implements Serializable {

	@ZteSoftCommentAnnotationParam(name="machineNo",type="String",isNecessary="Y",desc="param：参数,队列标识")
	private List<MachineNoRG> param;

	public List<MachineNoRG> getParam() {
		return param;
	}

	public void setParam(List<MachineNoRG> param) {
		this.param = param;
	}
	
}
