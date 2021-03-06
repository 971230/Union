package com.ztesoft.net.app.base.core.service.impl;

import com.ztesoft.net.app.base.core.model.SiteMenu;
import com.ztesoft.net.app.base.core.service.ISiteMenuManager;
import com.ztesoft.net.eop.sdk.database.BaseSupport;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class SiteMenuManager extends BaseSupport<SiteMenu> implements ISiteMenuManager {

	
	@Override
	public void add(SiteMenu siteMenu) {
		this.baseDaoSupport.insert("site_menu", siteMenu);
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	
	public void delete(String id) {
		String sql  ="delete from site_menu where parentid =?";
		this.baseDaoSupport.execute(sql,  id );
		sql = "delete from  site_menu   where menuid=?";
		this.baseDaoSupport.execute(sql,  id );
	}
	
	@Override
	public void edit(SiteMenu siteMenu) {
		this.baseDaoSupport.update("site_menu", siteMenu,"menuid="+siteMenu.getMenuid());
	}

	@Override
	public List<SiteMenu> list(String parentid) {
		String sql  ="select * from site_menu  where 1=1 order by parentid,sort";
		List<SiteMenu> menuList  = this.baseDaoSupport.queryForList(sql, SiteMenu.class);
		List<SiteMenu> topMenuList  = new ArrayList<SiteMenu>();
		if(this.logger.isDebugEnabled()){
			this.logger.debug("查找"+parentid+"的子...");
		}
		for(SiteMenu menu :menuList){
			if(menu.getParentid().equals(parentid)){
				if(this.logger.isDebugEnabled()){
					this.logger.debug("发现子["+menu.getName()+"-"+menu.getMenuid()+"]");
				}
				List<SiteMenu> children = this.getChildren(menuList, menu.getMenuid());
				menu.setChildren(children);
				topMenuList.add(menu);
			}
		}
		
		return topMenuList;
	}
	
	private List<SiteMenu> getChildren(List<SiteMenu> menuList ,String parentid){
		if(this.logger.isDebugEnabled()){
			this.logger.debug("查找["+parentid+"]的子");
		}
		List<SiteMenu> children =new ArrayList<SiteMenu>();
		for(SiteMenu menu :menuList){
			if(menu.getParentid().equals(parentid)){
				if(this.logger.isDebugEnabled()){
					this.logger.debug(menu.getName()+"-"+menu.getMenuid()+"是子");
				}
			 	menu.setChildren(this.getChildren(menuList, menu.getMenuid()));
				children.add(menu);
			}
		}
		return children;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	
	public void updateSort(String[] menuid, Integer[] sort) {
		
		if(menuid==null || sort == null )  throw new  IllegalArgumentException("menuid or sort is NULL");
		if(menuid.length != sort.length )  throw new  IllegalArgumentException("menuid or sort length not same");
		for(int i=0;i<menuid.length;i++){
			this.baseDaoSupport.execute("update site_menu set sort=? where menuid=?",sort[i],menuid[i]);
		}
		
	}
	
	@Override
	public SiteMenu get(String menuid) {	 
		return this.baseDaoSupport.queryForObject("select * from site_menu where menuid=?", SiteMenu.class, menuid);
	}

}
