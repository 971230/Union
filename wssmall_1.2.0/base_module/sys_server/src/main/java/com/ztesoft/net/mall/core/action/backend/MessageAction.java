package com.ztesoft.net.mall.core.action.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.core.service.auth.IAdminUserManager;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Message;
import com.ztesoft.net.mall.core.service.MessageManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;


public class MessageAction extends WWAction {

	private MessageManager userMessageManager;
	private IAdminUserManager adminUserManager;
	private Message message;
	private String topic;
	private String starttime;
	private String endtime;
	private String type;
	private String username;
	private String realname;
	private int founder;
	private String userid;
	private String user_num;
	private String user_name;
	private int num;
	private String messId;
	private String readMsgId;
	private String replyId;
	private String replyName;
	private int reciverState;
	private int senderState;
	private String replyTopic;
	private String reply_mid;
    private int reply_reciverType;
    private List typeList;
	public String findUser() {

		System.out.print(this.getRealname());
		this.webpage = adminUserManager.list(username, realname, founder, this
				.getPage(), this.getPageSize());

		return "show_user";

	}

	public String noReadMsg() {
		this.webpage = this.userMessageManager.list(Consts.reciverMessage_1,
				Consts.READ_STATE_0, senderState, topic, starttime, endtime,
				type, this.getPage(), this.getPageSize());
		return "list_reciver_msg";
	}

	public String listById() {
		this.webpage = this.userMessageManager.list(this.num,
				this.reciverState, this.senderState, topic, starttime, endtime,
				type, this.getPage(), this.getPageSize());
		typeList=this.userMessageManager.findTypeList();
		
		
		if (num == Consts.reciverMessage_1) {
			return "list_reciver_msg";
		}
		return "list_sender_msg";

	}

	public String add() {
		
		try {
			int usernum;
			if(this.user_num.length()<3){
			  usernum =  Integer.parseInt(this.user_num);
			
			if(usernum<5){
				List list = this.adminUserManager.getUseridsByFounder(usernum);
			     String  namestr = this.user_name;
				this.message.setM_reciverid(this.user_num);
				this.message.setM_recivername(namestr);
				this.message.setM_recivertype(this.userMessageManager
						.getReciverType(this.user_num));
				this.userMessageManager.add(message);
				
				
				for(int j=0;j<list.size();j++){
					Map<String, Object> map = (Map)list.get(j);
					String userid = map.get("userid").toString();
				
					
					
					this.userMessageManager.insertMsgRef(this.message.getM_id(),userid,Consts.READ_STATE_0);
				}
			 } 
			}
			else{
				String[] idstr = this.user_num.split(",");
				String[] namestr = this.user_name.split(",");
				
				for (int i = 0; i < idstr.length; i++) {
					this.message.setM_reciverid(idstr[i]);
					this.message.setM_recivername(namestr[i]);
					this.message.setM_recivertype(this.userMessageManager.getReciverType(idstr[i]));
					this.userMessageManager.add(message);
					String m_id = message.getM_id();
				    this.userMessageManager.insertMsgRef(this.message.getM_id(),idstr[i],Consts.READ_STATE_0);
				   }
			}
			
			this.json = "{result:0,message:'消息发送成功'}";
		} catch (RuntimeException e) {

			this.json = "{result:1,message:'消息发送失败'}";
		}

		return WWAction.JSON_MESSAGE;
	}

	public String delete() {

		try {
			String[] msg_id = this.messId.split(",");
            if(this.num ==Consts.senderMessage_2){
			 this.userMessageManager.delete(msg_id, this.num);
            }
            if(this.num == Consts.reciverMessage_1){
            	for(int i=0;i<msg_id.length;i++){
            	 this.userMessageManager.updateMsgRef(msg_id[i], ManagerUtils.getUserId(),Consts.READ_STATE_2);
            	}
             
            }
			this.json = "{'result':0,'message':'删除成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'删除失败'}";
		}
		return WWAction.JSON_MESSAGE;

	}

	public String noReadCount() {

		try {
			int noReadCount = this.userMessageManager.noReadCount();
			String jsonStr = "message:" + noReadCount;// ManagerUtils.toJsonString(countMap);

			this.json = "{result:1," + jsonStr + "}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"读取失败：" + e.getMessage() + "\"}";

		}
		return WWAction.JSON_MESSAGE;
	}

	public String messageCount() {

		try {
			int messageCount = this.userMessageManager.messageCount();
			int wait_message = this.userMessageManager.noReadCount();

			Map countMap = new HashMap();
			countMap.put("wait_message", wait_message + "");
			countMap.put("message_count", messageCount + "");
			String jsonStr = ManagerUtils.toJsonString(countMap);
            
			this.json = "{result:1," + jsonStr + "}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"读取失败：" + e.getMessage() + "\"}";

		}
		return WWAction.JSON_MESSAGE;
	}

