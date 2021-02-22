/**
 * 
 */
package zte.net.ecsord.params.bss.req;

import java.util.ArrayList;
import java.util.HashMap;
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
import zte.net.ecsord.params.busi.req.AttrZhuanDuiBaoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSubProductBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.BSSAccountInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSAccountRemarkVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSActParaVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSActivityInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSCustomerRemarkVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSNewCustomerInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSNumberInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSProComItemInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSProCompInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSProductVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSSimCardNoVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSSpclSvcInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSUserInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSUserRemarkVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSVacInfosVo;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.OpenDealApplyResourcesInfoVo;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.ecsord.utils.DataUtil;
import zte.net.ecsord.utils.SpecUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
/**
 * @author zengxianlian
 * @version 2016-03-15
 * @see 开户处理申请
 * 
 */
public class BSSAccountReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "operatorId：操作员ID")
	private String OperatorID;
	@ZteSoftCommentAnnotationParam(name = "省份", type = "String", isNecessary = "Y", desc = "province：省份")
	private String Province;
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "city：地市")
	private String City;
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "Y", desc = "district：区县")
	private String District;
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	private String ChannelID;
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型:参考附录渠道类型编码")
	private String ChannelType;
	@ZteSoftCommentAnnotationParam(name = "接入类型", type = "String", isNecessary = "Y", desc = "01 沃受理； 02 沃云购； 03 ECS； 04 ESS")
	private String AccessType;
	@ZteSoftCommentAnnotationParam(name = "外围系统订单ID", type = "String", isNecessary = "Y", desc = "ordersId：外围系统订单ID")
	private String OrdersID;
	@ZteSoftCommentAnnotationParam(name = "手机号码", type = "String", isNecessary = "Y", desc = "手机号码")
	private String NumID;
	@ZteSoftCommentAnnotationParam(name = "获取写卡数据业务流水", type = "String", isNecessary = "N", desc = "获取写卡数据业务流水")
	private String CardDataProcID;
	@ZteSoftCommentAnnotationParam(name = "手机号码", type = "String", isNecessary = "N", desc = "卡号")
	private String SimID;
	@ZteSoftCommentAnnotationParam(name = "新IMSI号", type = "String", isNecessary = "N", desc = "新IMSI号")
	private String IMSI;
	@ZteSoftCommentAnnotationParam(name = "白卡类型", type = "String", isNecessary = "N", desc = "白卡类型")
	private String CardType;
	@ZteSoftCommentAnnotationParam(name = "白卡数据", type = "String", isNecessary = "N", desc = "白卡数据")
	private String CardData;
	@ZteSoftCommentAnnotationParam(name = "受理类型", type = "String", isNecessary = "Y", desc = "1-后付费 2-预付费")
	private String SerType;
	@ZteSoftCommentAnnotationParam(name = "首月付费方式", type = "String", isNecessary = "N", desc = "01:标准资费（免首月月租） 02:全月套餐 03:套餐减半")
	private String FirstMonBillMode;
	@ZteSoftCommentAnnotationParam(name = "客户实名标识", type = "String", isNecessary = "Y", desc = "0-实名1-匿名")
	private String RealNameType;
	@ZteSoftCommentAnnotationParam(name = "VIP卡号", type = "String", isNecessary = "N", desc = "（三、四、五档以上需要赠送VIP卡）")
	private String VIPNum;
	@ZteSoftCommentAnnotationParam(name = "客户ID", type = "String", isNecessary = "N", desc = "客户ID(继承客户时使用，可选 )")
	private String CustomerID;
	@ZteSoftCommentAnnotationParam(name = "客户信息", type = "BSSNewCustomerInfoVo", isNecessary = "N", desc = "CustomerInfoVo：客户信息")
	private BSSNewCustomerInfoVo NewCustomerInfo;
	@ZteSoftCommentAnnotationParam(name = "用户信息", type = "BSSUserInfoVo", isNecessary = "Y", desc = "userInfo：用户信息")
	private BSSUserInfoVo UserInfo;
	@ZteSoftCommentAnnotationParam(name = "是否继承账户标示", type = "String", isNecessary = "N", desc = "是否继承账户标示0:新建账户1：继承老账户")
	private String CreateOrExtendsAcct;
	@ZteSoftCommentAnnotationParam(name = "账户ID(继承账户时使用，可选，BSS中真实存在 )", type = "String", isNecessary = "N", desc = "账户ID(继承账户时使用，可选，BSS中真实存在 )")
	private String AccountID;
	@ZteSoftCommentAnnotationParam(name = "账户信息", type = "BSSAccountInfoVo", isNecessary = "N", desc = "新建的账户信息(如果新/老账户标识为0此节点为必填)")
	private BSSAccountInfoVo AccountInfo;
	@ZteSoftCommentAnnotationParam(name = "开户时选择的产品信息", type = "list", isNecessary = "N", desc = "开户时选择的产品信息")
	private List<BSSProductVo> ProductInfo;
	@ZteSoftCommentAnnotationParam(name = "开户时选择的特服信息", type = "list", isNecessary = "N", desc = "开户时选择的特服信息")
	private List<BSSSpclSvcInfoVo> SpclSvcInfo;
	@ZteSoftCommentAnnotationParam(name = "Sp信息", type = "BSSVacInfosVo", isNecessary = "N", desc = "Sp信息")
	private BSSVacInfosVo VacInfos;
	@ZteSoftCommentAnnotationParam(name = "开户时选择的活动信息", type = "list", isNecessary = "N", desc = "开户时选择的活动信息")
	private List<BSSActivityInfoVo> ActivityInfo;
	@ZteSoftCommentAnnotationParam(name = "号码信息（适用于总部号码资源系统统一管理的号码）", type = "BSSVacInfosVo", isNecessary = "N", desc = "号码信息（适用于总部号码资源系统统一管理的号码）")
	private BSSNumberInfoVo NumberInfo;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
	private List<BSSParaVo> para;
	
	private EmpOperInfoVo essOperInfo;
	private BSSSimCardNoVo bssSimCardNoVo;

	public String getOperatorID() {
		return getEssOperInfo().getEss_emp_id();
	}

	public void setOperatorID(String operatorID) {
		this.OperatorID = operatorID;
	}

	public String getProvince() {
		return getEssOperInfo().getProvince();
	}

	public void setProvince(String province) {
		this.Province = province;
	}

	public String getCity() {
		return getEssOperInfo().getCity();
	}

	public void setCity(String city) {
		this.City = city;
	}

	public String getDistrict() {
		return getEssOperInfo().getDistrict();
	}

	public void setDistrict(String district) {
		this.District = district;
	}

	public String getChannelID() {
		return getEssOperInfo().getChannel_id();
	}

	public void setChannelID(String channelID) {
		this.ChannelID = channelID;
	}

	public String getChannelType() {
		return getEssOperInfo().getChannel_type();
	}

	public void setChannelType(String channelType) {
		this.ChannelType = channelType;
	}

	public String getOrdersID() {
		OrdersID = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BSS_INF_ID);
		if (StringUtils.isBlank(OrdersID)) return null;
		return OrdersID;
	}

	public void setOrdersID(String ordersID) {
		this.OrdersID = ordersID;
	}
	

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
	/**
	 * 获取客户备注信息
	 * @param order_id
	 * @return
	 */
	private List<BSSCustomerRemarkVo> getBSSCustomerRemarkVo(String order_id) {
		List<BSSCustomerRemarkVo> ls = new ArrayList<BSSCustomerRemarkVo>();
		BSSCustomerRemarkVo vo = new BSSCustomerRemarkVo();
		vo.setCustomerRemarkId(null);// Y 客户备注ID(未知，不晓得填什么)
		vo.setCustomerRemarkValue(null); // Y客户备注信息(未知，不晓得填什么)
		if (DataUtil.checkFieldValueNull(vo))return null;
		ls.add(vo);
		return ls;
	}
	/**
	 * 获取用户备注信息
	 * @param order_id
	 * @return
	 */
	private List<BSSUserRemarkVo> getBSSUserRemarkVo(String order_id) {
		List<BSSUserRemarkVo> ls = new ArrayList<BSSUserRemarkVo>();
		BSSUserRemarkVo vo = new BSSUserRemarkVo();
		vo.setUserRemarkId(null);// Y 客户备注ID(未知，不晓得填什么)
		vo.setUserRemarkValue(null); // Y客户备注信息(未知，不晓得填什么)
		if (DataUtil.checkFieldValueNull(vo))return null;
		ls.add(vo);
		return ls;
	}
	
	/**
	 * 获取用户备注信息
	 * @param order_id
	 * @return
	 */
	private List<BSSAccountRemarkVo> getBSSAccountRemarkVo(String order_id) {
		List<BSSAccountRemarkVo> ls = new ArrayList<BSSAccountRemarkVo>();
		BSSAccountRemarkVo vo = new BSSAccountRemarkVo();
		vo.setAccountRemarkId(null);// Y 客户备注ID(未知，不晓得填什么)
		vo.setAccountRemarkValue(null); // Y客户备注信息(未知，不晓得填什么)
		if (DataUtil.checkFieldValueNull(vo))return null;
		ls.add(vo);
		return ls;
	}
	
	
	/**
	 * 获取支付信息
	 * @param order_id
	 * @return
	 */
