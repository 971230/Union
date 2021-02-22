package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.BSSFeeInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;

public class MessageSyncResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="Y",desc="")
	private String resp_code;
	
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="")
	private String resp_msg;

	public String getResp_code() {
		return resp_code;
	}

	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}

	public String getResp_msg() {
		return resp_msg;
	}

	public void setResp_msg(String resp_msg) {
		this.resp_msg = resp_msg;
	}
	
	
}
