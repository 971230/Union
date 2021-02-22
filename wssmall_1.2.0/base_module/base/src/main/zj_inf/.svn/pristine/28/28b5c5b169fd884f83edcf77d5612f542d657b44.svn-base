/**
 * 
 */
package zte.net.ecsord.params.ecaop.req;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * cbss压单订单撤单
 * @author duan.shaochu
 *
 */
public class OrderCancelReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "cbssID为激活时使用", type = "String", isNecessary = "Y", desc = "orderId：cbssID为激活时使用")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name = "号码", type = "String", isNecessary = "Y", desc = "serialNumber：号码")
	private String serialNumber;
	@ZteSoftCommentAnnotationParam(name = "开户卡类型：1：白卡  2：成卡", type = "String", isNecessary = "Y", desc = "cardType：开户卡类型：1：白卡  2：成卡")
	private String cardType;
	@ZteSoftCommentAnnotationParam(name = "终端串码", type = "String", isNecessary = "Y", desc = "imei：终端串码")
	private String imei;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
	private List<ParamsVo> para;
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
	@ZteSoftCommentAnnotationParam(name = "办理业务系统", type = "String", isNecessary = "N", desc = "opeSysType：办理业务系统 1：ESS 2：CBSS")
	private String opeSysType;
	@ZteSoftCommentAnnotationParam(name = "大卡卡号", type = "String", isNecessary = "N", desc = "iccid：大卡卡号")
	private String iccid;
	
	public String getOpeSysType() {
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		opeSysType = CommonDataFactory.getInstance().getOtherDictVodeValue("aop_operSys_by_mobileNet", net_type);
		return opeSysType; // 业务类型 1：ESS，2：CBSS（默认给2，根据号码网别判断,3G,4G现在办理业务都是在CBSS系统办理）
	}

	public void setOpeSysType(String opeSysType) {
		this.opeSysType = opeSysType;
	}

	private EmpOperInfoVo essOperInfo;

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getOrderId() {
		String orderId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_ESS_ORDER_ID);
		if(StringUtils.isEmpty(orderId)){
			return null;
		}
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<ParamsVo> getPara() {
		return para;
	}

	public void setPara(List<ParamsVo> para) {
		this.para = para;
	}

	@Override
	public String getApiMethodName() {
		return EcsOrderConsts.AOP_ORDER_CANCEL;
	}

	public String getSerialNumber() {
		serialNumber = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
		if(StringUtils.isEmpty(serialNumber)){
			return null;
		}
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getCardType() {
		//0	成卡   1	白卡 2	半成卡  订单系统字典
		//AOP接口  1：白卡 2：成卡
		cardType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SIM_TYPE);
		if(StringUtils.isEmpty(cardType)){
			return null;
		}else{
			cardType = cardType.equals("0") ? "2": cardType;
		}
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getIccid() {
		iccid = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
		if(StringUtils.isEmpty(iccid)){
			return null;
		}
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getImei() {
		imei = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM);
		if(StringUtils.isEmpty(imei)){
			return null;
		}
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
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
