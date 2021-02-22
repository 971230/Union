package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.api.ApiRuleException;
import params.ZteRequest;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub.FeeInfo;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub.Para;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_sub.PayInfo;

public class AopBrdOpenSubReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "办理业务系统：", type = "String", isNecessary = "N", desc = "办理业务系统：")
	private String opeSysType;

	@ZteSoftCommentAnnotationParam(name = "省分", type = "String", isNecessary = "Y", desc = "省分")
	private String province;

	@ZteSoftCommentAnnotationParam(name = "收费信息*（ESS从BSS获取到的）", type = "FeeInfo", isNecessary = "N", desc = "收费信息*（ESS从BSS获取到的）")
	private List<FeeInfo> feeInfo;

	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "Para", isNecessary = "N", desc = "保留字段")
	private List<Para> para;

	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "地市")
	private String city;

	@ZteSoftCommentAnnotationParam(name = "BSS订单交易流水  为正式提交时使用", type = "String", isNecessary = "Y", desc = "BSS订单交易流水  为正式提交时使用")
	private String provOrderId;

	@ZteSoftCommentAnnotationParam(name = "总费用正整数，单位：厘", type = "String", isNecessary = "Y", desc = "总费用正整数，单位：厘")
	private String origTotalFee;

	@ZteSoftCommentAnnotationParam(name = "总部订单编号，非北六商城必须传", type = "String", isNecessary = "N", desc = "总部订单编号，非北六商城必须传")
	private String orderNo;

	@ZteSoftCommentAnnotationParam(name = "订单系统内部订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单号")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "操作员ID")
	private String operatorId;

	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "渠道类型")
	private String channelType;

	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "Y", desc = "区县")
	private String district;

	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "渠道编码")
	private String channelId;

	@ZteSoftCommentAnnotationParam(name = "客户支付信息", type = "PayInfo", isNecessary = "Y", desc = "客户支付信息")
	private PayInfo payInfo;

	public String getOpeSysType() {
		return this.opeSysType;
	}

	public void setOpeSysType(String v) {
		this.opeSysType = v;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String v) {
		this.province = v;
	}

	public List<FeeInfo> getFeeInfo() {
		return this.feeInfo;
	}

	public void setFeeInfo(List<FeeInfo> v) {
		this.feeInfo = v;
	}

	public List<Para> getPara() {
		return this.para;
	}

	public void setPara(List<Para> v) {
		this.para = v;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String v) {
		this.city = v;
	}

	public String getProvOrderId() {
		return this.provOrderId;
	}

	public void setProvOrderId(String v) {
		this.provOrderId = v;
	}

	public String getOrigTotalFee() {
		return this.origTotalFee;
	}

	public void setOrigTotalFee(String v) {
		this.origTotalFee = v;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(String v) {
		this.orderNo = v;
	}

	public String getNotNeedReqStrOrderId() {
		return this.notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String v) {
		this.notNeedReqStrOrderId = v;
	}

	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(String v) {
		this.operatorId = v;
	}

	public String getChannelType() {
		return this.channelType;
	}

	public void setChannelType(String v) {
		this.channelType = v;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String v) {
		this.district = v;
	}

	public String getChannelId() {
		return this.channelId;
	}

	public void setChannelId(String v) {
		this.channelId = v;
	}

	public PayInfo getPayInfo() {
		return this.payInfo;
	}

	public void setPayInfo(PayInfo v) {
		this.payInfo = v;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "ecaop.trades.sell.brd.sinp.open.sub_aop";
	}
}
