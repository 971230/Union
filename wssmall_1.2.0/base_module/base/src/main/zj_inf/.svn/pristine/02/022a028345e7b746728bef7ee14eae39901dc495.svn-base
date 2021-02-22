/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageSubProdBusiRequest;
import zte.net.ecsord.params.busi.req.AttrTmResourceInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSubProductBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.ActivityInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;
import zte.net.ecsord.params.ecaop.req.vo.ProdPackElementReqVo;
import zte.net.ecsord.params.ecaop.req.vo.ProductInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.TemiResourcesReqVo;
import zte.net.ecsord.params.ecaop.resp.OldUserBuyApplyResp;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.ecsord.utils.DataUtil;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * @author ZX
 * @version 2015-06-23
 * @see 老用户优惠购机处理申请
 *
 */
public class OldUserBuyApplyReq extends ZteRequest<OldUserBuyApplyResp> {
	
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
	@ZteSoftCommentAnnotationParam(name = "老用户优惠购机业务编码", type = "String", isNecessary = "Y", desc = "老用户优惠购机业务编码")
	private String bipCode;
	@ZteSoftCommentAnnotationParam(name = "办理业务系统：1：CBSS 2：不限制", type = "String", isNecessary = "Y", desc = "办理业务系统：1：CBSS 2：不限制")
	private String opeSysType;
	@ZteSoftCommentAnnotationParam(name = "业务类型：1-号卡类业务 裸卡?2-合约类业务 包括合约机以及合约号卡?3-上网卡4-上网本5-其它配件类6-沃享", type = "String", isNecessary = "Y", desc = "业务类型")
	private String bipType;
	@ZteSoftCommentAnnotationParam(name = "手机号码", type = "String", isNecessary = "Y", desc = "手机号码")
	private String serialNumber;
	@ZteSoftCommentAnnotationParam(name = "产品", type = "ProductInfoReqVo", isNecessary = "Y", desc = "产品")
	private List<ProductInfoReqVo> productInfo;
	@ZteSoftCommentAnnotationParam(name = "活动信息", type = "ActivityInfoReqVo", isNecessary = "Y", desc = "活动信息")
	private List<ActivityInfoReqVo> activityInfo;
	@ZteSoftCommentAnnotationParam(name = "发展人标识", type = "String", isNecessary = "N", desc = "发展人标识")
	private String recomPersonId;
	@ZteSoftCommentAnnotationParam(name = "发展人名称", type = "String", isNecessary = "N", desc = "发展人名称")
	private String recomPersonName;
	@ZteSoftCommentAnnotationParam(name = "集团标识 0：非集团用户 1：集团用户", type = "String", isNecessary = "N", desc = "集团标识 0：非集团用户 1：集团用户")
	private String groupFlag;
	@ZteSoftCommentAnnotationParam(name = "集团ID", type = "String", isNecessary = "Y", desc = "集团ID")
	private String groupId;
	@ZteSoftCommentAnnotationParam(name = "应用类别，当加入具体集团时为必填 0：行业应用 1：非行业应用", type = "String", isNecessary = "N", desc = "应用类别，当加入具体集团时为必填 0：行业应用 1：非行业应用")
	private String appType;
	@ZteSoftCommentAnnotationParam(name = "应用子类别 参考附录应用子类别说明", type = "String", isNecessary = "N", desc = "应用子类别 参考附录应用子类别说明")
	private String subAppType;
	@ZteSoftCommentAnnotationParam(name = "担保方式 参见附录", type = "String", isNecessary = "N", desc = "担保方式 参见附录")
	private String guarantorType;
	@ZteSoftCommentAnnotationParam(name = "担保信息参数", type = "String", isNecessary = "N", desc = "担保信息参数")
	private String guaratorID;
	@ZteSoftCommentAnnotationParam(name = "被担保人证件类型", type = "String", isNecessary = "N", desc = "被担保人证件类型")
	private String guCertType;
	@ZteSoftCommentAnnotationParam(name = "被担保人证件号码", type = "String", isNecessary = "N", desc = "被担保人证件号码")
	private String guCertNum;
	@ZteSoftCommentAnnotationParam(name = "资源信息", type = "String", isNecessary = "N", desc = "资源信息")
	private List<TemiResourcesReqVo> resourcesInfo;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "String", isNecessary = "N", desc = "保留字段")
	private List<ParaVo> para;
	
	private EmpOperInfoVo essOperInfo;

	public EmpOperInfoVo getEssOperInfo() {
		if(essOperInfo==null){
			essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
		}
		return essOperInfo;
	}
	
	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
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
	public String getOrdersId() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
	}
	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}
	public String getOpeSysType() {
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_operSys_by_mobileNet", net_type);
//		return EcsOrderConsts.AOP_OPE_SYS_TYPE_1;////TODO 目前只有3G开通了，后续如果总部把4G开通，应该用上面的方法获取，即根据号码网别
	}
	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}
	public String getBipType() {
		String type_id = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, "", SpecConsts.TYPE_ID);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_bip_type", type_id);
	}
	public void setBipType(String bipType) {
		this.bipType = bipType;
	}
	public String getSerialNumber() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public List<ProductInfoReqVo> getProductInfo() {
		List<ProductInfoReqVo> list = new ArrayList<ProductInfoReqVo>();
		ProductInfoReqVo vo = new ProductInfoReqVo();
		String old_product = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OLD_PRODUCT_ID);
		String new_product =CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.ESS_CODE);
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		vo.setProductId(new_product);
		vo.setProductMode(EcsOrderConsts.AOP_PRODUCT_TYPE_MAIN); //0：可选产品；1：主产品（默认主产品）
		vo.setOptType(EcsOrderConsts.AOP_OPER_TYPE_ORDER); // 【产品操作标示（待用），00：订购；01：退订；02：变更】
		
		if(EcsOrderConsts.NET_TYPE_4G.equals(net_type) && StringUtils.isNotEmpty(old_product)){
			ProductInfoReqVo v1 = new ProductInfoReqVo();
			if(!new_product.equals(old_product)){
				v1.setProductId(old_product);
				v1.setProductMode(EcsOrderConsts.AOP_PRODUCT_TYPE_MAIN);
				v1.setOptType(EcsOrderConsts.AOP_OPER_TYPE_UNSUBSCRIBE);
				list.add(v1);
			}else{
				vo.setOptType(EcsOrderConsts.AOP_OPER_TYPE_NO_CHANGE); // 【产品操作标示（待用），00：订购；01：退订；02：变更；03：不变更】
			}
		}
		
		vo.setPackageElement(getPackageElement(vo.getProductId()));
		list.add(vo);
		
		//附加产品处理
		List<OrderSubProductBusiRequest> subProductLists = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderSubProductBusiRequest();
		//获取附加产品数据
		for(OrderSubProductBusiRequest subProduct : subProductLists){
			//只处理主卡的可选包 2016-05-06
			if(null != subProduct.getProd_inst_id() && "0".equals(subProduct.getProd_inst_id())){
				ProductInfoReqVo subVo = new ProductInfoReqVo();
				subVo.setProductId(subProduct.getSub_pro_code());
				subVo.setPackageElement(getSubPackageElement(subProduct.getSub_pro_code(),subProduct.getSub_pro_inst_id()));
				subVo.setProductMode(EcsOrderConsts.AOP_PRODUCT_TYPE_ADD);
				subVo.setOptType(EcsOrderConsts.AOP_OPER_TYPE_ORDER); // 【附加产品操作标示（待用），00：订购；01：退订；02：变更】
				subVo.setProductCode(null);
				list.add(subVo);
			}
		}
		return list;
	}
	public void setProductInfo(List<ProductInfoReqVo> productInfo) {
		this.productInfo = productInfo;
	}
	public List<ActivityInfoReqVo> getActivityInfo() {
		String type_id = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, "", SpecConsts.TYPE_ID);//商品大类	
		List<ActivityInfoReqVo> list = new ArrayList<ActivityInfoReqVo>();
		ActivityInfoReqVo vo = new ActivityInfoReqVo();
		String limit = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10001, SpecConsts.PACKAGE_LIMIT);
		if (StringUtils.isNotBlank(limit)) {
			vo.setActPlanId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_PACKAGE_ID));
		} else {
			vo.setActPlanId(null);
		}
		
		vo.setResourcesType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_resources_type", type_id));
		// N 终端资源编码，一般是IMEI码
		//B环节的时候，终端串码还未录入，因此，传虚拟串码		
		String flowNode = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getFlow_trace_id();
		if(StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)){//B环节还没有串码,传虚拟的
			vo.setResourcesCode(CommonDataFactory.getInstance().getTerminalNum(notNeedReqStrOrderId));
			vo.setIsTest("0"); // N 是否测试终端 0：测试 1：正式
		}else{
			vo.setResourcesCode(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM));
			vo.setIsTest("1"); // N 是否测试终端 0：测试 1：正式
		}
		
		//合约机的商品属性中可以取到购机费用
		if(StringUtils.equals(type_id, EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE)){
			String rFee = CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, "", SpecConsts.MOBILE_PRICE);
			if(!StringUtils.isEmpty(rFee)){
				rFee = Double.parseDouble(rFee)*1000 +"";
				vo.setResourcesFee(rFee.substring(0, rFee.indexOf(".")));// N 资源费用
			}
		}else{
			vo.setResourcesFee(null);
		}
		vo.setResProcId(null); // 未知，待定【预占流水，保持前后多次资源操作的内容一致，保证多次操作是同一订单的前后关联】
		vo.setActPara(null);
		if (StringUtils.isEmpty(vo.getActPlanId()))return null;
		list.add(vo);
		return list;
	}
	public void setActivityInfo(List<ActivityInfoReqVo> activityInfo) {
		this.activityInfo = activityInfo;
	}
	public String getRecomPersonId() {
		//recomPersonId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.DEVELOPMENT_CODE);
	      recomPersonId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,"develop_code_new");

	    if (StringUtils.isBlank(recomPersonId)) return null;
		return recomPersonId;
	}
	public void setRecomPersonId(String recomPersonId) {
		this.recomPersonId = recomPersonId;
	}
	public String getRecomPersonName() {
	//	recomPersonName = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.DEVELOPMENT_NAME);
		recomPersonName = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,"develop_name_new");

		if (StringUtils.isBlank(recomPersonName)) return null;
		return recomPersonName;
	}
	public void setRecomPersonName(String recomPersonName) {
		this.recomPersonName = recomPersonName;
	}
	public String getGroupFlag() {
		String custType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CUSTOMER_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_is_group", custType);
	}
	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}
	public String getGroupId() {
		groupId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GROUP_CODE);
		if (StringUtils.isEmpty(groupId)) return null;
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getAppType() {
		String custType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CUSTOMER_TYPE);
		String groupFlag = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_is_group", custType);
		if (StringUtils.equals(groupFlag, EcsOrderConsts.NO_DEFAULT_VALUE)) { // 非集团客户
			appType = "1"; // 1：非行业应用
		} else if (StringUtils.equals(groupFlag, EcsOrderConsts.IS_DEFAULT_VALUE)) { // 集团客户
			appType = "0"; // 0：行业应用
		}
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getSubAppType() {
		return subAppType;
	}
	public void setSubAppType(String subAppType) {
		this.subAppType = subAppType;
	}
	public String getGuarantorType() {
		return guarantorType;
	}
	public void setGuarantorType(String guarantorType) {
		this.guarantorType = guarantorType;
	}
	public String getGuaratorID() {
		if (StringUtils.equals(groupFlag, EcsOrderConsts.IS_DEFAULT_VALUE)) {
			guaratorID = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GUARANTEE_INFO);
		}
		if (StringUtils.isBlank(guaratorID)) return null;
		return guaratorID;
	}
	public void setGuaratorID(String guaratorID) {
		this.guaratorID = guaratorID;
	}
	public String getGuCertType() {
		if (StringUtils.equals(groupFlag, EcsOrderConsts.IS_DEFAULT_VALUE)) {
			guCertType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GUARANTOR_CERTI_TYPE);
		}
		if (StringUtils.isBlank(guCertType)) return null;
		return guCertType;
	}
	public void setGuCertType(String guCertType) {
		this.guCertType = guCertType;
	}
	public String getGuCertNum() {
		if (StringUtils.equals(groupFlag, EcsOrderConsts.IS_DEFAULT_VALUE)) {
			guCertNum = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GUARANTOR_CERTI_NO);
		}
		if (StringUtils.isBlank(guCertNum)) return null;
		return guCertNum;
	}
	public void setGuCertNum(String guCertNum) {
		this.guCertNum = guCertNum;
	}
	public List<TemiResourcesReqVo> getResourcesInfo() throws Exception {
		List<TemiResourcesReqVo> list = new ArrayList<TemiResourcesReqVo>();
		TemiResourcesReqVo vo = new TemiResourcesReqVo();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		List<AttrTmResourceInfoBusiRequest> resourcelist = orderTree.getTmResourceInfoBusiRequests();
		if (resourcelist.size()>0) {
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
			vo.setCardPrice(resourceInfo.getCard_price());
			vo.setCost(resourceInfo.getC_cost());
			vo.setDistributionTag(resourceInfo.getDistribution_tag());
			vo.setMachineTypeCode(resourceInfo.getMachine_type_code());
			vo.setMachineTypeName(resourceInfo.getMachine_type_name());
			vo.setOrgDeviceBrandCode(resourceInfo.getOrg_device_brand_code());
			//vo.setProductActivityInfo(DataUtil.getActivityInfoVo(resourceInfo.getProductActivityInfo()));
			vo.setReservaPrice(resourceInfo.getReserva_price());
			vo.setResourcesBrandCode(resourceInfo.getResources_brand_code());
			vo.setResourcesBrandName(resourceInfo.getResources_brand_name());
			vo.setResourcesColor(resourceInfo.getResources_color());
			vo.setResourcesModelCode(resourceInfo.getResources_model_code());
			vo.setResourcesModelName(resourceInfo.getResources_model_name());
			vo.setResourcesServiceCorp(resourceInfo.getResources_service_corp());
			vo.setResourcesSrcCode(resourceInfo.getResources_src_code());
			vo.setResourcesSrcName(resourceInfo.getResources_src_name());
			vo.setResourcesSupplyCorp(resourceInfo.getResources_supply_corp());
			vo.setResRele(resourceInfo.getRes_rele());
			vo.setSalePrice(resourceInfo.getSale_price());
			vo.setServiceNumber(resourceInfo.getService_number());
			vo.setTerminalTSubType(resourceInfo.getTerminal_t_sub_type());
			vo.setTerminalType(resourceInfo.getTerminal_type());
			if (CommonDataFactory.checkFieldValueNull(vo)) return null;
			list.add(vo);
		}
		return list;
	}
	public String getBipCode() {
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		if(EcsOrderConsts.NET_TYPE_4G.equals(net_type)){//4G传固定值
			return EcsOrderConsts.BIP_CODE_4G;
		}else{
			return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BIP_CODE);
		}
	}

	public void setBipCode(String bipCode) {
		this.bipCode = bipCode;
	}

	public void setResourcesInfo(List<TemiResourcesReqVo> resourcesInfo) {
		this.resourcesInfo = resourcesInfo;
	}
	public List<ParaVo> getPara() {
		if (DataUtil.checkFieldValueNull(para)) return null;
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
		return "ecaop.trades.sell.mob.oldu.activity.app";
	}
	
	/**
	 * 获取可选包
	 * @param order_id
	 * @return
	 */
	private List<ProdPackElementReqVo> getPackageElement(String product_id) {
		List<ProdPackElementReqVo> ls = new ArrayList<ProdPackElementReqVo>();		
		List<AttrPackageBusiRequest> list = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getPackageBusiRequests();
		//没可选包就不用处理了
		if(list==null ||list.size()==0)return null;
		
		//获取可选包的依赖包等信息
		List<Map> map = AttrUtils.getInstance().doGetDependElementList(list, product_id,
																		CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.FIRST_PAYMENT));
		if (map != null) {
			for (Map m : map) {
				ProdPackElementReqVo vo = new ProdPackElementReqVo();
				if(m.size()>0){
					vo.setPackageId(m.get("package_id").toString());
					vo.setElementId(m.get("element_id").toString());
					vo.setElementType(m.get("element_type_code").toString());
					ls.add(vo);
				}
			}
			return ls;
		} else {
			return null;
		}
	}
	
	/**
	 * 获取附加产品可选包
	 * @param product_id
	 * @param sub_inst_id
	 * @return
	 */
	private List<ProdPackElementReqVo> getSubPackageElement(String product_id , String sub_inst_id) {
		List<ProdPackElementReqVo> ls = new ArrayList<ProdPackElementReqVo>();		
		List<AttrPackageBusiRequest> list = new ArrayList<AttrPackageBusiRequest>();
		List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getAttrPackageSubProdBusiRequest();
		
		//没可选包就不用处理了
		if(attrPackageSubProdList==null ||attrPackageSubProdList.size()==0)return null;
		
		//附加产品可选包转换为AttrPackageBusiRequest类对象
		for(AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList){
			if(sub_inst_id.equals(subPackage.getSubProd_inst_id())){
				AttrPackageBusiRequest attrPackage = new AttrPackageBusiRequest();
				attrPackage.setElementCode(subPackage.getElement_code());
				list.add(attrPackage);
			}
		}
		
		//获取可选包的依赖包等信息
		List<Map> map = AttrUtils.getInstance().doGetDependElementList(list, product_id,
																		CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.FIRST_PAYMENT));
		if (map != null) {
			for (Map m : map) {
				ProdPackElementReqVo vo = new ProdPackElementReqVo();
				if(m.size()>0){
					vo.setPackageId(m.get("package_id").toString());
					vo.setElementId(m.get("element_id").toString());
					vo.setElementType(m.get("element_type_code").toString());
					ls.add(vo);
				}
			}
			return ls;
		} else {
			return null;
		}
	}
	
}
