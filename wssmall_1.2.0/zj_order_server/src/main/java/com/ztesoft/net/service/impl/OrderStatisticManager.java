package com.ztesoft.net.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.drools.core.util.StringUtils;

import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.database.BaseSupportWork;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IOrderStatisticManager;

public class OrderStatisticManager extends BaseSupport implements
		IOrderStatisticManager {

	@Override
	public void insertOrderStatisticTacheItem(String orderId , String create_time) {
//		String sql = "insert into es_order_stats_tache (order_id,s_start_time,source_from) values (?,to_date(?,'YYYY-MM-DD HH24:MI:SS'),'"+ManagerUtils.getSourceFrom()+"')";
//		try{
//			this.baseDaoSupport.execute(sql, new Object[]{orderId,create_time});
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		String sql = "insert into es_order_stats_tache (order_id,s_start_time,source_from) values (?,sysdate,'"+ManagerUtils.getSourceFrom()+"')";
		try{
			this.baseDaoSupport.execute(sql, new Object[]{orderId});
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void updateOrderTacheItems(String orderId , String beforeFlowId, String flowId,String opId) {
		String sql = "update es_order_stats_tache set s_end_time = sysdate,b_begin_time = sysdate,b_op_id  = ? where order_id = ? and source_from = '"+ManagerUtils.getSourceFrom()+"'";
		try{
			//前一环节结束时间
			/*if("S".equalsIgnoreCase(beforeFlowId)){
				sql = sql.replaceAll("s_end_time", "s_end_time");
			}else if("B".equalsIgnoreCase(beforeFlowId)){
				sql = sql.replaceAll("s_end_time", "b_end_time");
			}else if("C".equalsIgnoreCase(beforeFlowId)){
				sql = sql.replaceAll("s_end_time", "c_end_time");
			}else if("D".equalsIgnoreCase(beforeFlowId)){
				sql = sql.replaceAll("s_end_time", "d_end_time");
			}else if("F".equalsIgnoreCase(beforeFlowId)){
				sql = sql.replaceAll("s_end_time", "f_end_time");
			}else if("H".equalsIgnoreCase(beforeFlowId)){
				sql = sql.replaceAll("s_end_time", "h_end_time");
			}else if("J".equalsIgnoreCase(beforeFlowId)){
				sql = sql.replaceAll("s_end_time", "j_end_time");
			}else if("X".equalsIgnoreCase(beforeFlowId)){
				sql = sql.replaceAll("s_end_time", "x_end_time");
			}else if("Y".equalsIgnoreCase(beforeFlowId)){
				sql = sql.replaceAll("s_end_time", "y_end_time");
			}else if("L".equalsIgnoreCase(beforeFlowId)){
				sql = sql.replaceAll("s_end_time", "l_end_time");
			}*/
			sql = sql.replaceAll("s_end_time", flowId.toLowerCase()+"_end_time");
			//当前环节开始时间
			/*if("B".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_begin_time", "b_begin_time");
				sql = sql.replaceAll("b_op_id", "b_op_id");
			}else if("C".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_begin_time", "c_begin_time");
				sql = sql.replaceAll("b_op_id", "c_op_id");
			}else if("D".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_begin_time", "d_begin_time");
				sql = sql.replaceAll("b_op_id", "d_op_id");
			}else if("F".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_begin_time", "f_begin_time");
				sql = sql.replaceAll("b_op_id", "f_op_id");
			}else if("H".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_begin_time", "h_begin_time");
				sql = sql.replaceAll("b_op_id", "h_op_id");
			}else if("J".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_begin_time", "j_begin_time");
				sql = sql.replaceAll("b_op_id", "j_op_id");
			}else if("X".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_begin_time", "x_begin_time");
				sql = sql.replaceAll("b_op_id", "x_op_id");
			}else if("Y".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_begin_time", "y_begin_time");
				sql = sql.replaceAll("b_op_id", "y_op_id");
			}else if("L".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_begin_time", "l_begin_time");
				sql = sql.replaceAll("b_op_id", "l_op_id");
			}*/
			sql = sql.replaceAll("b_begin_time", flowId.toLowerCase()+"_begin_time");
			sql = sql.replaceAll("b_op_id", flowId.toLowerCase()+"_op_id");
			try{
				if(StringUtil.isEmpty(opId)){
					AdminUser adminUser = ManagerUtils.getAdminUser();
					if(adminUser!=null){
						opId  = adminUser.getUserid();				
					}
				}
			}catch(Exception e){
				
			}
			if(StringUtil.isEmpty(opId)){
				opId ="1";//默认管理员工号
			}
			this.baseDaoSupport.execute(sql, new Object[]{opId,orderId});
		}catch(Exception e){
			e.printStackTrace();
		}
	}	


	
	public void updateOrderTacheItems(String orderId , String flowId,String opId) {
		String sql = "update es_order_stats_tache set b_end_time = sysdate, b_op_id  = ? where order_id = ? and source_from = '"+ManagerUtils.getSourceFrom()+"'";
		try{			
			//当前环节开始时间
			/*if("B".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_end_time", "b_end_time");
				sql = sql.replaceAll("b_op_id", "b_op_id");
			}else if("C".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_end_time", "c_end_time");
				sql = sql.replaceAll("b_op_id", "c_op_id");
			}else if("D".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_end_time", "d_end_time");
				sql = sql.replaceAll("b_op_id", "d_op_id");
			}else if("F".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_end_time", "f_end_time");
				sql = sql.replaceAll("b_op_id", "f_op_id");
			}else if("H".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_end_time", "h_end_time");
				sql = sql.replaceAll("b_op_id", "h_op_id");
			}else if("J".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_end_time", "j_end_time");
				sql = sql.replaceAll("b_op_id", "j_op_id");
			}else if("X".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_end_time", "x_end_time");
				sql = sql.replaceAll("b_op_id", "x_op_id");
			}else if("Y".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_end_time", "y_end_time");
				sql = sql.replaceAll("b_op_id", "y_op_id");
			}else if("L".equalsIgnoreCase(flowId)){
				sql = sql.replaceAll("b_end_time", "l_end_time");
				sql = sql.replaceAll("b_op_id", "l_op_id");
			}*/
			sql = sql.replaceAll("b_end_time", flowId.toLowerCase()+"_end_time");
			sql = sql.replaceAll("b_op_id", flowId.toLowerCase()+"_op_id");
			try{
				if(StringUtil.isEmpty(opId)){
					AdminUser adminUser = ManagerUtils.getAdminUser();
					if(adminUser!=null){
						opId  = adminUser.getUserid();				
					}
				}
			}catch(Exception e){
				
			}
			if(StringUtil.isEmpty(opId)){
				opId ="1";//默认管理员工号
			}
			this.baseDaoSupport.execute(sql, new Object[]{opId,orderId});
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	/**
	 * 获取工号的待办订单数、已处理订单数、各环节处理订单数、环节超时、环节预警订单数、平均时长等
	 */
	public Map<String,String> queryOrderStatisticsData(String user_id ,int founder, String start_time , String end_time){
		StringBuffer sqlBuff = new StringBuffer();
		Map map = null;
		try{
			sqlBuff.append("select est.totalorders,est.delayorders,est.myorders,est.bfloworders,est.bflowundoorders,");
			sqlBuff.append(" est.bflowtimeoutorders,est.bflowalertorders,est.bflowdealtimes,est.cfloworders,est.cflowundoorders,");
			sqlBuff.append(" est.cflowtimeoutorders,est.cflowalertorders,est.cflowdealtimes,est.yfloworders,est.yflowundoorders,");
			sqlBuff.append(" est.yflowtimeoutorders,est.yflowalertorders,est.yflowdealtimes,est.dfloworders,est.dflowundoorders,");
			sqlBuff.append(" est.dflowtimeoutorders,est.dflowalertorders,est.dflowdealtimes,est.xfloworders,est.xflowundoorders,");
			sqlBuff.append(" est.xflowtimeoutorders,est.xflowalertorders,est.xflowdealtimes,est.ffloworders,est.fflowundoorders,");
			sqlBuff.append(" est.fflowtimeoutorders,est.fflowalertorders,est.fflowdealtimes,est.hfloworders,est.hflowundoorders,");
			sqlBuff.append(" est.hflowtimeoutorders,est.hflowalertorders,est.hflowdealtimes,est.jfloworders,est.jflowundoorders,");
			sqlBuff.append(" est.jflowtimeoutorders,est.jflowalertorders,est.jflowdealtimes,est.lfloworders,est.lflowundoorders,");
			sqlBuff.append(" est.lflowtimeoutorders,est.lflowalertorders,est.lflowdealtimes,est.delayAlertOrders,est.lockedOrders,est.source_from");
			sqlBuff.append(" from (select count(distinct(case when mo.flow_trace_id in ('B','C','Y','D','X','F','H','J','L') then mo.order_id else null end)) totalOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id,'B',1,0)) delayOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'B' and sysdate - (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'B') > mo.tid_time then mo.order_id else null  end)) delayAlertOrders,");
			sqlBuff.append(" count(distinct(case when '"+user_id+"' in (os.b_op_id, os.c_op_id, os.y_op_id, os.d_op_id, os.x_op_id, os.f_op_id, os.h_op_id, os.j_op_id, os.l_op_id) then os.order_id else null end)) myOrders,");
			sqlBuff.append(" sum(case when os.b_op_id = '"+user_id+"' and os.b_end_time is not null then 1 else 0 end) BFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'B' then mo.order_id else null end)) BFlowUndoOrders,");
			sqlBuff.append(" sum(case when os.b_op_id = '"+user_id+"' and nvl(os.b_end_time,sysdate) - os.b_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'B') then 1 else 0 end) BFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'B', mo.isAlert, 0)) BFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.b_op_id = '"+user_id+"' and os.b_end_time is not null then (os.b_end_time - os.b_begin_time) * 24 * 60 * 60 else 0 end) BFlowDealTimes,");
			sqlBuff.append(" sum(case when os.c_op_id = '"+user_id+"' and os.c_end_time is not null then 1 else 0 end) CFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'C' then mo.order_id else null end)) CFlowUndoOrders,");
			sqlBuff.append(" sum(case when os.c_op_id = '"+user_id+"' and nvl(os.c_end_time,sysdate) - os.c_begin_time > (select p.codea  from es_dc_public p where p.stype = 20151211111   and pkey = 'C') then  1 else  0 end) CFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'C', mo.isAlert, 0)) CFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.c_op_id = '"+user_id+"' and os.c_end_time is not null then (os.c_end_time - os.c_begin_time) * 24 * 60 * 60 else 0 end) CFlowDealTimes,");
			sqlBuff.append(" sum(case when os.y_op_id = '"+user_id+"' and os.y_end_time is not null then 1 else 0 end) YFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'Y' then mo.order_id else null end)) YFlowUndoOrders,");
			sqlBuff.append(" sum(case when os.y_op_id = '"+user_id+"' and nvl(os.y_end_time,sysdate) - os.y_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'Y') then 1 else 0 end) YFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'Y', mo.isAlert, 0)) YFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.y_op_id = '"+user_id+"' and os.y_end_time is not null then (os.y_end_time - os.y_begin_time) * 24 * 60 * 60 else 0 end) YFlowDealTimes,");
			sqlBuff.append(" sum(case when os.d_op_id = '"+user_id+"' and os.d_end_time is not null then 1 else 0 end) DFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'D' then mo.order_id else null end)) DFlowUndoOrders,");
			sqlBuff.append(" sum(case when os.d_op_id = '"+user_id+"' and nvl(os.d_end_time,sysdate) - os.d_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'D') then 1 else 0 end) DFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'D', mo.isAlert, 0)) DFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.d_op_id = '"+user_id+"' and os.d_end_time is not null then (os.d_end_time - os.d_begin_time) * 24 * 60 * 60 else 0 end) DFlowDealTimes,");
			sqlBuff.append(" sum(case when os.x_op_id = '"+user_id+"' and os.x_end_time is not null then 1 else 0 end) XFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'X' then mo.order_id else null end)) XFlowUndoOrders,");
			sqlBuff.append(" sum(case when os.x_op_id = '"+user_id+"' and nvl(os.x_end_time,sysdate) - os.x_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'X') then 1 else 0 end) XFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'X', mo.isAlert, 0)) XFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.x_op_id = '"+user_id+"' and os.x_end_time is not null then (os.x_end_time - os.x_begin_time) * 24 * 60 * 60 else 0 end) XFlowDealTimes,");
			sqlBuff.append(" sum(case when os.f_op_id = '"+user_id+"' and os.f_end_time is not null then 1 else 0 end) FFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'F' then mo.order_id else null end)) FFlowUndoOrders,");
			sqlBuff.append(" sum(case when os.f_op_id = '"+user_id+"' and nvl(os.f_end_time,sysdate) - os.f_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'F') then 1 else 0 end) FFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'F', mo.isAlert, 0)) FFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.f_op_id = '"+user_id+"' and os.f_end_time is not null then (os.f_end_time - os.f_begin_time) * 24 * 60 * 60 else 0 end) FFlowDealTimes,");
			sqlBuff.append(" sum(case when os.h_op_id = '"+user_id+"' and os.h_end_time is not null then 1 else 0 end) HFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'H' then mo.order_id else null end)) HFlowUndoOrders,");
			sqlBuff.append(" sum(case when os.h_op_id = '"+user_id+"' and nvl(os.h_end_time,sysdate) - os.h_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'H') then 1 else 0 end) HFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'H', mo.isAlert, 0)) HFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.h_op_id = '"+user_id+"' and os.h_end_time is not null then (os.h_end_time - os.h_begin_time) * 24 * 60 * 60 else 0 end) HFlowDealTimes,");
			sqlBuff.append(" sum(case when os.j_op_id = '"+user_id+"' and os.j_end_time is not null then 1 else 0 end) JFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'J' then mo.order_id else null end)) JFlowUndoOrders,");
			sqlBuff.append(" sum(case when os.j_op_id = '"+user_id+"' and nvl(os.j_end_time,sysdate) - os.j_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'J') then 1 else 0 end) JFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'J', mo.isAlert, 0)) JFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.j_op_id = '"+user_id+"' and os.j_end_time is not null then (os.j_end_time - os.j_begin_time) * 24 * 60 * 60 else 0 end) JFlowDealTimes,");
			sqlBuff.append(" sum(case when os.l_op_id = '"+user_id+"' and os.l_end_time is not null then 1 else 0 end) LFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'L' then mo.order_id else null end)) LFlowUndoOrders,");
			sqlBuff.append(" sum(case when os.l_op_id = '"+user_id+"' and nvl(os.l_end_time,sysdate) - os.l_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'L') then 1 else 0 end) LFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'L', mo.isAlert, 0)) LFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.l_op_id = '"+user_id+"' and os.l_end_time is not null then (os.l_end_time - os.l_begin_time) * 24 * 60 * 60 else 0 end) LFlowDealTimes,");
			sqlBuff.append(" count(distinct case when mo.lock_user_id = '"+user_id+"' then mo.order_id else null end) lockedOrders,");
			sqlBuff.append(" 'ECS' source_from from (select oe.order_id,oe.flow_trace_id,oe.lock_user_id,");
			sqlBuff.append(" oe.source_from,(select case when ew.warning_time_1 is not null and");
			sqlBuff.append(" (sysdate - oe.last_deal_time) * 24 * 60 > ew.warning_time_1 then 1 else 0 end isAlert ");
			sqlBuff.append(" from es_order_warning ew,es_order_items_ext eie where ew.order_origin = oe.order_city_code");
			sqlBuff.append(" and ew.flow_id = oe.flow_trace_id and ew.order_from = oe.order_from and ew.product_type = eie.goods_type and eie.order_id=oe.order_id) isAlert,oe.tid_time");
			sqlBuff.append(" from es_order_ext oe ");
			sqlBuff.append(" where oe.source_from = '"+ManagerUtils.getSourceFrom()+"' and oe.visible_status='0'");
			sqlBuff.append(" and (oe.is_zhwj = '0' or oe.is_zhwj is null)");
			sqlBuff.append(" and oe.REFUND_DEAL_TYPE = '01'");
			sqlBuff.append(" and (oe.wm_isreservation_from = '0' or oe.wm_isreservation_from is null)");
			sqlBuff.append(" and (oe.order_if_cancel = '0' or oe.order_if_cancel is null)");
			sqlBuff.append(" and not exists (select 1 from es_esearch_expinst ei, es_esearch_specvalues es where ei.out_tid = oe.out_tid and ei.key_id = es.key_id and ei.record_status = '0' and es.is_order_sys_view = 'N')");
			sqlBuff.append(" and oe.tid_time >= to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
			sqlBuff.append(" and oe.tid_time <= to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
			//是否超级管理员，1是，0否
			if(founder != 1){
				sqlBuff.append(" and exists (select 1 from es_user_role  ur, es_role_auth  ra, es_order_role ord where ur.roleid = ra.roleid and ra.authid = ord.auth_id and ord.order_id = oe.order_id and oe.source_from = ur.source_from and ur.source_from = ra.source_from and ra.source_from = ord.source_from and ur.userid = '"+user_id+"')");
			}
			String p_ordstd =System.getProperty("JAVA_BUSI_ORDCTN");
			if(!StringUtils.isEmpty(p_ordstd)){
				if("yes".equals(p_ordstd)){
					sqlBuff.append(" and  exists (select 1 from es_order_queue_his qhis where qhis.object_id = oe.out_tid)");
				}else{
					sqlBuff.append(" and not exists (select 1 from es_order_queue_his qhis where qhis.object_id = oe.out_tid)");
				}
			}
			sqlBuff.append(" ) mo left join es_order_stats_tache os");
			sqlBuff.append(" on os.order_id = mo.order_id");
			sqlBuff.append(" where mo.source_from = '"+ManagerUtils.getSourceFrom()+"') est");
			sqlBuff.append(" where source_from = '"+ManagerUtils.getSourceFrom()+"'");
//			logger.info("进行正常单数据查询sql："+sqlBuff.toString());
			map = this.baseDaoSupport.queryForMap(sqlBuff.toString(),start_time,end_time);			
			if(null != map && map.size() > 0){
				//B环节
				String bfloworders = map.get("bfloworders").toString();
				String bflowdealtimes = map.get("bflowdealtimes").toString();
				if("".equals(bfloworders) || "0".equals(bfloworders)){
					map.put("bflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("bflowavgtimes", getAvgTimes(bfloworders,bflowdealtimes));
				}			
				// C 预拣货
				String cfloworders = map.get("cfloworders").toString();
				String cflowdealtimes = map.get("cflowdealtimes").toString();
				if("".equals(cfloworders) || "0".equals(cfloworders)){
					map.put("cflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("cflowavgtimes", getAvgTimes(cfloworders,cflowdealtimes));
				}			
				//D 开户
				String dfloworders = map.get("dfloworders").toString();
				String dflowdealtimes = map.get("dflowdealtimes").toString();
				if("".equals(dfloworders) || "0".equals(dfloworders)){
					map.put("dflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("dflowavgtimes", getAvgTimes(dfloworders,dflowdealtimes));
				}
				//F 质检稽核
				String ffloworders = map.get("ffloworders").toString();
				String fflowdealtimes = map.get("fflowdealtimes").toString();
				if("".equals(ffloworders) || "0".equals(ffloworders)){
					map.put("fflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("fflowavgtimes", getAvgTimes(ffloworders,fflowdealtimes));
				}
				//H 发货
				String hfloworders = map.get("hfloworders").toString();
				String hflowdealtimes = map.get("hflowdealtimes").toString();
				if("".equals(hfloworders) || "0".equals(hfloworders)){
					map.put("hflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("hflowavgtimes", getAvgTimes(hfloworders,hflowdealtimes));
				}
				//J 回单
				String jfloworders = map.get("jfloworders").toString();
				String jflowdealtimes = map.get("jflowdealtimes").toString();
				if("".equals(jfloworders) || "0".equals(jfloworders)){
					map.put("jflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("jflowavgtimes", getAvgTimes(jfloworders,jflowdealtimes));
				}
				//L 订单归档
				String lfloworders = map.get("lfloworders").toString();
				String lflowdealtimes = map.get("lflowdealtimes").toString();
				if("".equals(lfloworders) || "0".equals(lfloworders)){
					map.put("lflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("lflowavgtimes", getAvgTimes(lfloworders,lflowdealtimes));
				}
				// X 写卡
				String xfloworders = map.get("xfloworders").toString();
				String xflowdealtimes = map.get("xflowdealtimes").toString();
				if("".equals(xfloworders) || "0".equals(xfloworders)){
					map.put("xflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("xflowavgtimes", getAvgTimes(xfloworders,xflowdealtimes));
				}
				//Y 业务办理
				String yfloworders = map.get("yfloworders").toString();
				String yflowdealtimes = map.get("yflowdealtimes").toString();
				if("".equals(yfloworders) || "0".equals(yfloworders)){
					map.put("yflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("yflowavgtimes", getAvgTimes(yfloworders,yflowdealtimes));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 统计每天工单量
	 * @param user_id
	 * @param founder
	 * @param start_time
	 * @param end_time
	 * @return
	 */
	public Map<String,String> queryDayOrders(String user_id ,int founder, String start_time , String end_time){
	/*  改成通过定时任务将原有的查询插入es_order_work_line_pic 表 然后直接查询es_order_work_line_pic
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlBuff = new StringBuffer();
		StringBuffer sqlBuffHis = new StringBuffer();
		Map<String,String> map = new HashMap<String,String>(0);
		try{
			//正常订单
			sqlBuff.append("select oe.order_id,oe.tid_time create_time from es_order_ext oe");
			sqlBuff.append(" where oe.source_from = 'ECS' and oe.tid_time >= to_date('"+start_time+"', 'yyyy-mm-dd hh24:mi:ss')");
			sqlBuff.append(" and oe.tid_time <= to_date('"+end_time+"', 'yyyy-mm-dd hh24:mi:ss')");
			//是否超级管理员，1是，0否
//			if(founder != 1){
//				sqlBuff.append(" and exists (select 1 from es_user_role  ur, es_role_auth  ra, es_order_role ord where ur.roleid = ra.roleid and ra.authid = ord.auth_id and ord.order_id = oe.order_id and oe.source_from = ur.source_from and ur.source_from = ra.source_from and ra.source_from = ord.source_from and ur.userid = '"+user_id+"')");
//			}
			//归档订单
			sqlBuffHis.append(" union all select oe.order_id,oe.tid_time create_time from es_order_ext_his oe");
			sqlBuffHis.append(" where oe.source_from = 'ECS' and oe.tid_time >= to_date('"+start_time+"', 'yyyy-mm-dd hh24:mi:ss')");
			sqlBuffHis.append(" and oe.tid_time <= to_date('"+end_time+"', 'yyyy-mm-dd hh24:mi:ss')");
			//是否超级管理员，1是，0否
//			if(founder != 1){
//				sqlBuffHis.append(" and exists (select 1 from es_user_role  ur, es_role_auth  ra, es_order_role ord where ur.roleid = ra.roleid and ra.authid = ord.auth_id and ord.order_id = oe.order_id and oe.source_from = ur.source_from and ur.source_from = ra.source_from and ra.source_from = ord.source_from and ur.userid = '"+user_id+"')");
//			}
			
			sql.append("select l1.createtime, l1.order_by_time, nvl(l2.dayorders, 0) dayorders from ( ");
			sql.append("select to_char(to_date('"+start_time+"', 'yyyy-mm-dd hh24:mi:ss') + (level - 1), 'mm.dd') createTime, ");
			sql.append("to_char(to_date('"+start_time+"', 'yyyy-mm-dd hh24:mi:ss') + (level - 1), 'yyyy.mm.dd') order_by_time ");
			sql.append("from dual connect by level < to_date('"+end_time+"', 'yyyy-mm-dd hh24:mi:ss') -to_date('"+start_time+"', 'yyyy-mm-dd hh24:mi:ss') + 2) l1 ");
			sql.append(" left join (");
			sql.append("select to_char(create_time,'mm.dd') createTime,to_char(create_time, 'yyyy.mm.dd') order_by_time,count(distinct order_id) dayOrders from (");
			sql.append(sqlBuff);
			sql.append(sqlBuffHis);
			sql.append(" ) group by to_char(create_time,'mm.dd'),to_char(create_time, 'yyyy.mm.dd')");
			sql.append(" ) l2 on l1.order_by_time = l2.order_by_time order by l1.order_by_time");
			*/
		    Map<String,String> map = new HashMap<String,String>(0);
		try{
			String sql ="select createtime,dayorders  from es_order_work_line_pic where to_date(createtime,'mm.dd') >=to_date('"+start_time+"','yyyy-mm-dd hh24:mi:ss') and  to_date(createtime,'mm.dd') <=to_date('"+end_time+"', 'yyyy-mm-dd hh24:mi:ss') order by to_date(order_by_time,'yyyy.mm.dd')";
			
			List list = this.baseDaoSupport.queryForList(sql.toString());
			StringBuffer xaxis = new StringBuffer("");
			StringBuffer series = new StringBuffer("");
			if(null != list && list.size() > 0){
				for(int i = 0 ; i < list.size() ; i++){
					Map m = (Map)list.get(i);
					xaxis.append(m.get("createtime")).append(",");
					series.append(m.get("dayorders")).append(",");
				}
				map.put("xaxis", xaxis.toString());
				map.put("series", series.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
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
			return secToTime(Math.round(Double.parseDouble(totalTimes))/Long.parseLong(orders));
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
	
	/**
	 * 转换MAP中的空值为0
	 * @param m
	 */
	public void initMapNullToZero(Map<String,String> m){
		Iterator<String> iter = m.keySet().iterator();
		String key = null;
		Object value = null;
		try{
			while(iter.hasNext()){
				key = iter.next();
				value = m.get(key);
				if(value instanceof BigDecimal){
					BigDecimal b = (BigDecimal)value;
					if(StringUtils.isEmpty(b.toString())){
						m.put(key, "0");
					}
				}else if(value instanceof Integer){
					Integer i = (Integer)value;
					if(StringUtils.isEmpty(i.toString())){
						m.put(key, "0");
					}
				}else{
					if(StringUtils.isEmpty(m.get(key).toString())){
						m.put(key, "0");
					}
				}
			}
		}catch(Exception e){
			logger.info(e);
		}
	}
	
	/**
	 * 统计饼图数据（已处理（存在L环节）、未处理（非L环节且未退单）、退单）
	 * @param start_time
	 * @param end_time
	 * @return
	 */
	public Map<String,String> queryPieData(String start_time , String end_time){
		StringBuffer sqlBuf = new StringBuffer();
		Map<String,String> data = null;
		sqlBuf.append("select count(distinct case when oos.refund_deal_type = '02' then oos.order_id end) refundorders,");
		sqlBuf.append(" count(distinct case when oos.flow_trace_id = 'L' then oos.order_id end) dealorders,");
		sqlBuf.append(" count(distinct case when oos.flow_trace_id != 'L' and oos.refund_deal_type != '02' then oos.order_id end) delayorders");
		sqlBuf.append(" from (select oe.order_id, oe.refund_deal_type, oe.flow_trace_id from es_order_ext oe");
		sqlBuf.append(" where oe.tid_time >= to_date(?, 'yyyy-mm-dd hh24:mi:ss') and oe.tid_time <= to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
		sqlBuf.append(" union all select oeh.order_id, oeh.refund_deal_type, oeh.flow_trace_id from es_order_ext_his oeh");
		sqlBuf.append(" where oeh.tid_time >= to_date(?, 'yyyy-mm-dd hh24:mi:ss') and oeh.tid_time <= to_date(?, 'yyyy-mm-dd hh24:mi:ss')) oos");
		try{
			data = this.baseDaoSupport.queryForMap(sqlBuf.toString(), start_time,end_time, start_time,end_time);
		}catch(Exception e){
			logger.info("queryPieData fail" + e.getMessage(),e);
		}
		return data;
	}
	
	public Map<String,String> queryOrderStatisticsDataAll(String user_id ,int founder, String start_time , String end_time){
		StringBuffer sqlBuff = new StringBuffer();
		Map map = null;
		try{
			sqlBuff.append("select est.totalorders,est.delayorders,est.myorders,est.bfloworders,est.bflowundoorders,");
			sqlBuff.append(" est.bflowtimeoutorders,est.bflowalertorders,est.bflowdealtimes,est.cfloworders,est.cflowundoorders,");
			sqlBuff.append(" est.cflowtimeoutorders,est.cflowalertorders,est.cflowdealtimes,est.yfloworders,est.yflowundoorders,");
			sqlBuff.append(" est.yflowtimeoutorders,est.yflowalertorders,est.yflowdealtimes,est.dfloworders,est.dflowundoorders,");
			sqlBuff.append(" est.dflowtimeoutorders,est.dflowalertorders,est.dflowdealtimes,est.xfloworders,est.xflowundoorders,");
			sqlBuff.append(" est.xflowtimeoutorders,est.xflowalertorders,est.xflowdealtimes,est.ffloworders,est.fflowundoorders,");
			sqlBuff.append(" est.fflowtimeoutorders,est.fflowalertorders,est.fflowdealtimes,est.hfloworders,est.hflowundoorders,");
			sqlBuff.append(" est.hflowtimeoutorders,est.hflowalertorders,est.hflowdealtimes,est.jfloworders,est.jflowundoorders,");
			sqlBuff.append(" est.jflowtimeoutorders,est.jflowalertorders,est.jflowdealtimes,est.lfloworders,est.lflowundoorders,");
			sqlBuff.append(" est.lflowtimeoutorders,est.lflowalertorders,est.lflowdealtimes,est.delayAlertOrders,est.lockedOrders,est.source_from");
			sqlBuff.append(" from (select count(distinct(case when mo.flow_trace_id in ('B','C','Y','D','X','F','H','J','L') then mo.order_id else null end)) totalOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id,'B',1,0)) delayOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'B' and sysdate - (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'B') > mo.tid_time then mo.order_id else null  end)) delayAlertOrders,");
			sqlBuff.append(" count(1) myOrders,");
			sqlBuff.append(" sum(case when os.b_end_time is not null then 1 else 0 end) BFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'B' then mo.order_id else null end)) BFlowUndoOrders,");
			sqlBuff.append(" sum(case when nvl(os.b_end_time,sysdate) - os.b_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'B') then 1 else 0 end) BFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'B', mo.isAlert, 0)) BFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.b_end_time is not null then (os.b_end_time - os.b_begin_time) * 24 * 60 * 60 else 0 end) BFlowDealTimes,");
			sqlBuff.append(" sum(case when os.c_end_time is not null then 1 else 0 end) CFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'C' then mo.order_id else null end)) CFlowUndoOrders,");
			sqlBuff.append(" sum(case when nvl(os.c_end_time,sysdate) - os.c_begin_time > (select p.codea  from es_dc_public p where p.stype = 20151211111   and pkey = 'C') then  1 else  0 end) CFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'C', mo.isAlert, 0)) CFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.c_end_time is not null then (os.c_end_time - os.c_begin_time) * 24 * 60 * 60 else 0 end) CFlowDealTimes,");
			sqlBuff.append(" sum(case when os.y_end_time is not null then 1 else 0 end) YFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'Y' then mo.order_id else null end)) YFlowUndoOrders,");
			sqlBuff.append(" sum(case when nvl(os.y_end_time,sysdate) - os.y_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'Y') then 1 else 0 end) YFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'Y', mo.isAlert, 0)) YFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.y_end_time is not null then (os.y_end_time - os.y_begin_time) * 24 * 60 * 60 else 0 end) YFlowDealTimes,");
			sqlBuff.append(" sum(case when os.d_end_time is not null then 1 else 0 end) DFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'D' then mo.order_id else null end)) DFlowUndoOrders,");
			sqlBuff.append(" sum(case when nvl(os.d_end_time,sysdate) - os.d_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'D') then 1 else 0 end) DFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'D', mo.isAlert, 0)) DFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.d_end_time is not null then (os.d_end_time - os.d_begin_time) * 24 * 60 * 60 else 0 end) DFlowDealTimes,");
			sqlBuff.append(" sum(case when os.x_end_time is not null then 1 else 0 end) XFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'X' then mo.order_id else null end)) XFlowUndoOrders,");
			sqlBuff.append(" sum(case when nvl(os.x_end_time,sysdate) - os.x_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'X') then 1 else 0 end) XFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'X', mo.isAlert, 0)) XFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.x_end_time is not null then (os.x_end_time - os.x_begin_time) * 24 * 60 * 60 else 0 end) XFlowDealTimes,");
			sqlBuff.append(" sum(case when os.f_end_time is not null then 1 else 0 end) FFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'F' then mo.order_id else null end)) FFlowUndoOrders,");
			sqlBuff.append(" sum(case when nvl(os.f_end_time,sysdate) - os.f_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'F') then 1 else 0 end) FFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'F', mo.isAlert, 0)) FFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.f_end_time is not null then (os.f_end_time - os.f_begin_time) * 24 * 60 * 60 else 0 end) FFlowDealTimes,");
			sqlBuff.append(" sum(case when os.h_end_time is not null then 1 else 0 end) HFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'H' then mo.order_id else null end)) HFlowUndoOrders,");
			sqlBuff.append(" sum(case when nvl(os.h_end_time,sysdate) - os.h_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'H') then 1 else 0 end) HFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'H', mo.isAlert, 0)) HFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.h_end_time is not null then (os.h_end_time - os.h_begin_time) * 24 * 60 * 60 else 0 end) HFlowDealTimes,");
			sqlBuff.append(" sum(case when os.j_end_time is not null then 1 else 0 end) JFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'J' then mo.order_id else null end)) JFlowUndoOrders,");
			sqlBuff.append(" sum(case when nvl(os.j_end_time,sysdate) - os.j_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'J') then 1 else 0 end) JFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'J', mo.isAlert, 0)) JFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.j_end_time is not null then (os.j_end_time - os.j_begin_time) * 24 * 60 * 60 else 0 end) JFlowDealTimes,");
			sqlBuff.append(" sum(case when os.l_end_time is not null then 1 else 0 end) LFlowOrders,");
			sqlBuff.append(" count(distinct(case when mo.flow_trace_id = 'L' then mo.order_id else null end)) LFlowUndoOrders,");
			sqlBuff.append(" sum(case when nvl(os.l_end_time,sysdate) - os.l_begin_time > (select p.codea from es_dc_public p where p.stype = 20151211111 and pkey = 'L') then 1 else 0 end) LFlowTimeoutOrders,");
			sqlBuff.append(" sum(decode(mo.flow_trace_id, 'L', mo.isAlert, 0)) LFlowAlertOrders,");
			sqlBuff.append(" sum(case when os.l_end_time is not null then (os.l_end_time - os.l_begin_time) * 24 * 60 * 60 else 0 end) LFlowDealTimes,");
			sqlBuff.append(" count(distinct case when mo.lock_user_id is not null then mo.order_id else null end) lockedOrders,");
			sqlBuff.append(" 'ECS' source_from from (select oe.order_id,oe.flow_trace_id,oe.lock_user_id,");
			sqlBuff.append(" oe.source_from,(select case when ew.warning_time_1 is not null and");
			sqlBuff.append(" (sysdate - oe.last_deal_time) * 24 * 60 > ew.warning_time_1 then 1 else 0 end isAlert ");
			sqlBuff.append(" from es_order_warning ew,es_order_items_ext eie where ew.order_origin = oe.order_city_code");
			sqlBuff.append(" and ew.flow_id = oe.flow_trace_id and ew.order_from = oe.order_from and ew.product_type = eie.goods_type and eie.order_id=oe.order_id) isAlert,oe.tid_time");
			sqlBuff.append(" from es_order_ext oe ");
			sqlBuff.append(" where oe.source_from = '"+ManagerUtils.getSourceFrom()+"' and oe.visible_status='0'");
			sqlBuff.append(" and (oe.is_zhwj = '0' or oe.is_zhwj is null)");
			sqlBuff.append(" and oe.REFUND_DEAL_TYPE = '01'");
			sqlBuff.append(" and (oe.wm_isreservation_from = '0' or oe.wm_isreservation_from is null)");
			sqlBuff.append(" and (oe.order_if_cancel = '0' or oe.order_if_cancel is null)");
			sqlBuff.append(" and not exists (select 1 from es_esearch_expinst ei, es_esearch_specvalues es where ei.out_tid = oe.out_tid and ei.key_id = es.key_id and ei.record_status = '0' and es.is_order_sys_view = 'N')");
			sqlBuff.append(" and oe.tid_time >= to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
			sqlBuff.append(" and oe.tid_time <= to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
			//是否超级管理员，1是，0否
			//if(founder != 1){
			//	sqlBuff.append(" and exists (select 1 from es_user_role  ur, es_role_auth  ra, es_order_role ord where ur.roleid = ra.roleid and ra.authid = ord.auth_id and ord.order_id = oe.order_id and oe.source_from = ur.source_from and ur.source_from = ra.source_from and ra.source_from = ord.source_from and ur.userid = '"+user_id+"')");
			//}
			String p_ordstd =System.getProperty("JAVA_BUSI_ORDCTN");
			if(!StringUtils.isEmpty(p_ordstd)){
				if("yes".equals(p_ordstd)){
					sqlBuff.append(" and  exists (select 1 from es_order_queue_his qhis where qhis.object_id = oe.out_tid)");
				}else{
					sqlBuff.append(" and not exists (select 1 from es_order_queue_his qhis where qhis.object_id = oe.out_tid)");
				}
			}
			sqlBuff.append(" ) mo left join es_order_stats_tache os");
			sqlBuff.append(" on os.order_id = mo.order_id");
			sqlBuff.append(" where mo.source_from = '"+ManagerUtils.getSourceFrom()+"') est");
			sqlBuff.append(" where source_from = '"+ManagerUtils.getSourceFrom()+"'");
//			logger.info("进行正常单数据查询sql："+sqlBuff.toString());
			map = this.baseDaoSupport.queryForMap(sqlBuff.toString(),start_time,end_time);			
			if(null != map && map.size() > 0){
				//B环节
				String bfloworders = map.get("bfloworders").toString();
				String bflowdealtimes = map.get("bflowdealtimes").toString();
				if("".equals(bfloworders) || "0".equals(bfloworders)){
					map.put("bflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("bflowavgtimes", getAvgTimes(bfloworders,bflowdealtimes));
				}			
				// C 预拣货
				String cfloworders = map.get("cfloworders").toString();
				String cflowdealtimes = map.get("cflowdealtimes").toString();
				if("".equals(cfloworders) || "0".equals(cfloworders)){
					map.put("cflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("cflowavgtimes", getAvgTimes(cfloworders,cflowdealtimes));
				}			
				//D 开户
				String dfloworders = map.get("dfloworders").toString();
				String dflowdealtimes = map.get("dflowdealtimes").toString();
				if("".equals(dfloworders) || "0".equals(dfloworders)){
					map.put("dflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("dflowavgtimes", getAvgTimes(dfloworders,dflowdealtimes));
				}
				//F 质检稽核
				String ffloworders = map.get("ffloworders").toString();
				String fflowdealtimes = map.get("fflowdealtimes").toString();
				if("".equals(ffloworders) || "0".equals(ffloworders)){
					map.put("fflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("fflowavgtimes", getAvgTimes(ffloworders,fflowdealtimes));
				}
				//H 发货
				String hfloworders = map.get("hfloworders").toString();
				String hflowdealtimes = map.get("hflowdealtimes").toString();
				if("".equals(hfloworders) || "0".equals(hfloworders)){
					map.put("hflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("hflowavgtimes", getAvgTimes(hfloworders,hflowdealtimes));
				}
				//J 回单
				String jfloworders = map.get("jfloworders").toString();
				String jflowdealtimes = map.get("jflowdealtimes").toString();
				if("".equals(jfloworders) || "0".equals(jfloworders)){
					map.put("jflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("jflowavgtimes", getAvgTimes(jfloworders,jflowdealtimes));
				}
				//L 订单归档
				String lfloworders = map.get("lfloworders").toString();
				String lflowdealtimes = map.get("lflowdealtimes").toString();
				if("".equals(lfloworders) || "0".equals(lfloworders)){
					map.put("lflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("lflowavgtimes", getAvgTimes(lfloworders,lflowdealtimes));
				}
				// X 写卡
				String xfloworders = map.get("xfloworders").toString();
				String xflowdealtimes = map.get("xflowdealtimes").toString();
				if("".equals(xfloworders) || "0".equals(xfloworders)){
					map.put("xflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("xflowavgtimes", getAvgTimes(xfloworders,xflowdealtimes));
				}
				//Y 业务办理
				String yfloworders = map.get("yfloworders").toString();
				String yflowdealtimes = map.get("yflowdealtimes").toString();
				if("".equals(yfloworders) || "0".equals(yfloworders)){
					map.put("yflowavgtimes", "00天00时00分00秒");
				}else{
					map.put("yflowavgtimes", getAvgTimes(yfloworders,yflowdealtimes));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
}
