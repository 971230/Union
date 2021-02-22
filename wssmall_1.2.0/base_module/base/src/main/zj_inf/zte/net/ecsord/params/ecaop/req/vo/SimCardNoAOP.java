package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author song.qi
 * @version 2018-05-18
 *
 */
public class SimCardNoAOP implements Serializable {

	private static final long serialVersionUID = -4462866241006071347L;
	@ZteSoftCommentAnnotationParam(name = "获取写卡数据业务流水", type = "String", isNecessary = "N", desc = "cardDataProcId：获取写卡数据业务流水")
	private String cardDataProcId;
	@ZteSoftCommentAnnotationParam(name = "成卡是USIM卡号，白卡是ICCID", type = "String", isNecessary = "Y", desc = "simId：如果是成卡是USIM卡号，如果是白卡是ICCID")
	private String simId;
	@ZteSoftCommentAnnotationParam(name = "新IMSI号", type = "String", isNecessary = "N", desc = "imsi：新IMSI号")
	private String imsi;
	@ZteSoftCommentAnnotationParam(name = "白卡类型", type = "String", isNecessary = "N", desc = "cardType：白卡类型  参考附录白卡类型说明")
	private String cardType;
	@ZteSoftCommentAnnotationParam(name = "白卡数据", type = "String", isNecessary = "N", desc = "cardData：白卡数据")
	private String cardData;
	@ZteSoftCommentAnnotationParam(name = "卡费", type = "String", isNecessary = "N", desc = "cardFee：卡费，单位：厘")
	private String cardFee;

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

	public String getCardFee() {
		return cardFee;
	}

	public void setCardFee(String cardFee) {
		this.cardFee = cardFee;
	}

}
