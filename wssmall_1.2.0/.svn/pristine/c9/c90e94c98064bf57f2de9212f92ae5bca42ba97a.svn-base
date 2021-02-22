package com.ztesoft.net.eop.resource;

import java.util.List;

import com.ztesoft.net.eop.resource.model.Org;

/**
 * 
 * 组织架构管理
 */
public interface IOrgManager {
	
	 
	/**
	 * 添加菜单项
	 * @param org
	 * @return
	 */
	public String add(Org org);
	
	
	/**
	 * 修改一个菜单项
	 * @param org
	 */
	public void edit(Org org);
	
	
	/**
	 * 读取菜单列表
	 * @param userid
	 * @param siteid
	 * @return
	 */
	public List<Org> getOrgList();

	/**
	 * 读取菜单列表
	 * @param userid
	 * @param siteid
	 * @return
	 */
	public List<Org> getOrgListByParentId(String parent_id, String level);
	
	/**
	 * 获取某个菜单的详细信息
	 * @param id
	 * @return
	 */
	public Org get(String id);
	
	

	/**
	 * 读取某菜单列表并形成Tree格式
	 * 
	 * @param orgid 要读取的顶级菜单id ,0为读取所有菜单
	 * @return
	 * @since 2.1.3 
	 * @author kingapex
	 */
	public List<Org> getOrgTree(String id);
	
	
	/**
	 * 删除一个菜单
	 * @param id
	 * @throws RuntimeException如果存在子菜单则抛出此异常
	 */
	public void delete(String id) throws RuntimeException;
	
	
	/**
	 * 更新菜单排序
	 * @param ids
	 * @param sorts
	 */
	public void updateSort(String[] ids,String[] sorts);
	
	/**
	 * 清除
	 * 一般用于站点安装时
	 */
	public void clean();
	
}
