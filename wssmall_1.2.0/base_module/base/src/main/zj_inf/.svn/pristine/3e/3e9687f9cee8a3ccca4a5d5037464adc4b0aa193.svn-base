package zte.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 
 * BSS获取写卡数据
 *
 */
public class GetCardDataReq extends ZteRequest {


	@ZteSoftCommentAnnotationParam(name="服务号码",type="String",isNecessary="Y",desc="userNum：服务号码")
	private String UserNum;
	@ZteSoftCommentAnnotationParam(name="Sim卡卡号",type="String",isNecessary="Y",desc="simCard：Sim卡卡号")
	private String SimCard;
	@ZteSoftCommentAnnotationParam(name="地市编号",type="String",isNecessary="Y",desc="eparchyCode：地市编号")
	private String EparchyCode;
	@ZteSoftCommentAnnotationParam(name="客户名称",type="String",isNecessary="Y",desc="custName：客户名称")
	private String CustName;
	@ZteSoftCommentAnnotationParam(name="客户证件ID",type="String",isNecessary="Y",desc="psptId：客户证件ID")
	private String PsptId;
	@ZteSoftCommentAnnotationParam(name="产品ID",type="String",isNecessary="Y",desc="productId：产品ID")
	private String ProductId;
	@ZteSoftCommentAnnotationParam(name="业务类型",type="String",isNecessary="Y",desc="busiType：业务类型")
	private String BusiType;
	@ZteSoftCommentAnnotationParam(name="卡类型",type="String",isNecessary="Y",desc="cardType：卡类型")
	private String CardType;
	@ZteSoftCommentAnnotationParam(name="用户类型",type="String",isNecessary="Y",desc="userType：用户类型  00	预付费用户	01 	后付费用户")
	private String UserType;
	@ZteSoftCommentAnnotationParam(name="写卡流水",type="String",isNecessary="Y",desc="procId：写卡流水")
	private String ProcId;
	@ZteSoftCommentAnnotationParam(name="开户省份订单号",type="String",isNecessary="Y",desc="tradeId：开户省份订单号")
	private String TradeId;
	@ZteSoftCommentAnnotationParam(name="工号",type="String",isNecessary="Y",desc="staffId：工号")
	private String StaffId;
	@ZteSoftCommentAnnotationParam(name="部门工号对应渠道ID",type="String",isNecessary="Y",desc="departId：部门工号对应渠道ID")
	private String DepartId;
	@ZteSoftCommentAnnotationParam(name="业务受理类型",type="String",isNecessary="Y",desc="TradeTypeCode：业务受理类型")
	private String TradeTypeCode;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="Y",desc="para：保留字段")
	private String Para;
	
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;
	
	private EmpOperInfoVo essOperInfo;


	public EmpOperInfoVo getEssOperInfo() {
		if(essOperInfo==null){
			essOperInfo = CommonDataFactory.getEssInfoByOrderId(notNeedReqStrOrderId).getOperInfo();
		}
		return essOperInfo;
	}

	public void setEssOperInfo(EmpOperInfoVo essOperInfo) {
		this.essOperInfo = essOperInfo;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "ecaop.trades.sell.mob.comm.carddate.autoqry.bss";
	}

	public String getUserNum() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
	}

	public void setUserNum(String userNum) {
		this.UserNum = userNum;
	}

	public String getSimCard() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
	}

	public void setSimCard(String simCard) {
		this.SimCard = simCard;
	}

	public String getEparchyCode() {
		return getEssOperInfo().getCity();
	}

	public void setEparchyCode(String eparchyCode) {
		this.EparchyCode = eparchyCode;
	}

	public String getCustName() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_OWNER_NAME);
	}

	public void setCustName(String custName) {
		this.CustName = custName;
	}

	public String getPsptId() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM);
	}

	public void setPsptId(String psptId) {
		this.PsptId = psptId;
	}

	public String getProductId() {
		return CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.ESS_CODE);
	}

	public void setProductId(String productId) {
		this.ProductId = productId;
	}

	public String getBusiType() {
		//网别
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		if("2G".equals(net_type)){
			return EcsOrderConsts.BSS_BUSITYPE_OPEN_2G;
		}else if("3G".equals(net_type)){
			return EcsOrderConsts.BSS_BUSITYPE_OPEN_3G;
		}else if("4G".equals(net_type)){
			return EcsOrderConsts.BSS_BUSITYPE_OPEN_3G;
		}
		return "";
	}

	public void setBusiType(String busiType) {
		this.BusiType = busiType;
	}

	public String getCardType() {
		//网别
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		if("2G".equals(net_type)){
			return EcsOrderConsts.BSS_CARD_TYPE_2G;
		}else if("3G".equals(net_type)){
			return EcsOrderConsts.BSS_CARD_TYPE_3G;
		}else if("4G".equals(net_type)){
			return EcsOrderConsts.BSS_CARD_TYPE_4G;
		}
		return "";
	}

	public void setCardType(String cardType) {
		this.CardType = cardType;
	}

	public String getUserType() {
		String serType = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.PAY_TYPE);
		if("BAK".equalsIgnoreCase(serType)){
			return EcsOrderConsts.BSS_USER_TYPE_BAK;
		}else if("PRE".equalsIgnoreCase(serType)){
			return EcsOrderConsts.BSS_USER_TYPE_PRE;
		}
		return "";
	}

	public void setUserType(String userType) {
		this.UserType = userType;
	}

	public String getProcId() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);
	}

	public void setProcId(String procId) {
		this.ProcId = procId;
	}

	public String getTradeId() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);
	}

	public void setTradeId(String tradeId) {
		this.TradeId = tradeId;
	}

	public String getStaffId() {
		return getEssOperInfo().getEss_emp_id();
	}

	public void setStaffId(String staffId) {
		this.StaffId = staffId;
	}

	public String getDepartId() {
		return getEssOperInfo().getChannel_id();
	}

	public void setDepartId(String departId) {
		this.DepartId = departId;
	}

	public String getPara() {
		return Para;
	}

	public void setPara(String para) {
		Para = para;
	}

	public String getTradeTypeCode() {
		return "10";
	}

	public void setTradeTypeCode(String tradeTypeCode) {
		TradeTypeCode = tradeTypeCode;
	}
	
	
	
}
