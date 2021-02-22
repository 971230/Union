package zte.net.ecsord.params.ems.req;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.core.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.mq.client.common.UUID;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ems.vo.EmsItem;
import zte.net.ecsord.params.ems.vo.EmsReceiver;
import zte.net.ecsord.params.ems.vo.EmsSender;
import zte.net.ecsord.params.ems.vo.EmsWaybill;

public class EmsLogisticsInfoSyncReq extends ZteRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6523878317274705504L;
	@ZteSoftCommentAnnotationParam(name = "内部订单号", type = "String", isNecessary = "Y", desc = "内部订单号")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name = "运单详细信息", type = "String", isNecessary = "Y", desc = "电商订单号")
	private String waybill;
	@ZteSoftCommentAnnotationParam(name = "订单个数", type = "int", isNecessary = "Y", desc = "订单个数")
	private int size;
	
	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.ems.syncLogisticsInfoToEms";
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getWaybill() {
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(notNeedReqStrOrderId);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		OrderDeliveryBusiRequest delivery = CommonDataFactory.getInstance().getDeliveryBusiRequest(notNeedReqStrOrderId, EcsOrderConsts.LOGIS_NORMAL);
		String region_id = null;
		String city_id = null;
		String province_id = null;
		if(null!=delivery){
			region_id = delivery.getRegion_id().toString();
			city_id = delivery.getCity_id().toString();
			province_id = delivery.getProvince_id().toString();
		}
		//获取寄件人信息
		String shipping_company = delivery.getShipping_company();//delivery.getShipping_company();
		String order_city_code = orderExtBusiRequest.getOrder_city_code();
		Map logi_company = CommonDataFactory.getInstance().getLogiCompPersonData(shipping_company, order_city_code);
		//获取寄件方省市区信息
		Map postRegion = CommonDataFactory.getInstance().getPostRegion(order_city_code);
		
		EmsWaybill wb = new EmsWaybill();
		wb.setTxLogisticID("");
		wb.setOrderNo(notNeedReqStrOrderId);
		wb.setMailNum(delivery.getLogi_no());
		wb.setOrderType(1);
		wb.setServiceType("1");
		wb.setSendType("0");
		//寄件人信息
		EmsSender sender = new EmsSender();
		String post_linkman = (logi_company!=null && !logi_company.isEmpty())?(String)logi_company.get("post_linkman"):"";
		sender.setName(post_linkman);
		String post_code = (logi_company!=null && !logi_company.isEmpty())?(String)logi_company.get("post_code"):"";
		sender.setPostCode(post_code);
		String post_tel = (logi_company!=null && !logi_company.isEmpty())?(String)logi_company.get("post_tel"):"";
		sender.setPhone(post_tel);
		String post_address = (logi_company!=null && !logi_company.isEmpty())?(String)logi_company.get("post_address"):"";
		sender.setAddress(post_address);
		String province = (postRegion!=null && !postRegion.isEmpty())?(String)postRegion.get("province"):"";
		sender.setProv(province);
		String city = (postRegion!=null && !postRegion.isEmpty())?(String)postRegion.get("city"):"";
		sender.setCity(city);
		String district = (postRegion!=null && !postRegion.isEmpty())?(String)postRegion.get("district"):"";
		sender.setCounty(district);
		wb.setSender(sender);
		//收件人
		EmsReceiver receiver = new EmsReceiver();
		receiver.setName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIP_NAME));
		receiver.setPostCode("310000");
		receiver.setPhone(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REFERENCE_PHONE));
		receiver.setMobile(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.REVEIVER_MOBILE));
		receiver.setAddress(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.SHIP_ADDR));
		receiver.setProv(CommonDataFactory.getInstance().getLocalName(province_id));
		receiver.setCity(CommonDataFactory.getInstance().getLocalName(city_id));
		receiver.setCounty(CommonDataFactory.getInstance().getLocalName(region_id));
		wb.setReceiver(receiver);
		//商品信息
		List<EmsItem> items = new ArrayList<EmsItem>();
		EmsItem item = new EmsItem();
		item.setItemName(CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODSNAME));
		int goodsNumInt = 1;
		String goodsNum = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.GOODS_NUM);
		if(!StringUtils.isEmpty(goodsNum)){
			goodsNumInt = Integer.valueOf(goodsNum);
		}
		item.setNumber(goodsNumInt);
		long priceL = 0;
		String price = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_REAL_FEE);
		if(!StringUtils.isEmpty(price)){
			priceL = (long) (Double.valueOf(price)/10D);
		}
		item.setItemValue(priceL);
		items.add(item);
		wb.setItems(items);
		
		List<EmsWaybill> jsonList = new ArrayList<EmsWaybill>();
		jsonList.add(wb);
		Map jsonMap = new HashMap();
		jsonMap.put("waybills", jsonList);
		
		waybill = JSON.toJSONString(jsonMap);
		return waybill;
	}

	public void setWaybill(String waybill) {
		this.waybill = waybill;
	}

	public int getSize() {
		size = 1;
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
