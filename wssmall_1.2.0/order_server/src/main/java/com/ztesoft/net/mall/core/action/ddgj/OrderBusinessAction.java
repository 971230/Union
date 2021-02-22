package com.ztesoft.net.mall.core.action.ddgj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import params.adminuser.req.AdminUserPageReq;
import params.adminuser.req.AdminUserReq;
import params.adminuser.resp.AdminUserPageResp;
import params.adminuser.resp.AdminUserResp;
import params.paycfg.req.PaymentCfgBankReq;
import params.paycfg.req.PaymentCfgListReq;
import params.paycfg.req.PaymentCfgReq;
import services.AdminUserInf;
import services.DeliveryInf;
import services.PaymentCfgInf;
import services.PaymentInf;
import services.WarehouseInf;
import zte.net.iservice.IOrderServices;
import zte.net.iservice.ISysService;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.consts.OrderFlowConst;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.orderc.OrderApply;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.DeliveryFlow;
import com.ztesoft.net.mall.core.model.GoodsActionRule;
import com.ztesoft.net.mall.core.model.GoodsType;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderApplyItem;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.model.ShipRequest;
import com.ztesoft.net.mall.core.model.Warehouse;
import com.ztesoft.net.mall.core.service.IDeliveryFlow;
import com.ztesoft.net.mall.core.service.IDeliveryManager;
import com.ztesoft.net.mall.core.service.IDlyTypeManager;
import com.ztesoft.net.mall.core.service.ILogiManager;
import com.ztesoft.net.mall.core.service.IOrderApplyManage;
import com.ztesoft.net.mall.core.service.IOrderApplycMamager;
import com.ztesoft.net.mall.core.service.IOrderCommentManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderNFlowManager;
import com.ztesoft.net.mall.core.service.IOrderUtils;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.service.IOrderFlowBussManager;
import com.ztesoft.net.mall.service.IOrderGroupManager;
import com.ztesoft.net.model.OrderFlow;
import com.ztesoft.net.model.OrderFlowDef;
import com.ztesoft.net.model.OrderGroup;
import com.ztesoft.net.model.OrderReturnVisit;
import com.ztesoft.net.model.OrderReturnVisitType;
import com.ztesoft.net.model.OrderToDoList;

/**
 * 订单业务处理
 * @作者 MoChunrun
 * @创建日期 2014-5-23 
 * @版本 V 1.0
 */
public class OrderBusinessAction extends WWAction {

	
	private IOrderManager orderManager;
	private IOrderNFlowManager orderNFlowManager;
	private ILogiManager logiManager;
	private IDlyTypeManager dlyTypeManager;
	private IOrderGroupManager orderGroupManagerImpl;
	private AdminUserInf adminUserServ;
	private WarehouseInf warehouseServ;
	private IOrderUtils orderUtils;
	private ISysService sysService;
	private IOrderServices orderServices;
	
	private String order_id;
	private List<OrderGroup> orderGroupList;
	private String hint;
	private String service_type;
	
	private IOrderCommentManager orderCommentManager;
	private List orderExceptionList;
	private String query_type = "order";
	private OrderApply orderApply;
	private String apply_id;
	private List<OrderApplyItem> applyItemList;
	private boolean hasOrderApply = false;
	private boolean hasOrderItems = false;
	private IOrderApplyManage orderApplyManage;
	private AdminUser user;
	
	private OrderReturnVisit visit;
	private List<OrderReturnVisit> visitList;
	private List<OrderReturnVisitType> visitTypeList;
	private List<OrderReturnVisitType> visitMethodList;
	private OrderFlow orderFlow;
	private String action;
	private int monitor = 0;
	
	public String showAddReturnVisit(){
		visitTypeList = orderFlowBussManager.listOrderReturVisitType(0);
		visitMethodList = orderFlowBussManager.listOrderReturVisitType(1);
		return "add_return_visit";
	}
	
	public String saveOrderReturnVisit(){
		try{
			AdminUser user = ManagerUtils.getAdminUser();
			visit.setUser_id(user.getUserid());
			visit.setUser_name(user.getUsername());
			visit.setCreate_time("sysdate");
			orderFlowBussManager.addOrderReturnVisit(visit);
			json = "{status:0,msg:'添加成功'}";
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{status:1,msg:'添加失败'}";
		}
		return JSON_MESSAGE;
	}
	
	public String listOrderReturnVisit(){
		visitList = orderFlowBussManager.listOrderReturnVisit(order_id, todo_id);
		if("right".equals(action)){
			return "right_return_visit";
		}
		return "order_visit";
	}
	
