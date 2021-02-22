package zte.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.FlowPara;
import zte.net.ecsord.params.ecaop.req.vo.FlowProductInfo;
import zte.net.ecsord.params.ecaop.req.vo.PackageElement;
import zte.net.ecsord.utils.SpecUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * songqi 流量包订购/退订 日包/夜猫包/月包: 预提交
 */
public class FlowPacketApplyReq extends ZteRequest {

	private static final long serialVersionUID = -3292807637815081839L;

	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "operatorId：操作员ID")
	private String operatorId;
	@ZteSoftCommentAnnotationParam(name = "省分", type = "String", isNecessary = "Y", desc = "province：省分")
	private String province;
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "city：地市")
	private String city;
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "Y", desc = "district：区县")
	private String district;
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	private String channelId;
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型")
	private String channelType;
	@ZteSoftCommentAnnotationParam(name = "外围系统订单ID", type = "String", isNecessary = "Y", desc = "ordersId：外围系统订单ID")
	private String ordersId;
	@ZteSoftCommentAnnotationParam(name = "手机号码", type = "String", isNecessary = "Y", desc = "serialNumber：手机号码")
	private String serialNumber;
	@ZteSoftCommentAnnotationParam(name = "产品", type = "FlowProductInfo", isNecessary = "Y", desc = "productInfo：产品")
	private List<FlowProductInfo> productInfo;
	@ZteSoftCommentAnnotationParam(name = "发展人标识", type = "String", isNecessary = "N", desc = "recomPersonId：发展人标识")
	private String recomPersonId;
	@ZteSoftCommentAnnotationParam(name = "发展人名称", type = "String", isNecessary = "N", desc = "recomPersonName：发展人名称")
	private String recomPersonName;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "String", isNecessary = "N", desc = "保留字段")
	private List<FlowPara> para;
	private EmpOperInfoVo essOperInfo;//

	@Override
	public String getApiMethodName() {
		return "ecaop.trades.sell.mob.comm.traffic.sub";
	}

	@Override
	public void check() throws ApiRuleException {
	}

	public String getOperatorId() {// 订单信息能取到值，就不用默认
		this.operatorId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.OUT_OPERATOR);
		if (StringUtils.isEmpty(this.operatorId)) {
			this.operatorId = getEssInfo().getEss_emp_id();
		}
		// this.operatorId = "liuss7";
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
		this.province = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.ORDER_PROVINCE_CODE);
		if (this.province.trim().length() != 2) {
			this.province = getEssInfo().getProvince();
		}
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		this.city = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		if (this.city.trim().length() != 3) {
			this.city = CommonDataFactory.getInstance().getOtherDictVodeValue("city", this.city);
		}
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		this.district = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.DISTRICT_CODE);
		if (StringUtils.isEmpty(this.district)) {
			this.district = getEssInfo().getDistrict();
		}
		// this.district = "214";
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		this.channelId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.ORDER_CHA_CODE);
		if (StringUtils.isEmpty(this.channelId)) {
			this.channelId = getEssInfo().getChannel_id();
		}
		// this.channelId = "11a1203";
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		this.channelType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.CHANNEL_TYPE);
		if (StringUtil.isEmpty(this.channelType)) {
			this.channelType = getEssInfo().getChannel_type();
		}
		// this.channelType = "1010400";
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getOrdersId() {
		// OrderItemExtBusiRequest itemsExt =
		// CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId)
		// .getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest();
		// this.ordersId = itemsExt.getBssOrderId();
		// if (StringUtil.isEmpty(this.ordersId)) {
		// this.ordersId = itemsExt.getActive_no();
		// }
		this.ordersId = notNeedReqStrOrderId.replaceAll("[a-zA-Z]", "");
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public List<FlowPara> getPara() {
		return null;
	}

	public void setPara(List<FlowPara> para) {
		this.para = para;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getSerialNumber() {
		this.serialNumber = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.PHONE_NUM);
		// this.serialNumber = "18503607195";
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public List<FlowProductInfo> getProductInfo() {/**
													 * 货品 附件产品 参数中 CBSS产品编码
													 * cbss_product_code 包编码
													 * package_code 元素编码
													 * element_code 10050
													 * getProductSpec
													 */
		// String productId =
		// CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId,
		// "10050",
		// "cbss_product_code");
		// String packageId =
		// CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId,
		// "10050",
		// "package_code");
		// String elementId =
		// CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId,
		// "10050",
		// "element_code");

		productInfo = new ArrayList<FlowProductInfo>();
		FlowProductInfo product = new FlowProductInfo();
		String productId = EcsOrderConsts.EMPTY_STR_VALUE;
		String enableTag = EcsOrderConsts.EMPTY_STR_VALUE;
		List<PackageElement> packageElement = new ArrayList<PackageElement>();
		// 所有货品
		IGoodsManager goodsManager = SpringContextHolder.getBean("goodsManager");
		List<Goods> products = goodsManager.listGoodsRelProducts(
				CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "goods_id"));
		//配置的所有编码
		List<String> elementIds = new ArrayList<String>();
		for (Goods p : products) {
			String packageId = EcsOrderConsts.EMPTY_STR_VALUE;
			String elementId = EcsOrderConsts.EMPTY_STR_VALUE;
			Map specs = SpecUtils.getProductSpecMap(p);

			String package_type = (String) specs.get("package_type");

			if (StringUtils.equals(package_type, "ll") || StringUtils.isEmpty(package_type)) {
				if (specs != null && !specs.isEmpty()) {
					if (specs.get("cbss_product_code") != null) {
						productId = specs.get("cbss_product_code").toString();
						if ("-1".equals(productId)) {// 针对日包
							productId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
									AttrConsts.OLD_PRODUCT_ID);
							if (StringUtil.isEmpty(productId)) {
								productId = "-1";
							}
						}
					} else {
						productId = EcsOrderConsts.EMPTY_STR_VALUE;
					}
					packageId = specs.get("package_code") == null ? EcsOrderConsts.EMPTY_STR_VALUE
							: specs.get("package_code").toString();
					elementId = specs.get("element_code") == null ? EcsOrderConsts.EMPTY_STR_VALUE
							: specs.get("element_code").toString();
					elementIds.add(elementId);
					enableTag = specs.get("enableTag") == null ? EcsOrderConsts.EMPTY_STR_VALUE
							: specs.get("enableTag").toString();
				}
				PackageElement element = new PackageElement();
				element.setPackageId(packageId);// Y包编号
				element.setElementId(elementId);// Y元素编号
				element.setElementType("D");// Y 元素类型 D资费,S服务,A活动，X S服务
				packageElement.add(element);
			}
		}
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		if (cacheUtil.getConfigInfo("CB_FLOW_PRODUCTID").contains(productId) && elementIds.size() > 0
				&& packageElement.size() > 0) {
			PackageElement element = new PackageElement();
			element.setElementId(packageElement.get(0).getElementId());
			element.setPackageId(packageElement.get(0).getPackageId());
			element.setElementType(packageElement.get(0).getElementType());
			packageElement.clear();
			// 号码已存在的编码
			String element_ids = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "element_ids");
			//可用编码
			List<String> ids = new ArrayList<String>();
			for (String elementId : elementIds) {
				if (!element_ids.contains(elementId)) {
					ids.add(elementId);
				}
			}
			// 已经订购所有的话，就传
			if (ids.size() > 0) {
				element.setElementId(ids.get(0));
				packageElement.add(element);
			}
		}
		product.setProductId(productId);
		
		// add by zhaochen 20190417 根据传入的操作类型取值
		String optType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.optType);
		if("01".equals(optType)){
			optType = "01";
		}else{
			optType = "00";
		}
		
		product.setOptType(optType);// N 00：订购；01：退订
		product.setEnableTag(enableTag);// Y 生效方式 1：立即生效 2：次月生效
		// int tag =
		// CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getBss_time_type();
		// if (1 != tag) {// BSS业务办理时间类型：1当月办理，0非当月办理
		// product.setEnableTag("2");
		// }
		if (packageElement.size() != 0) {
			product.setPackageElement(packageElement);
		}
		productInfo.add(product);
		return productInfo;
	}

	public void setProductInfo(List<FlowProductInfo> productInfo) {
		this.productInfo = productInfo;
	}

	public String getRecomPersonId() {
		/*this.recomPersonId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.DEVELOPMENT_CODE);*/
	    this.recomPersonId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
                "develop_code_new");
		if (StringUtils.isEmpty(recomPersonId)) {
			return null;
		}
		return recomPersonId;
	}

	public void setRecomPersonId(String recomPersonId) {
		this.recomPersonId = recomPersonId;
	}

	public String getRecomPersonName() {
		/*this.recomPersonName = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
				AttrConsts.DEVELOPMENT_NAME);*/
	    this.recomPersonName = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
                "develop_name_new");
		if (StringUtils.isEmpty(recomPersonName)) {
			return null;
		}
		return recomPersonName;
	}

	public void setRecomPersonName(String recomPersonName) {
		this.recomPersonName = recomPersonName;
	}

	public EmpOperInfoVo getEssInfo() {
		if (essOperInfo == null) {
			String opercode = CommonDataFactory.getInstance().getOperatorCodeByOrderFrom(notNeedReqStrOrderId);
			if (StringUtil.isEmpty(opercode)) {

				opercode = "zjplxk";
			}
			String cityId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
					AttrConsts.ORDER_CITY_CODE);
			String otherCityCode = CommonDataFactory.getInstance().getOtherDictVodeValue("city", cityId);
			IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
			String sql = "select * from es_operator_rel_ext a where a.order_gonghao ='" + opercode + "' and a.city='"
					+ otherCityCode + "' and a.source_from ='" + ManagerUtils.getSourceFrom() + "'";
			essOperInfo = support.queryForObject(sql, EmpOperInfoVo.class);
		}
		return essOperInfo;
	}

}
