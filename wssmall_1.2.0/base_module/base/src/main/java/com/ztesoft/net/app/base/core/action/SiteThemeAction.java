package com.ztesoft.net.app.base.core.action;

import com.opensymphony.xwork2.Action;
import com.ztesoft.net.eop.resource.ISiteManager;
import com.ztesoft.net.eop.resource.IThemeManager;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.resource.model.Theme;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.action.WWAction;

import java.util.List;

/**
 * 站点主题管理
 * 
 * @author lzf
 *         <p>
 *         2009-12-30 上午11:01:08
 *         </p>
 * @version 1.0
 */
public class SiteThemeAction extends WWAction {

	private List<Theme> listTheme;
	private Theme theme;
	private IThemeManager themeManager;
	private EopSite eopSite;
	private ISiteManager siteManager;
	private String previewpath;
	private String previewBasePath;
	private String themeid;

	
	@Override
	public String execute() throws Exception {
		EopSite site  = EopContext.getContext().getCurrentSite();
		String contextPath = EopContext.getContext().getContextPath();
		previewBasePath = EopSetting.IMG_SERVER_DOMAIN +contextPath+ "/themes/";
 
		theme = themeManager.getTheme( site.getThemeid());
		listTheme = themeManager.list();
		previewpath = previewBasePath + theme.getPath() + "/preview.png";
		return SUCCESS;
	}
	
	
	public String add(){
		
		return Action.INPUT;
	}
	
	public String save(){
		this.msgs.add("模板创建成功");
		this.urls.put("品牌列表", "siteTheme.do");
		this.themeManager.addBlank(theme);
		return WWAction.MESSAGE;
	}
	
	public String change()throws Exception {
		siteManager.changeTheme( themeid);
		return this.execute(); 
	}

	public List<Theme> getListTheme() {
		return listTheme;
	}

	public void setListTheme(List<Theme> listTheme) {
		this.listTheme = listTheme;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public IThemeManager getThemeManager() {
		return themeManager;
	}

	public void setThemeManager(IThemeManager themeManager) {
		this.themeManager = themeManager;
	}

	public EopSite getEopSite() {
		return eopSite;
	}

	public void setEopSite(EopSite eopSite) {
		this.eopSite = eopSite;
	}

	public ISiteManager getSiteManager() {
		return siteManager;
	}

	public void setSiteManager(ISiteManager siteManager) {
		this.siteManager = siteManager;
	}

	public String getPreviewpath() {
		return previewpath;
	}

	public void setPreviewpath(String previewpath) {
		this.previewpath = previewpath;
	}

	public String getPreviewBasePath() {
		return previewBasePath;
	}

	public void setPreviewBasePath(String previewBasePath) {
		this.previewBasePath = previewBasePath;
	}

	public String getThemeid() {
		return themeid;
	}

	public void setThemeid(String themeid) {
		this.themeid = themeid;
	}
 

}
