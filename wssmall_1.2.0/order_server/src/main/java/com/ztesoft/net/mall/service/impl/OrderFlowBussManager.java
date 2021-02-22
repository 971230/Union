package com.ztesoft.net.mall.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.drools.RuntimeDroolsException;
import org.drools.core.util.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.order.req.ConfirmShippingReq;
import params.order.resp.ConfirmShippingResp;
import rule.RuleInvoker;
import rule.params.order.req.OrderFinishRuleReq;
import rule.params.order.req.OrderPackageRuleReq;
import rule.params.order.req.OrderShippingRuleReq;
import services.DeliveryInf;
import zte.net.iservice.IOrderServices;
import zte.params.order.req.OrderConfirmReq;
import zte.params.order.req.OrderStatusEditReq;
import zte.params.order.req.OrderStockingReq;
import zte.params.order.resp.OrderConfirmResp;
import zte.params.order.resp.OrderStockingResp;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.consts.OrderFlowConst;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.IOrderDirector;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.DeliveryFlow;
import com.ztesoft.net.mall.core.model.DeliveryItem;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderAudit;
import com.ztesoft.net.mall.core.model.OrderAuditRequest;
import com.ztesoft.net.mall.core.model.PaymentLog;
import com.ztesoft.net.mall.core.model.ShipRequest;
import com.ztesoft.net.mall.core.service.IDeliveryFlow;
import com.ztesoft.net.mall.core.service.IDeliveryManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.service.IOrderFlowBussManager;
import com.ztesoft.net.model.OrderFlow;
import com.ztesoft.net.model.OrderFlowDef;
import com.ztesoft.net.model.OrderGroup;
import com.ztesoft.net.model.OrderReturnVisit;
import com.ztesoft.net.model.OrderReturnVisitType;
import com.ztesoft.net.model.OrderToDoList;
import com.ztesoft.net.sqls.SF;

public class OrderFlowBussManager extends BaseSupport implements IOrderFlowBussManager {
	
	private IOrderManager orderManager;
	private IOrderServices orderServices;
	private DeliveryInf deliveryServ;
	private IDeliveryManager deliveryManager;
	private DeliveryFlow deliveryFlow;
	private IDeliveryFlow deliveryFlowManager;
	private IOrderDirector orderDirector;

	@Override
	public OrderFlow queryOrderFlow(String object_id, String flow_type,String service_type) {
		String sql = SF.orderSql("QueryOrderFlow");
		List<OrderFlow> list = this.baseDaoSupport.queryForList(sql, OrderFlow.class, flow_type,object_id,service_type);
		return list.size()>0?list.get(0):null;
	}
	
	@Override
	public OrderFlow getOrderFlowByGoods(String goods_id,String service_type){
		OrderFlow flow = queryOrderFlow(goods_id, OrderFlowConst.FLOW_TYPE_GOODS,service_type);
		if(flow==null){
			String sql = "select t.* from es_order_flow t ,es_goods g where t.object_id=g.type_id and g.goods_id=? and t.type_code=? and t.source_from=g.source_from and t.source_from=? and t.service_type=?";
			List<OrderFlow> list = this.baseDaoSupport.queryForList(sql, OrderFlow.class, goods_id,OrderFlowConst.FLOW_TYPE_GOODS_TYPE,ManagerUtils.getSourceFrom(),service_type);
			flow = list.size()>0?list.get(0):null;
		}
		if(flow==null)flow = queryOrderFlow("-1", OrderFlowConst.FLOW_TYPE_COMMON,service_type);
		return flow;
	}

	@Override
	public OrderFlowDef getOrderFlowDef(int order_status, String flow_id,String service_type) {
		String flow_def_type = OrderFlowConst.getFlowDefType(order_status,service_type);
		String sql = SF.orderSql("FlowDefByOrderStatus");
		List<OrderFlowDef> list = this.baseDaoSupport.queryForList(sql, OrderFlowDef.class, flow_id,flow_def_type);
		return list.size()>0?list.get(0):null;
	}

	@Override
	public OrderFlowDef getNextOrderFlowDef(String flow_def_id) {
		String sql = SF.orderSql("QueryNextOrderFlowDef");
		List<OrderFlowDef> list = this.baseDaoSupport.queryForList(sql, OrderFlowDef.class, flow_def_id);
		return list.size()>0?list.get(0):null;
	}
	
	@Override
	public OrderFlowDef getOrderFlowDef(String flow_def_id){
		String sql = SF.orderSql("OrderFlowDef");
		List<OrderFlowDef> list = this.baseDaoSupport.queryForList(sql, OrderFlowDef.class, flow_def_id);
		return list.size()>0?list.get(0):null;
	}
	
	@Override
	public List<OrderFlowDef> listOrderFlowDef(String flow_id,boolean isNotDo,String order_id){
		if(isNotDo){
			String sql = SF.orderSql("NoDoListOrderFlowDefList");
			return this.baseDaoSupport.queryForList(sql, OrderFlowDef.class, flow_id,order_id,ManagerUtils.getSourceFrom());
		}else{
			String sql = SF.orderSql("ListOrderFlowDefList");
			return this.baseDaoSupport.queryForList(sql, OrderFlowDef.class, flow_id);
		}
	}

	@Override
	public void insertOrderToDoList(OrderToDoList toDoList) {
		this.baseDaoSupport.insert("es_order_todo_list", toDoList);
	}

	@Override
	public void updateOrderToDoListStatus(String oper_id, String oper_name,int oper_type,
			int status,String hint, String list_id) {
		String sql = SF.orderSql("UpdateOrderToDoList");
		this.baseDaoSupport.execute(sql, status,oper_id,oper_name,oper_type,hint,list_id);
	}

