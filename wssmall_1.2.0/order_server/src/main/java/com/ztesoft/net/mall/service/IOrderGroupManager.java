package com.ztesoft.net.mall.service;

import java.util.List;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.OrderGroup;

public interface IOrderGroupManager {

	/**
	 * 按group_type查询组 组类型，包括：confrim确认组、delvery发货组、pay支付组、accept受理组、query查询组
	 * @作者 MoChunrun
	 * @创建日期 2014-5-26 
	 * @param group_type
	 * @return
	 */
	public List<OrderGroup> listGroupByGroupType(String group_type);
	
	/**
	 * 查询用户组
	 * @作者 MoChunrun
	 * @创建日期 2014-6-11 
	 * @param group_id
	 * @param user_name
	 * @return
	 */
	public Page queryGroupUsers(String group_id,String user_name,int pageNo,int pageSize);
	
}
