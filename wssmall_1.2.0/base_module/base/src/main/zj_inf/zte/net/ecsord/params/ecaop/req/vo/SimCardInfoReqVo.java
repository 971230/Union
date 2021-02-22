/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-05-05
 *
 */
public class SimCardInfoReqVo implements Serializable {
	
	private static final long serialVersionUID = -4462866241006071347L;
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
	@ZteSoftCommentAnnotationParam(name="卡容量",type="String",isNecessary="N",desc="capacityTypeCode：卡容量")
	private String capacityTypeCode;
	@ZteSoftCommentAnnotationParam(name="卡种类",type="String",isNecessary="N",desc="resKindCode：卡种类")
	private String resKindCode;
	
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;
	
	public String getCardDataProcId() {
		if (cardDataProcId == null || "".equals(cardDataProcId)) {
			cardDataProcId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PROC_ID);
		}
		if ("".equals(cardDataProcId)) {
			return null;
		}
		return cardDataProcId;
	}
	public void setCardDataProcId(String cardDataProcId) {
		this.cardDataProcId = cardDataProcId;
	}
	public String getSimId() {
		if (simId == null || "".equals(simId)) {
			simId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
		}
		if ("".equals(simId)) {
			return null;
		}
		return simId;
	}
	public void setSimId(String simId) {
		this.simId = simId;
	}
	public String getImsi() {
		if (imsi == null || "".equals(imsi)) {
			imsi = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SIMID);
		}
		if ("".equals(imsi)) {
			return null;
		}
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
		if (cardData == null || "".equals(cardData)) {
			List<OrderItemBusiRequest> orderItemBusiRequests = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests();
			if ((orderItemBusiRequests == null) || (orderItemBusiRequests.isEmpty())) {
				return null;
			}
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
			if (orderItemExtBusiRequest == null) {
				return null;
			}
			cardData = orderItemExtBusiRequest.getCard_data();
		}
		if ("".equals(cardData)) {
			return null;
		}
		return cardData;
	}
	public void setCardData(String cardData) {
		this.cardData = cardData;
	}
	public String getCardFee() {
		if (cardFee == null || "".equals(cardFee)) {
			cardFee = "1";
		}
		if ("".equals(cardFee)) {
			return null;
		}
		return cardFee;
	}
	public void setCardFee(String cardFee) {
		this.cardFee = cardFee;
	}
	public String getCapacityTypeCode() {
		if (capacityTypeCode == null || "".equals(capacityTypeCode)) {
			capacityTypeCode = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CAPACITY_TYPE_CODE);
		}
		if ("".equals(capacityTypeCode)) {
			return null;
		}
		return capacityTypeCode;
	}
	public void setCapacityTypeCode(String capacityTypeCode) {
		this.capacityTypeCode = capacityTypeCode;
	}
	public String getResKindCode() {
		if (resKindCode == null || "".equals(resKindCode)) {
			resKindCode = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.RES_KIND_CODE);
		}
		if ("".equals(resKindCode)) {
			return null;
		}
		return resKindCode;
	}
	public void setResKindCode(String resKindCode) {
		this.resKindCode = resKindCode;
	}
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
}
