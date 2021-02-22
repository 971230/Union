package com.ztesoft.net.app.base.core.service;

import com.ztesoft.net.app.base.core.model.Notice;
import com.ztesoft.net.framework.database.Page;

import java.util.List;

public interface INoticeManager {
    /**
     * 
     * @param notice  公告对象
     */
	public void addNotice(Notice notice);
    
    public void editNotice(Notice notice);
    
    public void deleteNotice(String notice_id);
    
    public Page ListAllNotice(String title,int pageSize,int pageNo);
    
    public Notice getNotice(String notice_id);
    
    public boolean isMaxCount();//判断是否超过最大值 没有超过为true 超过了为false
    
    public int getMaxCount();
    
    public List getNewNoticeList();
    
    public Page getMoreNoticeList(int pageSize,int pageNo);
    
}
