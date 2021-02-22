package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;

public class CardDataQryResponse extends ZteResponse {

	private static final long serialVersionUID = 7770747046022954794L;

	@ZteSoftCommentAnnotationParam(name="大卡卡号",type="String",isNecessary="Y",desc="iccid：大卡卡号")
	private String iccid;
	@ZteSoftCommentAnnotationParam(name="IMSI号(首次读卡必有)",type="String",isNecessary="N",desc="imsi：IMSI号(首次读卡必有)")
	private String imsi;
	@ZteSoftCommentAnnotationParam(name="读卡序列",type="String",isNecessary="Y",desc="procId：读卡序列")
	private String procId;
	@ZteSoftCommentAnnotationParam(name="交易流水（ESS生成返回）",type="String",isNecessary="Y",desc="activeId：交易流水（ESS生成返回）")
	private String activeId;
	@ZteSoftCommentAnnotationParam(name="制卡脚本,如果有多个脚本，用逗号“,”分隔，且要求顺序执行",type="String",isNecessary="Y",desc="scriptSeq：制卡脚本,如果有多个脚本，用逗号“,”分隔，且要求顺序执行")
	private String scriptSeq;
	@ZteSoftCommentAnnotationParam(name="白卡数据",type="String",isNecessary="N",desc="cardData：白卡数据")
	private String cardData;
	@ZteSoftCommentAnnotationParam(name="卡容量",type="String",isNecessary="N",desc="capacityTypeCode：卡容量")
	private String capacityTypeCode;
	@ZteSoftCommentAnnotationParam(name="卡类型",type="String",isNecessary="N",desc="resKindCode：卡类型")
	private String resKindCode;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="N",desc="paras：保留字段")
	private List<ParamsVo> paras;

	@ZteSoftCommentAnnotationParam(name="返回编码",type="String",isNecessary="N",desc="code：返回编码")
	private String code;
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="N",desc="detail：返回描述")
	private String detail;
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public String getScriptSeq() {
		return scriptSeq;
	}
	public void setScriptSeq(String scriptSeq) {
		this.scriptSeq = scriptSeq;
	}
	public String getCardData() {
		return cardData;
	}
	public void setCardData(String cardData) {
		this.cardData = cardData;
	}
	public String getCapacityTypeCode() {
		return capacityTypeCode;
	}
	public void setCapacityTypeCode(String capacityTypeCode) {
		this.capacityTypeCode = capacityTypeCode;
	}
	public String getResKindCode() {
		return resKindCode;
	}
	public void setResKindCode(String resKindCode) {
		this.resKindCode = resKindCode;
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
