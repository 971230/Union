/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.resp.UserInfoCheck3BackResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * @author ZX
 * @version 2015-06-25
 * @see 用户资料校验三户返回
 *
 */
public class UserInfoCheck3BackReq extends ZteRequest<UserInfoCheck3BackResp> {

	@ZteSoftCommentAnnotationParam(name = "订单ID", type = "String", isNecessary = "Y", desc = "订单ID")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "操作员ID")
	private String operatorId;
	@ZteSoftCommentAnnotationParam(name = "省分", type = "String", isNecessary = "Y", desc = "省分")
	private String province;
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "地市")
	private String city;
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "Y", desc = "区县")
	private String district;
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "渠道编码")
	private String channelId;
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "渠道类型")
	private String channelType;
	@ZteSoftCommentAnnotationParam(name = "业务编码，办理业务的代码标识（业务参数编码表）", type = "String", isNecessary = "Y", desc = "tradeTypeCode")
	private String tradeTypeCode; 
	@ZteSoftCommentAnnotationParam(name = "电信业务类别（电信业务类别）", type = "String", isNecessary = "Y", desc = "serviceClassCode")
	private String serviceClassCode; 
	@ZteSoftCommentAnnotationParam(name = "区号", type = "String", isNecessary = "N", desc = "areaCode")
	private String areaCode; 
	@ZteSoftCommentAnnotationParam(name = "服务号码", type = "String", isNecessary = "Y", desc = "serialNumber")
	private String serialNumber;
	@ZteSoftCommentAnnotationParam(name = "CUST|USER|ACCT：对应客户、用户和账户", type = "String", isNecessary = "Y", desc = "infoList")
	private String infoList; 
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
	public String getTradeTypeCode() {		
		return EcsOrderConsts.AOP_TRADE_TYOE_CODE_9999;
	}
	public void setTradeTypeCode(String tradeTypeCode) {
		this.tradeTypeCode = tradeTypeCode;
	}

	public String getServiceClassCode() {
		return EcsOrderConsts.AOP_SERVICE_CLASS_CODE_0000;//按照协议规范,传0000

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
	public String getSerialNumber() {
		serialNumber = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getInfoList() {
		return EcsOrderConsts.AOP_QUERY_CUST+"|"+EcsOrderConsts.AOP_QUERY_USER+"|"+EcsOrderConsts.AOP_QUERY_ACCT;
	}
	public void setInfoList(String infoList) {
		this.infoList = infoList;
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
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "ecaop.trades.query.comm.user.threepart.check";
	}
	
}
