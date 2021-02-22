package zte.net.ecsord.params.sr.resp;

import params.ZteResponse;
import zte.net.ecsord.params.sr.vo.WritCardRGResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class WriteICCIDSRRGResponse  extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="sr_response",type="WritCardResp",isNecessary="Y",desc="body 与父类冲突，在最外层封装")
	private WritCardRGResp sr_response;

	public WritCardRGResp getSr_response() {
		return sr_response;
	}

	public void setSr_response(WritCardRGResp sr_response) {
		this.sr_response = sr_response;
	}
	
}
