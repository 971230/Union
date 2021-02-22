package com.ztesoft.net.service.impl;




import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.util.SequenceTools;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.app.base.core.model.Org;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.OrderDispatchLog;
import com.ztesoft.net.model.WorkPoolRel;
import com.ztesoft.net.model.WorkerPool;
import com.ztesoft.net.service.IWorkPoolManager;

import consts.ConstsCore;


@SuppressWarnings("rawtypes")
public class WorkPoolManager extends BaseSupport implements IWorkPoolManager{

	/**
	 * 查询工号池列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page queryWorkerPoolList(WorkerPool workerPool, int pageIndex,int pageSize) {
		StringBuilder sqlBuilder = new StringBuilder("select a.*,b.org_name,c.realname as operator_name from es_worker_pool a ,es_organization b,es_adminuser c where 1=1 and a.source_from = '"+ManagerUtils.getSourceFrom()+"' and b.source_from = '"+ManagerUtils.getSourceFrom()+"' and c.source_from = '"+ManagerUtils.getSourceFrom()+"' and a.org_id = b.org_code and a.operator_id = c.userid ");
		Page page = new Page();
		if (workerPool != null) {
			// 工号池名称
			if (!StringUtil.isEmpty(workerPool.getPool_name())) {
				sqlBuilder.append(" and a.pool_name like '%" + workerPool.getPool_name().trim() + "%' ");
			}
		}
			page = this.baseDaoSupport.queryForPage(sqlBuilder.toString(), pageIndex, pageSize, WorkerPool.class, null);//operator_id
			return page;
	}

	/**
	 * 失效工号池
	 */
	@Override
	public void invalidateWorkerPool(WorkerPool workerPool) {
		String pool_id = workerPool.getPool_id();
		StringBuilder sqlBuilder = new StringBuilder("update es_worker_pool a set a.status = ? where a.pool_id = ? ");
		this.baseDaoSupport.execute(sqlBuilder.toString(),ZjEcsOrderConsts.WORKER_POOL_STATE_1,pool_id);
	}

	/**
	 * 启用工号池
	 */
	@Override
	public void openWorkerPool(WorkerPool workerPool) {
		String pool_id = workerPool.getPool_id();
		StringBuilder sqlBuilder = new StringBuilder("update es_worker_pool a set a.status = ? where a.pool_id = ? ");
		this.baseDaoSupport.execute(sqlBuilder.toString(),ZjEcsOrderConsts.WORKER_POOL_STATE_0,pool_id);
	}
	
	/**
	 * 修改工号池
	 */
	@Override
	public void editWorkerPool(WorkerPool workerPool) {
		String pool_id = workerPool.getPool_id();
		String pool_name = workerPool.getPool_name();
		String lock_time = workerPool.getLock_time();
		String org_id = workerPool.getOrg_id();
		StringBuilder sqlBuilder = new StringBuilder("update es_worker_pool a set a.pool_name = ?,a.lock_time = ?,a.org_id = ? where a.pool_id = ? ");
		this.baseDaoSupport.execute(sqlBuilder.toString(), pool_name,lock_time,org_id,pool_id);
	}

	/**
	 * 添加工号池
	 */
	@Override
	public void addWorkerPool(WorkerPool workerPool) {
		AdminUser adminUser = ManagerUtils.getAdminUser();
		String pool_id = SequenceTools.getdefualt22PrimaryKey();
		workerPool.setPool_id(pool_id);
		workerPool.setOperator_id(adminUser.getUserid());
		workerPool.setCreate_time(getNow());
		workerPool.setStatus(ZjEcsOrderConsts.WORKER_POOL_STATE_0);
		workerPool.setSource_from(ManagerUtils.getSourceFrom());
		this.baseDaoSupport.insert("ES_WORKER_POOL", workerPool);
	}

