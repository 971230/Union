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

import com.ztesoft.net.app.base.core.model.UserMsg;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.service.IMessageManager;
import com.ztesoft.net.mall.core.service.MessageManager;

import consts.ConstsCore;

public class MessageServ extends ServiceBase implements MessageInf{
	
	private MessageManager userMessageManager; 
	private IMessageManager messageManager;
	
	@Override
	public MessageCountResp noReadCount(MessageCountReq messageCountReq){
		
		int wait_message = this.userMessageManager.noReadCount();
		MessageCountResp messageCountResp = new MessageCountResp();
		messageCountResp.setCount(wait_message);
		addReturn(messageCountReq, messageCountResp);
		return messageCountResp;
	}
	
	@Override
	public SendMsgResp sendMsg(MessageSendReq messageSendReq){
		
		this.userMessageManager.sendMsg(messageSendReq.getReciverId(), messageSendReq.getContent());
		
		SendMsgResp sendMsgResp = new SendMsgResp();
		addReturn(messageSendReq, sendMsgResp);
		return sendMsgResp;
	}
	

	@Override
	public MessageListResp listMsg(MessageListReq messageListReq){
		MessageListResp messageListResp = new MessageListResp();
		try{
			Page page = this.userMessageManager.list(messageListReq.getNum(),messageListReq.getUser_id(),
					messageListReq.getReciverState(), messageListReq.getSenderState(), messageListReq.getTopic(), 
					messageListReq.getStarttime(), messageListReq.getEndtime(),
					messageListReq.getType(), messageListReq.getPageNo(), messageListReq.getPageSize());
			
			
			messageListResp.setWebPage(page);
			messageListResp.setError_code(ConstsCore.ERROR_SUCC);
		}catch(Exception e){
			e.printStackTrace();
			messageListResp.setError_code(ConstsCore.ERROR_FAIL);
			messageListResp.setError_msg("查询错误："+e.getMessage());
		}
		addReturn(messageListReq, messageListResp);
		return messageListResp;
	}
	
	@Override
	public MessageResp addMessage(MessageReq messageReq){
		
		messageManager.addMessage(messageReq.getMessage());
		
		MessageResp messageResp = new MessageResp();
		addReturn(messageReq, messageResp);
		return messageResp;
	}

	@Override
	public MessageDetaiResp getMessageDetail(MessageDetailReq messageDetailReq) {
		// TODO Auto-generated method stub
		
		MessageDetaiResp messageDetaiResp = new MessageDetaiResp();
		try{
			UserMsg userMsg = this.userMessageManager.getMessageById(messageDetailReq);
			messageDetaiResp.setUserMsg(userMsg);
			messageDetaiResp.setError_code(ConstsCore.ERROR_SUCC);
		}catch(Exception e){
			e.printStackTrace();
			messageDetaiResp.setError_code(ConstsCore.ERROR_FAIL);
			messageDetaiResp.setError_msg("操作失败："+e.getMessage());
		} 
		addReturn(messageDetailReq, messageDetaiResp);
		return messageDetaiResp;
	}
	
	public IMessageManager getMessageManager() {
		return messageManager;
	}

	public void setMessageManager(IMessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public MessageManager getUserMessageManager() {
		return userMessageManager;
	}

	public void setUserMessageManager(MessageManager userMessageManager) {
		this.userMessageManager = userMessageManager;
	}
}