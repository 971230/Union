package com.ztesoft.net.mall.core.action.backend;

import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.IOrderDirector;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.*;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;
import com.ztesoft.net.mall.core.service.*;
import params.goodstype.req.GoodsTypeReq;
import services.GoodsTypeInf;

import java.util.List;

/**
 * 收退货处理类
 * 
 * @author wui
 * 
 */
public class ShipAction extends WWAction {
	
	
	private ILogiManager logiManager;
	private IDlyTypeManager dlyTypeManager;
	private IOrderManager orderManager;
	public IOrderNFlowManager orderNFlowManager;
	private IOrderUtils orderUtils;
	private String orderId;
	private Order ord;
	private List logiList;
	private List dlyTypeList;
	private List itemList;
	private List giftList;
	
	private Delivery delivery;
	private String[] goods_nameArray;
	private String [] oldterminalArray;
	private String [] newterminalArray;
	private String[] goods_snArray;
	private String[] goods_idArray;
	private String[] product_idArray;
	private String[] itemid_idArray;
	private Integer[] numArray;
	private String btn_action;
	
	
	/********以下为赠品发货信息***********/
	private String[] giftidArray;
	private String[] giftnameArray;
	private Integer[] giftnumArray;
	private boolean hasGift; //是否有赠品货物
	
	private Regions province;
	private Regions city;
	private Regions region;
	private IOrderDirector orderDirector;
	
	private GoodsTypeInf goodsTypeServ;

	public Regions getProvince() {
		return province;
	}
	public void setProvince(Regions province) {
		this.province = province;
	}
	public Regions getCity() {
		return city;
	}
	public void setCity(Regions city) {
		this.city = city;
	}
	public Regions getRegion() {
		return region;
	}
	public void setRegion(Regions region) {
		this.region = region;
	}

	/**
	 * 显示发货对话框
	 * @return
	 */
	public String showShipDialog(){
		this.fillShipData();
		//读取未发货明细
		this.itemList = this.orderNFlowManager.listNotShipGoodsItem(orderId);
		dealItemList();
		ord = orderManager.get(orderId);
		giftList = this.orderNFlowManager.listNotShipGiftItem(orderId);
		hasGift= giftList==null||giftList.isEmpty()?false:true;
		return "ship_dialog";
	}
	private void dealItemList() {
		if(!ListUtil.isEmpty(this.itemList)){
			for (int i = 0; i < this.itemList.size(); i++) {
				OrderItem orderItem =  (OrderItem)this.itemList.get(i);
				
				GoodsTypeReq goodsTypeReq = new GoodsTypeReq();
				goodsTypeReq.setGoods_id(orderItem.getGoods_id());
				GoodsTypeDTO goodsType = goodsTypeServ.getGoodsType(goodsTypeReq).getGoodsTypeDTO();
				String stock =goodsType.getHave_stock();
				if(StringUtil.isEmpty(stock))
					stock = Consts.HAVE_STOCK_0;
				orderItem.setHave_stock(stock);
				
				//库存统一处理,计算库存
//				if(OrderBuilder.CLOUD_KEY.equals(orderManager.get(orderId).getType_code()))
//				{
//					
//				}else if(OrderBuilder.CLOUD_KEY.equals(orderManager.get(orderId).getType_code()))
//				{
//					
//				}
				orderItem.setType_code(goodsType.getType_code());
			}
		}
	}
	
	

	/**
	 * 显示确认收货对话框
	 * @return
	 */
	public String showShipConfirmDialog(){
		this.fillShipData();
		//读取未发货明细
		this.itemList = this.orderNFlowManager.listNotShipGoodsItem(orderId);
		giftList = this.orderNFlowManager.listNotShipGiftItem(orderId);
		hasGift= giftList==null||giftList.isEmpty()?false:true;
		return "ship_confirm_dialog";
	}
	
	
	
	
	
