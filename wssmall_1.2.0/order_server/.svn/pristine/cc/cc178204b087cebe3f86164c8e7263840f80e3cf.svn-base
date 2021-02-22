package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import params.order.req.AcceptReq;
import params.order.req.ActionRuleReq;
import params.order.req.DeliveryReq;
import params.order.req.PaymentLogReq;
import params.order.resp.ActionRuleResp;
import params.order.resp.DeliveryResp;
import params.order.resp.PaymentLogResp;
import rule.RuleInvoker;
import rule.params.accept.resp.AcceptRuleResp;
import rule.params.confirm.req.ConfirmRuleReq;
import rule.params.confirm.resp.ConfirmRuleResp;
import rule.params.pay.req.PayRuleReq;
import rule.params.pay.resp.PayRuleResp;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.iservice.IGoodsService;
import zte.net.iservice.ILogsServices;
import zte.net.iservice.IOrderServices;
import zte.params.goods.req.ProductsListReq;
import zte.params.goods.resp.ProductsListResp;
import zte.params.order.req.ShipItemSyncReq;
import zte.params.order.resp.OrderAddResp;

import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.attr.service.IAttrDefNManager;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsActionRule;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.service.IOrderAcceptManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderNFlowManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.service.IOrderFlowBussManager;

import consts.ConstsCore;

/**
 * 查询商品动作规则入口
 * 
 * @作者 MoChunrun
 * @创建日期 2013-10-10
 * @版本 V 1.0
 */
