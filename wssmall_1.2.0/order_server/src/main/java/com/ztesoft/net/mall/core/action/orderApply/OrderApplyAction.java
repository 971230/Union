package com.ztesoft.net.mall.core.action.orderApply;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.OrderApply;
import com.ztesoft.net.mall.core.service.IOrderApplyManage;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class OrderApplyAction extends WWAction {
	private IOrderApplyManage orderApplyManage;
	private IOrderManager orderManager;
	private String service_type;
	private String order_apply_id;
	private String a_order_item_id;
	private AdminUser adminUser;
	private int isAdminUser;
	private OrderApply orderApply;
	private String apply_state;

	public String list(){
		adminUser=ManagerUtils.getAdminUser();
		if(ManagerUtils.isAdminUser()){
			isAdminUser=1;
		}
		this.webpage=orderApplyManage.listOrderApply(service_type,order_apply_id, a_order_item_id, this.getPage(), this.getPageSize());
		return "list";
	}
	
	public String getTails(){
		orderApply=orderApplyManage.getOrderApply(order_apply_id);
		return "getTails";
	}
	
	public String updateOrderApplyState(){
		try {
			orderApply=orderApplyManage.getOrderApply(order_apply_id);
			orderApply.setApply_state(apply_state);
			orderApplyManage.updateOrderApply(orderApply);
			String msg = "";
			if(OrderStatus.ORDER_APPLY_STATUS_1.equals(apply_state)){
				msg = "退货申请审核通过,等待退款。";
			}else{
				msg = "退货申请审核不通过。";
			}
			AdminUser au = ManagerUtils.getAdminUser();
			orderManager.log(orderApply.getA_order_item_id(), msg, au.getUserid(), au.getUsername());
			this.json = "{'result':1,'message':'操作成功'}";
		} catch (Exception e) {
			this.json = "{'result':0,'message':'操作失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public IOrderApplyManage getOrderApplyManage() {
		return orderApplyManage;
	}

	public void setOrderApplyManage(IOrderApplyManage orderApplyManage) {
		this.orderApplyManage = orderApplyManage;
	}

	public String getOrder_apply_id() {
		return order_apply_id;
	}

	public void setOrder_apply_id(String order_apply_id) {
		this.order_apply_id = order_apply_id;
	}

	public String getA_order_item_id() {
		return a_order_item_id;
	}

	public void setA_order_item_id(String a_order_item_id) {
		this.a_order_item_id = a_order_item_id;
	}

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public int getIsAdminUser() {
		return isAdminUser;
	}

	public void setIsAdminUser(int isAdminUser) {
		this.isAdminUser = isAdminUser;
	}

	public OrderApply getOrderApply() {
		return orderApply;
	}

	public void setOrderApply(OrderApply orderApply) {
		this.orderApply = orderApply;
	}

	public String getApply_state() {
		return apply_state;
	}

	public void setApply_state(String apply_state) {
		this.apply_state = apply_state;
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}

	
	
	

}
