/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

/**
 * @author songqi
 * @see 卡信息资料
 */
public class SimCardNo implements Serializable {

	private static final long serialVersionUID = -2821004926893253327L;
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "N", desc = "内部订单号 ")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "卡号", type = "String", isNecessary = "Y", desc = "卡号")
	private String iccid;// 卡号 只需要前19位
	@ZteSoftCommentAnnotationParam(name = "开户卡类型", type = "String", isNecessary = "Y", desc = "开户卡类型： 1：白卡 2：成卡")
	private String cardType;// 开户卡类型： 1：白卡 2：成卡

	public String getIccid() {
		iccid = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
		if (iccid.length() == 20) {
			iccid = iccid.substring(0, 19);
		}
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	// 默认白卡
	public String getCardType() {
		// cardType =
		// CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
		// AttrConsts.WHITE_CART_TYPE);
		if (StringUtils.isBlank(cardType)) {
			cardType = EcsOrderConsts.SIM_TYPE_BK;
		}
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

}