	/**
	 * 显示退货对话框
	 * @return
	 */
	public String showReturnDialog(){
		this.fillShipData();
		//读取已发货明细
		this.itemList = this.orderNFlowManager.listShipGoodsItem(orderId);		//商品已发货明细
		
		dealItemList();
		giftList = this.orderNFlowManager.listShipGiftItem(orderId);
		hasGift= giftList==null||giftList.isEmpty()?false:true;
		return "return_dialog";
	}
	
	
	/**
	 * 显示换货对话框
	 * @return
	 */
	public String showChangeDialog(){
		
		this.fillShipData();
		
		//读取已发货明细
		this.itemList = this.orderNFlowManager.listShipGoodsItem(orderId);		//商品已发货明细
		
		dealItemList();
		giftList = this.orderNFlowManager.listShipGiftItem(orderId);
		hasGift= giftList==null||giftList.isEmpty()?false:true;
		return "change_dialog";
	}
	
	

	private void fillShipData(){
		logiList  = logiManager.list();
		dlyTypeList =  dlyTypeManager.list();
		this.ord = this.orderManager.get(orderId);
	}


	/**
	 * 确认收货处理
	 * @return
	 */
	public String confirm_ship(){
		try{
			Order order = this.orderManager.get(orderId);
			
			OrderRequst orderRequst = new OrderRequst();
			orderRequst.setService_name(order.getType_code());
			orderRequst.setFlow_name(OrderBuilder.DILVERY);
			orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_ORDER);
			orderRequst.setShip_action(OrderStatus.SHIP_ACTION_CONFIRM_SHIP);
			orderRequst.setOrderId(orderId);
			orderDirector.getOrderBuilder().buildOrderFlow();
			ShipRequest shipRequest = new ShipRequest();
			shipRequest.setDelivery(delivery);
			shipRequest.setGoods_idArray(goods_idArray);
			shipRequest.setGoods_nameArray(goods_nameArray);
			shipRequest.setNumArray(numArray);
			shipRequest.setProduct_idArray(product_idArray);
			shipRequest.setGiftidArray(giftidArray);
			shipRequest.setGoods_snArray(goods_snArray);
			orderRequst.setShipRequest(shipRequest);
			orderDirector.perform(orderRequst);
			this.json="{result:1,message:'订单["+getOrderId()+"]处理成功',shipStatus:"+order.getShip_status()+"}";
		}catch(RuntimeException e){
			if(logger.isDebugEnabled())
				logger.debug(e.getStackTrace());
			this.json="{result:0,message:\"处理失败："+e.getLocalizedMessage()+"\"}";
		
		}
		
