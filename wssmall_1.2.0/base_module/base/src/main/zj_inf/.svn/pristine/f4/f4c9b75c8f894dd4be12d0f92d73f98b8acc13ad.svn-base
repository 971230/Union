package zte.net.ecsord.params.ecaop.req;

import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

public class UserCountCheckReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="操作员ID",type="String",isNecessary="Y",desc="operatorID：操作员ID")
	private String OperatorID;
	@ZteSoftCommentAnnotationParam(name="省分",type="String",isNecessary="Y",desc="province：省分")
	private String Province;
	@ZteSoftCommentAnnotationParam(name="地市",type="String",isNecessary="Y",desc="city：地市")
	private String City;
	@ZteSoftCommentAnnotationParam(name="区县",type="String",isNecessary="Y",desc="district：区县")
	private String District;
	@ZteSoftCommentAnnotationParam(name="渠道编码",type="String",isNecessary="Y",desc="channelID：渠道编码")
	private String ChannelID;
	@ZteSoftCommentAnnotationParam(name="渠道类型",type="String",isNecessary="Y",desc="channelType：渠道类型")
	private String ChannelType;
	@ZteSoftCommentAnnotationParam(name="接入类型",type="String",isNecessary="Y",desc="accessType：接入类型")
	private String AccessType;
	@ZteSoftCommentAnnotationParam(name="证件类型",type="String",isNecessary="Y",desc="certType：证件类型")
	private String CertType;
	@ZteSoftCommentAnnotationParam(name="证件号码",type="String",isNecessary="Y",desc="certNum：证件号码")
	private String CertNum;
	@ZteSoftCommentAnnotationParam(name="姓名",type="String",isNecessary="Y",desc="name：姓名")
	private String Name;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="Y",desc="para：保留字段")
	private List<BSSParaVo> Para;
	
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;

	private EmpOperInfoVo essOperInfo;


	public String getCertType() {
		String cert_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERTI_TYPE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("bss_cert_type", cert_type);
	}

	public void setCertType(String certType) {
		this.CertType = certType;
	}

	public String getName() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_OWNER_NAME);
	}

	public void setName(String name) {
		this.Name = name;
	}

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

	public String getAccessType() {
		return EcsOrderConsts.BSS_ACCESS_TYPE_WYG;
	}

	public void setAccessType(String accessType) {
		this.AccessType = accessType;
	}

	public String getCertNum() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM);
	}

	public void setCertNum(String certNum) {
		this.CertNum = certNum;
	}

	public List<BSSParaVo> getPara() {
		return Para;
	}

	public void setPara(List<BSSParaVo> para) {
		this.Para = para;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
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

	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "bss.userCountCheckFromBSS";
	}

}
