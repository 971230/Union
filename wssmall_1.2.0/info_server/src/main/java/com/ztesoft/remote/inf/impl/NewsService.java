package com.ztesoft.remote.inf.impl;

import java.util.List;

import javax.annotation.Resource;

import services.ServiceBase;

import com.ztesoft.net.app.base.core.model.NewsVO;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.service.INewsManager;
import com.ztesoft.remote.inf.INewsService;
import com.ztesoft.remote.params.news.req.NewsCountReq;
import com.ztesoft.remote.params.news.req.NewsPageReq;
import com.ztesoft.remote.params.news.req.NewsReq;
import com.ztesoft.remote.params.news.req.NewsVoReq;
import com.ztesoft.remote.params.news.resp.NewsCountResp;
import com.ztesoft.remote.params.news.resp.NewsPageResp;
import com.ztesoft.remote.params.news.resp.NewsResp;

/**
 * 消息快讯信息service
* @作者 wu.i 
* @创建日期 2013-9-23 
* @版本 V 1.0
* 
 */
@SuppressWarnings("unchecked")
public class NewsService extends ServiceBase implements INewsService {
   
	@Resource
	private INewsManager newsManager;
	
	@Override
	public NewsResp queryNews(NewsReq newsReq) {
		NewsResp newsResp = new NewsResp();
		List<NewsVO> listNews = this.newsManager.listMemberLvNews(newsReq.getLv_id(), newsReq.getSize());
		newsResp.setNewsList(listNews);
		this.addReturn(newsReq, newsResp);
		return newsResp;
	}
	
	@Override
	public NewsPageResp getNewsPage(NewsPageReq newsPageReq) {
		NewsPageResp newsPageResp = new NewsPageResp();
		Page page = this.newsManager.listPage(newsPageReq.getPageNo(), newsPageReq.getPageSize());
		newsPageResp.setNewsPage(page);
		this.addReturn(newsPageReq, newsPageResp);
		return newsPageResp;
	}
	
	@Override
	public NewsResp getNewsVo(NewsVoReq newsVoReq) {
		NewsVO newsVo = this.newsManager.qryNewsById(newsVoReq.getNews_id());
		NewsResp newsResp = new NewsResp();
		newsResp.setNewsVo(newsVo);
		this.addReturn(newsVoReq, newsResp);
		return newsResp;
	}
	
	@Override
	public NewsCountResp getNewsCount(NewsCountReq newsCountReq){
		NewsCountResp newsCountResp = new NewsCountResp();
		int wait_audit_flash =  newsManager.countNoAudit();
        int flash_count = newsManager.countAllNews(); //快讯总数
        newsCountResp.setWait_audit_flash(wait_audit_flash);
        newsCountResp.setFlash_count(flash_count);
        this.addReturn(newsCountReq, newsCountResp);
        
        return newsCountResp;
	 }
	
}