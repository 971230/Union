package com.ztesoft.net.mall.core.service.impl;

import java.util.List;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.app.base.core.model.NewsLv;
import com.ztesoft.net.app.base.core.model.NewsMapper;
import com.ztesoft.net.app.base.core.model.NewsVO;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.INewsManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 快讯管理
 * @author Administrator
 *
 */
public class NewsManager extends BaseSupport<NewsVO> implements INewsManager {

	@Override
	public void addNews(NewsVO news) {
		this.baseDaoSupport.insert("news", news);

	}

	@Override
	public void delNews(String new_ids) {
		if (new_ids == null || new_ids.equals(""))
			return;
		String sql = "delete from news where news_id in (" + new_ids + ")";
		this.baseDaoSupport.execute(sql);

	}

	@Override
	public NewsVO getNewsDetail(String news_id) {
		NewsVO news=this.baseDaoSupport.queryForObject("select * from news where news_id=?", NewsVO.class,news_id);
		return news;
	}

	@Override
	public Page search(int state, int pageNo, int pageSize) {
		AdminUser adminUser = ManagerUtils.getAdminUser(); //获取登录用户
		int founder = adminUser.getFounder();
		StringBuffer sql = new StringBuffer("select * from news where 1=1");
		if(state!=-1){
			sql.append(" and state="+state);
		}
		if (Consts.CURR_FOUNDER0!=founder&&Consts.CURR_FOUNDER1!=founder){
			sql.append(" and user_id='"+adminUser.getUserid()+"'");
		}
		sql.append(" order by news_id desc");
		Page page=this.baseDaoSupport.queryForPage(sql.toString(), pageNo, pageSize);
		return page;
	}

	@Override
	public void updateNews(NewsVO news) {
		this.baseDaoSupport.update("news",news,"news_id='"+news.getNews_id()+"'");

	}

	@Override
	public List listNews() {
		String sql="select * from news where state=1 and browser_limit is null";
		Member member=(Member)ThreadContextHolder.getSessionContext().getAttribute(IUserService.CURRENT_MEMBER_KEY);
		/**
		 * 从session获取用户选择的等级
		 */
		String session_lv_id = (String) ThreadContextHolder.getSessionContext().getAttribute(Const.SESSION_MEMBER_LV);
		if(member!=null){
			sql+=" or browser_limit="+member.getLv_id();
		}
		sql+=" order by create_time desc";
		List<NewsVO> newsList=this.baseDaoSupport.queryForList(sql,new NewsMapper());
		return newsList;
	}
	
	@Override
	public int countAllNews(){
		int result=0;
    	String sql="select count(*) from "+this.getTableName("news");
    	result=this.daoSupport.queryForInt(sql);
    	return result;
	}
	
	@Override
	public int countNoAudit(){
		int result=0;
    	String sql="select count(*) from "+this.getTableName("news")+" where state=?";
    	result=this.daoSupport.queryForInt(sql, 0);
    	return result;
	}

	@Override
	public Page listPage(int page, int pageSize) {
		String sql = "select * from es_news where state=1 and browser_limit is null order by create_time desc";
		//String count = "select count(*) from es_news where state=1 and browser_limit is null";
		return this.baseDaoSupport.queryForPage(sql, page, pageSize, NewsVO.class);
	}

	@Override
	public NewsVO qryNewsById(String news_id) {
		String sql = "select * from es_news where news_id=?";
		return this.baseDaoSupport.queryForObject(sql, NewsVO.class, news_id);
	}

	@Override
	public void insertNewsLv(NewsLv newslv) {
		//String sql = "insert into es_news_lv(news_id,lv_id) values(?,?)";
		this.baseDaoSupport.insert("es_news_lv", newslv);
	}

	@Override
	public void delNewsLv(String new_id) {
		this.baseDaoSupport.execute("delete from es_news_lv where news_id=?", new_id);
	}

	@Override
	public List liseNewsLv(String new_id) {
		String sql = "select * from es_news_lv where news_id=?";
		List list = this.baseDaoSupport.queryForList(sql, new_id);
		return list;
	}

	@Override
	public List listMemberLvNews(String lvids,int size) {
		String sql = "select * from es_news n where exists(select 1 from es_news_lv lv where lv.news_id=n.news_id and lv.lv_id in("+lvids+")) "+DBTUtil.andRownum("?");
		List<NewsVO> newsList=this.baseDaoSupport.queryForList(sql,new NewsMapper(),size);
		return newsList;
	}
	
	

}