	/**
	 * 工号池员工关系列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page queryWorkerPoolRelList(WorkPoolRel workPoolRel, int pageIndex,int pageSize) {
		String pool_id = workPoolRel.getPool_id();
		//StringBuilder sqlBuilder = new StringBuilder("select a.*,b.pool_name from es_worker_pool_rel a ,es_worker_pool b where 1=1 and a.source_from = '"+ManagerUtils.getSourceFrom()+"' and b.source_from = '"+ManagerUtils.getSourceFrom()+"' and a.pool_id = b.pool_id ");
		//sqlBuilder.append(" and a.pool_id = '"+pool_id+"' ");
		StringBuilder sqlBuilder = new StringBuilder("select * from (select a.*,b.pool_name,(select count(*) from ES_ORDER_LOCK c where c.SOURCE_FROM = '"+ManagerUtils.getSourceFrom()+"' and c.LOCK_USER_ID= a.OPERATOR_ID and c.LOCK_STATUS = '1') as lock_counts from es_worker_pool_rel a ,es_worker_pool b where 1=1 and a.source_from = '"+ManagerUtils.getSourceFrom()+"' and b.source_from = '"+ManagerUtils.getSourceFrom()+"' and a.pool_id = b.pool_id and a.pool_id = '"+pool_id+"') where 1=1 ");
		Page page = new Page();
		if (workPoolRel != null) {
			// 工号
			if (!StringUtil.isEmpty(workPoolRel.getOperator_id())) {
				sqlBuilder.append(" and operator_id like '%" + workPoolRel.getOperator_id().trim() + "%' ");
			}
			// 工号名称
			if (!StringUtil.isEmpty(workPoolRel.getOperator_name())) {
				sqlBuilder.append(" and operator_name like '%"+ workPoolRel.getOperator_name().trim() + "%' ");
			}
		}
 			page = this.baseDaoSupport.queryForPage(sqlBuilder.toString(), pageIndex, pageSize, WorkPoolRel.class,null);
			return page;
	}
	
	/**
	 * 添加工号池员工关系
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addWorkerPoolRel(WorkPoolRel workPoolRel) {
		String operator_ids = workPoolRel.getOperator_id();
		operator_ids = operator_ids = operator_ids.substring(0, operator_ids.length() -1);
		String[] operatorIdArray = operator_ids.split(",");
		for (String operator_id : operatorIdArray) {
			WorkPoolRel poolRel = new WorkPoolRel(); 
			String pool_id = workPoolRel.getPool_id();
			if(getWorkPoolRelByIds(pool_id,operator_id) != null)
				continue;
			poolRel.setPool_id(pool_id);
			poolRel.setWeight("1");
			//poolRel.setCounts("0");
			poolRel.setMax_counts("0");
			poolRel.setOperator_id(operator_id);
			poolRel.setOperator_name(getAdminUserById(operator_id).getRealname());
			poolRel.setSource_from(ManagerUtils.getSourceFrom());
			this.baseDaoSupport.insert("ES_WORKER_POOL_REL", poolRel);
		}
	}
	
	/**
	 * 修改工号池员工关系
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void editWorkerPoolRel(String groups) {
		String[] groupsArray = groups.split("/");
		for (String group : groupsArray) {
			int i = group.indexOf(",");
			String pool_id = group.substring(0, i);// 工号池id
			group = group.substring(i + 1);
			i = group.indexOf(",");
			String operator_id = group.substring(0, i);// 关联工号id
			group = group.substring(i + 1);
			i = group.indexOf(",");
			String weight = group.substring(0, i);// 权重
			group = group.substring(i + 1);
			i = group.indexOf(",");
			String max_counts = group.substring(i + 1);// 保有量
			StringBuilder sqlBuilder = new StringBuilder("update es_worker_pool_rel a set a.weight = ?,a.max_counts = ? where a.pool_id = ? and a.operator_id = ? ");
			this.baseDaoSupport.execute(sqlBuilder.toString(), weight,max_counts,pool_id,operator_id);
		}
	}
	
	/**
	 * 删除工号池员工关系
	 */
	@Override
	public void deleteWorkerPoolRel(WorkPoolRel workPoolRel) {
		String pool_id = workPoolRel.getPool_id();
		String operator_id = workPoolRel.getOperator_id();
		String sql = "delete es_worker_pool_rel a where a.pool_id = ? and a.operator_id = ? ";
		this.baseDaoSupport.execute(sql,pool_id,operator_id);
	}
	
	/**
	 * 查询员工列表
	 */
	@Override
	public Page queryAdminUserList(AdminUser adminUser, int pageIndex,
			int pageSize) {
		StringBuilder sqlBuilder = new StringBuilder("select * from es_adminuser a where 1=1 ");
		Page page = new Page();
		if (adminUser != null) {
			// 工号id
			if (!StringUtil.isEmpty(adminUser.getUserid())) {
				sqlBuilder.append(" and a.USERID like '%" + adminUser.getUserid().trim() + "%' ");
			}
			// 工号名称
			if (!StringUtil.isEmpty(adminUser.getRealname())) {
				sqlBuilder.append(" and a.realname like '%"+ adminUser.getRealname().trim() + "%' ");
			}
		}
			page = this.baseDaoSupport.queryForPage(sqlBuilder.toString(), pageIndex, pageSize, AdminUser.class, null);
			return page;
	}
	
