package com.ztesoft.net.app.base.core.action;

import com.ztesoft.net.eop.resource.IAdminThemeManager;
import com.ztesoft.net.eop.resource.ISiteManager;
import com.ztesoft.net.eop.resource.model.AdminTheme;
import com.ztesoft.net.eop.resource.model.EopSite;
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
public class SiteAdminThemeAction extends WWAction {

	private IAdminThemeManager adminThemeManager;
	private ISiteManager siteManager;
	
	private List<AdminTheme> listTheme;
	private AdminTheme adminTheme;
	private EopSite eopSite;
	private String previewpath;
	private String previewBasePath;
	private String themeid;
	
	
	
	
	@Override
	public String execute() throws Exception {
		EopSite site  = EopContext.getContext().getCurrentSite();
		String contextPath = EopContext.getContext().getContextPath();
		previewBasePath = EopSetting.IMG_SERVER_DOMAIN + contextPath+ "/adminthemes/";
		adminTheme = adminThemeManager.get( site.getAdminthemeid());
		listTheme = adminThemeManager.list();
		previewpath = previewBasePath + adminTheme.getPath() + "/preview.png";
		return SUCCESS;
	}
	
	public String change()throws Exception {
		siteManager.changeAdminTheme(themeid);
		return this.execute();
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

	public List<AdminTheme> getListTheme() {
		return listTheme;
	}

	public void setListTheme(List<AdminTheme> listTheme) {
		this.listTheme = listTheme;
	}

	public AdminTheme getAdminTheme() {
		return adminTheme;
	}

	public void setAdminTheme(AdminTheme adminTheme) {
		this.adminTheme = adminTheme;
	}

	public IAdminThemeManager getAdminThemeManager() {
		return adminThemeManager;
	}

	public void setAdminThemeManager(IAdminThemeManager adminThemeManager) {
		this.adminThemeManager = adminThemeManager;
	}

}
