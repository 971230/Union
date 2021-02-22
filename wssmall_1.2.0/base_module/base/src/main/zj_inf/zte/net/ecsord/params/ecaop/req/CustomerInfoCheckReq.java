/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * @author ZX
 * @version 2015-05-04
 * @see 客户信息校验
 * 
 */
@SuppressWarnings("all")
public class CustomerInfoCheckReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "operatorId：操作员ID")
	private String operatorId;

	@ZteSoftCommentAnnotationParam(name = "省份", type = "String", isNecessary = "Y", desc = "province：省份")
	private String province;

	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "city：地市")
	private String city;

	@ZteSoftCommentAnnotationParam(name = "区县", type = "district", isNecessary = "Y", desc = "district：区县")
	private String district;

	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	private String channelId;

	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型")
	private String channelType;

	@ZteSoftCommentAnnotationParam(name = "校验类型", type = "String", isNecessary = "Y", desc = "checkType：校验类型：0：证件校验 1：号码校验")
	private String checkType;

	@ZteSoftCommentAnnotationParam(name = "办理业务系统", type = "String", isNecessary = "N", desc = "opeSysType：办理业务系统：1：ESS,2：CBSS,不传默认ESS")
	private String opeSysType;

	@ZteSoftCommentAnnotationParam(name = "号码类型", type = "String", isNecessary = "N", desc = "serviceClassCode：号码类型 0000,移动业务 0100:固定电话业务")
	private String serviceClassCode;

	@ZteSoftCommentAnnotationParam(name = "号码区号", type = "String", isNecessary = "N", desc = "areaCode：号码区号")
	private String areaCode;

	@ZteSoftCommentAnnotationParam(name = "号码", type = "String", isNecessary = "N", desc = "serialNumber：号码")
	private String serialNumber;

	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "N", desc = "certType：证件类型，参考附录证件类型")
	private String certType;

	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "N", desc = "certNum：证件号码")
	private String certNum;

	@ZteSoftCommentAnnotationParam(name = "受理类型", type = "String", isNecessary = "Y", desc = "serType：受理类型：1-后付费；2-预付费")
	private String serType;

	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "String", isNecessary = "N", desc = "para：保留字段")
	private ParamsVo para;

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
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

	public String getCheckType() {
//		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		//4G都用证件校验,3G证件信息齐全用证件，否则用号码
//		if(StringUtils.equals(net_type, EcsOrderConsts.NET_TYPE_4G)){
			checkType = EcsOrderConsts.AOP_CHECK_TYPE_BY_CERT;			
//		}
//		else{
//			if(!StringUtils.isEmpty(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.CERT_CARD_NUM))&&
//			   !StringUtils.isEmpty(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId,AttrConsts.CERTI_TYPE))){
//				checkType = EcsOrderConsts.AOP_CHECK_TYPE_BY_CERT;
//			}else{
//				checkType = EcsOrderConsts.AOP_CHECK_TYPE_BY_MOBILE;
//			}
//		}
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getOpeSysType() {
		if(StringUtils.isEmpty(opeSysType)){
			// 业务类型 1：ESS，2：CBSS（默认给2，根据号码网别判断,3G,4G现在办理业务都是在CBSS系统办理）			
			String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
			opeSysType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_operSys_by_mobileNet", net_type);
		}
		return opeSysType; 
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}
	public String getServiceClassCode() {
		serviceClassCode = EcsOrderConsts.AOP_SERVICE_CLASS_CODE_0000;
		return serviceClassCode;
	}

	public void setServiceClassCode(String serviceClassCode) {
		this.serviceClassCode = serviceClassCode;
	}

	public String getAreaCode() {
		//不传
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getSerialNumber() {
		//if(StringUtils.equals(getCheckType(), EcsOrderConsts.AOP_CHECK_TYPE_BY_MOBILE)){
			serialNumber = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
		//}
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCertType() {
		if(StringUtils.equals(getCheckType(), EcsOrderConsts.AOP_CHECK_TYPE_BY_CERT)){
			certType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERTI_TYPE);
			certType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_cert_type", certType);
		}
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNum() {	
		if(StringUtils.equals(getCheckType(), EcsOrderConsts.AOP_CHECK_TYPE_BY_CERT)){
			certNum = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM);		
		}
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getSerType() {
		String serType = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("aop_pay_type", serType);
	}

	public void setSerType(String serType) {
		this.serType = serType;
	}

	public ParamsVo getPara() {
		return para;
	}

	public void setPara(ParamsVo para) {
		this.para = para;
	}

	@NotDbField
	public void check() throws ApiRuleException {

	}

	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.zb.customerInfoQuery";
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
