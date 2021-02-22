package com.ztesoft.net.mall.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.ReturnsOrder;
import com.ztesoft.net.mall.core.service.IOrderFlowManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IReturnsOrderManager;
import com.ztesoft.net.sqls.SF;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 退货管理
 * @author kingapex
 *
 */
public class ReturnsOrderManager extends BaseSupport<ReturnsOrder> implements IReturnsOrderManager {

	
	private IOrderFlowManager orderFlowManager ;
	private IOrderManager orderManager;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(ReturnsOrder returnsOrder,Integer[] specids) {
		
		Member member = UserServiceFactory.getUserService().getCurrentMember();
		this.orderFlowManager.applyReturns(returnsOrder.getOrderid(),returnsOrder.getState(), specids);
		
		returnsOrder.setMemberid(member.getMember_id());
		returnsOrder.setState(0);
		returnsOrder.setAdd_time(DBTUtil.current());
		this.baseDaoSupport.insert("returns_order", returnsOrder);
	}

	
	@Override
	public void delete(Integer id) {
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_RETURN_ORDER_DELETE"), id);
	}

	
	@Override
	public ReturnsOrder get(String id) {
		ReturnsOrder order =baseDaoSupport.queryForObject(SF.orderSql("SERVICE_RETURN_ORDER_SELECT"), ReturnsOrder.class, id);
		
		List itemList  = orderManager.listGoodsItems(order.getOrderid());
		order.setItemList(itemList);
		return order;
	}

	
	@Override
	public Page listAll(int pageNo, int pageSize) {
		return this.baseDaoSupport.queryForPage(SF.orderSql("SERVICE_RETURN_ORDER_SELECT2"),pageNo, pageSize,  ReturnsOrder.class);
	}

	
	@Override
	public List<ReturnsOrder> listMemberOrder() {
		Member member  = UserServiceFactory.getUserService().getCurrentMember();
		
		return this.baseDaoSupport.queryForList(SF.orderSql("SERVICE_RETURN_ORDER_MEM"), ReturnsOrder.class, member.getMember_id());
	}

	
	@Override
	public void updateState(String id, int state) {
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_RETURN_ORDER_UPDATE"), state,id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.enation.mall.core.service.IReturnsOrderManager#refuse(java.lang.Integer, java.lang.String)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void refuse(String id, String reson) {
		ReturnsOrder order  =this.get(id);
		orderFlowManager.refuseRorC(order.getOrderid());
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_RETURN_ORDER_REFUSE"), 2,reson,id);
	}
	
	
	


	public IOrderFlowManager getOrderFlowManager() {
		return orderFlowManager;
	}


	public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
		this.orderFlowManager = orderFlowManager;
	}


	public IOrderManager getOrderManager() {
		return orderManager;
	}


	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}


}
