package zte.net.ecsord.params.ecaop.req;

import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * @author XMJ
 * @date 2015-06-24
 *
 */
@SuppressWarnings("rawtypes")
public class CannelOrder23to4Request extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	
	private static final long serialVersionUID = 2554509681176320812L;
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
	
	@ZteSoftCommentAnnotationParam(name = "业务编码", type = "String", isNecessary = "Y", desc = "tradeTypeCode:业务编码，办理业务的代码标识")
	private String tradeTypeCode;
	
	
	@ZteSoftCommentAnnotationParam(name = "电信业务类别", type = "String", isNecessary = "Y", desc = "serviceClassCode:电信业务类别")
	private String serviceClassCode;
	
	@ZteSoftCommentAnnotationParam(name = "区号", type = "String", isNecessary = "N", desc = "areaCode:区号")
	private String areaCode;

	@ZteSoftCommentAnnotationParam(name = "服务号码", type = "String", isNecessary = "Y", desc = "serialNumber:服务号码")
	private String serialNumber;
	
	
	@ZteSoftCommentAnnotationParam(name = "CUST|USER|ACCT", type = "String", isNecessary = "Y", desc = "infoList:CUST|USER|ACCT")
	private String infoList;
	
	@ZteSoftCommentAnnotationParam(name = "需要撤单的ess订单号", type = "String", isNecessary = "Y", desc = "soNbr:需要撤单的ess订单号")
	private String soNbr;
	
	@ZteSoftCommentAnnotationParam(name = "撤单原因", type = "String", isNecessary = "Y", desc = "cancelReason:撤单原因")
	private String cancelReason;
	
	@ZteSoftCommentAnnotationParam(name = "强制撤单标识", type = "String", isNecessary = "Y", desc = "forceCancel:强制撤单标识")
	private String forceCancel;
	
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
	private List<ParaVo> para;
	
	
	private EmpOperInfoVo essOperInfo;	
	
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

	public String getTradeTypeCode() {
		return EcsOrderConsts.AOP_TRADE_TYOE_CODE_9999;
	}

	public void setTradeTypeCode(String tradeTypeCode) {
		this.tradeTypeCode = tradeTypeCode;
	}

	public String getServiceClassCode() {
		return EcsOrderConsts.AOP_SERVICE_CLASS_CODE_0000;
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

	public String getSoNbr() {
		soNbr = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);
		return soNbr;
	}

	public void setSoNbr(String soNbr) {
		this.soNbr = soNbr;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getForceCancel() {
		return "1";
	}

	public void setForceCancel(String forceCancel) {
		this.forceCancel = forceCancel;
	}

	public List<ParaVo> getPara() {
		if (CommonDataFactory.getInstance().checkFieldValueNull(para))return null;
		return para;
	}

	public void setPara(List<ParaVo> para) {
		this.para = para;
	}

	@NotDbField
	public void check() throws ApiRuleException {}

	@NotDbField
	public String getApiMethodName() {
		return "ecaop.trades.serv.curt.23to4cannel.sub";
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
