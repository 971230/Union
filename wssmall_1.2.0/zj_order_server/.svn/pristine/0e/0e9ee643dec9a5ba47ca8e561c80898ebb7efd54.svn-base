package com.ztesoft.net.ecsord.params.ecaop.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;

public class CardDataSyncCBssResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="调用结果",type="String",isNecessary="Y",desc="200:表示成功，其他具体见附录3错误码列表")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="返回描述")
	private String detail;
	
	@ZteSoftCommentAnnotationParam(name="税控码",type="String",isNecessary="Y",desc="税控码")
	private String taxNo;
	
	@ZteSoftCommentAnnotationParam(name="实收卡费，单位厘",type="String",isNecessary="Y",desc="实收卡费，单位厘")
	private String cardRealFee;
	
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
	private List<BSSParaVo> para;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getCardRealFee() {
		return cardRealFee;
	}

	public void setCardRealFee(String cardRealFee) {
		this.cardRealFee = cardRealFee;
	}

	public List<BSSParaVo> getPara() {
		return para;
	}

	public void setPara(List<BSSParaVo> para) {
		this.para = para;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getDetail() {
		return detail;
	}
	
}
