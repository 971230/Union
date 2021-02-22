package com.ztesoft.net.action;


import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import com.ztesoft.net.app.base.core.model.Org;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.model.WorkPoolRel;
import com.ztesoft.net.model.WorkerPool;
import com.ztesoft.net.service.IWorkPoolManager;
/**
 * 工号池Action
 */
public class WorkerPoolAction extends WWAction{
	private static final long serialVersionUID = -1046682647983108339L;
	@Resource
	private IWorkPoolManager workPoolManager;//工号池管理类
	private WorkerPool workerPool;//工号池对象
	private WorkPoolRel workPoolRel;//工号池对应关系对象
	private AdminUser adminUser;//工号对象
	private Org organizationTemp;//组织对象
	private String success;//标记值
	private String groups;//一组参数
	
	/**
	 * 查询工号池列表
	 * @throws UnsupportedEncodingException 
	 */
	public String workerPoolList() throws UnsupportedEncodingException{
		this.webpage = workPoolManager.queryWorkerPoolList(workerPool, this.getPage(), this.getPageSize());
		return "es_work_pool_list";
	}
	
	/**
	 * 添加工号池
	 */
	@SuppressWarnings("static-access")
	public String addWorkerPool(){
		try {
			workPoolManager.addWorkerPool(workerPool);
		} catch (Exception e) {
			this.json = "{result : -1 }";
			return this.JSON_MESSAGE;
		}
		this.json = "{result : 0 }";
		return this.JSON_MESSAGE;
	}
	
	/**
	 * 禁用工号池
	 */
	@SuppressWarnings("static-access")
	public String invalidateWorkerPool(){
		try {
			workPoolManager.invalidateWorkerPool(workerPool);
		} catch (Exception e) {
			this.json = "{result : -1 }";
			return this.JSON_MESSAGE;
		}
		this.json = "{result : 0 }";
		return this.JSON_MESSAGE;
	}
	
	/**
	 * 启用工号池
	 */
	@SuppressWarnings("static-access")
	public String openWorkerPool(){
		try {
			workPoolManager.openWorkerPool(workerPool);
		} catch (Exception e) {
			this.json = "{result : -1 }";
			return this.JSON_MESSAGE;
		}
		this.json = "{result : 0 }";
		return this.JSON_MESSAGE;
	}
	
	/**
	 * 修改工号池
	 */
	@SuppressWarnings("static-access")
	public String editWorkerPool(){
		try {
			workPoolManager.editWorkerPool(workerPool);
		} catch (Exception e) {
			this.json = "{result : -1 }";
			return this.JSON_MESSAGE;
		}
		this.json = "{result : 0 }";
		return this.JSON_MESSAGE;
	}
	
	/**
	 * 工号池员工关系列表
	 */
	public String workPoolRelList(){
		success = workerPool.getPool_id();
		if(workPoolRel == null)
		workPoolRel = new WorkPoolRel();
		workPoolRel.setPool_id(success);
		this.webpage = workPoolManager.queryWorkerPoolRelList(workPoolRel, this.getPage(), this.getPageSize());
		return "es_work_pool_rel_list";
	}
	
	/**
	 * 添加工号池关系
	 */
	@SuppressWarnings("static-access")
	public String addWorkerPoolRel(){
		try {
			workPoolManager.addWorkerPoolRel(workPoolRel);
		} catch (Exception e) {
			this.json = "{result : -1 }";
			return this.JSON_MESSAGE;
		}
			this.json = "{result : 0 }";
		return this.JSON_MESSAGE;
	}
	
	/**
	 * 删除工号池关系
	 */
	@SuppressWarnings("static-access")
	public String deleteWorkerPoolRel(){
		try {
			workPoolManager.deleteWorkerPoolRel(workPoolRel);
		} catch (Exception e) {
			this.json = "{result : -1 }";
			return this.JSON_MESSAGE;
		}
		this.json = "{result : 0 }";
		return this.JSON_MESSAGE;
	}
	
	/**
	 * 修改工号池关系
	 */
	@SuppressWarnings("static-access")
	public String editWorkerPoolRel(){
		groups = groups.substring(0, groups.length() -1);
		try {
			workPoolManager.editWorkerPoolRel(groups);
		} catch (Exception e) {
			this.json = "{result : -1 }";
			return this.JSON_MESSAGE;
		}
		this.json = "{result : 0 }";
		return this.JSON_MESSAGE;
	}
	
	/**
	 * 【弹出框】选择员工组织列表
	 */
	public String workerOrganizationList() {
		this.webpage = workPoolManager.queryOrganizationList(organizationTemp, this.getPage(), this.getPageSize());
		return "choose_worker_pool_organization";
	}
	
	/**
	 * 【弹出框】选择员工列表
	 */
	public String workerList() {
		this.webpage = workPoolManager.queryAdminUserList(adminUser, this.getPage(), this.getPageSize());
		return "choose_worker_pool_worker";
	}
	
	public IWorkPoolManager getWorkPoolManager() {
		return workPoolManager;
	}

	public void setWorkPoolManager(IWorkPoolManager workPoolManager) {
		this.workPoolManager = workPoolManager;
	}

	public WorkerPool getWorkerPool() {
		return workerPool;
	}

	public void setWorkerPool(WorkerPool workerPool) {
		this.workerPool = workerPool;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public WorkPoolRel getWorkPoolRel() {
		return workPoolRel;
	}

	public void setWorkPoolRel(WorkPoolRel workPoolRel) {
		this.workPoolRel = workPoolRel;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public Org getOrganizationTemp() {
		return organizationTemp;
	}

	public void setOrganizationTemp(Org organizationTemp) {
		this.organizationTemp = organizationTemp;
	}

}
