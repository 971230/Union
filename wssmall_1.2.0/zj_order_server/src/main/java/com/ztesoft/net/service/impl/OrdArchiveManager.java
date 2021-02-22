package com.ztesoft.net.service.impl;

import java.util.List;
import java.util.Map;

import org.drools.core.util.StringUtils;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.CBSSBusiHandleVO;
import com.ztesoft.net.model.CBSSDealRequest; 
import com.ztesoft.net.service.IOrdArchiveManager; 
 
public class OrdArchiveManager extends BaseSupport implements IOrdArchiveManager {    
 
	@Override
	public List<Map<String,String>> ordArchiveList() { 
//		String sql = "select a.order_id order_id from es_order a left join es_order_ext b on a.order_id=b.order_id where a.source_from=? and  ((b.back_time<sysdate-nvl(b.archive_time,0) and a.status = " + EcsOrderConsts.DIC_ORDER_STATUS_10+")";
//		sql += " or b.order_if_cancel = '1') ";
		//积压订单日期，未配置则默认为下单60天未生产的订单
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String overstockTime = cacheUtil.getConfigInfo("OVERSTOCK_ORDER_ARCHIVE_TIME"); 
		if(StringUtils.isEmpty(overstockTime)){
			overstockTime = "60"; 
		}
		//试运行自动归档时间段
		//String testArchiveTime = cacheUtil.getConfigInfo("ARCHIVE_TRY_OUT_TIMES"); 
		String sql = "select order_id,order_if_cancel,archive_type from (" +  
						// 根据配置，回单多少天后归档(正常单、非退单)
						"select b.order_id,nvl(b.order_if_cancel,'0') order_if_cancel,'"+EcsOrderConsts.ARCHIVE_TYPE_1+"' archive_type from es_order_ext b where b.source_from = ? and (b.refund_deal_type is null or b.refund_deal_type='01') and b.back_time < sysdate - nvl(b.archive_time, 0) and b.flow_trace_id = '"+EcsOrderConsts.DIC_ORDER_NODE_J+"' and not exists (select 1 from es_order_sp_product eosp where eosp.order_id = b.order_id and eosp.source_from = b.source_from and eosp.status in ('"+EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0+"', '"+EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1+"')) and not exists (select 1 from es_attr_package_subprod eaps where eaps.order_id = b.order_id and eaps.source_from = b.source_from and eaps.status in ('"+EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0+"', '"+EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1+"')) and not exists (select 1 from es_archive_fail r where r.order_id = b.order_id) and (b.is_archive='"+EcsOrderConsts.NO_DEFAULT_VALUE+"' or b.is_archive is null) and rownum <= 200 " +
						" union " +
						// 作废订单归档
						"select b.order_id,nvl(b.order_if_cancel,'0') order_if_cancel,'"+EcsOrderConsts.ARCHIVE_TYPE_3+"' archive_type from es_order_ext b where b.source_from = ? and b.order_if_cancel = '1' and not exists (select 1 from es_archive_fail r where r.order_id = b.order_id) and (b.is_archive='"+EcsOrderConsts.NO_DEFAULT_VALUE+"' or b.is_archive is null) and rownum <= 50 " +
						" union " + 
						// 没有回单环节到归档环节的订单归档(正常单、非退单)
						"select b.order_id,nvl(b.order_if_cancel,'0') order_if_cancel,'"+EcsOrderConsts.ARCHIVE_TYPE_2+"' archive_type from es_order_ext b where b.source_from = ? and (b.refund_deal_type is null or b.refund_deal_type='01') and b.flow_trace_id = '"+EcsOrderConsts.DIC_ORDER_NODE_J+"' and b.back_time is null and not exists (select 1 from es_order_sp_product eosp where eosp.order_id = b.order_id and eosp.source_from = b.source_from and eosp.status in ('"+EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0+"', '"+EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1+"')) and not exists (select 1 from es_attr_package_subprod eaps where eaps.order_id = b.order_id and eaps.source_from = b.source_from and eaps.status in ('"+EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_0+"', '"+EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_1+"')) and not exists (select 1 from es_archive_fail r where r.order_id = b.order_id) and (b.is_archive='"+EcsOrderConsts.NO_DEFAULT_VALUE+"' or b.is_archive is null) and rownum <= 200 " + 
						" union " + 
						//积压订单归档
						" select b.order_id, nvl(b.order_if_cancel, '0') order_if_cancel,'"+EcsOrderConsts.ARCHIVE_TYPE_4+"' archive_type from es_order_ext b where b.tid_time <= sysdate - " + overstockTime + " and (b.recover_time is null or b.recover_time <= sysdate - " + overstockTime + ") and b.source_from = ? and b.flow_trace_id != '"+EcsOrderConsts.DIC_ORDER_NODE_J+"'  and not exists (select 1 from es_archive_fail r where r.order_id = b.order_id) and (b.is_archive='"+EcsOrderConsts.NO_DEFAULT_VALUE+"' or b.is_archive is null) and rownum <= 50" +
					 ") where rownum <= 500";
		
		List<Map<String,String>> orders = baseDaoSupport.queryForList(sql, Consts.ECS_SOURCE_FROM, Consts.ECS_SOURCE_FROM, Consts.ECS_SOURCE_FROM, Consts.ECS_SOURCE_FROM);
		return orders;
	}

