package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.FeeInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;
import zte.net.ecsord.params.ecaop.req.vo.PayInfoReqVo;
import zte.net.ecsord.params.ecaop.resp.OldUserRenActivitySubmitResp;

/**
 * 老用户续约提交
 * 2016-05-03
 * @author duan.shaochu
 *
 */
public class OldUserRenActivitySubmitReq extends
		ZteRequest<OldUserRenActivitySubmitResp> {
	
	@ZteSoftCommentAnnotationParam(name = "订单ID", type = "String", isNecessary = "Y", desc = "订单ID")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "操作员ID")
	private String operatorId;
	@ZteSoftCommentAnnotationParam(name = "省分", type = "String", isNecessary = "Y", desc = "省分")
	private String province;
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "地市")
	private String city;
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "Y", desc = "区县")
	private String district;
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "渠道编码")
	private String channelId;
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "渠道类型")
	private String channelType;
	@ZteSoftCommentAnnotationParam(name = "办理业务系统：1：ESS，3G？ 2：CBSS，4G？", type = "String", isNecessary = "Y", desc = "办理业务系统：1：ESS，3G？ 2：CBSS，4G？")
	private String opeSysType;
	@ZteSoftCommentAnnotationParam(name = "电子商城订单ID", type = "String", isNecessary = "Y", desc = "电子商城订单ID")
	private String ordersId;
	@ZteSoftCommentAnnotationParam(name = "协议流水，保证商城和ESS发起的单笔订单的一致性", type = "String", isNecessary = "N", desc = "协议流水，保证商城和ESS发起的单笔订单的一致性")
	private String essSubscribeId;
	@ZteSoftCommentAnnotationParam(name = "收取费用明细", type = "String", isNecessary = "Y", desc = "收取费用明细")
	private List<FeeInfoReqVo> feeInfo;
	@ZteSoftCommentAnnotationParam(name = "应收总金额", type = "String", isNecessary = "Y", desc = "应收总金额")
	private String origTotalFee;
	@ZteSoftCommentAnnotationParam(name = "发票号码", type = "String", isNecessary = "N", desc = "发票号码")
	private String invoiceNo;
	@ZteSoftCommentAnnotationParam(name = "客户支付信息", type = "String", isNecessary = "Y", desc = "客户支付信息")
	private PayInfoReqVo payInfo;
	@ZteSoftCommentAnnotationParam(name = "受理单要求标记0：不要求受理单，4G打不了？1：要求受理单，3G的可以打？", type = "String", isNecessary = "Y", desc = "")
	private String acceptanceReqTag;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "String", isNecessary = "N", desc = "保留字段")
	private List<ParaVo> para;
	private EmpOperInfoVo essOperInfo;

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "ecaop.trades.sell.mob.oldu.renActivity.sub";
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOperatorId() {
		return getEssOperInfo().getEss_emp_id();
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
		return getEssOperInfo().getProvince();
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return getEssOperInfo().getCity();
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return getEssOperInfo().getDistrict();
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		return getEssOperInfo().getChannel_id();
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		return getEssOperInfo().getChannel_type();
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getOpeSysType() {
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_operSys_by_mobileNet", net_type);
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	public String getOrdersId() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getEssSubscribeId() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);
	}

	public void setEssSubscribeId(String essSubscribeId) {
		this.essSubscribeId = essSubscribeId;
	}

	public List<FeeInfoReqVo> getFeeInfo() {
		List<FeeInfoReqVo> list = new ArrayList<FeeInfoReqVo>();
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		List<AttrFeeInfoBusiRequest> feeInfoList=orderTree.getFeeInfoBusiRequests();
		for(AttrFeeInfoBusiRequest req :feeInfoList){
			if(StringUtils.equals(EcsOrderConsts.BASE_YES_FLAG_1, req.getIs_aop_fee())){
				FeeInfoReqVo vo=new FeeInfoReqVo();
				vo.setFeeId(req.getFee_item_code());
				vo.setFeeCategory(req.getFee_category());//收费项科目
				vo.setFeeDes(req.getFee_item_name());
				
				String origFee = req.getO_fee_num()*100+"";
				vo.setOrigFee(origFee.substring(0, origFee.indexOf(".")));
				
				String reliefFee = req.getDiscount_fee()*100+"";
				vo.setReliefFee(reliefFee.substring(0, reliefFee.indexOf(".")));
				
				String realFee = req.getN_fee_num()*100+"";
				vo.setRealFee(realFee.substring(0, realFee.indexOf(".")));
				
				vo.setReliefResult(StringUtils.isEmpty(req.getDiscount_reason())?"无":req.getDiscount_reason());
				list.add(vo);
			}	
		}
		
		return list;
	}

	public void setFeeInfo(List<FeeInfoReqVo> feeInfo) {
		this.feeInfo = feeInfo;
	}

	public String getOrigTotalFee() {
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		List<OrderItemBusiRequest> orderItemBusiRequests =orderTree.getOrderItemBusiRequests();
		OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
		origTotalFee = orderItemExtBusiRequest.getTotalFee();
		return origTotalFee;
	}

	public void setOrigTotalFee(String origTotalFee) {
		this.origTotalFee = origTotalFee;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public PayInfoReqVo getPayInfo() {
		PayInfoReqVo vo = new PayInfoReqVo();
		vo.setPayType(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BILL_TYPE)); // 付费方式
		vo.setPayOrg(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_PROVIDER_NAME)); // 支付机构名称
		vo.setPayNum(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BANK_ACCOUNT)); // 支付账号
		return vo;
	}

	public void setPayInfo(PayInfoReqVo payInfo) {
		this.payInfo = payInfo;
	}

	public String getAcceptanceReqTag() {
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		//目前4G老用户还打不了
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_acceptanceReqTag", net_type);
	}

	public void setAcceptanceReqTag(String acceptanceReqTag) {
		this.acceptanceReqTag = acceptanceReqTag;
	}

	public List<ParaVo> getPara() {
		if (CommonDataFactory.checkFieldValueNull(para))return null;
		return para;
	}

	public void setPara(List<ParaVo> para) {
		this.para = para;
	}

	public EmpOperInfoVo getEssOperInfo() {
		if(essOperInfo==null){
			essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
		}
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}

	
	
}