public class OrderActionRuleServ extends ServiceBase implements
		OrderActionRuleInf {

	private IOrderNFlowManager orderNFlowManager;
	private PaymentInf paymentServ;
	private DeliveryInf deliveryServ;
	private AcceptInf acceptServ;
	private IOrderAcceptManager orderAcceptManager;
	private IOrderManager orderManager;
	private IOrderFlowBussManager orderFlowBussManager;
	private IAttrDefNManager attrDefNManager;
	private IGoodsService goodServices;
	private ILogsServices logsServices;
	

	@Override
	public ActionRuleResp confirmOrder(OrderAddResp orderAddResp,ActionRuleReq req) throws Exception {
		Order order =req.getOrder();
		ActionRuleResp resp = new ActionRuleResp();
		String order_id = req.getOrder_id();
		String service_code=req.getOrder().getService_code();
		//获取订单子单
		List<OrderItem> list = req.getOrderItemList(); 
		req.setOrder_id(order_id);
		for (OrderItem oi : list) {
			List<GoodsActionRule> actionRuleList = orderNFlowManager.listGoodsActionRuleByGoodsId(oi.getGoods_id(), service_code);
			if(actionRuleList==null)continue;
			oi.setActionRuleList(actionRuleList);
			// 检查需要确认
			if(!Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
				for(GoodsActionRule gr:actionRuleList){
					//if (OrderStatus.ACTION_CODE_CONFIRM.equals(oi.getAction_code())
					//		&& OrderStatus.ACCEPT_STATUS_YES.equals(oi.getStatus())) {
					if (OrderStatus.ACTION_CODE_CONFIRM.equals(gr.getAction_code())
							&& OrderStatus.ACCEPT_STATUS_YES.equals(gr.getStatus())) {
						// 订单确认规则，判断订单是否需要确认
						ConfirmRuleReq confirmRuleReq = new ConfirmRuleReq();
						confirmRuleReq.setGoods_id(oi.getGoods_id());
						confirmRuleReq.setOrderItem(oi);
						confirmRuleReq.setParams(req.getParamsList().get(0));
						confirmRuleReq.setUserSessionId(req.getUserSessionId());
						ConfirmRuleResp c_resp = RuleInvoker.computeConfirmStatus(confirmRuleReq); // 自动确认
		
						// 非自动确认，则需要则不允许确认处理   去掉了 2013-12-10 确认没有通过也生成记录
						if (c_resp == null || c_resp.isNeed_confirm()) {
							//resp.setError_code(ConstsCore.ERROR_FAIL);
							//resp.setError_msg("订单确认不通过，请先确认订单");
							//需要修改==================把订单状态改为待确认 流程还往下走不返回======================
							req.getOrder().setStatus(OrderStatus.ORDER_WAI_CONFIRM);
							orderManager.updateOrderStatus(OrderStatus.ORDER_WAI_CONFIRM, req.getOrder().getOrder_id());
							//return resp;
							break ;
						}
					}
				}
			}
		}
		return actionConfirm(orderAddResp,req);
	}

	
	/**
	 * 订单确认通过，则根据规则派生出发货、支付、确认单
	 * 
	 * @param req
	 * @return
	 * @throws BusiException 
	 */
	private ActionRuleResp actionConfirm(OrderAddResp orderAddResp,ActionRuleReq req) throws Exception {
		//所有属性实例
		List<AttrInstBusiRequest> allerAttrInstList = new ArrayList<AttrInstBusiRequest>();
		Order order =req.getOrder();
		
		//===================增加订单扩展属性与受理属性=========================
		Map oextMap = new HashMap();
		oextMap.put("order_id", order.getOrder_id());
		boolean extendsFlag = true;
		if("ECS".equals(ManagerUtils.getSourceFrom()))extendsFlag = false;
		
		if(extendsFlag || Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
			//订单自动化service_code
			
		   OrderTreeBusiRequest orderTreeBusiRequest = new OrderTreeBusiRequest();
		   orderTreeBusiRequest.setOrder_id(order.getOrder_id());
		   orderTreeBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
		   orderTreeBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		   orderTreeBusiRequest.setCreate_time(Consts.SYSDATE);
		   orderTreeBusiRequest.setUpdate_time(Consts.SYSDATE);
		   orderTreeBusiRequest.getOrderBusiRequest().setOrder_id(order.getOrder_id());//add by wui
		   
		   //设置属性标志为"yes",则不需要从订单树获取属性数据
		   logsServices.setAttrFlag(order.getOrder_id(),"yes");
		   
		   orderTreeBusiRequest.store();
		   logsServices.cacheOrderTree(orderTreeBusiRequest);
		 
		   
		   
		   //缓存订单、订单项信息
		   OrderBusiRequest orderBusiRequest = new OrderBusiRequest();
		   com.ztesoft.common.util.BeanUtils.copyProperties(orderBusiRequest, order);
		   OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order.getOrder_id());
		   orderTree.setOrderBusiRequest(orderBusiRequest);
		   List<OrderItem> orderItems =  order.getOrderItemList();
		   for (OrderItem orderItem:orderItems) {
			   OrderItemBusiRequest orderItemBusiRequest = new OrderItemBusiRequest();
			   com.ztesoft.common.util.BeanUtils.copyProperties(orderItemBusiRequest, orderItem);
			   orderTree.getOrderItemBusiRequests().add(orderItemBusiRequest);
		   }
		   logsServices.cacheOrderTree(orderTree);
		   
//		   long end1 = System.currentTimeMillis();
		   List<AttrInstBusiRequest> list = attrDefNManager.insertAttrTable(Const.ATTR_DEF_SPACE_TYPE_ORDER_EXT, null, req.getParamsList().get(0), oextMap, order.getOrder_id(), order.getOrder_id(),null,null,null,null,0);
		   if(list!=null)allerAttrInstList.addAll(list);
//		   long start4 = System.currentTimeMillis();
//		   logger.info("订单标准化11==========>："+(start4-end1));
		   //优先把数据先灌进去,add by wui
		   List<OrderItem> ilist = req.getOrderItemList();
		   for (OrderItem oim : ilist) {
				insertOrderItemExtAndProdTable(allerAttrInstList, req, order, oim,extendsFlag);
			}
		}
		//===================增加订单扩展属性与受理属性=========================
		List<OrderItem> list = req.getOrderItemList();//orderNFlowManager.listOrderItemActionCode(req.getOrder_id(),service_code);
		ActionRuleResp resp = new ActionRuleResp();
		String order_id = req.getOrder_id();
		boolean shipFlag = false;
		boolean acceptFlag = false;
		boolean payFlag = false;
		PayRuleResp p_resp =null;
		
		//先走支付、发货流程
		int i = 0;
		
		long start = System.currentTimeMillis();
//		logger.info("订单标准化1==========>："+(start-end1));
		for (OrderItem oim : list) {
			
			List<GoodsActionRule> actionRuleList = oim.getActionRuleList();
			if(actionRuleList==null)continue;
			for(GoodsActionRule oi:actionRuleList){
	            if(StringUtils.isEmpty(oi.getStatus()) ||!oi.getStatus().equals("yes")){
	                continue;
	            }
				if (OrderStatus.ACTION_CODE_PAY.equals(oi.getAction_code())) {
					if (OrderStatus.ACCEPT_STATUS_YES.equals(oi.getStatus()))
						payFlag = true;
					else //
					{
						// 订单确认规则，判断订单是否需要确认
						PayRuleReq payRuleReq = new PayRuleReq();
						payRuleReq.setGoods_id(oi.getGoods_id());
						payRuleReq.setOrderItem(oim);
						payRuleReq.setAppKey(req.getAppKey());
						payRuleReq.setSourceFrom(req.getSourceFrom());
						payRuleReq.setUserSessionId(req.getUserSessionId());
						payRuleReq.setOrder(order);
						 p_resp = RuleInvoker.computePayStatus(payRuleReq); // 自动确认
						payFlag = p_resp.isAuto_pay();
					}
	
				} else if (OrderStatus.ACTION_CODE_DELIVERY.equals(oi.getAction_code())
						&& OrderStatus.ACCEPT_STATUS_YES.equals(oi.getStatus())) {
					shipFlag = true;
				}
			}
			//===================增加子订单扩展属性与受理属性=========================
			String type_code = orderManager.getGoodsTypeCode(oim.getGoods_id());
			Map oiextMap = new HashMap();
			oiextMap.put("order_id", order.getOrder_id());
			oiextMap.put("item_id", oim.getItem_id());
			if(extendsFlag || Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
				List<AttrInstBusiRequest> glist = attrDefNManager.insertAttrTable(Const.ATTR_DEF_SPACE_TYPE_ORDER_ITEM_EXT, type_code , req.getParamsList().get(0), oiextMap, order.getOrder_id(), oim.getItem_id(),oim.getGoods_id(),null,oim.getProduct_id(),null,i);
				if(glist!=null)allerAttrInstList.addAll(glist);
				i++;
			}
			//===================增加子订单扩展属性与受理属性=========================
			insertOrderItemExtAndProd(allerAttrInstList,req, order, oim,extendsFlag);
			//===================增加子订单货品与扩展属性与受理属性=========================
		}
		
		long end = System.currentTimeMillis();
//		logger.info("订单标准化2==========>："+(end-start));
		PaymentLogReq plreq = null;
		PaymentLogResp payResp = null;
		if(!Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code()) && order.getOrder_amount()<=0)payFlag = false;
		if (payFlag) {
			// 生成支付记录,传入order id
			plreq = new PaymentLogReq();
			plreq.setOrder_id(order_id);
			plreq.setPay_type(req.getPayment_id());
			plreq.setUserSessionId(req.getUserSessionId());
			plreq.setOrder(order);
			plreq.setOrderOuters(req.getOrderOuters());
			plreq.setUserSessionId(req.getUserSessionId());
			plreq.setParams(req.getParamsList().get(0));
			payResp = paymentServ.createPaymentLog(plreq);
			if(payResp!=null){
				order.setPaymentLog(payResp.getPaymentLog());
				Map pextMap = new HashMap();
				pextMap.put("order_id", order.getOrder_id());
				pextMap.put("payment_id", payResp.getPaymentLog().getPayment_id());
				pextMap.put("member_id", order.getMember_id());
				if(extendsFlag || Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
					List<AttrInstBusiRequest> plist = attrDefNManager.insertAttrTable(Const.ATTR_DEF_SPACE_TYPE_ORDER_PAY_EXT, null, req.getParamsList().get(0), pextMap, order.getOrder_id(), payResp.getPaymentLog().getPayment_id(),null,null,null,null,0);
					if(plist!=null)allerAttrInstList.addAll(plist);
				}
			}
			
			orderAddResp.setPayFlag(true);
		}else{
			orderAddResp.setPayFlag(false);
		}
		DeliveryReq dlreq = null;
		DeliveryResp dResp = null;
		if (shipFlag) {
			dlreq = new DeliveryReq();
			dlreq.setOrder_id(order_id);
			dlreq.setUserSessionId(req.getUserSessionId());
			dlreq.setOrder(order);
			dlreq.setAppKey(req.getAppKey());
			dlreq.setOrderOuters(req.getOrderOuters());
			dlreq.setSourceFrom(req.getSourceFrom());
			dlreq.setUserSessionId(req.getUserSessionId());
			dlreq.setParams(req.getParamsList().get(0));
			dResp = deliveryServ.createDelivery(dlreq);
			
			if(dResp!=null){
				order.setDelivery(dResp.getDelivery());
				//====同步子订单到物流子单表==========================
				if("ECS".equals(ManagerUtils.getSourceFrom()) && Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
					IOrderServices os = SpringContextHolder.getBean("orderServices");
					ShipItemSyncReq syncreq = new ShipItemSyncReq();
					syncreq.setOrder_id(order_id);
					os.syncShipItems(syncreq);//此处会更新子单发货数量为全量发货
				}
				//====同步子订单到物流子单表==========================
				//===================更新物流单扩展属性与受理属性=========================
				Map dextMap = new HashMap();
				dextMap.put("delivery_id", dResp.getDelivery().getDelivery_id());
				if(extendsFlag || Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
					Map delMap =req.getParamsList().get(0);
					delMap.put("order_id", order.getOrder_id());
					List<AttrInstBusiRequest> slist = attrDefNManager.insertAttrTable(Const.ATTR_DEF_SPACE_TYPE_ORDER_DELIVERY, null, delMap, dextMap, order.getOrder_id(), dResp.getDelivery().getDelivery_id(),null,null,null,null,0);
					if(slist!=null)allerAttrInstList.addAll(slist);
				}
				
				//===================更新物流单扩展属性与受理属性=========================
			}
		}
		 
		if(!Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
			if(payFlag){
				RuleInvoker.afSavePaymentInst(plreq);
			}
			if(shipFlag){
				RuleInvoker.afSaveDeliverInst(dlreq);
			}
			if(order.getStatus()!=null && OrderStatus.ORDER_WAI_CONFIRM!=order.getStatus()){
				String sql = "update es_order set status=?,pay_status=?,ship_status=? where order_id=?";
				if(!shipFlag || !payFlag){
					String status = "2";
					String pay_status = "0";
					String ship_status = "0";
					if(!shipFlag){
						ship_status = "1";
					}
					if(!payFlag){
						pay_status = "1";			
					}
					if(!shipFlag && !payFlag){
						status = "5";
					}else if(!shipFlag && payFlag){
						status = "2";
					}else if(shipFlag && !payFlag){
						status = "3";
					}
					this.baseDaoSupport.execute(sql, status,pay_status,ship_status,order_id);
					order.setStatus(Integer.valueOf(status));
				}
			}
		}
		String goods_id = null;
		//再走受理流程
		for (OrderItem oim : list) {
			goods_id = oim.getGoods_id();
			List<GoodsActionRule> actionRuleList = oim.getActionRuleList();
			if(actionRuleList==null)continue;
			for(GoodsActionRule oi:actionRuleList){
	            if(StringUtils.isEmpty(oi.getStatus()) ||!oi.getStatus().equals("yes")){
	                continue;
	            }
				if (OrderStatus.ACTION_CODE_ACCEPT.equals(oi.getAction_code())
						&& OrderStatus.ACCEPT_STATUS_YES.equals(oi.getStatus())) {
					goods_id = oim.getGoods_id();
					// 调用授理方法传入参数 orderitem
					acceptFlag = true;
					// 受理单循环处理
					AcceptReq acceptReq = new AcceptReq();
					acceptReq.setOrderItem(oim);
					acceptReq.setAppKey(req.getAppKey());
					acceptReq.setSourceFrom(req.getSourceFrom());
					acceptReq.setZteRequest(req.getZteRequest());
					acceptReq.setOrder(req.getOrder());
					acceptReq.setUserSessionId(req.getUserSessionId());
					acceptReq.setOrderOuters(req.getOrderOuters());
					acceptReq.setParamsList(req.getParamsList());
					AcceptRuleResp arsp = acceptServ.createAccept(acceptReq);
					if(arsp!=null)//不为空，设置返回值
						orderAddResp.setZteResponse(arsp.getZteResponse());//设置受理后返回对象值
					
					//爱理完后跳出
					break ;
				}
			}
		}
		
		long start2 = System.currentTimeMillis();
		//批量插入属性实列表
