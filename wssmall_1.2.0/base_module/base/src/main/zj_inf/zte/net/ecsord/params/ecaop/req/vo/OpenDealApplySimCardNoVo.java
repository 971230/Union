/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-05-05
 *
 */
public class OpenDealApplySimCardNoVo implements Serializable {
	
	@ZteSoftCommentAnnotationParam(name="获取写卡数据业务流水",type="String",isNecessary="N",desc="cardDataProcId：获取写卡数据业务流水")
	private String cardDataProcId;
	@ZteSoftCommentAnnotationParam(name="成卡是USIM卡号，白卡是ICCID",type="String",isNecessary="Y",desc="simId：如果是成卡是USIM卡号，如果是白卡是ICCID")
	private String simId;		
	@ZteSoftCommentAnnotationParam(name="新IMSI号",type="String",isNecessary="N",desc="imsi：新IMSI号")
	private String imsi;		
	@ZteSoftCommentAnnotationParam(name="白卡类型",type="String",isNecessary="N",desc="cardType：白卡类型  参考附录白卡类型说明")
	private String cardType;		
	@ZteSoftCommentAnnotationParam(name="白卡数据",type="String",isNecessary="N",desc="cardData：白卡数据")
	private String cardData;		
	@ZteSoftCommentAnnotationParam(name="卡费",type="String",isNecessary="N",desc="cardFee：卡费，单位：厘")
	private String cardFee;
	
	public String getCardDataProcId() {
		if (StringUtils.isBlank(cardDataProcId)) return null;
		return cardDataProcId;
	}
	public void setCardDataProcId(String cardDataProcId) {
		this.cardDataProcId = cardDataProcId;
	}
	public String getSimId() {
		if (StringUtils.isBlank(simId)) return null;
		return simId;
	}
	public void setSimId(String simId) {
		this.simId = simId;
	}
	public String getImsi() {
		if (StringUtils.isBlank(imsi)) return null;
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getCardType() {
		if (StringUtils.isBlank(cardType)) return null;
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardData() {
		if (StringUtils.isBlank(cardData)) return null;
		return cardData;
	}
	public void setCardData(String cardData) {
		this.cardData = cardData;
	}
	public String getCardFee() {
		if (StringUtils.isBlank(cardFee)) return null;
		return cardFee;
	}
	public void setCardFee(String cardFee) {
		this.cardFee = cardFee;
	}
	
}
