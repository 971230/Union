package com.ztesoft.net.mall.core.action.backend;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderComments;
import com.ztesoft.net.mall.core.model.OrderExcepCollect;
import com.ztesoft.net.mall.core.model.OrderUncomments;
import com.ztesoft.net.mall.core.service.IOrderCommentManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import org.apache.struts2.ServletActionContext;

import zte.net.iservice.IOrderServices;
import zte.params.order.req.OrderExceptionReq;
import zte.params.order.req.OrderStatusEditReq;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 订单备注action
 * @作者 MoChunrun
 * @创建日期 2013-10-17 
 * @版本 V 1.0
 */
public class OrderCommentsAction extends WWAction {

	private String order_id;
	private List<OrderComments> commentList;
	private OrderComments ordComments;
	private List uncommentList;
	private OrderUncomments unComments;
	private IOrderCommentManager orderCommentManager;
	private IOrderManager orderManager;
	private List exceptionList;
	private OrderExcepCollect orderExcepCollect;
	private IOrderServices orderServices;
	
	public String list(){
		commentList = orderCommentManager.qryOrderComments(order_id);
		return "list";
	}
	
	public String listUnComments(){
		uncommentList = orderCommentManager.listUnComments(order_id);
		return "unlist";
	}
	
	public String showExceptionDialog(){
		this.exceptionList = this.orderManager.listException();
		
		return "exceptionDialog";
	}
	
	/**
	 * 订单异常
	 * @作者 MoChunrun
	 * @创建日期 2013-10-18 
	 * @return
	 */
	public String orderException(){
		try {
    		OrderStatusEditReq orderStatusEditReq = new OrderStatusEditReq();
    		orderStatusEditReq.setOrder_id(orderExcepCollect.getOrder_id());
    		orderStatusEditReq.setOrder_status(OrderStatus.ORDER_EXCEPTION);
    		this.orderServices.editOrderStatus(orderStatusEditReq);
    		
    		OrderExceptionReq orderExceptionReq = new OrderExceptionReq();
    		orderExceptionReq.setOrderExcepCollect(orderExcepCollect);
			this.orderServices.saveOrderFail(orderExceptionReq);
			
			
			 this.json = "{result:1,message:'操作成功'}";
		} catch (Exception e) {
			this.json = "{result:0,message:'操作失败,"+e.getMessage()+"'}";
		}
    	
    	
    	return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 显示取消订单
	 * @作者 MoChunrun
	 * @创建日期 2013-10-21 
	 * @return
	 */
	public String showCancelDialog(){
		return "cancelDialog";
	}
	
	public String cancelOrder(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		String json = null;
		try{
			Order order = orderManager.get(order_id);
			if(order==null){
				json = "{'result':'0','message':'没找到订单'}";
			}else if(order.getStatus()==OrderStatus.ORDER_COLLECT || order.getStatus()==OrderStatus.ORDER_COLLECT_REFUSE || order.getStatus()==OrderStatus.ORDER_NOT_PAY){
				orderCommentManager.updateConfirmStatus(OrderStatus.ORDER_CONFIRM_CANCEL,OrderStatus.ORDER_CONFIRM_STATUS_CANCEL_ALL, order_id);
				ordComments.setOrder_id(order_id);
				ordComments.setOper_time(DBTUtil.current());
				AdminUser au = ManagerUtils.getAdminUser();
				ordComments.setOper_id(au.getUserid());
				ordComments.setOpet_name(au.getUsername());
				orderCommentManager.add(ordComments);
				json = "{'result':'1','message':'成功'}";
			}else{
				json = "{'result':'0','message':'订单不允许取消'}";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			json = "{'result':'0','message':'失败'}";
		}
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}


	public List<OrderComments> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<OrderComments> commentList) {
		this.commentList = commentList;
	}

	public IOrderCommentManager getOrderCommentManager() {
		return orderCommentManager;
	}

	public void setOrderCommentManager(IOrderCommentManager orderCommentManager) {
		this.orderCommentManager = orderCommentManager;
	}

	public OrderComments getOrdComments() {
		return ordComments;
	}

	public void setOrdComments(OrderComments ordComments) {
		this.ordComments = ordComments;
	}

	public List<OrderUncomments> getUncommentList() {
		return uncommentList;
	}

	public void setUncommentList(List<OrderUncomments> uncommentList) {
		this.uncommentList = uncommentList;
	}

	public OrderUncomments getUnComments() {
		return unComments;
	}

	public void setUnComments(OrderUncomments unComments) {
		this.unComments = unComments;
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public List getExceptionList() {
		return exceptionList;
	}

	public void setExceptionList(List exceptionList) {
		this.exceptionList = exceptionList;
	}

	public OrderExcepCollect getOrderExcepCollect() {
		return orderExcepCollect;
	}

	public void setOrderExcepCollect(OrderExcepCollect orderExcepCollect) {
		this.orderExcepCollect = orderExcepCollect;
	}

	public IOrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}
	
}