	@Override
	public OrderToDoList queryUserOrderToDoList(String order_id, String user_id,int event) {
		if(0==event){
			String sql = SF.orderSql("QueryUserOrderToDoList0");
			List<OrderToDoList> list = this.baseDaoSupport.queryForList(sql, OrderToDoList.class, order_id,ManagerUtils.getSourceFrom(),user_id);
			return list.size()>0?list.get(0):null;
		}else{
			String sql = SF.orderSql("QueryUserOrderToDoList");
			List<OrderToDoList> list = this.baseDaoSupport.queryForList(sql, OrderToDoList.class, order_id,ManagerUtils.getSourceFrom(),user_id,user_id,user_id);
			return list.size()>0?list.get(0):null;
		}
	}

	@Override
	public int countOrderToDoList(String order_id,String flow_def_id) {
		String sql = SF.orderSql("CountOrderToDoList");
		return this.baseDaoSupport.queryForInt(sql, order_id,flow_def_id);
	}
	
	private boolean checkToDoListExists(String order_id,String group_id,String user_id,String flow_def_id,int status){
		String sql = "select count(*) from es_order_todo_list t where t.order_id=? ";
		List param = new ArrayList();
		param.add(order_id);
		if(!StringUtil.isEmpty(group_id)){
			sql += " and t.flow_group_id=? ";
			param.add(group_id);
		}
		if(!StringUtil.isEmpty(user_id)){
			sql += " and t.flow_user_id=? ";
			param.add(user_id);
		}
		if(!StringUtil.isEmpty(flow_def_id)){
			sql += " and t.flow_def_id=? ";
			param.add(flow_def_id);
		}
		if(status!=-999){
			sql += " and t.flow_status=? ";
			param.add(status);
		}
		return this.baseDaoSupport.queryForInt(sql, param.toArray())>0;
	}
	
	/*@Override
	public int create(String order_id,String goods_id, String [] flow_user_id,String [] flow_group_id,String service_type){
		return createOrderToDo(order_id, goods_id, flow_user_id, flow_group_id, null, null, null,0,service_type,"create");
		//return this.createOrderTodo(order_id, goods_id, null, "", 1, service_type, "create");
	}
	
	@Override
	public int create(String order_id,String [] flow_user_id,String [] flow_group_id,String next_flow_def_id,String service_type){
		return createOrderToDo(order_id,null, flow_user_id, flow_group_id, next_flow_def_id, null, null,0,service_type,"create");
		//return this.createOrderTodo(order_id, null, next_flow_def_id, "", 1, service_type, "create");
	}
	
	@Override
	public int next(String order_id,String [] flow_user_id,String [] flow_group_id,String next_flow_def_id,int flag_status,String message,OrderToDoList perv_todo,String service_type){
		return createOrderToDo(order_id,null, flow_user_id, flow_group_id, next_flow_def_id, message, perv_todo, flag_status,service_type,"next");
		//return this.createOrderTodo(order_id, null, next_flow_def_id, message, flag_status, service_type, "next");
	}
	
	@Override
	public int ribTodo(String order_id,OrderToDoList perv_todo){
		return this.createOrderToDo(order_id, null, null, null, null, "撤消", perv_todo, 4, null, "rib");
	}*/
	
	private List<OrderFlowDef> getOrderFlowDefByParentId(String parent_id,String flow_id){
		String sql = "select t.*,t.rowid from es_order_flow_def t where t.flow_id=? and t.parent_id=? and t.source_from=?";
		return this.baseDaoSupport.queryForList(sql, OrderFlowDef.class, flow_id,parent_id,ManagerUtils.getSourceFrom());
	}
	
	private OrderFlowDef getFirstFlowDef(String flow_id){
		List<OrderFlowDef> list = this.getOrderFlowDefByParentId("0", flow_id);
		return list.size()>0?list.get(0):null;
	}
	
	@Override
	public OrderFlow getOrderFlowByTodoListId(String todo_id){
		String sql = SF.orderSql("OrderFlowByTodoListId");
		List<OrderFlow> list = this.baseDaoSupport.queryForList(sql, OrderFlow.class, todo_id,ManagerUtils.getSourceFrom());
		return list.size()>0?list.get(0):null;
	}
	