//	private OpenDealApplyPayInfoVo getPayInfoVo(String order_id) {
//		OpenDealApplyPayInfoVo vo = new OpenDealApplyPayInfoVo();
//		vo.setPayType(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BILL_TYPE)); // 付费方式
//		vo.setPayOrg(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PAY_PROVIDER_NAME)); // 支付机构名称
//		vo.setPayNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BANK_ACCOUNT)); // 支付账号
//		if (DataUtil.checkFieldValueNull(vo))return null;
//		return vo;
//	}
	
	
	
	
//	/**
//	 * 获取终端资源信息
//	 * @param order_id
//	 * @return
//	 * @throws Exception 
//	 */
//	private List<OpenDealApplyResourcesInfoVo> getResourcesInfoVo(String order_id) throws Exception {
//		List<OpenDealApplyResourcesInfoVo> ls = new ArrayList<OpenDealApplyResourcesInfoVo>();
//		OpenDealApplyResourcesInfoVo vo = new OpenDealApplyResourcesInfoVo();
//		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
//		List<AttrTmResourceInfoBusiRequest> resourcelist = orderTree.getTmResourceInfoBusiRequests();
//		if (resourcelist.size()>0) {
//			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);
//			vo.setSalePrice(resourceInfo.getSale_price()); // N 销售价格（单位：厘）
//			vo.setCost(resourceInfo.getC_cost()); // N 成本价格（单位：厘）
//			vo.setCardPrice(resourceInfo.getCard_price()); // N 卡费（单位：厘）
//			vo.setReservaPrice(resourceInfo.getReserva_price()); // N 预存话费金额（单位：厘）
//			//o.setProductActivityInfo(getActivityInfoVo(resourceInfo.getProductActivityInfo())); // 套包对应产品活动信息（待定 2015-05-21 朱红玉说待定）
//			vo.setResourcesBrandCode(resourceInfo.getResources_brand_code()); // N 品牌
//			vo.setOrgDeviceBrandCode(resourceInfo.getOrg_device_brand_code()); // N 3GESS维护品牌，当iphone时品牌与上面的一致
//			vo.setResourcesBrandName(resourceInfo.getResources_brand_name()); // N 终端品牌名称
//			vo.setResourcesModelCode(resourceInfo.getResources_model_code()); // N 型号
//			vo.setResourcesModelName(resourceInfo.getResources_model_name()); // N 终端型号名称
//			vo.setResourcesSrcCode(resourceInfo.getResources_src_code()); // N 终端来源编码
//			vo.setResourcesSrcName(resourceInfo.getResources_src_name()); // N 终端来源名称
//			vo.setResourcesSupplyCorp(resourceInfo.getResources_supply_corp()); // N 终端供货商名称
//			vo.setResourcesServiceCorp(resourceInfo.getResources_service_corp()); // N 终端服务商名称
//			vo.setResourcesColor(resourceInfo.getResources_color()); // N 终端颜色
//			vo.setMachineTypeCode(resourceInfo.getMachine_type_code()); // N 终端机型编码
//			vo.setMachineTypeName(resourceInfo.getMachine_type_name()); // N 终端机型名称
//			vo.setDistributionTag(resourceInfo.getDistribution_tag()); // N "铺货标志 0非铺货终端  1铺货终端"
//			vo.setResRele(resourceInfo.getRes_rele()); // N 资源归属 01 总部管理资源 02 省分管理资源 03：全部资源 04 社会渠道资源
//			vo.setTerminalType(resourceInfo.getTerminal_type()); // N 终端类别编码：Iphone：IP 乐phone：LP  智能终端：PP普通定制终端：01 上网卡：04 上网本：05
//			vo.setTerminalTSubType(resourceInfo.getTerminal_t_sub_type()); // N 当终端类别为智能终端时，该字段必填，终端子类别编码：入门级：04  普及型：03 中高端：02 明星：01
//			vo.setServiceNumber(resourceInfo.getService_number()); // N 终端绑定的服务号码，当终端为打包的预付费产品时必传，如上网卡套包等
//			if (DataUtil.checkFieldValueNull(vo))return null;
//			ls.add(vo);
//		}
//		return ls;
//	}
	
	/**
	 * 获取是否集团标志
	 * @param order_id
	 * @return
	 */
	private String getGroupFlag(String order_id) {
		String custType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("bss_is_group", custType);
	}
	
	/**
	 * 获取首月资费
	 * @param order_id
	 * @return
	 */