	public String read() {

		String mid = this.readMsgId;
		String userid = ManagerUtils.getUserId();
		int  state = this.userMessageManager.selecStateByMidandReciverUserId(ManagerUtils.getUserId(), mid);
		
		if(state==2){
			this.msgs.add("该信息已被删除,您不能再执行此操作");
			this.urls.put("收件箱列表",
					"messageAction!listById.do?num=1&type=0&senderState=0");
			return WWAction.MESSAGE;
		}
		else{
		Message m = this.userMessageManager.getMsgById(mid);
		this.message = m;
		if(m.getM_reciverstate()==1){
			
			return "readMsg";
		}
		m.setM_reciverstate(1);
		
	
	      
		//m.setM_reciverstate(1);

		//this.userMessageManager.edit(m);
	 
	     this.userMessageManager.updateMsgRef(m.getM_id(), ManagerUtils.getUserId(),Consts.READ_STATE_1);
		return "readMsg";
		}
	}

	public String senderRead() {

		String mid = this.readMsgId;
		int state = this.userMessageManager.selecStateByMidandSendUserId(ManagerUtils.getUserId(), mid);
		if(state==1){
			this.msgs.add("该信息已被删除,您不能再执行此操作");
			this.urls.put("发件箱列表",
					"messageAction!listById.do?num=2&type=0&senderState=0");
			return WWAction.MESSAGE;
		}
		else{
		Message m = this.userMessageManager.getMsgById(mid);
		this.message = m;
       
		return "read_senderMsg";
		}
	}

	public String reply() {
		this.replyId = this.readMsgId;
	    int state = this.userMessageManager.selecStateByMidandReciverUserId(ManagerUtils.getUserId(), this.reply_mid);
	    if(state==2){
	    this.msgs.add("该信息已删除，您不能再执行此操作");
		this.urls.put("收件箱列表",
				"messageAction!listById.do?num=1&type=0&senderState=0");
		return WWAction.MESSAGE;
	    }
	    else{
		//this.replyName = adminUserManager.get(replyId).getRealname();
	    	Message replyMsg = new Message();
	    	
	    	replyMsg = this.userMessageManager.getMessageById(this.reply_mid, this.replyId);
	    	this.message =  replyMsg;
	    	//this.replyName = replyMsg.getM_sendername();
	    	//this.reply_reciverType = replyMsg.getM_sendertype();
		return "reply";
	    }
	}

	public String replyAdd() {
		this.message.setM_recivertype(this.reply_reciverType);
		this.userMessageManager.add(this.message);
		this.userMessageManager.insertMsgRef(this.message.getM_id(),this.message.getM_reciverid(),Consts.READ_STATE_0);
		//this.userMessageManager.updateStateById(this.reply_mid);
		this.userMessageManager.updateMsgRef(this.reply_mid, ManagerUtils.getUserId(),Consts.READ_STATE_4);
		this.msgs.add("回复成功");
		
		this.urls.put("收件箱列表",
				"messageAction!listById.do?num=1&reciverState=3&type=0");
		return WWAction.MESSAGE;
	}

	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

	public String getMessId() {
		return messId;
	}

	public void setMessId(String messId) {
		this.messId = messId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getReadMsgId() {
		return readMsgId;
	}

	public void setReadMsgId(String readMsgId) {
		this.readMsgId = readMsgId;
	}

	public Message getMessage() {
		return message;
	}

	public String getUser_num() {
		return user_num;
	}

	public void setUser_num(String user_num) {
		this.user_num = user_num;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public IAdminUserManager getAdminUserManager() {
		return adminUserManager;
	}

	public void setAdminUserManager(IAdminUserManager adminUserManager) {
		this.adminUserManager = adminUserManager;
	}

	public MessageManager getUserMessageManager() {
		return userMessageManager;
	}

	public void setUserMessageManager(MessageManager userMessageManager) {
		this.userMessageManager = userMessageManager;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public int getFounder() {
		return founder;
	}

	public void setFounder(int founder) {
		this.founder = founder;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getReciverState() {
		return reciverState;
	}

	public void setReciverState(int reciverState) {
		this.reciverState = reciverState;
	}

	public int getSenderState() {
		return senderState;
	}

	public void setSenderState(int senderState) {
		this.senderState = senderState;
	}

	public String getReplyTopic() {
		return replyTopic;
	}

	public void setReplyTopic(String replyTopic) {
		this.replyTopic = replyTopic;
	}

	public String getReply_mid() {
		return reply_mid;
	}

	public void setReply_mid(String reply_mid) {
		this.reply_mid = reply_mid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getReply_reciverType() {
		return reply_reciverType;
	}

	public void setReply_reciverType(int reply_reciverType) {
		this.reply_reciverType = reply_reciverType;
	}

	public List getTypeList() {
		return typeList;
	}

	public void setTypeList(List typeList) {
		this.typeList = typeList;
	}

	
    
}
