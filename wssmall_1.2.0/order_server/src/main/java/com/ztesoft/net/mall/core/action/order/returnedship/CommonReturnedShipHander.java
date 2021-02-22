package com.ztesoft.net.mall.core.action.order.returnedship;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.AbstractHander;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.model.*;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wui
 * 退货
 *
 */
public class CommonReturnedShipHander   extends AbstractHander implements IReturnedShipHander {



	@Override
	public void display() {
		
	}

	@Override
	public void execute() {
		Order order  = getOrder();
		/**
		 * 1.获取订购商品
		 * 2.获取商品分类
		 * 3.换货处理
		 * 4.调用CRM接口退订处理
		 * 
		 */
		returned();
	}

	@Override
	public void accept() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCanExecute() {
		return this.getOrderRequst().isCan_execute();
	}
	
	
	/**
	 * 退货
	 * @return
	 */
	public void returned(){
		
			ShipRequest shipRequest =getOrderRequst().getShipRequest();
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
			
			//已受理才允许处理
			if(getOrder().getStatus()>=OrderStatus.ORDER_ACCEPT || OrderStatus.ORDER_CHANGED == getOrder().getStatus())  //或已经换货
				assembleRefundOrderRel(goods_idArray, i);

			//更新退货状态为已处理
			try{
				String z_order_id = orderNFlowManager.getZOrderId(getOrder().getOrder_id(), OrderStatus.ORDER_TYPE_4);
				OrderRel orderRel = new OrderRel();
				orderRel.setZ_order_id(z_order_id);
				orderRel.setRel_type(OrderStatus.ORDER_TYPE_4);
				orderRel.setState(OrderStatus.ORDER_REL_STATE_1);
				orderRel.setState_date(DBTUtil.current());
				orderRel.setComments("分销平台退货已处理");
				orderNFlowManager.updateOrderRel(orderRel);
			}catch (Exception e) {
				// TODO: handle exception
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

	
	
	//订单处理
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
	

}
