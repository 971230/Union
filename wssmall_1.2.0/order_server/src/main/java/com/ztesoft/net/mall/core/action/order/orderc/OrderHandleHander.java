package com.ztesoft.net.mall.core.action.order.orderc;

import java.util.ArrayList;
import java.util.List;

import params.order.req.ConfirmShippingReq;
import params.order.resp.ConfirmShippingResp;
import services.DeliveryInf;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.context.webcontext.ThreadOrderHolder;
import com.ztesoft.net.mall.core.action.order.AbstractHander;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.OrderResult;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.DeliveryFlow;
import com.ztesoft.net.mall.core.model.DeliveryItem;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderLog;
import com.ztesoft.net.mall.core.model.ShipRequest;
import com.ztesoft.net.mall.core.service.IDeliveryFlow;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

public class OrderHandleHander extends AbstractHander{
	
	//private IOrderManager orderManager;
	private IDeliveryFlow deliveryFlowManager;
	private DeliveryInf deliveryServ;

	@Override
	public boolean isCanExecute() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void execute() {
		OrderRequst request = ThreadOrderHolder.getOrderParams().getOrderRequst();
		if(OrderBuilder.ORDER_BH.equals(request.getFlow_name())){
			confirmBL(request);
		}else if(OrderBuilder.ORDER_FH.equals(request.getFlow_name())){
			confirmFH(request);
		}else if(OrderBuilder.ORDER_SH.equals(request.getFlow_name())){
			confirmSH(request);
		}else if(OrderBuilder.ORDER_FINISH.equals(request.getFlow_name())){
			confirmFinish(request);
		}
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 确认发货
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-12 
	 * @param request
	 */
	public void confirmFH(OrderRequst request){
		Order order = orderManager.get(request.getOrderId());
		ShipRequest shipRequest =getOrderRequst().getShipRequest();
		String goods_idArray [] =shipRequest.getGoods_idArray();
		String goods_nameArray [] =shipRequest.getGoods_nameArray();
		String goods_snArray [] =shipRequest.getGoods_snArray();
		String product_idArray [] =shipRequest.getProduct_idArray();
		Integer numArray [] =shipRequest.getNumArray();
		String giftidArray []= shipRequest.getGiftidArray();
		String giftnameArray []= shipRequest.getGiftnameArray();
		Integer giftnumArray []= shipRequest.getGiftnumArray();
		Delivery delivery =shipRequest.getDelivery();
		
		String [] itemids = shipRequest.getItemid_idArray();
		
		List<DeliveryItem> itemList  = new ArrayList<DeliveryItem>();
		int i=0;
		int totalNum = 0;
		for(String goods_id :goods_idArray){
			DeliveryItem item = new DeliveryItem();
			item.setGoods_id(goods_id);
			item.setName(goods_nameArray[i]);
			item.setNum(numArray[i]);  
			item.setProduct_id(product_idArray[i]);
			item.setSn(goods_snArray[i]);
			item.setItemtype(0);
			//item.setItem_id(itemids[i]);
			item.setOrder_item_id(itemids[i]);
			itemList.add(item);
			if(item.getNum()!=null){
				totalNum += item.getNum();
			}
			i++;
		}
	    if(totalNum==0)throw new IllegalArgumentException("商品发货总数不能为[0]");
		i=0;
		List<DeliveryItem> giftitemList  = new ArrayList<DeliveryItem>();
		if(giftidArray!=null){
			for(String giftid :giftidArray){
				DeliveryItem item = new DeliveryItem();
				item.setGoods_id(giftid);
				item.setName(giftnameArray[i]);
				item.setNum(giftnumArray[i]);  
				item.setProduct_id(giftid);
				item.setItemtype(2);
				giftitemList.add(item);
				item.setOrder_item_id(itemids[i]);
				i++;
			}
		}
		//delivery.setOrder_id(order.getOrder_id());
		ConfirmShippingReq req = new ConfirmShippingReq();
		req.setDelivery_id(delivery.getDelivery_id());
		req.setDeliveryItems(itemList);
		req.setGiftitemList(giftitemList);
		req.setDelivery(delivery);
		req.setShippingType("1");
		req.setHouse_id(delivery.getHouse_id());
		//req.setNext_deal_group_id(request.getOrderParam().getFlow_group_id()[0]);
		//req.setNext_deal_user_id(request.getOrderParam().getFlow_user_id()[0]);
		ConfirmShippingResp resp = deliveryServ.confirmShipping(req);
		if("2".equals(resp.getError_code())){
			OrderResult result = ThreadOrderHolder.getOrderParams().getOrderResult();
			result.setCode("0");
			result.setMessage(resp.getError_msg());
			result.setOrder(order);
			return ;
		}
		if("0".equals(delivery.getLogi_id())){
			//写物流流程表
			String delivery_id = delivery.getDelivery_id(); 
			OrderHandleParam param = request.getOrderParam();
			DeliveryFlow flow = param.getFlow();
			AdminUser user = ManagerUtils.getAdminUser();
			if(flow!=null){
				flow.setCreate_time(DBTUtil.current());
				flow.setOp_id(user.getUserid());
				flow.setOp_name(user.getUsername());
				flow.setDelivery_id(delivery_id);
				deliveryFlowManager.addDeliveryFlow(flow);
			}
		}
		
		//完成订单处理组
		//this.baseDaoSupport.execute("update es_order t set t.status=?,t.ship_status=? where t.order_id=?",OrderStatus.ORDER_SHIP,OrderStatus.SHIP_YES,order.getOrder_id());
		//===写日志===
		//addOrderLog("确认发货成功:"+delivery.getRemark(),order.getOrder_id());
		OrderResult result = ThreadOrderHolder.getOrderParams().getOrderResult();
		result.setCode("1");
		result.setMessage("处理成功!");
		result.setOrder(order);
	}
	
	/**
	 * 完成
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-12 
	 * @param request
	 */
	public void confirmFinish(OrderRequst request){
		Order order = orderManager.get(request.getOrderParam().getOrderId());
		if(OrderStatus.ORDER_CONFIRM_SHIP==order.getStatus()){
			//已去付可备货
			this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"),OrderStatus.ORDER_COMPLETE,order.getOrder_id());
			//===写日志===
			addOrderLog("订单成功["+request.getOrderParam().getHint()+"]",order.getOrder_id());
			OrderResult result = ThreadOrderHolder.getOrderParams().getOrderResult();
			result.setCode("1");
			result.setMessage("处理成功!");
			result.setOrder(order);
		}else{
			//非备货状态
			OrderResult result = ThreadOrderHolder.getOrderParams().getOrderResult();
			result.setCode("0");
			result.setMessage("该订单不能备货!");
		}
	}
	
	/**
	 * 确认收货
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-12 
	 * @param request
	 */
	public void confirmSH(OrderRequst request){
		Order order = orderManager.get(request.getOrderParam().getOrderId());
		if(OrderStatus.ORDER_SHIP==order.getStatus()){
			//已去付可备货
			this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"),OrderStatus.ORDER_CONFIRM_SHIP,order.getOrder_id());
			//===写日志===
			addOrderLog("确认收货成功["+request.getOrderParam().getHint()+"]",order.getOrder_id());
			OrderResult result = ThreadOrderHolder.getOrderParams().getOrderResult();
			result.setCode("1");
			result.setMessage("处理成功!");
			result.setOrder(order);
		}else{
			//非备货状态
			OrderResult result = ThreadOrderHolder.getOrderParams().getOrderResult();
			result.setCode("0");
			result.setMessage("该订单不能备货!");
		}
	}
	
	/**
	 * 处理备货状态
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-12 
	 * @param request
	 * @return
	 */
	public void confirmBL(OrderRequst request){
		Order order = orderManager.get(request.getOrderParam().getOrderId());
		int flag_status = request.getOrderParam().getFlag_status();
		//if(OrderStatus.ORDER_PAY==order.getStatus()){
			//已支付可备货
		List param = new ArrayList();
		param.add(OrderStatus.ORDER_ACCEPT);
		String sql = "update es_order set status=?";
		/*if(!StringUtils.isEmpty(request.getOrderParam().getFlow_user_id()[0])){
			sql += ",ship_user_id=?";
			param.add(request.getOrderParam().getFlow_user_id()[0]);
		}else{
			sql += ",ship_user_id=''";
		}
		if(!StringUtils.isEmpty(request.getOrderParam().getFlow_group_id()[0])){
			sql += ",ship_group_id=?";
			param.add(request.getOrderParam().getFlow_group_id()[0]);
		}else{
			sql += ",ship_group_id=''";
		}*/
		sql += " where order_id=?";
		param.add(request.getOrderParam().getOrderId());
		this.baseDaoSupport.execute(sql,param.toArray());
		//===写日志===
		addOrderLog("确认备货成功["+request.getOrderParam().getHint()+"]",order.getOrder_id());
		OrderResult result = ThreadOrderHolder.getOrderParams().getOrderResult();
		result.setCode("1");
		result.setMessage("处理成功!");
		result.setOrder(order);
		/*}else{
			//非备货状态
			OrderResult result = ThreadOrderHolder.getOrderParams().getOrderResult();
			result.setCode("0");
			result.setMessage("该订单不能备货!");
		}*/
	}
	
	public void addOrderLog(String message,String orderID){
		AdminUser user = ManagerUtils.getAdminUser();
		OrderLog log = new OrderLog();
		log.setMessage(message);
		log.setOp_id(user.getUserid());
		log.setOp_name(user.getUsername());
		//DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//log.setOp_time(df1.format(new Date()));
		log.setOp_time(DBTUtil.current());
		log.setOrder_id(orderID);
		this.baseDaoSupport.insert("order_log", log);
	}

	public IDeliveryFlow getDeliveryFlowManager() {
		return deliveryFlowManager;
	}

	public void setDeliveryFlowManager(IDeliveryFlow deliveryFlowManager) {
		this.deliveryFlowManager = deliveryFlowManager;
	}

	public DeliveryInf getDeliveryServ() {
		return deliveryServ;
	}

	public void setDeliveryServ(DeliveryInf deliveryServ) {
		this.deliveryServ = deliveryServ;
	}


	/*public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}*/

}
