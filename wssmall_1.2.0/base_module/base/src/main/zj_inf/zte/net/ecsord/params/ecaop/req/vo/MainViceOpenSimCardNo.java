package zte.net.ecsord.params.ecaop.req.vo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class MainViceOpenSimCardNo {

	@ZteSoftCommentAnnotationParam(name = "获取写卡数据业务流水", type = "String", isNecessary = "Y", desc = "")
	private String cardDataProcId;
	@ZteSoftCommentAnnotationParam(name = "卡号", type = "String", isNecessary = "Y", desc = "")
	private String simId;
	@ZteSoftCommentAnnotationParam(name = "新IMSI号", type = "String", isNecessary = "Y", desc = "")
	private String imsi;
	@ZteSoftCommentAnnotationParam(name = "白卡类型参考附录白卡类型说明", type = "String", isNecessary = "Y", desc = "")
	private String cardType;
	@ZteSoftCommentAnnotationParam(name = "白卡数据", type = "String", isNecessary = "Y", desc = "")
	private String cardData;

	public String getCardDataProcId() {
		return cardDataProcId;
	}

	public void setCardDataProcId(String cardDataProcId) {
		this.cardDataProcId = cardDataProcId;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardData() {
		return cardData;
	}

	public void setCardData(String cardData) {
		this.cardData = cardData;
	}

}
