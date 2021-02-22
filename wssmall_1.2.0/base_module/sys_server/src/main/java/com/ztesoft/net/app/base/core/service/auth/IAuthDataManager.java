package com.ztesoft.net.app.base.core.service.auth;

import params.adminuser.req.AuthPageReq;

import com.ztesoft.net.app.base.core.model.RoleData;
import com.ztesoft.net.framework.database.Page;

public interface IAuthDataManager {
	
	/**
	 * 查询权限
	 * @param authPageReq
	 * @return
	 */
	public Page authListPage(AuthPageReq authPageReq);
	
	/**
	 * 新增数据权限
	 * @param roleData
	 * @return
	 */
	public String add(RoleData roleData);
	
	/**
	 * 新增数据权限
	 * @param roleData
	 * @return
	 */
	public void edit(RoleData roleData);	
	
	
	/**
	 * 删除数据权限
	 * @param roleData
	 * @return
	 */
	public void delete(String id);
}
