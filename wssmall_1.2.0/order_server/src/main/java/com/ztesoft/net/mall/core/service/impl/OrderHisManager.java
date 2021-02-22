package com.ztesoft.net.mall.core.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderQueryParam;
import com.ztesoft.net.mall.core.service.IOrderHisManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SF;

public class OrderHisManager extends BaseSupport implements IOrderHisManager {

	@Override
	public String getCurrentTime() {
		String ctime = DBTUtil.getDBCurrentTime();
		return ctime;
	}

	@Override
	public void syncOrderHis(String endTime) {
		String sql = SF.orderSql("SYNC_ORDER_HIS");
		this.baseDaoSupport.execute(sql, endTime);
	}

	@Override
	public void syncOrderItemsHis(String endTime) {
		String sql = SF.orderSql("SYNC_ORDER_ITEMS_HIS");
		this.baseDaoSupport.execute(sql, endTime);
	}

	@Override
	public void syncOrderAttrInstHis(String endTime) {
		String sql = SF.orderSql("SYNC_ORDER_ATTR_INST_HIS");
		this.baseDaoSupport.execute(sql, endTime);
	}

	@Override
	public void syncDeliveryHis(String endTime) {
		String sql = SF.orderSql("SYNC_ORDER_DELIVERY_HIS");
		this.baseDaoSupport.execute(sql, endTime);
	}

	@Override
	public void syncDeliveryItemHis(String endTime) {
		String sql = SF.orderSql("SYNC_ORDER_DELIVERY_ITEM_HIS");
		this.baseDaoSupport.execute(sql, endTime);
	}
	
	@Override
	public void syncPaymentLogs(String endTime){
		
	}
	
	@Override
	public void deletePaymentLogs(String endTime){
		
	}

	@Override
	public void deleteOrders(String endTime) {
		String sql = SF.orderSql("DELETE_SYNC_ORDER");
		this.baseDaoSupport.execute(sql, endTime);
	}

	@Override
	public void deleteOrderItems(String endTime) {
		String sql = SF.orderSql("DELETE_SYNC_ORDER_ITEMS");
		this.baseDaoSupport.execute(sql, endTime);
	}

	@Override
	public void deleteDelivery(String endTime) {
		String sql = SF.orderSql("DELETE_SYNC_ORDER_DELIVERY");
		this.baseDaoSupport.execute(sql, endTime);
	}

	@Override
	public void deleteDeliveryItem(String endTime) {
		String sql = SF.orderSql("DELETE_SYNC_ORDER_DELIVERY_ITEM");
		this.baseDaoSupport.execute(sql, endTime);
	}

