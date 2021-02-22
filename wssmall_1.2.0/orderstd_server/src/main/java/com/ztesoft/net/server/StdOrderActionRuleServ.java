package com.ztesoft.net.server;

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
import rule.params.accept.req.AcceptRuleReq;
import rule.params.accept.resp.AcceptRuleResp;
import rule.params.confirm.req.ConfirmRuleReq;
import rule.params.confirm.resp.ConfirmRuleResp;
import rule.params.pay.req.PayRuleReq;
import rule.params.pay.resp.PayRuleResp;
import services.ServiceBase;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.iservice.IGoodsService;
import zte.net.iservice.ILogsServices;
import zte.params.goods.req.ProductsListReq;
import zte.params.goods.resp.ProductsListResp;
import zte.params.order.resp.OrderAddResp;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.DeliveryItem;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsActionRule;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.PaymentLog;
import com.ztesoft.net.mall.core.service.ITplInstSManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AttrInst;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.orderstd.rule.StdRuleInvoker;
import com.ztesoft.orderstd.service.IAttrDefSManager;
import com.ztesoft.util.OrderThreadLocalHolder;

import commons.CommonTools;
import consts.ConstsCore;

/**
 * 查询商品动作规则入口
 * 
 * @作者 MoChunrun
 * @创建日期 2013-10-10
 * @版本 V 1.0
 */
public class StdOrderActionRuleServ extends ServiceBase {

	//private AcceptInf acceptServ;
	private IAttrDefSManager attrDefSManager;
	private IGoodsService goodServices;
	private ILogsServices logsServices;
	

