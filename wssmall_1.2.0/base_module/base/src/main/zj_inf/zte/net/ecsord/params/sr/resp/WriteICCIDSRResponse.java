package zte.net.ecsord.params.sr.resp;

import params.ZteResponse;
import zte.net.ecsord.params.sr.vo.WritCardResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class WriteICCIDSRResponse  extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="sr_response",type="WritCardResp",isNecessary="Y",desc="body 与父类冲突，在最外层封装")
	private WritCardResp sr_response;

	public WritCardResp getSr_response() {
		return sr_response;
	}

	public void setSr_response(WritCardResp sr_response) {
		this.sr_response = sr_response;
	}
	
}
