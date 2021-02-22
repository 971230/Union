/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author zengxianlian
 * @version 2016-03-15
 *
 */
public class BSSSimCardNoVo implements Serializable {
	
	private String cardDataProcId;
	private String simId;		
	private String imsi;		
	private String cardType;		
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
