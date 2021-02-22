package com.ztesoft.net.ecsord.params.ecaop.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.NewMSGPKGVO;

public class BroadbandRefundResp extends ZteResponse{

	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private NewMSGPKGVO MSGPKG;

	public NewMSGPKGVO getMSGPKG() {
		return MSGPKG;
	}

	public void setMSGPKG(NewMSGPKGVO mSGPKG) {
		MSGPKG = mSGPKG;
	}
	
}
