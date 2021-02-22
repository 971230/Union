package com.ztesoft.net.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.wyg.req.NotifyOrderInfoWYGRequset;
import zte.net.ecsord.params.wyg.resp.NotifyOrderInfoWYGResponse;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.params.resp.RuleTreeExeResp;

import com.powerise.ibss.util.RuleUtil;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.utils.BusiUtils;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AopQueryDataVo;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.IOrdArchiveTacheManager;
import com.ztesoft.net.service.IOrderFlowManager;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.util.CacheUtils;

import consts.ConstsCore;

/**
 * 订单归档  处理类
 * @author xuefeng
 */
public class OrdArchiveTacheManager extends BaseSupport implements IOrdArchiveTacheManager {
	@Resource
	private IOrderFlowManager ordFlowManager;//流程处理类
	
	//表名必须大写
	public static String[] insertTables = {"ES_ORDER","ES_ORDER_EXT","ES_ORDER_EXTVTL","ES_ORDER_ITEMS","ES_ORDER_ITEMS_EXT","ES_ORDER_ITEMS_PROD"
			,"ES_ORDER_ITEMS_PROD_EXT","ES_ORDER_LOG","ES_ORDER_EXCEPTION_COLLECT","ES_ORDER_COMMENTS"
			,"ES_ORDER_TREES","ES_ATTR_INST","ES_DELIVERY","ES_DELIVERY_ITEM","ES_PAYMENT_LOGS","ES_RULE_EXE_LOG"
			,"ES_ORDER_ITEMS_APT_PRINTS","ES_ORDER_ITEMS_INV_PRINTS","ES_ATTR_FEE_INFO","ES_ATTR_GIFT_INFO"
			,"ES_ATTR_GIFT_PARAM","ES_ATTR_PACKAGE","ES_ATTR_DISCOUNT_INFO","ES_ORDER_ITEMS_AGENT_MONEY","INF_COMM_CLIENT_CALLLOG"
			,"ES_ATTR_TERMINAL_EXT","ES_ORDER_PHONE_INFO","ES_ORDER_EXCEPTION_LOGS","ES_ORDER_ROLE","ES_ORDER_ITEMS_EXTVTL",
			"ES_ORDER_STATS_TACHE","ES_ORDER_ACTIVITY","ES_ATTR_PACKAGE_ACTIVITY","ES_ORDER_HANDLE_LOGS",
			"ES_ORDER_PHONE_INFO_FUKA","ES_ATTR_PACKAGE_FUKA","ES_ATTR_PACKAGE_SUBPROD","ES_ORDER_SUB_PRODCUT","ES_ORDER_SP_PRODUCT",
			"ES_ORDER_ITEMS_VALI_LOG","ES_ORDER_ROUTE_LOG","ES_HUASHENG_ORDER","ES_HUASHENG_ORDER_ITEM","ES_ORDER_LOCK","ES_ORDER_OUTCALL_LOG",
			"ES_ORDER_UPDATE_LOG","ES_ORDER_ZHWQ_ADSL","ES_ORDER_ZHWQ_GW","ES_ORDER_ZHWQ_YW","ES_ORDER_ZHWQ_YXT","ES_ORDER_AOP_QUERY"};
	