	@Override
	public int createOrderTodo(String order_id,String goods_id,String next_flow_def_id,String message,int flag_status,String flow_user_id,String service_type,String create_type){
		try{
			AdminUser user = ManagerUtils.getAdminUser();
			Order order = orderManager.get(order_id);
			if(order==null)return 0;
			String oper_id = "";
			String oper_name = "";
			int oper_type = 0;
			if(user!=null){
				oper_id = user.getUserid();
				oper_name = user.getUsername();
			}else{
				Member member  = UserServiceFactory.getUserService().getCurrentMember();
				if(member!=null){
					oper_id = member.getMember_id();
					oper_name = member.getUname();
					oper_type = 1;
				}
			}
			OrderToDoList currToDo = null;
			if(user!=null)currToDo = this.queryUserOrderToDoList(order_id, user.getUserid(),1);
			if(OrderFlowConst.FLOW_DEF_TYPE_ROB.equals(create_type) || "rib".equals(create_type) || OrderFlowConst.ORDER_TODO_LIST_DISPATCH.equals(create_type)){
				if(currToDo==null)return OrderFlowConst.FAIL;
				//抢单或撤单
				if(OrderFlowConst.FLOW_DEF_TYPE_ROB.equals(create_type) && (currToDo==null || !StringUtil.isEmpty(currToDo.getFlow_user_id()))){
					//不能抢单
					return OrderFlowConst.CANT_NOT_LOCK;
				}
				if("rib".equals(create_type) && (currToDo==null || StringUtil.isEmpty(currToDo.getFlow_user_id()))){
					//不能撤单
					return OrderFlowConst.CAN_NOT_RIB;
				}
				int status = OrderFlowConst.STRUTS_6;
				if("rib".equals(create_type)){
					status = OrderFlowConst.STRUTS_4;
				}else if(OrderFlowConst.FLOW_DEF_TYPE_ROB.equals(create_type)){
					status = OrderFlowConst.STRUTS_5;
				}
				if(message==null)message="";
				this.updateOrderToDoListStatus(oper_id, oper_name, oper_type, status,message, currToDo.getList_id());
					
				OrderToDoList todo = new OrderToDoList();
				todo.setCreate_time("sysdate");
				todo.setFlow_def_id(currToDo.getFlow_def_id());
				todo.setFlow_status(0);
				if(OrderFlowConst.FLOW_DEF_TYPE_ROB.equals(create_type)){
					todo.setFlow_user_id(oper_id);
				}else if(OrderFlowConst.ORDER_TODO_LIST_DISPATCH.equals(create_type)){
					todo.setFlow_user_id(flow_user_id);
				}
				todo.setFlow_group_id(currToDo.getFlow_group_id());
				todo.setOrder_id(order_id);
				todo.setFlow_type(currToDo.getFlow_type());
				//todo.setHint("抢单");
				this.insertOrderToDoList(todo);
				
				return OrderFlowConst.SUCCESS;
			}
			
			//以下是创建或进入下一流程==============================
			if(OrderFlowConst.STRUTS_1!=flag_status){
				//不通过
				//updateCurrToDoListStatus(order_id, 3, 0,null); 修改其它的为不需要处理
				if(currToDo!=null) updateOrderToDoListStatus(oper_id, oper_name, oper_type, flag_status,message, currToDo.getList_id());
				return OrderFlowConst.STRUTS_2;
			}
			OrderFlowDef nextFlowDef = null;
			//获取下一环节
			if("create".equals(create_type)){
				//获取第一个
				//如果没有开始流程按订单状态查询下一环节
				if(StringUtil.isEmpty(goods_id)){
					List itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
					if(itemList!=null && itemList.size()>0){
						Map map = (Map) itemList.get(0);
						goods_id = (String) map.get("goods_id");
					}
				}
				OrderFlow flow = getOrderFlowByGoods(goods_id,service_type);
				//nextFlowDef = this.getFirstFlowDef(flow.getFlow_id());
				if(flow!=null)nextFlowDef = getOrderFlowDef(order.getStatus(), flow.getFlow_id(),service_type);
			}else if(!StringUtil.isEmpty(next_flow_def_id)){
				//指定下一环节
				nextFlowDef = this.getOrderFlowDef(next_flow_def_id);
			}else if(currToDo!=null){
				//按流程找到下一环节
				nextFlowDef = this.getNextOrderFlowDef(currToDo.getFlow_def_id());
			}else{
				//如果没有开始流程按订单状态查询下一环节
				if(StringUtil.isEmpty(goods_id)){
					List itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
					if(itemList!=null && itemList.size()>0){
						Map map = (Map) itemList.get(0);
						goods_id = (String) map.get("goods_id");
					}
				}
				OrderFlow flow = getOrderFlowByGoods(goods_id,service_type);
				//按订单状态找到当前环节作为下一环节
				if(flow!=null)nextFlowDef = getOrderFlowDef(order.getStatus(), flow.getFlow_id(),service_type);
			}
			if(nextFlowDef!=null){
				//如果有下一环节就增加todolist
				OrderGroup og = this.getFlowUserGroup(nextFlowDef.getFlow_type());
				OrderToDoList todo = new OrderToDoList();
				todo.setCreate_time("sysdate");
				todo.setFlow_def_id(nextFlowDef.getFlow_def_id());
				todo.setFlow_status(0);
				//todo.setFlow_user_id(oper_id);
				todo.setFlow_group_id(og.getGroup_id());
				todo.setOrder_id(order_id);
				todo.setFlow_type(nextFlowDef.getFlow_type());
				this.insertOrderToDoList(todo);
			}
			if(currToDo!=null){
				//如果不是增流程侧攸 改当前流程状态
				this.updateOrderToDoListStatus(oper_id, oper_name, oper_type, flag_status,message, currToDo.getList_id());
			}
			
			return OrderFlowConst.SUCCESS;
		}catch(Exception ex){
			return OrderFlowConst.FAIL;
		}
		
	}
	
	@Override
	public int createOrderTodo(String order_id,String goods_id,String next_flow_def_id,String message,int flag_status,String service_type,String create_type){
		return this.createOrderTodo(order_id, goods_id, next_flow_def_id, message, flag_status, null, service_type, create_type);
	}

