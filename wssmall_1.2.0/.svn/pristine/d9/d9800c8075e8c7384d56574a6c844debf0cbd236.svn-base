package com.ztesoft.net.mall.core.service;

import java.util.Map;

import com.ztesoft.net.app.base.core.model.Message;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.MsgSet;

/**
 * 留言、短消息管理
 * 
 * @author lzf<br/>
 *         2010-3-18 上午11:30:28<br/>
 *         version 1.0<br/>
 */
public interface IMessageManager {
	
	/**
	 * 分页读取留言或短消息
	 * @param pageNo
	 * @param pageSize
	 * @param folder, 'inbox'=>收件箱; 'outbox'=>发件箱
	 * @return
	 */
	public Page pageMessage(int pageNo, int pageSize, String folder);
	
	/**
	 * 添加短消息
	 * @param message
	 */
	public void addMessage(Message message);
	
	/**
	 * 删除收件箱中的消息
	 * @param ids
	 */
	public void delinbox(String ids);
	
	/**
	 * 删除发件箱中的消息
	 * @param ids
	 */
	public void deloutbox(String ids);
	
	/**
	 * 修改短消息
	 * @param m
	 */
	public void setMessage_read(int msg_id);
	
	/**
	 * 信息模板设置获取
	 * @param params
	 */
	Page msgSetList(int pageNo, int pageSize, Map params);
	
	/**
	 * 信息模板设置删除
	 * @param set_id
	 */
	void msgSetDel(String set_id);
	
	/**
	 * 信息模板设置保存
	 * @param msgSet
	 */
	void msgSetSave(MsgSet msgSet);

}
