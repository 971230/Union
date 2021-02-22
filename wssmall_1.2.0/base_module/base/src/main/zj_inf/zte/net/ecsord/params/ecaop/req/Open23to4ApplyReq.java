/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.AcctountInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.ActParaVo;
import zte.net.ecsord.params.ecaop.req.vo.ActivityInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.CustInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.CustRemarkReqVo;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.NewCustInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.NiceInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.NumIdReqVo;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;
import zte.net.ecsord.params.ecaop.req.vo.PayInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.ProdPackElementReqVo;
import zte.net.ecsord.params.ecaop.req.vo.ProductInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.UserInfoReqVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * @author XMJ
 * @version 2015-06-23
 * @see 2-3G转4G开户处理申请 
 * 
 */
@SuppressWarnings("rawtypes")
public class Open23to4ApplyReq extends ZteRequest {
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "operatorId：操作员ID")
	private String operatorId;
	
	@ZteSoftCommentAnnotationParam(name = "省份", type = "String", isNecessary = "Y", desc = "province：省份")
	private String province;
	
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "city：地市")
	private String city;
	
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "N", desc = "district：区县")
	private String district;
	
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	private String channelId;
	
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型:参考附录渠道类型编码")
	private String channelType;
	
	@ZteSoftCommentAnnotationParam(name = "渠道来源标示", type = "String", isNecessary = "N", desc = "eModeCode：渠道来源标示")
	private String eModeCode;
	
	@ZteSoftCommentAnnotationParam(name = "外围系统订单ID", type = "String", isNecessary = "Y", desc = "ordersId：外围系统订单ID")
	private String ordersId;
	
	@ZteSoftCommentAnnotationParam(name = "办理业务系统", type = "String", isNecessary = "N", desc = "opeSysType：办理业务系统 1：ESS 2：CBSS")
	private String opeSysType;
	
	@ZteSoftCommentAnnotationParam(name = "号码资料", type = "NumIdReqVo", isNecessary = "N", desc = "numId：号码资料(无线上网卡不一定存在)")
	private List<NumIdReqVo> numId;
	
	@ZteSoftCommentAnnotationParam(name = "客户信息", type = "CustomerInfoVo", isNecessary = "N", desc = "CustomerInfoVo：客户信息")
	private List<CustInfoReqVo> customerInfo;
	
	@ZteSoftCommentAnnotationParam(name = "账户信息", type = "AcctInfoVo", isNecessary = "N", desc = "acctInfo：账户信息")
	private List<AcctountInfoReqVo> acctInfo;
	
	@ZteSoftCommentAnnotationParam(name = "用户信息", type = "UserInfoVo", isNecessary = "N", desc = "userInfo：用户信息")
	private List<UserInfoReqVo> userInfo;
	
	@ZteSoftCommentAnnotationParam(name = "客户业务发展区域", type = "String", isNecessary = "N", desc = "recomDis：客户业务发展区域")
	private String recomDis;
	
	@ZteSoftCommentAnnotationParam(name = "发展员工", type = "String", isNecessary = "Y", desc = "recomPersonId：发展员工")
	private String recomPersonId;
	
	@ZteSoftCommentAnnotationParam(name = "发展人名称", type = "String", isNecessary = "Y", desc = "recomPersonName：发展人名称")
	private String recomPersonName;
	
	
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
	private List<ParaVo> para;
	
	
	
	public String geteModeCode() {
		return eModeCode;
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

	public String getOrdersId() {
		ordersId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
		if (StringUtils.isBlank(ordersId)) return null;
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getOpeSysType() {
//		if (StringUtils.isBlank(opeSysType)) return null;
		return EcsOrderConsts.AOP_OPE_SYS_TYPE_2; // 业务类型 1：ESS，2：CBSS（默认给2，根据号码网别判断,3G,4G现在办理业务都是在CBSS系统办理）
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}


	public List<NumIdReqVo> getNumId() {
		numId = getNumIdReqVo(notNeedReqStrOrderId);
		if (numId==null || numId.size() <= 0){return null;}
		return numId;
	}

	public void setNumId(List<NumIdReqVo> numId) {
		this.numId = numId;
	}

	
	public String getRecomDis() {
		if (StringUtils.isBlank(recomDis)) {return null;}
		return recomDis;
	}

	public void setRecomDis(String recomDis) {
		this.recomDis = recomDis;
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
		//recomPersonName = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.DEVELOPMENT_NAME);
	    recomPersonName = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,"develop_name_new");

		if (StringUtils.isBlank(recomPersonName)) return null;
		return recomPersonName;
	}

	public void setRecomPersonName(String recomPersonName) {
		this.recomPersonName = recomPersonName;
	}



	public List<CustInfoReqVo> getCustomerInfo() {
		customerInfo = getCustomerInfoVo(notNeedReqStrOrderId);
		if (customerInfo==null || customerInfo.size() <= 0)return null;
		return customerInfo;
	}

	public void setCustomerInfo(List<CustInfoReqVo> customerInfo) {
		this.customerInfo = customerInfo;
	}

	public List<AcctountInfoReqVo> getAcctInfo() {
		acctInfo = getAcctInfoVo(notNeedReqStrOrderId);
		if (acctInfo==null || acctInfo.size() <= 0)return null;
		return acctInfo;
	}

	public void setAcctInfo(List<AcctountInfoReqVo> acctInfo) {
		this.acctInfo = acctInfo;
	}

	public List<UserInfoReqVo> getUserInfo() {
		userInfo = getUserInfoVo(notNeedReqStrOrderId);
		if (userInfo==null || userInfo.size() <= 0)return null;
		return userInfo;
	}

	public void setUserInfo(List<UserInfoReqVo> userInfo) {
		this.userInfo = userInfo;
	}


	public List<ParaVo> getPara() {
		return para;
	}

	public void setPara(List<ParaVo> para) {
		this.para = para;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	
	
	/**
	 * 获取号码信息
	 * @param order_id
	 * @return
	 */
	private List<NumIdReqVo> getNumIdReqVo(String order_id) {
		List<NumIdReqVo> ls = new ArrayList<NumIdReqVo>();
		NumIdReqVo vo = new NumIdReqVo();
		vo.setSerialNumber(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM)); // Y 号码
		//获取号码节点
		OrderPhoneInfoBusiRequest phoneInfo =CommonDataFactory.getInstance().getOrderTree(order_id).getPhoneInfoBusiRequest();
		vo.setProKey(phoneInfo.getProKey()); // Y 资源预占关键字 
		vo.setNiceInfo(null);
		ls.add(vo);
		return ls;
	}
	
	/**
	 * 获取靓号信息
	 * @param order_id
	 * @return
	 */
	@SuppressWarnings("unused")
	private NiceInfoReqVo getGoodNumInfoVo(OrderPhoneInfoBusiRequest phoneInfo) {
		NiceInfoReqVo vo = new NiceInfoReqVo ();
		
		vo.setAdvancePay(phoneInfo.getAdvancePay()); // Y 预存话费金额
		vo.setAdvanceCom(phoneInfo.getAdvanceCom()); // N 普通预存
		vo.setAdvanceSpe(phoneInfo.getAdvanceSpe()); // N 专项预存
		vo.setNumThawTim(phoneInfo.getNumThawTim()); // N 返还时长
		vo.setNumThawPro(phoneInfo.getNumThawPro()); // N 分月返还金额
		vo.setClassId(phoneInfo.getClassId()+""); // Y 号码等级：1：一级靓号 2：二级靓号 3：三级靓号 4：四级靓号 5：五级靓号 6：六级靓号 9：普通号码
		vo.setLowCostChe(phoneInfo.getLowCostChe()); // N 考核期最低消费
		vo.setTimeDurChe(phoneInfo.getTimeDurChe()); // N 考核时长
		vo.setChangeTagChe(phoneInfo.getChangeTagChe()+""); // N 考核期是否过户 0:允许 1：不允许
		vo.setCancelTagChe(phoneInfo.getCancelTagChe()+""); // N 考核期是否销户 0:允许 1：不允许
		vo.setBremonChe(phoneInfo.getBremonChe()); // N 考核期违约金月份
		vo.setLowCostPro(phoneInfo.getLowCostPro()); // N 协议期最低消费
		vo.setTimeDurPro(phoneInfo.getTimeDurPro()); // N 协议时长 当值为00000时表示无期限
		vo.setChangeTagPro(phoneInfo.getChangeTagPro()+""); // N 协议期是否过户 0:允许 1：不允许
		vo.setCancelTagPro(phoneInfo.getCancelTagPro()+""); // N 协议期是否销户 0:允许 1：不允许
		vo.setBroMonPro(phoneInfo.getBroMonPro()); // N 协议期违约金月份
		return vo;
	}
	
	
	/**
	 * 获取客户信息
	 * @param order_id
	 * @return
	 */
	private List<CustInfoReqVo> getCustomerInfoVo(String order_id) {
		List<CustInfoReqVo> ls = new ArrayList<CustInfoReqVo>();
		CustInfoReqVo vo = new CustInfoReqVo();
		vo.setAuthTag("1"); // Y 鉴权标识 0：未鉴权 1：已鉴权(如何理解？是否和客户信息校验理解一样？)
		vo.setRealNameType("0"); // Y 客户实名标识 0：实名 1：匿名(现在都是0)
		vo.setCustType(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_OLD)); // Y 新老客户标识 0：新增客户 1：老客户(完客户资料校验接口之后就知道是新客户还是老客户了)
		//vo.setCustId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CUST_ID)); // N 老客户ID
		//vo.setNewCustomerInfo(getNewCustInfoReqVo(order_id)); // 新客户信息      
		ls.add(vo);
		return ls;
	}
	
	/**
	 * 获取新客户信息 
	 * @param order_id
	 * @return
	 */
	private List<NewCustInfoReqVo> getNewCustInfoReqVo(String order_id) {
		List<NewCustInfoReqVo> ls = new ArrayList<NewCustInfoReqVo>();
		NewCustInfoReqVo vo = new NewCustInfoReqVo();
		vo.setCertType(getCertType(order_id)); // Y 证件类型 参考附录证件类型说明
		vo.setCertNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM)); // Y 证件号码
		vo.setCertAdress(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS)); // Y 证件地址
		vo.setCustomerName(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_OWNER_NAME)); // Y 客户名称（不能全数字）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_FAILURE_TIME).toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		vo.setCertExpireDate(sd.format(date)); // Y 格式：yyyymmdd 证件失效日期(考虑默认)
		vo.setContactPerson(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_NAME)); // N 联系人（不能全数字）
		vo.setContactPhone(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REFERENCE_PHONE)); // Y 联系人电话>6位
		vo.setContactAddress(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_ADDR)); // N 通讯地址
		vo.setCustType(getCustType(order_id)); // N 客户类型： 01：个人客户 02：集团客户
		vo.setCustomerRemark(null); // N 客户备注     整个节点不传
		ls.add(vo);
		return ls;
	}
	
	/**
	 * 获取客户备注信息
	 * @param order_id
	 * @return
	 */
	private List<CustRemarkReqVo> getCustRemarkReqVo(String order_id) {
		List<CustRemarkReqVo> ls = new ArrayList<CustRemarkReqVo>();
		CustRemarkReqVo vo = new CustRemarkReqVo();
		vo.setCustomerRemarkId("");// Y 客户备注ID(未知，不晓得填什么)
		vo.setCustomerRemarkValue(""); // Y客户备注信息(未知，不晓得填什么)
		ls.add(vo);
		return ls;
	}
	
	/**
	 * 获取帐号信息
	 * @param order_id
	 * @return
	 */
	private List<AcctountInfoReqVo> getAcctInfoVo(String order_id) {
		List<AcctountInfoReqVo> ls = new ArrayList<AcctountInfoReqVo>();
		AcctountInfoReqVo vo = new AcctountInfoReqVo();
		vo.setCreateOrExtendsAcct("1");
		vo.setAccountPayType(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BILL_TYPE)); // Y 账户付费方式，默认：10 参考附录证件类型说明
		vo.setDebutySn(""); // N 合帐号码(未知，暂时不晓得填什么)
		ls.add(vo);
		return ls;
	}
	
	/**
	 * 获取用户信息
	 * @param order_id
	 * @return
	 */
	private List<UserInfoReqVo> getUserInfoVo(String order_id) {
		List<UserInfoReqVo> ls = new ArrayList<UserInfoReqVo>();
		UserInfoReqVo vo = new UserInfoReqVo();
		vo.setUserType("2"); // Y 用户类型 1：新用户 2：老用户 
		vo.setBipType(getBipType_id(order_id)); // Y 业务类型： 1：号卡类业务 2：合约类业务 3：上网卡 4：上网本 5：其它配件类 6：自备机合约类业务
		vo.setIs3G(getNet_type(order_id)); // Y 0-2G 1-3G 2-4G
		vo.setSerType(getSerType(order_id)); // Y 受理类型 1：后付费 2：预付费 3：准预付费
		vo.setPackageTag(getPackageTag(order_id)); // Y 套包销售标记 0：非套包销售 1：套包销售
		vo.setFirstMonBillMode(getFirstBillMode(order_id)); // N 首月付费模式 01：标准资费（免首月月租） 02：全月套餐 03：套餐减半
		vo.setCheckType(getCheckType(order_id)); // N 认证类型：01：本地认证 02：公安认证 03：二代证读卡器 （默认 02）
		vo.setCreditVale(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CREDIT_CLASS)); // N 信用等级，（3G后付费业务取值范围A、B、C）
		vo.setCheckCreditVale(""); // N 用户选择信用等级（3G后付费业务取值范围A、B、C） （2015-05-06 朱红玉说未知）
		vo.setUserPwd(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PASSWORD)); // N 用户密码
		vo.setProduct(getProductVo(order_id)); // + 开户时选择的产品信息 ProductInfoReqVo (需要一个4G自由组合套餐数据样例？)
		vo.setGroupFlag(getGroupFlag(order_id)); // N 集团标识 0：非集团用户 1：集团用户
		if ("1".equals(getGroupFlag(order_id))) {//集团才有担保
			vo.setGroupId(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GROUP_CODE));// N 集团ID
			vo.setAppType(getAppType(order_id));// N 应用类别，当加入具体集团时为必填 0：行业应用 1：非行业应用
			vo.setSubAppType("");// N 应用子类别 参考附录应用子类别说明
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
			vo.setGuCertType(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GUARANTOR_CERTI_TYPE));// N 被担保人证件类型
			vo.setGuCertNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GUARANTOR_CERTI_NO) );// N 被担保人证件号码
		}
		vo.setPayInfo(getPayInfoReqVo(order_id));// Y 支付信息  PayInfoReqVo
		vo.setActivityInfo(getActivityInfo(order_id)); // 开户时活动信息   ActivityInfoReqVo
		ls.add(vo);
		return ls;
	}
	
	/**
	 * 获取产品信息
	 * @param order_id
	 * @return
	 */
	private List<ProductInfoReqVo> getProductVo(String order_id) {
		List<ProductInfoReqVo> ls = new ArrayList<ProductInfoReqVo>();
		ProductInfoReqVo vo = new ProductInfoReqVo();
		vo.setProductId(CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.ESS_CODE)); // Y 产品标识
		vo.setProductMode("1"); // Y 产品模式 1：主产品 2：附加产品 （）
		vo.setPackageElement(getPackageElement(order_id)); // * 产品下附加包及包元素（资费，服务） ProdPackElementReqVo
		vo.setProductCode(""); // N 套包编码，北六无线上网卡必传
		ls.add(vo);
		//获取赠品附加产品
