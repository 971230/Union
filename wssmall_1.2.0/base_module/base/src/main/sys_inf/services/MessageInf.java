package services;

import params.adminuser.req.MessageCountReq;
import params.adminuser.req.MessageDetailReq;
import params.adminuser.req.MessageListReq;
import params.adminuser.req.MessageReq;
import params.adminuser.req.MessageSendReq;
import params.adminuser.resp.MessageCountResp;
import params.adminuser.resp.MessageDetaiResp;
import params.adminuser.resp.MessageListResp;
import params.adminuser.resp.MessageResp;
import params.adminuser.resp.SendMsgResp;


/**
 * 信息接口
 * @author hu.yi
 * @date 2013.12.13
 */
public interface MessageInf {

	/**
	 * 获取未读消息
	 * @return
	 */
	public MessageCountResp noReadCount(MessageCountReq messageCountReq);
	
	/**
	 * 发送消息
	 * @param messageSendReq
	 */
	public SendMsgResp sendMsg(MessageSendReq messageSendReq);
	
	/**
	 * 列出消息
	 * @param messageListReq
	 * @return
	 */
	public MessageListResp listMsg(MessageListReq messageListReq);
	
	/**
	 * 添加信息
	 * @param message
	 */
	public MessageResp addMessage(MessageReq messageReq);
	
	public MessageDetaiResp getMessageDetail(MessageDetailReq messageDetailReq);
}
