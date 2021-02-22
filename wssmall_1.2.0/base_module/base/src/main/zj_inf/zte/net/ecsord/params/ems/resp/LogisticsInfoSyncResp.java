package zte.net.ecsord.params.ems.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;
import zte.net.ecsord.params.ems.vo.EmsEerrorDetail;

public class LogisticsInfoSyncResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name = "调用结果", type = "String", isNecessary = "Y", desc = "调用结果：1-无错误，0-有错误")
	private String resultCode;
	@ZteSoftCommentAnnotationParam(name = "外层错误描述", type = "String", isNecessary = "N", desc = "外层错误描述")
	private String errorDesc;
	@ZteSoftCommentAnnotationParam(name = "外层错误代码", type = "String", isNecessary = "N", desc = "外层错误代码")
	private String errorCode;
	@ZteSoftCommentAnnotationParam(name = "外层错误代码", type = "String", isNecessary = "N", desc = "外层错误代码")
	private EmsEerrorDetail errorDetail;

	

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public EmsEerrorDetail getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(EmsEerrorDetail errorDetail) {
		this.errorDetail = errorDetail;
	}

}
