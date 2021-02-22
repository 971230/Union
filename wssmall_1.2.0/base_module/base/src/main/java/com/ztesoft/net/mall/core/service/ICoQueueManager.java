package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.mall.core.model.CoQueue;

/**
 * 消息队列管理接口
 * 
 * @author wui
 */
public interface ICoQueueManager {
	
	
	/**
	 * 消息队列获取
	 * @param co_id
	 * @return
	 */
	public CoQueue get(String co_id);
	
	
	/**
	 * 定时任务扫描
	 * 只取状态为未发送【WFS】和响应失败【XYSB】（失败次数小于4）
	 * @param service_code
	 * @param max_count
	 * @return
	 */
	public List<CoQueue> getForJob(String service_code, int max_count);
	
	/**
	 * 添加
	 * @param coQueue
	 */
	public void add(CoQueue coQueue);
	
	/**
	 * 修改
	 * @param coQueue
	 */
	public void edit(CoQueue coQueue);
	
	
	/**
	 * 修改状态（用于失败时修改）
	 * @param co_id  消息队列标识
	 * @param resp_code  失败成功编码：000-成功；其他为失败
	 * @param resp_msg   失败描述
	 */
	public void modifyStatus(String co_id, String resp_code, String resp_msg);
	
	/**
	 * 修改状态
	 * @param co_id  消息队列标识
	 * @param status_new  新状态
	 * @param status_old  旧状态
	 * @param desc  描述信息
	 */
	public void modifyStatus(String co_id, String status_new, String status_old, String desc);
	
	/**
	 * 上锁（扫描后需要更新状态为发送中【FSZ】）
	 * @param co_id
	 */
	public void lock(String co_id);
	
	/**
	 * 解锁 -WFS
	 * @param co_id
	 */
	void unLock(String co_id);
	
	/**
	 * 删除
	 * @param coQueue
	 */
	public void del(String co_id);

	/**
	 * 是否已经上锁
	 * @param co_id
	 * @return
	 */
	boolean haveLocked(String co_id);
	
	
	public void modifyStatusToWFS(List co_ids);
	public List<CoQueue> getByOrder(String order_id, int max_count,String base_sql);
	public List<CoQueue> getForABGrayJob(String[] service_code, int max_count,String env_status,String env_code,boolean is_distinct) ;
}