	/**
	public int createOrderToDo(String order_id, String goods_id, String [] flow_user_id,String [] flow_group_id,
			String next_flow_def_id,String message,OrderToDoList perv_todo,int flag_status,String service_type,String create_type) throws RuntimeException{
		try{
			Order order = orderManager.get(order_id);
			String group_id = null;
			if(flow_group_id!=null && flow_group_id.length>0){
				group_id = flow_group_id[0];
			}
			AdminUser user = ManagerUtils.getAdminUser();
			String oper_id = "";
			String oper_name = "";
			int oper_type = 0;
			if(user!=null){
				oper_id = user.getUserid();
				oper_name = user.getUsername();
			}else{
				Member member  = UserServiceFactory.getUserService().getCurrentMember();
				oper_id = member.getMember_id();
				oper_name = member.getUname();
				oper_type = 1;
			}
			
			if("create".equals(create_type)){//|| "dispatch".equals(create_type)
				OrderFlow flow = getOrderFlowByGoods(goods_id,service_type);
				if(flow==null)return 0;
				OrderFlowDef flowDef = null;
				if(flow_user_id==null && flow_group_id==null){
					//获取第一个环节
					flowDef = this.getFirstFlowDef(flow.getFlow_id());
					String user_id = null;
					OrderGroup og = this.getFlowUserGroup(flowDef.getFlow_type());
					if(og==null){
						user_id = OrderFlowConst.getCommonAcceptUserId();
					}else{
						group_id = og.getGroup_id();
					}
					OrderToDoList todo = new OrderToDoList();
					todo.setCreate_time("sysdate");
					todo.setFlow_def_id(flowDef.getFlow_def_id());
					todo.setFlow_status(0);
					todo.setFlow_user_id(user_id);
					todo.setFlow_group_id(group_id);
					todo.setOrder_id(order_id);
					todo.setFlow_type(flowDef.getFlow_type());
					//todo.setFlow_type(OrderFlowConst.ORDER_TODO_LIST_DISPATCH);
					//todo.setHint(message);
					this.insertOrderToDoList(todo);
				}else{
					if(!StringUtil.isEmpty(next_flow_def_id)){
						flowDef = this.getOrderFlowDef(next_flow_def_id);
					}else{
						flowDef = getOrderFlowDef(order.getStatus(), flow.getFlow_id());
					}
					if(flowDef==null)return 0;
					if((flow_group_id!=null && flow_group_id.length>0) || (flow_user_id!=null && flow_user_id.length>0)){
						if(flow_user_id!=null && flow_user_id.length>0){
							for(int i=0;i<flow_user_id.length;i++){
								OrderToDoList todo = new OrderToDoList();
								todo.setCreate_time("sysdate");
								todo.setFlow_def_id(flowDef.getFlow_def_id());
								todo.setFlow_status(0);
								todo.setFlow_user_id(flow_user_id[i]);
								todo.setOrder_id(order_id);
								todo.setFlow_group_id(group_id);
								todo.setFlow_type(flowDef.getFlow_type());
								//todo.setHint(message);
								if(!this.checkToDoListExists(order_id, group_id, flow_user_id[i], flowDef.getFlow_def_id(), -1))
									this.insertOrderToDoList(todo);
							}
						}else{
							OrderToDoList todo = new OrderToDoList();
							todo.setCreate_time("sysdate");
							todo.setFlow_def_id(flowDef.getFlow_def_id());
							todo.setFlow_status(0);
							todo.setFlow_user_id(null);
							todo.setOrder_id(order_id);
							todo.setFlow_group_id(group_id);
							todo.setFlow_type(flowDef.getFlow_type());
							//todo.setHint(message);
							if(!this.checkToDoListExists(order_id, group_id, null, flowDef.getFlow_def_id(), -1))
								this.insertOrderToDoList(todo);
						}
					}
				}
			}else if("rib".equals(create_type)){
				int count = this.countOrderToDoList(order_id,perv_todo.getFlow_def_id());
				if(count>1)return OrderFlowConst.CAN_NOT_RIB;//不能撤消处理
				OrderGroup og = this.getFlowUserGroup(OrderFlowConst.FLOW_DEF_TYPE_ROB);
				OrderFlow of = this.getOrderFlowByDefId(perv_todo.getFlow_def_id());
				if(of==null)return OrderFlowConst.CAN_NOT_RIB;
				if(!OrderFlowConst.FLOW_SERVICE_TYPE_BUY.equals(of.getService_type()))return OrderFlowConst.CAN_NOT_RIB;
				//如果是抢单侧按订单状态决定下一环节
				OrderFlowDef currflowDef = this.getFirstFlowDef(of.getFlow_id());
				OrderToDoList todo = new OrderToDoList();
				todo.setCreate_time("sysdate");
				todo.setFlow_def_id(currflowDef.getFlow_def_id());
				todo.setFlow_status(0);
				todo.setFlow_user_id(null);
				todo.setOrder_id(order_id);
				todo.setFlow_group_id(og.getGroup_id());
				todo.setFlow_type(OrderFlowConst.FLOW_DEF_TYPE_ROB);
				todo.setHint("抢单");
				todo.setPrev_user_id(user.getUserid());
				this.insertOrderToDoList(todo);
				//flag_status 状态为4是撤消
				this.updateOrderToDoListStatus(oper_id, oper_name, oper_type, flag_status,"抢单", perv_todo.getList_id());
			}else if("next".equals(create_type)){
				OrderFlowDef nextflowDef = null;
				if((flow_group_id!=null && flow_group_id.length>0) || (flow_user_id!=null && flow_user_id.length>0)){
					if(!StringUtils.isEmpty(next_flow_def_id)){
						nextflowDef = this.getOrderFlowDef(next_flow_def_id);
					}else{
						OrderFlow of = null;
						if(OrderFlowConst.FLOW_DEF_TYPE_ROB.equals(perv_todo.getFlow_type())){
							of = this.getOrderFlowByDefId(perv_todo.getFlow_def_id());
						}
						//如果是抢单侧按订单状态决定下一环节
						if(of!=null){
							nextflowDef = getOrderFlowDef(order.getStatus(), of.getFlow_id());
						}else{
							nextflowDef = this.getNextOrderFlowDef(perv_todo.getFlow_def_id());
						}
					}
				}
				int count = this.countOrderToDoList(order_id,perv_todo.getFlow_def_id());
				int status = 0;
				if(count>1){
					status = -1;
				}
				
				if(flag_status==1){
					if(nextflowDef!=null){
						if((flow_group_id!=null && flow_group_id.length>0) || (flow_user_id!=null && flow_user_id.length>0)){
							if(flow_user_id!=null && flow_user_id.length>0){
								for(int i=0;i<flow_user_id.length;i++){
									OrderToDoList todo = new OrderToDoList();
									todo.setCreate_time("sysdate");
									todo.setFlow_def_id(nextflowDef.getFlow_def_id());
									todo.setFlow_status(status);
									todo.setFlow_user_id(flow_user_id[i]);
									todo.setOrder_id(order_id);
									todo.setFlow_group_id(group_id);
									todo.setFlow_type(nextflowDef.getFlow_type());
									//todo.setHint(message);
									todo.setPrev_user_id(user.getUserid());
									if(!this.checkToDoListExists(order_id, group_id, flow_user_id[i], nextflowDef.getFlow_def_id(), -1))
										this.insertOrderToDoList(todo);
								}
							}else{
								OrderToDoList todo = new OrderToDoList();
								todo.setCreate_time("sysdate");
								todo.setFlow_def_id(nextflowDef.getFlow_def_id());
								todo.setFlow_status(status);
								todo.setFlow_user_id(null);
								todo.setOrder_id(order_id);
								todo.setFlow_group_id(group_id);
								todo.setFlow_type(nextflowDef.getFlow_type());
								//todo.setHint(message);
								todo.setPrev_user_id(user.getUserid());
								if(!this.checkToDoListExists(order_id, group_id, null, nextflowDef.getFlow_def_id(), -1))
									this.insertOrderToDoList(todo);
							}
						}
						if(count<2){
							//最后一个处理人处理完订单后修改下一个预转流程的状态为可处理  
							String usql = "update es_order_todo_list t set t.flow_status=0 where t.order_id=? and t.flow_status=-1 and t.flow_def_id=?";
							this.baseDaoSupport.execute(usql, order_id,nextflowDef.getFlow_def_id());
						}
					}
					updateToDoListStatus(flag_status,message, perv_todo.getList_id());
					if(OrderFlowConst.FLOW_DEF_TYPE_ROB.equals(perv_todo.getFlow_type())){
						//抢完单后把其它的
						updateCurrToDoListStatus(order_id, 3, 0,perv_todo.getFlow_def_id());
					}
				}else{
					updateCurrToDoListStatus(order_id, 3, 0,null);
					updateToDoListStatus(flag_status,message, perv_todo.getList_id());
				}
				this.updateOrderToDoListStatus(oper_id, oper_name, oper_type, flag_status,message, perv_todo.getList_id());
				if(count>1)return 1;
			}
			return 0;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException("处理失败");
		}
		
	}
	*/
	