	@Override
	public List<Map<String,String>> ordDataArchiveList() {
		String sql = "select order_id from es_order_ext b where b.is_archive = ? and b.source_from = ? and rownum <= 400";
		List<Map<String,String>> orders = baseDaoSupport.queryForList(sql, EcsOrderConsts.IS_DEFAULT_VALUE, Consts.ECS_SOURCE_FROM);
		return orders;
	}
	
	@Override
	public List<CBSSDealRequest> getGiftListByBssStatus(String order_id){
		String sql = "select a.gift_inst_id,a.order_id,a.inst_id,a.goods_name,a.bss_status from ES_ATTR_GIFT_INFO a where rownum<20 and a.bss_status = 1 and a.order_id='"+order_id+"'";
		List<CBSSDealRequest> giftList = baseDaoSupport.queryForList(sql,CBSSDealRequest.class);
		return giftList;
	}
	
	@Override
	public Boolean isAllCBSSDeal(String orderId){
		String sql = "select count(1) from ES_ATTR_GIFT_INFO a where a.order_id="+orderId+" and a.bss_status=1";
		int count = baseDaoSupport.queryForInt(sql);
		if(count==0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public List<CBSSBusiHandleVO> getOrderByCbss() {
		// TODO Auto-generated method stub
		String sourceFrom = ManagerUtils.getSourceFrom();
		StringBuilder sql = new StringBuilder("select t.order_id from (select distinct a.order_id,a.source_from from es_order_ext a where a.source_from='"+sourceFrom+"' and nvl(a.refund_deal_type,'01') <> '02' ");
				sql.append(" and a.order_id in (select b.order_id from es_attr_gift_info b where b.source_from='"+sourceFrom+"' and b.bss_status='1' and (b.goods_name like '%省内闲时流量%' or b.goods_name like '%WO%视频%'))");
				sql.append(" and a.order_id in (select order_id from es_order_items_ext c where c.source_from='"+sourceFrom+"' and c.bss_status = '0') ) t where t.source_from='"+sourceFrom+"' and rownum<11");
		List<CBSSBusiHandleVO> list  = baseDaoSupport.queryForList(sql.toString(), CBSSBusiHandleVO.class);
		return list;
	}
	
	public List<Map<String,String>> getRefreshOrders(){
		String sql = "select order_id from es_order_extvtl where is_refresh = 1 and rownum < 16";
		List<Map<String,String>> orders = baseDaoSupport.queryForList(sql);
		return orders;
	}
	
	/**
	 * 插入归档失败订单信息
	 * @param order_id
	 */
	public void insertArchiveFailLog(String order_id , String message){
		try{
			String sql = "insert into es_archive_fail (order_id,error_message) values (?,?)";
			baseDaoSupport.execute(sql, order_id,message);
		}catch(Exception e){
			logger.info(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 */
	public List<Map<String, Object>> getCancelOrder(String goods_id) {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String order_from = cacheUtil.getConfigInfo("CANCEL_ORDER_FROM");
		String order_days = cacheUtil.getConfigInfo("CANCEL_ORDER_DAYS");
		int order_days2 = StringUtil.toInt(order_days, true) + 1;
		String intent_days = cacheUtil.getConfigInfo("CANCEL_INTENT_DAYS");
		String goods_type = cacheUtil.getConfigInfo("CANCEL_GOODS_TYPE");
		
		String sql = "select eoe.order_id                 " +
					"  from es_order_ext eoe             " +
					"  left join es_order eo             " +
					"    on eoe.order_id = eo.order_id   " +
					"  left join es_order_items_ext eoie " +
					"    on eoe.order_id = eoie.order_id " +
					"  left join es_order_intent eoi     " +
					"    on eoe.order_id = eoi.order_id  " +
					" where eoe.order_from in (" + order_from + ") " +
					"   and eo.pay_status = '0'          " +
					"   and sysdate - eo.create_time > " + order_days+
					"   and sysdate - eo.create_time <= " + order_days2 +
					"   and eoe.abnormal_type <> '4'     " +
					"   and eo.goods_id not in (" + goods_id + ") " +
					"   and eoie.goods_type not in (" + goods_type + ")  " +
					"   and eo.source_from = '" + ManagerUtils.getSourceFrom() + "' " +
					"   and eoi.order_id is null         " +
					"UNION                               " +
					"select eoe.order_id                 " +
					"  from es_order_ext eoe             " +
					"  left join es_order eo             " +
					"    on eoe.order_id = eo.order_id   " +
					"  left join es_order_items_ext eoie " +
					"    on eoe.order_id = eoie.order_id " +
					"  left join es_order_intent eoi     " +
					"    on eoe.order_id = eoi.order_id  " +
					" where eoe.order_from in (" + order_from + ") " +
					"   and eo.pay_status = '0'          " +
					"   and sysdate - eo.create_time > " + intent_days +
					"   and eoe.abnormal_type <> '4'     " +
					"   and eo.goods_id not in (" + goods_id + ") " +
					"   and eoie.goods_type not in (" + goods_type + ") " +
					"   and eo.source_from = '" + ManagerUtils.getSourceFrom() + "' " +
					"   and eoi.order_id is not null     ";
		
		return baseDaoSupport.queryForList(sql);
	}
}
