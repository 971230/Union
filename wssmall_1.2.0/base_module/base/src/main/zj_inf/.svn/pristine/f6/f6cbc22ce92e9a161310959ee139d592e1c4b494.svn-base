package zte.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

@SuppressWarnings("rawtypes")
public class CunFeeSongFeeRequest extends ZteRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2710691298602896363L;
	
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;

	
	@ZteSoftCommentAnnotationParam(name = "会话ID ", type = "String", isNecessary = "Y", desc = "accessSEQ：访问序列号")
	private String accessSEQ;
	@ZteSoftCommentAnnotationParam(name = "会话ID ", type = "String", isNecessary = "Y", desc = "sessionid：会话ID")
	private String sessionid;
	@ZteSoftCommentAnnotationParam(name = "用户号码 ", type = "String", isNecessary = "Y", desc = "serial_number：用户号码")
	private String serial_number;
	@ZteSoftCommentAnnotationParam(name = "地市编码 ", type = "String", isNecessary = "Y", desc = "eparchy_code：地市编码  例如广州 0020 ")
	private String eparchy_code;
	@ZteSoftCommentAnnotationParam(name = "渠道ID ", type = "String", isNecessary = "Y", desc = "channel_id：渠道ID ")
	private String channel_id;
	@ZteSoftCommentAnnotationParam(name = "cb员工号 ", type = "String", isNecessary = "Y", desc = "oper_id：cb员工号 ")
	private String oper_id;
	@ZteSoftCommentAnnotationParam(name = "渠道接入密码 ", type = "String", isNecessary = "N", desc = "trade_depart_paswd：渠道接入密码")
	private String trade_depart_paswd;
	@ZteSoftCommentAnnotationParam(name = "产品或者活动编码 ", type = "String", isNecessary = "Y", desc = "element_id：产品或者活动编码 ")
	private String element_id;
	@ZteSoftCommentAnnotationParam(name = "活动预存款 ", type = "String", isNecessary = "N", desc = "pay_money：活动预存款(单位分)")
	private String pay_money;
	@ZteSoftCommentAnnotationParam(name = "活动来源", type = "String", isNecessary = "N", desc = "notNeedReqStrOrderId：1：总部活动2：省份活动")
	private String activity_rang;
	
	private EmpOperInfoVo essOperInfo;
	
	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.gdaop.cunFeeSongFee";
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getAccessSEQ() {
		return CommonDataFactory.getInstance().getActiveNo(false);
	}

	public void setAccessSEQ(String accessSEQ) {
		this.accessSEQ = accessSEQ;
	}

	public String getSessionid() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ZB_INF_ID);
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getSerial_number() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public String getEparchy_code() {
		String city=CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		return CommonDataFactory.getInstance().getOtherDictVodeValue("bss_area_code", city);
	}

	public void setEparchy_code(String eparchy_code) {
		this.eparchy_code = eparchy_code;
	}

	public String getChannel_id() {
		return getEssOperInfo().getChannel_id();
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public String getOper_id() {
		return getEssOperInfo().getEss_emp_id();
	}

	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}

	public String getTrade_depart_paswd() {//(订购项目为活动此字段可为空，订购项目为产品必填) 
		return "";
	}

	public void setTrade_depart_paswd(String trade_depart_paswd) {
		this.trade_depart_paswd = trade_depart_paswd;
	}

	public String getElement_id() {		
		return CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId,  "", SpecConsts.BSS_CODE);
	}

	public void setElement_id(String element_id) {
		this.element_id = element_id;
	}

	public String getPay_money() {//活动预存款(单位分)(订购项目为活动此字段必填，订购项目为产品可为空)
		String fee = Double.parseDouble(CommonDataFactory.getInstance().getGoodSpec(notNeedReqStrOrderId, "", SpecConsts.DEPOSIT_FEE))*100+"";
		return fee.substring(0, fee.indexOf("."));
	}

	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
	}

	public String getActivity_rang() {//(订购项目为活动此字段必填，订购项目为产品可为空) 
		return EcsOrderConsts.AOP_ACTIVITY_RANG_GD;
	}

	public void setActivity_rang(String activity_rang) {
		this.activity_rang = activity_rang;
	}
	
	public EmpOperInfoVo getEssOperInfo() {
		if(essOperInfo==null){
			essOperInfo = CommonDataFactory.getEssinfoByOrderFrom(notNeedReqStrOrderId).getOperInfo();
		}
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}
}