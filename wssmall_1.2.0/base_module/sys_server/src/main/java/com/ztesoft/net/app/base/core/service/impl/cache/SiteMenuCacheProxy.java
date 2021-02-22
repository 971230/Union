package com.ztesoft.net.app.base.core.service.impl.cache;

import java.util.List;

import params.site.req.SiteMenuReq;
import services.SiteMenuInf;

import com.ztesoft.net.app.base.core.model.SiteMenu;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.cache.AbstractCacheProxy;

public class SiteMenuCacheProxy extends AbstractCacheProxy<List<SiteMenu>>{
	public  static final String MENU_LIST_CACHE_KEY = "siteMenuList";
	
	private SiteMenuInf siteMenuServ;
	
	public SiteMenuCacheProxy(SiteMenuInf siteMenuServ) {
		super(MENU_LIST_CACHE_KEY);
		this.siteMenuServ = siteMenuServ;
	}

	private void cleanCache(){
		EopSite site  = EopContext.getContext().getCurrentSite();
		this.cache.remove( MENU_LIST_CACHE_KEY+"_"+site.getUserid() +"_"+site.getId());
	}
	
	
	public void add(SiteMenu siteMenu) {
		SiteMenuReq siteMenuReq = new SiteMenuReq();
		siteMenuReq.setSiteMenu(siteMenu);
		this.siteMenuServ.add(siteMenuReq);
		this.cleanCache();
	}

	
	public void delete(String id) {
		SiteMenuReq siteMenuReq = new SiteMenuReq();
		siteMenuReq.setId(id);
		this.siteMenuServ.delete(siteMenuReq);
		this.cleanCache();
	 
	}

	
	public void edit(SiteMenu siteMenu) {
		SiteMenuReq siteMenuReq = new SiteMenuReq();
		siteMenuReq.setSiteMenu(siteMenu);
		this.siteMenuServ.edit(siteMenuReq);
		this.cleanCache();
	}

	
	public SiteMenu get(String menuid) {
		SiteMenuReq siteMenuReq = new SiteMenuReq();
		siteMenuReq.setMenuid(menuid);
		return this.siteMenuServ.get(siteMenuReq).getSiteMenu();
	}

	
	public List<SiteMenu> list(String parentid) {
		SiteMenuReq siteMenuReq = new SiteMenuReq();
		EopSite site  = EopContext.getContext().getCurrentSite();
		List<SiteMenu> menuList  =  this.cache.get( MENU_LIST_CACHE_KEY+"_"+site.getUserid() +"_"+site.getId());
		siteMenuReq.setParentid(parentid);
		
		if(menuList== null ){
			menuList = this.siteMenuServ.get(siteMenuReq).getList();
			this.cache.put( MENU_LIST_CACHE_KEY+"_"+site.getUserid() +"_"+site.getId(),menuList);
			if(this.logger.isDebugEnabled()){
				this.logger.debug("load sitemenu from database");
			}
		}else{
			if(this.logger.isDebugEnabled()){
				this.logger.debug("load sitemenu from cache");
			}
		}
		
		return menuList;
	}

	
	public void updateSort(String[] menuid, Integer[] sort) {
		SiteMenuReq siteMenuReq = new SiteMenuReq();
		siteMenuReq.setMenuids(menuid);
		siteMenuReq.setSort(sort);
		this.siteMenuServ.updateSort(siteMenuReq);
		this.cleanCache();
	}
}