	/**
	 * 逻辑归档
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public BusiDealResult ordLogisArchive(String order_id) throws Exception {
		BusiDealResult result = new BusiDealResult();

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		orderExtBusiRequest.setIs_archive(EcsOrderConsts.IS_DEFAULT_VALUE);
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.store();
		
		result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
		result.setError_msg("数据恢复成功");
		return result;
	}
	
	/**
	 * 订单信息归档
	 * @param order_id
	 * @return
	 */
//	@Override
	@Transactional(propagation = Propagation.REQUIRED, timeout=120)  
	public BusiDealResult ordArchive(String order_id) throws Exception {
		logger.info("ordArchive===start:"+order_id);
		BusiDealResult result = new BusiDealResult();
		
//		try {
			//数据插入历史记录表   es_order_items_apt_prints //es_order_items_inv_prints
			String sql = "";
			for(int i=0;i<insertTables.length;i++){
				String tableName = insertTables[i];
				String hisTableName = tableName + "_HIS";
				String queryColSql = "select column_name from user_col_comments where table_name = '"+tableName+"'";
				List colList = this.baseDaoSupport.queryForList(queryColSql);
				
				// add by shusx 如果表还未创建就执行数据归档，方便查找问题
				if (colList == null && colList.size() <= 0) {
					throw new RuntimeException("执行订单数据归档操作["+order_id+"],tableName:"+tableName+"不存在");
				}
				
				String cols = "";
				for(int j=0;j<colList.size();j++){
					Map colMap = (Map)colList.get(j);
					cols = cols + colMap.get("column_name") + ",";
				}
				cols = cols.substring(0, cols.length()-1);
				String keyName = "order_id";
				if(tableName.equals("ES_RULE_EXE_LOG")){
					keyName = "obj_id";
				}else if(tableName.equals("INF_COMM_CLIENT_CALLLOG")){
					keyName = "col3";
				}
				
				// add by shusx 先删除历史表(处理未加事务前的失败的数据) --- 影响归档性能,异常数据特殊处理
//				String delSql = "delete from "+hisTableName+" t where t."+keyName+" = '"+order_id+"' and exists(select 1 from "+tableName+" th where th."+keyName+" = t."+keyName+")";
//				this.baseDaoSupport.execute(delSql);
				
				// 再备份到历史表
				sql = "insert into "+hisTableName +" ("+cols+") "+
				"select "+cols+" from "+tableName +" where "+keyName+" = '"+order_id+"'";
				
				//****************** add by qin.yingxiong at 2016/10/20 start *******************
				boolean useOrgTable = RuleUtil.useOrgTable(order_id);
				if(useOrgTable) {
					sql = RuleUtil.replaceTableNameForOrgBySql(sql);
				}
				//****************** add by qin.yingxiong at 2016/10/20 end *******************
				if(!sql.contains("es_rule_exe_log_his_org")){
					this.baseDaoSupport.execute(sql);
				}
				
			}
			for(int i=0;i<insertTables.length;i++){
				String tableName = insertTables[i];
				String keyName = "order_id";
				if(tableName.equals("ES_RULE_EXE_LOG")){
					keyName = "obj_id";
				}else if(tableName.equals("INF_COMM_CLIENT_CALLLOG")){
					keyName = "col3";
				}
				String delSql = "delete from "+tableName+" where "+keyName+" = '"+order_id+"'";
				//****************** add by qin.yingxiong at 2016/10/20 start *******************
				boolean useOrgTable = RuleUtil.useOrgTable(order_id);
				if(useOrgTable) {
					sql = RuleUtil.replaceTableNameForOrgBySql(sql);
				}
				//****************** add by qin.yingxiong at 2016/10/20 end *******************
				this.baseDaoSupport.execute(delSql);
			}
		
		
		//删除外系统订单数据
		String rep_outer_order_id = this.baseDaoSupport.queryForString(SF.orderSql("ORDER_OUTER_ID_BY_ORDERID"),order_id); //
		if(!StringUtil.isEmpty(rep_outer_order_id)){
			
			//数据归档
			//this.baseDaoSupport.execute(SF.archivesSql("INS_ORDER_OUTER_HIS"), rep_outer_order_id);
//			this.baseDaoSupport.execute(SF.archivesSql("INS_ORDER_OUTER_ATTR_INST_HIS"), rep_outer_order_id); add by wui整个系统替换后放开
			this.baseDaoSupport.execute(SF.archivesSql("INS_ORDER_REL_HIS"), rep_outer_order_id,order_id);
			
			
			//删除数据
			//this.baseDaoSupport.execute(SF.archivesSql("DEL_ORDER_OUTER"), rep_outer_order_id); //
//			this.baseDaoSupport.execute(SF.archivesSql("DEL_ORDER_OUTER_ATTR_INST"), rep_outer_order_id); //add by wui整个系统替换后放开
			this.baseDaoSupport.execute(SF.archivesSql("DEL_ORDER_OUTER_REL"), rep_outer_order_id,order_id);
		}
		
		//删除 add by wui不需要修改状态，保留原有状态不变。
		//修改里历史表里订单状态为“已归档“
//		 sql = SF.archivesSql("UP_ORDER_HIS");
//		 this.baseDaoSupport.execute(sql, EcsOrderConsts.ORDER_STATUS,order_id);
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
//			result.setError_msg("归档数据出错："+e.getMessage());
//		}

		logger.info("ordArchive===end:"+order_id);
		// 删除订单树缓存
		try {
			String key = BusiUtils.genCachePrimikey("order_id", order_id);
			CacheUtils.deleteCache(EcsOrderConsts.ORDER_TREE_NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY+key);
		} catch (Exception e) {
			logger.info("===ordArchive==delete orderTree cache error:"+order_id+";"+e.getMessage());
			e.printStackTrace();
		}
		logger.info("ordArchive===end delete orderTree cache:"+order_id);
		
		result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
		result.setError_msg("数据归档成功");
		return result;
	}
	/**
	 * 退库存
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult removeRepertory(String order_id) {
		
		return null;
	}
	/**
	 * 归档完成状态通知新商城
	 * @param order_id
	 * @return
	 */
	@Override
	public BusiDealResult notifyFinishToOuterShop(String order_id) {
		String field_value="";
		BusiDealResult result = new BusiDealResult();
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils
				.getSourceFrom());
		
