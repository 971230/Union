package zte.net.ecsord.params.wyg.resp;

import java.util.List;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.zb.vo.NumInfo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class NumberResourceQueryWYGResponse extends ZteBusiResponse {
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String resp_code;
	
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String resp_msg;
	

	@ZteSoftCommentAnnotationParam(name = "号码信息", type = "List", isNecessary = "Y", desc = "numInfo：号码信息")
	private List<NumInfo> numInfo;


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


	public List<NumInfo> getNumInfo() {
		return numInfo;
	}


	public void setNumInfo(List<NumInfo> numInfo) {
		this.numInfo = numInfo;
	}

	

}
