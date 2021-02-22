package com.ztesoft.net.mall.core.action.order.orderc;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import params.goodstype.req.GoodsTypeReq;
import services.GoodsTypeInf;

import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.IOrderDirector;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.model.Regions;
import com.ztesoft.net.mall.core.model.ShipRequest;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;
import com.ztesoft.net.mall.core.service.IDlyTypeManager;
import com.ztesoft.net.mall.core.service.ILogiManager;
import com.ztesoft.net.mall.core.service.IOrderApplycMamager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderNFlowManager;
import com.ztesoft.net.mall.core.service.IOrderUtils;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.service.IOrderFlowBussManager;
import com.ztesoft.net.model.OrderToDoList;

public class GoodsShipAction extends WWAction{

	private ILogiManager logiManager;
	private IDlyTypeManager dlyTypeManager;
	private IOrderManager orderManager;
	public IOrderNFlowManager orderNFlowManager;
	private IOrderUtils orderUtils;
	private IOrderApplycMamager orderApplycMamager;
	private String orderId;
	private Order ord;
	private List logiList;
	private List dlyTypeList;
	private List<OrderItem> itemList;
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
	private String [] order_applyArray;
	private String btn_action;
	private String logi_id_name;
	
	private GoodsTypeInf goodsTypeServ;
	
	
	/********以下为赠品发货信息***********/
	private String[] giftidArray;
	private String[] giftnameArray;
	private Integer[] giftnumArray;
	private boolean hasGift; //是否有赠品货物
	
	private Regions province;
	private Regions city;
	private Regions region;
	private IOrderDirector orderDirector;

	public String[] getOrder_applyArray() {
		return order_applyArray;
	}
	public void setOrder_applyArray(String[] order_applyArray) {
		this.order_applyArray = order_applyArray;
	}
	public String getLogi_id_name() {
		return logi_id_name;
	}
	public void setLogi_id_name(String logi_id_name) {
		this.logi_id_name = logi_id_name;
	}
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

