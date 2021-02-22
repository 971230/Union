package zte.net.ecsord.params.ecaop.req;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;

@SuppressWarnings("rawtypes")
public class CardDataQryRequestZJ extends ZteRequest {

	private static final long serialVersionUID = 592709333290748934L;
	@ZteSoftCommentAnnotationParam(name="操作员ID",type="String",isNecessary="Y",desc="operatorId：操作员ID")
	private String operatorId;
	@ZteSoftCommentAnnotationParam(name="省分",type="String",isNecessary="Y",desc="province：省分")
	private String province;
	@ZteSoftCommentAnnotationParam(name="地市",type="String",isNecessary="Y",desc="city：地市")
	private String city;
	@ZteSoftCommentAnnotationParam(name="区县",type="String",isNecessary="Y",desc="district：区县")
	private String district;
	@ZteSoftCommentAnnotationParam(name="渠道编码",type="String",isNecessary="Y",desc="channelId：渠道编码")
	private String channelId;
	@ZteSoftCommentAnnotationParam(name="渠道类型",type="String",isNecessary="Y",desc="channelType：渠道类型")
	private String channelType;
	@ZteSoftCommentAnnotationParam(name="写卡目的：0：新开户；1：补换卡",type="String",isNecessary="Y",desc="cardUseType：写卡目的：0：新开户；1：补换卡")
	private String cardUseType;
	@ZteSoftCommentAnnotationParam(name="重复查询标记0：首次读卡,1：重复读卡,2: 重复写卡",type="String",isNecessary="Y",desc="reDoTag：重复查询标记0：首次读卡1：重复读卡2: 重复写卡")
	private String reDoTag;
	@ZteSoftCommentAnnotationParam(name="办理业务系统：1：ESS,2：CBSS",type="String",isNecessary="N",desc="opeSysType：办理业务系统：1：ESS,2：CBSS")
	private String opeSysType;
	@ZteSoftCommentAnnotationParam(name="读卡序列（首次为空）",type="String",isNecessary="N",desc="procId：读卡序列（首次为空）")
	private String procId;
	@ZteSoftCommentAnnotationParam(name="交易流水（ESS生成返回,首次为空）",type="String",isNecessary="N",desc="activeId：交易流水（ESS生成返回,首次为空）")
	private String activeId;
	@ZteSoftCommentAnnotationParam(name="大卡卡号",type="String",isNecessary="Y",desc="iccid：大卡卡号")
	private String iccid;
	@ZteSoftCommentAnnotationParam(name="原白卡卡号（重复读卡必传）",type="String",isNecessary="N",desc="oldIccid：原白卡卡号（重复读卡必传）")
	private String oldIccid;
	@ZteSoftCommentAnnotationParam(name="手机号码",type="String",isNecessary="Y",desc="numId：手机号码")
	private String numId;
	@ZteSoftCommentAnnotationParam(name="业务类型",type="String",isNecessary="Y",desc="busiType：业务类型")
	private String busiType;
	@ZteSoftCommentAnnotationParam(name="白卡业务类型",type="String",isNecessary="N",desc="cardType：白卡业务类型")
	private String cardType;
	@ZteSoftCommentAnnotationParam(name="用户类型",type="String",isNecessary="N",desc="userType：用户类型")
	private String userType;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="N",desc="paras：保留字段")
	private List<ParamsVo> para;
	@ZteSoftCommentAnnotationParam(name = "调拨标识", type = "String", isNecessary = "N", desc = "allocationFlag：0 调拨 1 不调拨")
	private String allocationFlag;
	
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;

	private EmpOperInfoVo essOperInfo;

	public String getOperatorId() {
			this.operatorId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getProvince() {
			this.province = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PROVINCE);
			return "36";
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		this.city = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		if (this.city.trim().length()!=3) {
			this.city = CommonDataFactory.getInstance().getOtherDictVodeValue("city", this.city);
		}
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		this.district = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.DISTRICT_CODE);
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getChannelId() {
		this.channelId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CHA_CODE);
		return this.channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelType() {
		this.channelType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CHANNEL_TYPE);
		return this.channelType.trim().length()==0?"1030100":this.channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getCardUseType() {
		cardUseType = EcsOrderConsts.AOP_CARD_USE_TYPE_0;
		return cardUseType;
	}

	public void setCardUseType(String cardUseType) {
		this.cardUseType = cardUseType;
	}

	public String getReDoTag() {
		if (StringUtils.isEmpty(getOldIccid())) {//没有记录老ICCID，首次读卡
			this.reDoTag = EcsOrderConsts.AOP_RE_DO_TAG_0;
		}else if (getOldIccid().equalsIgnoreCase(getIccid())) {//新老ICCID一样，重复读卡
			this.reDoTag = EcsOrderConsts.AOP_RE_DO_TAG_1;
		}else {//新老ICCID不一样，新卡
			this.reDoTag = EcsOrderConsts.AOP_RE_DO_TAG_0;
		}
		return reDoTag;
	}

	public void setReDoTag(String reDoTag) {
		this.reDoTag = reDoTag;
	}

	public String getOpeSysType() {
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		opeSysType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_operSys_by_mobileNet", net_type);
		return opeSysType; // 业务类型 1：ESS，2：CBSS（默认给2，根据号码网别判断,3G,4G现在办理业务都是在CBSS系统办理）
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	public String getProcId() {
		if (procId == null || "".equals(procId)) {
			procId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PROC_ID);
		}
		if ("".equals(procId)) {
			return null;
		}
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public String getActiveId() {
		if (activeId == null || "".equals(activeId)) {
			activeId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_ID);
		}
		if ("".equals(activeId)) {
			return null;
		}
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getIccid() {
		if (iccid == null || "".equals(iccid)) {
			iccid = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
		}
		if ("".equals(iccid)) {
			return null;
		}
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getOldIccid() {
		if (oldIccid == null || "".equals(oldIccid)) {
			oldIccid = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OLD_ICCID);
		}
		if ("".equals(oldIccid)) {
			return null;
		}
		return oldIccid;
	}

	public void setOldIccid(String oldIccid) {
		this.oldIccid = oldIccid;
	}

	public String getNumId() {
		if (numId == null || "".equals(numId)) {
			numId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
		}
		if ("".equals(numId)) {
			return null;
		}
		return numId;
	}

	public void setNumId(String numId) {
		this.numId = numId;
	}

	public String getBusiType() {//TODO 根据网别拼装，第二位编码怎么定
		busiType = EcsOrderConsts.AOP_BUSI_TYPE_32;
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getCardType() {
		String goodType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_whitecard_type", goodType);
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getUserType() {		
		String serType = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_pay_type", serType);
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<ParamsVo> getParas() {
		return para;
	}

	public void setParas(List<ParamsVo> paras) {
		this.para = paras;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	public String getAllocationFlag() {
		allocationFlag = EcsOrderConsts.AOP_ALLOCATION_FLAG_0;
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		if (cacheUtil.getConfigInfo("ALLOCATIONFLAG_ORDER_FROM").contains(
				CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_FROM))) {
			this.allocationFlag = EcsOrderConsts.AOP_ALLOCATION_FLAG_1;
		}
		return allocationFlag;
	}

	public void setAllocationFlag(String allocationFlag) {
		this.allocationFlag = allocationFlag;
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "ecaop.trades.sell.mob.comm.carddate.qry";
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
