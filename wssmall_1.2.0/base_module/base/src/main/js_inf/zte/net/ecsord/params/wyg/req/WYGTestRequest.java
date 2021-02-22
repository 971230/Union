package zte.net.ecsord.params.wyg.req;

import params.ZteRequest;
import zte.net.ecsord.params.wyg.resp.WYGTestResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class WYGTestRequest extends ZteRequest<WYGTestResponse> {

	private static final long serialVersionUID = 7948477231938689160L;

	@ZteSoftCommentAnnotationParam(name = "环节编码", type = "String", isNecessary = "N", desc = "环节编码")
	private String traceCode;

	@ZteSoftCommentAnnotationParam(name = "外部单号", type = "String", isNecessary = "N", desc = "外部单号")
	private String outOrderId;

	@ZteSoftCommentAnnotationParam(name = "内部单号", type = "String", isNecessary = "N", desc = "内部单号")
	private String orderId;
	
	@ZteSoftCommentAnnotationParam(name = "是否可传外部单号", type = "String", isNecessary = "N", desc = "是否可传外部单号 false:不可传环境,true可传环境")
	private Boolean isOutOrderId;
	

	public Boolean getIsOutOrderId() {
		return isOutOrderId;
	}

	public void setIsOutOrderId(Boolean isOutOrderId) {
		this.isOutOrderId = isOutOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOutOrderId() {
		return outOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}

	public String getTraceCode() {
		return traceCode;
	}

	public void setTraceCode(String traceCode) {
		this.traceCode = traceCode;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "";
		// return "com.zte.unicomService.wyg.wygtest";
	}

}
