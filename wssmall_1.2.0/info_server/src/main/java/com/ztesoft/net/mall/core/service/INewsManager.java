package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.app.base.core.model.NewsLv;
import com.ztesoft.net.app.base.core.model.NewsVO;
import com.ztesoft.net.framework.database.Page;

import java.util.List;

/**
 * 快讯接口
 * @author 莫清华
 *
 */
public interface INewsManager {
	/**
	 * 快讯修改
	 * @param news
	 */
	public void updateNews(NewsVO news);
	
	/**
	 * 获取快讯详情
	 * @param news_id
	 * @return
	 */
	public NewsVO getNewsDetail(String news_id);
	
	/**
	 * 快讯新增
	 * @param news
	 */
	public void addNews(NewsVO news);
	
	/**
	 * 快讯删除
	 * @param new_ids
	 */
	public void delNews(String new_ids);
	
	/**
	 * 查询快讯
	 * @param state
	 * @param pageNo
	 * @param pageSize
	 * @param order
	 * @return
	 */
	public Page search(int state,int pageNo,int pageSize);
	
	/**
	 * 列表显示已发布的快讯
	 * @return
	 */
	public List listNews();
	public List listMemberLvNews(String lvids,int size);
	
	public int countAllNews();
	
	public int countNoAudit();
	
	/**
	 * 分页查询
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-18 
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page listPage(int page,int pageSize);

	/**
	 * 查询详细信息
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-18 
	 * @param news_id
	 * @return
	 */
	public NewsVO qryNewsById(String news_id);
	
	public void insertNewsLv(NewsLv newslv);
	public void delNewsLv(String new_id);
	public List liseNewsLv(String new_id);
}