	private void updateCurrToDoListStatus(String order_id,int to_flow_status,int curr_flow_status,String flow_def_id){
		String sql = "update es_order_todo_list t set t.flow_status=? where t.order_id=? and t.flow_status=? ";
		if(!StringUtils.isEmpty(flow_def_id))
			sql += " and t.flow_def_id='"+flow_def_id+"'";
		this.baseDaoSupport.execute(sql, 3,order_id,0);
	}
	
	private void updateToDoListStatus(int status,String message,String list_id){
		String usql2 = "update es_order_todo_list t set t.flow_status=?,t.hint=? where t.list_id=?";
		this.baseDaoSupport.execute(usql2, status,message,list_id);
	}
	
	@Override
	public void updateOrderTodoListByOrderIdAndFlowType(String order_id,String flow_type,int status){
		String sql = SF.orderSql("UpdateToDoListByOrderIDAndFlowType");
		this.baseDaoSupport.execute(sql, status,order_id,flow_type);
	}
	
	@Override
	public OrderFlow getOrderFlowByDefId(String flow_def_id){
		String sql = SF.orderSql("OrderFlowByDefId");
		List<OrderFlow> list = this.baseDaoSupport.queryForList(sql, OrderFlow.class, flow_def_id,ManagerUtils.getSourceFrom());
		return list.size()>0?list.get(0):null;
	}
	