	@Override
	public void deleteOrderAttrInst(String endTime) {
		String sql = SF.orderSql("DELETE_SYNC_ORDER_ATTR_INST");
		this.baseDaoSupport.execute(sql, endTime);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void syncAllOrderHistory() {
		try{
			String endTime = this.getCurrentTime();
			this.syncOrderHis(endTime);
			this.syncOrderItemsHis(endTime);
			this.syncDeliveryHis(endTime);
			this.syncDeliveryItemHis(endTime);
			this.syncOrderAttrInstHis(endTime);
			this.deleteOrderAttrInst(endTime);
			this.deleteDeliveryItem(endTime);
			this.deleteDelivery(endTime);
			this.deleteOrderItems(endTime);
			this.deleteOrders(endTime);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException("同步出错");
		}
	}
	
	@Override
	public Page queryHisOrder(int pageNO, int pageSize,int disabled, OrderQueryParam ordParam, String order){
		List params = new ArrayList();
		AdminUser au = ManagerUtils.getAdminUser();
		String userid = au.getUserid();
		StringBuffer sql = new StringBuffer(SF.orderSql("QueryHistoryOrder"));
		params.add(ManagerUtils.getSourceFrom());
		//params.add(ordParam.getAction());
		params.add(userid);
		params.add(userid);
		params.add(userid);
		if(StringUtils.isEmpty(ordParam.getAction()) || "all".equals(ordParam.getAction()) || "day".equals(ordParam.getAction()) || "exception".equals(ordParam.getAction())){
			if("day".equals(ordParam.getAction())){
				//今天所有订单
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String now = df.format(new Date());
				sql.append(" and t.create_time >= "+DBTUtil.to_date(2));
				params.add(now+" 00:00:00");
				sql.append(" and t.create_time <= "+DBTUtil.to_date(2));
				params.add(now+" 23:59:59");
			}else if("exception".equals(ordParam.getAction())){
				sql.append(" and t.status = "+OrderStatus.ORDER_EXCEPTION+"");
			}
		}else{
			sql.append(" and l.flow_type=? and l.flow_status=0");
			params.add(ordParam.getAction());
		}
		assembleNewOrdSql(ordParam, params, sql);
		String countSql = "select count(*) from ("+sql.toString()+") c";
		if("all".equals(ordParam.getAction()) || StringUtils.isEmpty(ordParam.getAction())){
			order = StringUtil.isEmpty(order)?" t.create_time desc":order;
		}else{
			order = StringUtil.isEmpty(order)?" t.create_time desc":order;
		}
		sql.append(" order by ").append(order);
		Page page  =this.baseDaoSupport.queryForCPage(sql.toString(), pageNO, pageSize,  Order.class, countSql,params.toArray());
		return page;
	}
	
	private void assembleNewOrdSql(OrderQueryParam ordParam, List params, StringBuffer sqls) {
		
		if(ordParam != null ){
			if(ordParam.getStartTime() != null && !"".equals(ordParam.getStartTime())){
				sqls.append(" and t.create_time >= "+DBTUtil.to_date(2));
				params.add(ordParam.getStartTime()+" 00:00:00");
			}
			if(ordParam.getEndTime() != null && !"".equals(ordParam.getEndTime())){
				sqls.append(" and t.create_time <= "+DBTUtil.to_date(2));
				params.add(ordParam.getEndTime()+" 23:59:59");
			}
			
			if(ordParam.getSourceFrom() != null && !"".equals(ordParam.getSourceFrom())){
				if(OrderStatus.SOURCE_FROM_TAOBAO.equals(ordParam.getSourceFrom())){
					sqls.append(" and t.source_from = ? ");
					params.add(ordParam.getSourceFrom());
				}else{
					sqls.append(" and t.source_from <> ? ");
					params.add(OrderStatus.SOURCE_FROM_TAOBAO);
				}
			}
			if(ordParam.getOrderName() != null && !"".equals(ordParam.getOrderName())){
				sqls.append(" and t.goods_id in (select g.goods_id from es_goods g  where upper(g.name) like '%"+ ordParam.getOrderName().trim().toUpperCase() +"%')");
//				params.add(ordParam.getOrderName());
			}
			
			if(ordParam.getOrderId() != null && !"".equals(ordParam.getOrderId())){
				sqls.append(" and t.order_id like '%"+ ordParam.getOrderId() +"%'");
			}
			
			/**
			 * 修改为前端用户
			 */
			/*if(ordParam.getUserid() != null && !"".equals(ordParam.getUserid())){
				sqls.append(" and t.userid = '"+ ordParam.getUserid() +"'");
			}*/
			if(ordParam.getUserName()!=null && !"".equals(ordParam.getUserName())){
				sqls.append(" and upper(t.ship_name) like '%"+ordParam.getUserName().trim().toUpperCase()+"%'");
			}
			
			if(!StringUtils.isEmpty(ordParam.getOrderstatus()) && !"-1".equals(ordParam.getOrderstatus())){
				sqls.append(" and t.status="+ordParam.getOrderstatus());
			}
			
			if(!StringUtils.isEmpty(ordParam.getPhoneNo())){
				sqls.append(" and t.ship_mobile='"+ordParam.getPhoneNo()+"'");
			}
			if(!StringUtils.isEmpty(ordParam.getDeliveryNo())){
				// TODO Auto-generated method stub
				//物流单号
			}
			if(!StringUtils.isEmpty(ordParam.getNetPhoneNo())){
				// TODO Auto-generated method stub
				//入网号
			}
			if(!StringUtils.isEmpty(ordParam.getTerminalNo())){
				// TODO Auto-generated method stub
				//终端串码
			}
			if(!StringUtils.isEmpty(ordParam.getDlyTypeId()) && !"-1".equals(ordParam.getDlyTypeId())){
				sqls.append(" and t.shipping_id='"+ordParam.getDlyTypeId()+"'");
			}
			if(!StringUtils.isEmpty(ordParam.getPayment_id()) && !"-1".equals(ordParam.getPayment_id())){
				sqls.append(" and t.payment_id='"+ordParam.getPayment_id()+"'");
			}
		}
	}
	
	@Override
	public Order getHisOrder(String order_id){
		String sql  = SF.orderSql("HIS_SERVICE_ORDER_SELECT_BY_ID")+" and source_from=? ";
		String source_from = ManagerUtils.getSourceFrom();
		List<Order> list = this.baseDaoSupport.queryForList(sql,Order.class, order_id.trim(),source_from.trim());
		return list.size()>0?list.get(0):null;
	}
	
	@Override
	public List<Map> listHisGoodsItems(String order_id){
		String sql = SF.orderSql("HIS_SERVICE_GOODS_ITEMS_SELECT");
		String order_from = ManagerUtils.getSourceFrom();
		List<Map> itemList = this.daoSupport.queryForList(sql, new RowMapper(){
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map map = new HashMap();
				map.put("item_id", rs.getString("item_id"));
				map.put("order_id", rs.getString("order_id"));
				map.put("goods_id", rs.getString("goods_id"));
				map.put("spec_id", rs.getString("spec_id"));
				map.put("num", rs.getInt("num"));
				int ship_nun = rs.getInt("ship_num");
				map.put("ship_num", ship_nun);
				map.put("name", rs.getString("name"));
				map.put("price", rs.getObject("price"));
				map.put("gainedpoint", rs.getInt("gainedpoint"));
				map.put("addon", rs.getString("addon"));	
				map.put("coupon_price", rs.getDouble("coupon_price"));
				String returndnum = rs.getString("returndnum");
				map.put("returndnum", returndnum);
				String image_default = rs.getString("image");
				if(image_default!=null){
					image_default  =UploadUtil.replacePath(image_default);
				}
				map.put("image", image_default);
				map.put("sn", rs.getString("sn"));
				int can_return = ship_nun;
				if(!StringUtils.isEmpty(returndnum)){
					can_return = can_return - Integer.parseInt(returndnum);
				}
				map.put("can_return", can_return);
				return map;
			}
			
		}, order_id,order_from);
		return itemList;
	}
	
	@Override
	public List listDelivery(String orderId, Integer type) {
		return this.baseDaoSupport.queryForList(SF.orderSql("HIS_SERVICE_DELIVERY_SELECT_BY_TYPE"), orderId, type);
	}
	
	@Override
	public List listPayLogs(String orderId, Integer type) {
		return this.baseDaoSupport.queryForList(SF.orderSql("HIS_SERVICE_PAY_LOGS_SELECT"), orderId, type,ManagerUtils.getSourceFrom()); //展示处理成功的记录
	}
	
}
