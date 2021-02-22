package com.ztesoft.net.mall.core.service;

import java.util.List;

import params.adminuser.req.MessageDetailReq;

import com.ztesoft.net.app.base.core.model.UserMsg;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Message;

public interface MessageManager {
	/**
	 * 发送信息 添加到es_message表
	 * @param m 为消息实体类
	 */
    public void add(Message m);
    /**
     * 删除全部信息
     */
    public void deleteAll();
    	
  /**
   * 删除选中的信息
   * @param ids
   */    
    public void delete(String[] ids,int num);
    /**
     * 修改信息
     * @param m
     */
    public void edit(Message m);
    /**
     * 
     * @param topic 主题
     * @param sendtime 发送时间
     * @param type 消息类型
     * @param pageNo 页号
     * @param pageSize 每页大小
     * @param pageSize 区分是收件箱[1]/发件箱[2]
     * @return
     */
	public Page list(int num,int readState,int senderState,String topic,String starttime,String endtime,String type ,int pageNo, int pageSize);
	public Page list(int num,String user_id, int reciverState, int senderState, String topic,
            String starttime, String endtime, String type, int pageNo,
            int pageSize);
	public int getReciverType(String reciverId);
	
	public Message getMsgById(String m_Id);
	public UserMsg getMessageById(MessageDetailReq messageDetailReq);
	   /**
		 * 
		 * @param content信息内容
		 * @param reciverId收件人ID
		 */
    public void sendMessage(String content,String reciverId);
    /**
     * 待回复消息（未读）
     * @return
     */
    public int noReadCount();
    /**
     * 消息总数
     * @return
     */
    public int messageCount();
    
    public void updateStateById(String m_id);
    
    public void insertMsgRef(String m_id,String userid,int state);
    
    public void updateMsgRef(String m_id,String usedid,int state);
   //收件箱
    public  int  selecStateByMidandReciverUserId(String userid,String mid);
    //发件箱
    public  int  selecStateByMidandSendUserId(String userid,String mid);
    //根据  消息ID  userId查找 回复消息的接受者名称
    public Message  getMessageById(String reply_mid,String userId);
    /**
     * 查看所有的电信管理员
     * @return
     */
    public List selectAllElectric();
    /**会员发送消息给电信管理员*/
    public void addMemberMsg(Message m);
    /**
     * 获取会员信息
     * @param memberId 会员ID
     * @param pageNo  第几页
     * @param pageSize 每页大小
     * @return
     */
    public Page selecctAllMemberMsgById(String memberId,int pageNo,int pageSize);
    
    /**
     * 查找所有的员工类型
     * @return
     */
    public List findTypeList();
    
    public void sendMsg(String reciver_id,String content);
    
    @Deprecated
    /**
     * 移动政企模拟在线客服对话过程
     * @param memberId
     * @return
     */
    public Page onLineServiceDialogueRecords(String memberId, int pageNo, int pageSize);
}
