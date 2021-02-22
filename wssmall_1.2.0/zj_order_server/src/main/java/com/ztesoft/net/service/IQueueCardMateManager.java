package com.ztesoft.net.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.QueueCardMateVo;
import com.ztesoft.net.model.QueueManageVO;
import com.ztesoft.net.model.QueueParams;


public interface IQueueCardMateManager {
	/**
	 * 队列写卡机列表
	 * @param page
	 * @param pageNo
	 * @param param
	 * @return
	 */
	public Page queryQueueCardMateForPage(int pageNo,int pageSize,Map param);
	
	/**
	 * 保存队列写卡机
	 * @param queueCardMateVo
	 * @return
	 */
	public boolean saveQueueCardMate(QueueCardMateVo queueCardMateVo);
	
	/**
	 * 根据id删除队列写卡机
	 * @param id
	 * @return
	 */
	public boolean deleteQueueCardMate(String id);
	
	/**
	 * 根据id查询队列写卡机
	 * @param id
	 * @return
	 */
	public QueueCardMateVo queryQueueCardMate(String id);
	
	/**
	 * 根据id修改队列写卡机
	 * @param id
	 * @param queueCardMateVo
	 * @return
	 */
	public boolean modifyQueueCardMate(String id,QueueCardMateVo queueCardMateVo);
	
	/**
	 * 检验写卡机编码是否唯一
	 * @param card_mate_code
	 * @return
	 */
	public boolean checkCardMateCode(String card_mate_code,String queue_card_mate_id);

	/**
	 * 队列回退订单列表
	 * @return
	 */
	public List<Map<String,String>> queueReturnList();
	
	/**
	 * 队列删除垃圾数据订单列表
	 * @return
	 */
	public List<Map<String,String>> queueDeleteList();
	
	/**
	 * 查询队列维护
	 * @param sqlParams
	 * @return
	 */
	public Page getQueueManageList(int pageNo,int pageSize,QueueParams params) ;

	/**
	 * 更新队列维护信息
	 * @param queueManagevo
	 * @return
	 */
	public boolean updateQueueManageVO(QueueManageVO queueManagevo);

	/**
	 * 根据id查询队列信息
	 * @param queueId
	 * @return
	 */
	public QueueManageVO getQueueManageById(String queueId);
	/**
	 * 新增队列维护信息
	 * @param queueManagevo
	 * @return
	 */
	public boolean saveQueueManage(QueueManageVO queueManagevo);
	/**
	 * 更新队列维护信息
	 * @param queueManagevo
	 * @return
	 */
	public boolean updateQueueManageCon(List<String> updateParams,String whereParams);
	/**
	 * 退回队列
	 * @param updateParams
	 * @param ids
	 * @return
	 */
	public boolean rebackQueueManage(List<String> updateParams, String ids);

	/**
	 * 查询集中写卡订单列表
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public Page getQueueOrderList(int pageNo, int pageSize, QueueParams params);
	/**
	 * 回退订单
	 * @param updateParams
	 * @param ids
	 * @return
	 */
	public boolean rebackQueueOrder(List<String> updateParams, String ids);
	/**
	 * 订单标记等待回退
	 * @param order_id
	 * @return
	 */
	public boolean rebackQueueOrder(String order_id);
	/**
	 * 查询队列写卡机列表，不分页
	 * @param params
	 * @return
	 */
	public List<QueueCardMateVo> queryQueueCardMate(QueueParams params);
	/**
	 * 查询队列中等待写卡的订单
	 * @param params
	 * @return
	 */
	public List<String> getQueueOrderWaitWriteCardList(QueueParams params);
	/**
	 *  查询队列中等待开户的订单
	 * @param params
	 * @return
	 */
	public List<String> getQueueOrderWaitOpenAccList(QueueParams params);
	/**
	 * 查询队列，不分页
	 * @param params
	 * @return
	 */
	public List<QueueManageVO> getQueueManage(QueueParams params);

	public void updateCardQueueStatus();

	public Page queryQueueCardMateOrderNum(QueueParams queueParams, int page,int pageSize);

	public Page queryOrderItemsByStatus(String cardmateid, String sucflag,String sta_time, String end_time,int pageNo,int pageSize);
	
	public List queryOrderItemsListByStatus(String cardmateid, String sucflag,String sta_time, String end_time);

}
