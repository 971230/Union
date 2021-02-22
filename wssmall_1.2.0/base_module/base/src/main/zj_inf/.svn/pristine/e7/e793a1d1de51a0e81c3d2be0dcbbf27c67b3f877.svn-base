package zte.net.ecsord.params.bss.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.bss.resp.FeeInformResp;
import zte.net.ecsord.params.bss.vo.GDBssSocketHead;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.api.internal.utils.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 商城收费、退款成功触发接口 请求对象
 */
public class FeeInformReq extends ZteRequest<FeeInformResp> {

	private static final long serialVersionUID = 6918330370110594378L;

	@ZteSoftCommentAnnotationParam(name = "socket报文头信息", type = "String", isNecessary = "Y", desc = "socket报文头信息")
	private GDBssSocketHead gDBssSocketHead;

	@ZteSoftCommentAnnotationParam(name = "收费工号", type = "String", isNecessary = "Y", desc = "收费工号")
	private String paraCode1;

	@ZteSoftCommentAnnotationParam(name = "支付时间", type = "String", isNecessary = "Y", desc = "支付时间")
	private String acceptDate;

	@ZteSoftCommentAnnotationParam(name = "订单金额，单位：分", type = "String", isNecessary = "Y", desc = "订单金额，单位：分")
	private String depositMoney;

	@ZteSoftCommentAnnotationParam(name = "订单流水", type = "String", isNecessary = "Y", desc = "订单流水")
	private String tradeId;

	@ZteSoftCommentAnnotationParam(name = "2/3/4G", type = "String", isNecessary = "Y", desc = "2/3/4G")
	private String paraCode2;

	@ZteSoftCommentAnnotationParam(name = "0-开户 1-返销", type = "String", isNecessary = "Y", desc = "0-开户 1-返销")
	private String xTag;

	@ZteSoftCommentAnnotationParam(name = "0-在线支付（默认）", type = "String", isNecessary = "Y", desc = "0-在线支付（默认）")
	private String modifyTag;

	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;

	public GDBssSocketHead getgDBssSocketHead() {
		return gDBssSocketHead;
	}

	public void setgDBssSocketHead(GDBssSocketHead gDBssSocketHead) {
		this.gDBssSocketHead = gDBssSocketHead;
	}

	public String getParaCode1() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BSS_OPERATOR);
	}

	public void setParaCode1(String paraCode1) {
		this.paraCode1 = paraCode1;
	}

	public String getAcceptDate() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_TIME).replaceAll("\\D*", "");
	}

	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
	}

	public String getDepositMoney() {
		String moneyStr = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_REAL_FEE);
		if (!StringUtils.isEmpty(moneyStr)) {
			return String.valueOf((int) Double.parseDouble(moneyStr) * 100);
		}
		return moneyStr;
	}

	public void setDepositMoney(String depositMoney) {
		this.depositMoney = depositMoney;
	}

	public String getTradeId() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_TID);
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getParaCode2() {
		String phoneNum = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
		return CommonDataFactory.getInstance().getNumberSpec(phoneNum, SpecConsts.NUMERO_NO_GEN);
	}

	public void setParaCode2(String paraCode2) {
		this.paraCode2 = paraCode2;
	}

	public String getxTag() {
		return xTag;
	}

	public void setxTag(String xTag) {
		this.xTag = xTag;
	}

	public String getModifyTag() {
		return "0";
	}

	public void setModifyTag(String modifyTag) {
		this.modifyTag = modifyTag;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.bss.feeInform";
	}

}
