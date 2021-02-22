package zte.params.member.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.app.base.core.model.Ask;

public class AskGetResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="问题信息",type="String",isNecessary="Y",desc="问题信息")
	private Ask ask;

	public Ask getAsk() {
		return ask;
	}

	public void setAsk(Ask ask) {
		this.ask = ask;
	}
	
}
