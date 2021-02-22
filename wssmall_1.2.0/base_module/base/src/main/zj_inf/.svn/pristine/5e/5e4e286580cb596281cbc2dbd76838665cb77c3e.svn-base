package zte.net.ecsord.params.ecaop.req;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrTmResourceInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.BSSFeeInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSPayInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.ChangeResInfoVo;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.ecaop.resp.EmpIdEssIDResp;
import zte.net.ecsord.utils.DataUtil;
import zte.net.ecsord.utils.SpecUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.Goods;

/**
 * 
 * @author Administrator
 * @see 订单返销
 */
@SuppressWarnings("rawtypes")
public class BSSOrderReverseSalesReq extends ZteRequest{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 186035828290386578L;
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId:订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "操作员ID", type = "String", isNecessary = "Y", desc = "操作员ID")
	private String OperatorID; // 
	@ZteSoftCommentAnnotationParam(name = "省分", type = "String", isNecessary = "Y", desc = "省分")
	private String Province; // Y 
	@ZteSoftCommentAnnotationParam(name = "City", type = "String", isNecessary = "N", desc = "City")
	private String City; // Y 
	@ZteSoftCommentAnnotationParam(name = "District", type = "String", isNecessary = "N", desc = "District")
	private String District; // Y 	
	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "N", desc = "渠道编码")
	private String ChannelID; // Y 
	@ZteSoftCommentAnnotationParam(name = "渠道类型", type = "String", isNecessary = "Y", desc = "渠道类型")
	private String ChannelType; // 
	@ZteSoftCommentAnnotationParam(name = "接入类型", type = "String", isNecessary = "N", desc = "接入类型01 WEB； 02 短信； 03 WAP； 99 其他")
	private String AccessType; // 
	@ZteSoftCommentAnnotationParam(name = "需要返销的总部订单对应的业务类型", type = "String", isNecessary = "N", desc = "需要返销的总部订单对应的业务类型")
	private String OrigBipCode; // 
	@ZteSoftCommentAnnotationParam(name = "需要返销的总部原订单ID", type = "String", isNecessary = "Y", desc = "需要返销的总部原订单ID")
	private String EssOrigOrderID; // 
	@ZteSoftCommentAnnotationParam(name = "当前总部订单ID", type = "String", isNecessary = "N", desc = "当前总部订单ID")
	private String EssRollBackOrderID; // 
	@ZteSoftCommentAnnotationParam(name = "结算价", type = "String", isNecessary = "N", desc = "结算价")
	private String ChargePrice; // 
	@ZteSoftCommentAnnotationParam(name = "铺货标示", type = "String", isNecessary = "N", desc = "铺货标示0非铺货终端，1铺货终端")
	private String ShopGoodsFlag; // 
	@ZteSoftCommentAnnotationParam(name = "机型编码", type = "String", isNecessary = "N", desc = "机型编码")
	private String ModelCode; // 
	@ZteSoftCommentAnnotationParam(name = "原换机资源信息", type = "String", isNecessary = "N", desc = "原换机资源信息")
	private ChangeResInfoVo ChangeResInfo; // 
	@ZteSoftCommentAnnotationParam(name = "返销费用明细", type = "String", isNecessary = "N", desc = "返销费用明细")
	private List<BSSFeeInfoReqVo> FeeInfo; // 
	@ZteSoftCommentAnnotationParam(name = "应收总金额", type = "String", isNecessary = "N", desc = "应收总金额")
	private String OrigTotalFee; // 
	@ZteSoftCommentAnnotationParam(name = "客户支付信息", type = "BSSPayInfoReqVo", isNecessary = "N", desc = "客户支付信息")
	private BSSPayInfoReqVo PayInfo; // 部门
	@ZteSoftCommentAnnotationParam(name = "订单状态", type = "String", isNecessary = "N", desc = "订单状态10：竣工订单11：提交未制卡订单")
	private String OrederState; // 
	@ZteSoftCommentAnnotationParam(name = "返销原因", type = "String", isNecessary = "N", desc = "返销原因")
	private String RollBackResult; // 
	@ZteSoftCommentAnnotationParam(name = "扩展字段", type = "List", isNecessary = "N", desc = "扩展字段")
	private List<BSSParaVo> para;	

	private EmpOperInfoVo essOperInfo;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
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

	public String getOperatorID() {
		return getEssOperInfo().getEss_emp_id();
	}

	public void setOperatorID(String operatorID) {
		OperatorID = operatorID;
	}

	public String getProvince() {
		return getEssOperInfo().getProvince();
	}

	public void setProvince(String province) {
		Province = province;
	}

	public String getCity() {
		return getEssOperInfo().getCity();
	}

	public void setCity(String city) {
		City = city;
	}

	public String getDistrict() {
		return getEssOperInfo().getDistrict();
	}

	public void setDistrict(String district) {
		District = district;
	}

	public String getChannelID() {
		return getEssOperInfo().getChannel_id();
	}

	public void setChannelID(String channelID) {
		ChannelID = channelID;
	}

	public String getChannelType() {
		return getEssOperInfo().getChannel_type();
	}

	public void setChannelType(String channelType) {
		ChannelType = channelType;
	}

	public String getAccessType() {
		return EcsOrderConsts.BSS_ACCESS_TYPE_WYG;
	}

	public void setAccessType(String accessType) {
		AccessType = accessType;
	}

	public String getOrigBipCode() {
		return "WOSL0051";
	}

	public void setOrigBipCode(String origBipCode) {
		OrigBipCode = origBipCode;
	}

	public String getEssOrigOrderID() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BSS_INF_ID);
	}

	public void setEssOrigOrderID(String essOrigOrderID) {
		EssOrigOrderID = essOrigOrderID;
	}

	public String getEssRollBackOrderID() {
		return CommonDataFactory.getInstance().getOrderIdRandom(16);
	}

	public void setEssRollBackOrderID(String essRollBackOrderID) {
		EssRollBackOrderID = essRollBackOrderID;
	}

	public String getChargePrice() {
		return ChargePrice;
	}

	public void setChargePrice(String chargePrice) {
		ChargePrice = chargePrice;
	}

	public String getShopGoodsFlag() {
		return ShopGoodsFlag;
	}

	public void setShopGoodsFlag(String shopGoodsFlag) {
		ShopGoodsFlag = shopGoodsFlag;
	}

	public String getModelCode() {
		String is_physics = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.IS_PHISICS);
		String modelcode = "";
		//实物单获取资源信息
		if(StringUtils.equals(is_physics, EcsOrderConsts.IS_DEFAULT_VALUE)){
			//获取订单商品机型
			List<Goods> products = SpecUtils.getEntityProducts(notNeedReqStrOrderId);
			Goods entity = null;
			for(int i=0;i<products.size();i++){
				Goods product = products.get(i);
				//取手机终端，上网卡硬件，配件货品
				if(SpecConsts.TYPE_ID_10000.equals(product.getType_id()) 
						|| SpecConsts.TYPE_ID_10006.equals(product.getType_id())
						|| SpecConsts.TYPE_ID_10003.equals(product.getType_id())){
					entity = product;
					break;
				}
			}
			if(entity!=null && !StringUtils.isEmpty(entity.getBrand_code()) 
					&& !StringUtils.isEmpty(entity.getColor()) 
					&& !StringUtils.isEmpty(entity.getModel_code()) 
					&& !StringUtils.isEmpty(entity.getSn())){
				modelcode = entity.getSn();//机型编码
			}
		}
		return modelcode;
	}

	public void setModelCode(String modelCode) {
		ModelCode = modelCode;
	}

	public ChangeResInfoVo getChangeResInfo() {
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_TYPE);
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		List<AttrTmResourceInfoBusiRequest> resourcelist = orderTree.getTmResourceInfoBusiRequests();
		ChangeResInfoVo resvo = null;
		if (resourcelist.size()>0) {
			resvo = new ChangeResInfoVo();
			AttrTmResourceInfoBusiRequest resourceInfo = resourcelist.get(0);			
			resvo.setResourcesBrand(resourceInfo.getResources_brand_code()); // N 品牌
			resvo.setResourcesModel(resourceInfo.getResources_model_code()); // N 型号
			resvo.setResourceType(CommonDataFactory.getInstance().getOtherDictVodeValue("bss_access_type", goods_type));
			resvo.setResourcesRele(resourceInfo.getRes_rele()); // N 资源归属 01 总部管理资源 02 省分管理资源 03：全部资源 04 社会渠道资源
			resvo.setNewResourcesCode(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.TERMINAL_NUM));
			resvo.setOldResourcesCode(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OLD_TERMINAL_NUM));
		}
		if (DataUtil.checkFieldValueNull(resvo))return null;
		return resvo;
	}

	public void setChangeResInfo(ChangeResInfoVo changeResInfo) {
		ChangeResInfo = changeResInfo;
	}

	public List<BSSFeeInfoReqVo> getFeeInfo() {
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		List<AttrFeeInfoBusiRequest> list=orderTree.getFeeInfoBusiRequests();
		List<BSSFeeInfoReqVo> feeInfo = new ArrayList<BSSFeeInfoReqVo>();
		for(AttrFeeInfoBusiRequest req :list){
			if(StringUtils.equals(EcsOrderConsts.BASE_YES_FLAG_1, req.getIs_aop_fee())){
				BSSFeeInfoReqVo feevo = new BSSFeeInfoReqVo();
				feevo.setFeeID(req.getFee_item_code());
				feevo.setFeeCategory(req.getFee_category());//收费项科目
				
				String origFee = req.getO_fee_num()*1000+"";
				feevo.setOrigFee(origFee.substring(0, origFee.indexOf(".")));
				
				String reliefFee = req.getDiscount_fee()*1000+"";
				feevo.setReliefFee(reliefFee.substring(0, reliefFee.indexOf(".")));
				
				String realFee = req.getN_fee_num()*1000+"";
				feevo.setRealFee(realFee.substring(0, realFee.indexOf(".")));
				
				feevo.setReliefResult(StringUtils.isEmpty(req.getDiscount_reason())?"无":req.getDiscount_reason());
				feeInfo.add(feevo);
			}	
		}
		if (DataUtil.checkFieldValueNull(feeInfo))return null;
		return feeInfo;
	}

	public void setFeeInfo(List<BSSFeeInfoReqVo> feeInfo) {
		FeeInfo = feeInfo;
	}

	public String getOrigTotalFee() {List<OrderItemBusiRequest> orderItemBusiRequests =CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId).getOrderItemBusiRequests();
		OrderItemExtBusiRequest orderItemExtBusiRequest = orderItemBusiRequests.get(0).getOrderItemExtBusiRequest();
		return orderItemExtBusiRequest.getTotalFee();
	}

	public void setOrigTotalFee(String origTotalFee) {
		OrigTotalFee = origTotalFee;
	}

	public String getOrederState() {
		return EcsOrderConsts.BSS_ORDER_STATE_11;//这个字段，如果写了卡，就传10，没写卡就传11
	}

	public void setOrederState(String orederState) {
		OrederState = orederState;
	}

	public String getRollBackResult() {
		return "无";
	}

	public void setRollBackResult(String rollBackResult) {
		RollBackResult = rollBackResult;
	}

	public List<BSSParaVo> getPara() {
		return para;
	}

	public void setPara(List<BSSParaVo> para) {
		this.para = para;
	}
	
	public BSSPayInfoReqVo getPayInfo() {
		BSSPayInfoReqVo payvo = new BSSPayInfoReqVo();
		payvo.setPayType(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BILL_TYPE)); // 付费方式
		payvo.setPayOrg(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PAY_PROVIDER_NAME)); // 支付机构名称
		payvo.setPayNum(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BANK_ACCOUNT)); // 支付账号
		if (DataUtil.checkFieldValueNull(payvo))return null;
		return payvo;		
	}

	public void setPayInfo(BSSPayInfoReqVo payInfo) {
		PayInfo = payInfo;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		
		return "ecaop.trades.serv.curt.cannel.sub.bss";
	}

}
