/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;
import zte.net.ecsord.params.ecaop.req.vo.PayInfoVo;
import zte.net.ecsord.utils.DataUtil;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

/**
 * @author ZX
 * @version 2015-05-05
 * @see 开户处理提交
 */
public class OpenDealSubmitReq extends ZteRequest {
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	protected String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "operatorId：操作员ID")
	protected String operatorId;
	@ZteSoftCommentAnnotationParam(name = "省份", type = "String", isNecessary = "Y", desc = "province：省份")
	protected String province;
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "city：地市")
	protected String city;
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "Y", desc = "district：区县")
	protected String district;
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	protected String channelId;
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型:参考附录渠道类型编码")
	protected String channelType;
	@ZteSoftCommentAnnotationParam(name = "ESS订单交易流水", type = "String", isNecessary = "Y", desc = "provOrderId：ESS订单交易流水  为正式提交时使用")
	protected String provOrderId;
	@ZteSoftCommentAnnotationParam(name = "外围系统订单ID", type = "String", isNecessary = "Y", desc = "ordersId：外围系统订单ID")
	protected String ordersId;
	@ZteSoftCommentAnnotationParam(name = "办理业务系统", type = "String", isNecessary = "N", desc = "opeSysType：办理业务系统 1：ESS 2：CBSS")
	protected String opeSysType;
	// @ZteSoftCommentAnnotationParam(name = "卡信息资料", type = "List", isNecessary
	// = "N", desc = "simCardNo：卡信息资料")
	// protected List<SimCardNoVo> simCardNo;
	@ZteSoftCommentAnnotationParam(name = "收费信息", type = "List", isNecessary = "N", desc = "feeInfo：收费信息")
	protected List<FeeInfoReqVo> feeInfo = new ArrayList<FeeInfoReqVo>();
	@ZteSoftCommentAnnotationParam(name = "总费用正整数", type = "String", isNecessary = "Y", desc = "origTotalFee：总费用正整数，单位：厘")
	protected String origTotalFee;
	@ZteSoftCommentAnnotationParam(name = "发票号码", type = "FeeInfoReqVo", isNecessary = "Y", desc = "invoiceNo：发票号码")
	protected String invoiceNo;
	@ZteSoftCommentAnnotationParam(name = "客户支付信息", type = "PayInfoVo", isNecessary = "N", desc = "payInfo：客户支付信息")
	protected PayInfoVo payInfo = new PayInfoVo();
	@ZteSoftCommentAnnotationParam(name = "受理单要求标记", type = "String", isNecessary = "Y", desc = "acceptanceReqTag：受理单要求标记  0：不要求受理单 1：要求受理单")
	protected String acceptanceReqTag;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
	protected List<ParamsVo> para = new ArrayList<ParamsVo>();

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	private EmpOperInfoVo essOperInfo;

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

	public String getProvOrderId() {
		provOrderId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);
		if (StringUtils.isEmpty(provOrderId)) {
			provOrderId = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getActive_no();
		}
		return provOrderId;
	}

	public void setProvOrderId(String provOrderId) {
		this.provOrderId = provOrderId;
	}

	public String getOrdersId() {
		ordersId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getOpeSysType() {
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		opeSysType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_operSys_by_mobileNet", net_type);
		String goods_cat_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_CAT_ID);
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String GOODSCATIDS = cacheUtil.getConfigInfo("GOODSCATIDS");
		if(GOODSCATIDS.contains(goods_cat_id)){
			opeSysType = "2";//add by song.qi AOP套餐变更
		}
		return opeSysType; // 业务类型
							// 1：ESS，2：CBSS（默认给2，根据号码网别判断,3G,4G现在办理业务都是在CBSS系统办理）
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	// public List<SimCardNoVo> getSimCardNo() {
	// return simCardNo;
	// }
	//
	// public void setSimCardNo(List<SimCardNoVo> simCardNo) {
	// this.simCardNo = simCardNo;
	// }

	public List<FeeInfoReqVo> getFeeInfo() {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		List<AttrFeeInfoBusiRequest> list = orderTree.getFeeInfoBusiRequests();
		for (AttrFeeInfoBusiRequest req : list) {
			if (StringUtils.equals(EcsOrderConsts.BASE_YES_FLAG_1, req.getIs_aop_fee())) {
				FeeInfoReqVo vo = new FeeInfoReqVo();
				vo.setFeeId(req.getFee_item_code());
				vo.setFeeCategory(req.getFee_category());// 收费项科目
				vo.setFeeDes(req.getFee_item_name());

				String origFee = req.getO_fee_num() * 1000 + "";
				vo.setOrigFee(origFee.substring(0, origFee.indexOf(".")));

				String reliefFee = req.getDiscount_fee() * 1000 + "";
				vo.setReliefFee(reliefFee.substring(0, reliefFee.indexOf(".")));

				String realFee = req.getN_fee_num() * 1000 + "";
				vo.setRealFee(realFee.substring(0, realFee.indexOf(".")));

				vo.setReliefResult(StringUtils.isEmpty(req.getDiscount_reason()) ? "无" : req.getDiscount_reason());
				feeInfo.add(vo);
			}
		}
		if (feeInfo != null && feeInfo.isEmpty()) {
			feeInfo = null;
		}
		return feeInfo;
	}

	public void setFeeInfo(List<FeeInfoReqVo> feeInfo) {
		this.feeInfo = feeInfo;
	}

	public String getOrigTotalFee() {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
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
		if (invoiceNo == "") {
			this.invoiceNo = null;
		}
	}

	public PayInfoVo getPayInfo() {
		payInfo.setPayType(EcsOrderConsts.BILL_TYPE_10);// 支付类型
		return payInfo;
	}

	public void setPayInfo(PayInfoVo payInfo) {
		this.payInfo = payInfo;
	}

	public String getAcceptanceReqTag() {
		acceptanceReqTag = EcsOrderConsts.IS_EASY_ACCOUNT_YES;
		return acceptanceReqTag;
	}

	public void setAcceptanceReqTag(String acceptanceReqTag) {
		this.acceptanceReqTag = acceptanceReqTag;
	}

	public List<ParamsVo> getPara() {
		// 4G的加参数
		ParamsVo paraVo = new ParamsVo();
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		if (StringUtils.equals(EcsOrderConsts.NET_TYPE_4G, net_type)) {
			paraVo.setParaId(EcsOrderConsts.AOP_4G_PARA_SPEED);
			paraVo.setParaValue(EcsOrderConsts.AOP_4G_PARA_SPEED_VALUE);
			para.add(paraVo);
		}
		if (para != null && para.isEmpty()) {
			para = null;
		}
		return para;
	}

	public void setPara(List<ParamsVo> para) {
		this.para = para;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.zte.unicomService.zb.openDealSubmit";
	}

	public EmpOperInfoVo getEssOperInfo() {
		if (essOperInfo == null) {
			essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
		}
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}

}
