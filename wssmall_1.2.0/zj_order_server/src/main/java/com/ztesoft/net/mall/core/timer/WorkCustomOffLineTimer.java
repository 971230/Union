package com.ztesoft.net.mall.core.timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.workCustom.po.WORK_CUSTOM_FLOW_DATA;

import com.alibaba.fastjson.JSON;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.service.workCustom.interfaces.IWorkCustomEngine;

public class WorkCustomOffLineTimer {

	private static Logger logger = Logger.getLogger(WorkCustomOffLineTimer.class);
	static INetCache cache;
	static{
		cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	
	public void run(){
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		String sql = " select t.order_id from es_order_ext t "
				   + " left join es_work_custom_node_ins ni on ni.order_id=t.order_id and ni.is_curr_step='1' "
				   + " left join es_order o on o.order_id=t.order_id "
				   + " left join es_order_items_ext ie on ie.order_id=t.order_id "
				   + " where  ni.order_id=t.order_id and ni.is_curr_step='1' "
				   + " and o.order_id=t.order_id and ie.order_id=t.order_id "
				   + " and t.order_from='10093' and ie.goods_cat_id='90000000000000901' "
				   + " and ni.old_node_code='WP' and sysdate-o.create_time>1 "/*"and t.order_id='D90000000001465944'"*/;
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String FZNZD_OFFLINE_SQL = cacheUtil.getConfigInfo("FZNZD_OFFLINE_SQL");
		if(!StringUtil.isEmpty(FZNZD_OFFLINE_SQL)){
			sql = FZNZD_OFFLINE_SQL;
		}
		String FZNZD_OFFLINE_NODE_CODE = cacheUtil.getConfigInfo("FZNZD_OFFLINE_NODE_CODE");
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		List list = baseDaoSupport.queryForList(sql, null);
		OrderTreeBusiRequest orderTree = null;
		String set_sql  = "";
		IWorkCustomEngine workCustomEngine = SpringContextHolder.getBean("WorkCustomEngine");
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String order_id = Const.getStrValue(map, "order_id");
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			try {
				Map map_param=new HashMap();
				map_param.put("dealDesc", "超过24小时未支付");
				map_param.put("returnedReasonCode", "");
				map_param.put("applyFrom", "1");
				String json_param=JSON.toJSONString(map_param);
				workCustomEngine.gotoNode(order_id, FZNZD_OFFLINE_NODE_CODE,"");
				WORK_CUSTOM_FLOW_DATA flow_data=workCustomEngine.runNodeManualByCode(order_id, FZNZD_OFFLINE_NODE_CODE, true, "", "", json_param);
				orderTree.getOrderExtBusiRequest().setOrder_deal_method("2");
				set_sql = " order_deal_method = '2' ";
				updateOrderTree(set_sql,"es_order_ext",order_id,orderTree,baseDaoSupport);
			} catch (Exception e) {
				logger.info(e.getStackTrace());
				orderTree = null;
				set_sql = "";
				continue;
			}
		}
		
	}
	
	public void updateOrderTree(String set_sql,String table_name,String order_id,OrderTreeBusiRequest orderTree,IDaoSupport baseDaoSupport){
		String sql ="update "+table_name+" set "+set_sql+" where order_id=?";
		if(baseDaoSupport==null){
			baseDaoSupport=SpringContextHolder.getBean("baseDaoSupport");
		}
	        baseDaoSupport.execute(sql, order_id);
		//更新订单树缓存
		cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY+"order_id"+ order_id,orderTree, RequestStoreManager.time);
		
	}
}
