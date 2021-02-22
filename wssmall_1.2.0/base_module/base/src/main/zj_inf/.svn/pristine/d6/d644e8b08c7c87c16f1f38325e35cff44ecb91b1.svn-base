package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import params.ZteRequest;
import params.ZteResponse;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.ActivityInfoReq4MV;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.PackageElementVO;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;
import zte.net.ecsord.params.ecaop.req.vo.ProductInfo4MV;
import zte.net.ecsord.utils.SpecUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.common.util.StringUtils;
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

/**
 * @author song.qi
 * @version 2018-05-21
 * @see 老用户加入主副卡:支撑老用户加入，退出cbss群组关系
 *
 */
public class UserJoinMainViceCardReq extends ZteRequest<ZteResponse> {

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
	@ZteSoftCommentAnnotationParam(name = "电子商城订单ID", type = "String", isNecessary = "Y", desc = "电子商城订单ID")
	private String ordersId;
	@ZteSoftCommentAnnotationParam(name = "手机号码", type = "String", isNecessary = "Y", desc = "手机号码")
	private String serialNumber;
	@ZteSoftCommentAnnotationParam(name = "副卡对应的主卡号码", type = "String", isNecessary = "Y", desc = "")
	private String mainNumber;
	@ZteSoftCommentAnnotationParam(name = "业务标示0:加入主卡1:加入副卡", type = "String", isNecessary = "N", desc = "")
	private String serType;
	@ZteSoftCommentAnnotationParam(name = "订购标示0:加入1:退出", type = "String", isNecessary = "N", desc = "")
	private String orderType;
	@ZteSoftCommentAnnotationParam(name = "扣款标示0：不扣款1：扣款--该字段没传的时候默认扣款", type = "String", isNecessary = "N", desc = "")
	private String deductionTag;
	@ZteSoftCommentAnnotationParam(name = "是否行销装备，0：否，1：是该字段没传的时候默认 0：否", type = "String", isNecessary = "N", desc = "")
	private String markingTag;
	@ZteSoftCommentAnnotationParam(name = "销售模式类型0：自营厅行销模式1：行销渠道直销模式", type = "String", isNecessary = "N", desc = "")
	private String saleModType;
	@ZteSoftCommentAnnotationParam(name = "副卡关系类型（不传默认0）0：普通全国产品副卡2：全国冰激凌产品副卡3：冰激凌‘沃+副卡’产品副卡", type = "String", isNecessary = "N", desc = "")
	private String secCardRoleType;
	@ZteSoftCommentAnnotationParam(name = "产品", type = "", isNecessary = "Y", desc = "productInfo")
	private List<ProductInfo4MV> productInfo;
	@ZteSoftCommentAnnotationParam(name = "活动信息", type = "", isNecessary = "Y", desc = "")
	private List<ActivityInfoReq4MV> activityInfo;
	@ZteSoftCommentAnnotationParam(name = "发展人标识", type = "String", isNecessary = "Y", desc = "recomPersonId")
	private String recomPersonId;
	@ZteSoftCommentAnnotationParam(name = "发展人名称", type = "String", isNecessary = "Y", desc = "recomPersonName")
	private String recomPersonName;
	@ZteSoftCommentAnnotationParam(name = "联系人", type = "String", isNecessary = "Y", desc = "")
	private String contactPerson;
	@ZteSoftCommentAnnotationParam(name = "联系人电话", type = "String", isNecessary = "Y", desc = "")
	private String contactPhone;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "", isNecessary = "Y", desc = "para")
	private List<ParaVo> para;

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

	public String getOperatorId() {
		this.operatorId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		if (StringUtils.isEmpty(this.operatorId) && !StringUtils.isEmpty(getEssOperInfo().getEss_emp_id())) {
			this.operatorId = getEssOperInfo().getEss_emp_id();
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
		this.district = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DISTRICT_CODE);
		if (StringUtils.isEmpty(this.district)) {
			this.district = getEssOperInfo().getDistrict();
		}
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		this.channelId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CHA_CODE);
		if (StringUtils.isEmpty(this.channelId)) {
			this.channelId = getEssOperInfo().getChannel_id();
		}
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		this.channelType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CHANNEL_TYPE);
		if (StringUtil.isEmpty(this.channelType)) {
			this.channelType = getEssOperInfo().getChannel_type();
		}
		return this.channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
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
	    serialNumber = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "mainNumber");
        return serialNumber;