	/**
	 * 查询员工组织列表
	 */
	@Override
	public Page queryOrganizationList(Org organizationTemp, int pageIndex,
			int pageSize) {
		StringBuilder sqlBuilder = new StringBuilder("select * from es_organization a where 1=1 and a.source_from = '"+ManagerUtils.getSourceFrom()+"' and a.status_cd = '00A' ");
		Page page = new Page();
		if (organizationTemp != null) {
			// 组织编码
			if (!StringUtil.isEmpty(organizationTemp.getOrg_code())) {
				sqlBuilder.append(" and a.org_code like '%" + organizationTemp.getOrg_code().trim() + "%' ");
			}
			// 组织名称
			if (!StringUtil.isEmpty(organizationTemp.getOrg_name())) {
				sqlBuilder.append(" and a.org_name like '%"+ organizationTemp.getOrg_name().trim() + "%' ");
			}
		}
			page = this.baseDaoSupport.queryForPage(sqlBuilder.toString(), pageIndex, pageSize, Org.class, null);
			return page;
	}
	
	/**
	 * 根据工号池id和员工id查找工号池关系对象
	 */
	@Override
	public WorkPoolRel getWorkPoolRelByIds(String pool_id,String operator_id) {
		StringBuilder sqlBuilder = new StringBuilder("select a.* from es_worker_pool_rel a where 1=1 and a.source_from = '"+ManagerUtils.getSourceFrom()+"' and a.pool_id = ? and a.operator_id = ? ");
		return (WorkPoolRel) this.baseDaoSupport.queryForObject(sqlBuilder.toString(), WorkPoolRel.class, pool_id.trim(),operator_id.trim());
	}
	
	/**
	 * 根据工号池id返回工号池对象
	 */
	@Override
	public WorkerPool getWorkPoolById(String pool_id) {
		StringBuilder sqlBuilder = new StringBuilder("select a.* from es_worker_pool a where 1=1 and a.source_from = '"+ManagerUtils.getSourceFrom()+"' and a.pool_id = ? ");
		return (WorkerPool) this.baseDaoSupport.queryForObject(sqlBuilder.toString(), WorkerPool.class, pool_id.trim());
	}
	
	/**
	 * 【公共方法】根据员工id查找员工名称
	 */
	public AdminUser getAdminUserById(String user_id) {
		StringBuilder sqlBuilder = new StringBuilder("select * from es_adminuser a where a.USERID = ? ");
		return (AdminUser) this.baseDaoSupport.queryForObject(sqlBuilder.toString(), AdminUser.class, user_id.trim());
	}
	
	/**
	 * 【公共方法】 获取当前时间
	 */
	private String getNow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 根据工号池ID、权重、当前计数得到应分配的处理人工号
	 */
	@Override
	public String getOperatorIdByAuto(String pool_id) {
		//StringBuilder sqlBuilder = new StringBuilder(" select operator_id from (select operator_id from (select a.* from es_worker_pool_rel a where a.pool_id = ? and (a.max_counts > counts or a.max_counts = 0)  order by a.weight desc) order by counts asc) where rownum <= 1 ");
		//StringBuilder sqlBuilder = new StringBuilder(" select * from (select * from (select a.* from es_worker_pool_rel a where a.pool_id = ? and (a.max_counts > counts or a.max_counts = 0)  order by a.weight desc) order by counts asc) where rownum <= 1 and source_from = '"+ManagerUtils.getSourceFrom()+"'");
		//StringBuilder sqlBuilder = new StringBuilder(" select * from (select * from (select a.* from es_worker_pool_rel a where a.pool_id = ? and (a.max_counts > (select count(*) from es_order_lock b,es_adminuser c where b.lock_user_id =c.userid and b.lock_status='1' and c.username=a.operator_id) or a.max_counts = 0) order by a.weight desc) order by (select count(*) from es_worker_pool_rel a,es_order_lock b,es_adminuser c where a.pool_id = ? and b.lock_user_id =c.userid and b.lock_status='1' and c.username =a.operator_id) asc) where rownum <= 1 and source_from = '"+ManagerUtils.getSourceFrom()+"' ");
		String operator_id = "";
		StringBuilder sqlBuilder = new StringBuilder("select * from (select a.*,nvl(t.num,0) num from es_worker_pool_rel a,(select count(*) num,c.username  from es_order_lock b,es_adminuser c where B.source_from = '"+ManagerUtils.getSourceFrom()+"' AND C.source_from = '"+ManagerUtils.getSourceFrom()+"' AND b.lock_user_id =c.userid and b.lock_status='1' group by c.username )t ");
		sqlBuilder.append(" where a.operator_id=t.username(+) and (a.max_counts = 0 or t.num is null or a.max_counts>t.num) and a.pool_id = ? order by a.weight desc ,num asc) where rownum <= 1 ");
		WorkPoolRel workPoolRel = (WorkPoolRel) this.baseDaoSupport.queryForObject(sqlBuilder.toString(), WorkPoolRel.class,pool_id);
		if(workPoolRel != null)
		operator_id = workPoolRel.getOperator_id();
		return operator_id;
	}

