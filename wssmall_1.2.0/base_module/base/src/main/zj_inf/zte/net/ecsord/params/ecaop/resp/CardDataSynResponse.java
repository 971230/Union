package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;

public class CardDataSynResponse extends ZteResponse {

	private static final long serialVersionUID = 1129828342443562720L;

	@ZteSoftCommentAnnotationParam(name="发票号码",type="String",isNecessary="N",desc="taxNo：发票号码")
	private String taxNo;
	@ZteSoftCommentAnnotationParam(name="实收卡费，单位厘",type="String",isNecessary="N",desc="cardRealFee：实收卡费，单位厘")
	private String cardRealFee;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="N",desc="paras：保留字段")
	private List<ParamsVo> paras;

	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="N",desc="code：返回编码")
	private String code;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="N",desc="detail：返回描述")
	private String detail;
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
	public List<ParamsVo> getParas() {
		return paras;
	}
	public void setParas(List<ParamsVo> paras) {
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
