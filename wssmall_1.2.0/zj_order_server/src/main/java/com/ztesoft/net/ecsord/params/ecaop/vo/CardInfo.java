package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


public class CardInfo implements Serializable{
	@ZteSoftCommentAnnotationParam(name = "大卡卡号", type = "String", isNecessary = "Y", desc = "卡信息->iccid:  ")
	private String iccid = "";
	@ZteSoftCommentAnnotationParam(name = "IMSI号(首次读卡必有)", type = "String", isNecessary = "Y", desc = "卡信息->imsi:  ")
	private String imsi = "";
	@ZteSoftCommentAnnotationParam(name = "读卡序列", type = "String", isNecessary = "Y", desc = "卡信息->procId:  ")
	private String procId = "";
	@ZteSoftCommentAnnotationParam(name = "交易流水（ESS生成返回）", type = "String", isNecessary = "Y", desc = "卡信息->activeId:  ")
	private String activeId = "";
	@ZteSoftCommentAnnotationParam(name = "制卡脚本，如果有多个脚本，用逗号“,”分隔，且要求顺序执行", type = "String", isNecessary = "Y", desc = "卡信息->scriptSeq:  ")
	private String scriptSeq = "";
	@ZteSoftCommentAnnotationParam(name = "白卡数据", type = "String", isNecessary = "Y", desc = "卡信息->cardData:  ")
	private String cardData = "";
	@ZteSoftCommentAnnotationParam(name = "卡容量", type = "String", isNecessary = "Y", desc = "卡信息->capacityTypeCode:  ")
	private String capacityTypeCode = "";
	@ZteSoftCommentAnnotationParam(name = "卡种类", type = "String", isNecessary = "Y", desc = "卡信息->resKindCode:  ")
	private String resKindCode = "";
	@ZteSoftCommentAnnotationParam(name = "白卡业务类型,参考附录白卡业务类型说明", type = "String", isNecessary = "Y", desc = "卡信息->cardType:  ")
	private String cardType = "";
	@ZteSoftCommentAnnotationParam(name = "卡实际归属渠道编码", type = "String", isNecessary = "Y", desc = "卡信息->channelId:  ")
	private String channelId = "";
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "String", isNecessary = "Y", desc = "卡信息->para:  ")
	private String para = "";
	@ZteSoftCommentAnnotationParam(name = "保留字段ID", type = "String", isNecessary = "Y", desc = "卡信息->paraId:  ")
	private String paraId = "";
	@ZteSoftCommentAnnotationParam(name = "保留字段值", type = "String", isNecessary = "Y", desc = "卡信息->paraValue:  ")
	private String paraValue = "";
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
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getPara() {
		return para;
	}
	public void setPara(String para) {
		this.para = para;
	}
	public String getParaId() {
		return paraId;
	}
	public void setParaId(String paraId) {
		this.paraId = paraId;
	}
	public String getParaValue() {
		return paraValue;
	}
	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}
	
	/**
	* get the value from Map
	*/
	public void fromMap(Map map) {
		setIccid((map.get("iccid") == null?"":(map.get("iccid").toString())));
		setImsi((map.get("imsi") == null?"":(map.get("imsi").toString())));
		setProcId((map.get("procId") == null?"":(map.get("procId").toString())));
		setActiveId((map.get("activeId") == null?"":(map.get("activeId").toString())));
		setScriptSeq((map.get("scriptSeq") == null?"":(map.get("scriptSeq").toString())));
		setCardData((map.get("cardData") == null?"":(map.get("cardData").toString())));
		setCapacityTypeCode((map.get("capacityTypeCode") == null?"":(map.get("capacityTypeCode").toString())));
		setResKindCode((map.get("resKindCode") == null?"":(map.get("resKindCode").toString())));
		setCardType((map.get("cardType") == null?"":(map.get("cardType").toString())));
		setChannelId((map.get("channelId") == null?"":(map.get("channelId").toString())));
		setPara((map.get("para") == null?"":(map.get("para").toString())));
		setParaId((map.get("paraId") == null?"":(map.get("paraId").toString())));
		setParaValue((map.get("paraValue") == null?"":(map.get("paraValue").toString())));
	}
	/**
	* set the value from Map
	*/
	public Map toMap() {
		Map map = new HashMap();
		map.put("iccid",getIccid());
		map.put("imsi",getImsi());
		map.put("procId",getProcId());
		map.put("activeId",getActiveId());
		map.put("scriptSeq",getScriptSeq());
		map.put("cardData",getCardData());
		map.put("capacityTypeCode",getCapacityTypeCode());
		map.put("resKindCode",getResKindCode());
		map.put("cardType",getCardType());
		map.put("channelId",getChannelId());
		map.put("para",getPara());
		map.put("paraId",getParaId());
		map.put("paraValue",getParaValue());
		return map;
	}
}
