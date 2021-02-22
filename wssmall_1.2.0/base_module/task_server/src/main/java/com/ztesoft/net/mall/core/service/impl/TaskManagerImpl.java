package com.ztesoft.net.mall.core.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import services.AdminUserInf;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Task;
import com.ztesoft.net.mall.core.model.TaskDetail;
import com.ztesoft.net.mall.core.service.ITaskManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class TaskManagerImpl extends BaseSupport implements ITaskManager {

	AdminUserInf  adminUserServ;
	@Override
	public Page searchTaskList(String node_level,String org_id,String lan_code,String city_code,String task_type,String task_name,
								String state,String finished,String create_time,int pageNo,int pageSize){
		AdminUser adminUser = ManagerUtils.getAdminUser(); 
		
		String sql = " select task_id,task_code,task_name,to_char(create_time,'yyyy-mm-dd hh24:mi:ss') create_time, d.service_offer_name task_type," +
				     " e.region_name as lan_name,c.region_name region_name,task_num," +
				     " (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num, a.state, a.finished, a.op_id, a.p_task_id, a.task_cate " +
				     " from es_task a left join es_service_offer d on a.task_type=d.service_code " +
				     " left outer join es_common_region e on a.lan_code = e.region_id " +
				     " left outer join es_common_region c on a.city_code = c.region_id " +
				     " where 1=1  #cond order by create_time desc ";
		StringBuilder whereCond = new StringBuilder();
		List pList = new ArrayList();
		if(StringUtils.isNotEmpty(lan_code)){
			whereCond.append(" and a.lan_code= '" +lan_code+"'");
		}
		
		if(StringUtils.isNotEmpty(city_code)){
			whereCond.append(" and a.city_code =  '"+city_code+"'");
		}
		
		if(StringUtils.isNotEmpty(task_name)){
			whereCond.append(" and a.task_name like '%"+task_name+"%'");
		}
		
		if(StringUtils.isNotEmpty(task_type)){
			whereCond.append(" and a.task_type = '"+task_type+"'");
		}
		
		if(StringUtils.isNotEmpty(create_time)){
			whereCond.append(" and to_char(a.create_time,'yyyy-mm-dd') = '"+create_time+"'");
		}
		
		if(StringUtils.isNotEmpty(state)){
			whereCond.append(" and a.state ='"+state+"'");
		}
		if(StringUtils.isNotEmpty(finished)){
			whereCond.append(" and a.finished = '"+finished+"'");
		}
		
		whereCond.append(" and a.op_id = '"+adminUser.getUserid()+"'");
		
		
		sql = sql.replaceAll("#cond", whereCond.toString());
		logger.info("查询任务列表："+sql);
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
		return page;
	}
	
	@Override
	public List searchAdminUser(String username){
		AdminUser adminUser = ManagerUtils.getAdminUser(); // 鑾峰彇鐧诲綍鐢ㄦ埛
		String admin_org_id = adminUser.getOrg_id();//褰撳墠鐧诲綍鐢ㄦ埛缁勭粐ID
		String sql = "select userid,username,realname,b.lan_id,b.region_id,c.region_name city  from es_adminuser a,es_organization b left join es_common_region c on  b.region_id=c.region_id where a.org_id=b.party_id "+
								" and a.org_id in (select party_id  from es_organization  where parent_party_id = ?) and founder = 3 ";
		if(StringUtils.isNotEmpty(username)){
			sql += " and username like '%"+username+"%'";
		}
		List list = this.baseDaoSupport.queryForList(sql, admin_org_id);
		return list;
	}
	
	@Override
	public Page getPartner(AdminUser adminUser, String lan_id, String city_id, int pageInx,
			int PageSize) {
		StringBuffer sql = new StringBuffer(" select userid, username, realname, a.lan_id, e.region_name lan_name, a.city_id, c.region_name city_name " +
											" from es_adminuser a " +
											" left join es_common_region c on a.city_id = c.region_id and c.source_from = a.source_from " +
											" left outer join es_common_region e on a.lan_id = e.region_id and e.source_from = a.source_from " +
											" where a.founder = 3 and a.source_from='" + ManagerUtils.getSourceFrom() + "' ");
		if(StringUtils.isNotEmpty(lan_id) && StringUtils.isNotEmpty(city_id)){
			sql.append(" and a.lan_id=?  and a.city_id in ( select region_id " +
					"from es_common_region connect by prior region_id= parent_region_id start with region_id=? and source_from = '" + ManagerUtils.getSourceFrom() + "' )");
		}else if(StringUtils.isNotEmpty(lan_id) && StringUtils.isEmpty(city_id)){
			if("07XX".equals(lan_id)){
				sql.append(" and a.lan_id in (select t.region_id from (select a.region_id, level lvl " +
						"from es_common_region a connect by prior a.region_id = a.parent_region_id " +
						"start with a.region_id = '07XX' and a.source_from = '" + ManagerUtils.getSourceFrom() + "') t where t.lvl=2) and a.city_id is null ");
			}else{
				sql.append(" and a.lan_id=? and a.city_id in (select t.region_id from (select a.region_id, level lvl " +
						"from es_common_region a connect by prior a.region_id = a.parent_region_id " +
						"start with a.region_id = ? and a.source_from = '" + ManagerUtils.getSourceFrom() + "') t where t.lvl=2)" );
			}
		}else if(StringUtils.isEmpty(lan_id) && StringUtils.isNotEmpty(city_id)){
			sql.append(" and a.city_id in (select a.region_id  from es_common_region a " +
					"connect by prior a.region_id = a.parent_region_id start with " +
					"a.region_id = ? and a.source_from = '" + ManagerUtils.getSourceFrom() + "') ");
		}else{
			sql.append(" and a.lan_id in (select t.region_id from (select a.region_id, level lvl " +
					"from es_common_region a connect by prior a.region_id = a.parent_region_id start with " +
					"a.region_id = '07XX' and a.source_from = '" + ManagerUtils.getSourceFrom() + "') t where t.lvl=2) ");
		}
		if(adminUser != null){
			if(StringUtils.isNotEmpty(adminUser.getUsername())){
				sql.append(" and a.username='"+adminUser.getUsername()+"'");
			}
			if(StringUtils.isNotEmpty(adminUser.getRealname())){
				sql.append(" and a.realname like '%"+adminUser.getRealname()+"%'");
			}
		}
		
		if(StringUtils.isNotEmpty(lan_id) && StringUtils.isNotEmpty(city_id)){
			return this.daoSupport.queryForPage(sql.toString(), pageInx, PageSize, lan_id, city_id);
		}else if(StringUtils.isNotEmpty(lan_id) && StringUtils.isEmpty(city_id)){
			if("07XX".equals(lan_id)){
				return this.daoSupport.queryForPage(sql.toString(), pageInx, PageSize);
			}else{
				return this.daoSupport.queryForPage(sql.toString(), pageInx, PageSize, lan_id, lan_id);
			}
		}else if(StringUtils.isNotEmpty(city_id) && StringUtils.isEmpty(lan_id)){
			return this.daoSupport.queryForPage(sql.toString(), pageInx, PageSize, city_id);
		}else{
			return this.daoSupport.queryForPage(sql.toString(), pageInx, PageSize);
		}
	}
	
	@Override
	public Map searchTaskByTaskId(String task_id){
		String sql = " select a.task_id, a.task_code, a.task_type, a.task_name, a.task_desc, a.task_num, a.lan_code, e.region_name lan_name, a.city_code, c.region_name city_name, " +
					 " a.op_id, a.create_time, a.target_rate, a.task_cate, a.p_task_id, " +
					 " (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end ) target_num, "+
					 " to_char(begin_date,'yyyy-mm-dd') begin_date, to_char(end_date,'yyyy-mm-dd') end_date, a.partner_level, a.unit, a.state, a.finished " +
					 " from es_task a " +
					 " left outer join es_common_region e on a.lan_code = e.region_id and e.source_from = a.source_from" +
					 " left outer join es_common_region c on a.city_code = c.region_id and c.source_from = a.source_from" +
					 " where a.task_id=? and a.source_from='" + ManagerUtils.getSourceFrom()+ "'";
		Map task = this.baseDaoSupport.queryForMap(sql, task_id);
		if(task != null){
			String task_cate = Const.getStrValue(task, "task_cate");
			if(Consts.TASK_CATE_0.equals(task_cate)){
				sql = "select sum(finished_num) from es_taskdetail where task_id=?";
			}else{
				sql = "select sum(finished_amount) from es_taskdetail where task_id=?";
			}
			int finished_num = this.baseDaoSupport.queryForInt(sql, task_id);
			task.put("finished_num", finished_num);
		}else{
			task = new HashMap();
		}
	
		return task;
	}
	
	public String getRegionCode(String org_id){
		org_id = org_id.substring(1);
		String sql = "select region_id from es_organization where party_id='"+org_id+"'";
		String region_code = this.baseDaoSupport.queryForString(sql);
		return region_code;
	}
	
	@Override
	public String getRegionByLanId(String lan_id){
		List datas = new ArrayList();
		if("07XX".equals(lan_id)){
			JSONArray ja = JSONArray.fromObject(datas);
			logger.info(ja.toString());
			return ja.toString();
		}else{
			String sql = "select region_id,region_name from es_common_region where area_code=? ";
			datas = this.baseDaoSupport.queryForList(sql, lan_id);
			JSONArray ja = JSONArray.fromObject(datas);
			logger.info(ja.toString());
			return ja.toString();
		}
	}
	
	@Override
	public void saveAdd(Task task){
		task.setFinished("0");
		task.setState("001");
		task.setCreate_time(DBTUtil.current());
		String target_rate = task.getTarget_rate();
		if(StringUtils.isNotEmpty(target_rate)){
			if(target_rate.indexOf("%") == -1){
				target_rate += "%";
				task.setTarget_rate(target_rate);
			}
		}
		String task_cate = task.getTask_cate();
		if(Consts.TASK_CATE_0.equals(task_cate)){
			task.setTarget_amount(null);
		}else{
			task.setTarget_amount(task.getTarget_num());
			task.setTarget_num(null);
		}
		String lan_id  = ManagerUtils.getAdminUser().getLan_id();
		String city_id = ManagerUtils.getAdminUser().getCity_id();
		if(StringUtils.isEmpty(task.getLan_code()))
			task.setLan_code(lan_id);
		if(!"07XX".equals(task.getLan_code()) && StringUtils.isEmpty(task.getCity_code()))
			task.setCity_code(city_id);
		
		this.baseDaoSupport.insert("es_task", task);
	}
	
	@Override
	public void saveEdit(Task task){
		String target_rate = task.getTarget_rate();
		if(StringUtils.isNotEmpty(target_rate)){
			if(target_rate.indexOf("%") == -1){
				target_rate += "%";
				task.setTarget_rate(target_rate);
			}
		}
		
		String task_cate = task.getTask_cate();
		if(Consts.TASK_CATE_0.equals(task_cate)){
			task.setTarget_amount(null);
		}else{
			task.setTarget_amount(task.getTarget_num());
			task.setTarget_num(null);
		}
		
		String lan_id  = ManagerUtils.getAdminUser().getLan_id();
		String city_id = ManagerUtils.getAdminUser().getCity_id();
		if(StringUtils.isEmpty(task.getLan_code()))
			task.setLan_code(lan_id);
		if(!"07XX".equals(task.getLan_code()) && StringUtils.isEmpty(task.getCity_code()))
			task.setCity_code(city_id);
		
		this.baseDaoSupport.update("es_task", task, " task_id = "+task.getTask_id());
	}
	
	@Override
	public void savePartnerTask(List datas){
		String task_id = null;
		if(datas!=null && datas.size()>0){
			for(int i=0;i<datas.size();i++){
				TaskDetail taskDetail = (TaskDetail) datas.get(i);
				task_id = taskDetail.getTask_id();
				Map map = searchTaskByTaskId(task_id);
				if(map != null){
					if(Consts.TASK_CATE_0.equals(Const.getStrValue(map, "task_cate"))){
						taskDetail.setFinished_amount(null);
						taskDetail.setTask_amount(null);
						taskDetail.setFinished_num(0);
					}else{
						taskDetail.setFinished_amount(0);
						taskDetail.setTask_amount(taskDetail.getTask_num());
						taskDetail.setTask_num(null);
						taskDetail.setFinished_num(null);
					}
				}
				this.baseDaoSupport.insert("es_taskdetail", taskDetail);
			}
		}
		//淇敼浠诲姟鐘舵�佷负宸插垎瑙�
		String sql = "update es_task set state='002' where task_id=?";
		this.baseDaoSupport.execute(sql, task_id);
	}

	@Override
	public Page qryTask(String flag, String userid, int pageIndex, int pageSize) {
		//1銆佹柊涓嬪彂 2銆佸凡瀹屾垚 3銆佹湭瀹屾垚
		String sql = "";
		if("1".equals(flag)){
			sql = " select * from (select a.task_id, a.task_name, (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num, a.begin_date, a.end_date, a.create_time, a.lan_code, a.city_code, " +
								  " a.task_type, c.service_offer_name task_type_name, " +
							 	  " a.state, " +
							 	  "  decode(a.state, '001','已录入', '002','已分解', '003','已下发', '004','已撤销', '005','已删除') state_name, " +
							 	  " b.detail_id, " +
							 	  " (case when a.task_cate='0' then b.task_num when a.task_cate='1' then b.task_amount end) task_num, " +
							 	  " (case when a.task_cate='0' then b.finished_num when a.task_cate='1' then b.finished_amount end) finished_num from es_task a, es_taskdetail b,es_service_offer c where a.task_id = b.task_id and a.task_type=c.service_code " +
							 	  " and a.state='003' and a.task_cate='0' and b.finished_num=0 and b.userid=? " +
							 	  " union all " +
							 	  " select a.task_id, a.task_name, (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num, a.begin_date, a.end_date, a.create_time, a.lan_code, a.city_code, " +
								  " a.task_type, c.service_offer_name task_type_name, " +
							 	  " a.state,  decode(a.state, '001','已录入', '002','已分解', '003','已下发', '004','已撤销', '005','已删除') state_name, " +
							 	  " b.detail_id, " +
							 	  " (case when a.task_cate='0' then b.task_num when a.task_cate='1' then b.task_amount end) task_num, " +
							 	  " (case when a.task_cate='0' then b.finished_num when a.task_cate='1' then b.finished_amount end) finished_num from es_task a, es_taskdetail b,es_service_offer c where a.task_id = b.task_id and a.task_type=c.service_code " +
							 	  " and a.state='003' and a.task_cate='1' and b.finished_amount=0 and b.userid=? " +
			 	 ") ";
			
		}else if("2".equals(flag)){
			sql = " select * from (select a.task_id, a.task_name, (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num, a.begin_date, a.end_date, a.create_time, a.lan_code, a.city_code, " +
								  " a.task_type, c.service_offer_name  task_type_name, " +
							 	  " a.state, " +
							 	  "  decode(a.state, '001','已录入', '002','已分解', '003','已下发', '004','已撤销', '005','已删除') state_name, " +
							 	  " b.detail_id, " +
							 	  " (case when a.task_cate='0' then b.task_num when a.task_cate='1' then b.task_amount end) task_num, " +
							 	  " (case when a.task_cate='0' then b.finished_num when a.task_cate='1' then b.finished_amount end) finished_num "+
							 	  " from es_task a, es_taskdetail b,es_service_offer c where a.task_id = b.task_id and a.task_type=c.service_code  " +
							 	  " and a.state='003' and a.task_cate='0' and b.task_num = b.finished_num and b.userid=? "+
							 	  " union all "+
							 	  " select a.task_id, a.task_name, (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num, a.begin_date, a.end_date, a.create_time, a.lan_code, a.city_code, " +
								  " a.task_type, c.service_offer_name  task_type_name, " +
							 	  " a.state,  decode(a.state, '001','已录入', '002','已分解', '003','已下发', '004','已撤销', '005','已删除') state_name, " +
							 	  " b.detail_id, " +
							 	  " (case when a.task_cate='0' then b.task_num when a.task_cate='1' then b.task_amount end) task_num, " +
							 	  " (case when a.task_cate='0' then b.finished_num when a.task_cate='1' then b.finished_amount end) finished_num "+
							 	  " from es_task a, es_taskdetail b,es_service_offer c where a.task_id = b.task_id and a.task_type=c.service_code  " +
							 	  " and a.state='003' and a.task_cate='1' and b.task_amount = b.finished_amount and b.userid=? " +
				 ") ";
			
		}else{
			sql = " select * from (select a.task_id, a.task_name, (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num, a.begin_date, a.end_date, a.create_time, a.lan_code, a.city_code, " +
								  " a.task_type, c.service_offer_name task_type_name, " +
							 	  " a.state, " +
							 	  "  decode(a.state, '001','已录入', '002','已分解', '003','已下发', '004','已撤销', '005','已删除') state_name," +
							 	  " b.detail_id, " +
							 	  " (case when a.task_cate='0' then b.task_num when a.task_cate='1' then b.task_amount end) task_num, " +
							 	  " (case when a.task_cate='0' then b.finished_num when a.task_cate='1' then b.finished_amount end) finished_num "+
							 	  " from es_task a, es_taskdetail b,es_service_offer c where a.task_id = b.task_id and a.task_type=c.service_code  " +
							 	  " and a.state='003' and a.task_cate='0'  and b.finished_num >0 and b.task_num > b.finished_num and b.userid=? "+
							 	  " union all "+
							 	  " select a.task_id, a.task_name, (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num, a.begin_date, a.end_date, a.create_time, a.lan_code, a.city_code, " +
								  " a.task_type, c.service_offer_name task_type_name, " +
							 	  " a.state,  decode(a.state, '001','已录入', '002','已分解', '003','已下发', '004','已撤销', '005','已删除') state_name, b.detail_id, " +
							 	  " (case when a.task_cate='0' then b.task_num when a.task_cate='1' then b.task_amount end) task_num, " +
							 	  " (case when a.task_cate='0' then b.finished_num when a.task_cate='1' then b.finished_amount end) finished_num "+
							 	  " from es_task a, es_taskdetail b,es_service_offer c where a.task_id = b.task_id and a.task_type=c.service_code  " +
							 	  " and a.state='003' and a.task_cate='1'  and b.finished_amount >0 and b.task_amount > b.finished_amount and b.userid=? " +
				 ") ";
			
		}
		//return this.daoSupport.queryForCPage(sql, pageIndex, pageSize, null, countSql, userid, userid);
		return this.daoSupport.queryForPage(sql, pageIndex, pageSize, userid, userid);
	}

	@Override
	public Page qryTaskDetail(String task_name, int pageIndex, int pageSize) {

			StringBuffer sql = new StringBuffer(" select a.task_id, a.task_name, (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num," +
											" a.begin_date, a.end_date, a.create_time, a.lan_code, a.city_code, " +
											" a.state, " +
											"  decode(a.state, '001','已录入', '002','已分解', '003','已下发', '004','已撤销', '005','已删除') state_name, " +
											" a.task_type, d.service_offer_name task_type_name, " +
											" b.detail_id, " +
											" (case when a.task_cate='0' then b.task_num when a.task_cate='1' then b.task_amount end) task_num, " +
											" (case when a.task_cate='0' then b.finished_num when a.task_cate='1' then b.finished_amount end) finished_num, c.username, " +
											" c.realname from es_task a, es_taskdetail b,es_service_offer d, es_adminuser c where " +
											" a.task_id = b.task_id and a.task_type=d.service_code and b.userid=c.userid and " +
											"a.source_from = b.source_from and b.source_from = d.source_from and d.source_from = c.source_from " +
											"and c.source_from = '" + ManagerUtils.getSourceFrom() + "'");
			if(StringUtils.isNotEmpty(task_name)){
				sql.append(" and a.task_name like '%"+task_name+"%' ");
			}
			return this.daoSupport.queryForPage(sql.toString(), pageIndex, pageSize);
		
	}

	@Override
	public Page qryTaskDetail(String userid, String task_name, int pageIndex,
			int pageSize) {
		
		
		StringBuffer sql = new StringBuffer(" select a.task_id, a.task_name, (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num, a.begin_date, a.end_date, a.create_time, a.lan_code, a.city_code, " +
											" a.state, " +
											"  decode(a.state, '001','已录入', '002','已分解', '003','已下发', '004','已撤销', '005','已删除') state_name, " +
											" a.task_type, d.service_offer_name task_type_name, " +
											" b.detail_id, "+
											" (case when a.task_cate='0' then b.task_num when a.task_cate='1' then b.task_amount end) task_num, " +
											" (case when a.task_cate='0' then b.finished_num when a.task_cate='1' then b.finished_amount end) finished_num, " +
											" c.username, c.realname from es_task a, es_taskdetail b,es_service_offer d, es_adminuser c where a.task_id = b.task_id and a.task_type=d.service_code and b.userid=c.userid " +
											" and b.userid=?  ");
		if(StringUtils.isNotEmpty(task_name)){
			sql.append(" and a.task_name like '%"+task_name+"%' ");
		}
		return this.daoSupport.queryForPage(sql.toString(), pageIndex, pageSize, userid);
	}

	@Override
	public Map qryTotalDetail(String userid) {
		
		Map map = new HashMap();
		
		       String sql_new = " select count(*) from (" +
												" select a.task_id, b.detail_id from  es_task a, es_taskdetail b where a.task_id=b.task_id and a.state='003' and a.task_cate='0' and b.finished_num=0 and b.userid=? " +
												" union all " +
												" select a.task_id, b.detail_id from es_task a, es_taskdetail b where a.task_id=b.task_id and a.state='003' and a.task_cate='1' and b.finished_amount=0 and b.userid=? ) " ;
		
		String sql_unfinished = " select count(*) from ( select a.task_id, b.detail_id from es_task a, es_taskdetail b where a.task_id=b.task_id and a.state='003' and a.task_cate='0' and b.finished_num>=0 and b.task_num > b.finished_num and b.userid=? " +
											    " union all " +
												" select a.task_id, b.detail_id from es_task a, es_taskdetail b where a.task_id=b.task_id and a.state='003' and a.task_cate='1' and b.finished_amount>=0 and b.task_amount > b.finished_amount and b.userid=? ) ";
		
		String sql_finished   = " select count(*) from ( select a.task_id, b.detail_id from es_task a, es_taskdetail b where a.task_id=b.task_id and a.state='003' and a.task_cate='0' and b.task_num = b.finished_num and b.userid=? " +
												" union all "+
												" select a.task_id, b.detail_id from es_task a, es_taskdetail b where a.task_id=b.task_id and a.state='003' and a.task_cate='1' and b.task_amount = b.finished_amount and b.userid=? ) ";
		
		int new_ct = daoSupport.queryForInt(sql_new, userid, userid);
		int unfinished_ct = daoSupport.queryForInt(sql_unfinished, userid,userid);
		int finished_ct = daoSupport.queryForInt(sql_finished, userid, userid);
		
		map.put("new_task", new_ct);
		map.put("unfinished_task", unfinished_ct);
		map.put("finished_task", finished_ct);
		return map;
	}

	@Override
	public List viewTaskDetail(String task_id, String detail_id) {

		List list = new ArrayList();
		
		String sql = " select a.task_id, a.task_name, (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num, a.begin_date, a.end_date, a.create_time, a.lan_code, " +
					 " (select c.region_name from es_common_region c where b.task_region = c.region_id "+ManagerUtils.apSFromCond("c")+") lan_name,  a.city_code, " +
	 	  			 " a.state,  decode(a.state, '001','已录入', '002','已分解', '003','已下发', '004','已撤销', '005','已删除') state_name, " +
	 	  			 " a.task_type, d.service_offer_name task_type_name, " +
	 	  			 " b.detail_id, " +
	 	  			 " (case when a.task_cate='0' then b.task_num when a.task_cate='1' then b.task_amount end) task_num, " +
					 " (case when a.task_cate='0' then b.finished_num when a.task_cate='1' then b.finished_amount end) finished_num " +
	 	  			 " from es_task a, es_taskdetail b,es_service_offer d where a.task_id = b.task_id  and a.task_type=d.service_code" +
	 	  			 " and a.task_id=? and b.detail_id=?  and a.source_from ='"+ManagerUtils.getSourceFrom()+"' " +ManagerUtils.apSFromCond("b")+ManagerUtils.apSFromCond("d");
		
		list =  this.daoSupport.queryForList(sql, task_id, detail_id);
		
		return list;
	}

	@Override
	public Page assignTask(String task_name, int pageIndex, int pageSize) {

		StringBuffer sql = new StringBuffer(" select a.task_id, a.task_code, a.task_name, a.task_num, a.target_rate, (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num, a.begin_date, a.end_date, a.create_time, a.lan_code, a.state, " +
											"  decode(a.state, '001','已录入', '002','已分解', '003','已下发', '004','已撤销', '005','已删除') state_name, " +
											" a.task_type, d.service_offer_name task_type_name " +
											" from es_task a,es_service_offer d where " +
											"a.task_type=d.service_code and a.state='002' and a.source_from = d.source_from " +
											"and d.source_from = '" + ManagerUtils.getSourceFrom() + "'");
	
		if(StringUtils.isNotEmpty(task_name)){
			sql.append(" and a.task_name like '%"+task_name+"%' ");
		}
		return this.daoSupport.queryForPage(sql.toString(), pageIndex, pageSize, null);
	}

	@Override
	public List qryEachTaskDetail(String task_id) {
		
		String sql = " select b.detail_id, b.task_id, (case when a.task_cate='0' then b.task_num when a.task_cate='1' then b.task_amount end) task_number, " +
					 " (select c.region_name from es_common_region c where b.task_region = c.region_id) task_city, " +
					 " d.username, d.realname from es_task a, es_taskdetail b, es_adminuser d where a.task_id = b.task_id and b.userid = d.userid and b.task_id=? ";
		return this.daoSupport.queryForList(sql, task_id);
	}

	@Override
	public Map qryTaskById(String task_id) {
		 String sql = " select a.task_id, a.task_code, a.task_name, a.task_num, (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num, a.target_rate, a.begin_date, a.end_date, a.create_time, a.lan_code, " +
		 			  " a.state, " +
		 			  "  decode(a.state, '001','已录入', '002','已分解', '003','已下发', '004','已撤销', '005','已删除') state_name, " +
		 			  " a.task_type, d.service_offer_name task_type_name from es_task a,es_service_offer d where a.task_type=d.service_code and a.task_id=? ";
		return this.daoSupport.queryForMap(sql, task_id);
	}

	@Override
	public String confirmSend(String task_id) {
		
		Map fields = new HashMap();
		String where = "task_id='"+task_id+"'";
		fields.put("state", "003");
		Map rtnMap = new HashMap();
		rtnMap.put("status", "-1");
		rtnMap.put("msg", "浠诲姟涓嬪彂澶辫触");
		int a = this.daoSupport.update("es_task", fields, where,null);
		if( a>0 ){
			rtnMap.put("status", "0");
			rtnMap.put("msg", "浠诲姟涓嬪彂鎴愬姛");
		}
		JSONObject json = JSONObject.fromObject(rtnMap);
		return json.toString();
	}

	@Override
	public String cancelTask(String task_id) {
		
		Map fields = new HashMap();
		String where = "task_id='"+task_id+"'";
		fields.put("state", "005");
		Map rtnMap = new HashMap();
		rtnMap.put("status", "-1");
		rtnMap.put("msg", "任务撤销失败");
		
		//String json = "{status:-1,msg:'浠诲姟鍒犻櫎澶辫触'}";
		int a = this.daoSupport.update("es_task", fields, where,null);
		if( a>0 ){
			rtnMap.put("status", "0");
			rtnMap.put("msg", "任务撤销成功");
			//json = "{status:0,msg:'浠诲姟鍒犻櫎鎴愬姛'}";
		}
		JSONObject json = JSONObject.fromObject(rtnMap);
		return json.toString();
	}

	@Override
	public String revokeTask(String task_id) {

		Map fields = new HashMap();
		String where = " task_id='"+task_id+"' ";
		fields.put("state", "004");
		Map rtnMap = new HashMap();
		rtnMap.put("status", "-1");
		rtnMap.put("msg", "浠诲姟鎾ら攢澶辫触");
		int a = this.daoSupport.update("es_task", fields, where,null);
		if( a>0 ){
			rtnMap.put("status", "0");
			rtnMap.put("msg", "浠诲姟鎾ら攢鎴愬姛");
		}
		JSONObject json = JSONObject.fromObject(rtnMap);
		return json.toString();
	}

	@Override
	public Page qryCancelList(String task_name, int pageIndex, int pageSize) {
		
		StringBuffer sql = new StringBuffer(" select a.task_id, a.task_code, a.task_name, a.task_num, a.target_rate, (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num, a.begin_date, a.end_date, a.create_time, a.lan_code, a.state, " +
				" decode(a.state, '001','已录入', '002','已分解', '003','已下发', '004','已撤销', '005','已删除') state_name, " +
				" a.task_type, d.service_offer_name task_type_name " +
				" from es_task a,es_service_offer d where a.task_type=d.service_code and (a.state='001' or a.state='002') ");

		if(StringUtils.isNotEmpty(task_name)){
		sql.append(" and a.task_name like '%"+task_name+"%' ");
		}
		return this.daoSupport.queryForPage(sql.toString(), pageIndex, pageSize, null);
	
	}

	@Override
	public Page qryRevokeList(String task_name, int pageIndex, int pageSize) {

		StringBuffer sql = new StringBuffer(" select a.task_id, a.task_code, a.task_name, a.task_num, a.target_rate, (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num, a.begin_date, a.end_date, a.create_time, a.lan_code, a.state, " +
				" decode(a.state, '001','已录入', '002','已分解', '003','已下发', '004','已撤销', '005','已删除') state_name, " +
				" a.task_type, d.service_offer_name task_type_name " +
				" from es_task a,es_service_offer d where a.task_type=d.service_code and a.state='003' ");

		if(StringUtils.isNotEmpty(task_name)){
		sql.append(" and a.task_name like '%"+task_name+"%' ");
		}
		return this.daoSupport.queryForPage(sql.toString(), pageIndex, pageSize, null);
	
	}
	
	@Override
	public String finishTask(String task_id){
		Map rtnMap = new HashMap();
		String sql = "select (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num from es_task a where task_id=?";
		String sql2 = " select sum(e.finished_num) from (select (case when a.task_cate = '0' then b.finished_num when a.task_cate = '1' then b.finished_amount end) finished_num "+
					  " from es_task a, es_taskdetail b where a.task_id = b.task_id and b.task_id = ?) e " ;
		long target_num = this.baseDaoSupport.queryForLong(sql, task_id);
		long finished_num = this.baseDaoSupport.queryForLong(sql2, task_id);
		if(finished_num<target_num){
			rtnMap.put("status", "-1");
			rtnMap.put("msg", "以选择的任务目标量:"+target_num+"，任务完成量:"+finished_num+"。完成量未达到目标量，任务关闭失败");
		}else{
			String updateSql = "update es_task set finished='1' where task_id=?";
			this.baseDaoSupport.execute(updateSql, task_id);
			rtnMap.put("status", "0");
			rtnMap.put("msg", "任务关闭成功");
		}
		JSONObject json = JSONObject.fromObject(rtnMap);
		return json.toString();
	}
	
	@Override
	public List showTaskDetailGroupByRegion(String task_id){
	
		String sql = " select h.task_id, h.lan_code, h.city_code, h.task_num, h.finished_num, h.target_num, h.task_name, "+
             		 " h.total_num, h.finished_rate, f.region_name lan_name, ff.region_name city_name from"+ 
             		 "( "+
					 " select e.task_id, e.lan_code, e.city_code, "+
			         " sum(task_num) as task_num, "+
			         " sum(finished_num) as finished_num, "+
			         " e.target_num, "+
			         " e.task_name,"+
			         " e.total_num,"+
			         " to_char(sum(e.finished_num)/sum(e.task_num)*100,'990.99')||'%' finished_rate "+ 
			         " from ( "+
			         	"  select d.task_id, "+
		                 " (case when d.task_cate='0' then c.task_num when d.task_cate='1' then c.task_amount end) task_num,"+ 
		                 " (case when d.task_cate='0' then c.finished_num when d.task_cate='1' then c.finished_amount end) finished_num,"+
		                 " d.task_name,"+
		                 " (case when d.task_cate='0' then d.target_num when d.task_cate='1' then d.target_amount end) target_num,"+
		                 " d.total_num, " +
		                 " c.lan_id lan_code, " +
		                 " c.city_id city_code " +
		                 " from (select a.task_id, a.task_region, a.task_num, a.finished_num ,a.task_amount, a.finished_amount, g.lan_id, g.city_id " +
		                 "from es_taskdetail a, es_adminuser g where a.userid=g.userid and a.source_from = g.source_from and g.source_from = '" + ManagerUtils.getSourceFrom() + "') c "+ 
		                 " inner join (select task_id, task_name, task_num total_num,target_num, task_cate, target_amount  from es_task t where t.source_from = '" + ManagerUtils.getSourceFrom() + "'" +
		                 " ) d "+
		                 " on c.task_id = d.task_id "+
		                 " where c.task_id = ? "+
		                 ") e" +
		                 " group by e.task_id, e.task_name, e.target_num, e.total_num, e.lan_code, e.city_code " +
		             " ) h " +
		                 " left outer join es_common_region f on h.lan_code = f.region_id and f.source_from = '" + ManagerUtils.getSourceFrom() + "'" +
		                 " left outer join es_common_region ff on h.city_code = ff.region_id and ff.source_from = '" + ManagerUtils.getSourceFrom() + "' ";
		List datas = this.baseDaoSupport.queryForList(sql, task_id);
		return datas;
	}
	
	@Override
	public List showTaskDetailGroupByPartner(String task_id){
		String sql = " select h.task_id, h.userid, h.task_num, h.finished_num, h.task_name, h.total_num, h.finished_rate, g.username user_name, g.realname real_name from " +
				" ( select "+
					" e.task_id, "+
					" e.userid, "+
					" sum(e.task_num) task_num, "+ 
					" sum(e.finished_num) finished_num, "+
					" e.task_name, "+
					" e.total_num, "+
					" to_char(sum(e.finished_num)/sum(e.task_num)*100,'990.99')||'%' finished_rate "+
					" from "+
					" (select d.task_id,c.userid, "+
					" (case when d.task_cate='0' then c.task_num when d.task_cate='1' then c.task_amount end) task_num, "+
					" (case when d.task_cate='0' then c.finished_num  when d.task_cate='1' then c.finished_amount end) finished_num, "+
					" d.task_name, "+
					" (case when d.task_cate='0' then d.target_num  when d.task_cate='1' then d.target_amount end) target_num, "+
					" d.total_num "+
					" from (select a.task_id, a.userid, a.task_num, a.finished_num ,a.task_amount, a.finished_amount from " +
					" es_taskdetail a where a.source_from = '" + ManagerUtils.getSourceFrom() + "') c "+
					" inner join "+
					" (select task_id, task_name, task_num total_num, target_num, target_amount, task_cate from es_task where source_from = '" + ManagerUtils.getSourceFrom() + "') d "+
					" on c.task_id = d.task_id "+
					" where c.task_id = ?) "+
					" e group by e.task_id,e.task_name,e.target_num,e.total_num,e.userid "+
					" ) h inner join es_adminuser g on h.userid = g.userid and g.source_from = '" + ManagerUtils.getSourceFrom() + "' ";
		
		List datas = this.baseDaoSupport.queryForList(sql, task_id);
		return datas;
	}
	
	@Override
	public List qryServiceList(){
		String sql = "select service_code,service_offer_name from es_service_offer";
		List datas = this.baseDaoSupport.queryForList(sql);
		return datas;
	}

	@Override
	public Page getPTask(String lan_id, String city_id, int founder, int pageIndex, int pageSize) {
		Page page = new Page();
		if(founder == 0 || founder == 1){ //鐢典俊鍛樺伐鎴栫鐞嗗憳娌℃湁鐖剁被浠诲姟
			return page;
		}else{	
			StringBuffer sql = new StringBuffer(" select a.task_id, a.task_code, a.task_name, a.task_num, a.target_rate, " +
												" (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num, a.begin_date, a.end_date, a.create_time, a.lan_code, a.state, " +
												" decode(a.state, '001','已录入', '002','已分解', '003','已下发', '004','已撤销', '005','已删除') state_name, " +
												" a.task_type, a.task_cate, a.target_amount, a.p_task_id, d.service_offer_name task_type_name " +
												" from es_task a,es_service_offer d where a.task_type=d.service_code and a.state='003' ");
			
			if(StringUtils.isNotEmpty(lan_id) && StringUtils.isNotEmpty(city_id)){
				sql.append(" and a.lan_code=?  " +
						   " and a.city_code in (select d.parent_region_id from (select region_id, parent_region_id, level lvl from es_common_region connect by prior parent_region_id = region_id start with region_id=? ) d where d.lvl=1) " +
						   " and exists (select 1 from es_taskdetail b where a.task_id = b.task_id and b.task_region=? ) ");
			}else if(StringUtils.isNotEmpty(lan_id) && StringUtils.isEmpty(city_id)){
				sql.append(" and a.lan_code='07XX' " +
						   " and exists (select 1 from es_taskdetail b where a.task_id=b.task_id and b.task_region=? ) " );
			}else{
				sql.setLength(0);
			}
			
			if(StringUtils.isNotEmpty(lan_id) && StringUtils.isNotEmpty(city_id)){
				page = this.daoSupport.queryForPage(sql.toString(), pageIndex, pageSize, lan_id, city_id, city_id);
			}else if(StringUtils.isNotEmpty(lan_id) && StringUtils.isEmpty(city_id)){
				page = this.daoSupport.queryForPage(sql.toString(), pageIndex, pageSize, lan_id);
			}else{
				
			}
			for(int i=0; i<page.getResult().size(); i++){
				Map map = (Map)page.getResult().get(i);
				String task_id = Const.getStrValue(map, "task_id");
				String task_cate = Const.getStrValue(map, "task_cate");
				int re = 0;
				if("0".equals(task_cate)){
					if(StringUtils.isNotEmpty(city_id)){
						String sql1 = " select sum(task_num) from es_taskdetail where task_region=? and task_id=? ";
						re = this.daoSupport.queryForInt(sql1, city_id, task_id);
					}
					if(StringUtils.isNotEmpty(lan_id) && StringUtils.isEmpty(city_id)){
						String sql1 = " select sum(task_num) from es_taskdetail where task_region=? and task_id=? ";
						re = this.daoSupport.queryForInt(sql1, lan_id, task_id);
					}
				}else{
					if(StringUtils.isNotEmpty(city_id)){
						String sql1 = " select sum(task_amount) from es_taskdetail where task_region=? and task_id=? ";
						re = this.daoSupport.queryForInt(sql1, city_id, task_id);
					}else if(StringUtils.isNotEmpty(lan_id) && StringUtils.isEmpty(city_id)){
						String sql1 = " select sum(task_amount) from es_taskdetail where task_region=? and task_id=? ";
						re = this.daoSupport.queryForInt(sql1, lan_id, task_id);
					}
				}
				map.put("total_num", re);
			}
			return page;
		}
	}

	@Override
	public String qryLanNamebyCode(String lan_id) {
		
		String sql = " select * from es_common_region where region_id=? ";
		List list =  this.daoSupport.queryForList(sql, lan_id);
		if(ListUtil.isEmpty(list)) return "";
		Map map = (Map)list.get(0);
		return Const.getStrValue(map, "region_name");
	}

	@Override
	public String qryPTaskName(String task_id) {
		String sql = " select a.task_id, b.task_name p_task_name from es_task a, es_task b where a.p_task_id = b.task_id and a.task_id=? ";
		List list = this.daoSupport.queryForList(sql, task_id);
		if(!ListUtil.isEmpty(list)){
			Map map = (Map)list.get(0);
			return Const.getStrValue(map, "p_task_name");
		}
		return "";
	}

	@Override
	public List searchUserCondi(String username) {
		
		AdminUser adminUser = ManagerUtils.getAdminUser(); // 鑾峰彇鐧诲綍鐢ㄦ埛
		String admin_org_id = adminUser.getOrg_id();//褰撳墠鐧诲綍鐢ㄦ埛缁勭粐ID
		String sql = " select a.userid, a.username, realname, b.lan_id, b.region_id, c.region_name city, (case when d.task_cate='0' then e.task_num when d.task_cate='1' then e.task_amount end) task_number " +
				     " from es_task d, es_taskdetail e, es_adminuser a,es_organization b left join es_common_region c on  b.region_id=c.region_id where a.org_id=b.party_id "+
					 " and d.task_id=e.task_id and a.userid=e.userid and a.org_id in (select party_id  from es_organization  where parent_party_id = ?) and founder =3 ";
		
		if(StringUtils.isNotEmpty(username)){
			sql += " and a.username like '%"+username+"%'";
		}
		List list = this.baseDaoSupport.queryForList(sql, admin_org_id);
		return list;
		
	}
	
	@Override
	public List searchPartnerList(String task_id){
		String sql = " select a.userid, a.username, realname, a.lan_id, e.region_name lan_name, c.region_name city_name," +
	     " (case when d.task_cate='0' then f.task_num when d.task_cate='1' then f.task_amount end) task_number " +
	     "  from es_adminuser a " +
	     " left outer join es_common_region e on a.lan_id = e.region_id and a.source_from = e.source_from " +
	     " left outer join es_common_region c on a.city_id = c.region_id and a.source_from = c.source_from " +
	     " , es_task d, es_taskdetail f where a.userid = f.userid and d.task_id = f.task_id and d.task_id=? " +
	     " and a.source_from = d.source_from and a.source_from = f.source_from and a.source_from = '" + ManagerUtils.getSourceFrom() + "' ";
		List list = this.baseDaoSupport.queryForList(sql, task_id);
		if(list == null) list = new ArrayList();
		return list;
	}
	
	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}

	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}

	@Override
	public void deleSplitTaskDetail(String task_id) {
		String sql = " delete from es_taskdetail where task_id=? ";
		this.baseDaoSupport.execute(sql, task_id);
	}
	
	@Override
	public List getRootOrg() {
		List org_list = new ArrayList();
		
		String sql = " select a.party_id, a.parent_party_id, " +
				"(select c.org_name from es_organization c where a.parent_party_id = c.party_id) parent_org_name, a.org_code, a.org_name, a.org_level, (SELECT F.REGION_NAME FROM ES_COMMON_REGION F WHERE A.LAN_ID = F.REGION_ID) lan_name, (SELECT G.REGION_NAME FROM ES_COMMON_REGION G WHERE A.REGION_ID = G.REGION_ID) city_name, a.org_type_id, b.org_type_name " +
					 " , a.lan_id, a.region_id, a.org_content, a.union_org_code from es_organization a, es_organization_type b " +
					 "where a.org_type_id = b.org_type_id  and a.parent_party_id= '-1' and a.status_cd='00A' " +
					 " and a.source_from = b.source_from and a.source_from = '" + ManagerUtils.getSourceFrom() + "' order by a.party_id " ;
					 
		org_list = baseDaoSupport.queryForList(sql);
		
		return org_list;
	}
	
	@Override
	public List getCurrOrg(String login_org_id) {
		List org_list = new ArrayList();
		
		String sql = " select a.party_id, a.parent_party_id, " +
					 " (select c.org_name from es_organization c where a.parent_party_id = c.party_id and c.status_cd='00A') parent_org_name,  a.org_code, a.org_name, a.org_level, a.org_type_id, b.org_type_name, " +
					 " (SELECT F.REGION_NAME FROM ES_COMMON_REGION F WHERE A.LAN_ID = F.REGION_ID) lan_name, " +
					 " (SELECT G.REGION_NAME FROM ES_COMMON_REGION G WHERE A.REGION_ID = G.REGION_ID) city_name," +
					 " a.lan_id, a.region_id, a.org_content, a.union_org_code from  es_organization a, es_organization_type b " +
					 "where a.org_type_id = b.org_type_id  and a.party_id= ? and a.status_cd='00A'" +
					 " and a.source_from = b.source_from and a.source_from = '" + ManagerUtils.getSourceFrom() + "' order by a.party_id " ;
					 
		org_list = baseDaoSupport.queryForList(sql, login_org_id);
		
		return org_list;
	}
	
	@Override
	public int qryChildrenOrgCount(String org_id) {

		int cnt = 0;
		String sql = "select count(*) from es_organization a, es_organization_type b where a.org_type_id = b.org_type_id and " +
				"a.parent_party_id=? and a.status_cd='00A' and a.source_from = b.source_from and a.source_from = '" + ManagerUtils.getSourceFrom() + "' ";
		
		 cnt = baseDaoSupport.queryForInt(sql, org_id);
		
		return cnt;
	}
	
	@Override
	public List getChildrenOrg(String org_id){
		
		List children_list  = new ArrayList();
		
		String sql = "select a.party_id, a.parent_party_id, (select c.org_name from es_organization c where a.parent_party_id = c.party_id and c.status_cd='00A') parent_org_name, a.org_code, a.org_name, a.org_level, a.org_type_id, b.org_type_name " +
					 " , (SELECT F.REGION_NAME FROM ES_COMMON_REGION F WHERE A.LAN_ID = F.REGION_ID) lan_name, (SELECT G.REGION_NAME FROM ES_COMMON_REGION G WHERE A.REGION_ID = G.REGION_ID) city_name, a.lan_id, a.region_id , a.org_content, a.union_org_code " +
					 "from es_organization a, es_organization_type b where a.org_type_id = b.org_type_id and a.parent_party_id=? " +
					 "and a.status_cd='00A' and a.source_from = b.source_from and a.source_from = '" + ManagerUtils.getSourceFrom() + "' order by a.party_id ";
		
		children_list = baseDaoSupport.queryForList(sql, org_id);
		
		return children_list;
	}
	
	@Override
	public Page searchTaskList(String lan_id, String node_level,String org_id,String lan_code,String city_code,String task_type,String task_name,
								String state,String finished,String create_time,int pageNo,int pageSize){
		AdminUser adminUser = ManagerUtils.getAdminUser();
		
		String sql = " select task_id,task_code,task_name,to_char(create_time,'yyyy-mm-dd hh24:mi:ss') create_time, d.service_offer_name task_type," +
				     " e.region_name as lan_name,c.region_name region_name,task_num," +
				     " (case when a.task_cate='0' then a.target_num when a.task_cate='1' then a.target_amount end) target_num, a.state, a.finished, a.op_id, a.p_task_id, a.task_cate " +
				     " from es_task a left join es_service_offer d on a.task_type=d.service_code and d.source_from = a.source_from" +
				     " left outer join es_common_region e on a.lan_code = e.region_id and e.source_from = a.source_from" +
				     " left outer join es_common_region c on a.city_code = c.region_id and c.source_from = a.source_from" +
				     " where a.source_from = '" + ManagerUtils.getSourceFrom() + "'  #cond order by create_time desc ";
		StringBuilder whereCond = new StringBuilder();
		List pList = new ArrayList();
		if(StringUtils.isNotEmpty(lan_code)){
			whereCond.append(" and a.lan_code= '" +lan_code+"'");
		}else if(StringUtils.isEmpty(lan_code) && "07XX".equals(lan_id)){
			whereCond.append(" and a.lan_code in (select t.region_id from (select region_id, level lvl from " +
					"es_common_region cr connect by prior region_id = parent_region_id and cr.source_from = '" + ManagerUtils.getSourceFrom() + "'"+
							 " start with region_id = '07XX') t where t.lvl = 1 or t.lvl = 2)");
		}
		
		if(StringUtils.isNotEmpty(city_code)){
			whereCond.append(" and a.city_code =  '"+city_code+"'");
		}else if(StringUtils.isEmpty(city_code) && !"07XX".equals(lan_id)){
			whereCond.append(" and ( a.city_code in (select t.region_id from (select region_id, level lvl from " +
					"es_common_region cr connect by prior region_id = parent_region_id  and cr.source_from = '" + ManagerUtils.getSourceFrom() + "'"+
							 " start with region_id = '"+lan_id+"') t where t.lvl > 1) or a.lan_code='"+lan_id+"') ");
		}
		
		if(StringUtils.isNotEmpty(task_name)){
			whereCond.append(" and a.task_name like '%"+task_name+"%'");
		}
		
		if(StringUtils.isNotEmpty(task_type)){
			whereCond.append(" and a.task_type = '"+task_type+"'");
		}
		
		if(StringUtils.isNotEmpty(create_time)){
			whereCond.append(" and to_char(a.create_time,'yyyy-mm-dd') = '"+create_time+"'");
		}
		
		if(StringUtils.isNotEmpty(state)){
			whereCond.append(" and a.state ='"+state+"'");
		}
		if(StringUtils.isNotEmpty(finished)){
			whereCond.append(" and a.finished = '"+finished+"'");
		}
		
		sql = sql.replaceAll("#cond", whereCond.toString());
		logger.info("查询任务列表："+sql);
		Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
		return page;
	}
	
}
