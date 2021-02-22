package com.ztesoft.net.mall.core.action.order.orderc;

import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.IOrderDirector;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.OrderResult;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.*;
import com.ztesoft.net.mall.core.service.*;
import org.apache.struts2.ServletActionContext;
import services.WarehouseInf;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDealWidthAction  extends WWAction{

	private String orderId;
	private IOrderManager orderManager;
	private IOrderDirector orderDirector;
	private Order order;
	private List itemList;
	private String hint;
	public IOrderNFlowManager orderNFlowManager;
	private ILogiManager logiManager;
	private IDlyTypeManager dlyTypeManager;
	private IOrderUtils orderUtils;
	private List logiList;
	private List dlyTypeList;
	private List giftList;
	
	private IDeliveryFlow deliveryFlowManager;
	private DeliveryFlow flow;
	private IDeliveryManager deliveryManager;
	
	private boolean hasGift; //是否有赠品货物
	
	private ShipRequest shipRequest;
	private Delivery delivery;
	private String logi_id_name;
	
	private double shipPrice;

	private List<Warehouse> wareHouseList;

    @Resource
    private WarehouseInf warehouseServ;
	
	public String updateShipPirce(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		String json = null;
		try{
			orderManager.exchangeDeliveryPrice(orderId, shipPrice);
			json = "{'result':'1','message':'修改成功'}";
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{'result':'0','message':'修改失败'}";
		}
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 显示备货窗口
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-12 
	 * @return
	 */
	public String showCustAcceptDialog(){
		itemList = orderManager.listGoodsItems(orderId); // 订单商品列表
		delivery = orderNFlowManager.qryDeliverByOrderID(orderId);
		logiList  = logiManager.list();
		dlyTypeList =  dlyTypeManager.list();
		order = this.orderManager.get(orderId);
		giftList = orderNFlowManager.listNotShipGiftItem(orderId);
		hasGift= giftList==null||giftList.isEmpty()?false:true;
		return "showcustacceptdialog";
	}
	
	/**
	 * 显示发货确认窗口
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-12 
	 * @return
	 */
	public String showFHDialog(){
		logiList  = logiManager.list();
		Map map = new HashMap();
		map.put("id", "0");
		map.put("name", "--其它--");
		logiList.add(map);
		dlyTypeList =  dlyTypeManager.list();
		order = orderManager.get(orderId);
		//itemList = orderManager.listGoodsItems(orderId); // 订单商品列表
		itemList = this.orderNFlowManager.listNotShipGoodsItem(orderId);
		dealItemList();
		giftList = this.orderNFlowManager.listNotShipGiftItem(orderId);
		hasGift= giftList==null||giftList.isEmpty()?false:true;
		wareHouseList = warehouseServ.qrySupplierWareHouse();
		Warehouse wh = new Warehouse();
		wh.setHouse_id("");
		wh.setHouse_name("--请选择--");
		wareHouseList.add(0,wh);
		return "showFHDialog";
	}
	
	private void dealItemList() {
		if(!ListUtil.isEmpty(this.itemList)){
			for (int i = 0; i < this.itemList.size(); i++) {
				OrderItem orderItem =  (OrderItem)this.itemList.get(i);
				GoodsType goodsType = orderUtils.qryGoodsTypeByGoodsId(orderItem.getGoods_id());
				String stock =goodsType==null?null:goodsType.getHave_stock();
				if(StringUtil.isEmpty(stock))
					stock = Consts.HAVE_STOCK_0;
				orderItem.setHave_stock(stock);
				orderItem.setType_code(goodsType==null?null:goodsType.getType_code());
			}
		}
	}
	
	/**
	 * 显示收货确认窗口
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-12 
	 * @return
	 */
	public String showSHDialog(){
		//order = orderManager.get(orderId);
		itemList = orderManager.listGoodsItems(orderId); // 订单商品列表
		delivery = orderNFlowManager.qryDeliverByOrderID(orderId);
		logiList  = logiManager.list();
		dlyTypeList =  dlyTypeManager.list();
		order = this.orderManager.get(orderId);
		//itemList = orderNFlowManager.listNotShipGoodsItem(orderId);
		giftList = orderNFlowManager.listNotShipGiftItem(orderId);
		hasGift= giftList==null||giftList.isEmpty()?false:true;
		
		return "showSHDialog";
	}
	
	/**
	 * 显示完成订单确认窗口
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-12 
	 * @return
	 */
	public String showFinishDialog(){
		itemList = orderManager.listGoodsItems(orderId); // 订单商品列表
		delivery = orderNFlowManager.qryDeliverByOrderID(orderId);
		logiList  = logiManager.list();
		dlyTypeList =  dlyTypeManager.list();
		order = this.orderManager.get(orderId);
		giftList = orderNFlowManager.listNotShipGiftItem(orderId);
		hasGift= giftList==null||giftList.isEmpty()?false:true;
		return "showFinishDialog";
	}
	
	private String [] flow_group_id;
	private String [] flow_user_id;
	private int flag_status;
	private String flow_def_id;
	
	/**
	 * 处理备货
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-12 
	 * @return
	 */
	public String confirmBH(){
		try{
			orderDirector.getOrderBuilder().buildOrderWithBLFlow();
			OrderRequst orderRequst = new OrderRequst();
			orderRequst.setService_name(OrderBuilder.COMMON_KEY);
			orderRequst.setFlow_name(OrderBuilder.ORDER_BH);
			orderRequst.setAccept_action(OrderStatus.BUTTON_CUST_ACCEPT_C);
			OrderHandleParam param = new OrderHandleParam();
			param.setOrderId(orderId);
			param.setHint(hint);
			param.setFlow_user_id(flow_group_id);
			param.setFlow_group_id(flow_group_id);
			orderRequst.setOrderParam(param);
			OrderResult result = orderDirector.perform(orderRequst);
			this.json = "{result:'"+result.getCode()+"',message:'"+result.getMessage()+"'}";
		}catch(Exception ex){
			this.json = "{result:0,message:'"+ex.getMessage()+"'}";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(this.json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 处理发货
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-12 
	 * @return
	 */
	public String confirmFH(){
		try{
			orderDirector.getOrderBuilder().buildOrderWithFHFlow();
			OrderRequst orderRequst = new OrderRequst();
			orderRequst.setService_name(OrderBuilder.COMMON_KEY);
			orderRequst.setFlow_name(OrderBuilder.ORDER_FH);
			orderRequst.setAccept_action(OrderStatus.BUTTON_SHIPPING_C);
			orderRequst.setOrderId(orderId);
			OrderHandleParam param = new OrderHandleParam();
			param.setOrderId(orderId);
			param.setHint(hint);
			param.setFlow(flow);
			param.setFlow_user_id(flow_group_id);
			param.setFlow_group_id(flow_group_id);
			orderRequst.setOrderParam(param);
			Delivery dv = this.deliveryManager.getByOrderId(orderId);
			if(dv!=null){
				String [] logi = logi_id_name.split("\\|");
				dv.setLogi_id(logi[0]);
				dv.setLogi_name(logi[1]);
				dv.setLogi_no(delivery.getLogi_no());
				dv.setShip_name(delivery.getShip_name());
				dv.setShip_tel(delivery.getShip_tel());
				dv.setShip_mobile(delivery.getShip_mobile());
				dv.setShip_zip(delivery.getShip_zip());
				dv.setShip_addr(delivery.getShip_addr());
				dv.setRemark(delivery.getRemark());
				dv.setHouse_id(delivery.getHouse_id());
				shipRequest.setDelivery(dv);
				orderRequst.setShipRequest(shipRequest);
				OrderResult result = orderDirector.perform(orderRequst);
				this.json = "{result:'"+result.getCode()+"',message:'"+result.getMessage()+"'}";
			}else{
				this.json = "{result:0,message:'找不到发货单'}";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.json = "{result:0,message:'发货失败("+ex.getMessage()+")'}";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(this.json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 处理收货
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-12 
	 * @return
	 */
	public String confirmSH(){
		try{
			orderDirector.getOrderBuilder().buildOrderWithSHFlow();
			OrderRequst orderRequst = new OrderRequst();
			orderRequst.setService_name(OrderBuilder.COMMON_KEY);
			orderRequst.setFlow_name(OrderBuilder.ORDER_SH);
			orderRequst.setAccept_action(OrderStatus.BUTTON_GET_SHIPPING_C);
			OrderHandleParam param = new OrderHandleParam();
			param.setOrderId(orderId);
			param.setHint(hint);
			orderRequst.setOrderParam(param);
			
			OrderResult result = orderDirector.perform(orderRequst);
			this.json = "{result:'"+result.getCode()+"',message:'"+result.getMessage()+"'}";
		}catch(Exception ex){
			this.json = "{result:0,message:'"+ex.getMessage()+"'}";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(this.json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 完成订单
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-12 
	 * @return
	 */
	public String confirmFinish(){
		try{
			orderDirector.getOrderBuilder().buildOrderWithFinishFlow();
			OrderRequst orderRequst = new OrderRequst();
			orderRequst.setService_name(OrderBuilder.COMMON_KEY);
			orderRequst.setFlow_name(OrderBuilder.ORDER_FINISH);
			orderRequst.setAccept_action(OrderStatus.BUTTON_FINISHED_C);
			OrderHandleParam param = new OrderHandleParam();
			param.setOrderId(orderId);
			param.setHint(hint);
			orderRequst.setOrderParam(param);
			OrderResult result = orderDirector.perform(orderRequst);
			this.json = "{result:'"+result.getCode()+"',message:'"+result.getMessage()+"'}";
		}catch(Exception ex){
			this.json = "{result:0,message:'"+ex.getMessage()+"'}";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(this.json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List getItemList() {
		return itemList;
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public IOrderDirector getOrderDirector() {
		return orderDirector;
	}

	public void setOrderDirector(IOrderDirector orderDirector) {
		this.orderDirector = orderDirector;
	}

	public IOrderNFlowManager getOrderNFlowManager() {
		return orderNFlowManager;
	}

	public void setOrderNFlowManager(IOrderNFlowManager orderNFlowManager) {
		this.orderNFlowManager = orderNFlowManager;
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

	public IOrderUtils getOrderUtils() {
		return orderUtils;
	}

	public void setOrderUtils(IOrderUtils orderUtils) {
		this.orderUtils = orderUtils;
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

	public List getGiftList() {
		return giftList;
	}

	public void setGiftList(List giftList) {
		this.giftList = giftList;
	}

	public boolean isHasGift() {
		return hasGift;
	}

	public void setHasGift(boolean hasGift) {
		this.hasGift = hasGift;
	}

	public ShipRequest getShipRequest() {
		return shipRequest;
	}

	public void setShipRequest(ShipRequest shipRequest) {
		this.shipRequest = shipRequest;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public IDeliveryFlow getDeliveryFlowManager() {
		return deliveryFlowManager;
	}

	public void setDeliveryFlowManager(IDeliveryFlow deliveryFlowManager) {
		this.deliveryFlowManager = deliveryFlowManager;
	}

	public DeliveryFlow getFlow() {
		return flow;
	}

	public void setFlow(DeliveryFlow flow) {
		this.flow = flow;
	}

	public String getLogi_id_name() {
		return logi_id_name;
	}

	public void setLogi_id_name(String logi_id_name) {
		this.logi_id_name = logi_id_name;
	}

	public double getShipPrice() {
		return shipPrice;
	}

	public void setShipPrice(double shipPrice) {
		this.shipPrice = shipPrice;
	}

	public IDeliveryManager getDeliveryManager() {
		return deliveryManager;
	}

	public void setDeliveryManager(IDeliveryManager deliveryManager) {
		this.deliveryManager = deliveryManager;
	}

	public List<Warehouse> getWareHouseList() {
		return wareHouseList;
	}

	public void setWareHouseList(List<Warehouse> wareHouseList) {
		this.wareHouseList = wareHouseList;
	}

	public WarehouseInf getWarehouseServ() {
		return warehouseServ;
	}

	public void setWarehouseServ(WarehouseInf warehouseServ) {
		this.warehouseServ = warehouseServ;
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

	public int getFlag_status() {
		return flag_status;
	}

	public void setFlag_status(int flag_status) {
		this.flag_status = flag_status;
	}

	public String getFlow_def_id() {
		return flow_def_id;
	}

	public void setFlow_def_id(String flow_def_id) {
		this.flow_def_id = flow_def_id;
	}

}
