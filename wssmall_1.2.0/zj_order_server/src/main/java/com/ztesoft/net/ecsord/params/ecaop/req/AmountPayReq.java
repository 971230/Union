package com.ztesoft.net.ecsord.params.ecaop.req;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

public class AmountPayReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "服务号码", type = "String", isNecessary = "Y", desc = "服务号码")
	private String service_num;
	@ZteSoftCommentAnnotationParam(name = "对端操作员", type = "String", isNecessary = "Y", desc = "对端操作员")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name = "对端操作点", type = "String", isNecessary = "Y", desc = "对端操作点")
	private String office_id;
	@ZteSoftCommentAnnotationParam(name = "缴费金额", type = "String", isNecessary = "Y", desc = "缴费金额")
	private String pay_amount;
	@ZteSoftCommentAnnotationParam(name = "记账日期", type = "String", isNecessary = "Y", desc = "记账日期，非空[8] 格式：YYYYMMDD，对账的依据，必须>=自然日")
	private String account_date;
	@ZteSoftCommentAnnotationParam(name = "缴费时间", type = "String", isNecessary = "Y", desc = "缴费时间，非空[14]YYYYMMDDHHMMSS，自然时间")
	private String pay_time;
	@ZteSoftCommentAnnotationParam(name = "支付方式", type = "String", isNecessary = "Y", desc = "支付方式1:现金 7:MISPOS  40:行销WOPAY沃钱包41: 行销ALIPAY支付宝42: 行销TENPAY微信43: 行销账户代扣44: 行销沃账户银行卡代扣")
	private String fund_source;
	@ZteSoftCommentAnnotationParam(name = "MISPOS流水", type = "String", isNecessary = "Y", desc = "MISPOS流水,fund_source为7时必传")
	private String trade_id;
	@ZteSoftCommentAnnotationParam(name = "外部流水号", type = "String", isNecessary = "Y", desc = "外部流水号，建议必传。对账以这个为准生成规则为office_id+yyyymmhhddmiss+序列号，最大长度30，不可重复")
	private String out_trade_id;
	@ZteSoftCommentAnnotationParam(name = "销售模式类型", type = "String", isNecessary = "Y", desc = "销售模式类型0：自营厅行销模式1：行销渠道直销模式")
	private String saleModType;
	@ZteSoftCommentAnnotationParam(name = "是否行销装备", type = "String", isNecessary = "Y", desc = "是否行销装备，0：否，1：是该字段没传的时候默认 0：否")
	private String markingTag;

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getService_num() {
		service_num = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getPhone_num();
		return service_num;
	}

	public void setService_num(String service_num) {
		this.service_num = service_num;
	}

	public String getOperator_id() {
		operator_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOffice_id() {
		office_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getPay_amount() {
		Double payamount = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderBusiRequest().getPaymoney();
		int newpayamount = (int) (payamount * 100);
		pay_amount = newpayamount + "";
		return pay_amount;
	}

	public void setPay_amount(String pay_amount) {
		this.pay_amount = pay_amount;
	}

	public String getAccount_date() {
		try {
			account_date = DateUtil.getTime3();
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account_date;
	}

	public void setAccount_date(String account_date) {
		this.account_date = account_date;
	}

	public String getPay_time() {
		try {
			pay_time = DateUtil.getTime5();
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public String getFund_source() {
		fund_source = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderPayBusiRequests().get(0).getPay_method();
		return fund_source;
	}

	public void setFund_source(String fund_source) {
		this.fund_source = fund_source;
	}

	public String getTrade_id() {
		return trade_id;
	}

	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
	}

	public String getOut_trade_id() {
		String outtradeid = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
		try {
			outtradeid = outtradeid + DateUtil.getTime7();
		} catch (FrameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out_trade_id = outtradeid;
		return out_trade_id;
	}

	public void setOut_trade_id(String out_trade_id) {
		this.out_trade_id = out_trade_id;
	}

	public String getSaleModType() {
		saleModType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SALE_MOD_TYPE);
		return saleModType;
	}

	public void setSaleModType(String saleModType) {
		this.saleModType = saleModType;
	}

	public String getMarkingTag() {
		markingTag = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.MARKING_TAG);
		return markingTag;
	}

	public void setMarkingTag(String markingTag) {
		this.markingTag = markingTag;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZJInfServices.amountPayReq";
	}
}