/*		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
*/	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getMainNumber() {
		/*if ("0".equals(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "serType"))) {
			return null;
		}
		mainNumber = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "mainNumber");*/
		return mainNumber;
	}

	public void setMainNumber(String mainNumber) {
		this.mainNumber = mainNumber;
	}

	public String getSerType() {
		serType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "serType");
		serType="0";
		return serType;
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public String getOrderType() {
		orderType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "orderType");
		orderType="0";
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getDeductionTag() {
		return deductionTag;
	}

	public void setDeductionTag(String deductionTag) {
		this.deductionTag = deductionTag;
	}

	public String getMarkingTag() {
		markingTag = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.MARKING_TAG);
		if (StringUtils.isEmpty(markingTag)) {
			return "0";
		}
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

	public String getSecCardRoleType() {
		secCardRoleType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "secCardRoleType");
		return secCardRoleType;
	}

	public void setSecCardRoleType(String secCardRoleType) {
		this.secCardRoleType = secCardRoleType;
	}

	public List<ProductInfo4MV> getProductInfo() {
		productInfo = new ArrayList<ProductInfo4MV>();
		ProductInfo4MV vo = new ProductInfo4MV();

		String productId = "";
		List<PackageElementVO> packageElement = new ArrayList<PackageElementVO>();
		IGoodsManager goodsManager = SpringContextHolder.getBean("goodsManager");
		/*List<Goods> products = goodsManager.listGoodsRelProducts(
				CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "goods_id"));*/
	    ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	    String JOINMANAGERVice = cacheUtil.getConfigInfo("JOINMANAGERVice");
	    if(StringUtils.isEmpty(JOINMANAGERVice)){
	        return null;
	    }
		List<Goods> products = goodsManager.listGoodsRelProducts(JOINMANAGERVice);
		for (Goods p : products) {
			String packageId = "";
			String elementId = "";
			String elementType = "";
			Map specs = SpecUtils.getProductSpecMap(p);
			if (specs != null && !specs.isEmpty()) {
				productId = specs.get("cbss_product_code") == null ? "" : specs.get("cbss_product_code").toString();
				packageId = specs.get("package_code") == null ? null : specs.get("package_code").toString();
				elementId = specs.get("element_code") == null ? null : specs.get("element_code").toString();
				elementType = specs.get("elementType") == null ? null : specs.get("elementType").toString();
			}
			// 为空的话整个节点去掉
			if (!StringUtils.isEmpty(elementId) && !StringUtils.isEmpty(packageId)) {
				PackageElementVO pvo = new PackageElementVO();
				pvo.setPackageId(packageId);
				pvo.setElementId(elementId);
				pvo.setElementType(elementType);
				packageElement.add(pvo);
			}
		}
		if (StringUtils.isEmpty(productId)) {
			return null;
		}
		vo.setProductId(productId);
		if (packageElement.size() > 0) {
			vo.setPackageElement(packageElement);
		}
		String productMode = cacheUtil.getConfigInfo("productMode");
		/*String productMode = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "productMode");*/
		if (!StringUtil.isEmpty(productMode)) {
			vo.setProductMode(productMode);
		}
	    String first_payment = cacheUtil.getConfigInfo("first_payment");
		//String first_payment = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.FIRST_PAYMENT);
		if (!StringUtil.isEmpty(first_payment)) {
		//	String firstMonBillMode = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_first_payment", first_payment);
			vo.setFirstMonBillMode(first_payment);
		}
		productInfo.add(vo);
		return productInfo;
	}

	public void setProductInfo(List<ProductInfo4MV> productInfo) {
		this.productInfo = productInfo;
	}

	public List<ActivityInfoReq4MV> getActivityInfo() {
		activityInfo = new ArrayList<ActivityInfoReq4MV>();
		ActivityInfoReq4MV vo = new ActivityInfoReq4MV();
		List<PackageElementVO> packageElement = new ArrayList<PackageElementVO>();
		String actPlanId = null;
		IGoodsManager goodsManager = SpringContextHolder.getBean("goodsManager");
		/*List<Goods> products = goodsManager.listGoodsRelProducts(
				CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "goods_id"));*/
	   ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
	   String JOINMANAGERVice = cacheUtil.getConfigInfo("JOINMANAGERVice");
	   if(StringUtils.isEmpty(JOINMANAGERVice)){
	       return null;
	   }
		List<Goods> products = goodsManager.listGoodsRelProducts(JOINMANAGERVice);
		for (Goods p : products) {
			String packageId = null;
			String elementId = null;
			String enableTag = null;
			Map specs = SpecUtils.getProductSpecMap(p);
			String package_type = (String) specs.get("package_type");
			if (StringUtils.equals(package_type, "ll") || StringUtils.isEmpty(package_type)) {
				if (specs != null && !specs.isEmpty()) {
					actPlanId = specs.get("actPlanId") == null ? EcsOrderConsts.EMPTY_STR_VALUE : specs.get("actPlanId").toString();
					if (StringUtil.isEmpty(actPlanId)) {// 活动信息，不添加在产品信息里
						continue;
					}
					packageId = specs.get("package_code") == null ? EcsOrderConsts.EMPTY_STR_VALUE : specs.get("package_code").toString();
					elementId = specs.get("element_code") == null ? EcsOrderConsts.EMPTY_STR_VALUE : specs.get("element_code").toString();
					enableTag = specs.get("enableTag") == null ? EcsOrderConsts.EMPTY_STR_VALUE : specs.get("enableTag").toString();
				}
			}
			if (!StringUtil.isEmpty(packageId) && !StringUtil.isEmpty(elementId) && !StringUtil.isEmpty(enableTag)) {
				PackageElementVO element = new PackageElementVO();
				element.setElementId(elementId);
				element.setPackageId(packageId);
				element.setElementType(enableTag);
				packageElement.add(element);
			}
		}
		if (StringUtil.isEmpty(actPlanId)) {
			return null;
		}
		vo.setActPlanId(actPlanId);
		if (packageElement.size() > 0) {
			vo.setPackageElement(packageElement);
		}
		vo.setResourcesCode(null);
		vo.setIsTest(null);
		vo.setResourcesFee(null);
		vo.setActivityFee(null);

		activityInfo.add(vo);
		return activityInfo;
	}

	public void setActivityInfo(List<ActivityInfoReq4MV> activityInfo) {
		this.activityInfo = activityInfo;
	}

	public String getRecomPersonId() {
		recomPersonId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DEVELOPMENT_CODE);
		if (StringUtils.isEmpty(recomPersonId)) {
			return null;
		}
		return recomPersonId;
	}

	public void setRecomPersonId(String recomPersonId) {
		this.recomPersonId = recomPersonId;
	}

	public String getRecomPersonName() {
		recomPersonName = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DEVELOPMENT_NAME);
		if (StringUtils.isEmpty(recomPersonName)) {
			// this.recomPersonName = getEssOperInfo().getDep_name();
			return null;
		}
		return recomPersonName;
	}

	public void setRecomPersonName(String recomPersonName) {
		this.recomPersonName = recomPersonName;
	}

	public String getContactPerson() {
		contactPerson = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIP_NAME);
		if (StringUtils.isEmpty(contactPerson)) {
			return null;
		}
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPhone() {
		contactPhone = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REFERENCE_PHONE);
		if (StringUtils.isEmpty(contactPhone)) {
			return null;
		}
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public List<ParaVo> getPara() {
		if (null == para || para.size() < 1) {
			return null;
		}
		return para;
	}

	public void setPara(List<ParaVo> para) {
		this.para = para;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "ecaop.trades.sell.mob.main.vice.chg";
	}
}
