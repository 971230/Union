package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import params.ZteRequest;
import params.ZteResponse;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderRealNameInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.MainViceOpenAcctInfo;
import zte.net.ecsord.params.ecaop.req.vo.MainViceOpenActivityInfo;
import zte.net.ecsord.params.ecaop.req.vo.MainViceOpenCustInfo;
import zte.net.ecsord.params.ecaop.req.vo.MainViceOpenNiceNumberInfo;
import zte.net.ecsord.params.ecaop.req.vo.MainViceOpenPackageElement;
import zte.net.ecsord.params.ecaop.req.vo.MainViceOpenPara;
import zte.net.ecsord.params.ecaop.req.vo.MainViceOpenProductInfo;
import zte.net.ecsord.params.ecaop.req.vo.MainViceOpenRecomInfo;
import zte.net.ecsord.params.ecaop.req.vo.MainViceOpenSimCardNo;
import zte.net.ecsord.utils.SpecUtils;

/**
 * @see 支持主卡合约终端类一键开户，副卡和主卡必须是同一个客户id，主卡开户时，默认下发“互打免费”产品，副卡开户时默认下发副卡的产品和对应服务。
 *      开副卡时，需要主卡竣工。（增加主卡未激活时开副卡流程）
 * @see 暂时仅支持-CBSS新用户副卡开户申请
 * 
 */
