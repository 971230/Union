package zte.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.CustInfoModPara;
import zte.net.ecsord.params.ecaop.req.vo.CustInfoModVo;
import zte.net.ecsord.params.ecaop.req.vo.DeveloperInfo4CustInfoMod;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * @see 暂停
 * @author song.qi 客户资料信息修改--用于对客户资料进行修改、返档、以及证件类型、证件号码的变更
 *         ecaop.trades.serv.curt.custinfo.mod
 */
public class CustInfoModReq extends ZteRequest {

	private static final long serialVersionUID = 1L;

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
	@ZteSoftCommentAnnotationParam(name = "渠道标示", type = "String", isNecessary = "N", desc = "eModeCode：渠道标示")
	private String eModeCode;
	@ZteSoftCommentAnnotationParam(name = "BSS交互订单号,外围系统订单ID", type = "String", isNecessary = "Y", desc = "ordersId：外围系统订单ID")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name = "号码", type = "String", isNecessary = "Y", desc = "号码")
	private String serialNumber;
	@ZteSoftCommentAnnotationParam(name = "办理业务系统：1：ESS2：CBSS不传默认ESS", type = "Integer", isNecessary = "Y", desc = "1：ESS2：CBSS不传默认ESS")
	private Integer opeSysType;
	@ZteSoftCommentAnnotationParam(name = "电信类型编码", type = "String", isNecessary = "Y", desc = "电信类型编码")
	private String serviceClassCode;
	@ZteSoftCommentAnnotationParam(name = "区号", type = "String", isNecessary = "Y", desc = "区号")
	private String areaCode;
	@ZteSoftCommentAnnotationParam(name = "操作标识", type = "String", isNecessary = "Y", desc = "操作标识")
	private String operTag;
	@ZteSoftCommentAnnotationParam(name = "照片标识", type = "String", isNecessary = "Y", desc = "照片标识")
	private String photoTag;
	@ZteSoftCommentAnnotationParam(name = "实人认证标志", type = "String", isNecessary = "Y", desc = "实人认证标志")
	private String realPersonTag;
	@ZteSoftCommentAnnotationParam(name = "客户信息", type = "String", isNecessary = "Y", desc = "客户信息")
	private List<CustInfoModVo> custInfo;
	@ZteSoftCommentAnnotationParam(name = "返档人信息", type = "String", isNecessary = "Y", desc = "返档人信息")
	private List<DeveloperInfo4CustInfoMod> developerInfo;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "String", isNecessary = "Y", desc = "保留字段")
	private List<CustInfoModPara> para;

	private EmpOperInfoVo essOperInfo;

	@Override
	public String getApiMethodName() {
		return "ecaop.trades.serv.curt.custinfo.mod";
	}

	@Override
	public void check() throws ApiRuleException {
	}

	public String getOperatorId() {
		this.operatorId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		if (StringUtils.isEmpty(this.operatorId)) {
			this.operatorId = getEssInfo().getEss_emp_id();
		}
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
		this.province = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_PROVINCE_CODE);
		if (this.province.trim().length() != 2) {
			this.province = getEssInfo().getProvince();
		}
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		this.city = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		if (this.city.trim().length() != 3) {
			this.city = CommonDataFactory.getInstance().getOtherDictVodeValue("city", this.city);
		}
		if (StringUtils.isEmpty(this.city)) {
			this.city = getEssInfo().getCity();
		}
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		this.district = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DISTRICT);
		if (StringUtils.isEmpty(this.district)) {
			this.district = getEssInfo().getDistrict();
		}
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {// 渠道ID-操作点-受理点
		this.channelId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
		if (StringUtils.isEmpty(this.channelId)) {
			this.channelId = getEssInfo().getChannel_id();
		}
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		this.channelType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CHANNEL_TYPE);
		if (StringUtils.isEmpty(this.channelType)) {
			this.channelType = getEssInfo().getChannel_type();
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

	public String getOrderId() {// 非空，不能带字母
		orderId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);
		if (StringUtils.isEmpty(orderId)) {
			orderId = notNeedReqStrOrderId.replaceAll("[a-zA-Z]", "");
		}
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getOpeSysType() {
		// 暂无其他类型
		opeSysType = 1;
		String type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.IS_AOP);
		if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(type)) {
			opeSysType = 2;
		}
		return opeSysType;
	}

	public void setOpeSysType(Integer opeSysType) {
		this.opeSysType = opeSysType;
	}

	public String getSerialNumber() {
		serialNumber = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getServiceClassCode() {
		// serviceClassCode =
		// CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,
		// AttrConsts.SERVICE_CLASS_CODE);
		serviceClassCode = "0000";// 电信业务类别（电信业务类别）0000是移网 0100是固网
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String AOP_GW_GOODS_TYPE = cacheUtil.getConfigInfo("AOP_GW_GOODS_TYPE");
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_TYPE);
		if (AOP_GW_GOODS_TYPE.contains(goods_type)) {
			serviceClassCode = "0100";
		}
		return serviceClassCode;
	}

	public void setServiceClassCode(String serviceClassCode) {
		this.serviceClassCode = serviceClassCode;
	}

	public String getAreaCode() {

		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getOperTag() {
		/*
		 * 操作标识： 01 客户资料修改（可修改除证件类型+证件号码之外的其它信息） 02 新用户的客户资料返档 03
		 * 证件类型、证件号码变更（包括非关键信息的修改） 04 客户资料修改（可修改除证件类型+证件号码+客户姓名之外的其它信息） 05
		 * 老用户的客户资料返档 07 集团用户未返档 08 集团用户已返档
		 */
		// if (StringUtils.isEmpty(operTag)) {
		// operTag = "02";
		// }
		return operTag;// 外部set
	}

	public void setOperTag(String operTag) {
		this.operTag = operTag;
	}

	public String getPhotoTag() {
		// 照片标识：1：已拍照（拍照且照片上传无纸化可以传1，不满足条件不传）
		return photoTag;
	}

	public void setPhotoTag(String photoTag) {
		this.photoTag = photoTag;
	}

	public String getRealPersonTag() {
		// 实人认证标志：1：实名-静态 2：实名-动态（通过人脸比对的可以传1，通过人脸比对及活体认证的可以传2，不满足条件不传）
		return realPersonTag;
	}

	public void setRealPersonTag(String realPersonTag) {
		this.realPersonTag = realPersonTag;
	}

	public List<CustInfoModVo> getCustInfo() {
		custInfo = new ArrayList<CustInfoModVo>();
		CustInfoModVo cust = new CustInfoModVo();
		cust.setCustId(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CUST_ID));
		cust.setCustName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_OWNER_NAME));
		cust.setCertAddr(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_ADDRESS));
		String certi_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERTI_TYPE);
		cust.setCertType(CommonDataFactory.getInstance().getOtherDictVodeValue("aop_cert_type", certi_type));
		cust.setCertNum(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM));
		cust.setPostAddress(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIP_ADDR));
		cust.setContactName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIP_NAME));
		cust.setContactPhone(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REVEIVER_MOBILE));
		cust.setCustCertPictureUrl(null);
		cust.setCustCertPictureName(null);
		cust.setCheckType(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CHECK_TYPE));
		cust.setCerValidFlag(null);// 没有这个标记，只传有效期
		cust.setCertExpireDate(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_FAILURE_TIME));
		cust.setCustPhone(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REVEIVER_MOBILE));

		custInfo.add(cust);
		return custInfo;
	}

	public void setCustInfo(List<CustInfoModVo> custInfo) {
		this.custInfo = custInfo;
	}

	public List<DeveloperInfo4CustInfoMod> getDeveloperInfo() {
		String developerId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DEVELOPMENT_CODE);
		String developerName = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CUST_ID);
		String developerPhone = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DEVELOPMENT_NAME);
		String agentChannelId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CHA_CODE);
		if (StringUtils.isEmpty(developerId) || StringUtils.isEmpty(developerName) || StringUtils.isEmpty(developerPhone) || StringUtils.isEmpty(agentChannelId)) {
			return null;
		}
		developerInfo = new ArrayList<DeveloperInfo4CustInfoMod>();
		DeveloperInfo4CustInfoMod dpInfo = new DeveloperInfo4CustInfoMod();
		dpInfo.setDeveloperId(developerId);
		dpInfo.setDeveloperName(developerName);
		dpInfo.setDeveloperPhone(developerPhone);
		dpInfo.setAgentChannelId(agentChannelId);
		developerInfo.add(dpInfo);
		
		
		return developerInfo;
	}

	public void setDeveloperInfo(List<DeveloperInfo4CustInfoMod> developerInfo) {
		this.developerInfo = developerInfo;
	}

	public List<CustInfoModPara> getPara() {
		// return para;
		return null;
	}

	public void setPara(List<CustInfoModPara> para) {
		this.para = para;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public EmpOperInfoVo getEssInfo() {
		if (essOperInfo == null) {
			String opercode = CommonDataFactory.getInstance().getOperatorCodeByOrderFrom(notNeedReqStrOrderId);
			if (StringUtil.isEmpty(opercode)) {
				opercode = "zjplxk";
			}
			String cityId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
			String otherCityCode = CommonDataFactory.getInstance().getOtherDictVodeValue("city", cityId);
			IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
			String sql = "select * from es_operator_rel_ext a where a.order_gonghao ='" + opercode + "' and a.city='" + otherCityCode + "' and a.source_from ='" + ManagerUtils.getSourceFrom() + "'";
			essOperInfo = support.queryForObject(sql, EmpOperInfoVo.class);
		}
		return essOperInfo;
	}

}
