/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * @see 客户信息校验
 * 
 */
@SuppressWarnings("rawtypes")
public class BSSCustomerInfoCheckReq extends ZteRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4347308534699247117L;

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name="操作员ID",type="String",isNecessary="Y",desc="操作员ID")
	private String OperatorID;	
	@ZteSoftCommentAnnotationParam(name="省分",type="String",isNecessary="Y",desc="Province：省分")
	private String Province	;		
	@ZteSoftCommentAnnotationParam(name="地市",type="String",isNecessary="Y",desc="City：地市")
	private String City;		
	@ZteSoftCommentAnnotationParam(name="区县",type="String",isNecessary="Y",desc="District：减区县")
	private String District;		
	@ZteSoftCommentAnnotationParam(name="渠道编码",type="String",isNecessary="Y",desc="ChannelID：渠道编码")
	private String ChannelID;		
	@ZteSoftCommentAnnotationParam(name="渠道类型",type="String",isNecessary="Y",desc="ChannelType：渠道类型")
	private String ChannelType;
	@ZteSoftCommentAnnotationParam(name="接入类型",type="String",isNecessary="Y",desc="AccessType：接入类型")
	private String AccessType;	
	@ZteSoftCommentAnnotationParam(name="证件类型",type="String",isNecessary="Y",desc="CertType：证件类型")
	private String CertType;		
	@ZteSoftCommentAnnotationParam(name="证件号码",type="String",isNecessary="Y",desc="CertNum：证件号码")
	private String CertNum;		
	@ZteSoftCommentAnnotationParam(name="号码类型",type="String",isNecessary="Y",desc="ServiceType：号码类型")
	private String ServiceType;		
	@ZteSoftCommentAnnotationParam(name="区号",type="String",isNecessary="Y",desc="AreaCode：区号")
	private String AreaCode;		
	@ZteSoftCommentAnnotationParam(name="服务号码",type="String",isNecessary="Y",desc="NumID：服务号码")
	private String NumID;	
	@ZteSoftCommentAnnotationParam(name="返档类型",type="String",isNecessary="Y",desc="ReturnType：返档类型")
	private String ReturnType;

	private EmpOperInfoVo essOperInfo;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}	

	public String getOperatorID() {
		return getEssOperInfo().getEss_emp_id();
	}

	public void setOperatorID(String operatorID) {
		OperatorID = operatorID;
	}

	public String getProvince() {
		return getEssOperInfo().getProvince();
	}

	public void setProvince(String province) {
		Province = province;
	}

	public String getCity() {
		return getEssOperInfo().getCity();
	}

	public void setCity(String city) {
		City = city;
	}

	public String getDistrict() {
		return getEssOperInfo().getDistrict();
	}

	public void setDistrict(String district) {
		District = district;
	}

	public String getChannelID() {
		return getEssOperInfo().getChannel_id();
	}

	public void setChannelID(String channelID) {
		ChannelID = channelID;
	}

	public String getChannelType() {
		return getEssOperInfo().getChannel_type();
	}

	public void setChannelType(String channelType) {
		ChannelType = channelType;
	}

	public String getAccessType() {
		return EcsOrderConsts.BSS_ACCESS_TYPE_WYG;
	}

	public void setAccessType(String accessType) {
		AccessType = accessType;
	}

	public String getCertType() {
		String certType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERTI_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("bss_cert_type", certType);
	}

	public void setCertType(String certType) {
		CertType = certType;
	}

	public String getCertNum() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM);
	}

	public void setCertNum(String certNum) {
		CertNum = certNum;
	}

	public String getServiceType() {
		return ServiceType;
	}

	public void setServiceType(String serviceType) {
		ServiceType = serviceType;
	}

	public String getAreaCode() {
		return AreaCode;
	}

	public void setAreaCode(String areaCode) {
		AreaCode = areaCode;
	}

	public String getNumID() {
		return NumID;
	}

	public void setNumID(String numID) {
		NumID = numID;
	}

	public String getReturnType() {
		return ReturnType;
	}

	public void setReturnType(String returnType) {
		ReturnType = returnType;
	}

	@NotDbField
	public void check() throws ApiRuleException {

	}

	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.zb.customerInfoQuery.bss";
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
