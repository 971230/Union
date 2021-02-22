package com.ztesoft.net.mall.core.service.impl;

import java.util.Map;

import params.req.QueryExpOrderStatisticsReq;
import params.resp.QueryExpOrderStatisticsResp;

import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupportWork;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IExpOrderStatisticsManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IOrderExpManager;

public class ExpOrderStatisticsManager extends BaseSupportWork implements
		IExpOrderStatisticsManager {

	private IOrderExpManager OrderExpManager;
	
	/**
	 * 获取异常单统计信息：待处理、已处理、待处理预警、待处理超时、已处理平均时长
	 */
	@Override
	public QueryExpOrderStatisticsResp queryExpOrderStatisticsData(QueryExpOrderStatisticsReq req) {
		QueryExpOrderStatisticsResp resp = new QueryExpOrderStatisticsResp();
		String[] tache_codes = new String[]{"B","C","D","X","Y","F","H","J","L"};
		StringBuffer sqlBuff = new StringBuffer();
		//总异常数
		sqlBuff.append("select count(eo.excp_inst_id) TOTALEXPINSTS,");
		//总异常订单数
		sqlBuff.append("count(distinct eo.rel_obj_id) TOTALEXPORDERS,");
		//已处理异常数
		sqlBuff.append("count(case when eo.record_status = '").append(EcsOrderConsts.EXPINST_RECORD_STATUS_1)
				.append("' then eo.excp_inst_id else null end) TOTALEXPINSTS_1,");
		//已处理异常订单数
		sqlBuff.append("count(distinct case when eo.record_status = '").append(EcsOrderConsts.EXPINST_RECORD_STATUS_1)
				.append("' then eo.rel_obj_id else null end) TOTALEXPORDERS_1,");
		//待处理异常数
		sqlBuff.append("count( case when eo.record_status = '").append(EcsOrderConsts.EXPINST_RECORD_STATUS_0)
				.append("' then eo.excp_inst_id else null end) TOTALEXPINSTS_0,");
		//待处理异常订单数
		sqlBuff.append("count(distinct case when eo.record_status = '").append(EcsOrderConsts.EXPINST_RECORD_STATUS_0)
				.append("' then eo.rel_obj_id else null end) TOTALEXPORDERS_0,");
		//超时异常数
		sqlBuff.append("count(distinct case when eo.record_status = '").append(EcsOrderConsts.EXPINST_RECORD_STATUS_0)
				.append("' and eo.timeout_flag = '").append(EcsOrderConsts.EXP_SPECVALUES_TIMEOUT_FLAG_Y)
				.append("' and eo.excp_create_time + eo.timeout_limit / 24 > sysdate then eo.excp_inst_id else null end) TOTALEXPINSTS_TIMEOUT,");
		//超时异常订单数
		sqlBuff.append("count(distinct case when eo.record_status = '").append(EcsOrderConsts.EXPINST_RECORD_STATUS_0)
				.append("' and eo.timeout_flag = '").append(EcsOrderConsts.EXP_SPECVALUES_TIMEOUT_FLAG_Y)
				.append("' and eo.excp_create_time + eo.timeout_limit / 24 > sysdate then eo.rel_obj_id else null end) TOTALEXPORDERS_TIMEOUT,");
		//已处理总时长
		sqlBuff.append("sum(case when eo.record_status = '").append(EcsOrderConsts.EXPINST_RECORD_STATUS_1)
				.append("' then (eo.deal_time - eo.excp_create_time) * 24 * 60 * 60 else 0 end) TOTALEXPINSTS_DEALTIMES,");
		for(String tache_code : tache_codes) {
			sqlBuff.append("count(distinct case when eo.tache_code = '").append(tache_code)
					.append("' and eo.record_status = '").append(EcsOrderConsts.EXPINST_RECORD_STATUS_0)
					.append("' then eo.rel_obj_id else null end) ").append(tache_code).append("_0,");
			sqlBuff.append("count(distinct case when eo.tache_code = '").append(tache_code)
					.append("' and eo.record_status = '").append(EcsOrderConsts.EXPINST_RECORD_STATUS_1)
					.append("' then eo.rel_obj_id else null end) ").append(tache_code).append("_1,");
			sqlBuff.append("count(distinct case when eo.tache_code = '").append(tache_code)
					.append("' and eo.record_status = '").append(EcsOrderConsts.EXPINST_RECORD_STATUS_0)
					.append("' and eo.timeout_flag = '").append(EcsOrderConsts.EXP_SPECVALUES_TIMEOUT_FLAG_Y)
					.append("' and eo.excp_create_time + eo.timeout_limit / 24 > sysdate then eo.rel_obj_id else null end) ").append(tache_code).append("_TIMEOUT,");
			sqlBuff.append("sum(case when eo.tache_code = '").append(tache_code)
					.append("' and eo.record_status = '").append(EcsOrderConsts.EXPINST_RECORD_STATUS_1)
					.append("' then (eo.deal_time - eo.excp_create_time) * 24 * 60 * 60 else 0 end) ").append(tache_code).append("_DEALTIMES,");
		}
		sqlBuff.append("count(distinct case when eo.tache_code is null and eo.record_status = '").append(EcsOrderConsts.EXPINST_RECORD_STATUS_0)
				.append("' then eo.rel_obj_id else null end) GJ_0,");
		sqlBuff.append("count(distinct case when eo.tache_code is null and eo.record_status = '").append(EcsOrderConsts.EXPINST_RECORD_STATUS_1)
				.append("' then eo.rel_obj_id else null end) GJ_1,");
		sqlBuff.append("count(distinct case when eo.tache_code is null and eo.record_status = '").append(EcsOrderConsts.EXPINST_RECORD_STATUS_0)
				.append("' and eo.timeout_flag = 'Y' and eo.excp_create_time + eo.timeout_limit / 24 > sysdate then eo.rel_obj_id else null end) GJ_TIMEOUT,");
		sqlBuff.append("sum(case when eo.tache_code is null and eo.record_status = '").append(EcsOrderConsts.EXPINST_RECORD_STATUS_1)
				.append("' then (eo.deal_time - eo.excp_create_time) * 24 * 60 * 60 else 0 end) GJ_DEALTIMES");
		
		sqlBuff.append(" from (select a.catalog_id,a.excp_inst_id, a.rel_obj_id, a.rel_obj_type, a.record_status, a.excp_create_time, a.deal_time, ee.tache_code, ");
		sqlBuff.append(" es.key_id, es.warming_flag, es.warming_limit, es.timeout_flag, es.timeout_limit, a.source_from ");
		sqlBuff.append(" FROM es_esearch_expinst a left join es_esearch_expinst_ext ee on a.excp_inst_id = ee.excp_inst_id ");
		sqlBuff.append(" left join es_esearch_specvalues es on es.key_id = a.key_id and es.source_from = a.source_from ");
		sqlBuff.append(" where a.excp_create_time >= to_date('").append(req.getStart_time()).append("', 'yyyy-mm-dd hh24:mi:ss') ");
		sqlBuff.append(" and a.excp_create_time <= to_date('").append(req.getEnd_time()).append("', 'yyyy-mm-dd hh24:mi:ss') ");
		sqlBuff.append(" and a.is_visible = '").append(EcsOrderConsts.EXP_INST_IS_VISIBLE_Y).append("' ");
		
		try{
			Map mapEnv = BeanUtils.getCurrHostEnv();//获取当前的主机环境
			if(mapEnv != null&& mapEnv.size() > 0) {
				String busi_version = (String)mapEnv.get("BUSI_VERSION");//
				String env_group = (String)mapEnv.get("ENV_GROUP");//
				logger.info("busi_version:"+busi_version);
				sqlBuff.append(" and exists( select 1 from es_abgray_ord_env_rel_log aorl, es_abgray_hostenv ah");
				sqlBuff.append(" where aorl.env_code = ah.env_code and aorl.out_tid = a.out_tid and ah.busi_version = ");
				sqlBuff.append("'").append(busi_version).append("'");
				sqlBuff.append(" and ah.env_group = ");
				sqlBuff.append("'").append(env_group).append("')");
			}
			if(req.getFounder() != null && req.getFounder() != 1){
				sqlBuff.append(" and exists( select 1 ");
				sqlBuff.append(" from es_user_role ur,es_role_auth ra, es_role_data rd");
				sqlBuff.append(" where ur.roleid=ra.roleid and ra.authid=rd.id");
				sqlBuff.append(" and ur.source_from = rd.source_from and ur.source_from = ra.source_from");
				sqlBuff.append(" and (regexp_like(rd.orderexp_catalog, ',' || a.catalog_id || ',', 'i')");
				sqlBuff.append(" or regexp_like(rd.orderexp_catalog, ',' ||").append(EcsOrderConsts.EXP_INST_LIST_ALL).append("|| ',', 'i'))");
				sqlBuff.append(" and ur.userid = '"+req.getUser_id()+"')");
			}
			
			sqlBuff.append(" ) eo ");
			sqlBuff.append(" where eo.source_from = '").append(ManagerUtils.getSourceFrom()).append("'");
			logger.info(sqlBuff.toString());
			Map map = this.baseDaoSupportWork.queryForMap(sqlBuff.toString());
			if(null != map && map.size() > 0){
				String totalExpDeals = map.get("totalexpinsts_1").toString();
				String totalExpDealTimes = map.get("totalexpinsts_dealtimes").toString();
				if("".equals(totalExpDeals) || "0".equals(totalExpDeals)){
					map.put("totalexpinsts_dealavgtimes", "00天00时00分00秒");
				}else{
					map.put("totalexpinsts_dealavgtimes", getAvgTimes(totalExpDeals,totalExpDealTimes));
				}
				
				String gjDeals = map.get("gj_1").toString();
				String gjDealTimes = map.get("gj_dealtimes").toString();
				if("".equals(totalExpDeals) || "0".equals(totalExpDeals)){
					map.put("gj_dealavgtimes", "00天00时00分00秒");
				}else{
					map.put("gj_dealavgtimes", getAvgTimes(totalExpDeals,totalExpDealTimes));
				}
				for(String tache_code : tache_codes) {
					String deals = map.get(tache_code.toLowerCase()+"_1").toString();
					String dealTimes = map.get(tache_code.toLowerCase()+"_dealtimes").toString();
					if("".equals(deals) || "0".equals(deals)){
						map.put(tache_code.toLowerCase()+"_dealavgtimes", "00天00时00分00秒");
					}else{
						map.put(tache_code.toLowerCase()+"_dealavgtimes", getAvgTimes(deals,dealTimes));
					}
				}
			}
			resp.setError_code("0");
			resp.setError_msg("查询成功");
			resp.setMap(map);
		}catch(Exception e){
			resp.setError_code("-1");
			resp.setError_msg("查询失败");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 获取平均处理时长
	 * @param orders
	 * @param totalTimes
	 * @return
	 */
	public String getAvgTimes(String orders , String totalTimes){
		if("".equals(orders) || "0".equals(orders)){
			return "";
		}else{
			return secToTime(Long.parseLong(totalTimes)/Long.parseLong(orders));
		}
	}
	
	/**
	 * 秒转换为XX时XX分XX秒
	 * @param time
	 * @return
	 */
	public String secToTime(long secondTime){
		int ss = 1;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;
		
		long day = secondTime / dd;
		long hour = (secondTime - day * dd) / hh;
		long minute = (secondTime - day * dd - hour * hh) / mi;
		long second = (secondTime - day * dd - hour * hh - minute * mi) / ss;
		
		String strDay = day < 10 ? "0" + day : "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		return new StringBuffer(strDay).append("天").append(strHour).append("时")
				.append(strMinute).append("分").append(strSecond).append("秒").toString();
	}

	public IOrderExpManager getOrderExpManager() {
		return OrderExpManager;
	}

	public void setOrderExpManager(IOrderExpManager orderExpManager) {
		OrderExpManager = orderExpManager;
	}

}
