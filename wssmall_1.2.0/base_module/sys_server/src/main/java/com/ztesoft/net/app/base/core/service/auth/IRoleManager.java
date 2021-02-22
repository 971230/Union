package com.ztesoft.net.app.base.core.service.auth;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.core.model.Role;
import com.ztesoft.net.framework.database.Page;

/**
 * 角色管理接口
 * @author kingapex
 * 2010-10-24下午12:43:12
 */
public interface IRoleManager {
	/**
	 * 角色列表分页
	 */
	public Page rolePage(int pageNo,int pageSize,String role_name,String role_code,String auth_type,String role_group);
	/**
	 * 读取所有角色列表 
	 * @return
	 */
	public List<Role> list(String rolename);
	
	
	/**
	 * 添加一个角色
	 * @param role 角色实体
	 * @param acts 此角色的权限集合
	 */
	public void add(Role role,String[] acts);
	
	
	
	/**
	 * 修改角色<br>
	 * 将会同时修改此属于此角色用户的权限
	 * @param role  角色实体
	 * @param acts 此角色的权限集合
	 */
	public void edit(Role role,String[] acts);
	
	
	/**
	 * 删除某角色
	 * @param roleid
	 */
	public void delete(String roleid);
	
	
	/**
	 * 读取某个角色信息，同时读取此角色权限
	 * @param roleid
	 * @return 权限id存于role.actids数组中
	 */
	public Role get(String roleid);
	
	/**
	 * 获取所有角色分组
	 * @return
	 */
	public List<Map> getListRoleGroup();

}