	public ActionRuleResp confirmOrder(OrderAddResp orderAddResp,ActionRuleReq req) throws Exception {
		Order order =req.getOrder();
		ActionRuleResp resp = new ActionRuleResp();
		String order_id = req.getOrder_id();
		String service_code=req.getOrder().getService_code();
		//获取订单子单
		List<OrderItem> list = req.getOrderItemList(); 
		req.setOrder_id(order_id);
		for (OrderItem oi : list) {
			List<GoodsActionRule> actionRuleList = listGoodsActionRuleByGoodsId(oi.getGoods_id(), service_code);
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
						ConfirmRuleResp c_resp = StdRuleInvoker.computeConfirmStatus(confirmRuleReq); // 自动确认
		
						// 非自动确认，则需要则不允许确认处理   去掉了 2013-12-10 确认没有通过也生成记录
						if (c_resp == null || c_resp.isNeed_confirm()) {
							//resp.setError_code(ConstsCore.ERROR_FAIL);
							//resp.setError_msg("订单确认不通过，请先确认订单");
							//需要修改==================把订单状态改为待确认 流程还往下走不返回======================
							req.getOrder().setStatus(OrderStatus.ORDER_WAI_CONFIRM);
							attrDefSManager.updateOrderStatus(OrderStatus.ORDER_WAI_CONFIRM, req.getOrder().getOrder_id());
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
	 * @throws
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
		   
		   long end1 = System.currentTimeMillis();
		   List<AttrInstBusiRequest> list = attrDefSManager.insertAttrTable(Const.ATTR_DEF_SPACE_TYPE_ORDER_EXT, null, req.getParamsList().get(0), oextMap, order.getOrder_id(), order.getOrder_id(),order.getGoods_id(),null,null,null,0);
		   if(list!=null)allerAttrInstList.addAll(list);
		   long start4 = System.currentTimeMillis();
		   logger.info("实例属性数据插入时间attrDefSManager.insertAttrTable==========>："+(start4-end1));
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
						 p_resp = StdRuleInvoker.computePayStatus(payRuleReq); // 自动确认
						payFlag = p_resp.isAuto_pay();
					}
	
				} else if (OrderStatus.ACTION_CODE_DELIVERY.equals(oi.getAction_code())
						&& OrderStatus.ACCEPT_STATUS_YES.equals(oi.getStatus())) {
					shipFlag = true;
				}
			}
			//===================增加子订单扩展属性与受理属性=========================
			String type_code = attrDefSManager.getGoodsTypeCode(oim.getGoods_id());
			Map oiextMap = new HashMap();
			oiextMap.put("order_id", order.getOrder_id());
			oiextMap.put("item_id", oim.getItem_id());
			if(extendsFlag || Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
				List<AttrInstBusiRequest> glist = attrDefSManager.insertAttrTable(Const.ATTR_DEF_SPACE_TYPE_ORDER_ITEM_EXT, type_code , req.getParamsList().get(0), oiextMap, order.getOrder_id(), oim.getItem_id(),oim.getGoods_id(),null,oim.getProduct_id(),null,i);
				if(glist!=null)allerAttrInstList.addAll(glist);
				i++;
			}
			//===================增加子订单扩展属性与受理属性=========================
			long start_OrderItemExtAndProd = System.currentTimeMillis();
			insertOrderItemExtAndProd(allerAttrInstList,req, order, oim,extendsFlag);
			long end_OrderItemExtAndProd = System.currentTimeMillis();
			logger.info("插入子订单扩展属性与受理属性insertOrderItemExtAndProd耗时:"+(end_OrderItemExtAndProd-start_OrderItemExtAndProd));
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
			payResp = createPaymentLog(plreq);
			if(payResp!=null){
				order.setPaymentLog(payResp.getPaymentLog());
				Map pextMap = new HashMap();
				pextMap.put("order_id", order.getOrder_id());
				pextMap.put("payment_id", payResp.getPaymentLog().getPayment_id());
				pextMap.put("member_id", order.getMember_id());
				if(extendsFlag || Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
					List<AttrInstBusiRequest> plist = attrDefSManager.insertAttrTable(Const.ATTR_DEF_SPACE_TYPE_ORDER_PAY_EXT, null, req.getParamsList().get(0), pextMap, order.getOrder_id(), payResp.getPaymentLog().getPayment_id(),order.getGoods_id(),null,null,null,0);
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
			dResp = createDelivery(dlreq);
			
			if(dResp!=null){
				order.setDelivery(dResp.getDelivery());
				//====同步子订单到物流子单表==========================
				if("ECS".equals(ManagerUtils.getSourceFrom()) && Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
//					IOrderServices os = SpringContextHolder.getBean("orderServices");
//					ShipItemSyncReq syncreq = new ShipItemSyncReq();
//					syncreq.setOrder_id(order_id);
//					os.syncShipItems(syncreq);//此处会更新子单发货数量为全量发货
					syncShipItems(dResp.getDelivery(),list);
				}
				//====同步子订单到物流子单表==========================
				//===================更新物流单扩展属性与受理属性=========================
				Map dextMap = new HashMap();
				dextMap.put("delivery_id", dResp.getDelivery().getDelivery_id());
				if(extendsFlag || Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
					Map delMap =req.getParamsList().get(0);
					delMap.put("order_id", order.getOrder_id());
					List<AttrInstBusiRequest> slist = attrDefSManager.insertAttrTable(Const.ATTR_DEF_SPACE_TYPE_ORDER_DELIVERY, null, delMap, dextMap, order.getOrder_id(), dResp.getDelivery().getDelivery_id(),order.getGoods_id(),null,null,null,0);
					if(slist!=null)allerAttrInstList.addAll(slist);
				}
				
				//===================更新物流单扩展属性与受理属性=========================
			}
		}
		 
		if(!Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
			if(payFlag){
				StdRuleInvoker.afSavePaymentInst(plreq);
			}
			if(shipFlag){
				StdRuleInvoker.afSaveDeliverInst(dlreq);
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
					AcceptRuleResp arsp = createAccept(acceptReq);
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
					StringBuffer subProdBuffer = new StringBuffer();
					for (OrderItemProdBusiRequest orderItemProdBusiRequest:orderItemBusiRequest.getOrderItemProdBusiRequests()) {
						Map oipextMap = new HashMap();
						oipextMap.put("order_id", order.getOrder_id());
						oipextMap.put("item_id", oim.getItem_id());
						oipextMap.put("item_prod_inst_id", orderItemProdBusiRequest.getItem_prod_inst_id());
						if(extendsFlag || Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(order.getService_code())){
							String sub_prod_spec_type_code =orderItemProdBusiRequest.getProd_spec_type_code();
							if(subProdBuffer.toString().indexOf(sub_prod_spec_type_code)>-1)
								continue;
							List<AttrInstBusiRequest> plist = attrDefSManager.insertAttrTable(Const.ATTR_DEF_SPACE_TYPE_ORDER_ITEM_PRO_EXT,sub_prod_spec_type_code , req.getParamsList().get(0), oipextMap, order.getOrder_id(), orderItemProdBusiRequest.getItem_prod_inst_id(),oim.getGoods_id(),orderItemProdBusiRequest.getProd_spec_goods_id(),oim.getProduct_id(),orderItemProdBusiRequest.getItem_prod_inst_id(),k);
							subProdBuffer.append(sub_prod_spec_type_code);
							if(plist!=null)attrInstlist.addAll(plist);
							k++;
						}
				}
			}
		}
	  }
	}

	
	public IAttrDefSManager getAttrDefSManager() {
		return attrDefSManager;
	}

	public void setAttrDefSManager(IAttrDefSManager attrDefSManager) {
		this.attrDefSManager = attrDefSManager;
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

	public List<GoodsActionRule> listGoodsActionRuleByGoodsId(String goods_id,String service_code) {
		String sql = SF.orderSql("QUERY_GOODS_ACTION_RULE_GOODS_ID");
		return this.baseDaoSupport.queryForList(sql, GoodsActionRule.class, goods_id,ManagerUtils.getSourceFrom(),service_code);
	}
	private ITplInstSManager tplInstSManager; 
	public ITplInstSManager getTplInstSManager(){
		return tplInstSManager;
	}
	public void setTplInstSManager(ITplInstSManager tplInstSManager){
		this.tplInstSManager = tplInstSManager;
	}
	public DeliveryResp createDelivery(DeliveryReq req) {
		DeliveryResp resp = new DeliveryResp();
		Order order = req.getOrder();
		if(order==null)
			order = attrDefSManager.getOrder(req.getOrder_id());
		req.setOrder_id(order.getOrder_id());
		//List<OrderItem> orderItemList = orderNFlowManager.listNotShipGoodsItem(order.getOrder_id());
		//List<GoodsActionRule> list = orderNFlowManager.listGoodsActionRule(order.getOrder_id());
		String create_type =req.getOrder().getCreate_type()+"";
		Delivery delivery = new Delivery();
		if(OrderStatus.WP_CREATE_TYPE_6.equals(create_type) || OrderStatus.WP_CREATE_TYPE_7.equals(create_type)){
			delivery.setType(2);
		}else{
			delivery.setType(1);
		}
		delivery.setOrder_id(order.getOrder_id());
		delivery.setMember_id(order.getMember_id());
		delivery.setShip_type(order.getShipping_type());
		delivery.setShip_name(order.getShip_name());
		delivery.setShip_addr(order.getShip_addr());
		delivery.setShip_mobile(order.getShip_mobile());
		delivery.setShip_email(order.getShip_email());
		delivery.setShip_tel(order.getShip_tel());
		delivery.setShip_zip(order.getShip_zip());
		delivery.setCreate_time(DBTUtil.current());
		delivery.setWeight(order.getWeight().floatValue());
		delivery.setPrint_status(OrderStatus.DELIVERY_PRINT_STATUS_0);
		delivery.setShip_status(OrderStatus.DELIVERY_SHIP_STATUS_NOT_CONFIRM);
		delivery.setShip_num(0);
		delivery.setUserid(order.getShip_user_id());
		delivery.setBatch_id(order.getBatch_id());
		delivery.setCreate_type(create_type);
		AdminUser user = OrderThreadLocalHolder.getInstance().getOrderCommonData().getAdminUser();
		if(user!=null){
			delivery.setOp_name(user.getUsername());
		}
		baseDaoSupport.insert("delivery", delivery);
		
		String delivery_id = delivery.getDelivery_id();
		String service_code=order.getService_code();
		List<OrderItem> list = listOrderItemActionCode(order.getOrder_id(),service_code);
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		//查询需要发货的orderItem
		for(OrderItem oi:list){
			if("delivery".equals(oi.getAction_code()) && OrderStatus.ACCEPT_STATUS_YES.equals(oi.getStatus())){
				orderItemList.add(oi);
			}
		}
		
		/*List<DeliveryItem> itemList  = new ArrayList<DeliveryItem>();
		for(OrderItem oi :orderItemList){
			DeliveryItem item = new DeliveryItem();
			item.setGoods_id(oi.getGoods_id());
			item.setName(oi.getName());
			//item.setNum(oi.getNum()-oi.getShip_num());  
			item.setNum(0);
			item.setProduct_id(oi.getSpec_id());
			item.setSn(oi.getSn());
			item.setItemtype(0);
			item.setOrder_item_id(oi.getItem_id());
			item.setDelivery_id(delivery_id);
			itemList.add(item);
			this.baseDaoSupport.insert("delivery_item", item);
		}*/
		/*List<DeliveryItem> giftitemList  = new ArrayList<DeliveryItem>();
		List<Map> giftList = orderNFlowManager.listNotShipGiftItem(order.getOrder_id());
		if(giftList!=null && giftList.size()>0){
			for(Map map :giftList){
				DeliveryItem item = new DeliveryItem();
				item.setGoods_id(map.get("gift_id").toString());
				item.setName(map.get("gift_name").toString());
				item.setNum(0);  
				item.setProduct_id(map.get("gift_id").toString());
				item.setItemtype(2);
				item.setDelivery_id(delivery_id);
				giftitemList.add(item);
				//item.setOrder_item_id(itemids[i]);
				baseDaoSupport.insert("delivery_item", item);
			}
		}*/
		for(OrderItem oi :orderItemList){
			OrderOuter oo = attrDefSManager.getOrderOuterByGoodsId(oi.getProduct_id(),req.getOrderOuters());
			List<AttrInst> oattrInsts = attrDefSManager.getOuterAttrInst(oo);
			Map<String ,List<AttrInst>> acceptTableInstAttrs = new HashMap<String, List<AttrInst>>();
			List<AttrInst> propAttrInsts = new ArrayList<AttrInst>();
			Map<String, String> tableInstFkeys = new HashMap<String, String>();
			//属性数据分组
			tplInstSManager.groupAttrInsts(oattrInsts, acceptTableInstAttrs, propAttrInsts,OrderStatus.SUB_ATTR_SPEC_TYPE_DELIVERY);
			//保存受理属性实例数据到指定的库表
			tplInstSManager.saveTableInst(req.getOrder(),oi,acceptTableInstAttrs,tableInstFkeys);
			//保存受理属性实例数据到属性实例表
			tplInstSManager.saveAttrInst(oi,propAttrInsts,tableInstFkeys);
			
		}
		
		resp.setError_code("0");
		resp.setError_msg("成功");
		resp.setDelivery(delivery);
		resp.setUserSessionId(req.getUserSessionId());
		addReturn(req,resp);
		return resp;
	}
	public List<OrderItem> listOrderItemActionCode(String order_id, String service_code) {
        String sql = SF.orderSql("SERVICE_ITEM_ACTION_CODE_SELECT");
        return this.baseDaoSupport.queryForList(sql, OrderItem.class, service_code, order_id);
    }
	public PaymentLogResp createPaymentLog(PaymentLogReq req){
		PaymentLogResp resp = new PaymentLogResp();
		Order order = req.getOrder();
		if(order==null)
			order = attrDefSManager.getOrder(req.getOrder_id());
		req.setOrder_id(order.getOrder_id());
		String pay_type = req.getPay_type();
		//弹出银行支付界面，选择银行支付处理
		PaymentLog payment = new PaymentLog();
		payment.setPaytype(0);
		payment.setCreate_time(DBTUtil.current());
		//这个需要根据支付方式进行判断为-1(货到付款)还是0(在线支付)
		if("online".equals(pay_type)){
			payment.setStatus(OrderStatus.PAY_STATUS_0);
		}else{
			payment.setStatus(OrderStatus.PAY_STATUS_NOT_CONFIRM);
		}
		payment.setUserid(order.getPay_user_id());
		payment.setMember_id(order.getMember_id());
		payment.setOrder_id(order.getOrder_id());
		if(OrderStatus.WP_CREATE_TYPE_6.equals(order.getCreate_type()+"")||OrderStatus.WP_CREATE_TYPE_7.equals(order.getCreate_type()+"")){
			payment.setType(OrderStatus.PAY_TYPE_2);
		}else{
			payment.setType(OrderStatus.PAY_TYPE_1);
		}
		payment.setPay_type(pay_type);
		payment.setPay_method(order.getPayment_id()+"");
		payment.setMoney(order.getOrder_amount()-order.getPaymoney());
		baseDaoSupport.insert("payment_logs", payment);
		
		String service_code=req.getOrder().getService_code();
		List<OrderItem> list = listOrderItemActionCode(order.getOrder_id(),service_code);
	
		//查询需要支付的orderItem
		for(OrderItem oi:list){
			if(OrderStatus.ACTION_CODE_PAY.equals(oi.getAction_code()) && OrderStatus.ACCEPT_STATUS_YES.equals(oi.getStatus())){
				OrderOuter oo = attrDefSManager.getOrderOuterByGoodsId(oi.getProduct_id(), req.getOrderOuters());
				List<AttrInst> oattrInsts = attrDefSManager.getOuterAttrInst(oo);
				Map<String ,List<AttrInst>> acceptTableInstAttrs = new HashMap<String, List<AttrInst>>();
				List<AttrInst> propAttrInsts = new ArrayList<AttrInst>();
				Map<String, String> tableInstFkeys = new HashMap<String, String>();
				//属性数据分组
				tplInstSManager.groupAttrInsts(oattrInsts, acceptTableInstAttrs, propAttrInsts,OrderStatus.ACTION_CODE_PAY);
				//保存受理属性实例数据到指定的库表
				tplInstSManager.saveTableInst(req.getOrder(),oi,acceptTableInstAttrs,tableInstFkeys);
				//保存受理属性实例数据到属性实例表
				tplInstSManager.saveAttrInst(oi,propAttrInsts,tableInstFkeys);
			}
		}
		
		resp.setUserSessionId(req.getUserSessionId());
		resp.setPaymentLog(payment);
		addReturn(req,resp);
		return resp;
	}
	
//	public AcceptInf getAcceptServ(){
//		return acceptServ;
//	}
//	public void setAcceptServ(AcceptInf acceptServ){
//		this.acceptServ = acceptServ;
//	}
	public AcceptRuleResp createAccept(AcceptReq acceptReq) throws ApiBusiException{
		
		OrderItem orderItem = acceptReq.getOrderItem();
		//add by wui将规则移到本地化中
		AcceptRuleReq acceptRuleReq = new AcceptRuleReq();
		acceptRuleReq.setOrderItem(orderItem);
		acceptRuleReq.setParams(acceptReq.getParamsList().get(0));
		acceptRuleReq.setZteRequest(acceptReq.getZteRequest());
		acceptRuleReq.setOattrInsts(attrDefSManager.getOuterAttrInst(attrDefSManager.getOrderOuterByGoodsId(orderItem.getProduct_id(),acceptReq.getOrderOuters())));
		acceptRuleReq.setOrder(acceptReq.getOrder());
		AcceptRuleResp acceptRuleResp = StdRuleInvoker.afSaveAcceptInst(acceptRuleReq);
		
		return acceptRuleResp;
	}
	public void syncShipItems(Delivery dv,List<OrderItem> orderItems) {
		if (dv == null)
			CommonTools.addError("1", "没有找到订单物流信息");
		List<DeliveryItem> itemList = new ArrayList<DeliveryItem>();
		int totalNum = 0;
		for (OrderItem oi : orderItems) {
			DeliveryItem item = new DeliveryItem();
			int ship_num = oi.getNum() - oi.getShip_num();
			item.setGoods_id(oi.getGoods_id());
			item.setName(oi.getName());
			item.setNum(ship_num);
			item.setProduct_id(oi.getProduct_id());
			item.setSn(oi.getSn());
			item.setItemtype(0);
			item.setOrder_item_id(oi.getItem_id());
			item.setDelivery_id(dv.getDelivery_id());
			itemList.add(item);
			if (ship_num > 0) {// 有没发货商品才加到物流item表
				totalNum += item.getNum();
				// 修改子单发货数量为全发货
				String sql = "update es_order_items t set t.ship_num=? where t.item_id=?";
				this.baseDaoSupport.execute(sql, ship_num,oi.getItem_id());
				this.baseDaoSupport.insert("es_delivery_item", item);
			}
		}
	}
	
}
