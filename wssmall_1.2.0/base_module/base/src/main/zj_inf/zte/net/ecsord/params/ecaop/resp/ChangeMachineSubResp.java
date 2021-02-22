package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ChangeMachineSubResp extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name="总部订单交易流水",type="String",isNecessary="Y",desc="provOrderId：总部订单交易流水")
	private String provOrderId;
	@ZteSoftCommentAnnotationParam(name="BSS订单ID",type="String",isNecessary="N",desc="bssOrderId:BSS订单ID")
	private String bssOrderId;
	
	
	@ZteSoftCommentAnnotationParam(name="保留字段",type="ParamsVo",isNecessary="N",desc="paras：保留字段")
	private List<ParaVo> paras;
	@ZteSoftCommentAnnotationParam(name="返回编码",type="String",isNecessary="Y",desc="code：返回编码")
	private String code;
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="detail：返回描述")
	private String detail;
	
	
	public String getProvOrderId() {
		return provOrderId;
	}
	public void setProvOrderId(String provOrderId) {
		this.provOrderId = provOrderId;
	}
	public String getBssOrderId() {
		return bssOrderId;
	}
	public void setBssOrderId(String bssOrderId) {
		this.bssOrderId = bssOrderId;
	}
	public List<ParaVo> getParas() {
		return paras;
	}
	public void setParas(List<ParaVo> paras) {
		this.paras = paras;
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
	
	
	
}
