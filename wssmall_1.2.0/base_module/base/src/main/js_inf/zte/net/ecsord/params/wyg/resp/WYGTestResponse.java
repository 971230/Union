package zte.net.ecsord.params.wyg.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class WYGTestResponse extends ZteResponse {

	private static final long serialVersionUID = 888046899474844984L;

	@ZteSoftCommentAnnotationParam(name = "返回报文", type = "String", isNecessary = "N", desc = "返回报文")
	private String returnMsg;

	@ZteSoftCommentAnnotationParam(name = "环节编码", type = "String", isNecessary = "N", desc = "环节编码")
	private String traceCode;

	@ZteSoftCommentAnnotationParam(name = "执行次数", type = "String", isNecessary = "N", desc = "执行次数")
	private String execCount;

	public String getExecCount() {
		return execCount;
	}

	public void setExecCount(String execCount) {
		this.execCount = execCount;
	}

	public String getTraceCode() {
		return traceCode;
	}

	public void setTraceCode(String traceCode) {
		this.traceCode = traceCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

}