		NotifyOrderInfoWYGRequset req = new NotifyOrderInfoWYGRequset();
		req.setTrace_code(EcsOrderConsts.NEW_SHOP_ORDER_STATE_04);		//通知外部商城归集完成（新商城）
		req.setNotNeedReqStrOrderId(order_id);
		
		NotifyOrderInfoWYGResponse infResp = client.execute(req, NotifyOrderInfoWYGResponse.class);
		
		if (!infResp.getResp_code().equals(EcsOrderConsts.NEW_SHOP_INF_SUCC_CODE)){
			result.setError_msg("错误编码：" + infResp.getResp_code() + ";错误信息："
					+ infResp.getResp_msg());
			result.setError_code(infResp.getResp_code());
			field_value=EcsOrderConsts.NEW_SHOP_STATUS_4;
		}else{
			field_value=EcsOrderConsts.NEW_SHOP_STATUS_3;
		}
		String [] name={ AttrConsts.NEW_SHOP_STATUS};
		String [] value={field_value};
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id,name, value);
		//归档完成通知新商城
		
		return result;
	}
	
	/**
	 * 该方法不从缓存中取订单数据（非特殊不要使用这种方法）
	 * @param order_id
	 * @return
	 */
	@Override
	public Map getArchiveCondition(String order_id) {
		HashMap<String, String> map = null;
		//暂时保留eoie.bss_status BSS业务办理状态 的判断,以保证历史数据的归档校验。
		//待历史数据归档完毕,再去除eoie.bss_status BSS业务办理状态 的判断
		//仕鹏确认去掉旧的业务办理状态校验条件
//		List list = this.baseDaoSupport.queryForList("select eoie.bss_status,to_char(eoe.back_time,'yyyy-mm-dd hh24:mi:ss') back_time,eoe.archive_time,case when (exists(select 1 from es_order_sp_product eosp where eosp.order_id=eoe.order_id and eosp.source_from=eoe.source_from and eosp.status in ('0','1')) or exists(select 1 from es_attr_package_subprod eaps where eaps.order_id=eoe.order_id and eaps.source_from=eoe.source_from and (eaps.status is null or eaps.status in ('0','1')))) then '1' else '0' end exists_business_to_deal_with from es_order_ext eoe,es_order_items_ext eoie where eoe.order_id=eoie.order_id and eoe.source_from='ECS' and eoe.order_id=?",order_id);
		List list = this.baseDaoSupport.queryForList("select to_char(eoe.back_time,'yyyy-mm-dd hh24:mi:ss') back_time,to_char(trunc(eoe.back_time+archive_time)-trunc(sysdate)) archive_time,case when (exists(select 1 from es_order_sp_product eosp where eosp.order_id=eoe.order_id and eosp.source_from=eoe.source_from and eosp.status not in ('"+EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2+"','"+EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3+"')) or exists(select 1 from es_attr_package_subprod eaps where eaps.order_id=eoe.order_id and eaps.source_from=eoe.source_from and (eaps.status is null or eaps.status not in ('"+EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_2+"','"+EcsOrderConsts.ORDER_SP_PRODUCT_STATUS_3+"')))) then '1' else '0' end exists_business_to_deal_with from es_order_ext eoe where eoe.source_from='"+ManagerUtils.getSourceFrom()+"' and eoe.order_id=?",order_id);
		if (list != null && list.size() > 0) {
			map = (HashMap<String, String>)list.get(0);
		}
		return map;
	}

	/**
	 * 积压订单恢复，从历史表中恢复数据到正常表
	 */
	@Transactional(propagation = Propagation.REQUIRED, timeout=120)
	public BusiDealResult ordRecover(String order_id) throws Exception {
		BusiDealResult result = new BusiDealResult();
//		try {
			//数据插入历史记录表   es_order_items_apt_prints //es_order_items_inv_prints
			String sql = "";
			for(int i=0;i<insertTables.length;i++){
				String tableName = insertTables[i] + "_HIS";
				String hisTableName = insertTables[i];
				String queryColSql = "select column_name from user_col_comments where table_name = '"+hisTableName+"'";
				List colList = this.baseDaoSupport.queryForList(queryColSql);
				
				// add by shusx 如果表还未创建就执行数据归档，方便查找问题
				if (colList == null && colList.size() <= 0) {
					throw new RuntimeException("执行订单数据归档操作["+order_id+"],tableName:"+hisTableName+"不存在");
				}
				
				String cols = "";
				for(int j=0;j<colList.size();j++){
					Map colMap = (Map)colList.get(j);
					cols = cols + colMap.get("column_name") + ",";
				}
				cols = cols.substring(0, cols.length()-1);
				String keyName = "order_id";
				if(hisTableName.equals("ES_RULE_EXE_LOG")){
					keyName = "obj_id";
				}else if(hisTableName.equals("INF_COMM_CLIENT_CALLLOG")){
					keyName = "col3";
				}
				
				// add by shusx 先删除历史表(处理未加事务前的失败的数据)  --- 影响归档性能,异常数据特殊处理
//				String delSql = "delete from "+hisTableName+" t where t."+keyName+" = '"+order_id+"' and exists(select 1 from "+tableName+" th where th."+keyName+" = t."+keyName+")";
//				this.baseDaoSupport.execute(delSql);
				
				// 再备份到历史表
				sql = "insert into "+hisTableName +" ("+cols+") "+
				"select "+cols+" from "+tableName +" where "+keyName+" = '"+order_id+"'";
				//****************** add by qin.yingxiong at 2016/10/20 start *******************
				boolean useOrgTable = RuleUtil.useOrgTable(order_id);
				if(useOrgTable) {
					sql = RuleUtil.replaceTableNameForOrgBySql(sql);
				}
				//****************** add by qin.yingxiong at 2016/10/20 end *******************
				this.baseDaoSupport.execute(sql);
			}
			for(int i=0;i<insertTables.length;i++){
				String tableName = insertTables[i] + "_HIS";
				String keyName = "order_id";
				if(insertTables[i].equals("ES_RULE_EXE_LOG")){
					keyName = "obj_id";
				}else if(insertTables[i].equals("INF_COMM_CLIENT_CALLLOG")){
					keyName = "col3";
				}
				String delSql = "delete from "+tableName+" where "+keyName+" = '"+order_id+"'";
				//****************** add by qin.yingxiong at 2016/10/20 start *******************
				boolean useOrgTable = RuleUtil.useOrgTable(order_id);
				if(useOrgTable) {
					sql = RuleUtil.replaceTableNameForOrgBySql(sql);
				}
				//****************** add by qin.yingxiong at 2016/10/20 end *******************
				this.baseDaoSupport.execute(delSql);
			}
			
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
//			result.setError_msg("数据恢复成功："+e.getMessage());
//			return result;
//		}
			
		// 删除订单树缓存
		try {
			String key = BusiUtils.genCachePrimikey("order_id", order_id);
			CacheUtils.deleteCache(EcsOrderConsts.ORDER_TREE_NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY+key);
		} catch (Exception e) {
			logger.info("===ordRecover==delete orderTree cache error:"+order_id+";"+e.getMessage());
			e.printStackTrace();
		}

		result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
		result.setError_msg("数据恢复成功");
		return result;
	}
	
