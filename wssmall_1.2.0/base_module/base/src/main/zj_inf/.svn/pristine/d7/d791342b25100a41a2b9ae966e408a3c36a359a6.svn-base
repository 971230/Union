package zte.net.ecsord.params.ecaop.req;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import params.ZteRequest;
import params.ZteResponse;
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
import zte.net.ecsord.params.ecaop.req.vo.SimCardNoAOP;

/**
 * ecaop.trades.sell.mob.newu.open.sub
 * 
 * @author song.qi
 * @version 2018年5月17日
 * @see 开户处理提交(正式提交)
 */
public class OpenAccountSubmitReq extends ZteRequest<ZteResponse> {
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
	@ZteSoftCommentAnnotationParam(name = "订单来源", type = "String", isNecessary = "N", desc = "00：集团电商01：华盛天猫02：百度糯米03：乐视商城04：华为商城05：小米商城06：北分卖场")
	protected String netType;
	@ZteSoftCommentAnnotationParam(name = "卡信息资料", type = "List", isNecessary = "N", desc = "卡信息资料")
	protected List<SimCardNoAOP> simCardNo;
	@ZteSoftCommentAnnotationParam(name = "收费信息", type = "List", isNecessary = "N", desc = "feeInfo：收费信息")
	protected List<FeeInfoReqVo> feeInfo;
	@ZteSoftCommentAnnotationParam(name = "收费方式", type = "String", isNecessary = "N", desc = "0：未支付1：已支付")
	protected String payTag;
	@ZteSoftCommentAnnotationParam(name = "总费用正整数", type = "String", isNecessary = "Y", desc = "origTotalFee：总费用正整数，单位：厘")
	protected String origTotalFee;
	@ZteSoftCommentAnnotationParam(name = "发票号码", type = "FeeInfoReqVo", isNecessary = "Y", desc = "invoiceNo：发票号码")
	protected String invoiceNo;
	@ZteSoftCommentAnnotationParam(name = "客户支付信息", type = "PayInfoVo", isNecessary = "N", desc = "payInfo：客户支付信息")
	protected PayInfoVo payInfo;
	@ZteSoftCommentAnnotationParam(name = "受理单要求标记", type = "String", isNecessary = "Y", desc = "acceptanceReqTag：受理单要求标记  0：不要求受理单 1：要求受理单")
	protected String acceptanceReqTag;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
	protected List<ParamsVo> para;

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	private EmpOperInfoVo essOperInfo;