//		attrDefNManager.batchInsertAttrInst(allerAttrInstList);
		long end2 = System.currentTimeMillis();
		addReturn(req, resp);

		return resp;
	}
	
	/**
	 * 货品表、货品扩展表统一数据写入
	 * @param attrInstlist
	 * @param req
	 * @param order
	 * @param oim
	 * @throws Exception
	 */
	private void insertOrderItemExtAndProdTable(List<AttrInstBusiRequest> attrInstlist,ActionRuleReq req, Order order,OrderItem oim,boolean extendsFlag) throws Exception {
		if(extendsFlag || Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
			List<Goods> products = null;
			ProductsListReq plreq = new ProductsListReq();
			plreq.setGoods_id(oim.getGoods_id());
			ProductsListResp plresp = goodServices.listProducts(plreq);
			if(plresp!=null)products = plresp.getProducts();//new ArrayList<Goods>();
			//插入商品货品表
			if(products!=null && products.size()>0){
				int k = 0;
				for(Goods g:products){
					String item_pro_inst_id = this.baseDaoSupport.getSequences("S_ES_ORDER_ITEMS_PROD");
					OrderItemProdBusiRequest pro = new OrderItemProdBusiRequest();
					pro.setOrder_id(order.getOrder_id());
					pro.setCreate_time("sysdate");
					pro.setItem_id(oim.getItem_id());
					pro.setItem_prod_inst_id(item_pro_inst_id);
					pro.setItem_spec_goods_id(oim.getGoods_id());
					pro.setOrder_id(order.getOrder_id());
					pro.setProd_spec_goods_id(g.getProd_goods_id());
					pro.setProd_spec_type_code(g.getType_code());
					pro.setStatus("0");
					pro.setName(g.getName());
					pro.setDb_action(ConstsCore.DB_ACTION_INSERT);
					pro.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					pro.store();
					
					//扩展表写入数据
					Map extMap = new HashMap();
					extMap.put("order_id", order.getOrder_id());
					extMap.put("item_id", oim.getItem_id());
					extMap.put("item_prod_inst_id", item_pro_inst_id);
					//货品单表
					String o_item_id = (String)extMap.get("item_id");
					String o_item_prod_inst_id = (String)extMap.get("item_prod_inst_id");
					OrderItemProdExtBusiRequest reqext = new OrderItemProdExtBusiRequest();
					reqext.setOrder_id(order.getOrder_id());
					reqext.setItem_id(o_item_id);
					reqext.setItem_prod_inst_id(o_item_prod_inst_id);
					reqext.setDb_action(ConstsCore.DB_ACTION_INSERT);
					reqext.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					reqext.store();
				}
			}
		}
	}

	/**
	 * 触发属性
	 * @param attrInstlist
	 * @param req
	 * @param order
	 * @param oim
	 * @throws Exception
	 */
	private void insertOrderItemExtAndProd(List<AttrInstBusiRequest> attrInstlist,ActionRuleReq req, Order order,OrderItem oim,boolean extendsFlag) throws Exception {
		if(extendsFlag || Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
			List<Goods> products = null;
			ProductsListReq plreq = new ProductsListReq();
			plreq.setGoods_id(oim.getGoods_id());
			List<OrderItemBusiRequest> orderItemBusiRequests = CommonDataFactory.getInstance().getOrderTree(oim.getOrder_id()).getOrderItemBusiRequests();
			for (OrderItemBusiRequest orderItemBusiRequest:orderItemBusiRequests) {
				if(orderItemBusiRequest.getItem_id().equals(oim.getItem_id())){ //获取匹配的订单项
					int k=0;
					for (OrderItemProdBusiRequest orderItemProdBusiRequest:orderItemBusiRequest.getOrderItemProdBusiRequests()) {
						Map oipextMap = new HashMap();
						oipextMap.put("order_id", order.getOrder_id());
						oipextMap.put("item_id", oim.getItem_id());
						oipextMap.put("item_prod_inst_id", orderItemProdBusiRequest.getItem_prod_inst_id());
						if(extendsFlag || Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
							List<AttrInstBusiRequest> plist = attrDefNManager.insertAttrTable(Const.ATTR_DEF_SPACE_TYPE_ORDER_ITEM_PRO_EXT, orderItemProdBusiRequest.getProd_spec_type_code(), req.getParamsList().get(0), oipextMap, order.getOrder_id(), orderItemProdBusiRequest.getItem_prod_inst_id(),oim.getGoods_id(),orderItemProdBusiRequest.getProd_spec_goods_id(),oim.getProduct_id(),orderItemProdBusiRequest.getItem_prod_inst_id(),k);
							if(plist!=null)attrInstlist.addAll(plist);
							k++;
						}
				}
			}
		}
	  }
	}

	public IOrderNFlowManager getOrderNFlowManager() {
		return orderNFlowManager;
	}

	public void setOrderNFlowManager(IOrderNFlowManager orderNFlowManager) {
		this.orderNFlowManager = orderNFlowManager;
	}

	public PaymentInf getPaymentServ() {
		return paymentServ;
	}

	public void setPaymentServ(PaymentInf paymentServ) {
		this.paymentServ = paymentServ;
	}

	public DeliveryInf getDeliveryServ() {
		return deliveryServ;
	}

	public void setDeliveryServ(DeliveryInf deliveryServ) {
		this.deliveryServ = deliveryServ;
	}

	public AcceptInf getAcceptServ() {
		return acceptServ;
	}

	public void setAcceptServ(AcceptInf acceptServ) {
		this.acceptServ = acceptServ;
	}

	public IOrderAcceptManager getOrderAcceptManager() {
		return orderAcceptManager;
	}

	public void setOrderAcceptManager(IOrderAcceptManager orderAcceptManager) {
		this.orderAcceptManager = orderAcceptManager;
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public IOrderFlowBussManager getOrderFlowBussManager() {
		return orderFlowBussManager;
	}

	public void setOrderFlowBussManager(IOrderFlowBussManager orderFlowBussManager) {
		this.orderFlowBussManager = orderFlowBussManager;
	}

	public IAttrDefNManager getAttrDefNManager() {
		return attrDefNManager;
	}

	public void setAttrDefNManager(IAttrDefNManager attrDefNManager) {
		this.attrDefNManager = attrDefNManager;
	}

	public IGoodsService getGoodServices() {
		return goodServices;
	}

	public void setGoodServices(IGoodsService goodServices) {
		this.goodServices = goodServices;
	}

	public ILogsServices getLogsServices() {
		return logsServices;
	}

	public void setLogsServices(ILogsServices logsServices) {
		this.logsServices = logsServices;
	}

}
