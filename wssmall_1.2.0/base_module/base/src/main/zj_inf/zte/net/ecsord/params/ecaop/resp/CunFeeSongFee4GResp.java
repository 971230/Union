package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;

import zte.net.ecsord.params.ecaop.req.vo.ParaVo;
import zte.net.ecsord.params.ecaop.resp.vo.FeeInfoRspVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class CunFeeSongFee4GResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="总部订单交易流水",type="String",isNecessary="Y",desc="provOrderId：总部订单交易流水")
	private String provOrderId;
	@ZteSoftCommentAnnotationParam(name="BSS订单ID",type="String",isNecessary="N",desc="bssOrderId:BSS订单ID")
	private String bssOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "收费信息", type = "OldUserBuyApplyFeeInfoVo", isNecessary = "Y", desc = "feeInfo：收费信息")
	private List<FeeInfoRspVo> feeInfo;
	@ZteSoftCommentAnnotationParam(name = "应收费用正整数单位：厘", type = "String", isNecessary = "Y", desc = "para：应收费用正整数单位：厘")
	private String totalFee;
	
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
	public List<FeeInfoRspVo> getFeeInfo() {
		return feeInfo;
	}
	public void setFeeInfo(List<FeeInfoRspVo> feeInfo) {
		this.feeInfo = feeInfo;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
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
