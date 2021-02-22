package com.ztesoft.net.mall.core.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.service.IMemberOrderManager;
import com.ztesoft.net.sqls.SF;


public class MemberOrderManager extends BaseSupport implements
		IMemberOrderManager {

	
	@Override
	public Page pageOrders(int pageNo, int pageSize) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		
		String sql = SF.memberSql("MEMBER_PAGE_ORDERS"); //and status<>'-10'
		Page rpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, member.getMember_id());
		List<Map> list = (rpage.getResult());
		return rpage;
	}

	@Override
	public Page pageOrders(int pageNo, int pageSize, String status) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		
		String sql = SF.memberSql("MEMBER_PAGE_ORDERS_STATUS");
		Page rpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, member.getMember_id(),status);
		List<Map> list = (rpage.getResult());
		return rpage;
	}	

	@Override
	public Page pageOrdersOngoning(int pageNo, int pageSize, String status) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		
		String sql = SF.memberSql("MEMBER_PAGEORDERS_ONGONING");
		Page rpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, member.getMember_id(),status);
		List<Map> list = (rpage.getResult());
		return rpage;
	}		
	
	@Override
	public int totalCount(){
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		String sql = SF.memberSql("MEMBER_ORDER_TOTALCOUNT");
		int	count = this.baseDaoSupport.queryForInt(sql, member.getMember_id()); 
		return count;		
	}
	
	@Override
	public int orderCount(String status){
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		String sql = SF.memberSql("MEMBER_ORDER_COUNT");
		int	count = this.baseDaoSupport.queryForInt(sql, member.getMember_id(),status); 
		return count;		
	}
	
	@Override
	public Order getOrder(int order_id) {
		String sql = SF.memberSql("MEMBER_GET_ORDER");
		Order order = (Order)this.baseDaoSupport.queryForObject(sql, Order.class, order_id);
		return order;
	}

	
	@Override
	public Delivery getOrderDelivery(String order_id) {
		String sql = SF.memberSql("MEMBER_GET_ORDER_DELIVERY");
		Delivery delivery = (Delivery)this.baseDaoSupport.queryForObject(sql, Delivery.class, order_id);
		return delivery;
	}
	
	
	@Override
	public List listOrderLog(String order_id) {
		String sql = SF.memberSql("MEMBER_LIST_ORDERLOG");
		List list = this.baseDaoSupport.queryForList(sql, order_id);
		list = list == null ? new ArrayList() : list;
		return list;
	}

	
	@Override
	public List listGoodsItems(String order_id) {
		String sql = SF.memberSql("MEMBER_LIST_GOODSITEMS");
		List list = this.baseDaoSupport.queryForList(sql, order_id);
		list = list == null ? new ArrayList() : list;
		return list;
	}

	
	@Override
	public List listGiftItems(String orderid) {
		String sql  = SF.memberSql("MEMBER_LIST_GIFTITEMS");
		return this.baseDaoSupport.queryForList(sql, orderid);
	}


	@Override
	public boolean isBuy(String goodsid) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		if(member==null) return false;
		String sql  = SF.memberSql("MEMBER_IS_BUY");
		int count  = this.daoSupport.queryForInt(sql, member.getMember_id(),goodsid);
		return count>0;
	}

}