public class MainViceOpenReq extends ZteRequest<ZteResponse> {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "订单ID", type = "String", isNecessary = "Y", desc = "订单ID")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "操作员ID")
	private String operatorId;
	@ZteSoftCommentAnnotationParam(name = "省分（客户归属）", type = "String", isNecessary = "Y", desc = "省分（客户归属）")
	private String province;
	@ZteSoftCommentAnnotationParam(name = "地市（客户归属）", type = "String", isNecessary = "Y", desc = "地市（客户归属）")
	private String city;
	@ZteSoftCommentAnnotationParam(name = "区县（客户归属）", type = "String", isNecessary = "Y", desc = "区县（客户归属）")
	private String district;
	@ZteSoftCommentAnnotationParam(name = "渠道编码（联通分配）", type = "String", isNecessary = "Y", desc = "渠道编码（联通分配）")
	private String channelId;
	@ZteSoftCommentAnnotationParam(name = "渠道类型（联通分配）", type = "String", isNecessary = "Y", desc = "渠道类型（联通分配）")
	private String channelType;
	@ZteSoftCommentAnnotationParam(name = "渠道标示", type = "String", isNecessary = "N", desc = "渠道标示")
	private String eModeCode;
	@ZteSoftCommentAnnotationParam(name = "电子商城订单ID", type = "String", isNecessary = "Y", desc = "电子商城订单ID")
	private String ordersId;
	@ZteSoftCommentAnnotationParam(name = "主副卡标示：0：主卡；1：副卡", type = "String", isNecessary = "Y", desc = "")
	private String mainNumberTag;
	@ZteSoftCommentAnnotationParam(name = "是否行销装备，0：否，1：是该字段没传的时候默认 0：否", type = "String", isNecessary = "N", desc = "")
	private String markingTag;
	@ZteSoftCommentAnnotationParam(name = "销售模式类型0：自营厅行销模式1：行销渠道直销模式", type = "String", isNecessary = "N", desc = "")
	private String saleModType;
	@ZteSoftCommentAnnotationParam(name = "订单是否同步到电子渠道激活1：同步默认该字段不传", type = "String", isNecessary = "N", desc = "")
	private String isAfterActivation;
	@ZteSoftCommentAnnotationParam(name = "副卡关系类型（不传默认0）0：普通全国产品副卡2：全国冰激凌产品副卡3：冰激凌‘沃+副卡’产品副卡", type = "String", isNecessary = "N", desc = "")
	private String secCardRoleType;
	@ZteSoftCommentAnnotationParam(name = "照片标识：1：已拍照（拍照且照片上传无纸化可以传1，不满足条件不传）", type = "String", isNecessary = "N", desc = "")
	private String photoTag;
	@ZteSoftCommentAnnotationParam(name = "实人认证标志：1：实名-静态  2：实名-动态（通过人脸比对的可以传1，通过人脸比对及活体认证的可以传2，不满足条件不传）", type = "String", isNecessary = "N", desc = "")
	private String realPersonTag;
	@ZteSoftCommentAnnotationParam(name = "客户信息", type = "String", isNecessary = "N", desc = "")
	private MainViceOpenCustInfo custInfo;
	@ZteSoftCommentAnnotationParam(name = "开户号码", type = "String", isNecessary = "N", desc = "")
	private String serialNumber;
	@ZteSoftCommentAnnotationParam(name = "副卡开户时对应的主卡号码", type = "String", isNecessary = "N", desc = "")
	private String mainNumber;
	@ZteSoftCommentAnnotationParam(name = "业务类型： 1：号卡类业务 2：合约类业务 3：上网卡", type = "String", isNecessary = "N", desc = "")
	private String bipType;
	@ZteSoftCommentAnnotationParam(name = "受理类型 1：后付费  2：预付费 3：准预付费", type = "String", isNecessary = "N", desc = "")
	private String serType;
	@ZteSoftCommentAnnotationParam(name = "靓号信息", type = "0-1", isNecessary = "N", desc = "")
	private List<MainViceOpenNiceNumberInfo> niceNumberInfo;
	@ZteSoftCommentAnnotationParam(name = "卡信息资料", type = "String", isNecessary = "N", desc = "")
	private List<MainViceOpenSimCardNo> simCardNo;
	@ZteSoftCommentAnnotationParam(name = "开户时选择的产品信息（组合版用，共享版不需要）", type = "1-n", isNecessary = "Y", desc = "")
	private List<MainViceOpenProductInfo> productInfo;
	@ZteSoftCommentAnnotationParam(name = "开户时活动信息", type = "MainViceOpenActivityInfo", isNecessary = "Y", desc = "")
	private List<MainViceOpenActivityInfo> activityInfo;
	@ZteSoftCommentAnnotationParam(name = "帐户信息", type = "MainViceOpenAcctInfo", isNecessary = "Y", desc = "")
	private List<MainViceOpenAcctInfo> acctInfo;
	@ZteSoftCommentAnnotationParam(name = "帐户信息", type = "0-1", isNecessary = "Y", desc = "")
	private MainViceOpenRecomInfo recomInfo;
	@ZteSoftCommentAnnotationParam(name = "领用代理商工号（折扣销售必传）", type = "String", isNecessary = "N", desc = "")
	private String batDeveStaffId;
	@ZteSoftCommentAnnotationParam(name = "领用代理商部门（折扣销售必传）", type = "String", isNecessary = "N", desc = "")
	private String batDeveDepatId;
	@ZteSoftCommentAnnotationParam(name = "减免金额（折扣销售必传，单位：厘）", type = "String", isNecessary = "N", desc = "")
	private String discountFee;
	@ZteSoftCommentAnnotationParam(name = "速率0：100M1：42M2：21M不传的话，默认1", type = "String", isNecessary = "N", desc = "")
	private String phoneSpeedLevel;
	@ZteSoftCommentAnnotationParam(name = "是否压单标示0:不压单1:压单该字段没传的时候默认不压单", type = "String", isNecessary = "N", desc = "")
	private String delayTag;
	@ZteSoftCommentAnnotationParam(name = "代理商扣款标示0：不扣款1：扣款该字段没传的时候默认扣款", type = "String", isNecessary = "N", desc = "")
	private String deductionTag;
	@ZteSoftCommentAnnotationParam(name = "集团标识0：非集团用户1：集团用户", type = "String", isNecessary = "N", desc = "")
	private String groupFlag;
	@ZteSoftCommentAnnotationParam(name = "集团ID", type = "String", isNecessary = "N", desc = "")
	private String groupId;
	@ZteSoftCommentAnnotationParam(name = "收入归集集团ID，不传默认取cBSS集团信息查询接口返回第一条", type = "String", isNecessary = "N", desc = "")
	private String cBSSGroupId;
	@ZteSoftCommentAnnotationParam(name = "备注", type = "String", isNecessary = "N", desc = "")
	private String remark;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "MainViceOpenPara", isNecessary = "Y", desc = "")
	private List<MainViceOpenPara> para;// NEWKSVIP 大王卡超级会员开户时传此字段

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	private EmpOperInfoVo essOperInfo;

	public EmpOperInfoVo getEssOperInfo() {
		if (essOperInfo == null) {
			// essOperInfo =
			// CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();//会给默认值
			String opercode = CommonDataFactory.getInstance().getOperatorCodeByOrderFrom(notNeedReqStrOrderId);
			String cityId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
					AttrConsts.ORDER_CITY_CODE);
			String otherCityCode = CommonDataFactory.getInstance().getOtherDictVodeValue("city", cityId);
			IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
			String sql = "select * from es_operator_rel_ext a where a.order_gonghao ='" + opercode + "' and a.city='"
					+ otherCityCode + "' and a.source_from ='" + ManagerUtils.getSourceFrom() + "'";
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

	public String getOperatorId() {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.notNeedReqStrOrderId);
		String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");
		
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)){
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(this.notNeedReqStrOrderId,
					"order_deal_method");
			
			//自定义流程
			if("2".equals(deal_method)){
				this.operatorId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OPERATOR);
				if (StringUtils.isEmpty(this.operatorId)) {
					this.operatorId = getEssOperInfo().getEss_emp_id();
				}
			}else{
				this.operatorId = getEssOperInfo().getEss_emp_id();
			}
		}else{
			if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
				this.operatorId = getEssOperInfo().getEss_emp_id();
			} else {
				this.operatorId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.OUT_OPERATOR);
				if (StringUtils.isEmpty(this.operatorId)) {
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
		// return getEssOperInfo().getProvince();
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
		this.district = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.DISTRICT_CODE);
		if (StringUtils.isEmpty(this.district)) {
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
		
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)){
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(this.notNeedReqStrOrderId,
					"order_deal_method");
			
			//自定义流程
			if("2".equals(deal_method)){
				this.channelId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.ORDER_CHA_CODE);
				if (StringUtils.isEmpty(this.channelId)) {
					this.channelId = getEssOperInfo().getChannel_id();
				}
			}else{
				this.channelId = getEssOperInfo().getChannel_id();
			}
		}else{
			if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
				this.channelId = getEssOperInfo().getChannel_id();
			} else {
				this.channelId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.ORDER_CHA_CODE);
				if (StringUtils.isEmpty(this.channelId)) {
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
		
		if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
				&& "1".equals(flag)){
			String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(this.notNeedReqStrOrderId,
					"order_deal_method");
			
			//自定义流程
			if("2".equals(deal_method)){
				this.channelType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.CHANNEL_TYPE);
				if (StringUtil.isEmpty(this.channelType)) {
					this.channelType = getEssOperInfo().getChannel_type();
				}
			}else{
				this.channelType = getEssOperInfo().getChannel_type();
			}
		}else{
			if (cacheUtil.getConfigInfo("FUKA_ORDER_FROM").contains(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
				this.channelType = getEssOperInfo().getChannel_type();
			} else {
				this.channelType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
						AttrConsts.CHANNEL_TYPE);
				if (StringUtil.isEmpty(this.channelType)) {
					this.channelType = getEssOperInfo().getChannel_type();
				}
			}
		}
		
		return this.channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String geteModeCode() {
		return eModeCode;
	}

	public void seteModeCode(String eModeCode) {
		this.eModeCode = eModeCode;
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

	public String getSerialNumber() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getMainNumberTag() {
		mainNumberTag = "1";
		return mainNumberTag;
	}

	public void setMainNumberTag(String mainNumberTag) {
		this.mainNumberTag = mainNumberTag;
	}

	public String getMarkingTag() {
		markingTag = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.MARKING_TAG);
		return markingTag;
	}

	public void setMarkingTag(String markingTag) {
		this.markingTag = markingTag;
	}

	public String getSaleModType() {
		saleModType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SALE_MOD_TYPE);
		return saleModType;
	}

	public void setSaleModType(String saleModType) {
		this.saleModType = saleModType;
	}

	public String getIsAfterActivation() {
		if ("1".equals(getDelayTag())) {
			return "1";
		}
		return isAfterActivation;
	}

	public void setIsAfterActivation(String isAfterActivation) {
		this.isAfterActivation = isAfterActivation;
	}

	public String getSecCardRoleType() {
		secCardRoleType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.SEC_CARD_ROLE_TYPE);
		return secCardRoleType;
	}

	public void setSecCardRoleType(String secCardRoleType) {
		this.secCardRoleType = secCardRoleType;
	}

	public String getPhotoTag() {
		return photoTag;
	}

	public void setPhotoTag(String photoTag) {
		this.photoTag = photoTag;
	}

	public String getRealPersonTag() {
		return realPersonTag;
	}

	public void setRealPersonTag(String realPersonTag) {
		this.realPersonTag = realPersonTag;
	}

	public MainViceOpenCustInfo getCustInfo() {
		custInfo = new MainViceOpenCustInfo();
		custInfo.setCustId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CUST_ID));
		/*
		 * CB号卡预提交custInfo节点新增新增三个字段：certType，certNum，customerName by zzj
		 */ 
		String cert_type =CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERTI_TYPE);
	 	String certi_type= CommonDataFactory.getInstance().getOtherDictVodeValue("aop_cert_type", cert_type );
		custInfo.setCertType(certi_type);
		custInfo.setCertNum(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM));
		custInfo.setCustomerName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NAME));
		// custInfo.setCertAdress("");
		// custInfo.setSex("");
		// custInfo.setCertExpireDate("");
		// custInfo.setContactPerson("");
		// custInfo.setContactPhone("");
		// custInfo.setContactAddress("");
		custInfo.setCheckType(
				CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CHECK_TYPE));
		return custInfo;
	}

	public void setCustInfo(MainViceOpenCustInfo custInfo) {
		this.custInfo = custInfo;
	}

	public String getMainNumber() {
		mainNumber = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.MAIN_NUMBER);
		return mainNumber;
	}

	public void setMainNumber(String mainNumber) {
		this.mainNumber = mainNumber;
	}

	public String getBipType() {
		bipType = "1";
		return bipType;
	}

	public void setBipType(String bipType) {
		this.bipType = bipType;
	}

	// 受理类型 1：后付费 2：预付费 3：准预付费
	public String getSerType() {
		if (StringUtil.isEmpty(serType)) {
			IGoodsManager goodsManager = SpringContextHolder.getBean("goodsManager");
			List<Goods> products = goodsManager.listGoodsRelProducts(
					CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "goods_id"));
			for (Goods p : products) {
				Map specs = SpecUtils.getProductSpecMap(p);
				String package_type = (String) specs.get("package_type");
				if (StringUtils.equals(package_type, "ll") || StringUtils.isEmpty(package_type)) {
					if (specs != null && !specs.isEmpty()) {
						serType = specs.get("pay_type") == null ? serType : specs.get("pay_type").toString();
					}
				}
			}
			if (!StringUtil.isEmpty(serType)) {
				serType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_pay_type_new", serType);
			}
		} else {
			serType = "1";
		}
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public List<MainViceOpenNiceNumberInfo> getNiceNumberInfo() {
		return niceNumberInfo;
	}

	public void setNiceNumberInfo(List<MainViceOpenNiceNumberInfo> niceNumberInfo) {
		this.niceNumberInfo = niceNumberInfo;
	}

	public List<MainViceOpenSimCardNo> getSimCardNo() {
		return simCardNo;
	}

	public void setSimCardNo(List<MainViceOpenSimCardNo> simCardNo) {
		this.simCardNo = simCardNo;
	}

	public List<MainViceOpenProductInfo> getProductInfo() {
		productInfo = new ArrayList<MainViceOpenProductInfo>();
		MainViceOpenProductInfo info = new MainViceOpenProductInfo();
		String productId = null;
		List<MainViceOpenPackageElement> packageElement = new ArrayList<MainViceOpenPackageElement>();
		// 所有货品
		// List<Goods> products =
		// SpecUtils.getAllOrderProducts(notNeedReqStrOrderId);
		IGoodsManager goodsManager = SpringContextHolder.getBean("goodsManager");
		List<Goods> products = goodsManager.listGoodsRelProducts(
				CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "goods_id"));
		// 附加产品
		List<Goods> productList = new ArrayList<Goods>();
		for (Goods p : products) {
			if ("181251045132000162".equals(p.getCat_id())) {// 总部-附加产品 货品类型
				productList.add(p);
				continue;
			}

			String packageId = null;
			String elementId = null;
			String elementType = null;
			Map specs = SpecUtils.getProductSpecMap(p);
			String package_type = (String) specs.get("package_type");
			if (StringUtils.equals(package_type, "ll") || StringUtils.isEmpty(package_type)) {
				if (specs != null && !specs.isEmpty()) {
					String actPlanId = StringUtil.isEmpty(specs.get("actPlanId") + "") ? EcsOrderConsts.EMPTY_STR_VALUE
							: specs.get("actPlanId") + "";
					if (!StringUtil.isEmpty(actPlanId)) {// 活动信息，不添加在产品信息里
						continue;
					}
					productId = StringUtil.isEmpty(specs.get("cbss_product_code") + "") ? productId
							: specs.get("cbss_product_code") + "";
					packageId = StringUtil.isEmpty(specs.get("package_code") + "") ? null
							: specs.get("package_code") + "";
					elementId = StringUtil.isEmpty(specs.get("element_code") + "") ? null
							: specs.get("element_code") + "";
					elementType = StringUtil.isEmpty(specs.get("elementType") + "") ? "D"
							: specs.get("elementType") + "";
				}
			}
			if (!StringUtil.isEmpty(packageId) || !StringUtil.isEmpty(elementId)) {
				MainViceOpenPackageElement element = new MainViceOpenPackageElement();
				element.setElementId(elementId);
				element.setPackageId(packageId);
				if (StringUtil.isEmpty(elementType)) {
					element.setElementType("D");
				} else {
					element.setElementType(elementType);
				}
				packageElement.add(element);
			}
		}
		if (StringUtil.isEmpty(productId)) {// 其实这里都是主产品的
			return null;
		}
		info.setProductId(productId);
		info.setPackageElement(packageElement);
		String first_payment = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.FIRST_PAYMENT);
		if (!StringUtil.isEmpty(first_payment)) {
			info.setFirstMonBillMode(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_first_payment", first_payment));
		}
		info.setProductMode(
				CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PRODUCT_MODE));
		productInfo.add(info);

		// 附加产品
		if (productList.size() > 0) {
			info = new MainViceOpenProductInfo();
			Set<String> productIdStrs = new HashSet<String>();// 多个附加产品的编码
			for (Goods g : productList) {
				Map specs = SpecUtils.getProductSpecMap(g);
				if (specs != null && !specs.isEmpty()) {
					productId = specs.get("cbss_product_code") == null ? "" : specs.get("cbss_product_code") + "";
					if (!StringUtil.isEmpty(productId)) {
						productIdStrs.add(productId);
					}
				}
			}
			if (!productIdStrs.isEmpty()) {
				for (String pid : productIdStrs) {// 不同的产品编码可能会有多个包元素编码
					if (!StringUtil.isEmpty(pid)) {
						info = new MainViceOpenProductInfo();
						List<MainViceOpenPackageElement> packageElement1 = new ArrayList<MainViceOpenPackageElement>();
						for (Goods g : productList) {
							Map specs = SpecUtils.getProductSpecMap(g);
							if (specs != null && !specs.isEmpty()) {
								productId = specs.get("cbss_product_code") == null ? ""
										: specs.get("cbss_product_code") + "";
								if (!StringUtil.isEmpty(productId) && pid.equals(productId)) {
									String packageId = null;
									String elementId = null;
									String elementType = null;
									packageId = specs.get("package_code") == null ? null
											: specs.get("package_code") + "";
									elementId = specs.get("element_code") == null ? null
											: specs.get("element_code") + "";
									elementType = specs.get("elementType") == null ? "D"
											: specs.get("elementType") + "";
									if (!StringUtil.isEmpty(packageId) || !StringUtil.isEmpty(elementId)) {
										MainViceOpenPackageElement element = new MainViceOpenPackageElement();
										element.setElementId(elementId);
										element.setPackageId(packageId);
										element.setElementType(elementType);
										packageElement1.add(element);
									}
								}
							}
						}

						info.setProductId(pid);
						info.setPackageElement(packageElement1);
						info.setFirstMonBillMode(first_payment);//附加产品的首月默认和主产品的一致
						info.setProductMode("2");// 产品模式1：主产品2：附加产品

						productInfo.add(info);
					}
				}
			}
		}
		return productInfo;

	}

	public void setProductInfo(List<MainViceOpenProductInfo> productInfo) {
		this.productInfo = productInfo;
	}

	public List<MainViceOpenActivityInfo> getActivityInfo() {
		activityInfo = new ArrayList<MainViceOpenActivityInfo>();
		MainViceOpenActivityInfo info = new MainViceOpenActivityInfo();
		info.setIsTest(null);// 是否测试终端0：测试1：正式
		info.setResourcesCode(null);// 终端资源编码，一般是IMEI码
		info.setActPlanId("");// Y活动ID（总部活动ID）
		info.setResourcesFee(null);// 资源费用：厘
		info.setActivityFee(null);// 活动预存费用：厘

		List<MainViceOpenPackageElement> packageElement = new ArrayList<MainViceOpenPackageElement>();
		String actPlanId = null;
		IGoodsManager goodsManager = SpringContextHolder.getBean("goodsManager");
		List<Goods> products = goodsManager.listGoodsRelProducts(
				CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "goods_id"));
		for (Goods p : products) {
			String packageId = null;
			String elementId = null;
			String elementType = null;
			Map specs = SpecUtils.getProductSpecMap(p);
			String package_type = (String) specs.get("package_type");
			if (StringUtils.equals(package_type, "ll") || StringUtils.isEmpty(package_type)) {
				if (specs != null && !specs.isEmpty()) {
					actPlanId = specs.get("actPlanId") == null ? EcsOrderConsts.EMPTY_STR_VALUE
							: specs.get("actPlanId").toString();
					if (StringUtil.isEmpty(actPlanId)) {// 活动信息，不添加在产品信息里
						continue;
					}
					// productId = specs.get("cbss_product_code") == null ?
					// EcsOrderConsts.EMPTY_STR_VALUE :
					// specs.get("cbss_product_code").toString();
					packageId = specs.get("package_code") == null ? EcsOrderConsts.EMPTY_STR_VALUE
							: specs.get("package_code").toString();
					elementId = specs.get("element_code") == null ? EcsOrderConsts.EMPTY_STR_VALUE
							: specs.get("element_code").toString();
					elementType = specs.get("elementType") == null ? EcsOrderConsts.EMPTY_STR_VALUE
							: specs.get("elementType").toString();
				}
			}
			if (!StringUtil.isEmpty(packageId) && !StringUtil.isEmpty(elementId) && !StringUtil.isEmpty(elementType)) {
				MainViceOpenPackageElement element = new MainViceOpenPackageElement();
				element.setElementId(elementId);
				element.setPackageId(packageId);
				element.setElementType(elementType);
				packageElement.add(element);
			}
		}
		if (StringUtil.isEmpty(actPlanId)) {
			return null;
		}
		info.setActPlanId(actPlanId);
		if (packageElement.size() > 0) {
			info.setPackageElement(packageElement);
		}
		activityInfo.add(info);
		return activityInfo;
	}

	public void setActivityInfo(List<MainViceOpenActivityInfo> activityInfo) {
		this.activityInfo = activityInfo;
	}

	public List<MainViceOpenAcctInfo> getAcctInfo() {
		acctInfo = new ArrayList<MainViceOpenAcctInfo>();
		MainViceOpenAcctInfo info = new MainViceOpenAcctInfo();
		info.setAccountPayType("10");
		info.setCreateOrExtendsAcct("1");
		info.setDebutySn(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "debutySn"));
		acctInfo.add(info);
		return acctInfo;
	}

	public void setAcctInfo(List<MainViceOpenAcctInfo> acctInfo) {
		this.acctInfo = acctInfo;
	}

	public MainViceOpenRecomInfo getRecomInfo() {

		/*String recomPersonId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.DEVELOPMENT_CODE);
		String recomPersonName = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.DEVELOPMENT_NAME);
		String recomDepartId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				"development_point_code");
		if (StringUtil.isEmpty(recomPersonId) || StringUtil.isEmpty(recomPersonName)) {
			return null;
		}
		recomInfo = new MainViceOpenRecomInfo();
		recomInfo.setRecomPersonId(recomPersonId);
		recomInfo.setRecomPersonName(recomPersonName);
		recomInfo.setRecomDepartId(StringUtils.isEmpty(recomDepartId) ? null : recomDepartId);
		recomInfo.setRecomCity(null);*/
		
		String develop_code_new = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
	                "develop_code_new");
	    String develop_name_new = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
	                "develop_name_new");
	    String develop_point_code_new = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
	                "develop_point_code_new");
        if (StringUtil.isEmpty(develop_code_new) || StringUtil.isEmpty(develop_name_new)) {
            return null;
        }
        recomInfo = new MainViceOpenRecomInfo();
        recomInfo.setRecomPersonId(develop_code_new);
        recomInfo.setRecomPersonName(develop_name_new);
        recomInfo.setRecomDepartId(StringUtils.isEmpty(develop_point_code_new) ? null : develop_point_code_new);
        recomInfo.setRecomCity(null);
		return recomInfo;
	}

	public void setRecomInfo(MainViceOpenRecomInfo recomInfo) {
		this.recomInfo = recomInfo;
	}

	public String getBatDeveStaffId() {
		return batDeveStaffId;
	}

	public void setBatDeveStaffId(String batDeveStaffId) {
		this.batDeveStaffId = batDeveStaffId;
	}

	public String getBatDeveDepatId() {
		return batDeveDepatId;
	}

	public void setBatDeveDepatId(String batDeveDepatId) {
		this.batDeveDepatId = batDeveDepatId;
	}

	public String getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}

	public String getPhoneSpeedLevel() {
		phoneSpeedLevel = "0";
		return phoneSpeedLevel;
	}

	public void setPhoneSpeedLevel(String phoneSpeedLevel) {
		this.phoneSpeedLevel = phoneSpeedLevel;
	}

	public String getDelayTag() {
		if (StringUtil.isEmpty(this.delayTag)) {
			// 取订单来源
			String order_from = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId)
					.getOrderExtBusiRequest().getOrder_from();
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(this.notNeedReqStrOrderId);
			String flag = cacheUtil.getConfigInfo("WORK_CUSTOM_OPERATOR_FLAG");
			
			if(orderTree!=null && "1".equals(orderTree.getOrderExtBusiRequest().getIs_work_custom())
					&& "1".equals(flag)){
				String deal_method = CommonDataFactory.getInstance().getAttrFieldValue(this.notNeedReqStrOrderId,
						"order_deal_method");
				
				//自定义流程
				if("2".equals(deal_method)){
					//线下方式，现场激活
					this.delayTag = EcsOrderConsts.NO_DEFAULT_VALUE;
				}else{
					//线上方式，压单
					this.delayTag = EcsOrderConsts.IS_DEFAULT_VALUE;
				}
				
			}else{
				String DELAY_TAG_ORDER_FROM = cacheUtil.getConfigInfo("DELAY_TAG_ORDER_FROM");
				if (DELAY_TAG_ORDER_FROM.contains(order_from)) {
					this.delayTag = "1";
				} else {
					// 0:不压单 1:压单 后向激活时压单
					String later_active_flag = EcsOrderConsts.NO_DEFAULT_VALUE;
					OrderRealNameInfoBusiRequest realNameBus = CommonDataFactory.getInstance()
							.getOrderTree(notNeedReqStrOrderId).getOrderRealNameInfoBusiRequest();
					if (null != realNameBus) {
						later_active_flag = realNameBus.getLater_active_flag();
					}
					String goods_cat_id = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
							AttrConsts.GOODS_CAT_ID);
					if (StringUtils.equals(later_active_flag, EcsOrderConsts.IS_DEFAULT_VALUE)) {
						this.delayTag = EcsOrderConsts.IS_DEFAULT_VALUE;
					} else if (!StringUtils.isEmpty(goods_cat_id) && (EcsOrderConsts.GOODS_CAT_ID_DDWK.equals(goods_cat_id)
							|| EcsOrderConsts.GOODS_CAT_ID_TXWK.equals(goods_cat_id))) {
						this.delayTag = EcsOrderConsts.IS_DEFAULT_VALUE;
					} else {

						this.delayTag = EcsOrderConsts.NO_DEFAULT_VALUE;
					}
				}
			}
		}
		return this.delayTag;

	}

	public void setDelayTag(String delayTag) {
		this.delayTag = delayTag;
	}

	public String getDeductionTag() {
		return deductionTag;
	}

	public void setDeductionTag(String deductionTag) {
		this.deductionTag = deductionTag;
	}

	public String getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getcBSSGroupId() {
		return cBSSGroupId;
	}

	public void setcBSSGroupId(String cBSSGroupId) {
		this.cBSSGroupId = cBSSGroupId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<MainViceOpenPara> getPara() {
		return para;
	}

	public void setPara(List<MainViceOpenPara> para) {
		this.para = para;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "ecaop.trades.sell.main.vice.open.app";
	}

}
