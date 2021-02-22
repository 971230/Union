package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

/**
 * 
 * BSS卡数据同步
 *
 */
public class WriteCardPreReq extends ZteRequest {

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
	@ZteSoftCommentAnnotationParam(name="开户总部订单",type="String",isNecessary="Y",desc="orderID：开户总部订单")
	private String OrderID;
	@ZteSoftCommentAnnotationParam(name="开户省分订单ID",type="String",isNecessary="Y",desc="provOrderID：开户省分订单ID")
	private String ProvOrderID;
	@ZteSoftCommentAnnotationParam(name="办理相关业务号码",type="String",isNecessary="Y",desc="numID：办理相关业务号码")
	private String NumID;
	@ZteSoftCommentAnnotationParam(name="成卡输入USIM卡号白卡ICCID",type="String",isNecessary="Y",desc="simID：成卡输入USIM卡号白卡ICCID")
	private String SimID;
	@ZteSoftCommentAnnotationParam(name="新IMSI号",type="String",isNecessary="Y",desc="IMSI：新IMSI号")
	private String IMSI;
	@ZteSoftCommentAnnotationParam(name="白卡类型",type="String",isNecessary="Y",desc="cardType：白卡类型")
	private String CardType;
	@ZteSoftCommentAnnotationParam(name="写卡业务类型",type="String",isNecessary="Y",desc="busiType：写卡业务类型")
	private String BusiType;
	@ZteSoftCommentAnnotationParam(name="白卡数据",type="String",isNecessary="Y",desc="cardData：白卡数据")
	private String CardData;
	@ZteSoftCommentAnnotationParam(name="获取写卡数据业务流水",type="String",isNecessary="Y",desc="cardDataProcID：获取写卡数据业务流水")
	private String CardDataProcID;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="Y",desc="para：保留字段")
	private List<BSSParaVo> Para;
	
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;

	private EmpOperInfoVo essOperInfo;

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

	public String getOrderID() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BSS_INF_ID);
	}

	public void setOrderID(String orderID) {
		this.OrderID = orderID;
	}

	public String getProvOrderID() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ACTIVE_NO);
	}

	public void setProvOrderID(String provOrderID) {
		this.ProvOrderID = provOrderID;
	}

	public String getNumID() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);
	}

	public void setNumID(String numID) {
		this.NumID = numID;
	}

	public String getSimID() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
	}

	public void setSimID(String simID) {
		this.SimID = simID;
	}

	public String getIMSI() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SIMID);
	}

	public void setIMSI(String iMSI) {
		IMSI = iMSI;
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

	public String getBusiType() {
		//网别
		String net_type = CommonDataFactory.getInstance().getProductSpec(notNeedReqStrOrderId, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
		if("2G".equals(net_type)){
			return EcsOrderConsts.BSS_BUSITYPE_OPEN_2G;
		}else if("3G".equals(net_type)){
			return EcsOrderConsts.BSS_BUSITYPE_OPEN_3G;
		}else if("4G".equals(net_type)){
			return EcsOrderConsts.BSS_BUSITYPE_OPEN_4G;
		}
		return "";
	}

	public void setBusiType(String busiType) {
		this.BusiType = busiType;
	}

	public String getCardData() {
		if (CardData == null || "".equals(CardData)) {
			List<OrderItemBusiRequest> orderItemBusiRequests = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests();
			if ((orderItemBusiRequests == null) || (orderItemBusiRequests.isEmpty())) {
				return null;
			}
			OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
			if (orderItemExtBusiRequest == null) {
				return null;
			}
			CardData = orderItemExtBusiRequest.getCard_data();
		}
		return CardData;
	}

	public void setCardData(String cardData) {
		this.CardData = cardData;
	}

	public String getCardDataProcID() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PROC_ID);
	}

	public void setCardDataProcID(String cardDataProcID) {
		this.CardDataProcID = cardDataProcID;
	}

	public List<BSSParaVo> getPara() {
		List<BSSParaVo> params = new ArrayList<BSSParaVo>();
		BSSParaVo p = new BSSParaVo();
		p.setParaID("ORDER_TYPE");
		p.setParaValue("00");
		params.add(p);
		return params;
	}

	public void setPara(List<BSSParaVo> para) {
		this.Para = para;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "ecaop.trades.sell.mob.newu.opencarddate.syn.bss";
	}

}