//	@Override
//	public void updateOrderSpProductsState(List<Map<String,String>> batchArgs) {
//		String sql = "UPDATE es_order_sp_product SET status=:status WHERE sp_inst_id=:sp_inst_id";
//		this.baseDaoSupport.batchExecuteByListMap(sql, batchArgs);
//	}
//
//	@Override
//	public void updateOrderSpProductState(Map<String, String> map) {
//		String sql = "UPDATE es_order_sp_product SET status=? WHERE sp_inst_id=?";
//		this.baseDaoSupport.execute(sql, map.get("status"), map.get("sp_inst_id"));
//	}
	@Override
	/**
	 * 总商爬虫同步模式订单归档
	 */
	public void zbOrderDataArchive(String orderId) {
		// TODO Auto-generated method stub
		String sql="insert into es_order_status_zb_his(ORDER_ID,OUT_TID,ZB_ID,STATUS,CREATE_TIME,ZB_LAST_MODIFY_TIME,ZB_LAST_MODIFY_TIME,REMARK,SOURCE_FROM) select ORDER_ID,OUT_TID,ZB_ID,STATUS,CREATE_TIME,ZB_LAST_MODIFY_TIME,REMARK,SOURCE_FROM from es_order_status_zb where order_id=?";
		String sql1="insert into es_order_operation_zb_his(ORDER_ID,OPERATION_TIME,OPERATION_TYPE,OPERATION_CONTENT,OPERATION_USER,REMARK) select ORDER_ID,OPERATION_TIME,OPERATION_TYPE,OPERATION_CONTENT,OPERATION_USER,REMARK from  es_order_operation_zb where order_id=?";
		
		this.baseDaoSupport.execute(sql, orderId);
		this.baseDaoSupport.execute(sql1, orderId);
		
		this.baseDaoSupport.execute("delete from es_order_status_zb where order_id=?", orderId);
		this.baseDaoSupport.execute("delete from es_order_operation_zb where order_id=?", orderId);
	}
}