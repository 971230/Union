package zte.net.ecsord.params.ems.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;
import zte.net.ecsord.params.ems.vo.EmsAssignId;
import zte.net.ecsord.params.ems.vo.EmsAssignIds;
import zte.net.ecsord.params.ems.vo.LogisticsNumber;

public class LogisticsNumberGetResp extends ZteResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "调用结果", type = "String", isNecessary = "Y", desc = "调用结果1:无错误0:有错误")
	private String resultCode;

	@ZteSoftCommentAnnotationParam(name = "错误描述", type = "String", isNecessary = "N", desc = "错误描述")
	private String errorDesc;

	@ZteSoftCommentAnnotationParam(name = "错误代码", type = "String", isNecessary = "N", desc = "错误代码")
	private String errorCode;

	@ZteSoftCommentAnnotationParam(name = "错误代码", type = "EmsAssignIds", isNecessary = "N", desc = "错误代码")
	private EmsAssignIds assignIds;

	

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

	public EmsAssignIds getAssignIds() {
		return assignIds;
	}

	public void setAssignIds(EmsAssignIds assignIds) {
		this.assignIds = assignIds;
	}

	

	

}
