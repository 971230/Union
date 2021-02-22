package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import zte.net.common.rule.ZteCommonTraceRule;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.common.util.ListUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.CoQueue;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdArchiveTacheManager;
import com.ztesoft.net.service.IOrderGuijiManager;
import com.ztesoft.net.sqls.SF;

public class OrderGuijiManager extends BaseSupport implements IOrderGuijiManager {

	@Override
	public Page searchOrderCoQueue(Map params, int pageNo, int pageSize) {
		String co_id = Const.getStrValue(params, "co_id");
		String ext_order_id = Const.getStrValue(params, "ext_order_id");
		String service_code = Const.getStrValue(params, "service_code");
		String tb_name = Const.getStrValue(params, "tb_name");
		String sql ="";
		if(StringUtils.isEmpty(service_code)){
			service_code = Consts.SERVICE_CODE_CO_GUIJI_NEW;
		}
		if(StringUtils.isEmpty(tb_name))
		{
			tb_name=Consts.ES_CO_QUEUE;
		}
		List pList = new ArrayList();
		StringBuilder where = new StringBuilder();
		if(!StringUtils.isEmpty(service_code)){
			where.append("and a.service_code=? ");
			pList.add(service_code);
		}
		if(!StringUtils.isEmpty(ext_order_id)){
			where.append(" and b.old_sec_order_id=? ");
			pList.add(ext_order_id);
		}
		if(!StringUtils.isEmpty(co_id)){
			where.append(" and a.co_id=? ");
			pList.add(co_id);
		}
		
		if(tb_name.equals(Consts.ES_CO_QUEUE))
		{
			sql=SF.ecsordSql("ORDER_CO_QUEUE_BAK_LIST");
		}
		else
		{
			sql=SF.ecsordSql("ORDER_CO_QUEUE_BAK_LIST1");
		}
		
		where.append(" order by a.status_date desc ");
		Page page = this.baseDaoSupport.queryForPage(sql+where.toString(), pageNo, pageSize, pList.toArray());
		return page;
	}

