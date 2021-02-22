package com.ztesoft.net.mall.core.action.order.changeship;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.AbstractHander;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wui
 * 换货处理
 *
 */

public class CommonChangeShipHander   extends AbstractHander implements IChangeShipHander {

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
		 * 4.调用CRM接口换货处理
		 * 
		 */
		change();
	}

	@Override
	public void accept() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCanExecute() {
		return this.getOrderRequst().isCan_execute();
	}


	private void dealOuterOrderApply() {
		this.addLog("订单"+getOrder().getOrder_id()+"不允许换货");
		String z_order_id = orderNFlowManager.getZOrderId(getOrder().getOrder_id(), OrderStatus.ORDER_TYPE_3);
		if(!StringUtil.isEmpty(z_order_id)){
			OrderOuter orderOuter = new OrderOuter();
			orderOuter.setOrder_id(z_order_id);
			orderOuter.setComments("分销平台不允许换货");
			orderNFlowManager.updateOrderOuter(orderOuter); //更新外系统订单
			
			
			OrderRel orderRel = new OrderRel();
			orderRel.setZ_order_id(z_order_id);
			orderRel.setRel_type(OrderStatus.ORDER_TYPE_3);
			orderRel.setState(OrderStatus.ORDER_REL_STATE_1);
			orderRel.setState_date(DBTUtil.current());
			orderNFlowManager.updateOrderRel(orderRel);
			orderRel.setComments("分销平台换货已处理");
		}
	}
	
	
	/**
	 * 换货
	 * @return
	 */
	public void change() {
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
			
			String oldterminalArray []= shipRequest.getOldterminalArray();
			String newterminalArray []= shipRequest.getNewterminalArray();
			String itemid_idArray []= shipRequest.getItemid_idArray();
			Delivery delivery =shipRequest.getDelivery();
			
			List<DeliveryItem> itemList = new ArrayList<DeliveryItem>();
			int i = 0;
			for (String goods_id : goods_idArray) {

				DeliveryItem item = new DeliveryItem();
				item.setGoods_id(goods_id);
				item.setName(goods_nameArray[i]);
				item.setNum(numArray[i]);
				item.setProduct_id(product_idArray[i]);
				item.setSn(goods_snArray[i]);
				item.setItem_id(itemid_idArray[i]);
				itemList.add(item);
				
				//已受理才允许换货处理
				if(getOrder().getStatus()>=OrderStatus.ORDER_ACCEPT )
					assembleChange(goods_idArray, oldterminalArray,newterminalArray, i, item); //变更信息
				i++;
				
			}
			delivery.setOrder_id(getOrder().getOrder_id());
			
			if(goods_idArray !=null)
				orderNFlowManager.change(delivery, itemList, null);
			
		
	}

	
	//组装终端变动信息
	private void assembleChange(String[] goods_idArray,String[] oldterminalArray, String[] newterminalArray, int i,DeliveryItem item) {
		if(OrderBuilder.CONTRACT_KEY.equals(getOrder().getType_code()))
		{
			
			
				OrderChange orderChange = new OrderChange();
				orderChange.setOrder_id(getOrder().getOrder_id());
				orderChange.setItem_id(item.getItem_id());
				orderChange.setNew_value(newterminalArray[i]);
				orderChange.setNew_value_desc(newterminalArray[i]);
				orderChange.setOld_value(oldterminalArray[i]);
				orderChange.setOld_value_desc(oldterminalArray[i]);
				orderChange.setCreate_date(DBTUtil.current());
				orderChange.setField_name("terminal_code");
				orderChange.setTable_name("ES_ORDER_ITEM");
				orderChange.setSequ(0);
				
				contractManager.change(getOrderRequst(), getOrderResult(),orderChange); //调用接口换终端
				
				//保存变动信息
				orderNFlowManager.saveChange(orderChange);
				
				//更新终端串码
				OrderItem orderItem = new OrderItem();
				orderItem.setItem_id(item.getItem_id());
				//orderItem.setTerminal_code(newterminalArray[i]); //更新终端
				orderNFlowManager.updateOrderItem(orderItem);
					
				
				//换货后更新状态为已处理
				OrderRel orderRel = new OrderRel();
				orderRel.setA_order_id(getOrder().getOrder_id());
				orderRel.setRel_type(OrderStatus.ORDER_TYPE_3);
				orderRel.setState_date(DBTUtil.current());
				orderRel.setState(OrderStatus.ORDER_REL_STATE_1);
				orderNFlowManager.updateOrderRel(orderRel);
				
				
		}
	}
	
	

	

}
