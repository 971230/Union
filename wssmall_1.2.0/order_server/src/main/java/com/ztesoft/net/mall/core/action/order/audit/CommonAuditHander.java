package com.ztesoft.net.mall.core.action.order.audit;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.mall.core.action.order.AbstractHander;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.GoodsUsage;
import com.ztesoft.net.mall.core.model.OrderAuditRequest;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 
 * 
 * @author wui
 * 商品审核
 *
 */
public  class CommonAuditHander extends AbstractHander implements IAuditHander {
	
	@Override
	public void audit() {
		
	}
	
	public boolean  canAudit(){
		if(getOrder().getStatus().equals(OrderStatus.ORDER_COLLECT)) 
			return true;
		return false;
	}

	
	@Override
	public void display() {
		
		
	}

	@Override
	public void execute() {
		
		GoodsUsage goodsUsage = new GoodsUsage();
		goodsUsage.setUserid(getOrder().getUserid());
		goodsUsage.setGoods_id(getOrder().getGoods_id());
		goodsUsage.setStock_num(getOrder().getGoods_num());
		goodsUsage.setState(Consts.GOODS_USAGE_STATE_0);
		orderUtils.insertGoodsUsage(goodsUsage);
		
		
		if(OrderStatus.ORDER_TYPE_2.equals(getOrder().getOrder_type())) //退费订单，且审核通过，直接生成退费处理按钮
		{
			OrderAuditRequest orderAuditRequest = new OrderAuditRequest();
			orderAuditRequest.setOrder_id(getOrder().getOrder_id());
			orderAuditRequest.setFrom_userid(getOrder().getUserid());
			orderAuditRequest.setCreate_date(DBTUtil.current());
			orderAuditRequest.setAudit_type(OrderStatus.AUDIT_TYPE_00B);
			if(isFirstPartnerOrder())
			{
				//一级分销商申请，直接修改状态为电信人员审核通过，生成退费按钮订单
				orderAuditRequest.setState(OrderStatus.ORDER_AUDIT_STATE_4); 
				orderAuditRequest.setTo_mgr_userid(ManagerUtils.getUserId());
				orderAuditRequest.setMgr_deal_message(getOrderRequst().getGoodsAudit().getAudit_desc());
			}else
			{	
				orderAuditRequest.setState(OrderStatus.ORDER_AUDIT_STATE_2); //二级分销商直接修改为一级分销商审核通过，待电信审核通过后直接退费
				orderAuditRequest.setTo_userid(ManagerUtils.getUserId());
				orderAuditRequest.setDeal_message(getOrderRequst().getGoodsAudit().getAudit_desc());
			}
			orderAuditRequest.setApply_message("申请退费审核通过");
			orderAuditRequest.setSequ(0);
			orderAuditManager.apply(orderAuditRequest);
			
			
		}
	}
	
	/**
	 * if(OrderStatus.ORDER_TYPE_2.equals(getOrder().getOrder_type())) //退费订单，且审核通过，直接生成退费处理按钮
		{
			
			OrderAuditRequest orderAuditRequest = new OrderAuditRequest();
			String state = getLastAuditState();
			if (OrderStatus.ORDER_AUDIT_STATE_0.equals(state)) // 一级分销商审核通过
			{
				orderAuditRequest.setState(OrderStatus.ORDER_AUDIT_STATE_2);
				orderAuditRequest.setTo_userid(ManagerUtils.getUserId());
				orderAuditRequest.setDeal_message(getOrderRequst().getGoodsAudit().getAudit_desc());
				
			} else if (OrderStatus.ORDER_AUDIT_STATE_2.equals(state)) {// 电信员工审核通过
				orderAuditRequest.setState(OrderStatus.ORDER_AUDIT_STATE_4);
				orderAuditRequest.setTo_mgr_userid(ManagerUtils.getUserId());
				orderAuditRequest.setMgr_deal_message(getOrderRequst().getGoodsAudit().getAudit_desc());
			}
			orderAuditRequest.setState_date(Consts.SYSDATE);
			orderAuditRequest.setOrder_id(getOrder().getOrder_id());
			orderAuditManager.audit(orderAuditRequest);
		}
		
	 */
	
	
	public void syOrderAutoAudit()
	{
		String orderId = getOrderRequst().getOrderId();
		orderNFlowManager.audit_through(orderId,"外系统订单缺省审核通过");
		
	}
	

	@Override
	public boolean isCanExecute() {
		return this.getOrderRequst().isCan_execute();
	}
}
