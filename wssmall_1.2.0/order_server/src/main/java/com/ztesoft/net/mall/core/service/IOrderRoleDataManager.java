package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.app.base.core.model.RoleData;
import com.ztesoft.net.model.OrderRole;

/**
 * 订单权限管理类
 * @作者 MoChunrun
 * @创建日期 2014-11-20 
 * @版本 V 1.0
 */
public interface IOrderRoleDataManager {

	public List<RoleData> listOrderRoleData();
	
	public List<RoleData> cacheOrderRoleData();
	
	/**
	 * 从缓存拿数据，没拿到从数据库拿
	 * @作者 MoChunrun
	 * @创建日期 2014-11-20 
	 * @return
	 */
	public List<RoleData> getCacheOrderRoleData();
	
	/**
	 * 判断代理商权限
	 * @作者 MoChunrun
	 * @创建日期 2014-11-20 
	 * @param user_id
	 * @param agent_code
	 * @return
	 */
	public boolean agentAuto(String user_id,String agent_code);
	
	/**
	 * 批量插入订单权限表
	 * @作者 MoChunrun
	 * @创建日期 2014-11-21 
	 * @param list
	 */
	public void batchInsertOrderRole(List<OrderRole> list);
	
	/**
	 * 删除订单权限
	 * @作者 MoChunrun
	 * @创建日期 2014-11-21 
	 * @param order_id
	 */
	public void deleteOrderRole(String order_id);

	/**
	 * 获取用户是否有处理该订单的权限
	 * @param order_id
	 * @param user_id
	 * @return
	 */
	public boolean selectOrderAuthorityByUser(String order_id,String user_id);
	
}
