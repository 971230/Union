package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ErrorListRspVo  implements Serializable{
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "省份23转4校验错误列表", type = "String", isNecessary = "Y", desc = "respCode:省份23转4校验错误列表")
	private String respCode;
	@ZteSoftCommentAnnotationParam(name = "省份23转4校验错误描述", type = "String", isNecessary = "Y", desc = "respCode:省份23转4校验错误描述")
	private String respDesc;

	
	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
	}

	public String getRespCode() {
		return respCode;
	}	
}