//		List<AttrGiftInfoBusiRequest> giftInfoBusiRequests = CommonDataFactory.getInstance().getOrderTree(order_id).getGiftInfoBusiRequests();
//		if(giftInfoBusiRequests.size()>0){
//			for(AttrGiftInfoBusiRequest gift : giftInfoBusiRequests){
//				ProductInfoReqVo giftvo = new ProductInfoReqVo();
//				giftvo.setProductId(gift.getGift_id());
//				giftvo.setProductMode("2");
//				ls.add(giftvo);				
//			}
//		}

		return ls;
	}
	
	/**
	 * 获取支付信息
	 * @param order_id
	 * @return
	 */
	private PayInfoReqVo getPayInfoReqVo(String order_id) {
		PayInfoReqVo vo = new PayInfoReqVo();
		vo.setPayType(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BILL_TYPE)); // 付费方式
		vo.setPayOrg(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PAY_PROVIDER_NAME)); // 支付机构名称
		vo.setPayNum(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BANK_ACCOUNT)); // 支付账号
		return vo;
	}
	
	/**
	 * 获取活动信息
	 * @param order_id
	 * @return
	 */
	private List<ActivityInfoReqVo> getActivityInfo(String order_id) {
		List<ActivityInfoReqVo> ls = new ArrayList<ActivityInfoReqVo>();
		ActivityInfoReqVo vo = new ActivityInfoReqVo();
		vo.setActPlanId(getOutPackageId(order_id)); // Y 活动ID（合约编码）
		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);//商品大类		
		vo.setResourcesType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_resources_type", type_id));// N 资源类型 03：移动电话 04：上网卡 05：上网本
		vo.setResProcId("");// N 预占流水，保持前后多次资源操作的内容一致，保证多次操作是同一订单的前后关联
		vo.setResourcesCode(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.TERMINAL_NUM));// N 终端资源编码，一般是IMEI码
		vo.setResourcesFee("");// N 资源费用
		vo.setPackageElement(null/*getPackageElement(order_id)*/); // 活动下自选包  ProdPackElementReqVo（2015-05-19 朱红玉说没有活动附件去掉先）
		vo.setActPara(null); // * 活动扩展字段    ActParaVo(2015-05-19 朱红玉说  actPara这种下面每个字段都没值的，整个节点都不要传)
		if(StringUtils.isEmpty(vo.getActPlanId()))return null;
		ls.add(vo);
		return ls;
	}
	
	/**
	 * 获取可选包
	 * @param order_id
	 * @return
	 */
	private List<ProdPackElementReqVo> getPackageElement(String order_id) {
		List<ProdPackElementReqVo> ls = new ArrayList<ProdPackElementReqVo>();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<AttrPackageBusiRequest> p_list = orderTree.getPackageBusiRequests();
		if (p_list != null && p_list.size() > 0) {
			for (AttrPackageBusiRequest busi : p_list) {
				ProdPackElementReqVo vo = new ProdPackElementReqVo();
				vo.setPackageId(busi.getPackageCode());
				vo.setElementId(busi.getElementCode());
				vo.setElementType(busi.getElementType());
				ls.add(vo);
			}
			return ls;
		} else {
			return null;
		}
	}
	
	/**
	 * 获取活动扩展信息
	 * @param order_id
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<ActParaVo> getActParaVo(String order_id) {
		List<ActParaVo> ls = new ArrayList<ActParaVo>();
		ActParaVo vo = new ActParaVo();
		ls.add(vo);
		return ls;
	}
	
	
	/**
	 * 获取是否集团标志
	 * @param order_id
	 * @return
	 */
	private String getGroupFlag(String order_id) {
		String custType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_is_group", custType);
	}
	
	/**
	 * 获取受理类型
	 * @param order_id
	 * @return
	 */
	private String getSerType(String order_id) {
		// 受理类型
		String serType = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_pay_type", serType);
	}
	
	/**
	 * 获取首月资费
	 * @param order_id
	 * @return
	 */
	private String getFirstBillMode(String order_id) {
		String first_payment = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.FIRST_PAYMENT);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_first_payment", first_payment);
	}
	
	


	
	public String geteModeCode(String order_id) {
		
		
		return eModeCode;
	}

	public void seteModeCode(String eModeCode) {
		this.eModeCode = eModeCode;
	}

	/**
	 * 获取客户类型
	 * @param order_id
	 * @return
	 */
	private String getCustType(String order_id) {
		String custType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CUSTOMER_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_user_type", custType);
	}
	
	/**
	 * 获取套包销售标记
	 * @param order_id
	 * @return
	 */
	private String getPackageTag(String order_id) {
		String packageSale = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PACKAGE_SALE);
		String packageTag =  CommonDataFactory.getInstance().getOtherDictVodeValue("aop_package_sale", packageSale);
		if (StringUtils.isBlank(packageTag)) {
			packageTag = "0"; 
		} 		
		return packageTag;
	}
	
	/**
	 * 获取业务类型
	 * @param order_id
	 * @return
	 */
	private String getBipType_id(String order_id) {
		String type_id = CommonDataFactory.getInstance().getGoodSpec(order_id, "", SpecConsts.TYPE_ID);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_bip_type", type_id);
	}
	
	/**
	 * 获取网别
	 * @param order_id
	 * @return
	 */
	private String getNet_type(String order_id) {
		String net_type = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_mobile_net", net_type);
	}
	
	/**
	 * 获取行业应用
	 * @param order_id
	 * @return
	 */
	private String getAppType(String order_id) {
		String appType = "";
		String groupFlag = getGroupFlag(order_id);
		//return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_user_type", custType);
		if (groupFlag.equals("0")) { // 非集团客户
			appType = "1"; // 1：非行业应用
		} else if (groupFlag.equals("1")) { // 集团客户
			appType = "0"; // 0：行业应用
		}
		return appType;
	}
	
	/**
	 * 获取证件类型
	 * @param order_id
	 * @return
	 */
	private String getCertType(String order_id) {
		String cert_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_cert_type", cert_type);
	}
	private String getCheckType(String order_id) {
		String checkType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_EXAMINE_CARD);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_check_type", checkType);
	}
	
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
	
	
	@NotDbField
	public void check() throws ApiRuleException {

	}

	@NotDbField
	public String getApiMethodName() {
		return "ecaop.trades.sell.mob.newu.23to4open.app";
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