	@Override
	public Page listOrderSynService(Map params, int pageNo, int pageSize) {
		List pList = new ArrayList();
		String sql = SF.ecsordSql("ORDER_SYN_EXE_TMPL_LIST");
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, pList.toArray());
		return page;
	}

	@Override
	public void stopOrderSyn(Map params) {
		String tmpl_id = Const.getStrValue(params, "tmpl_id");
		String sql = SF.ecsordSql("OUTER_EXECUTE_TMPL_STOP");
		
		if(!StringUtils.isEmpty(tmpl_id)){
			tmpl_id = Const.getInWhereCond(tmpl_id);
			sql += " and tmpl_id in("+tmpl_id+")";
			this.baseDaoSupport.execute(sql, EcsOrderConsts.RUN_STATUS_00X);
		}
		
	}

	@Override
	public void startOrderSyn(Map params) {
		String tmpl_id = Const.getStrValue(params, "tmpl_id");
		String sql = SF.ecsordSql("OUTER_EXECUTE_TMPL_START");
		
		if(!StringUtils.isEmpty(tmpl_id)){
			tmpl_id = Const.getInWhereCond(tmpl_id);
			sql += " and tmpl_id in("+tmpl_id+")";
			this.baseDaoSupport.execute(sql, EcsOrderConsts.RUN_STATUS_00A);
		}
		
	}

	@Override
	public Map getDataMoveLog() {
		String sql = SF.ecsordSql("CO_QUEUE_MOVE_LOG");
		Map log = this.baseDaoSupport.queryForMap(sql, "10001");
		this.updateDataMoveLog();
		return log;
	}
	
	public void updateDataMoveLog(){
		Map fields = new HashMap();
		fields.put("move_time", DBTUtil.getDBCurrentTime());
		String where = " log_id = '10001' ";
		this.baseDaoSupport.update("es_co_queue_move_log", fields, where);
	}

	@Override
	public void insertGuijiCoQueue(List<CoQueue> queues) {
		String sql = "select count(*) from es_co_queue where co_id=? ";
		for(CoQueue queue : queues){
			String count = this.baseDaoSupport.queryForString(sql, queue.getCo_id());
			if("0".equals(count)){
				queue.setService_code(Consts.SERVICE_CODE_CO_GUIJI_NEW);
				this.baseDaoSupport.insert("es_co_queue", queue);
			}
		}
	}
	@Resource
	private IEcsOrdManager ecsOrdManager;
	@Resource
	private IOrdArchiveTacheManager ordArchiveTacheManager;
	public String doGuiji(Map params) {
		String co_id = Const.getStrValue(params, "co_id");
		String tb_name = Consts.ES_CO_QUEUE;
		String out_tid =Const.getStrValue(params, "out_tid");
		String sql ="";
		String sql2 ="";
		if(StringUtils.isEmpty(tb_name))
		{
			tb_name=Consts.ES_CO_QUEUE;
		}
		logger.info("============================================================>"+tb_name);
//		deleteHis(co_id, tb_name,out_tid);
		sql = SF.ecsordSql("CO_QUEUE_GET").replaceAll("#table_name", tb_name);
		sql2 = SF.ecsordSql("CO_QUEUE_GET").replaceAll("#table_name", "es_co_queue_bak");
		ZteCommonTraceRule commonTraceRule = SpringContextHolder.getBean("zteCommonTraceRule");
		List<CoQueue> coQueues = this.baseDaoSupport.queryForList(sql, CoQueue.class, co_id);
		
		logger.info("============================================================>"+sql+":"+co_id);
		if(coQueues==null || coQueues.size()==0){
			coQueues = this.baseDaoSupport.queryForList(sql2, CoQueue.class, co_id);
		}
		CoQueue coQueue = (coQueues!=null && coQueues.size()>0) ? coQueues.get(0) : null;
		logger.info("============================================================>"+commonTraceRule);
		if(coQueue!=null){
			commonTraceRule.orderStanding(coQueue);
			return "success";
		}
		else{
			return "fail";
		}
	}
	@Transactional(propagation = Propagation.REQUIRED)
	private void deleteHis(String co_id, String tb_name,String out_tid) {
	    String sql_insert;
		//删除旧数据，新数据重新搬迁
	    try{
	    	if(!StringUtils.isEmpty(out_tid)){
				String dSql ="select a.order_id from es_order_ext a where a.out_tid=?";
				List<Map<String,String>> repOrderIds = this.baseDaoSupport.queryForList(dSql,out_tid); //获取上一条记录的订单编号,删除订单数据
				if(!ListUtil.isEmpty(repOrderIds)){
					for (Map repOrderIdM:repOrderIds) {
						String rep_order_id = (String)repOrderIdM.get("order_id");
						ordArchiveTacheManager.ordArchive(rep_order_id);
					}
					
				}
		    }
			if(Consts.ES_CO_QUEUE_BAK.equals(tb_name))
			{
				sql_insert = SF.ecsordSql("INSERT_INFORMATION_ES_CO_QUEUE");
				this.baseDaoSupport.execute(sql_insert, co_id);
				this.baseDaoSupport.execute("delete from es_co_queue_bak a where a.co_id = ? ", co_id);
			}
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	
	

	@Override
	public void insertOrderOuter(List orders) {
		String sql = "select count(*) from es_order_outer where order_id=? ";
		int size = orders.size();
		for(int i=0;i<size;i++){
			OrderOuter order = (OrderOuter) orders.get(i);
			String order_id = order.getOrder_id();
			String count = this.baseDaoSupport.queryForString(sql, order_id);
			if("0".equals(count)){
				this.baseDaoSupport.insert("es_order_outer", order);
			}
		}
		
	}

	@Override
	public void insertOrderData(Goods goods, Product product,
			List<Map> relations, List<Map> packages, List<Goods> products,
			List<Product> productList,List<Map> pmtGoods,List<Map> pmts,List<Map> pmtActs) {
		if(goods!=null && StringUtils.isNotEmpty(goods.getGoods_id())){
			String count = this.baseDaoSupport.queryForString(SF.goodsSql("COUNT_GOODS"), goods.getGoods_id());
			if("0".equals(count)){
				this.baseDaoSupport.insert("es_goods", goods);
			}
		}
		if(product!=null && StringUtils.isNotEmpty(product.getGoods_id())){
			String count = this.baseDaoSupport.queryForString(SF.goodsSql("COUNT_PRODUCT"), goods.getGoods_id());
			if("0".equals(count)){
				this.baseDaoSupport.insert("es_product", product);
			}
		}
		if(relations!=null && relations.size()>0){
			for(Map relation : relations){
				String count = this.baseDaoSupport.queryForString(SF.goodsSql("COUNT_GOODS_REL"), relation.get("a_goods_id").toString(),relation.get("z_goods_id").toString());
				if("0".equals(count)){
					this.baseDaoSupport.insert("es_goods_rel", relation);
				}
			}
		}
		if(packages!=null && packages.size()>0){
			for(Map packageMap : packages){
				String count = this.baseDaoSupport.queryForString(SF.goodsSql("COUNT_GOODS_PACKAGE2"), packageMap.get("goods_id").toString());
				if("0".equals(count)){
					this.baseDaoSupport.insert("es_goods_package", packageMap);
				}
			}
		}
		if(products!=null && products.size()>0){
			for(Goods product1 : products){
				String count = this.baseDaoSupport.queryForString(SF.goodsSql("COUNT_GOODS"), product1.getGoods_id());
				if("0".equals(count)){
					this.baseDaoSupport.insert("es_goods", product1);
				}
			}
		}
		if(productList!=null && productList.size()>0){
			for(Product product1 : productList){
				String count = this.baseDaoSupport.queryForString(SF.goodsSql("COUNT_PRODUCT"), product1.getGoods_id());
				if("0".equals(count)){
					this.baseDaoSupport.insert("es_product", product1);
				}
			}
		}
		
		if(pmtGoods!=null && pmtGoods.size()>0){
			for(Map map : pmtGoods){
				String count = this.baseDaoSupport.queryForString(SF.goodsSql("PMT_GOODS_COUNT"),Const.getStrValue(map, "pmt_id"),goods.getGoods_id());
				if("0".equals(count)){
					this.baseDaoSupport.insert("es_pmt_goods", map);
				}
			}
		}
		
		if(pmts!=null && pmts.size()>0){
			for(Map map : pmts){
				String count = this.baseDaoSupport.queryForString(SF.goodsSql("PROMOTION_COUNT"),Const.getStrValue(map, "pmt_id"));
				if("0".equals(count)){
					this.baseDaoSupport.insert("es_promotion", map);
				}
			}
		}
		
		if(pmtActs!=null && pmtActs.size()>0){
			for(Map map : pmtActs){
				String count = this.baseDaoSupport.queryForString(SF.goodsSql("PROMOTION_ACTIVITY_COUNT"),Const.getStrValue(map, "id"));
				if("0".equals(count)){
					this.baseDaoSupport.insert("es_promotion_activity", map);
				}
			}
		}
		
		String sql = "select count(*) from es_goods_action_rule where goods_id=? and service_code='ORDERSTANDARDIZE_AUTO'";
		String count = this.baseDaoSupport.queryForString(sql, goods.getGoods_id());
		if("0".equals(count)){
			String insertSql = "insert into es_goods_action_rule(goods_id, action_code, action_name, status, disabled, source_from, service_code) "+
					"select t.goods_id, t.action_code, t.action_name, t.status, t.disabled, t.source_from, "+
					"'ORDERSTANDARDIZE_AUTO' from es_goods_action_rule t where t.service_code='ORDERSTANDARDIZE' and t.goods_id=?";
			this.baseDaoSupport.execute(insertSql, goods.getGoods_id());
		}
	}

	@Override
	public String getOrderGoodsId(Map params) {
		String tb_name = Const.getStrValue(params, "tb_name");
		String out_tid = Const.getStrValue(params, "out_tid");
		String sql = "select b.goods_id from #table_name a,es_order_outer b where a.source_from=b.source_from and a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.object_id=b.order_id ";
		List pList = new ArrayList();
//		if(!StringUtils.isEmpty(co_id)){
//			sql += " and a.co_id=? ";
//			pList.add(co_id);
//		}
		if(!StringUtils.isEmpty(out_tid)){
			sql += " and b.old_sec_order_id=? ";
			pList.add(out_tid);
		}
		sql = sql.replaceAll("#table_name", "es_co_queue");
		String goods_id = this.baseDaoSupport.queryForString(sql, pList.toArray());
		return goods_id;
		
	}

	public IEcsOrdManager getEcsOrdManager() {
		return ecsOrdManager;
	}

	public void setEcsOrdManager(IEcsOrdManager ecsOrdManager) {
		this.ecsOrdManager = ecsOrdManager;
	}

	public IOrdArchiveTacheManager getOrdArchiveTacheManager() {
		return ordArchiveTacheManager;
	}

	public void setOrdArchiveTacheManager(
			IOrdArchiveTacheManager ordArchiveTacheManager) {
		this.ordArchiveTacheManager = ordArchiveTacheManager;
	}

}