//	private String getFirstBillMode(String order_id) {
//		String first_payment = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.FIRST_PAYMENT);
//		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_first_payment", first_payment);
//	}
	
	/**
	 * 获取预留
	 * @param order_id
	 * @return
	 */
//	private List<ParamsVo> getParaVo(String order_id) {
//		List<ParamsVo> ls = new ArrayList<ParamsVo>();
//		ParamsVo vo = new ParamsVo();
//		vo.setParaId(null);
//		vo.setParaValue(null);
//		if (DataUtil.checkFieldValueNull(vo))return null;
//		ls.add(vo);
//		return ls;
//	}
	
	/**
	 * 获取客户类型
	 * @param order_id
	 * @return
	 */
	private String getCustType(String order_id) {
		String custType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("bss_user_type", custType);
	}
	
	/**
	 * 获取套包销售标记
	 * @param order_id
	 * @return
	 */
//	private String getPackageTag(String order_id) {
//		String packageSale = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PACKAGE_SALE);
//		String packageTag =  CommonDataFactory.getInstance().getOtherDictVodeValue("aop_package_sale", packageSale);
//		if (StringUtils.isBlank(packageTag)) {
//			packageTag = "0"; 
//		} 		
//		return packageTag;
//	}
	
	/**
	 * 获取业务类型
	 * @param order_id
	 * @return
	 */
//	private String getBipType_id(String order_id) {
//		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);
//		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_bip_type", type_id);
//	}
	
	/**
	 * 获取网别
	 * @param order_id
	 * @return
	 */
//	private String getNet_type(String order_id) {
//		String net_type = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
//		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_mobile_net", net_type);
//	}
	
	/**
	 * 获取行业应用
	 * @param order_id
	 * @return
	 */