	public void getConfirmAdmin(String user_id){
		if(!StringUtil.isEmpty(user_id)){
			try{
				AdminUserReq req = new AdminUserReq();
				req.setUser_id(user_id);
				AdminUserResp resp = adminUserServ.getAdminUserById(req);
				user = resp.getAdminUser();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	public String queryOrderInfo(){
		AdminUser user = ManagerUtils.getAdminUser();
		order = orderManager.get(order_id);
		itemList = orderManager.listGoodsItems(order_id);
		hasOrderApply = orderApplyManage.orderIsOnApply(order_id);
		hasOrderItems = orderManager.countHasShipOrderItems(order_id)>0;
		List<OrderToDoList> list = orderFlowBussManager.getOrderTodoListByFowStatus(order_id, 0);
		if(list!=null && list.size()>0){
			toDo = list.get(0);
			if(user.getUserid().equals(toDo.getFlow_user_id()))toDo.setCurrUserToDo(true);
		}
		//toDo = orderFlowBussManager.queryUserOrderToDoList(order_id, user.getUserid(),0);
		if(toDo!=null){
			orderFlow = orderFlowBussManager.getOrderFlowByDefId(toDo.getFlow_def_id());
		}
		if("apply".equals(query_type)){
			orderApply = orderApplycMamager.qryByAppliId(apply_id);
			applyItemList = orderApplycMamager.queryApplyItems(apply_id, null);
		}else{
			if(OrderStatus.ORDER_EXCEPTION_99==order.getStatus())
				orderExceptionList = orderCommentManager.listUnComments(order_id);
		}
		return "order_info";
	}
	
	/**
	 * 显示取消订单窗口
	 * @作者 MoChunrun
	 * @创建日期 2014-5-26 
	 * @return
	 */
	public String showCancel(){
		return "order_cancel";
	}
	
	private List itemList;
	private Delivery delivery;
	private List logiList;
	private List dlyTypeList;
	private List giftList;
	private Order order;
	private boolean hasGift; //是否有赠品货物
	private AdminUser adminUser;
	
	private String user_name;
	private String group_id;
	
	private OrderFlow flow;
	private OrderFlowDef flowDef;
	private List<OrderFlowDef> flowDefList;
	private String flow_def_id;
	private String todo_id;
	private OrderToDoList toDo;
	private int flag_status;
	private String [] flow_group_id;
	private String [] flow_user_id;
	
	private IOrderFlowBussManager orderFlowBussManager;
	
	/**
	 * 抢单
	 * @作者 MoChunrun
	 * @创建日期 2014-7-2 
	 * @return
	 */
	public String robOrder(){
		try{
			json = orderFlowBussManager.robOrder(order_id);
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{result:0,message:'"+ex.getMessage()+"'}";
		}
		return JSON_MESSAGE;
	}
	
	//释放订单
	public String ribOrder(){
		user = ManagerUtils.getAdminUser();
		//OrderToDoList perv_todo = orderFlowBussManager.queryUserOrderToDoList(order_id, user.getUserid());
		int i = orderFlowBussManager.createOrderTodo(order_id, null, null, "解锁订单", 1, null, null, "rib");
		if(i==OrderFlowConst.CAN_NOT_RIB){
			json="{result:0,message:'不能撤消'}";
		}else if(0==i){
			json = "{result:1,message:'成功'}";
		}else{
			json = "{result:0,message:'撤消失败'}";
		}
		return JSON_MESSAGE;
	}
	
	public String showConfirmDialog(){
		itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
		delivery = orderNFlowManager.qryDeliverByOrderID(order_id);
		logiList  = logiManager.list();
		dlyTypeList =  dlyTypeManager.list();
		order = this.orderManager.get(order_id);
		getConfirmAdmin(order.getPay_user_id());
		orderGroupList = orderGroupManagerImpl.listGroupByGroupType(null);
		queryFlow(null);
		if(flowDefList!=null && flowDefList.size()>0){
			List<GoodsActionRule> rules = orderNFlowManager.listGoodsActionRule(order.getOrder_id(),order.getService_code());
			for(GoodsActionRule r:rules){
				if(OrderFlowConst.FLOW_DEF_TYPE_PAY.equalsIgnoreCase(r.getAction_code()) && "no".equals(r.getStatus())){
					for(OrderFlowDef f:flowDefList){
						if(f.getFlow_type().equalsIgnoreCase(r.getAction_code())){
							flowDefList.remove(f);
							break ;
						}
					}
				}else if(OrderFlowConst.FLOW_DEF_TYPE_DELIVERY.equalsIgnoreCase(r.getAction_code()) && "no".equalsIgnoreCase(r.getStatus())){
					for(OrderFlowDef f:flowDefList){
						if(f.getFlow_type().equalsIgnoreCase(r.getAction_code())){
							flowDefList.remove(f);
						}
						if(OrderFlowConst.FLOW_DEF_TYPE_PACKAGE.equalsIgnoreCase(f.getFlow_type())){
							flowDefList.remove(f);
						}
					}
				}
			}
		}
		return "order_confirm";
	}
	
	public String qyOrder(){
		json = orderFlowBussManager.qyOrder(order_id, flow_user_id, flow_group_id, flow_def_id, flag_status, hint);
		return JSON_MESSAGE;
	}
	
	public String queryAdminUser(){
		AdminUserPageReq req = new AdminUserPageReq();
		req.setUsername(user_name);
		req.setPageNo(this.getPage()+"");
		req.setPageZise(this.getPageSize()+"");
		AdminUserPageResp resp = sysService.qryAdminUser(req);
		webpage = resp.getWebPage();
		return "select_admin";
	}
	
	public String queryGroupAdminUser(){
		webpage = orderGroupManagerImpl.queryGroupUsers(group_id, user_name, this.getPage(), this.getPageSize());
		return "select_group_admin";
	}
	
	/**
	 * 显示开户
	 * @作者 MoChunrun
	 * @创建日期 2014-6-12 
	 * @return
	 */
	public String showOpenAccountDialog(){
		itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
		delivery = orderNFlowManager.qryDeliverByOrderID(order_id);
		logiList  = logiManager.list();
		dlyTypeList =  dlyTypeManager.list();
		order = this.orderManager.get(order_id);
		getConfirmAdmin(order.getShip_user_id());
		orderGroupList = orderGroupManagerImpl.listGroupByGroupType(null);
		queryFlow(null);
		return "order_openaccount";
	}
	
	public String openAccount(){
		/*AdminUser user = ManagerUtils.getAdminUser();
		order = this.orderManager.get(order_id);
		itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
		flowDef = orderFlowBussManager.getOrderFlowDef(flow_def_id);
		Map map = (Map) itemList.get(0);
		String goods_id = (String) map.get("goods_id");
		toDo = orderFlowBussManager.queryUserOrderToDoList(order_id, user.getUserid());
		int next_status = orderFlowBussManager.next(order_id, flow_user_id, flow_group_id, flow_def_id, flag_status, hint, toDo,OrderFlowConst.FLOW_SERVICE_TYPE_BUY);
		if(next_status==0 && flag_status==1){
			int accept_status = 1;
			if(flag_status!=1)accept_status=2;
			int status = OrderFlowConst.getOrderStatus(flowDef.getFlow_type());
			if(status!=-9999 && status!=order.getStatus()){
				OrderStatusEditReq req = new OrderStatusEditReq();
				req.setOrder_id(order_id);
				req.setOrder_status(status+"");
				orderServices.editOrderStatus(req);
			}
			orderManager.updateOrderAcceptStatus(accept_status, order_id);
		}
		json = "{result:1,message:'成功'}";*/
		
		try{
			json = orderFlowBussManager.openAccount(order_id, flow_def_id, flow_user_id, flow_group_id, flag_status, hint);
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{result:0,message:'"+ex.getMessage()+"'}";
		}
		return JSON_MESSAGE;
	}
	
	public String showDispatchDialog(){
		itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
		delivery = orderNFlowManager.qryDeliverByOrderID(order_id);
		logiList  = logiManager.list();
		dlyTypeList =  dlyTypeManager.list();
		order = this.orderManager.get(order_id);
		getConfirmAdmin(order.getConfirm_user_id());
		orderGroupList = orderGroupManagerImpl.listGroupByGroupType(null);
		queryFlow(OrderFlowConst.ORDER_TODO_LIST_DISPATCH);
		return "order_dispatch";
	}
	
	private OrderGroup orderGroup;
	
	private void queryFlow(String flow_type){
		if(order!=null){
			AdminUser user = ManagerUtils.getAdminUser();
			toDo = orderFlowBussManager.queryUserOrderToDoList(order_id, user.getUserid(),1);
			if(toDo!=null && !"-1".equals(toDo.getFlow_def_id())){
				if(!StringUtil.isEmpty(flow_type) && OrderFlowConst.ORDER_TODO_LIST_DISPATCH.equals(flow_type)){
					flowDef = orderFlowBussManager.getOrderFlowDef(toDo.getFlow_def_id());
				}else{
					flowDef = orderFlowBussManager.getNextOrderFlowDef(toDo.getFlow_def_id());
				}
			}
			if(flowDef==null){
				flowDef = new OrderFlowDef();
				flowDef.setFlow_type(OrderFlowConst.getFlowDefType(order.getStatus(),service_type));
				if(itemList!=null && itemList.size()>0){
					Object obj = itemList.get(0);
					String goodsid = null;
					if(obj instanceof OrderItem){
						OrderItem oi = (OrderItem) obj;
						goodsid = oi.getGoods_id();
					}else{
						Map map = (Map) itemList.get(0);
						goodsid = (String) map.get("goods_id");
					}
					if(StringUtil.isEmpty(service_type))service_type=OrderFlowConst.FLOW_SERVICE_TYPE_BUY;
					flow = orderFlowBussManager.getOrderFlowByGoods(goodsid,service_type);
					flowDefList = orderFlowBussManager.listOrderFlowDef(flow.getFlow_id(),false,order_id);
				}
			}else{
				flow = orderFlowBussManager.getOrderFlowByDefId(flowDef.getFlow_def_id());
				flowDefList = orderFlowBussManager.listOrderFlowDef(flowDef.getFlow_id(),false,order_id);
			}
			orderGroup = orderFlowBussManager.getFlowUserGroup(flowDef.getFlow_type());
		}
	}
	
	public String paySuccess(){
		/*AdminUser user = ManagerUtils.getAdminUser();
		order = orderManager.get(order_id);
		itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
		Map map = (Map) itemList.get(0);
		String goods_id = (String) map.get("goods_id");
		toDo = orderFlowBussManager.queryUserOrderToDoList(order_id, user.getUserid());
		int next_status = orderFlowBussManager.createOrderToDo(order_id, order.getStatus(), goods_id, flow_user_id, flow_group_id, flow_def_id,hint,toDo,flag_status,OrderFlowConst.FLOW_SERVICE_TYPE_BUY, "next");
		flowDef = orderFlowBussManager.getOrderFlowDef(flow_def_id);
		int status = OrderFlowConst.getOrderStatus(flowDef.getFlow_type());
		if(status!=-9999 && status!=order.getStatus()){
			OrderStatusEditReq req = new OrderStatusEditReq();
			req.setOrder_id(order_id);
			req.setOrder_status(status+"");
			orderServices.editOrderStatus(req);
		}
		json = "{result:1,message:'成功'}";*/
		try{
			json = orderFlowBussManager.paySuccess(order_id, flow_def_id, flow_user_id, flow_group_id, flag_status, hint);
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{result:0,message:'"+ex.getMessage()+"'}";
		}
		return JSON_MESSAGE;
	}
	/**
	 * 确认分派
	 * @作者 MoChunrun
	 * @创建日期 2014-6-11 
	 * @return
	 */
	public String dispatch(){
		/*AdminUser user = ManagerUtils.getAdminUser();
		order = orderManager.get(order_id);
		itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
		flowDef = orderFlowBussManager.getOrderFlowDef(flow_def_id);
		Map map = (Map) itemList.get(0);
		String goods_id = (String) map.get("goods_id");
		toDo = orderFlowBussManager.queryUserOrderToDoList(order_id, user.getUserid());
		int next_status = orderFlowBussManager.createOrderToDo(order_id, order.getStatus(), goods_id, flow_user_id, flow_group_id, flow_def_id,hint,toDo,flag_status,OrderFlowConst.FLOW_SERVICE_TYPE_BUY, "next");
		if(next_status==0 && flag_status==1){
			int status = OrderFlowConst.getOrderStatus(flowDef.getFlow_type());
			if(status!=-9999 && status!=order.getStatus()){
				OrderStatusEditReq req = new OrderStatusEditReq();
				req.setOrder_id(order_id);
				req.setOrder_status(status+"");
				orderServices.editOrderStatus(req);
			}
		}
		json = "{result:1,message:'成功'}";*/
		try{
			json = orderFlowBussManager.dispatch(order_id, flow_def_id, flow_user_id, flow_group_id, flag_status, hint);
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{result:0,message:'"+ex.getMessage()+"'}";
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 显示备货窗口
	 * @作者 MoChunrun
	 * @创建日期 2014-5-26 
	 * @return
	 */
	public String showBHDialog(){
		itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
		delivery = orderNFlowManager.qryDeliverByOrderID(order_id);
		logiList  = logiManager.list();
		dlyTypeList =  dlyTypeManager.list();
		order = this.orderManager.get(order_id);
		getConfirmAdmin(order.getShip_user_id());
		orderGroupList = orderGroupManagerImpl.listGroupByGroupType(null);
		queryFlow(null);
		//giftList = orderNFlowManager.listNotShipGiftItem(order_id);
		//hasGift= giftList==null||giftList.isEmpty()?false:true;
		/*orderGroupList = orderGroupManagerImpl.listGroupByGroupType(OrderStatus.ACTION_CODE_DELIVERY);
		OrderGroup og = new OrderGroup();
		og.setGroup_id("-1");
		og.setGroup_name("--请选择--");
		orderGroupList.add(0,og);
		if(!StringUtils.isEmpty(order.getShip_user_id())){
			AdminUserReq req = new AdminUserReq();
			req.setUser_id(order.getShip_user_id());
			AdminUserResp resp = adminUserServ.getAdminUserById(req);
			adminUser = resp.getAdminUser();
		}*/
		return "order_bh";
	}
	
	/**
	 * 订单备货
	 * @作者 MoChunrun
	 * @创建日期 2014-6-12 
	 * @return
	 */
	public String stokingOrder(){
		/*AdminUser user = ManagerUtils.getAdminUser();
		order = orderManager.get(order_id);
		itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
		flowDef = orderFlowBussManager.getOrderFlowDef(flow_def_id);
		Map map = (Map) itemList.get(0);
		String goods_id = (String) map.get("goods_id");
		toDo = orderFlowBussManager.queryUserOrderToDoList(order_id, user.getUserid());
		int next_status = orderFlowBussManager.createOrderToDo(order_id, order.getStatus(), goods_id, flow_user_id, flow_group_id, flow_def_id,hint,toDo,flag_status,OrderFlowConst.FLOW_SERVICE_TYPE_BUY, "next");
		if(next_status==0 && flag_status==1){
			int status = OrderFlowConst.getOrderStatus(flowDef.getFlow_type());
			//订单状态
			if(status!=-9999 && status!=order.getStatus()){
				
				OrderStockingReq sreq = new OrderStockingReq();
				sreq.setConfirm_userid(user.getUserid());
				sreq.setConfirm_username(user.getUsername());
				sreq.setOrder_id(order_id);
				OrderStockingResp resp = orderServices.stokingOrder(sreq);
				if(!"0".equals(resp.getError_code())){
					json = "{result:0,message:'"+resp.getError_msg()+"'}";
				}
				OrderStatusEditReq req = new OrderStatusEditReq();
				req.setOrder_id(order_id);
				req.setOrder_status(status+"");
				orderServices.editOrderStatus(req);
			}
		}
		OrderPackageRuleReq req = new OrderPackageRuleReq();
		req.setOrder(order);
		try {
			RuleInvoker.afOrderPackage(req);
		} catch (ApiBusiException e) {
			e.printStackTrace();
		}
		json = "{result:1,message:'成功'}";*/
		try{
			json = orderFlowBussManager.stokingOrder(order_id, flow_def_id, flow_user_id, flow_group_id, flag_status, hint);
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{result:0,message:'"+ex.getMessage()+"'}";
		}
		return JSON_MESSAGE;
	}
	
	private ShipRequest shipRequest;
	private DeliveryInf deliveryServ;
	private DeliveryFlow deliveryFlow;
	private IDeliveryManager deliveryManager;
	private String logi_id_name;
	private IDeliveryFlow deliveryFlowManager;
	
	public String shipOrder(){
		try{
			/*AdminUser user = ManagerUtils.getAdminUser();
			itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
			Map map = (Map) itemList.get(0);
			String goodsid = (String) map.get("goods_id");
			toDo = orderFlowBussManager.queryUserOrderToDoList(order_id, user.getUserid());
			//int next_status = orderFlowBussManager.createOrderToDo(order_id, order.getStatus(), goodsid, flow_user_id, flow_group_id, flow_def_id,hint,toDo,flag_status, "next");
			int next_status = orderFlowBussManager.next(order_id, flow_user_id, flow_group_id, flow_def_id, flag_status, hint, toDo,OrderFlowConst.FLOW_SERVICE_TYPE_BUY);
			if(next_status!=0 || flag_status!=1){
				this.json = "{result:1,message:'成功'}";
				return JSON_MESSAGE;
			}
			Delivery dv = deliveryManager.getByOrderId(order_id);
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
			}else{
				this.json = "{result:0,message:'找不到发货单'}";
				return JSON_MESSAGE;
			}
			
			order = orderManager.get(order_id);
			String goods_idArray [] =shipRequest.getGoods_idArray();
			String goods_nameArray [] =shipRequest.getGoods_nameArray();
			String goods_snArray [] =shipRequest.getGoods_snArray();
			String product_idArray [] =shipRequest.getProduct_idArray();
			Integer numArray [] =shipRequest.getNumArray();
			String giftidArray []= shipRequest.getGiftidArray();
			String giftnameArray []= shipRequest.getGiftnameArray();
			Integer giftnumArray []= shipRequest.getGiftnumArray();
			
			
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
			req.setDelivery_id(dv.getDelivery_id());
			req.setDeliveryItems(itemList);
			req.setGiftitemList(giftitemList);
			req.setDelivery(dv);
			req.setShippingType("1");
			req.setHouse_id(dv.getHouse_id());
			//req.setNext_deal_group_id(flow_group_id);
			//req.setNext_deal_user_id(flow_user_id);
			ConfirmShippingResp resp = deliveryServ.confirmShipping(req);
			if("2".equals(resp.getError_code())){
				json = "{result:0,message:'"+resp.getError_msg()+"'}";
			}else{
				if("0".equals(dv.getLogi_id())){
					//写物流流程表
					String delivery_id = dv.getDelivery_id(); 
					if(flow!=null){
						deliveryFlow.setCreate_time(DBTUtil.current());
						deliveryFlow.setOp_id(user.getUserid());
						deliveryFlow.setOp_name(user.getUsername());
						deliveryFlow.setDelivery_id(delivery_id);
						deliveryFlowManager.addDeliveryFlow(deliveryFlow);
					}
				}
				json = "{result:1,message:'成功'}";
			}
			flowDef = orderFlowBussManager.getOrderFlowDef(flow_def_id);
			int status = OrderFlowConst.getOrderStatus(flowDef.getFlow_type());
			if(status!=-9999 && status!=order.getStatus()){
				OrderStatusEditReq sreq = new OrderStatusEditReq();
				sreq.setOrder_id(order_id);
				sreq.setOrder_status(status+"");
				orderServices.editOrderStatus(sreq);
			}
			OrderShippingRuleReq sreq = new OrderShippingRuleReq();
			sreq.setOrder(order);
			RuleInvoker.afOrderShipping(sreq);*/
			json = orderFlowBussManager.shipOrder(order_id, flow_def_id, flow_user_id, flow_group_id, flag_status, hint, delivery, logi_id_name, shipRequest, flow);
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{result:0,message:'发货失败["+ex.getMessage()+"]'}";
		}
		return JSON_MESSAGE;
	}
	
	public String received(){
		/*AdminUser user = ManagerUtils.getAdminUser();
		itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
		Map map = (Map) itemList.get(0);
		String goodsid = (String) map.get("goods_id");
		toDo = orderFlowBussManager.queryUserOrderToDoList(order_id, user.getUserid());
		int next_status = orderFlowBussManager.next(order_id, flow_user_id, flow_group_id, flow_def_id, flag_status, hint, toDo,OrderFlowConst.FLOW_SERVICE_TYPE_BUY);
		if(next_status!=0 || flag_status!=1){
			this.json = "{result:1,message:'成功'}";
			return JSON_MESSAGE;
		}
		OrderConfirmReq req = new OrderConfirmReq();
		req.setOrder_id(order_id);
		req.setConfirm_userid(user.getUserid());
		req.setConfirm_username(user.getUsername());
		OrderConfirmResp resp = orderServices.confirmOrder(req);
		flowDef = orderFlowBussManager.getOrderFlowDef(flow_def_id);
		int status = OrderFlowConst.getOrderStatus(flowDef.getFlow_type());
		order = orderManager.get(order_id);
		if(status!=-9999 && status!=order.getStatus()){
			OrderStatusEditReq sreq = new OrderStatusEditReq();
			sreq.setOrder_id(order_id);
			sreq.setOrder_status(status+"");
			orderServices.editOrderStatus(sreq);
		}
		this.json = "{result:1,message:'成功'}";*/
		try{
			json = orderFlowBussManager.received(order_id, flow_def_id, flow_user_id, flow_group_id, flag_status, hint);
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{result:0,message:'"+ex.getMessage()+"'}";
		}
		return JSON_MESSAGE;
	}
	
	public String finish(){
		/*AdminUser user = ManagerUtils.getAdminUser();
		itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
		Map map = (Map) itemList.get(0);
		String goodsid = (String) map.get("goods_id");
		toDo = orderFlowBussManager.queryUserOrderToDoList(order_id, user.getUserid());
		int next_status = orderFlowBussManager.next(order_id, null, null, flow_def_id, flag_status, hint, toDo,OrderFlowConst.FLOW_SERVICE_TYPE_BUY);
		if(next_status!=0 || flag_status!=1){
			this.json = "{result:1,message:'成功'}";
			return JSON_MESSAGE;
		}
		//已支付可备货
		orderManager.updateOrderStatus(OrderStatus.ORDER_COMPLETE, order_id);
		//===写日志===
		orderManager.log(order_id, hint, user.getUserid(), user.getUsername());
		order = orderManager.get(order_id);
		OrderFinishRuleReq req = new OrderFinishRuleReq();
		req.setOrder(order);
		try {
			RuleInvoker.afOrderFinish(req);
		} catch (ApiBusiException e) {
			e.printStackTrace();
		}
		this.json = "{result:1,message:'成功'}";*/
		try{
			json = orderFlowBussManager.finish(order_id, flow_def_id, flow_user_id, flow_group_id, flag_status, hint);
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{result:0,message:'"+ex.getMessage()+"'}";
		}
		return JSON_MESSAGE;
	}
	
	private List<Warehouse> wareHouseList;
	/**
	 * 显示发货窗口
	 * @作者 MoChunrun
	 * @创建日期 2014-5-26 
	 * @return
	 */
	public String showShippingDialog(){
		logiList  = logiManager.list();
		Map map = new HashMap();
		map.put("id", "0");
		map.put("name", "--其它--");
		logiList.add(map);
		dlyTypeList =  dlyTypeManager.list();
		order = orderManager.get(order_id);
		//itemList = orderManager.listGoodsItems(orderId); // 订单商品列表
		itemList = this.orderNFlowManager.listNotShipGoodsItem(order_id);
		dealItemList();
		giftList = this.orderNFlowManager.listNotShipGiftItem(order_id);
		hasGift= giftList==null||giftList.isEmpty()?false:true;
		wareHouseList = warehouseServ.qrySupplierWareHouse();
		Warehouse wh = new Warehouse();
		wh.setHouse_id("");
		wh.setHouse_name("--请选择--");
		wareHouseList.add(0,wh);
		orderGroupList = orderGroupManagerImpl.listGroupByGroupType(null);
		queryFlow(null);
		return "order_shipping";
	}
	
	/**
	 * 显示确认收货窗口
	 * @作者 MoChunrun
	 * @创建日期 2014-5-27 
	 * @return
	 */
	public String showOrderReceive(){
		itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
		delivery = orderNFlowManager.qryDeliverByOrderID(order_id);
		logiList  = logiManager.list();
		dlyTypeList =  dlyTypeManager.list();
		order = this.orderManager.get(order_id);
		//giftList = orderNFlowManager.listNotShipGiftItem(order_id);
		//hasGift= giftList==null||giftList.isEmpty()?false:true;
		orderGroupList = orderGroupManagerImpl.listGroupByGroupType(null);
		queryFlow(null);
		return "order_receive";
	}
	
	/**
	 * 显示完成订单窗口
	 * @作者 MoChunrun
	 * @创建日期 2014-5-27 
	 * @return
	 */
	public String showFinish(){
		itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
		delivery = orderNFlowManager.qryDeliverByOrderID(order_id);
		logiList  = logiManager.list();
		dlyTypeList =  dlyTypeManager.list();
		order = this.orderManager.get(order_id);
		//giftList = orderNFlowManager.listNotShipGiftItem(order_id);
		//hasGift= giftList==null||giftList.isEmpty()?false:true;
		//orderGroupList = orderGroupManagerImpl.listGroupByGroupType(null);
		//queryFlow();
		return "order_finish";
	}
	
	List<OrderItem> shipGoodsList;
	private IOrderApplycMamager orderApplycMamager;
	
	/**
	 * 显示退货对话框
	 * @作者 MoChunrun
	 * @创建日期 2014-5-27 
	 * @return
	 */
	public String showReturnDialog(){
		this.fillShipData();
		//读取已发货明细
		order = orderManager.get(order_id);
		getConfirmAdmin(order.getPay_user_id());
		shipGoodsList = this.orderNFlowManager.listShipGoodsItem(order_id);		//商品已发货明细
		List<OrderApply> list = orderApplycMamager.queryApplyByOrderId(order_id,OrderStatus.ORDER_TYPE_4, OrderStatus.ORDER_APPLY_STATUS_1);
		for(OrderItem o:shipGoodsList){
			for(OrderApply a:list){
				if(o.getItem_id().equals(a.getItemId())){
					o.setBack_num(a.getReturned_num());
					o.setOrder_apply_id(a.getOrder_apply_id());
				}
			}
		}
		orderGroupList = orderGroupManagerImpl.listGroupByGroupType(null);
		queryFlow(null);
		if(shipGoodsList!=null && shipGoodsList.size()>0){
			OrderItem obj = shipGoodsList.get(0);
			String goodsid = obj.getGoods_id();
			flow = orderFlowBussManager.getOrderFlowByGoods(goodsid,OrderFlowConst.FLOW_SERVICE_TYPE_RETURNED);
			flowDefList = orderFlowBussManager.listOrderFlowDef(flow.getFlow_id(),true,order_id);
		}
		orderGroup = orderFlowBussManager.getFlowUserGroup(OrderFlowConst.FLOW_DEF_TYPE_REFUND);
		return "return_dialog";
	}
	
	private double refundAmount=0;
	private List<OrderApply> applylist;
	private double depreciation_price = 0;
	private double ship_price = 0;
	private double returned_price = 0;
	private String payment_cfg_id;
	private List paymentList;
	private PaymentCfgInf paymentCfgServ;
	
	/**
	 * 显示退费窗口
	 * @作者 MoChunrun
	 * @创建日期 2014-5-27 
	 * @return
	 */
	public String showRefund(){
		this.order = this.orderManager.get(order_id);
		applylist = orderApplycMamager.queryApplyByOrderId(order_id,OrderStatus.ORDER_TYPE_2, OrderStatus.ORDER_APPLY_STATUS_1);
		for(OrderApply oa:applylist){
			refundAmount +=oa.getPay_price();
			depreciation_price += oa.getDepreciation_price();
			ship_price += oa.getShip_price();	
			returned_price += Double.parseDouble(oa.getRefund_value());
		}
		if(!StringUtil.isEmpty(payment_cfg_id)){
			paymentList = new ArrayList();
			PaymentCfgReq req = new PaymentCfgReq();
			req.setPayment_cfg_id(payment_cfg_id);
			paymentList.add(paymentCfgServ.queryPaymentCfgById(req).getPaymentCfg());
			//this.paymentList  = this.paymentManager.listById(payment_cfg_id);
		}else{
			PaymentCfgListReq req = new PaymentCfgListReq();
			this.paymentList  = paymentCfgServ.queryPaymentCfgList(req).getPayCfgList();
		}
		return "refund_dialog";
	}
	
	private List<OrderApply> orderApplyItemList;
	private List<OrderItem> orderItemList;
	
	/**
	 * 显示换货窗口
	 * @作者 MoChunrun
	 * @创建日期 2014-5-27 
	 * @return
	 */
	public String showChange(){
		this.fillShipData();
		order = orderManager.get(order_id);
		getConfirmAdmin(order.getPay_user_id());
		//读取已发货明细
		orderItemList = this.orderNFlowManager.listShipGoodsItem(order_id);		//商品已发货明细
		orderApplyItemList = orderApplycMamager.queryApplyByOrderId(order_id,OrderStatus.ORDER_TYPE_3, OrderStatus.ORDER_APPLY_STATUS_1);
		for(OrderApply oa:orderApplyItemList){
			if(OrderStatus.ORDER_TYPE_4.equals(oa.getItem_type())){
				refundAmount +=oa.getPay_price();
				depreciation_price += oa.getDepreciation_price();
				ship_price += oa.getShip_price();	
				returned_price += Double.parseDouble(oa.getRefund_value());
			}
		}
		
		for(OrderItem o:orderItemList){
			for(OrderApply a:orderApplyItemList){
				if(o.getItem_id().equals(a.getItemId())){
					o.setBack_num(a.getReturned_num());
					o.setOrder_apply_id(a.getOrder_apply_id());
				}
			}
		}
		orderGroupList = orderGroupManagerImpl.listGroupByGroupType(null);
		queryFlow(null);
		if(orderItemList!=null && orderItemList.size()>0){
			OrderItem obj = orderItemList.get(0);
			String goodsid = obj.getGoods_id();
			flow = orderFlowBussManager.getOrderFlowByGoods(goodsid,OrderFlowConst.FLOW_SERVICE_TYPE_EXCHANGE);
			flowDefList = orderFlowBussManager.listOrderFlowDef(flow.getFlow_id(),true,order_id);
		}
		orderGroup = orderFlowBussManager.getFlowUserGroup(OrderFlowConst.FLOW_DEF_TYPE_REFUND);
		return "change_dialog";
	}
	
	private List bankList;
	private PaymentInf paymentServ;
	private PayCfg payCfg;
	
	/**
	 * 显示支付窗口
	 * @作者 MoChunrun
	 * @创建日期 2014-5-28 
	 * @return
	 */
	public String showOrderPay(){
		this.order = this.orderManager.get(order_id);
		getConfirmAdmin(order.getShip_user_id());
		//if(user.getUserid().equals(order.getPay_user_id()) || orderManager.qryGroupByOrder(user.getUserid(), order.getPay_group_id()).size()>0){
		PaymentCfgReq reqid = new PaymentCfgReq();
		reqid.setPayment_cfg_id(payment_cfg_id);
		payCfg =paymentCfgServ.queryPaymentCfgById(reqid).getPaymentCfg();
		PaymentCfgBankReq req = new PaymentCfgBankReq();
		req.setPayment_cfg_id(payment_cfg_id);
		bankList = paymentCfgServ.queryCfgBankList(req).getBankList();
		itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
		orderGroupList = orderGroupManagerImpl.listGroupByGroupType(null);
		queryFlow(null);
		//}else{
		//	return "no_security_pay";
		//}
		return "order_pay";
	}
	
	private void fillShipData(){
		logiList  = logiManager.list();
		Map map = new HashMap();
		map.put("id", "0");
		map.put("name", "--其它--");
		logiList.add(map);
		dlyTypeList =  dlyTypeManager.list();
		order = this.orderManager.get(order_id);
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
	

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
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


	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}

	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}

	public WarehouseInf getWarehouseServ() {
		return warehouseServ;
	}

	public void setWarehouseServ(WarehouseInf warehouseServ) {
		this.warehouseServ = warehouseServ;
	}

	public IOrderUtils getOrderUtils() {
		return orderUtils;
	}

	public void setOrderUtils(IOrderUtils orderUtils) {
		this.orderUtils = orderUtils;
	}

	public List<OrderGroup> getOrderGroupList() {
		return orderGroupList;
	}

	public void setOrderGroupList(List<OrderGroup> orderGroupList) {
		this.orderGroupList = orderGroupList;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public boolean isHasGift() {
		return hasGift;
	}

	public void setHasGift(boolean hasGift) {
		this.hasGift = hasGift;
	}

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public List<Warehouse> getWareHouseList() {
		return wareHouseList;
	}

	public void setWareHouseList(List<Warehouse> wareHouseList) {
		this.wareHouseList = wareHouseList;
	}

	public IOrderGroupManager getOrderGroupManagerImpl() {
		return orderGroupManagerImpl;
	}

	public void setOrderGroupManagerImpl(IOrderGroupManager orderGroupManagerImpl) {
		this.orderGroupManagerImpl = orderGroupManagerImpl;
	}

	public ISysService getSysService() {
		return sysService;
	}

	public void setSysService(ISysService sysService) {
		this.sysService = sysService;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public List<OrderItem> getShipGoodsList() {
		return shipGoodsList;
	}

	public void setShipGoodsList(List<OrderItem> shipGoodsList) {
		this.shipGoodsList = shipGoodsList;
	}

	public IOrderApplycMamager getOrderApplycMamager() {
		return orderApplycMamager;
	}

	public void setOrderApplycMamager(IOrderApplycMamager orderApplycMamager) {
		this.orderApplycMamager = orderApplycMamager;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public List<OrderApply> getApplylist() {
		return applylist;
	}

	public void setApplylist(List<OrderApply> applylist) {
		this.applylist = applylist;
	}

	public double getDepreciation_price() {
		return depreciation_price;
	}

	public void setDepreciation_price(double depreciation_price) {
		this.depreciation_price = depreciation_price;
	}

	public double getShip_price() {
		return ship_price;
	}

	public void setShip_price(double ship_price) {
		this.ship_price = ship_price;
	}

	public double getReturned_price() {
		return returned_price;
	}

	public void setReturned_price(double returned_price) {
		this.returned_price = returned_price;
	}

	public String getPayment_cfg_id() {
		return payment_cfg_id;
	}

	public void setPayment_cfg_id(String payment_cfg_id) {
		this.payment_cfg_id = payment_cfg_id;
	}

	public List getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List paymentList) {
		this.paymentList = paymentList;
	}

	public PaymentCfgInf getPaymentCfgServ() {
		return paymentCfgServ;
	}

	public void setPaymentCfgServ(PaymentCfgInf paymentCfgServ) {
		this.paymentCfgServ = paymentCfgServ;
	}

	public List<OrderApply> getOrderApplyItemList() {
		return orderApplyItemList;
	}

	public void setOrderApplyItemList(List<OrderApply> orderApplyItemList) {
		this.orderApplyItemList = orderApplyItemList;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public List getBankList() {
		return bankList;
	}

	public void setBankList(List bankList) {
		this.bankList = bankList;
	}

	public PaymentInf getPaymentServ() {
		return paymentServ;
	}

	public void setPaymentServ(PaymentInf paymentServ) {
		this.paymentServ = paymentServ;
	}

	public PayCfg getPayCfg() {
		return payCfg;
	}

	public void setPayCfg(PayCfg payCfg) {
		this.payCfg = payCfg;
	}

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public OrderFlow getFlow() {
		return flow;
	}

	public void setFlow(OrderFlow flow) {
		this.flow = flow;
	}

	public OrderFlowDef getFlowDef() {
		return flowDef;
	}

	public void setFlowDef(OrderFlowDef flowDef) {
		this.flowDef = flowDef;
	}

	public List<OrderFlowDef> getFlowDefList() {
		return flowDefList;
	}

	public void setFlowDefList(List<OrderFlowDef> flowDefList) {
		this.flowDefList = flowDefList;
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

	public IOrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getTodo_id() {
		return todo_id;
	}

	public void setTodo_id(String todo_id) {
		this.todo_id = todo_id;
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

	public ShipRequest getShipRequest() {
		return shipRequest;
	}

	public void setShipRequest(ShipRequest shipRequest) {
		this.shipRequest = shipRequest;
	}

	public DeliveryInf getDeliveryServ() {
		return deliveryServ;
	}

	public void setDeliveryServ(DeliveryInf deliveryServ) {
		this.deliveryServ = deliveryServ;
	}

	public DeliveryFlow getDeliveryFlow() {
		return deliveryFlow;
	}

	public void setDeliveryFlow(DeliveryFlow deliveryFlow) {
		this.deliveryFlow = deliveryFlow;
	}

	public IDeliveryManager getDeliveryManager() {
		return deliveryManager;
	}

	public void setDeliveryManager(IDeliveryManager deliveryManager) {
		this.deliveryManager = deliveryManager;
	}

	public String getLogi_id_name() {
		return logi_id_name;
	}

	public void setLogi_id_name(String logi_id_name) {
		this.logi_id_name = logi_id_name;
	}

	public IDeliveryFlow getDeliveryFlowManager() {
		return deliveryFlowManager;
	}

	public void setDeliveryFlowManager(IDeliveryFlow deliveryFlowManager) {
		this.deliveryFlowManager = deliveryFlowManager;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public IOrderCommentManager getOrderCommentManager() {
		return orderCommentManager;
	}

	public void setOrderCommentManager(IOrderCommentManager orderCommentManager) {
		this.orderCommentManager = orderCommentManager;
	}

	public List getOrderExceptionList() {
		return orderExceptionList;
	}

	public void setOrderExceptionList(List orderExceptionList) {
		this.orderExceptionList = orderExceptionList;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

	public OrderApply getOrderApply() {
		return orderApply;
	}

	public void setOrderApply(OrderApply orderApply) {
		this.orderApply = orderApply;
	}

	public String getApply_id() {
		return apply_id;
	}

	public void setApply_id(String apply_id) {
		this.apply_id = apply_id;
	}

	public List<OrderApplyItem> getApplyItemList() {
		return applyItemList;
	}

	public void setApplyItemList(List<OrderApplyItem> applyItemList) {
		this.applyItemList = applyItemList;
	}

	public boolean isHasOrderApply() {
		return hasOrderApply;
	}

	public void setHasOrderApply(boolean hasOrderApply) {
		this.hasOrderApply = hasOrderApply;
	}

	public IOrderApplyManage getOrderApplyManage() {
		return orderApplyManage;
	}

	public void setOrderApplyManage(IOrderApplyManage orderApplyManage) {
		this.orderApplyManage = orderApplyManage;
	}

	public boolean isHasOrderItems() {
		return hasOrderItems;
	}

	public void setHasOrderItems(boolean hasOrderItems) {
		this.hasOrderItems = hasOrderItems;
	}

	public AdminUser getUser() {
		return user;
	}

	public void setUser(AdminUser user) {
		this.user = user;
	}

	public OrderReturnVisit getVisit() {
		return visit;
	}

	public void setVisit(OrderReturnVisit visit) {
		this.visit = visit;
	}

	public List<OrderReturnVisit> getVisitList() {
		return visitList;
	}

	public void setVisitList(List<OrderReturnVisit> visitList) {
		this.visitList = visitList;
	}

	public List<OrderReturnVisitType> getVisitTypeList() {
		return visitTypeList;
	}

	public void setVisitTypeList(List<OrderReturnVisitType> visitTypeList) {
		this.visitTypeList = visitTypeList;
	}

	public OrderGroup getOrderGroup() {
		return orderGroup;
	}

	public void setOrderGroup(OrderGroup orderGroup) {
		this.orderGroup = orderGroup;
	}

	public OrderFlow getOrderFlow() {
		return orderFlow;
	}

	public void setOrderFlow(OrderFlow orderFlow) {
		this.orderFlow = orderFlow;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<OrderReturnVisitType> getVisitMethodList() {
		return visitMethodList;
	}

	public void setVisitMethodList(List<OrderReturnVisitType> visitMethodList) {
		this.visitMethodList = visitMethodList;
	}

	public int getMonitor() {
		return monitor;
	}

	public void setMonitor(int monitor) {
		this.monitor = monitor;
	}
	
}
