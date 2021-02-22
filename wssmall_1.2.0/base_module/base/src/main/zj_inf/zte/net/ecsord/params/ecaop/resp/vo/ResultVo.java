package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ResultVo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "订单id", type = "String", isNecessary = "N", desc = "订单id")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name = "异常编码", type = "String", isNecessary = "N", desc = "异常编码")
	private String code;
	@ZteSoftCommentAnnotationParam(name = "异常描述", type = "String", isNecessary = "N", desc = "异常描述")
	private String detail;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "String", isNecessary = "N", desc = "保留字段")
	private Object para;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Object getPara() {
		return para;
	}

	public void setPara(Object para) {
		this.para = para;
	}

}
