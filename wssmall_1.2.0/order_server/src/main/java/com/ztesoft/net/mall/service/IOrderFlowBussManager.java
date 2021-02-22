package com.ztesoft.net.mall.service;

import java.util.List;

import org.drools.RuntimeDroolsException;

import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.OrderAudit;
import com.ztesoft.net.mall.core.model.PaymentLog;
import com.ztesoft.net.mall.core.model.ShipRequest;
import com.ztesoft.net.model.OrderFlow;
import com.ztesoft.net.model.OrderFlowDef;
import com.ztesoft.net.model.OrderGroup;
import com.ztesoft.net.model.OrderReturnVisit;
import com.ztesoft.net.model.OrderReturnVisitType;
import com.ztesoft.net.model.OrderToDoList;

public interface IOrderFlowBussManager {

	/**
	 * 按类型查询流程 
	 * @作者 MoChunrun
	 * @创建日期 2014-6-10 
	 * @param object_id
	 * @param flow_type common为通用流程优先级别3 goodstype为商品类型优先级别为2 goods为商品优先级别为1
	 * @return
	 */
	public OrderFlow queryOrderFlow(String object_id,String flow_type,String service_type);
	
	/**
	 * 按商品ID查询流程环节
	 * @作者 MoChunrun
	 * @创建日期 2014-6-10 
	 * @param goods_id
	 * @return
	 */
	public OrderFlow getOrderFlowByGoods(String goods_id,String service_type);
	
	/**
	 * 根据订单状态查询下一环节流程
	 * @作者 MoChunrun
	 * @创建日期 2014-6-10 
	 * @param order_status
	 * @param flow_id
	 * @return
	 */
	public OrderFlowDef getOrderFlowDef(int order_status,String flow_id,String service_type);
	
	/**
	 * 获取下一环节流程
	 * @作者 MoChunrun
	 * @创建日期 2014-6-10 
	 * @param flow_def_id
	 * @return
	 */
	public OrderFlowDef getNextOrderFlowDef(String flow_def_id);
	
	public OrderFlowDef getOrderFlowDef(String flow_def_id);
	
	/**
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-7-2 
	 * @param flow_id
	 * @param isNotDo 是否排除已处理的环节 ture排除 如果为true侧order_id不能为空
	 * @return
	 */
	public List<OrderFlowDef> listOrderFlowDef(String flow_id,boolean isNotDo,String order_id);
	
	/**
	 * 插入订单待办事项表
	 * @作者 MoChunrun
	 * @创建日期 2014-6-10 
	 * @param toDoList
	 */
	public void insertOrderToDoList(OrderToDoList toDoList);
	
	/**
	 * 修改待办事项状态
	 * @作者 MoChunrun
	 * @创建日期 2014-6-10 
	 * @param oper_id
	 * @param oper_name
	 * @param status
	 * @param prev_user_id
	 * @param list_id
	 */
	public void updateOrderToDoListStatus(String oper_id,String oper_name,int oper_type,int status,String hint,String list_id);
	
	/**
	 * 查询用户订单待处理任务
	 * @作者 MoChunrun
	 * @创建日期 2014-6-10 
	 * @param order_id
	 * @param user_id
	 * @param event 0可处理的 1可见的
	 * @return
	 */
	public OrderToDoList queryUserOrderToDoList(String order_id,String user_id,int event);
	
	/**
	 * 查询订单待处理流程
	 * @作者 MoChunrun
	 * @创建日期 2014-6-10 
	 * @param order_id
	 * @param status
	 * @return
	 */
	public int countOrderToDoList(String order_id,String flow_def_id);
	
	/**
	 * 创建订单待办事项 返回值0可处理 1不可处理
	 * @作者 MoChunrun
	 * @创建日期 2014-6-10 
	 * @param order_id
	 * @param order_status
	 * @param create_type 新建create、转到下一还节next、分派dispatch
	 * 
	 */
	//public int createOrderToDo(String order_id,String goods_id,String [] flow_user_id,String [] flow_group_id,String next_flow_def_id,String message,OrderToDoList perv_todo,int flag_status,String service_type,String create_type)throws RuntimeException;
	public int createOrderTodo(String order_id,String goods_id,String next_flow_def_id,String message,int flag_status,String service_type,String create_type);
	public int createOrderTodo(String order_id,String goods_id,String next_flow_def_id,String message,int flag_status,String flow_user_id,String service_type,String create_type);
	
