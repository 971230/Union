package com.ztesoft.net.mall.core.action.order.orderc;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.consts.OrderFlowConst;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.AbstractHander;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.DeliveryItem;
import com.ztesoft.net.mall.core.model.OrderAuditRequest;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.OrderRel;
import com.ztesoft.net.mall.core.model.PaymentLog;
import com.ztesoft.net.mall.core.model.ShipRequest;
import com.ztesoft.net.mall.core.service.IOrderApplyManage;
import com.ztesoft.net.mall.core.service.IOrderApplycMamager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.service.IOrderFlowBussManager;
import com.ztesoft.net.sqls.SF;

public class GoodsReturnedShipHander extends AbstractHander{
	
	private IOrderApplycMamager orderApplycMamager;
	@Resource
	private IOrderApplyManage orderApplyManage;

	public IOrderApplycMamager getOrderApplycMamager() {
		return orderApplycMamager;
	}

	public void setOrderApplycMamager(IOrderApplycMamager orderApplycMamager) {
		this.orderApplycMamager = orderApplycMamager;
	}

	@Override
	public boolean isCanExecute() {
		// TODO Auto-generated method stub
		return true;
	}

	private IOrderFlowBussManager orderFlowBussManager;
	
	@Override
	public void execute() {
		ShipRequest shipRequest =getOrderRequst().getShipRequest();
		String order_type = OrderStatus.ORDER_TYPE_4;
		if("exchange".equals(shipRequest.getAction()))
			order_type = OrderStatus.ORDER_TYPE_3;
		List<OrderApply> list = orderApplycMamager.queryApplyByOrderId(getOrderRequst().getOrderId(),order_type, OrderStatus.ORDER_APPLY_STATUS_1);
		if(list==null || list.size()<1) throw new IllegalArgumentException("apply orderid is not found");
		
		String btn_action = shipRequest.getBtn_action();
		if("not_allow".equals(btn_action)) //不允许换货、则插入日志，更新关联表的订单状态为不允许换货
		{
			dealOuterOrderApply();
			return;
		}
		
		String goods_idArray [] =shipRequest.getGoods_idArray();
		String goods_nameArray [] =shipRequest.getGoods_nameArray();
		String goods_snArray [] =shipRequest.getGoods_snArray();
		String product_idArray [] =shipRequest.getProduct_idArray();
		Integer numArray [] =shipRequest.getNumArray();
		String giftidArray []= shipRequest.getGiftidArray();
		String giftnameArray []= shipRequest.getGiftnameArray();
		Integer giftnumArray []= shipRequest.getGiftnumArray();
		Delivery delivery =shipRequest.getDelivery();
		String [] itemid_idArray = shipRequest.getItemid_idArray();
		
		List<DeliveryItem> itemList  = new ArrayList<DeliveryItem>();
		int i=0;
		if(goods_idArray !=null){ //商品未发货直接退货
			for(String goods_id :goods_idArray){
				
				DeliveryItem item = new DeliveryItem();
				item.setGoods_id(goods_id);
				item.setName(goods_nameArray[i]);
				item.setNum(numArray[i]);  
				item.setProduct_id(product_idArray[i]);
				item.setSn(goods_snArray[i]);
				item.setOrder_item_id(itemid_idArray[i]);
				itemList.add(item);
				i++;
			}
			
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
					item.setSn(goods_snArray[i]);
					giftitemList.add(item);
					i++;
				}
			}	
			
