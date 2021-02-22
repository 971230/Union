package com.ztesoft.net.service;

import java.util.Map;

import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;

import com.ztesoft.net.app.base.core.model.Org;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.OrderDispatchLog;
import com.ztesoft.net.model.WorkPoolRel;
import com.ztesoft.net.model.WorkerPool;

public interface IWorkPoolManager {

	/**
	 * 查询工号池列表
	 */
	public Page queryWorkerPoolList(WorkerPool workerPool, int pageIndex,
			int pageSize);

	/**
	 * 失效工号池
	 */
	public void invalidateWorkerPool(WorkerPool workerPool);
	
	/**
	 * 启用工号池
	 */
	public void openWorkerPool(WorkerPool workerPool);

	/**
	 * 修改工号池
	 */
	public void editWorkerPool(WorkerPool workerPool);

	/**
	 * 添加工号池
	 */
	public void addWorkerPool(WorkerPool workerPool);

	/**
	 * 工号池员工关系列表
	 */
	public Page queryWorkerPoolRelList(WorkPoolRel workPoolRel, int pageIndex,
			int pageSize);

	/**
	 * 修改工号池员工关系
	 */
	public void editWorkerPoolRel(String groups);

	/**
	 * 添加工号池员工关系
	 */
	public void addWorkerPoolRel(WorkPoolRel workPoolRel);
	
	/**
	 * 删除工号池员工关系
	 */
	public void deleteWorkerPoolRel(WorkPoolRel workPoolRel);
	
	/**
	 * 查询员工列表
	 */
	public Page queryAdminUserList(AdminUser adminUser, int pageIndex,
			int pageSize);
	/**
	 * 查询员工组织列表
	 */
	public Page queryOrganizationList(Org organizationTemp, int pageIndex,
			int pageSize);
	/**
	 * 根据工号池ID、权重、当前计数得到应分配的处理人工号
	 */
	public String getOperatorIdByAuto(String pool_id);
	
	/**
	 * 记录派单日志
	 */
	public void addOrderDispatchLog(OrderDispatchLog orderDispatchLog );
	
	/**
	 * 根据pool_id、operator_id修改关系表es_worker_pool_rel，对count进行加一操作(type :ADD代表加一，CDD代表减一)
	 */
	public void updateWorkerPoolRelCounts(String pool_id,String operator_id,String type);
	
	/**
	 * 根据order_id查询派单日志es_order_dispatch_log，得到pool_id、operator_id
	 */
	public OrderDispatchLog getOrderDispatchLogByOrderId(String order_id);

	
	/**
	 * 根据工号池id和员工id查找工号池关系对象
	 */
	public WorkPoolRel getWorkPoolRelByIds(String pool_id,String operator_id) ;
	
	/**
	 * 根据工号池id返回工号池对象
	 */
	public WorkerPool getWorkPoolById(String pool_id);
	
	/**
	 * 根据order_id，trace_id查询es_order_lock表中对象
	 */
	@SuppressWarnings("rawtypes")
	public Map getOrderLockTempByIds(String order_id,String trace_id);
	
	/**
	 * 根据锁定人
	 * 获取锁定工号池和锁定时间
	 */
	public OrderLockBusiRequest getLockTimeByUserId(String user_id);
	
}