	private void dealItemList() {
		if(!ListUtil.isEmpty(this.itemList)){
			for (int i = 0; i < this.itemList.size(); i++) {
				OrderItem orderItem =  this.itemList.get(i);
				
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
	 * 显示退货对话框
	 * @return
	 */
	public String showReturnDialog(){
		this.fillShipData();
		//读取已发货明细
		this.itemList = this.orderNFlowManager.listShipGoodsItem(orderId);		//商品已发货明细
		List<OrderApply> list = orderApplycMamager.queryApplyByOrderId(orderId,OrderStatus.ORDER_TYPE_4, OrderStatus.ORDER_APPLY_STATUS_1);
		for(OrderItem o:itemList){
			for(OrderApply a:list){
				if(o.getItem_id().equals(a.getItemId())){
					o.setBack_num(a.getReturned_num());
					o.setOrder_apply_id(a.getOrder_apply_id());
				}
			}
		}
		//2014-2-14去掉了
		//dealItemList();
		giftList = this.orderNFlowManager.listShipGiftItem(orderId);
		hasGift= giftList==null||giftList.isEmpty()?false:true;
		return "return_dialog";
	}
	
	private List<OrderApply> orderApplyItemList;
	
	/**
	 * 显示换货对话框
	 * @return
	 */
	public String showChangeDialog(){
		
		this.fillShipData();
		
		//读取已发货明细
		this.itemList = this.orderNFlowManager.listShipGoodsItem(orderId);		//商品已发货明细
		orderApplyItemList = orderApplycMamager.queryApplyByOrderId(orderId,OrderStatus.ORDER_TYPE_3, OrderStatus.ORDER_APPLY_STATUS_1);
		for(OrderItem o:itemList){
			for(OrderApply a:orderApplyItemList){
				if(o.getItem_id().equals(a.getItemId())){
					o.setBack_num(a.getReturned_num());
					o.setOrder_apply_id(a.getOrder_apply_id());
				}
			}
		}
		//dealItemList();
		giftList = this.orderNFlowManager.listShipGiftItem(orderId);
		hasGift= giftList==null||giftList.isEmpty()?false:true;
		return "change_dialog";
	}
	
	

	private void fillShipData(){
		logiList  = logiManager.list();
		Map map = new HashMap();
		map.put("id", "0");
		map.put("name", "--其它--");
		logiList.add(map);
		dlyTypeList =  dlyTypeManager.list();
		this.ord = this.orderManager.get(orderId);
	}
	
	private IOrderFlowBussManager orderFlowBussManager;
	private String flow_def_id;
	private OrderToDoList toDo;
	private int flag_status;
	private String [] flow_group_id;
	private String [] flow_user_id;
	private String hint;
	
	/**
	 * 退货
	 * @return
	 */
	public String returned(){
		try{
			AdminUser user = ManagerUtils.getAdminUser();
			/*toDo = orderFlowBussManager.queryUserOrderToDoList(orderId, user.getUserid());
			int next_status = orderFlowBussManager.next(orderId, flow_user_id, flow_group_id, flow_def_id, flag_status, hint, toDo,OrderFlowConst.FLOW_SERVICE_TYPE_RETURNED);
			if(next_status!=0 || flag_status!=1){
				this.json = "{result:1,message:'订单["+orderId+"]处理成功'}";
				return JSON_MESSAGE;
			}*/
			if(delivery==null)delivery = new Delivery();
			delivery.setRemark(hint);
			Order order = this.orderManager.get(orderId);
			OrderRequst orderRequst = new OrderRequst();
			orderRequst.setService_name(OrderBuilder.COMMONAGE);
			orderRequst.setFlow_name(OrderBuilder.DILVERY);
			orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_CANCEL_SHIPPING);
			orderRequst.setOrderId(orderId);
			ShipRequest shipRequest = new ShipRequest();
			String [] logi = logi_id_name.split("\\|");
			delivery.setLogi_id(logi[0]);
			delivery.setLogi_name(logi[1]);
			orderDirector.getOrderBuilder().buildGoodsRefundShipFlow();
			shipRequest.setDelivery(delivery);
			shipRequest.setGoods_idArray(goods_idArray);
			shipRequest.setGoods_nameArray(goods_nameArray);
			shipRequest.setNumArray(numArray);
			shipRequest.setProduct_idArray(product_idArray);
			shipRequest.setItemid_idArray(itemid_idArray);
			shipRequest.setGiftidArray(giftidArray);
			shipRequest.setGoods_snArray(goods_snArray);
			shipRequest.setOrder_applyArray(order_applyArray);
			shipRequest.setBtn_action(btn_action);
			orderRequst.setShipRequest(shipRequest);
			orderDirector.perform(orderRequst);
			this.json="{result:1,message:'订单["+order.getSn()+"]处理成功',shipStatus:"+order.getShip_status()+"}";

			json = orderFlowBussManager.returned(orderId,flow_def_id, flow_user_id, flow_group_id,flag_status,hint,delivery,logi_id_name,btn_action,goods_idArray,goods_nameArray,numArray, product_idArray, itemid_idArray, giftidArray, goods_snArray, order_applyArray);
		}catch(RuntimeException e){
			e.printStackTrace();
			if(logger.isDebugEnabled())
				logger.debug(e.getStackTrace());
			this.json="{result:0,message:\"处理失败："+e.getMessage()+"\"}";
		
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	public String exchange(){
		try{
			AdminUser user = ManagerUtils.getAdminUser();
			/*toDo = orderFlowBussManager.queryUserOrderToDoList(orderId, user.getUserid());
			int next_status = orderFlowBussManager.next(orderId, flow_user_id, flow_group_id, flow_def_id, flag_status, hint, toDo,OrderFlowConst.FLOW_SERVICE_TYPE_EXCHANGE);
			if(next_status!=0 || flag_status!=1){
				this.json = "{result:1,message:'订单["+orderId+"]处理成功'}";
				return JSON_MESSAGE;
			}*/
			
			Order order = this.orderManager.get(orderId);
			OrderRequst orderRequst = new OrderRequst();
			orderRequst.setService_name(OrderBuilder.COMMONAGE);
			orderRequst.setFlow_name(OrderBuilder.DILVERY);
			orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_CANCEL_SHIPPING);
			orderRequst.setOrderId(orderId);
			ShipRequest shipRequest = new ShipRequest();
			String [] logi = logi_id_name.split("\\|");
			if(delivery==null)delivery = new Delivery();
			delivery.setRemark(hint);
			delivery.setLogi_id(logi[0]);
			delivery.setLogi_name(logi[1]);
			orderDirector.getOrderBuilder().buildGoodsRefundShipFlow();
			shipRequest.setDelivery(delivery);
			shipRequest.setGoods_idArray(goods_idArray);
			shipRequest.setGoods_nameArray(goods_nameArray);
			shipRequest.setNumArray(numArray);
			shipRequest.setProduct_idArray(product_idArray);
			shipRequest.setItemid_idArray(itemid_idArray);
			shipRequest.setGiftidArray(giftidArray);
			shipRequest.setGoods_snArray(goods_snArray);
			shipRequest.setOrder_applyArray(order_applyArray);
			shipRequest.setBtn_action(btn_action);
			shipRequest.setAction("exchange");
			orderRequst.setShipRequest(shipRequest);
			orderDirector.perform(orderRequst);
			this.json="{result:1,message:'订单["+order.getSn()+"]处理成功',shipStatus:"+order.getShip_status()+"}";
			json = orderFlowBussManager.exchange(orderId,flow_def_id, flow_user_id, flow_group_id,flag_status,hint,logi_id_name,delivery,goods_idArray, goods_nameArray,numArray, product_idArray, itemid_idArray, giftidArray, goods_snArray, order_applyArray,btn_action);
		}catch(RuntimeException e){
			e.printStackTrace();
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
	@Deprecated
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


	public List<OrderItem> getItemList() {
		return itemList;
	}



	public void setItemList(List<OrderItem> itemList) {
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
	public IOrderApplycMamager getOrderApplycMamager() {
		return orderApplycMamager;
	}
	public void setOrderApplycMamager(IOrderApplycMamager orderApplycMamager) {
		this.orderApplycMamager = orderApplycMamager;
	}
	public GoodsTypeInf getGoodsTypeServ() {
		return goodsTypeServ;
	}
	public void setGoodsTypeServ(GoodsTypeInf goodsTypeServ) {
		this.goodsTypeServ = goodsTypeServ;
	}
	public List<OrderApply> getOrderApplyItemList() {
		return orderApplyItemList;
	}
	public void setOrderApplyItemList(List<OrderApply> orderApplyItemList) {
		this.orderApplyItemList = orderApplyItemList;
	}
	public IOrderFlowBussManager getOrderFlowBussManager() {
		return orderFlowBussManager;
	}
	public void setOrderFlowBussManager(IOrderFlowBussManager orderFlowBussManager) {
		this.orderFlowBussManager = orderFlowBussManager;
	}
	public String getFlow_def_id() {
		return flow_def_id;
	}
	public void setFlow_def_id(String flow_def_id) {
		this.flow_def_id = flow_def_id;
	}
	public OrderToDoList getToDo() {
		return toDo;
	}
	public void setToDo(OrderToDoList toDo) {
		this.toDo = toDo;
	}
	public int getFlag_status() {
		return flag_status;
	}
	public void setFlag_status(int flag_status) {
		this.flag_status = flag_status;
	}
	public String[] getFlow_group_id() {
		return flow_group_id;
	}
	public void setFlow_group_id(String[] flow_group_id) {
		this.flow_group_id = flow_group_id;
	}
	public String[] getFlow_user_id() {
		return flow_user_id;
	}
	public void setFlow_user_id(String[] flow_user_id) {
		this.flow_user_id = flow_user_id;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	
}