			delivery.setOrder_id(getOrder().getOrder_id());
			this.orderNFlowManager.returned(delivery, itemList,giftitemList);
		}
		
		//已受理才允许处理   生成退费订单  
		/*if(getOrder().getStatus()>=OrderStatus.ORDER_ACCEPT || OrderStatus.ORDER_CHANGED == getOrder().getStatus()){  //或已经换货
			assembleRefundOrderRel(goods_idArray, i);
		}*/
		
		/**
		 * 增加退款单与更新状态
		 */
		String order_applyArray [] =shipRequest.getOrder_applyArray();
		String sid = order_applyArray[0];//把之前的一个商品只有一张申请单改为一个订单只有一张申请单 
		//for(String sid:order_applyArray){
			if(sid!=null && !"".equals(sid) && !"0".equals(sid)){
				//新增退款单=======
				com.ztesoft.net.mall.core.model.OrderApply oa = orderApplyManage.getOrderApply(sid);
				String oaid = oa.getOrder_apply_id();
				if(oa.getPay_price()>0){
					oa.setOrder_apply_id(null);
					oa.setService_type(OrderStatus.ORDER_TYPE_2);
					oa.setApply_state(OrderStatus.ORDER_APPLY_STATUS_1);
					orderApplyManage.addOrderApply(oa);
					
					OrderRel orderRel  = new OrderRel();
					orderRel.setZ_order_id(oa.getOrder_apply_id());
					orderRel.setCreate_date(DBTUtil.current());
					orderRel.setZ_table_name("ES_ORDER_APPLY");
					orderRel.setRel_type(OrderStatus.ORDER_TYPE_2);
					orderRel.setState(OrderStatus.ORDER_REL_STATE_0);
					orderRel.setA_order_id(oa.getA_order_item_id());
					orderRel.setComments("退货生成的退款单"); //
					orderNFlowManager.saveOrderRel(orderRel);
				}else{
					orderFlowBussManager.updateOrderTodoListByOrderIdAndFlowType(oa.getA_order_item_id(), OrderFlowConst.FLOW_DEF_TYPE_REFUND, 3);
				}
				//新增退款单=======
				//更新状态
				String apply_status = OrderStatus.ORDER_APPLY_STATUS_3;
				if("exchange".equals(shipRequest.getAction()))
					apply_status = OrderStatus.ORDER_APPLY_STATUS_7;
				orderApplycMamager.updateApplyState(oaid, apply_status);
				this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_REL_UPDATE"), OrderStatus.ORDER_APPLY_REL_STATE_1,oaid);
			}
		//}

		//更新退货状态为已处理
		/*try{
			String z_order_id = orderNFlowManager.getZOrderId(getOrder().getOrder_id(), OrderStatus.ORDER_TYPE_4);
			OrderRel orderRel = new OrderRel();
			orderRel.setZ_order_id(z_order_id);
			orderRel.setRel_type(OrderStatus.ORDER_TYPE_4);
			orderRel.setState(OrderStatus.ORDER_REL_STATE_1);
			orderRel.setState_date(Consts.SYSDATE);
			orderRel.setComments("分销平台退货已处理");
			orderNFlowManager.updateOrderRel(orderRel);
		}catch (Exception e) {
			// TODO: handle exception
		}*/
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 不允许退货
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-28
	 */
	private void dealOuterOrderApply() {
		//设置不允许退货
		this.addLog("订单"+getOrder().getOrder_id()+"不允许退货");
		String z_order_id = orderNFlowManager.getZOrderId(getOrder().getOrder_id(), OrderStatus.ORDER_TYPE_4);
		if(!StringUtil.isEmpty(z_order_id)){
			try{
				OrderOuter orderOuter = new OrderOuter();
				orderOuter.setOrder_id(z_order_id);
				orderOuter.setComments("分销平台不允许退货");
				orderNFlowManager.updateOrderOuter(orderOuter); //更新外系统订单
			}catch (Exception e) {
				
			}
			OrderRel orderRel = new OrderRel();
			orderRel.setZ_order_id(z_order_id);
			orderRel.setRel_type(OrderStatus.ORDER_TYPE_4);
			orderRel.setState(OrderStatus.ORDER_REL_STATE_1);
			orderRel.setComments("分销平台不允许退货");
			orderNFlowManager.updateOrderRel(orderRel);
			
		}
	}

	//退货派生退费单
	private void assembleRefundOrderRel(String[] goods_idArray, int i) {
		
		if(OrderBuilder.CONTRACT_KEY.equals(getOrder().getType_code())) //已收费才允许生成退费订)
		{
			//TODO 换货成功生成退费订单
			if(isFirstPartnerOrder()){ //一级分销商的退货单
				OrderRel orderRel  = new OrderRel();
				orderRel.setA_order_id(getOrder().getOrder_id());
				orderRel.setZ_order_id(getOrder().getOrder_id());
				orderRel.setCreate_date(DBTUtil.current());
				orderRel.setZ_table_name("ES_ORDER");
				orderRel.setRel_type(OrderStatus.ORDER_TYPE_2);
				orderRel.setState(OrderStatus.ORDER_REL_STATE_1);
				orderRel.setComments("当前退货单归属商户为【一级分销商】，通过销售品退订将费用退给一级分销商"); //
				orderNFlowManager.saveOrderRel(orderRel);
				
				
				//TODO根据退费金额插入退费记录
				if(getOrder().getStatus()>=OrderStatus.ORDER_ACCEPT){ //已收费才允许退费
					PaymentLog payment = orderNFlowManager.queryPaymentLogByOrderId(getOrder().getOrder_id(), "1"); //获取已支付的
					if(payment !=null)
						orderNFlowManager.refund(payment);
				}
				
				
				
			}else if(isSecondPartnerOrder()){ //二级分销商的退货单
				
				//自动生成退费单，//退费单处理逻辑
				OrderRel orderRel  = new OrderRel();
				orderRel.setA_order_id(getOrder().getOrder_id());
				orderRel.setZ_order_id(getOrder().getOrder_id());
				orderRel.setCreate_date(DBTUtil.current());
				orderRel.setZ_table_name("ES_ORDER");
				orderRel.setRel_type(OrderStatus.ORDER_TYPE_2);
				orderRel.setState(OrderStatus.ORDER_REL_STATE_0);
				orderRel.setComments("二级分销商合约机退货，通过销售品退订将费用退给一级分销商，通过预存金充值冲正到二级分销商帐户"); //
				orderNFlowManager.saveOrderRel(orderRel);
				
				//退费单到审核环节、待电信人员审核通过后给予退费处理
				OrderAuditRequest orderAudit = new OrderAuditRequest();
				orderAudit.setApply_message("二级分销商合约机办理退货，通过销售品退订将费用退给一级分销商，并派生退费单将退费金额通过预存金充值冲正到二级分销商帐户");
				orderAudit.setFrom_userid(getOrder().getUserid());
				orderAudit.setTo_userid(ManagerUtils.getAdminUser().getParuserid());
				orderAudit.setCreate_date(DBTUtil.current());
				orderAudit.setOrder_id(getOrder().getOrder_id());
				orderAudit.setAudit_type(OrderStatus.AUDIT_TYPE_00B);
				orderAudit.setState(OrderStatus.ORDER_AUDIT_STATE_0);
				orderAuditManager.apply(orderAudit);
			}
			this.contractManager.cancel(getOrderRequst(), getOrderResult());
		}
	}

	public IOrderApplyManage getOrderApplyManage() {
		return orderApplyManage;
	}

	public void setOrderApplyManage(IOrderApplyManage orderApplyManage) {
		this.orderApplyManage = orderApplyManage;
	}

	public IOrderFlowBussManager getOrderFlowBussManager() {
		return orderFlowBussManager;
	}

	public void setOrderFlowBussManager(IOrderFlowBussManager orderFlowBussManager) {
		this.orderFlowBussManager = orderFlowBussManager;
	}
	
}