//	private String getAppType(String groupFlag) {
//		String appType = "";
//		if (StringUtils.equals(groupFlag, EcsOrderConsts.NO_DEFAULT_VALUE)) { // 非集团客户
//			appType = "1"; // 1：非行业应用
//		} else if (StringUtils.equals(groupFlag, EcsOrderConsts.IS_DEFAULT_VALUE)) { // 集团客户
//			appType = "0"; // 0：行业应用
//		}
//		return appType;
//	}
	
	/**
	 * 获取证件类型
	 * @param order_id
	 * @return
	 */
	private String getCertType(String order_id) {
		String cert_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("bss_cert_type", cert_type);
	}
//	private String getCheckType(String order_id) {//取不到值默认2代 
//		String checkType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_EXAMINE_CARD);
//		if(StringUtils.isEmpty(checkType))checkType="0";
//		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_check_type", checkType);
//	}
	
	/**
	 * 获取活动编码，即合约编码
	 * @param order_id
	 * @return
	 */
	private String getOutPackageId (String order_id) {
		// 有 合约期限 的才取 活动编码
		String limit = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001, SpecConsts.PACKAGE_LIMIT);
		if (StringUtils.isNotBlank(limit)) {
			return CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_PACKAGE_ID);
		} else {
			return null;
		}
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
		return "com.zte.unicomService.zb.openDealApplyBSS.bss";
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

	public String getAccessType() {
		return EcsOrderConsts.BSS_ACCESS_TYPE_WYG;//牛姐说写死沃云购
	}

	public void setAccessType(String accessType) {
		this.AccessType = accessType;
	}

	public String getNumID() {
		return  CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
	}

	public void setNumID(String numID) {
		this.NumID = numID;
	}

	public String getCardDataProcID() {
		if(null != bssSimCardNoVo && StringUtils.isNotEmpty(bssSimCardNoVo.getCardDataProcId())){
			CardDataProcID = bssSimCardNoVo.getCardDataProcId();
		}
		return CardDataProcID;
	}

	public void setCardDataProcID(String cardDataProcID) {
		this.CardDataProcID = cardDataProcID;
	}

	public String getSimID() {
		if(null != bssSimCardNoVo && StringUtils.isNotEmpty(bssSimCardNoVo.getSimId())){
			SimID = bssSimCardNoVo.getSimId();
		}
		return SimID;
	}

	public void setSimID(String simID) {
		this.SimID = simID;
	}

	public String getiMSI() {
		if(null != bssSimCardNoVo && StringUtils.isNotEmpty(bssSimCardNoVo.getImsi())){
			IMSI = bssSimCardNoVo.getImsi();
		}
		return IMSI;
	}

	public void setiMSI(String iMSI) {
		this.IMSI = iMSI;
	}

	public String getCardType() {
		if(null != bssSimCardNoVo && StringUtils.isNotEmpty(bssSimCardNoVo.getCardType())){
			CardType = bssSimCardNoVo.getCardType();
		}
		return CardType;
	}

	public void setCardType(String cardType) {
		this.CardType = cardType;
	}

	public String getCardData() {
		if(null != bssSimCardNoVo && StringUtils.isNotEmpty(bssSimCardNoVo.getCardData())){
			CardData = bssSimCardNoVo.getCardData();
		}
		return CardData;
	}

	public void setCardData(String cardData) {
		this.CardData = cardData;
	}

	public String getSerType() {
		String serType = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("bss_ser_type", serType);
	}

	public void setSerType(String serType) {
		this.SerType = serType;
	}

	public String getFirstMonBillMode() {
		String first_payment = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.FIRST_PAYMENT);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("bss_first_payment", first_payment);
	}

	public void setFirstMonBillMode(String firstMonBillMode) {
		this.FirstMonBillMode = firstMonBillMode;
	}

	public String getRealNameType() {
		//默认0
		return "0";
	}

	public void setRealNameType(String realNameType) {
		this.RealNameType = realNameType;
	}

	public String getvIPNum() {
		return VIPNum;
	}

	public void setvIPNum(String vIPNum) {
		this.VIPNum = vIPNum;
	}

	public String getCustomerID() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CUST_ID);
	}

	public void setCustomerId(String customerID) {
		this.CustomerID = customerID;
	}

	public BSSSimCardNoVo getBssSimCardNoVo() {
		if(null == bssSimCardNoVo){
			String sim_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SIM_TYPE);
			if(EcsOrderConsts.SIM_TYPE_BCK.equals(sim_type)){//半成卡
				bssSimCardNoVo = getBssSimCardNoVo(notNeedReqStrOrderId);
			}
		}
		return bssSimCardNoVo;
	}

	public void setBssSimCardNoVo(BSSSimCardNoVo bssSimCardNoVo) {
		this.bssSimCardNoVo = bssSimCardNoVo;
	}
	
	/**
	 * 获取卡信息(目前只有半成卡用到)
	 * @param order_id
	 * @return
	 */
	private BSSSimCardNoVo getBssSimCardNoVo(String order_id) {
		BSSSimCardNoVo vo = new BSSSimCardNoVo();
		vo.setCardDataProcId(""); // 获取写卡数据业务流水（此字段用途是什么，暂时还未明确）
		String ICCID = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
		vo.setSimId(null==ICCID?"":ICCID); // simId：如果是成卡是USIM卡号，如果是白卡是ICCID(卡号，是否指ICCID??)
		vo.setImsi(""); // 新IMSI号(新IMSI号，未读写卡，何来此信息？)
		vo.setCardType(""/*CommonDataFactory.getInstance().getAttrFieldValue(order_id, "WHITE_CART_TYPE")*/); // cardType：白卡类型  参考附录白卡类型说明(2015-05-19 朱红玉说这个整点不要传，不管取到娶不到)
		vo.setCardData(""); // cardData：白卡数据(白卡数据，未读写卡，何来此信息？)
		return vo;
	}

	public String getCreateOrExtendsAcct() {
		return "0";// Y 是否继承账户标示 0：新建账户 1：继承老账户（2015-05-06 朱红玉说我们系统都是新用户）;
	}
	
	/**
	 * 获取新客户信息 
	 * @param order_id
	 * @return
	 */
	private BSSNewCustomerInfoVo getBSSNewCustomerInfoVo(String order_id) {
		if(StringUtils.isEmpty(getCustomerID())){
			BSSNewCustomerInfoVo vo = new BSSNewCustomerInfoVo();
			vo.setCustomerType(getCustType(order_id)); // N 客户类型： 01：个人客户 02：集团客户
			//vo.setCustomerLevel("");
			//vo.setCustomerLoc("");
			vo.setCertType(getCertType(order_id)); // Y 证件类型 参考附录证件类型说明
			vo.setCertNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM)); // Y 证件号码
			vo.setCustomerName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME)); // Y 客户名称（不能全数字）
			//vo.setCustomerPasswd("");
			vo.setReleOfficeID(getChannelID());//牛姐说先取开户工号
			String certExpireDate = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_FAILURE_TIME);
			certExpireDate = certExpireDate.replace("-", "");
			certExpireDate = StringUtils.isEmpty(certExpireDate)?"20591231":certExpireDate;
			vo.setCertExpireDate(certExpireDate.substring(0,8)); // Y 格式：yyyymmdd 证件失效日期(考虑默认)
			//vo.setCustomerZip("");
			vo.setCustomerSex("");
			
			vo.setContactPhone(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REVEIVER_MOBILE)); // Y 联系人电话>6位
			vo.setCertAddr(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS)); // Y 证件地址
			vo.setContactPerson(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_NAME)); // N 联系人（不能全数字）
			vo.setContactAddr(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CONTACT_ADDR)); // N 通讯地址
			vo.setCustomerRemark(getBSSCustomerRemarkVo(order_id)); // N 客户备注  OpenDealApplyCustomerRemarkVo (2015-05-06下午朱红玉说整个对象都可以不传)
			return vo;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取用户信息
	 * @param order_id
	 * @return
	 */
	private BSSUserInfoVo getBSSUserInfoVo(String order_id) {
		BSSUserInfoVo vo = new BSSUserInfoVo();
		vo.setUserName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME));
		vo.setPackID(CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.ESS_CODE));
		vo.setUserAddr(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS));
		vo.setGroupFlag(getGroupFlag(order_id)); // N 集团标识 0：非集团用户 1：集团用户
		if (StringUtils.equals(vo.getGroupFlag(), EcsOrderConsts.IS_DEFAULT_VALUE)) {//集团才有担保
			vo.setGroupId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GROUP_CODE));// N 集团ID
			//vo.setAppType(getAppType(vo.getGroupFlag()));// N 应用类别，当加入具体集团时为必填 0：行业应用 1：非行业应用
			//vo.setSubAppType(null);// N 应用子类别 参考附录应用子类别说明
			vo.setGuarantorType(null);// N 担保方式 参见附录
			vo.setGuaratorID(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GUARANTEE_INFO) );// N 担保信息参数 根据担保类型确定 如担保类型为01 则填担保人客户ID 如担保类型为02
										// 则填退款方式（11 按年，12 一次性） 如担保类型为03 则置空 如担保类型为04
										// 则填无担保原因 如担保类型为05 则填社保卡卡号 如担保类型为06
										// 则填“单位名称，单位编码，合同协议编码” 如担保类型为07 则填“银行名称，银行编码
										// 如担保类型为08 则填“银行或保险公司名称，银行或保险公司编码，合同协议编码”
										// 如担保类型为09
										// 则填“银行公司名称,银行公司编码,合同协议编码,冻结存款流水号,冻结金额(单位：元)”
										// 如担保类型为10 则填“担保公司名称，担保公司编码，合同协议编码”
										// 如担保类型为11
										// 则填“单位名称，单位编码，银行名称，银行编码，合同协议编码，冻结金额（单位：元）”如担保类型为12
										// 则填“担保公司名称，担保公司编码，合同协议编码”"
			//vo.setGuCertType(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GUARANTOR_CERTI_TYPE) );// N 被担保人证件类型
			//vo.setGuCertNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GUARANTOR_CERTI_NO) );// N 被担保人证件号码
		}
		//W3G1 3G后付
		// W3G3 3G预付
		// W4G1 4G后付
		// G000  2G后付
		// OC00  2G预付
		//自备机终端串号
		String terminalId = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.TERMINAL_NUM);//串号
		vo.setTerminalId(terminalId);
		vo.setBrandID("W4G1");//具体怎么传要看
		vo.setUserType("1"); // Y 用户类型 1：新用户 2：老用户 （2015-05-06 朱红玉说我们系统都是新用户）
		vo.setCreditVale(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CREDIT_CLASS)); // N 信用等级，（3G后付费业务取值范围A、B、C）
		vo.setCheckCreditVale(null); // N 用户选择信用等级（3G后付费业务取值范围A、B、C） （2015-05-06 朱红玉说未知）
		vo.setUserRemark(getBSSUserRemarkVo(order_id)); // N 客户备注  OpenDealApplyCustomerRemarkVo (2015-05-06下午朱红玉说整个对象都可以不传)
		//发展人编码
		vo.setRecomPersonID(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.DEVELOPMENT_CODE));
		//发展人名称
		vo.setRecomPersonName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.DEVELOPMENT_NAME));
		//推荐部门标识
		vo.setRecomDeparID(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.ORDER_CHA_CODE));
		//推荐部门名称
		vo.setRecomDeparName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.ORDER_CHA_NAME));
		return vo;
	}
	
	/**
	 * 获取帐号信息
	 * @param order_id
	 * @return
	 */
	private BSSAccountInfoVo getBSSAccountInfoVo(String order_id) {
		if("0".equals(getCreateOrExtendsAcct())){//新用户
			BSSAccountInfoVo vo = new BSSAccountInfoVo();
			vo.setAccountName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME));
			vo.setAccountPasswd("");
			vo.setAccountPayType(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BILL_TYPE));
			vo.setAcctType("");
			vo.setAccountAddr("");
			vo.setAccountZip("");
			vo.setBillSendCont("");
			vo.setSendBillFlag("");
			vo.setAccountRemarkVo(getBSSAccountRemarkVo(order_id));
			//vo.setCreateOrExtendsAcct("0");// Y 是否继承账户标示 0：新建账户 1：继承老账户（2015-05-06 朱红玉说我们系统都是新用户）
			//vo.setDebutySn(null); // N 合帐号码(未知，暂时不晓得填什么)
			//if (DataUtil.checkFieldValueNull(vo))return null;
			return vo;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取产品信息
	 * @param order_id
	 * @return
	 */
	private List<BSSProductVo> getBSSProductVo(String order_id) {
		List<BSSProductVo> ls = new ArrayList<BSSProductVo>();
		BSSProductVo vo = new BSSProductVo();
		vo.setProductID(CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.ESS_CODE)); // Y 产品标识
		vo.setProducttype("01"); // Y 产品模式 1：主产品 2：附加产品 （）
		vo.setProCompInfo(getBSSProCompInfoVo(vo.getProductID()));// * 产品下附加包及包元素（资费，服务） BSSProCompInfoVo
		//vo.setPackageElement(getPackageElement(vo.getProductId())); // * 产品下附加包及包元素（资费，服务） OpenDealApplyPackageElementVo
		//vo.setProductCode(null); // N 套包编码，北六无线上网卡必传
		if (DataUtil.checkFieldValueNull(vo))return null;
		ls.add(vo);
		OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(this.notNeedReqStrOrderId, new Object[0]);
	    List<OrderSubProductBusiRequest> subProductLists = ordertree.getOrderSubProductBusiRequest();
	    List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = null;

	    for (OrderSubProductBusiRequest subProduct : subProductLists) {
	      BSSProductVo subVo = new BSSProductVo();
	      subVo.setProductID(subProduct.getSub_pro_code());
	      subVo.setProducttype("02");
	      attrPackageSubProdList = ordertree.getAttrPackageSubProdBusiRequest();
	      if ((attrPackageSubProdList != null) && (attrPackageSubProdList.size() > 0)) {
	        List<BSSProCompInfoVo> proCompList = new ArrayList<BSSProCompInfoVo>();
	        BSSProCompInfoVo proCompVo = new BSSProCompInfoVo();
	        for (AttrPackageSubProdBusiRequest attrSubProd : attrPackageSubProdList) {
	          if (subProduct.getSub_pro_inst_id().equals(attrSubProd.getSubProd_inst_id())) {
	            proCompVo.setProComponentID(attrSubProd.getPackage_code());
	            proCompVo.setProComItemInfo(getProComItemInfo(subProduct.getSub_pro_code(), subProduct.getSub_pro_inst_id()));
	            proCompList.add(proCompVo);
	          }
	        }
	        subVo.setProCompInfo(proCompList);
	      } else {
	        subVo.setProCompInfo(null);
	      }
	      ls.add(subVo);
	    }

		return ls;
	}
	
	/**
	 * 获取可选包
	 * @param order_id
	 * @return
	 */
	private List<BSSProCompInfoVo> getBSSProCompInfoVo(String product_id) {
		List<BSSProCompInfoVo> ls = new ArrayList<BSSProCompInfoVo>();		
		List<BSSProComItemInfoVo> itemInfoVos = null;		
		List<AttrPackageBusiRequest> list = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getPackageBusiRequests();
		//没可选包就不用处理了
		if(list==null ||list.size()==0)return null;
		
		//获取可选包的依赖包等信息
		List<Map> map = AttrUtils.getInstance().doGetDependElementList(list, product_id,
																		CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.FIRST_PAYMENT));
		Map<String,BSSProCompInfoVo> checkMap = new HashMap<String,BSSProCompInfoVo>(0);
		if (map != null) {
			for (Map m : map) {
				BSSProCompInfoVo vo = new BSSProCompInfoVo();
				BSSProComItemInfoVo itemInfoVo = new BSSProComItemInfoVo();
				itemInfoVo.setProComItemID(m.get("element_id").toString());
				itemInfoVo.setProComItemType(m.get("element_type_code").toString());
				if(m.size()>0){
					String pid = m.get("package_id").toString();
					if(checkMap.containsKey(pid)){
						vo = checkMap.get(pid);
						vo.getProComItemInfo().add(itemInfoVo);
					}else{
						vo.setProComponentID(pid);
						itemInfoVos = new ArrayList<BSSProComItemInfoVo>();		
						itemInfoVos.add(itemInfoVo);
						vo.setProComItemInfo(itemInfoVos);
						checkMap.put(pid, vo);
					}
					ls.add(vo);
				}
			}
			return ls;
		} else {
			return null;
		}
	}
	
	/**
	 * 获取活动信息
	 * @param order_id
	 * @return
	 */
	private List<BSSActivityInfoVo> getBSSActivityInfoVo(String order_id) {
		List<BSSActivityInfoVo> ls = new ArrayList<BSSActivityInfoVo>();
		BSSActivityInfoVo vo = new BSSActivityInfoVo();
		//getOutPackageId(order_id)
		String actPlanId = getOutPackageId(order_id);
		//vo.setActPlanID(actPlanId); // Y 活动ID（合约编码）
		vo.setActionId(getActionId());//这个不知道怎么传
		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);//商品大类			
		vo.setResourcesType(CommonDataFactory.getInstance().getOtherDictVodeValue("bss_resources_type", type_id));// N 资源类型 03：移动电话 04：上网卡 05：上网本
		vo.setIdType("03");
		vo.setProductIdA(actPlanId);
		//vo.setResProcId(null);// N 预占流水，终端预占接口没有这个东西，因此我们也取不到
		
		// N 终端资源编码，一般是IMEI码
		//B环节的时候，终端串码还未录入，因此，传虚拟串码		
		String flowNode = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderExtBusiRequest().getFlow_trace_id();
		if(StringUtils.equals(flowNode, EcsOrderConsts.DIC_ORDER_NODE_B)){//B环节还没有串码,传虚拟的
			if(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id)){
				//合约机
				vo.setResourcesType("01");
				vo.setResourcesCode("");
				vo.setIsVirtual("1");
				vo.setMachineTypeCode(CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, SpecConsts.GOODS_SN));
			}else{
				/** 自备机不能传
				vo.setResourcesCode(CommonDataFactory.getInstance().getTerminalNum(notNeedReqStrOrderId));
				*/
			}
		}else{
			if(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(type_id)){
				//合约机
				vo.setResourcesType("01");
				vo.setResourcesCode(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM));
				vo.setIsVirtual("0");
				vo.setMachineTypeCode(CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, SpecConsts.GOODS_SN));
				vo.setIsOccupied("1");
			}else{
				/** 自备机不能传
				vo.setResourcesCode(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM));
				*/
			}
		}
		
		//合约机的商品属性中可以取到购机费用
		if(StringUtils.equals(type_id, EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE)){
			String rFee = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.MOBILE_PRICE);
			if(!StringUtils.isEmpty(rFee)){
				rFee = Double.parseDouble(rFee)*1000 +"";
				vo.setResourcesFee(rFee.substring(0, rFee.indexOf(".")));// N 资源费用
			}
		}else{
			vo.setResourcesFee(null);
			//vo.setActivityFee(null);
		}
		
		//vo.setPackageElement(null); // 活动下自选包  
		vo.setActPara(getBSSActParaVo(order_id)); // * 活动扩展字段   
		
		//没有活动id的，整个就不传了
		if(StringUtils.isEmpty(vo.getProductIdA()))return null;
		ls.add(vo);
		return ls;
	}
	
	/**
	 * 获取活动扩展信息
	 * @param order_id
	 * @return
	 */
	private List<BSSActParaVo> getBSSActParaVo(String order_id) {
		List<BSSActParaVo> ls = new ArrayList<BSSActParaVo>();
		BSSActParaVo vo = new BSSActParaVo();
		vo.setActParaId(null); // Y 活动扩展字段ID
		vo.setActParaValue(null); // Y 活动扩展字段值
		if (DataUtil.checkFieldValueNull(vo))return null;
		ls.add(vo);
		return ls;
	}

	/**
	 * 获取号码信息
	 * @param order_id
	 * @return
	 */
	private BSSNumberInfoVo getBSSNumberInfoVo(String order_id) {
		BSSNumberInfoVo vo = new BSSNumberInfoVo();
		//获取号码节点
		OrderPhoneInfoBusiRequest phoneInfo =CommonDataFactory.getInstance().getOrderTree(order_id).getPhoneInfoBusiRequest();
		vo.setNumGrade(phoneInfo.getClassId()+"");
		vo.setOccupiedId(phoneInfo.getProKey());
		if (DataUtil.checkFieldValueNull(vo))return null;
		return vo;
	}
	
	public void setCreateOrExtendsAcct(String createOrExtendsAcct) {
		this.CreateOrExtendsAcct = createOrExtendsAcct;
	}

	public BSSNewCustomerInfoVo getNewCustomerInfo() {
		return getBSSNewCustomerInfoVo(notNeedReqStrOrderId);
	}

	public void setNewCustomerInfo(BSSNewCustomerInfoVo newCustomerInfo) {
		this.NewCustomerInfo = newCustomerInfo;
	}

	public String getAccountID() {
		return AccountID;
	}

	public void setAccountID(String accountID) {
		this.AccountID = accountID;
	}

	public BSSAccountInfoVo getAccountInfo() {
		return getBSSAccountInfoVo(notNeedReqStrOrderId);
	}

	public void setAccountInfo(BSSAccountInfoVo accountInfo) {
		this.AccountInfo = accountInfo;
	}

	public List<BSSProductVo> getProductInfo() {
		return getBSSProductVo(notNeedReqStrOrderId);
	}

	public void setProductInfo(List<BSSProductVo> productInfo) {
		this.ProductInfo = productInfo;
	}

	public List<BSSSpclSvcInfoVo> getSpclSvcInfo() {
		return null;//这期不考虑
	}

	public void setSpclSvcInfo(List<BSSSpclSvcInfoVo> spclSvcInfo) {
		this.SpclSvcInfo = spclSvcInfo;
	}

	public BSSVacInfosVo getVacInfos() {
		return null;//这期不考虑
	}

	public void setVacInfos(BSSVacInfosVo vacInfos) {
		this.VacInfos = vacInfos;
	}

	public List<BSSActivityInfoVo> getActivityInfo() {
		return getBSSActivityInfoVo(notNeedReqStrOrderId);
	}

	public void setActivityInfo(List<BSSActivityInfoVo> activityInfo) {
		this.ActivityInfo = activityInfo;
	}

	public BSSNumberInfoVo getNumberInfo() {
		return getBSSNumberInfoVo(notNeedReqStrOrderId);
	}

	public void setNumberInfo(BSSNumberInfoVo numberInfo) {
		this.NumberInfo = numberInfo;
	}

	public void setUserInfo(BSSUserInfoVo userInfo) {
		this.UserInfo = userInfo;
	}

	public List<BSSParaVo> getPara() {
		List<BSSParaVo> plist = new ArrayList<BSSParaVo>();
		//4G的加参数
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		if(StringUtils.equals(EcsOrderConsts.NET_TYPE_4G, net_type)){
			BSSParaVo vo = new BSSParaVo();
			vo.setParaID(EcsOrderConsts.AOP_4G_PARA_SPEED);
			vo.setParaValue(EcsOrderConsts.AOP_4G_PARA_SPEED_VALUE);
			plist.add(vo);
		}
		if (plist.size() == 0)return null;
		return plist;
	}
	
	public void setPara(List<BSSParaVo> para) {
		this.para = para;
	}

	public BSSUserInfoVo getUserInfo() {
		return getBSSUserInfoVo(notNeedReqStrOrderId);
	}
	
	/**
	 * 获取转兑包的bss_code
	 * @return
	 */
	public String getActionId(){
		List<AttrZhuanDuiBaoBusiRequest> zhuanDuiBaoBusiRequest = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getZhuanDuiBaoBusiRequest();
		if (null != zhuanDuiBaoBusiRequest && zhuanDuiBaoBusiRequest.size() > 0) {
			AttrZhuanDuiBaoBusiRequest zhuanDuiBao = zhuanDuiBaoBusiRequest.get(0);
			String goods_id = zhuanDuiBao.getGoods_id();
			Map spec = SpecUtils.getGoodSpec(goods_id);
			return spec.get("bss_code").toString();
		}
		return "";
	}

	/**
	 * 获取附加产品可选包
	 * @param product_id
	 * @param sub_inst_id
	 * @return
	 */
	public List<BSSProComItemInfoVo> getProComItemInfo(String product_id, String sub_inst_id){
		List<BSSProComItemInfoVo> ls = new ArrayList<BSSProComItemInfoVo>();
		List<AttrPackageBusiRequest> list = new ArrayList<AttrPackageBusiRequest>();
		List<AttrPackageSubProdBusiRequest> attrPackageSubProdList = CommonDataFactory.getInstance().getOrderTree(this.notNeedReqStrOrderId, new Object[0]).getAttrPackageSubProdBusiRequest();
		
		AttrPackageBusiRequest attrPackage = null;
		for (AttrPackageSubProdBusiRequest subPackage : attrPackageSubProdList) {
			if (sub_inst_id.equals(subPackage.getSubProd_inst_id())) {
				attrPackage = new AttrPackageBusiRequest();
				attrPackage.setElementCode(subPackage.getElement_code());
				list.add(attrPackage);
			}
		}
		
		List<Map> map = AttrUtils.getInstance().doGetDependElementList(list, product_id, 
			      CommonDataFactory.getInstance().getAttrFieldValue(this.notNeedReqStrOrderId, "first_payment"));
		if(null != map){
			for (Map m : map) {
				BSSProComItemInfoVo vo = new BSSProComItemInfoVo();
				if (m.size() > 0) {
					vo.setProComItemID(m.get("element_id").toString());
					vo.setProComItemType(m.get("element_type_code").toString());
					vo.setProComItemAttr(null);
					ls.add(vo);
				}
			}
			return ls;
		}
		return null;
	}
}
