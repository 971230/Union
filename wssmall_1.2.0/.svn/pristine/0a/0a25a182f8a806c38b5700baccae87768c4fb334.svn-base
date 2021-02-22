package zte.net.ecsord.params.wms.req;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.InfConst;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.wms.resp.NotifyOrderInfoWMSResp;
import zte.net.ecsord.params.wms.vo.CarryInfoVo;
import zte.net.ecsord.params.wms.vo.GiftInfoVo;
import zte.net.ecsord.params.wms.vo.NotifyGoodInfoVo;
import zte.net.ecsord.params.wms.vo.NotifyOrderInfoVo;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import commons.CommonTools;

/**
 * 同步订单信息到WMS系统
 * 入参对象
 */
public class NotifyOrderInfoWMSReq extends ZteRequest<NotifyOrderInfoWMSResp>{
	
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="访问流水")
	private String activeNo;
	@ZteSoftCommentAnnotationParam(name="接入Id",type="String",isNecessary="Y",desc="接入Id(由物流公司提供)")
	private String reqId;
	@ZteSoftCommentAnnotationParam(name="请求类型",type="String",isNecessary="Y",desc="请求类型,固定值：wl_syn_order")
	private String reqType;
	@ZteSoftCommentAnnotationParam(name="请求时间",type="String",isNecessary="Y",desc="请求时间，格式：yyyy-mm-dd HH:mi:ss")
	private String reqTime;
	@ZteSoftCommentAnnotationParam(name="订单信息",type="List<NotifyOrderInfoVo>",isNecessary="Y",desc="订单信息")
	private List<NotifyOrderInfoVo> orderInfo;
	@ZteSoftCommentAnnotationParam(name="订单Id",type="String",isNecessary="Y",desc="订单Id")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name="WMS系统订单Id",type="String",isNecessary="Y",desc="订单Id，WMS自动生成，唯一主键")
	private String soid;
	public String getSoid() {
		soid = "";
		return soid;
	}

	public void setSoid(String soid) {
		this.soid = soid;
	}
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		
		return "com.zte.unicomService.wms.notifyOrderInfoWMS";
	}

	public String getActiveNo() {
		
		return CommonDataFactory.getInstance().getActiveNo(AttrConsts.ACTIVE_NO_ON_OFF);
	}

	public void setActiveNo(String activeNo) {
		this.activeNo = activeNo;
	}

	public String getReqId() {
		reqId = "";
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getReqType() {
		return InfConst.WMS_WL_SYN_ORDER;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return dateFormat.format(new Date());
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public List<NotifyOrderInfoVo> getOrderInfo() {
		List<NotifyOrderInfoVo> NotifyOrderInfoVos=new ArrayList<NotifyOrderInfoVo>();
//		NotifyOrderInfoVo NotifyOrderInfoVo=new NotifyOrderInfoVo();
//		List<CarryInfoVo> CarryInfoVos=new ArrayList<CarryInfoVo>();
//		List<GiftInfoVo> giftInfo;
//		List<NotifyGoodInfoVo> notifyGoodInfoVos;
//		CarryInfoVo carryInfoVo=new CarryInfoVo();
//		NotifyOrderInfoVo.setOrderCity(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_CITY_CODE));
//		String create_time = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderBusiRequest().getCreate_time();
//		NotifyOrderInfoVo.setCreateTime(CommonTools.formatDate(create_time,"yyyy-MM-dd'T'HH:mm:ss"));
//		String order_create_time = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.TID_TIME);
//		NotifyOrderInfoVo.setOrderCreateTime(CommonTools.formatDate(order_create_time, "yyyy-MM-dd'T'HH:mm:ss"));
//		NotifyOrderInfoVo.setOrderId(orderId);
//		NotifyOrderInfoVo.setOrderOrigin(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.ORDER_ORIGIN));
//		String order_type = CommonDataFactory.getInstance().getOrderTree(orderId).getOrderBusiRequest().getOrder_type();
//		String wms_order_type = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.WMS_ORDER_TYPE, order_type);
//		NotifyOrderInfoVo.setOrdertype(wms_order_type);//WMS订单类型：1正常单，2换货单
//		//NotifyOrderInfoVo.setSoid("");
//		//收货人
//		String sending_type = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SENDING_TYPE);
//		String zb_sending_type = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ZB_CARRY_MODE, sending_type);
//		String wms_carry_mode = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.WMS_CARRY_MODE, zb_sending_type);
//		if(StringUtils.isEmpty(wms_carry_mode)){
//			wms_carry_mode = EcsOrderConsts.WMS_CARRY_MODE_DEFAULT;
//		}
//		carryInfoVo.setCarryMode(wms_carry_mode);
//		carryInfoVo.setOrderId(orderId);
//		carryInfoVo.setConsigneeName(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SHIP_NAME));
//		String logi_company_id = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SHIPPING_COMPANY);
//		carryInfoVo.setLogistics(CommonDataFactory.getInstance().getLogiCompanyCode(logi_company_id));
//		carryInfoVo.setPackageId(CommonDataFactory.getInstance().getOrderTree(orderId).getOrderBusiRequest().getGoods_id());
//		carryInfoVo.setReceiveAddress(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SHIP_ADDR));
//		carryInfoVo.setReceiveMobileNum(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.REVEIVER_MOBILE));
//		carryInfoVo.setReceiveTelNum(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.REFERENCE_PHONE));
//		carryInfoVo.setReceiveZip(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SHIP_ZIP));
//		CarryInfoVos.add(carryInfoVo);
//		NotifyOrderInfoVo.setCarryInfo(CarryInfoVos);
//		//礼品
//		giftInfo = new ArrayList<GiftInfoVo>();
//		List<AttrGiftInfoBusiRequest> gifts = CommonDataFactory.getInstance().getOrderTree(orderId).getGiftInfoBusiRequests();
//		if(gifts!=null && gifts.size()>0){
//			for(AttrGiftInfoBusiRequest gift : gifts){
//				if(EcsOrderConsts.NO_DEFAULT_VALUE.equals(gift.getIs_virtual())){//实物赠品，非实物赠品不用传给WMS
//					GiftInfoVo vo = new GiftInfoVo();
//					vo.setGiftDetailID(gift.getGift_inst_id());
//					vo.setGiftCode(gift.getSku_id());
//					vo.setGiftID(gift.getGift_id());
//					vo.setGiftName(gift.getGoods_name());
//					vo.setGiftDesc(gift.getGoods_desc());
//					vo.setGiftType(gift.getGift_type());
//					vo.setGiftNum(gift.getSku_num());
//					vo.setWhetherProcessing(gift.getIs_process());
//					vo.setProcessingType(gift.getProcess_type());
//					vo.setProcessingContent(gift.getProcess_desc());
//					giftInfo.add(vo);
//				}
//			}
//		}
//		
//		NotifyOrderInfoVo.setGiftInfo(giftInfo);
//		//商品信息
//		notifyGoodInfoVos=new ArrayList<NotifyGoodInfoVo>();
//		
//		List<OrderItemBusiRequest> orderItemBusiRequests= CommonDataFactory.getInstance().getOrderTree(orderId).getOrderItemBusiRequests();
//		if(orderItemBusiRequests != null && orderItemBusiRequests.size()>0){
//			String name = "";
//			OrderItemBusiRequest orderItemBusiRequest = orderItemBusiRequests.get(0);
//			List<OrderItemProdBusiRequest> prods = orderItemBusiRequest.getOrderItemProdBusiRequests();
//			for(OrderItemProdBusiRequest prod : prods){
//				if(EcsOrderConsts.PRODUCT_TYPE_CODE_OFFER.equals(prod.getProd_spec_type_code())
//						|| EcsOrderConsts.PRODUCT_TYPE_CODE_CONTRACT.equals(prod.getProd_spec_type_code())){
//					name += prod.getName()+";";//拼接合约计划和套餐货品的名称，作为sim卡的名称送到WMS
//				}
//			}
//			for(OrderItemProdBusiRequest prod : prods){
//				NotifyGoodInfoVo notifyGoodInfoVo=new NotifyGoodInfoVo();
//				String product_type_id = CommonDataFactory.getInstance().getTypeIdByTypeCode(prod.getProd_spec_type_code());
//				String is_physical_product = CommonDataFactory.getInstance().getDictCodeValue(StypeConsts.IS_PHYSICAL, product_type_id);
//				if(EcsOrderConsts.PRODUCT_TYPE_CODE_OFFER.equals(prod.getProd_spec_type_code())
//						|| EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_physical_product)){//传套餐货品【sim卡】、实物货品
//					notifyGoodInfoVo.setOrderId(orderId);
//					String goods_type_id = CommonDataFactory.getInstance().getGoodSpec(orderId, null, SpecConsts.TYPE_ID);		;
//					String is_old = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.IS_OLD);
//					String sim_type = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SIM_TYPE);
//					notifyGoodInfoVo.setPackType(CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.WMS_GOODS_TYPE, goods_type_id));
//					notifyGoodInfoVo.setDetailId(prod.getProd_spec_goods_id());	
//					notifyGoodInfoVo.setPackageId(prod.getItem_spec_goods_id());
//					notifyGoodInfoVo.setDetailType("1");//换货2待定
//					notifyGoodInfoVo.setProductType("");
//					notifyGoodInfoVo.setQty(1);
//					if(EcsOrderConsts.PRODUCT_TYPE_CODE_OFFER.equals(prod.getProd_spec_type_code())){//sim卡
//						//合约机新用户、号卡白卡需要写卡
//						if((EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type_id) && EcsOrderConsts.NO_DEFAULT_VALUE.equals(is_old))
//								|| EcsOrderConsts.SIM_TYPE_BK.equals(sim_type)){
//							String card_type = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.WHITE_CART_TYPE);
//							notifyGoodInfoVo.setCardType(CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.WMS_CARD_TYPE,card_type));
//						}
//						else{
//							notifyGoodInfoVo.setCardType(EcsOrderConsts.EMPTY_STR_VALUE);
//						}
//						String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.GOODS_TYPE);
//						String specification_code = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SPECIFICATION_CODE);
//						String white_card_type = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.WHITE_CART_TYPE);
//						String sku = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.SIM_CARD_SKU);
//						if(StringUtils.isEmpty(sku) || EcsOrderConsts.DEFAULT_NUMBER_SKU.equals(sku)){
//							sku = CommonDataFactory.getInstance().getNumberSku(orderId,goods_type,specification_code,sim_type,white_card_type);
//						}
//						notifyGoodInfoVo.setProductCode(sku);
//						notifyGoodInfoVo.setProductName(name);
//						notifyGoodInfoVo.setNumber(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PHONE_NUM));
//					}
//					else if(EcsOrderConsts.IS_DEFAULT_VALUE.equals(is_physical_product)){//其他实物货品
//						String sku = CommonDataFactory.getInstance().getProductSpec(orderId, product_type_id, SpecConsts.SKU);
//						notifyGoodInfoVo.setProductCode(sku);
//						notifyGoodInfoVo.setProductName(prod.getName());
//						notifyGoodInfoVo.setCardType(EcsOrderConsts.EMPTY_STR_VALUE);
//						notifyGoodInfoVo.setNumber(EcsOrderConsts.EMPTY_STR_VALUE);
//					}
//					notifyGoodInfoVos.add(notifyGoodInfoVo);
//				}
//			}
//		}
//		NotifyOrderInfoVo.setGoodInfo(notifyGoodInfoVos);
//		NotifyOrderInfoVos.add(NotifyOrderInfoVo);
		return NotifyOrderInfoVos;
	}

	public void setOrderInfo(List<NotifyOrderInfoVo> orderInfo) {
		this.orderInfo = orderInfo;
	}
}
