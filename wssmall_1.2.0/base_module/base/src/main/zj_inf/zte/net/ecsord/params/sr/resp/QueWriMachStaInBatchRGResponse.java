package zte.net.ecsord.params.sr.resp;

import params.ZteResponse;
import zte.net.ecsord.params.sr.vo.QueWriMachStaInBatchRGResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class QueWriMachStaInBatchRGResponse  extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="sr_response",type="RecycleCardResp",isNecessary="Y",desc="body 与父类冲突，在最外层封装")
	private QueWriMachStaInBatchRGResp sr_response;

	public QueWriMachStaInBatchRGResp getSr_response() {
		return sr_response;
	}

	public void setSr_response(QueWriMachStaInBatchRGResp sr_response) {
		this.sr_response = sr_response;
	}
	
}
