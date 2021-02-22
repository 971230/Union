package zte.net.ecsord.params.spec.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class PCodeGetResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="合约编码",type="String",isNecessary="Y",desc="合约编码")
	private String p_code;

	public String getP_code() {
		return p_code;
	}

	public void setP_code(String p_code) {
		this.p_code = p_code;
	}
}
