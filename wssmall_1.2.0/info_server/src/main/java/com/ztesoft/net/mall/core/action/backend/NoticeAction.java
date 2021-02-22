package com.ztesoft.net.mall.core.action.backend;

import java.util.List;

import net.sf.json.JSONArray;

import com.ztesoft.net.app.base.core.model.Notice;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.service.INoticeManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class NoticeAction extends WWAction{
    private INoticeManager noticeManager;
	private Notice notice;
	private String noticeTitle;
    private String notice_id;
    
    private String curUserId;
    
    public String list(){
       this.curUserId = ManagerUtils.getUserId();
       this.webpage = this.noticeManager.ListAllNotice(noticeTitle, this.getPageSize(), this.getPage());
       return "list";
    }
    public String newNoticeList(){
    	try{
    	List noticeList =  this.noticeManager.getNewNoticeList();
		
		JSONArray arr = new JSONArray();
		arr = JSONArray.fromObject(noticeList);
		
		this.json = arr.toString();
	    } catch (RuntimeException e) {
		e.printStackTrace();
     	}
	     return WWAction.JSON_MESSAGE;
    }
    public String detail(){
       this.notice = this.noticeManager.getNotice(notice_id);
    	
       return "show_detail";
    }
    public String showMoreNotice(){
    	this.webpage = this.noticeManager.getMoreNoticeList(this.getPageSize(), this.getPage());
    	return "more_notice";
    }
    public String add(){
    	return "notice_add";
    }
    public String addSave(){
    	try{
    		int maxCount = this.noticeManager.getMaxCount();
    		boolean flag = this.noticeManager.isMaxCount();
    		if(flag){
    			String content = notice.getContent();
    			content = content.replaceAll("\n", "");
    			notice.setContent(content.replaceAll("＝", "="));
				this.noticeManager.addNotice(notice);
				this.json = "{result:0,message:'操作成功'}";
    		}else{
    			this.json = "{result:1,message:'操作失败，系统最多允许发布"+maxCount+"条最新活动'}";
    		}
			
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}

		return WWAction.JSON_MESSAGE;
    	
    }
    public String edit(){
    	this.notice = this.noticeManager.getNotice(notice_id);
    	return "edit";
    }
    public String editSave(){
    	
    	try{
    		String content = notice.getContent();
    		content = content.replaceAll("\n", "");
			notice.setContent(content.replaceAll("＝", "="));
			this.noticeManager.editNotice(notice);
			this.json = "{result:0,message:'操作成功'}";
			
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}

		return WWAction.JSON_MESSAGE;
    }
    
    public String delete(){
    	try{
			this.noticeManager.deleteNotice(notice_id);
			this.json = "{result:0,message:'操作成功'}";
			
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}

		return WWAction.JSON_MESSAGE;
    	
    }
	public INoticeManager getNoticeManager() {
		return noticeManager;
	}
	public void setNoticeManager(INoticeManager noticeManager) {
		this.noticeManager = noticeManager;
	}
	
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNotice_id() {
		return notice_id;
	}
	public void setNotice_id(String notice_id) {
		this.notice_id = notice_id;
	}
	public Notice getNotice() {
		return notice;
	}
	public void setNotice(Notice notice) {
		this.notice = notice;
	}
	public String getCurUserId() {
		return curUserId;
	}
	public void setCurUserId(String curUserId) {
		this.curUserId = curUserId;
	}
    
    
    
}
