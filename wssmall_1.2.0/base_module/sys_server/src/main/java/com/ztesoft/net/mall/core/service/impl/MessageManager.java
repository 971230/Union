package com.ztesoft.net.mall.core.service.impl;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.model.Message;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.MsgSet;
import com.ztesoft.net.mall.core.service.IMessageManager;
import com.ztesoft.net.sqls.SF;

public class MessageManager extends BaseSupport<Message> implements IMessageManager {

	
	@Override
	public Page pageMessage(int pageNo, int pageSize, String folder) {
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		String sql = "select * from message where folder = ? ";
		if(folder.equals("inbox")){//收件箱
			sql += " and to_id = ? and del_status != '1'";
		}else{//发件箱
			sql += " and from_id = ? and del_status != '2'";
		}
		sql += " order by date_line desc";
		Page webpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, folder, member.getMember_id());
		List<Map> list = (webpage.getResult());
//		for (Map order : list) {
//			Long time = (Long)order.get("date_line");
//			order.put("date", (new Date(time)));
//		}
		return webpage;
	}

	
	@Override
	public void addMessage(Message message) {
		this.baseDaoSupport.insert("message", message);
		
	}

	
	@Override
	public void delinbox(String ids) {
		this.baseDaoSupport.execute("delete from message where msg_id in (" + ids + ") and del_status = '2'");
		this.baseDaoSupport.execute("update message set del_status = '1' where msg_id in (" + ids + ")");
	}

	
	@Override
	public void deloutbox(String ids) {
		this.baseDaoSupport.execute("delete from message where msg_id in (" + ids + ") and del_status = '1'");
		this.baseDaoSupport.execute("update message set del_status = '2' where msg_id in (" + ids + ")");
	}

	
	@Override
	public void setMessage_read(int msg_id) {
		this.daoSupport.execute("update " + this.getTableName("message") + " set unread = '1' where msg_id = ?", msg_id);
	}


	@Override
	public Page msgSetList(int pageNo, int pageSize, Map params) {

		String sql = SF.sysSql("MSG_SET_LIST");
		Page webpage = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
		return webpage;
	}

	@Override
	public void msgSetDel(String set_id) {
		
		String sql = SF.sysSql("MSG_SET_DELETE_BY_KEY");
		this.daoSupport.execute(sql, set_id);
		
	}

	@Override
	public void msgSetSave(MsgSet msgSet) {
	
		this.baseDaoSupport.insert("es_msg_set", msgSet);
	}

}