	/*public int create(String order_id, String goods_id, String [] flow_user_id,String [] flow_group_id,String service_type);
	public int create(String order_id,String [] flow_user_id,String [] flow_group_id,String next_flow_def_id,String service_type);
	public int next(String order_id,String [] flow_user_id,String [] flow_group_id,String next_flow_def_id,int flag_status,String message,OrderToDoList perv_todo,String service_type);
	public int ribTodo(String order_id,OrderToDoList perv_todo);*/
	
	public void updateOrderTodoListByOrderIdAndFlowType(String order_id,String flow_type,int status);
	
	public OrderFlow getOrderFlowByDefId(String flow_def_id);
	
	/**
	 * 按用订单ID查询订单流程处理日志
	 * @作者 MoChunrun
	 * @创建日期 2014-6-17 
	 * @param order_id
	 * @return
	 */
	public List<OrderToDoList> listOrderToDoListByOrderId(String order_id);
	
	public String openAccount(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint)throws RuntimeDroolsException;
	
	public String paySuccess(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint) throws RuntimeDroolsException;
	
	public String dispatch(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint) throws RuntimeDroolsException;
	
	public String stokingOrder(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint) throws RuntimeDroolsException;
	
	public String shipOrder(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint,Delivery delivery,String logi_id_name,ShipRequest shipRequest,OrderFlow flow) throws RuntimeDroolsException;
	
	public String received(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint)throws RuntimeDroolsException;
	
	public String finish(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint)throws RuntimeDroolsException;
	
	public String returned(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint,Delivery delivery,String logi_id_name,String btn_action,
			String [] goods_idArray,String []goods_nameArray,Integer[] numArray,String [] product_idArray,String [] itemid_idArray,String [] giftidArray,String [] goods_snArray,String [] order_applyArray)throws RuntimeDroolsException;
	
	public String cancel_pay_c(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint,OrderAudit orderAudit,PaymentLog payment)throws RuntimeDroolsException;
	
	public String exchange(String order_id,String flow_def_id,String [] flow_user_id,String [] flow_group_id,int flag_status,String hint,String logi_id_name,Delivery delivery,
			String [] goods_idArray,String [] goods_nameArray,Integer [] numArray,String [] product_idArray,String [] itemid_idArray,String [] giftidArray,String [] goods_snArray,String [] order_applyArray,String btn_action)throws RuntimeDroolsException;
	
	public String qyOrder(String order_id,String flow_user_id[],String flow_group_id[],String flow_def_id,int flag_status,String hint);
	/**
	 * @作者 MoChunrun
	 * @创建日期 2014-7-3 
	 * @param kind 0回访类型 1回访方法
	 * @return
	 */
	public List<OrderReturnVisitType> listOrderReturVisitType(int kind);
	
	public void addOrderReturnVisit(OrderReturnVisit visit);
	
	public List<OrderReturnVisit> listOrderReturnVisit(String order_id,String todo_id);
	
	/**
	 * 抢单
	 * @作者 MoChunrun
	 * @创建日期 2014-7-1 
	 * @param order_id
	 * @param flow_def_id
	 * @param flow_user_id
	 * @param flow_group_id
	 * @param flag_status
	 * @param hint
	 * @return
	 * @throws RuntimeDroolsException
	 */
	public String robOrder(String order_id) throws RuntimeDroolsException;
	
	/**
	 * 按流程类型查询组
	 * @作者 MoChunrun
	 * @创建日期 2014-7-2 
	 * @param flow_type
	 * @return
	 */
	public OrderGroup getFlowUserGroup(String flow_type);
	
	public OrderFlow getOrderFlowByTodoListId(String todo_id);
	
	public List<OrderToDoList> getOrderTodoListByFowStatus(String order_id,int status);
}
