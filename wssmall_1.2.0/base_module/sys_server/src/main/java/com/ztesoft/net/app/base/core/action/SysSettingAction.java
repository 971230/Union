package com.ztesoft.net.app.base.core.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.MsgSet;
import com.ztesoft.net.mall.core.service.IMessageManager;

/**
 * @author apexking
 *
 */
public class SysSettingAction extends WWAction {
	
	private Map<String, String> params = new HashMap<String, String>(0);
	
	@Resource
	private IMessageManager messageManager;
	private MsgSet msgSet;
	
	/**
	 * 到短信设置展示
	 */
	public String msgSetList() {
	
		this.webpage = this.messageManager.msgSetList(this.getPage(), this.getPageSize(), this.getParams());
		return "msg_set_index";
	}
	
	/**
	 * 到短信设置添加
	 */
	public String msgSetAdd() {
	
		return "msg_set_add";
	}
	
	/**
	 * 短信设置删除
	 * @return
	 */
	public String msgSetDel() {
		
		try {
			
			this.messageManager.msgSetDel(this.getMsgSet().getSet_id());
			this.json = "{'result':0, 'message':'删除成功！'}";
		} catch (Exception e) {
			this.json = "{'result':1, 'message':'删除失败！'}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 短信设置保存
	 * @return
	 */
	public String msgSetSave(){
		
		try {
			this.messageManager.msgSetSave(this.getMsgSet());
			this.msgs.add("添加成功！");
			this.urls.put("短信模板设置列表", "sysSetting!msgSetList.do");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.msgs.add("非法操作！");
			return WWAction.MESSAGE;
		}
		
		return WWAction.MESSAGE;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public IMessageManager getMsgManager() {
		return messageManager;
	}

	public void setMsgManager(IMessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public MsgSet getMsgSet() {
		return msgSet;
	}

	public void setMsgSet(MsgSet msgSet) {
		this.msgSet = msgSet;
	}
	
}