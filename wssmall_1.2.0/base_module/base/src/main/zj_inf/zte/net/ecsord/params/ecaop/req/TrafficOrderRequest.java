package zte.net.ecsord.params.ecaop.req;

import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * @author XMJ
 * @date 2015-06-25
 *
 */
@SuppressWarnings("rawtypes")
public class TrafficOrderRequest extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	
	private static final long serialVersionUID = 2554509681176320812L;
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
	
	@ZteSoftCommentAnnotationParam(name = "服务号码", type = "String", isNecessary = "Y", desc = "numId：服务号码")
	private String numId;
	
	@ZteSoftCommentAnnotationParam(name = "办理业务系统", type = "String", isNecessary = "Y", desc = "opeSysType：办理业务系统")
	private String opeSysType;
	
	@ZteSoftCommentAnnotationParam(name = "电信业务类别", type = "String", isNecessary = "Y", desc = "serviceClassCode：电信业务类别")
	private String serviceClassCode;
	
	@ZteSoftCommentAnnotationParam(name = "资源类型", type = "String", isNecessary = "Y", desc = "resType：资源类型")
	private String resType;
	
	@ZteSoftCommentAnnotationParam(name = "金额", type = "String", isNecessary = "Y", desc = "money：金额")
	private String money;
	
	@ZteSoftCommentAnnotationParam(name = "资源量", type = "String", isNecessary = "Y", desc = "count：资源量")
	private String count;
	
	@ZteSoftCommentAnnotationParam(name = "当前资源有效时间", type = "String", isNecessary = "Y", desc = "validTime：当前资源有效时间")
	private String validTime;
	
	@ZteSoftCommentAnnotationParam(name = "有效期计算方式", type = "String", isNecessary = "Y", desc = "validCalculateType：有效期计算方式")
	private String validCalculateType;
	
	@ZteSoftCommentAnnotationParam(name = "付费方式", type = "String", isNecessary = "Y", desc = "payType：付费方式")
	private String payType;

	
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
		return getEssOperInfo().getProvince();
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


	public String getServiceClassCode() {
		serviceClassCode = EcsOrderConsts.AOP_SERVICE_CLASS_CODE_0000;
		return serviceClassCode;
	}

	public void setServiceClassCode(String serviceClassCode) {
		this.serviceClassCode = serviceClassCode;
	}


	public String getNumId() {
		numId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
		return numId;
	}

	public void setNumId(String numId) {
		this.numId = numId;
	}

	public String getOpeSysType() {
		opeSysType = EcsOrderConsts.AOP_OPE_SYS_TYPE_2;
		return opeSysType;
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	public String getResType() {
		resType = StypeConsts.DIC_RESOURCE_TYPE;
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public String getMoney() {
		
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getCount() {
		
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getValidCalculateType() {
		validCalculateType = "1";
		return validCalculateType;
	}

	public void setValidCalculateType(String validCalculateType) {
		this.validCalculateType = validCalculateType;
	}

	public String getPayType() {
		payType = "0";
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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
		return "ecaop.trades.sell.mob.comm.traffic.order";
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
