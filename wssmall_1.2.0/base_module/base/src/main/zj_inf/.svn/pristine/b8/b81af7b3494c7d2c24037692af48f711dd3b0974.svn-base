/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;

/**
 * @author ZX
 * @version 2015-05-05
 * @see 写卡结果通知
 */
@SuppressWarnings("all")
public class WriteCardResultNoticeReqZJ extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "operatorId：操作员ID")
	private String operatorId;
	@ZteSoftCommentAnnotationParam(name = "省份", type = "String", isNecessary = "Y", desc = "province：省份")
	private String province;
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "city：地市")
	private String city;
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "Y", desc = "district：区县")
	private String district;
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	private String channelId;
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型:参考附录渠道类型编码")
	private String channelType;
	@ZteSoftCommentAnnotationParam(name = "写卡目的", type = "String", isNecessary = "Y", desc = "cardUseType：写卡目的：0：新开户；1：补换卡")
	private String cardUseType;
	@ZteSoftCommentAnnotationParam(name = "办理业务系统", type = "String", isNecessary = "N", desc = "opeSysType：办理业务系统 1：ESS 2：CBSS")
	private String opeSysType;
	@ZteSoftCommentAnnotationParam(name = "坏卡卡号", type = "String", isNecessary = "N", desc = "errorIccid：坏卡卡号(重复写卡必传)")
	private String errorIccid;
	@ZteSoftCommentAnnotationParam(name = "大卡卡号", type = "String", isNecessary = "Y", desc = "iccid： 大卡卡号")
	private String iccid;
	@ZteSoftCommentAnnotationParam(name = "写入的IMSI号", type = "String", isNecessary = "Y", desc = "imsi：写入的IMSI号")
	private String imsi;
	@ZteSoftCommentAnnotationParam(name = "读卡序列", type = "String", isNecessary = "N", desc = "procId：读卡序列")
	private String procId;
	@ZteSoftCommentAnnotationParam(name = "交易流水", type = "String", isNecessary = "Y", desc = "activeId：交易流水（ESS生成返回,首次为空）")
	private String activeId;
	@ZteSoftCommentAnnotationParam(name = "写卡成功", type = "String", isNecessary = "Y", desc = "reasonId：0：写卡成功 非0则由读卡器返回的错误代码")
	private String reasonId;
	@ZteSoftCommentAnnotationParam(name = "卡容量", type = "String", isNecessary = "N", desc = "capacityTypeCode：卡容量")
	private String capacityTypeCode;
	@ZteSoftCommentAnnotationParam(name = "卡类型", type = "String", isNecessary = "N", desc = "resKindCode：卡类型")
	private String resKindCode;
	@ZteSoftCommentAnnotationParam(name = "错误说明", type = "String", isNecessary = "N", desc = "errorComments：错误说明 由写卡器返回的错误说明，如果reasonId为非0，则必填")
	private String errorComments;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
	private ParamsVo para;

	@ZteSoftCommentAnnotationParam(name = "流水号", type = "String", isNecessary = "Y", desc = "activeNo：流水号")
	private String activeNo;

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

	public String getOpeSysType() {
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		opeSysType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_operSys_by_mobileNet", net_type);
		return opeSysType; // 业务类型 1：ESS，2：CBSS（默认给2，根据号码网别判断,3G,4G现在办理业务都是在CBSS系统办理）
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	public String getErrorIccid() {//先不传
		return errorIccid;
	}

	public void setErrorIccid(String errorIccid) {
		this.errorIccid = errorIccid;
	}

	public String getIccid() {
		iccid = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getImsi() {
		imsi = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SIMID);		
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getProcId() {
		procId =CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PROC_ID);
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public String getActiveId() {
		activeId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_ID);
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getReasonId() {//成功才会进行下一步，so~~默认成功编码
		reasonId = EcsOrderConsts.WMS_WRITE_CARD_STATUS_0;
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	public String getCapacityTypeCode() {
		capacityTypeCode = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CAPACITY_TYPE_CODE);
		return capacityTypeCode;
	}

	public void setCapacityTypeCode(String capacityTypeCode) {
		this.capacityTypeCode = capacityTypeCode;
	}

	public String getResKindCode() {
		resKindCode = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.RES_KIND_CODE);
		return resKindCode;
	}

	public void setResKindCode(String resKindCode) {
		this.resKindCode = resKindCode;
	}

	public String getErrorComments() {
		errorComments = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ERRORCOMMENTS);
		return errorComments;
	}

	public void setErrorComments(String errorComments) {
		this.errorComments = errorComments;
	}

	public ParamsVo getPara() {
		return para;
	}

	public void setPara(ParamsVo para) {
		this.para = para;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getActiveNo() {
		activeNo = CommonDataFactory.getInstance().getActiveNo(AttrConsts.ACTIVE_NO_ON_OFF);
		return activeNo;
	}

	public void setActiveNo(String activeNo) {
		this.activeNo = activeNo;
	}

	@NotDbField
	public void check() throws ApiRuleException {

	}

	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.zb.writeCardResultNoticezj";
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
