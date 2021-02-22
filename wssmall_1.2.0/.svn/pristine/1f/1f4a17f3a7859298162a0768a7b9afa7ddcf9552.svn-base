package com.ztesoft.net.app.base.core.service.auth;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.core.model.AuthAction;
import com.ztesoft.net.framework.database.Page;

/**
 * 权限点管理接口
 * @author kingapex
 * 2010-10-24下午12:37:51
 */
public interface IAuthActionManager {
	
	/**
	 *根据权限id获取权限
	 * @param autid
	 * @return
	 */
	public AuthAction get(String autid);
	
	
	
	/**
	 * 读取所有权限点
	 * @return
	 */
	public List<AuthAction> list(String [] auids);
	
	
	/**
	 * 添加一个权限点
	 * @param act
	 * @return 返回添加的权限点id
	 */
	public String add(AuthAction act);
	
	
	
	/**
	 * 修改权限点
	 * @param act
	 */
	public void edit(AuthAction act);
	
	
	
	/**
	 * 删除某个权限点
	 * @param actid
	 */
	public void delete(String actid);
	
	public AuthAction getByRoleId(String roleid);
	
	public List<AuthAction> getListByRoleId(String roleid);
	
	public List<AuthAction> getList(String autid,String auth_type);
	/**
	 * 查询自己用有的权限和自己创建的权限
	 * @param act_id 权限ID
	 * @param name  权限名称
	 * @param type  权限类型
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page  authPage(String act_id,String name,String type,int page,int pageSize);
	
	/**
	 * 角色类型分组
	 * @author GL
	 * @return
	 */
	public List<Map> roleGroupType();
}
