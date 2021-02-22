package zte.net.ecsord.params.ecaop.req;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ChangeMachineSubReq extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="操作员ID",type="String",isNecessary="Y",desc="operatorId：操作员ID")
	private String operatorId;
	@ZteSoftCommentAnnotationParam(name="省份",type="String",isNecessary="Y",desc="province：省份")
	private String province;
	@ZteSoftCommentAnnotationParam(name = "地市", type = "String", isNecessary = "Y", desc = "city：地市")
	private String city;
	@ZteSoftCommentAnnotationParam(name = "区县", type = "String", isNecessary = "Y", desc = "district：区县")
	private String district;
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "channelId：渠道编码")
	private String channelId;
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "channelType：渠道类型:参考附录渠道类型编码")
	private String channelType;
	@ZteSoftCommentAnnotationParam(name = "外围系统订单ID", type = "String", isNecessary = "Y", desc = "ordersId：外围系统订单ID")
	private String ordersId;
	@ZteSoftCommentAnnotationParam(name = "办理业务系统：1：ESS，3G老用户合约机或者裸机  2：CBSS，4G", type = "String", isNecessary = "Y", desc = "opeSysType:办理业务系统：1：ESS，3G老用户合约机或者裸机  2：CBSS，4G")
	private String opeSysType;
	@ZteSoftCommentAnnotationParam(name = "号码", type = "String", isNecessary = "Y", desc = "number:号码")
	private String number;
	@ZteSoftCommentAnnotationParam(name = "新资源标识（串号）", type = "String", isNecessary = "Y", desc = "newResourcesCode:新资源标识（串号）")
	private String newResourcesCode;
	@ZteSoftCommentAnnotationParam(name = "旧资源标识（串号）", type = "String", isNecessary = "Y", desc = "oldResourcesCode:旧资源标识（串号）")
	private String oldResourcesCode;
	@ZteSoftCommentAnnotationParam(name = "故障单号", type = "String", isNecessary = "N", desc = "checkId：故障单号")
	private String checkId;
	@ZteSoftCommentAnnotationParam(name = "故障原因", type = "String", isNecessary = "N", desc = "brokenRe：故障原因")
	private String brokenRe;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "String", isNecessary = "N", desc = "paras：保留字段")
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

	public String getOrdersId() {
		ordersId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);
		if (StringUtils.isBlank(ordersId)) return null;
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getOpeSysType() {
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		opeSysType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_operSys_by_mobileNet", net_type);
		return opeSysType;
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	public String getNumber() {
		number = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getNewResourcesCode() {
		newResourcesCode = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM);
		return newResourcesCode;
	}

	public void setNewResourcesCode(String newResourcesCode) {
		this.newResourcesCode = newResourcesCode;
	}

	public String getOldResourcesCode() {
		oldResourcesCode = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OLD_TERMINAL_NUM);
		return oldResourcesCode;
	}

	public void setOldResourcesCode(String oldResourcesCode) {
		this.oldResourcesCode = oldResourcesCode;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getBrokenRe() {
		return brokenRe;
	}

	public void setBrokenRe(String brokenRe) {
		this.brokenRe = brokenRe;
	}

	public List<ParaVo> getPara() {
		return para;
	}

	public void setPara(List<ParaVo> para) {
		this.para = para;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "ecaop.trades.serv.curt.changeph.sub";
	}

}