	/**
	 * 记录派单日志
	 */
	@Override
	public void addOrderDispatchLog(OrderDispatchLog orderDispatchLog) {
			orderDispatchLog.setSource_from(ManagerUtils.getSourceFrom());
			orderDispatchLog.setCreate_time(getNow());
			this.baseDaoSupport.insert("ES_ORDER_DISPATCH_LOG", orderDispatchLog);
	}

	/**
	 * 根据pool_id、operator_id修改关系表es_worker_pool_rel，对count进行加一操作(type :ADD代表加一，CDD代表减一)
	 */
	@Override
	public void updateWorkerPoolRelCounts(String pool_id, String operator_id,String type) {
		StringBuilder sqlBuilder = null;
		if("ADD".equalsIgnoreCase(type))
			sqlBuilder = new StringBuilder("update es_worker_pool_rel a set a.counts=(a.counts+1) where a.pool_id = ? and a.operator_id = ? ");
		else
		    sqlBuilder = new StringBuilder("update es_worker_pool_rel a set a.counts=(a.counts-1) where a.pool_id = ? and a.operator_id = ? ");
		this.baseDaoSupport.execute(sqlBuilder.toString(),pool_id,operator_id);
	}

	/**
	 * 根据order_id查询派单日志es_order_dispatch_log，得到pool_id、operator_id
	 */
	@Override
	public OrderDispatchLog getOrderDispatchLogByOrderId(String order_id) {
		StringBuilder sqlBuilder = new StringBuilder("select a.* from es_order_dispatch_log a where 1=1 and a.source_from = '"+ManagerUtils.getSourceFrom()+"' and a.order_id = ? ");
		return (OrderDispatchLog) this.baseDaoSupport.queryForObject(sqlBuilder.toString(), OrderDispatchLog.class, order_id.trim());
	}

	/**
	 * 根据order_id，trace_id查询es_order_lock表中对象
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getOrderLockTempByIds(String order_id, String trace_id) {
		Map map = null;
		StringBuilder sqlBuilder = new StringBuilder("select a.lock_user_id,a.pool_id from es_order_lock a where 1=1 and a.source_from = '"+ManagerUtils.getSourceFrom()+"' and a.order_id = ? and a.tache_code = ? ");
		List<Map> listMap = this.baseDaoSupport.queryForList(sqlBuilder.toString(),order_id,trace_id);
		if(listMap != null && listMap.size() != 0 )
			map = listMap.get(0);
		return map;
	}

	
	
	/**
	 * 根据锁定人
	 * 获取锁定工号池和锁定时间
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OrderLockBusiRequest getLockTimeByUserId(String user_id) {
		if(user_id ==null)user_id = "";
		OrderLockBusiRequest orderLockBusiRequest = new OrderLockBusiRequest();
		String lock_time_value = "";
		StringBuilder sqlBuilder = new StringBuilder("select a.* from es_worker_pool_rel a where 1=1 and a.source_from = '"+ManagerUtils.getSourceFrom()+"' and a.OPERATOR_ID = ? ");
		List<Map> listWorkerPoolMap = this.baseDaoSupport.queryForList(sqlBuilder.toString(),user_id);
		if(listWorkerPoolMap != null && listWorkerPoolMap.size() > 0){
			String pool_id = listWorkerPoolMap.get(0).get("pool_id").toString();
			orderLockBusiRequest.setPool_id(pool_id);
			lock_time_value = getWorkPoolById(pool_id).getLock_time();
		}else{
			orderLockBusiRequest.setPool_id(ConstsCore.NULL_VAL);
			ICacheUtil util = (ICacheUtil)SpringContextHolder.getBean("cacheUtil");
			lock_time_value = util.getConfigInfo("DEFAULT_LOCK_TIME");
		}
		String lock_end_time = ConstsCore.NULL_VAL;
		if(!StringUtil.isEmpty(lock_time_value) && !lock_time_value.equals("0")){
			try {
				lock_end_time = DateUtil.addDate(DateUtil.getTime2(),DateUtil.DATE_FORMAT_2,Integer.valueOf(lock_time_value), DateUtil.MINUTE);
				orderLockBusiRequest.setLock_time(DateUtil.getTime2());
			} catch (FrameException e) {
				logger.info(e.getMessage());
			}						
	}
		orderLockBusiRequest.setLock_end_time(lock_end_time);
		return orderLockBusiRequest;
	}
	
}