	public String getOperatorId() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.notNeedReqStrOrderId);
		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");

		if (orderTree != null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)) {
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(this.notNeedReqStrOrderId,
					"order_deal_method");

			// 自定义流程
			if ("2".equals(deal_method)) {
				// 线下方式受理业务，取传入的操作点
				this.operatorId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OPERATOR);

				if (StringUtils.isEmpty(this.operatorId)) {
					this.operatorId = getEssOperInfo().getEss_emp_id();
				}
			} else {
				// 线上方式受理取配置的操作点
				this.operatorId = getEssOperInfo().getEss_emp_id();
			}
		} else {

			if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
				this.operatorId = getEssOperInfo().getEss_emp_id();
			} else {
				this.operatorId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OPERATOR);
				if (StringUtils.isEmpty(this.operatorId) && !StringUtils.isEmpty(getEssOperInfo().getEss_emp_id())) {
					this.operatorId = getEssOperInfo().getEss_emp_id();
				}
			}
		}
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
		return "36";
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		this.city = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		if (!StringUtils.isEmpty(this.city) && this.city.length() == 6) {
			this.city = CommonDataFactory.getInstance().getOtherDictVodeValue("city", this.city);
		} else {
			if (!StringUtils.isEmpty(getEssOperInfo().getCity())) {
				this.city = getEssOperInfo().getCity();
			}
		}
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		this.district = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DISTRICT_CODE);
		if (StringUtils.isEmpty(this.district) && !StringUtils.isEmpty(getEssOperInfo().getDistrict())) {
			this.district = getEssOperInfo().getDistrict();
		}
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.notNeedReqStrOrderId);
		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");

		if (orderTree != null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)) {
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(this.notNeedReqStrOrderId,
					"order_deal_method");

			// 自定义流程
			if ("2".equals(deal_method)) {
				// 线下方式受理业务，取传入的操作点
				this.channelId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.ORDER_CHA_CODE);
				if (StringUtils.isEmpty(this.channelId)) {
					this.channelId = getEssOperInfo().getChannel_id();
				}
			} else {

				// 线上方式受理取配置的操作点
				this.channelId = getEssOperInfo().getChannel_id();
			}
		} else {

			if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
				this.channelId = getEssOperInfo().getChannel_id();
			} else {
				this.channelId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.ORDER_CHA_CODE);
				if (StringUtils.isEmpty(this.channelId) && !StringUtils.isEmpty(getEssOperInfo().getChannel_id())) {
					this.channelId = getEssOperInfo().getChannel_id();
				}
			}
		}
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.notNeedReqStrOrderId);
		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");

		if (orderTree != null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)) {
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(this.notNeedReqStrOrderId,
					"order_deal_method");

			// 自定义流程
			if ("2".equals(deal_method)) {
				// 线下方式受理业务，取传入的操作点
				this.channelType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.CHANNEL_TYPE);
				if (StringUtil.isEmpty(this.channelType)) {
					this.channelType = getEssOperInfo().getChannel_type();
				}
			} else {
				// 线上方式受理取配置的操作点
				this.channelType = getEssOperInfo().getChannel_type();
			}
		} else {

			if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
				this.channelType = getEssOperInfo().getChannel_type();
			} else {
				this.channelType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.CHANNEL_TYPE);
				if (StringUtil.isEmpty(this.channelType) && !StringUtils.isEmpty(getEssOperInfo().getChannel_type())) {
					this.channelType = getEssOperInfo().getChannel_type();
				}
			}
		}
		return this.channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getProvOrderId() {
		//provOrderId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);
		provOrderId = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getActive_no();
		return provOrderId;
	}

	public void setProvOrderId(String provOrderId) {
		this.provOrderId = provOrderId;
	}

	public String getOrdersId() {
		this.ordersId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		if (StringUtils.isEmpty(this.ordersId)) {
			this.ordersId = notNeedReqStrOrderId.replaceAll("[a-zA-Z]", "");
		}
		return this.ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getOpeSysType() {
		return EcsOrderConsts.AOP_OPE_SYS_TYPE_2;
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public List<SimCardNoAOP> getSimCardNo() {
		String iccid = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
		if (StringUtils.isEmpty(iccid)) {
			return null;
		}
		SimCardNoAOP vo = new SimCardNoAOP();
		vo.setSimId(iccid);
		simCardNo = new ArrayList<SimCardNoAOP>();
		simCardNo.add(vo);
		return simCardNo;
	}

	public void setSimCardNo(List<SimCardNoAOP> simCardNo) {
		this.simCardNo = simCardNo;
	}

	public List<FeeInfoReqVo> getFeeInfo() {
		List<AttrFeeInfoBusiRequest> list = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getFeeInfoBusiRequests();
		if (list == null || list.isEmpty() || list.size() == 0) {
			feeInfo = null;
		}
		feeInfo = new ArrayList<FeeInfoReqVo>();
		for (AttrFeeInfoBusiRequest req : list) {
			if (StringUtils.equals(EcsOrderConsts.BASE_YES_FLAG_1, req.getIs_aop_fee())) {
				FeeInfoReqVo vo = new FeeInfoReqVo();
				vo.setFeeId(req.getFee_item_code());
				vo.setFeeCategory(req.getFee_category());// 收费项科目
				vo.setFeeDes(req.getFee_item_name());

				DecimalFormat df = new DecimalFormat("#");

				String origFee = df.format(req.getO_fee_num());
				vo.setOrigFee(origFee);

				String reliefFee = df.format(req.getDiscount_fee());
				vo.setReliefFee(reliefFee);

				String realFee = df.format(req.getO_fee_num() - req.getN_fee_num());
				vo.setRealFee(realFee);

				vo.setReliefResult(StringUtils.isEmpty(req.getDiscount_reason()) ? "无" : req.getDiscount_reason());
				feeInfo.add(vo);
			}
		}

		return feeInfo;
	}

	public void setFeeInfo(List<FeeInfoReqVo> feeInfo) {
		this.feeInfo = feeInfo;
	}

	public String getPayTag() {
		payTag = "1";
		return payTag;
	}

	public void setPayTag(String payTag) {
		this.payTag = payTag;
	}

	public String getOrigTotalFee() {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		List<OrderItemBusiRequest> orderItemBusiRequests = orderTree.getOrderItemBusiRequests();
		OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
		origTotalFee = orderItemExtBusiRequest.getTotalFee();
		if (StringUtils.isEmpty(origTotalFee)) {
			return "0";
		}
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

	public PayInfoVo getPayInfo() {
		payInfo = new PayInfoVo();
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
		para = new ArrayList<ParamsVo>();
		// 4G的加参数
		ParamsVo paraVo = new ParamsVo();
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		if (StringUtils.equals(EcsOrderConsts.NET_TYPE_4G, net_type)) {
			paraVo.setParaId(EcsOrderConsts.AOP_4G_PARA_SPEED);
			paraVo.setParaValue(EcsOrderConsts.AOP_4G_PARA_SPEED_VALUE);
			para.add(paraVo);
		}
		if (para.isEmpty() || para.size() == 0) {
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
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "ecaop.trades.sell.mob.newu.open.sub";
	}

	public EmpOperInfoVo getEssOperInfo() {
		if (essOperInfo == null) {
			// essOperInfo =
			// CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
			String opercode = CommonDataFactory.getInstance().getOperatorCodeByOrderFrom(notNeedReqStrOrderId);
			String cityId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
			String otherCityCode = CommonDataFactory.getInstance().getOtherDictVodeValue("city", cityId);
			IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
			String sql = "select * from es_operator_rel_ext a where a.order_gonghao ='" + opercode + "' and a.city='" + otherCityCode + "' and a.source_from ='" + ManagerUtils.getSourceFrom() + "'";
			essOperInfo = support.queryForObject(sql, EmpOperInfoVo.class);
			if (null == essOperInfo) {// 未配置opercode
				essOperInfo = new EmpOperInfoVo();
			}
		}
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}

}
