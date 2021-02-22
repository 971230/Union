/**
 * 
 */
package com.ztesoft.net.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.ISystemPageManager;

/**
 * @author zengjunhua
 *
 */
public class SystemPageManager extends BaseSupport implements ISystemPageManager{

	@Override
	public List getNoticeList() {
		// TODO Auto-generated method stub
		String sql = "select n.title,u.realname,n.content,n.create_time,n.begin_time,n.end_time from es_notices n,es_adminUser u where n.user_id=u.userid and rownum<=5 and n.source_from=u.source_from and n.source_from='"
				+ ManagerUtils.getSourceFrom()
				+ "' order by n.create_time desc";
		return this.baseDaoSupport.queryForList(sql);
	}

	@Override
	public List getPendingOrderList() {
		// TODO Auto-generated method stub
		AdminUser admin =ManagerUtils.getAdminUser();
		String sql = " select a.order_id,a.create_time,(select t.pname from es_dc_public_ext t where t.pkey=a.status and t.stype='DIC_ORDER_STATUS') status,"
				+ " (select t1.pname from es_dc_public_ext t1 "
				+ " where t1.pkey=b.order_from and t1.stype='source_from') order_from,c.phone_owner_name,c.goodsname,d.ship_name,d.ship_mobile,e.phone_num from es_order a, es_order_ext b,es_order_extvtl c,es_delivery d,es_order_items_ext e "
				+ " where a.order_id=b.order_id and a.source_from=b.source_from and b.order_id=c.order_id and b.source_from=c.source_from and c.order_id=d.order_id and c.source_from=d.source_from and d.order_id=e.order_id and d.source_from=e.source_from and  rownum<=5 ";
				if(!"1".equals(admin.getFounder()+"")&&!StringUtils.isEmpty(admin.getFounder()+"")){
					sql += " and exists (select 1 from es_order_lock ol where ol.order_id = a.order_id  and b.flow_trace_id = ol.tache_code and ol.lock_status='1' and ol.lock_user_id = '"+ManagerUtils.getAdminUser().getUserid()+"')";
				}
				sql += " and a.status not in(11,12) order by a.create_time desc";
		return this.baseDaoSupport.queryForList(sql);
	}

	@Override
	public List<Map> getOrderListCount() {
		// TODO Auto-generated method stub
		String tache_code ="BDXHF";
		AdminUser user = ManagerUtils.getAdminUser();
		
		String tacheAuth = user.getTacheAuth();

		String sql = "select count(1) count, a.flow_trace_id"
				+" from es_order_ext a, es_ORDER_STATS_TACHE t, es_order_lock l"
				+" where a.order_id = t.order_id"
				+" and a.order_id = l.order_id"
				+" and a.flow_trace_id = l.tache_code"
				+" and l.lock_user_id = '"+user.getUserid()+"'";
				if(user.getFounder() != 1) {
					sql = sql + " and a.flow_trace_id in( "+user.getTacheAuth()+" )";
				}
				sql = sql +" and a.source_from = t.source_from"
						+" group by a.flow_trace_id";
				List list = this.baseDaoSupport.queryForList(sql);
				List resultList = new ArrayList();
				List list3 = new ArrayList();

				String result_tache_code = "";//DBXF=3
				for (Object object : list) {
					Map<String, Object> map = (Map)object;
					String traceId = (String)map.get("flow_trace_id");
					if(tache_code.indexOf(traceId)!=-1){
						result_tache_code += traceId;
						resultList.add(object);
					}else {
						list3.add(object);
					}
				}
				int index = 5-result_tache_code.length();
				if(result_tache_code.indexOf("X") == -1 && result_tache_code.indexOf("D") == -1){
					index++;
				}
				for (Object object : list3) {
					if(index > 0){
						resultList.add(object);
						index --;
					}else{
						break;
					}
				}
			return resultList;
	}

	@Override
	public List getOrderList(List<String> trace_id) {
		AdminUser user = ManagerUtils.getAdminUser();
		
		String sql = "";
		for(int i=0;i<trace_id.size();i++) {
			sql += " select a.flow_trace_id,t.order_id,ceil(sysdate -t.s_start_time ) timeout from es_order_ext a,es_ORDER_STATS_TACHE t, es_order_lock l where a.order_id=t.order_id and rownum<=5 and a.flow_trace_id='"+ trace_id.get(i) +"' and a.source_from=t.source_from and a.source_from='"
					+ ManagerUtils.getSourceFrom() + "'"
					+" and a.order_id = l.order_id"
					+" and a.flow_trace_id = l.tache_code"
					+" and l.lock_user_id = '"+user.getUserid()+"'";
			if(i<trace_id.size()-1) {
				sql = sql + " union all ";
			}
		}
		//System.out.print(sql);
		if(!StringUtil.isEmpty(sql))
			return this.baseDaoSupport.queryForList(sql);
		else
			return new ArrayList<Map<String, Integer>>();
		
	}

	@Override
	public List getOrderFList() {
		AdminUser user = ManagerUtils.getAdminUser();
		if(!StringUtil.isEmpty(user.getTacheAuth()) && user.getTacheAuth().indexOf("F") != -1 || user.getFounder()==1 ){
			String sqlF = "select t.order_id,ceil(sysdate -t.s_start_time ) timeout from es_order_ext a,es_ORDER_STATS_TACHE t where a.order_id=t.order_id and rownum<=5 and a.flow_trace_id='F' and a.source_from=t.source_from and a.source_from='"
					+ ManagerUtils.getSourceFrom()
					+ "'order by t.s_start_time desc ";
			return this.baseDaoSupport.queryForList(sqlF);
		}else{
			return new ArrayList<Map>();
		}
	}

	@Override
	public List getOrderCList() {
		AdminUser user = ManagerUtils.getAdminUser();
		if(!StringUtil.isEmpty(user.getTacheAuth()) && user.getTacheAuth().indexOf("C") != -1 || user.getFounder()==1 ){
			String sqlC = "select t.order_id,ceil(sysdate -t.s_start_time ) timeout from es_order_ext a,es_ORDER_STATS_TACHE t where a.order_id=t.order_id and rownum<=5 and a.flow_trace_id='C' and a.source_from=t.source_from and a.source_from='"
					+ ManagerUtils.getSourceFrom()
					+ "'order by t.s_start_time desc ";
			return this.baseDaoSupport.queryForList(sqlC);
		} else {
			return new ArrayList<Map>();
		}
	}

	@Override
	public List getOrderHList() {
		AdminUser user = ManagerUtils.getAdminUser();
		if(!StringUtil.isEmpty(user.getTacheAuth()) && user.getTacheAuth().indexOf("H") != -1 || user.getFounder()==1 ){
			String sqlH = "select t.order_id,ceil(sysdate -t.s_start_time ) timeout from es_order_ext a,es_ORDER_STATS_TACHE t where a.order_id=t.order_id and rownum<=5 and a.flow_trace_id='H' and a.source_from=t.source_from and a.source_from='"
					+ ManagerUtils.getSourceFrom()
					+ "'order by t.s_start_time desc ";
			return this.baseDaoSupport.queryForList(sqlH);
		}else {
			return new ArrayList<Map>();
		}
		
		
	}


}
