package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.OrderGroupDef;
import com.ztesoft.net.mall.core.model.OrderGroupRel;

public interface IOrderGroupManager {
	public Page GroupDefList(String group_name,String group_type,int pageSize,int pageNo);
	/**
	 * 添加分组
	 * @param orderGroupDef  分组实体
	 */
	public void insertOrderGroupDef(OrderGroupDef orderGroupDef);
	
	/**
	 * 删除分组
	 * @param group_id 分组Id
	 */
	public void deleteOrderGroupDef(String group_id);
	/**
	 * 分组关联分组角色
	 * @param orderGroupRel   
	 */
	public void insertOrderGroupRel(OrderGroupRel orderGroupRel);
	/**
	 * 分组关联分组角色修改
	 * @param group_id  分组Id
	 */
	public void updateOrderGroupRel(String userid,String role_id,String group_id);
	
	public List getOrderGroupRole(String group_id);
	
	public void saveOrderGroupRelRole(OrderGroupRel orderGroupRel);
	
	public List getOrderGroupUser(String group_id);
	
	public Page getUserList(String username,String realname,int pageSize,int pageNo);
	
	public void saveOrderGroupRelUser(OrderGroupRel orderGroupRel);
	
	public int getCountByGroupName(String groupName);
	
	public int getCountByGroupCode(String group_code);
}