	@Override
	public List<OrderToDoList> listOrderToDoListByOrderId(String order_id){
		String sql = SF.orderSql("ListOrderFlowToDoList");
		return this.baseDaoSupport.queryForList(sql, OrderToDoList.class, order_id,ManagerUtils.getSourceFrom());
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String openAccount(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint) throws RuntimeException{
		try{
			//AdminUser user = ManagerUtils.getAdminUser();
			Order order = this.orderManager.get(order_id);
			OrderFlowDef flowDef = this.getOrderFlowDef(flow_def_id);
			/*List itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
			Map map = (Map) itemList.get(0);
			String goods_id = (String) map.get("goods_id");*/
			//OrderToDoList toDo = this.queryUserOrderToDoList(order_id, user.getUserid());
			//int next_status = this.next(order_id, flow_user_id, flow_group_id, flow_def_id, flag_status, hint, toDo,OrderFlowConst.FLOW_SERVICE_TYPE_BUY);
			int next_status = this.createOrderTodo(order_id, null, flow_def_id, hint, flag_status, OrderFlowConst.FLOW_SERVICE_TYPE_BUY, "next");
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
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeDroolsException("处理失败");
		}
		return "{result:1,message:'成功'}";
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String paySuccess(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint) throws RuntimeDroolsException{
		try{
			//AdminUser user = ManagerUtils.getAdminUser();
			Order order = orderManager.get(order_id);
			//List itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
			//Map map = (Map) itemList.get(0);
			//String goods_id = (String) map.get("goods_id");
			//OrderToDoList toDo = this.queryUserOrderToDoList(order_id, user.getUserid());
			//int next_status = this.createOrderToDo(order_id, goods_id, flow_user_id, flow_group_id, flow_def_id,hint,toDo,flag_status,OrderFlowConst.FLOW_SERVICE_TYPE_BUY, "next");
			int next_status = this.createOrderTodo(order_id, null, flow_def_id, hint, flag_status, OrderFlowConst.FLOW_SERVICE_TYPE_BUY, "next");
			OrderFlowDef flowDef = this.getOrderFlowDef(flow_def_id);
			int status = OrderFlowConst.getOrderStatus(flowDef.getFlow_type());
			if(status!=-9999 && status!=order.getStatus()){
				OrderStatusEditReq req = new OrderStatusEditReq();
				req.setOrder_id(order_id);
				req.setOrder_status(status+"");
				orderServices.editOrderStatus(req);
			}
			return "{result:1,message:'成功'}";
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeDroolsException("处理失败");
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String dispatch(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint) throws RuntimeDroolsException{
		try{
			//AdminUser user = ManagerUtils.getAdminUser();
			Order order = orderManager.get(order_id);
			//List itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
			OrderFlowDef flowDef = this.getOrderFlowDef(flow_def_id);
			//Map map = (Map) itemList.get(0);
			//String goods_id = (String) map.get("goods_id");
			//OrderToDoList toDo = this.queryUserOrderToDoList(order_id, user.getUserid());
			//int next_status = this.createOrderToDo(order_id, goods_id, flow_user_id, flow_group_id, flow_def_id,hint,toDo,flag_status,OrderFlowConst.FLOW_SERVICE_TYPE_BUY, "next");
			int next_status = this.createOrderTodo(order_id, null, flow_def_id, hint, flag_status,flow_user_id[0], OrderFlowConst.FLOW_SERVICE_TYPE_BUY, OrderFlowConst.ORDER_TODO_LIST_DISPATCH);
			if(next_status==0 && flag_status==1){
				int status = OrderFlowConst.getOrderStatus(flowDef.getFlow_type());
				if(status!=-9999 && status!=order.getStatus()){
					OrderStatusEditReq req = new OrderStatusEditReq();
					req.setOrder_id(order_id);
					req.setOrder_status(status+"");
					orderServices.editOrderStatus(req);
				}
			}
			return "{result:1,message:'成功'}";
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeDroolsException("处理失败");
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String stokingOrder(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint) throws RuntimeDroolsException{
		try{
			AdminUser user = ManagerUtils.getAdminUser();
			Order order = orderManager.get(order_id);
			//List itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
			OrderFlowDef flowDef = this.getOrderFlowDef(flow_def_id);
			//Map map = (Map) itemList.get(0);
			//String goods_id = (String) map.get("goods_id");
			//OrderToDoList toDo = this.queryUserOrderToDoList(order_id, user.getUserid());
			//int next_status = this.createOrderToDo(order_id, goods_id, flow_user_id, flow_group_id, flow_def_id,hint,toDo,flag_status,OrderFlowConst.FLOW_SERVICE_TYPE_BUY, "next");
			int next_status = this.createOrderTodo(order_id, null, flow_def_id, hint, flag_status, OrderFlowConst.FLOW_SERVICE_TYPE_BUY, "next");
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
						throw new RuntimeDroolsException(resp.getError_msg());
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
			return "{result:1,message:'成功'}";
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeDroolsException("处理失败");
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String shipOrder(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint,Delivery delivery,String logi_id_name,ShipRequest shipRequest,OrderFlow flow) throws RuntimeDroolsException{
		try{
			String json="";
			AdminUser user = ManagerUtils.getAdminUser();
			/*List itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
			Map map = (Map) itemList.get(0);
			String goodsid = (String) map.get("goods_id");*/
			//OrderToDoList toDo = this.queryUserOrderToDoList(order_id, user.getUserid());
			//int next_status = this.next(order_id, flow_user_id, flow_group_id, flow_def_id, flag_status, hint, toDo,OrderFlowConst.FLOW_SERVICE_TYPE_BUY);
			int next_status = this.createOrderTodo(order_id, null, flow_def_id, hint, flag_status, OrderFlowConst.FLOW_SERVICE_TYPE_BUY, "next");
			if(next_status!=0 || flag_status!=1){
				json = "{result:1,message:'成功'}";
				return json;
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
				json = "{result:0,message:'找不到发货单'}";
				return json;
			}
			
			Order order = orderManager.get(order_id);
			String goods_idArray [] =shipRequest.getGoods_idArray();
			String goods_nameArray [] =shipRequest.getGoods_nameArray();
			String goods_snArray [] =shipRequest.getGoods_snArray();
			String product_idArray [] =shipRequest.getProduct_idArray();
			Integer numArray [] =shipRequest.getNumArray();
			String giftidArray []= shipRequest.getGiftidArray();
			String giftnameArray []= shipRequest.getGiftnameArray();
			Integer giftnumArray []= shipRequest.getGiftnumArray();
			
			
			String [] itemids = shipRequest.getItemid_idArray();
			
			List<DeliveryItem> ditemList  = new ArrayList<DeliveryItem>();
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
				ditemList.add(item);
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
			req.setDeliveryItems(ditemList);
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
			OrderFlowDef flowDef = this.getOrderFlowDef(flow_def_id);
			int status = OrderFlowConst.getOrderStatus(flowDef.getFlow_type());
			if(status!=-9999 && status!=order.getStatus()){
				OrderStatusEditReq sreq = new OrderStatusEditReq();
				sreq.setOrder_id(order_id);
				sreq.setOrder_status(status+"");
				orderServices.editOrderStatus(sreq);
			}
			OrderShippingRuleReq sreq = new OrderShippingRuleReq();
			sreq.setOrder(order);
			RuleInvoker.afOrderShipping(sreq);
			return json;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeDroolsException("处理失败["+ex.getMessage()+"]");
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String received(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint)throws RuntimeDroolsException{
		try{
			AdminUser user = ManagerUtils.getAdminUser();
			//List itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
			//Map map = (Map) itemList.get(0);
			//String goodsid = (String) map.get("goods_id");
			//OrderToDoList toDo = this.queryUserOrderToDoList(order_id, user.getUserid());
			//int next_status = this.next(order_id, flow_user_id, flow_group_id, flow_def_id, flag_status, hint, toDo,OrderFlowConst.FLOW_SERVICE_TYPE_BUY);
			int next_status = this.createOrderTodo(order_id, null, flow_def_id, hint, flag_status, OrderFlowConst.FLOW_SERVICE_TYPE_BUY, "next");
			if(next_status!=0 || flag_status!=1){
				return "{result:1,message:'成功'}";
			}
			OrderConfirmReq req = new OrderConfirmReq();
			req.setOrder_id(order_id);
			req.setConfirm_userid(user.getUserid());
			req.setConfirm_username(user.getUsername());
			OrderConfirmResp resp = orderServices.confirmOrder(req);
			OrderFlowDef flowDef = this.getOrderFlowDef(flow_def_id);
			int status = OrderFlowConst.getOrderStatus(flowDef.getFlow_type());
			Order order = orderManager.get(order_id);
			if(status!=-9999 && status!=order.getStatus()){
				OrderStatusEditReq sreq = new OrderStatusEditReq();
				sreq.setOrder_id(order_id);
				sreq.setOrder_status(status+"");
				orderServices.editOrderStatus(sreq);
			}
			return "{result:1,message:'成功'}";
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeDroolsException("处理失败");
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String finish(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint)throws RuntimeDroolsException{
		try{
			AdminUser user = ManagerUtils.getAdminUser();
			//List itemList = orderManager.listGoodsItems(order_id); // 订单商品列表
			//Map map = (Map) itemList.get(0);
			//String goodsid = (String) map.get("goods_id");
			//OrderToDoList toDo = this.queryUserOrderToDoList(order_id, user.getUserid());
			//int next_status = this.next(order_id, null, null, flow_def_id, flag_status, hint, toDo,OrderFlowConst.FLOW_SERVICE_TYPE_BUY);
			int next_status = this.createOrderTodo(order_id, null, flow_def_id, hint, flag_status, OrderFlowConst.FLOW_SERVICE_TYPE_BUY, "next");
			if(next_status!=0 || flag_status!=1){
				return "{result:1,message:'成功'}";
			}
			//已支付可备货
			orderManager.updateOrderStatus(OrderStatus.ORDER_COMPLETE, order_id);
			//===写日志===
			orderManager.log(order_id, hint, user.getUserid(), user.getUsername());
			Order order = orderManager.get(order_id);
			OrderFinishRuleReq req = new OrderFinishRuleReq();
			req.setOrder(order);
			try {
				RuleInvoker.afOrderFinish(req);
			} catch (ApiBusiException e) {
				e.printStackTrace();
			}
			return "{result:1,message:'成功'}";
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeDroolsException("处理失败");
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String returned(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint,Delivery delivery,String logi_id_name,String btn_action,
			String [] goods_idArray,String []goods_nameArray,Integer[] numArray,String [] product_idArray,String [] itemid_idArray,String [] giftidArray,String [] goods_snArray,String [] order_applyArray)throws RuntimeDroolsException{
		try{
			String orderId=order_id;
			String json = "";
			//AdminUser user = ManagerUtils.getAdminUser();
			//OrderToDoList toDo = this.queryUserOrderToDoList(orderId, user.getUserid());
			//int next_status = this.next(orderId, flow_user_id, flow_group_id, flow_def_id, flag_status, hint, toDo,OrderFlowConst.FLOW_SERVICE_TYPE_RETURNED);
			int next_status = this.createOrderTodo(order_id, null, flow_def_id, hint, flag_status, OrderFlowConst.FLOW_SERVICE_TYPE_RETURNED, "next");
			if(next_status!=0 || flag_status!=1){
				json = "{result:1,message:'订单["+orderId+"]处理成功'}";
				return json;
			}
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
			json="{result:1,message:'订单["+order.getSn()+"]处理成功',shipStatus:"+order.getShip_status()+"}";
			return json;
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeDroolsException("处理失败");
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String cancel_pay_c(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint,OrderAudit orderAudit,PaymentLog payment)throws RuntimeDroolsException{
		try{
			String orderId=order_id;
			AdminUser user = ManagerUtils.getAdminUser();
			//OrderToDoList toDo = this.queryUserOrderToDoList(orderId, user.getUserid());
			//OrderFlow flow = this.getOrderFlowByDefId(toDo.getFlow_def_id());
			//int next_status = this.next(orderId, null, null, flow_def_id, flag_status, hint, toDo,flow.getService_type());
			
			//退款toDo不可能为null
			int next_status = this.createOrderTodo(order_id, null, null, hint, flag_status, null, "next");
			if(next_status!=0 || flag_status!=1){
				return "{result:1,message:'成功'}";
			}
			Order order = this.orderManager.get(orderId);
			OrderRequst orderRequst = new OrderRequst();
			orderRequst.setService_name(OrderBuilder.COMMONAGE);
			orderRequst.setFlow_name(OrderBuilder.PAYMENT);
			orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_REFUND);
			OrderAuditRequest orderAuditRequest = new OrderAuditRequest();
			if(orderAudit ==null)
				 orderAudit = new OrderAudit();
			orderAuditRequest.setApply_message(orderAudit.getApply_message());
			orderAuditRequest.setP_audit_message(orderAudit.getP_audit_message());
			orderAuditRequest.setP_audit_state(orderAudit.getP_audit_state());
			orderAuditRequest.setAudit_type(OrderStatus.AUDIT_TYPE_00B);
			orderRequst.setOrderAuditRequest(orderAuditRequest);
			orderDirector.getOrderBuilder().buildGoodsRefundFeeFlow();
			orderRequst.setPayment(payment);
			orderRequst.setOrderId(orderId);
			
			orderDirector.perform(orderRequst);
		
			return "{result:1,message:'订单["+order.getSn()+"]处理成功',payStatus:"+order.getPay_status()+"}";
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeDroolsException("处理失败");
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String exchange(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint,String logi_id_name,Delivery delivery,
			String [] goods_idArray,String [] goods_nameArray,Integer [] numArray,String [] product_idArray,String [] itemid_idArray,String [] giftidArray,String [] goods_snArray,String [] order_applyArray,String btn_action)throws RuntimeDroolsException{
		try{
			String orderId = order_id;
			//AdminUser user = ManagerUtils.getAdminUser();
			//OrderToDoList toDo = this.queryUserOrderToDoList(orderId, user.getUserid());
			//int next_status = this.next(orderId, flow_user_id, flow_group_id, flow_def_id, flag_status, hint, toDo,OrderFlowConst.FLOW_SERVICE_TYPE_EXCHANGE);
			int next_status = this.createOrderTodo(order_id, null, flow_def_id, hint, flag_status, OrderFlowConst.FLOW_SERVICE_TYPE_EXCHANGE, "next");
			if(next_status!=0 || flag_status!=1){
				return "{result:1,message:'订单["+orderId+"]处理成功'}";
			}
			
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
			return "{result:1,message:'订单["+order.getSn()+"]处理成功',shipStatus:"+order.getShip_status()+"}";
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeDroolsException("处理失败");
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String robOrder(String order_id) throws RuntimeDroolsException{
		//AdminUser user = ManagerUtils.getAdminUser();
		//OrderToDoList toDo = this.queryUserOrderToDoList(order_id, user.getUserid());
		//String [] flow_user_id = new String[]{user.getUserid()};
		//int next_status = this.next(order_id, flow_user_id, null, null, 1, "抢单", toDo,OrderFlowConst.FLOW_SERVICE_TYPE_BUY);
		int next_status = this.createOrderTodo(order_id, null, null, "锁定订单", 1, null, OrderFlowConst.FLOW_DEF_TYPE_ROB);
		if(next_status==OrderFlowConst.CANT_NOT_LOCK){
			return "{result:0,message:'该订单已被锁定，不能抢单'}";
		}
		return "{result:1,message:'成功'}";
	}
	
	@Override
	public String qyOrder(String order_id,String flow_user_id[],String flow_group_id[],String flow_def_id,int flag_status,String hint){
		Order order = this.orderManager.get(order_id);
		//AdminUser user = ManagerUtils.getAdminUser();
		//toDo = orderFlowBussManager.queryUserOrderToDoList(order_id, user.getUserid());
		//int next_status = orderFlowBussManager.next(order_id, flow_user_id, flow_group_id, flow_def_id, flag_status, hint, toDo,OrderFlowConst.FLOW_SERVICE_TYPE_BUY);
		int next_status = this.createOrderTodo(order_id, null, flow_def_id, hint, flag_status, OrderFlowConst.FLOW_SERVICE_TYPE_BUY, "next");
		OrderFlowDef flowDef = this.getOrderFlowDef(flow_def_id);
		if(next_status==0 && flag_status==1){
			int status = OrderFlowConst.getOrderStatus(flowDef.getFlow_type());
			if(status!=-9999 && status!=order.getStatus()){
				OrderStatusEditReq req = new OrderStatusEditReq();
				req.setOrder_id(order_id);
				req.setOrder_status(status+"");
				orderServices.editOrderStatus(req);
			}else{
				orderManager.updateOrderStatus(OrderStatus.ORDER_NOT_PAY, order_id);
			}
		}
		return "{result:1,message:'成功'}";
	}
	
	@Override
	public List<OrderReturnVisitType> listOrderReturVisitType(int kind){
		String sql = SF.orderSql("ListOrderReturVisitType");
		return this.baseDaoSupport.queryForList(sql, OrderReturnVisitType.class,kind);
	}
	
	@Override
	public void addOrderReturnVisit(OrderReturnVisit visit){
		this.baseDaoSupport.insert("es_order_return_visit", visit);
	}
	
	@Override
	public List<OrderReturnVisit> listOrderReturnVisit(String order_id,String todo_id){
		String sql = SF.orderSql("ListOrderReturnVisit");
		List param = new ArrayList();
		param.add(order_id);
		param.add(ManagerUtils.getSourceFrom());
		if(!StringUtil.isEmpty(todo_id)){
			sql += " and t.todo_list_id=? ";
		}
		sql += " order by t.create_time ";
		return this.baseDaoSupport.queryForList(sql, OrderReturnVisit.class, param.toArray());
	}
	
	@Override
	public OrderGroup getFlowUserGroup(String flow_type){
		String sql = SF.orderSql("QueryFlowUserGroupByFlowType");
		List<OrderGroup> list = this.baseDaoSupport.queryForList(sql, OrderGroup.class, flow_type,ManagerUtils.getSourceFrom());
		return list.size()>0?list.get(0):null;
	}

	@Override
	public List<OrderToDoList> getOrderTodoListByFowStatus(String order_id,int status){
		String sql = SF.orderSql("OrderTodoListByFowStatus");
		return this.baseDaoSupport.queryForList(sql, OrderToDoList.class, order_id,status);
	}
	
	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public IOrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public DeliveryInf getDeliveryServ() {
		return deliveryServ;
	}

	public void setDeliveryServ(DeliveryInf deliveryServ) {
		this.deliveryServ = deliveryServ;
	}

	public IDeliveryManager getDeliveryManager() {
		return deliveryManager;
	}

	public void setDeliveryManager(IDeliveryManager deliveryManager) {
		this.deliveryManager = deliveryManager;
	}

	public DeliveryFlow getDeliveryFlow() {
		return deliveryFlow;
	}

	public void setDeliveryFlow(DeliveryFlow deliveryFlow) {
		this.deliveryFlow = deliveryFlow;
	}

	public IDeliveryFlow getDeliveryFlowManager() {
		return deliveryFlowManager;
	}

	public void setDeliveryFlowManager(IDeliveryFlow deliveryFlowManager) {
		this.deliveryFlowManager = deliveryFlowManager;
	}

	public IOrderDirector getOrderDirector() {
		return orderDirector;
	}

	public void setOrderDirector(IOrderDirector orderDirector) {
		this.orderDirector = orderDirector;
	}
	
	
}