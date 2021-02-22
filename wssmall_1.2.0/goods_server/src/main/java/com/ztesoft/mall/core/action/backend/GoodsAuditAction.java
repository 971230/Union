package com.ztesoft.mall.core.action.backend;

import services.AdminUserInf;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.GoodsApply;
import com.ztesoft.net.mall.core.model.GoodsAudit;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;

/**
 * 商品审核
 * 
 * @author wui
 * 
 */
public class GoodsAuditAction extends WWAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderId;
	private GoodsApply goodsApply;
	private GoodsAudit goodsAudit;
	private GoodsTypeDTO goodsType;
	//private Order order;
	//private IOrderDirector orderDirector;
	
	private String apply_desc;
	private String apply_time;
	//private OrderInf orderServ;
	private AdminUserInf adminUserServ;
	
	

	
	
	/**
	 * 显示商品申请审核界面
	 * @return
	 */
	public String showGoodsApplyAuditDialog() {
		/*OrderReq orderReq = new OrderReq();
		orderReq.setOrder_id(orderId);
		Order order = orderServ.get(orderReq).getOrder();
		AdminUserReq adminUserReq = new AdminUserReq();
		adminUserReq.setUser_id(order.getUserid());
		AdminUserResp adminUserResp = adminUserServ.getAdminUserById(adminUserReq);
	    AdminUser adminUser = new AdminUser();
		if(adminUserResp != null){
			adminUser = adminUserResp.getAdminUser();
		}
		order.setUser_name(adminUser.getRealname());*/
		
		//add by wui暂时先屏蔽
		//List logList = this.orderManager.listLogs(orderId);
		//Map logP =((Map)logList.get(0));
		
	//	apply_desc  = (String)logP.get("message");
		return "goods_audit_dialog";
	}
	
	//商品审核
	public String audit(){
		try {
			
			if(StringUtil.isEmpty(goodsAudit.getAudit_state()))
			{
				this.json = "{result:1,message:'处理状态不能为空!'}";
				return WWAction.JSON_MESSAGE;
			}
			goodsAudit();
			this.json = "{result:1,message:'订单[" +orderId+ "]处理成功'}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"商品申请失败：" + e.getMessage() + "\"}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	
	/**
	 * 
	 * 商品审核流程 触发条件：前台商品审核时触发
	 */
	private void goodsAudit() {
		/*OrderReq orderReq = new OrderReq();
		orderReq.setOrder_id(orderId);
		Order order = orderServ.get(orderReq).getOrder();
		orderDirector.getOrderBuilder().buildOrderFlow();
		OrderRequst orderRequst = new OrderRequst();
		orderRequst.setService_name(order.getType_code());
		orderRequst.setFlow_name(OrderBuilder.AUDIT);
		orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_ORDER);
		orderRequst.setGoodsAudit(goodsAudit);
		orderRequst.setOrderId(orderId);
		orderDirector.perform(orderRequst);*/

	}
	


	public GoodsApply getGoodsApply() {
		return goodsApply;
	}

	public void setGoodsApply(GoodsApply goodsApply) {
		this.goodsApply = goodsApply;
	}

	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public GoodsAudit getGoodsAudit() {
		return goodsAudit;
	}

	public void setGoodsAudit(GoodsAudit goodsAudit) {
		this.goodsAudit = goodsAudit;
	}

	public GoodsTypeDTO getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(GoodsTypeDTO goodsType) {
		this.goodsType = goodsType;
	}

	

	/*public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}



	public IOrderDirector getOrderDirector() {
		return orderDirector;
	}

	public void setOrderDirector(IOrderDirector orderDirector) {
		this.orderDirector = orderDirector;
	}*/

	public String getApply_desc() {
		return apply_desc;
	}

	public void setApply_desc(String apply_desc) {
		this.apply_desc = apply_desc;
	}

	public String getApply_time() {
		return apply_time;
	}

	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}

	/*public OrderInf getOrderServ() {
		return orderServ;
	}

	public void setOrderServ(OrderInf orderServ) {
		this.orderServ = orderServ;
	}*/

	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}

	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}
	
	

}
