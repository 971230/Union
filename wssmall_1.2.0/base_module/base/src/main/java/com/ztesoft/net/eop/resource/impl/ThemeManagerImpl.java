package com.ztesoft.net.eop.resource.impl;

import java.util.List;

import com.ztesoft.net.eop.resource.IThemeManager;
import com.ztesoft.net.eop.resource.model.Theme;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.FileBaseUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class ThemeManagerImpl extends BaseSupport<Theme> implements IThemeManager {
 

	@Override
	public void clean() {
		this.baseDaoSupport.execute("truncate table theme");
	}
	@Override
	public Theme getTheme(String themeid) {
		//logger.info(themeid);
		//add by wui 写死主题
		themeid ="1";
		Theme theme =this.baseDaoSupport.queryForObject("select * from theme where id=? and source_from ='"+ManagerUtils.getSourceFrom()+"'", Theme.class, themeid);
		if(theme ==null)
			theme  = new Theme();
		theme.setId(1 + "");
		theme.setDeleteflag(0);
		theme.setSiteid("0");
		theme.setThumb("preview.png");
		theme.setPath(StringUtil.getThemePath());
		return theme;
	}

	
	@Override
	public List<Theme> list() {
		if(EopContext.getContext().getCurrentSite().getMulti_site()==0){
			return this.baseDaoSupport.queryForList("select * from theme where siteid = 0",Theme.class);
		}else{
			return this.baseDaoSupport.queryForList("select * from theme where siteid = ?",Theme.class, EopContext.getContext().getCurrentChildSite().getSiteid());
		}
		
	}
	
	/* 
	 * 取得主站的theme列表
	 * (non-Javadoc)
	 * @see com.enation.eop.core.resource.IThemeManager#getMainThemeList()
	 */
	@Override
	public List<Theme> list(int siteid) {
		return this.baseDaoSupport.queryForList("select * from theme where siteid = 0",Theme.class);
	}

	@Override
	public void addBlank(Theme theme){
		try {
	 
			//公用模板由common目录复制，非公用由产品目录复制
			String basePath =  EopSetting.APP_DATA_STORAGE_PATH;
			basePath =basePath + "/themes";
			
			//复制资源到静态资源服务器
			theme.setSiteid("0");
			String contextPath = EopContext.getContext().getContextPath();
			String targetPath  = EopSetting.IMG_SERVER_PATH+ contextPath+ "/themes/" + theme.getPath();
			FileBaseUtil.copyFolder(basePath + "/blank/images",targetPath + "/images");
			FileBaseUtil.copyFile(basePath + "/blank/preview.png",targetPath + "/preview.png");
			FileBaseUtil.copyFolder(basePath + "/blank/css",targetPath + "/css");
			FileBaseUtil.copyFolder(basePath + "/blank/js",targetPath+ "/js");
			FileBaseUtil.copyFolder(basePath + "/blank",EopSetting.EOP_PATH
					+contextPath
					+ "/themes/" + theme.getPath());
			this.baseDaoSupport.insert("theme", theme);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("创建主题出错");
		}
	}
	
	@Override
	public String add(Theme theme,boolean isCommon) {
		try {
			this.copy(theme,isCommon);
		//	logger.info("安装"+ theme.getThemename());
			this.baseDaoSupport.insert("theme", theme);
			return theme.getId();
		} catch (Exception e) {
			 
			e.printStackTrace();
			throw new RuntimeException("安装主题出错");
		}
		
	}

	
	private  void copy( Theme theme ,boolean isCommon) throws Exception {
		//公用模板由common目录复制，非公用由产品目录复制
		String basePath = isCommon?EopSetting.APP_DATA_STORAGE_PATH:EopSetting.PRODUCTS_STORAGE_PATH+"/" + theme.getProductId() ;
		basePath =basePath + "/themes";
		
		//复制资源到静态资源服务器
		String contextPath = EopContext.getContext().getContextPath();
		String targetPath  = EopSetting.IMG_SERVER_PATH+ contextPath+ "/themes/" + theme.getPath();
		FileBaseUtil.copyFolder(basePath + "/" + theme.getPath() + "/images",targetPath + "/images");
		FileBaseUtil.copyFile(basePath + "/" + theme.getPath() + "/preview.png",targetPath + "/preview.png");
		FileBaseUtil.copyFolder(basePath + "/" + theme.getPath() + "/css",targetPath + "/css");
		FileBaseUtil.copyFolder(basePath + "/" + theme.getPath() + "/js",targetPath+ "/js");

		
		FileBaseUtil.copyFolder(basePath + "/" + theme.getPath(),EopSetting.EOP_PATH
				+contextPath
				+ "/themes/" + theme.getPath());
		/*
		 * 只考jsp到eop应用服务器中
		 
		IOFileFilter txtSuffixFilter = FileFilterUtils.suffixFileFilter(".jsp");
		IOFileFilter txtFiles = FileFilterUtils.andFileFilter(FileFileFilter.FILE, txtSuffixFilter);

		FileBaseUtils.copyDirectory(
				new File(basePath + "/" + theme.getPath() )
				, 
				
				new File(EopSetting.EOP_PATH
				+ "/user/"
				+userid
				+ "/"
				+siteid
				+ "/themes/" + theme.getPath())
				, 
				txtFiles
				);
		
		
		FileBaseUtil.copyFolder(basePath + "/" + theme.getPath(), StringUtil
				.getRootPath()
				+ "/user/"
				+userid
				+ "/"
				+siteid
				+ "/themes/" + theme.getPath());
				*/
	}
	
}
