package zte.net.ecsord.params.sr.resp;

import params.ZteResponse;
import zte.net.ecsord.params.sr.vo.RecycleCardResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class RevertCardResponse  extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="sr_response",type="RecycleCardResp",isNecessary="Y",desc="body 与父类冲突，在最外层封装")
	private RecycleCardResp sr_response;

	public RecycleCardResp getSr_response() {
		return sr_response;
	}

	public void setSr_response(RecycleCardResp sr_response) {
		this.sr_response = sr_response;
	}
	
}