		return JSON_MESSAGE;
	}
	
	
	
	
	/**
	 * 发货处理
	 * @return
	 */
	public String ship(){
		try{
			Order order = this.orderManager.get(orderId);
		
			OrderRequst orderRequst = new OrderRequst();
			orderRequst.setService_name(order.getType_code());
			orderRequst.setFlow_name(OrderBuilder.DILVERY);
			orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_ORDER);
			orderRequst.setShip_action(OrderStatus.SHIP_ACTION_SHIP);
			orderRequst.setOrderId(orderId);
			orderDirector.getOrderBuilder().buildOrderFlow();
			
			ShipRequest shipRequest = new ShipRequest();
			shipRequest.setDelivery(delivery);
			shipRequest.setGoods_idArray(goods_idArray);
			shipRequest.setGoods_nameArray(goods_nameArray);
			shipRequest.setNumArray(numArray);
			shipRequest.setProduct_idArray(product_idArray);
			shipRequest.setGiftidArray(giftidArray);
			shipRequest.setItemid_idArray(itemid_idArray);
			shipRequest.setGoods_snArray(goods_snArray);
			orderRequst.setShipRequest(shipRequest);
			orderDirector.perform(orderRequst);
			this.json="{result:1,message:'订单["+order.getSn()+"]处理成功',shipStatus:"+order.getShip_status()+"}";
		}catch(RuntimeException e){
				if(logger.isDebugEnabled())
					logger.debug(e.getStackTrace());
				this.json="{result:0,message:\"处理失败："+e.getLocalizedMessage()+"\"}";
			
		}catch(Exception e){
			if(logger.isDebugEnabled())
				logger.debug(e.getStackTrace());
			this.json="{result:0,message:\"处理失败："+e.getLocalizedMessage()+"\"}";
		}
		
		return JSON_MESSAGE;
	}
	
	
	/**
	 * 退货
	 * @return
	 */
	public String returned(){
		try{
			Order order = this.orderManager.get(orderId);
			
			OrderRequst orderRequst = new OrderRequst();
			orderRequst.setService_name(OrderBuilder.COMMON_KEY);
			orderRequst.setFlow_name(OrderBuilder.DILVERY);
			orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_CANCEL_SHIPPING);
			orderRequst.setOrderId(orderId);
			ShipRequest shipRequest = new ShipRequest();
			
			orderDirector.getOrderBuilder().buildRefundShipFlow();
			shipRequest.setDelivery(delivery);
			shipRequest.setGoods_idArray(goods_idArray);
			shipRequest.setGoods_nameArray(goods_nameArray);
			shipRequest.setNumArray(numArray);
			shipRequest.setProduct_idArray(product_idArray);
			shipRequest.setItemid_idArray(itemid_idArray);
			shipRequest.setGiftidArray(giftidArray);
			shipRequest.setGoods_snArray(goods_snArray);
			shipRequest.setBtn_action(btn_action);
			orderRequst.setShipRequest(shipRequest);
			orderDirector.perform(orderRequst);
			this.json="{result:1,message:'订单["+order.getSn()+"]处理成功',shipStatus:"+order.getShip_status()+"}";
		}catch(RuntimeException e){
			if(logger.isDebugEnabled())
				logger.debug(e.getStackTrace());
			this.json="{result:0,message:\"处理失败："+e.getLocalizedMessage()+"\"}";
		
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	
	/**
	 * 换货
	 * @return
	 */
	public String change() {
		try {
			Order order = this.orderManager.get(orderId);
		
			OrderRequst orderRequst = new OrderRequst();
			orderRequst.setService_name(OrderBuilder.COMMON_KEY);
			orderRequst.setFlow_name(OrderBuilder.DILVERY);
			orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_CHANGE_SHIPPING);
			
			orderRequst.setOrderId(orderId);
			ShipRequest shipRequest = new ShipRequest();
			orderDirector.getOrderBuilder().buildChangeShipFlow();
			shipRequest.setDelivery(delivery);
			shipRequest.setGoods_idArray(goods_idArray);
			shipRequest.setItemid_idArray(itemid_idArray);
			shipRequest.setGoods_nameArray(goods_nameArray);
			shipRequest.setNumArray(numArray);
			shipRequest.setProduct_idArray(product_idArray);
			shipRequest.setGiftidArray(giftidArray);
			shipRequest.setGoods_snArray(goods_snArray);
			shipRequest.setOldterminalArray(oldterminalArray);
			shipRequest.setNewterminalArray(newterminalArray);
			shipRequest.setBtn_action(btn_action);
			orderRequst.setShipRequest(shipRequest);
			orderDirector.perform(orderRequst);
			this.json = "{result:1,message:'订单[" + order.getSn()
					+ "]处理成功',shipStatus:" + order.getShip_status() + "}";
		} catch (RuntimeException e) {
	
				logger.info(e.getMessage(), e);
				this.json = "{result:0,message:\"处理失败："
						+ e.getLocalizedMessage() + "\"}";
			
		}

		return WWAction.JSON_MESSAGE;
	}

	public ILogiManager getLogiManager() {
		return logiManager;
	}



	public void setLogiManager(ILogiManager logiManager) {
		this.logiManager = logiManager;
	}



	public IDlyTypeManager getDlyTypeManager() {
		return dlyTypeManager;
	}



	public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
		this.dlyTypeManager = dlyTypeManager;
	}



	public List getLogiList() {
		return logiList;
	}



	public void setLogiList(List logiList) {
		this.logiList = logiList;
	}



	public List getDlyTypeList() {
		return dlyTypeList;
	}



	public void setDlyTypeList(List dlyTypeList) {
		this.dlyTypeList = dlyTypeList;
	}



	public IOrderManager getOrderManager() {
		return orderManager;
	}



	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}



	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public Order getOrd() {
		return ord;
	}



	public void setOrd(Order ord) {
		this.ord = ord;
	}


	public List getItemList() {
		return itemList;
	}



	public void setItemList(List itemList) {
		this.itemList = itemList;
	}



	public Delivery getDelivery() {
		return delivery;
	}



	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}



	public String[] getGoods_nameArray() {
		return goods_nameArray;
	}



	public void setGoods_nameArray(String[] goodsNameArray) {
		goods_nameArray = goodsNameArray;
	}



	public String[] getGoods_idArray() {
		return goods_idArray;
	}



	public void setGoods_idArray(String[] goodsIdArray) {
		goods_idArray = goodsIdArray;
	}



	public String[] getProduct_idArray() {
		return product_idArray;
	}



	public void setProduct_idArray(String[] productIdArray) {
		product_idArray = productIdArray;
	}



	public Integer[] getNumArray() {
		return numArray;
	}



	public void setNumArray(Integer[] numArray) {
		this.numArray = numArray;
	}



	public List getGiftList() {
		return giftList;
	}



	public void setGiftList(List giftList) {
		this.giftList = giftList;
	}



	public Integer[] getGiftnumArray() {
		return giftnumArray;
	}



	public void setGiftnumArray(Integer[] giftnumArray) {
		this.giftnumArray = giftnumArray;
	}



	public String[] getGiftidArray() {
		return giftidArray;
	}



	public void setGiftidArray(String[] giftidArray) {
		this.giftidArray = giftidArray;
	}



	public String[] getGiftnameArray() {
		return giftnameArray;
	}



	public void setGiftnameArray(String[] giftnameArray) {
		this.giftnameArray = giftnameArray;
	}



	public boolean isHasGift() {
		return hasGift;
	}



	public void setHasGift(boolean hasGift) {
		this.hasGift = hasGift;
	}



	public String[] getGoods_snArray() {
		return goods_snArray;
	}



	public void setGoods_snArray(String[] goodsSnArray) {
		goods_snArray = goodsSnArray;
	}
	public IOrderUtils getOrderUtils() {
		return orderUtils;
	}
	public void setOrderUtils(IOrderUtils orderUtils) {
		this.orderUtils = orderUtils;
	}
	public IOrderNFlowManager getOrderNFlowManager() {
		return orderNFlowManager;
	}
	public void setOrderNFlowManager(IOrderNFlowManager orderNFlowManager) {
		this.orderNFlowManager = orderNFlowManager;
	}
	public String[] getOldterminalArray() {
		return oldterminalArray;
	}
	public void setOldterminalArray(String[] oldterminalArray) {
		this.oldterminalArray = oldterminalArray;
	}
	public String[] getNewterminalArray() {
		return newterminalArray;
	}
	public void setNewterminalArray(String[] newterminalArray) {
		this.newterminalArray = newterminalArray;
	}
	public String[] getItemid_idArray() {
		return itemid_idArray;
	}
	public void setItemid_idArray(String[] itemid_idArray) {
		this.itemid_idArray = itemid_idArray;
	}
	public String getBtn_action() {
		return btn_action;
	}
	public void setBtn_action(String btn_action) {
		this.btn_action = btn_action;
	}
	public IOrderDirector getOrderDirector() {
		return orderDirector;
	}
	public void setOrderDirector(IOrderDirector orderDirector) {
		this.orderDirector = orderDirector;
	}
	public GoodsTypeInf getGoodsTypeServ() {
		return goodsTypeServ;
	}
	public void setGoodsTypeServ(GoodsTypeInf goodsTypeServ) {
		this.goodsTypeServ = goodsTypeServ;
	}
	
}
