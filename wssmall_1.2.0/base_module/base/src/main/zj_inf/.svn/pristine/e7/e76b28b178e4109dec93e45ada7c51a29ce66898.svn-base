package zte.net.ecsord.params.ecaop.req;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;
import zte.net.ecsord.params.ecaop.resp.EmpIdEssIDResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 
 * @author Administrator
 * @see 订单返销
 */
public class OrderReverseSalesReq extends ZteRequest{
	
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
	@ZteSoftCommentAnnotationParam(name = "办理业务系统：1：ESS，3G老用户合约机或者裸机  2：CBSS，4G", type = "String", isNecessary = "N", desc = "opeSysType:办理业务系统：1：ESS，3G老用户合约机或者裸机  2：CBSS，4G")
	private String opeSysType;
	@ZteSoftCommentAnnotationParam(name = "号码", type = "String", isNecessary = "N", desc = "serialNumber：号码")
	private String serialNumber;
	@ZteSoftCommentAnnotationParam(name = "需要返销的总部原订单ID", type = "String", isNecessary = "Y", desc = "essOrigOrderId：需要返销的总部原订单ID")
	private String essOrigOrderId;
	@ZteSoftCommentAnnotationParam(name = "开户卡类型：1：白卡，默认    2：成卡", type = "String", isNecessary = "N", desc = "cardType：开户卡类型：1：白卡，默认2：成卡")
	private String cardType;
	@ZteSoftCommentAnnotationParam(name = "终端串码", type = "String", isNecessary = "N", desc = "imei：终端串码")
	private String imei;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="ParamsVo",isNecessary="N",desc="paras：保留字段")
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
		ordersId = CommonDataFactory.getInstance().getOrderIdRandom(16);
		if (StringUtils.isBlank(ordersId)) return null;
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getOpeSysType() {
		return EcsOrderConsts.AOP_OPE_SYS_TYPE_2; 
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	public String getSerialNumber() {
		serialNumber =  CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getEssOrigOrderId() {
		essOrigOrderId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);
		return essOrigOrderId;
	}

	public void setEssOrigOrderId(String essOrigOrderId) {
		this.essOrigOrderId = essOrigOrderId;
	}

	public String getCardType() {
		//cardType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.WHITE_CART_TYPE);
		if(StringUtils.isBlank(cardType)) cardType = EcsOrderConsts.SIM_TYPE_BK;
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getImei() {
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_TYPE);
		if(StringUtils.equals(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE, goods_type)){
			imei = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM);
		}
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public List<ParaVo> getPara() {
		if (CommonDataFactory.getInstance().checkFieldValueNull(para))return null;
		return para;
	}

	public void setPara(List<ParaVo> para) {
		this.para = para;
	}

	public EmpOperInfoVo getEssOperInfo() {
		if(essOperInfo==null){
			//获取开户工号
			String essId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BSS_OPERATOR);
			//获取工号信息
			EmpIdEssIDResp essRspInfo = CommonDataFactory.getEssinfoByEssId(essId,notNeedReqStrOrderId,EcsOrderConsts.OPER_TYPE_ESS);
			essOperInfo = essRspInfo.getOperInfo();
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
		
		return "ecaop.trades.serv.curt.cannel.sub";
	}

}
