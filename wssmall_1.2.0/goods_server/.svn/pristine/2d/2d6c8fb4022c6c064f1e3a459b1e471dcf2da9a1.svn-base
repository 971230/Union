package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.GroupBuy;
import com.ztesoft.net.mall.core.model.GroupBuyCount;

import java.util.List;
import java.util.Map;




/**
 * 团购管理
 * @author kingapex
 *
 */
public interface IGroupBuyManager {
	
	
	/**
	 * 添加一个团购信息
	 * @param broupBuy
	 */
	public void add(GroupBuy groupBuy);
	
	public void updateBuyedCount(GroupBuy groupBuy);
	
	/**
	 * 获取团购的详细信息
	 * @param groupid
	 * @return
	 */
	public GroupBuy get(String groupid);
	
	
	/**
	 * 修改一个团购信息
	 * @param broupBuy
	 */
	public void edit(GroupBuy groupBuy);
	
	
	/**
	 * 读取所有团购列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page list(int pageNo,int pageSize);
	
	
	
	/**
	 *  获取某个团购物数量增长规则
	 * @param groupid
	 * @return
	 */
	public List<GroupBuyCount> listRule(String groupid);
	
	
	
	/**
	 * 读取当前可用团购
	 * @return
	 */
	public List<GroupBuy> listEnable();
	
	/**
	 * 删除某个团购
	 * @param groupid
	 */
	public void delete( String groupid);
	
	
	/**
	 * 增加团购购买数
	 */
	public void addBuyCount();
	
	
	/**根据查询条件查询团购列表
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page queryList(Map<String,String> map, int pageNo, int pageSize);
	
	/**逻辑删除与恢复
	 * @param groupid
	 * @param disabled
	 */
	public void editGroupState(String groupid,int disabled);
	
}
