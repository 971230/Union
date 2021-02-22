package com.ztesoft.net.eop.processor.backend.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import params.adminuser.req.AdminCurrUserReq;
import params.adminuser.req.AdminUserReq;
import params.adminuser.req.UserActReq;
import params.adminuser.resp.AdminUserResp;
import params.adminuser.resp.UserActResp;
import services.AdminUserInf;
import services.PermissionInf;

import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.net.app.base.core.model.AuthAction;
import com.ztesoft.net.eop.processor.AbstractFacadeProcessor;
import com.ztesoft.net.eop.processor.FacadePage;
import com.ztesoft.net.eop.processor.core.Response;
import com.ztesoft.net.eop.processor.core.StringResponse;
import com.ztesoft.net.eop.resource.IMenuManager;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.resource.model.Menu;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.utils.UploadUtilc;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.service.IDictManager;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.remote.app.service.IAppLocalService;
import com.ztesoft.remote.pojo.AppInfo;

import consts.ConstsCore;

public class MenuJsonGetter extends AbstractFacadeProcessor {
	
	private static Logger logger = Logger.getLogger(MenuJsonGetter.class);
	public MenuJsonGetter (FacadePage page) {
		super(page);
	}

	
	@Override
	protected Response process() {		
	
		Response response = new StringResponse();
		String menu = getMenuJson();
		response.setContent(menu);
		return response;
	}
	
	public  String getMenuJson(){
		StringBuffer json = new StringBuffer();
		
		/*
		 * 调用核心api读取站点的菜单
		 */
		IMenuManager menuManager = SpringContextHolder.getBean("menuManager");
		List<Menu> tempMenuList  = menuManager.getMenuList();
		List<Menu> menuList=  new ArrayList<Menu>();
		PermissionInf permissionServ = SpringContextHolder.getBean("permissionServ");
		AdminUserInf adminUserServ =  SpringContextHolder.getBean("adminUserServ");
		AdminCurrUserReq adminCurrUserReq = new AdminCurrUserReq();
		AdminUserResp adminUserResp = adminUserServ.getCurrentUser(adminCurrUserReq);
	    AdminUser user = new AdminUser();
		if(adminUserResp != null){
			user = adminUserResp.getAdminUser();
		}
		AdminUserReq adminUserReq = new AdminUserReq();
		adminUserReq.setUser_id(user.getUserid());
		if(user ==null)
		{
			AdminUserResp adminUserResp1 = adminUserServ.getAdminUserById(adminUserReq);
			if(adminUserResp1 != null){
				user = adminUserResp1.getAdminUser();
			}
		}
		UserActReq userActReq = new UserActReq();
		userActReq.setUserid(user.getUserid());
		userActReq.setActtype("menu");
		
		UserActResp userActResp = new UserActResp();
		userActResp = permissionServ.getUesrAct(userActReq);
		List<AuthAction> authList = new ArrayList<AuthAction>();
		if(userActResp != null){
			authList = userActResp.getList();
		}
		for(Menu menu:tempMenuList){
			if(new Integer(Menu.MENU_TYPE_APP).intValue() ==menu.getMenutype()){

				if(user.getFounder()!=1){
					if( !checkPermssion(menu,authList) ){
						continue; 
					}
				}
			} 
			//add by qin.yingxiong at 2015/12/15 URL增加token
			String url = BeanUtils.urlAddToken(menu.getUrl(), user.getUsername());
			if(url.indexOf("http:")>-1){
				String requestUrl=httpRequest.getRequestURL().toString();
				String addr = url.substring(0, url.lastIndexOf(":"));
				url = url.replace(addr, requestUrl.substring(0, requestUrl.lastIndexOf(":")));
			}
			menu.setUrl(url);
			menuList.add(menu);
		}
		List<Menu> syslist  = getMenuList(Menu.MENU_TYPE_SYS,menuList);
		List<Menu> applist  = getMenuList(Menu.MENU_TYPE_APP,menuList);
		List<Menu> extlist  = getMenuList(Menu.MENU_TYPE_EXT,menuList);
		
		//获取app权限菜单
		IAppLocalService appLocalService = SpringContextHolder.getBean("appLocalService");
		List<AppInfo> appList = appLocalService.queryAllList();
		
		
		//获取按钮权限菜单
		IDictManager dictManager = SpringContextHolder.getBean("dictManager");
		List<Map> btnList = dictManager.listRoleBtn(); 
		
		
		//获取顶部快捷菜单
		List<Map> shortCutList = menuManager.listShotCutMenu(ManagerUtils.getUserId());
		if(shortCutList!=null && shortCutList.size()>0){
			for(Map shortCut : shortCutList){
				//add by qin.yingxiong at 2015/12/15 URL增加token
				String menu_url = shortCut.get("menu_url")==null?null:(String)shortCut.get("menu_url");
				shortCut.put("menu_url", BeanUtils.urlAddToken(menu_url, user.getUsername()));
			}
		}
		
		//商品系统不显示顶部快捷菜单和订单系统相关菜单
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String localHostIp = System.getProperty("LOCAL_HOST_IP");
		String cacheValue1 = cacheUtil.getConfigInfo("SERVER_IP_ADDR_GOODS_01");
		String cacheValue2 = cacheUtil.getConfigInfo("SERVER_IP_ADDR_GOODS_02");
		if(StringUtils.equals(cacheValue1, localHostIp) || StringUtils.equals(cacheValue2, localHostIp)){
			shortCutList = new ArrayList<Map>();
			List<Menu> applistNew = new ArrayList<Menu>();
			for(Menu app : applist){
				if(!( StringUtils.contains(app.getTitle(), "工作台") || StringUtils.equals(app.getTitle(), "订单管理") || 
						StringUtils.equals(app.getTitle(), "校验") || StringUtils.equals(app.getTitle(), "异常单配置") || 
						StringUtils.equals(app.getTitle(), "报表管理") )){
					applistNew.add(app);
				}
			}
			applist = applistNew;
		}
		
		String  up_id = ""; //第一级菜单ID
		json.append("var app={");
			json.append("'all':[");
			json.append(toAppJson(appList,appList));
			json.append("]");
		json.append("};");
		
		json.append("var btn={");
			json.append("'all':[");
			json.append(toBtnJson(btnList,btnList));
			json.append("]");
		json.append("};");
		
		json.append("var menu ={");
			json.append("'sys':[");
			json.append(toJson(syslist,menuList,up_id));
			json.append("]");
			
			json.append(",'app':[");
			json.append(toJson(applist,menuList,up_id));
			json.append("]");	
	
			json.append(",'ext':[");
			json.append(toJson(extlist,menuList,up_id));
			json.append("]");
			
			json.append(",'shortCut':[");
			json.append(toShortCutJson(shortCutList));
			json.append("]");
		json.append("};");
		HttpServletRequest request  =ThreadContextHolder.getHttpRequest();
		json.append("var mainpage=true;");
		json.append("var domain='"+request.getServerName()+"';");
		json.append("var runmode="+EopSetting.RUNMODE+";");
		json.append("var app_path='"+request.getContextPath()+"';");
		
		AdminUser adminUser = ManagerUtils.getAdminUser();
		adminUser.setMenu_jsons(json.toString());
		
		ConstsCore.ALL_MENU = toJson(menuList,menuList,up_id);
		
		return json.toString();
		
		

	}
	
	public static void main(String[] args) {
		String url = "#ORDEREXP#/shop/admin/sss.html";
		String u1 = url.substring(1,url.indexOf("#", 2));
		String u2 = url.substring(url.indexOf("#", 2)+1);
		logger.info(u1);
		logger.info(u2);
	}
	/**
	 * 将一个menuList转为json数组
	 * @param menuList
	 * @return
	 */
	public  String toJson(List<Menu> menuList,List<Menu> allList,String up_id){
		StringBuffer menuItem = new StringBuffer();
		int i =0;
		
		for(Menu menu:menuList ){
			
			if(i!=0) menuItem.append(",");
			menuItem.append( toJson (menu,allList,up_id));
			i++;
			
		}
		
		return menuItem.toString();
	}
	
	
	/**
	 * 将一个appList转为json数组
	 * @param appList
	 * @param allList
	 * @return
	 */
	public  String toAppJson(List<AppInfo> appList,List<AppInfo> allList){
		StringBuffer appItem = new StringBuffer();
		int i =0;
		
		for(AppInfo app:appList ){
			
			if(i!=0) appItem.append(",");
			appItem.append( toAppJson (app,allList));
			i++;
			
		}
		
		return appItem.toString();
	}
	
	
	private boolean checkPermssion(Menu menu,List<AuthAction> authList){
		for(AuthAction auth:authList){
			String values =  auth.getObjvalue();
			if(values!=null){
				String[] value_ar = values.split(",");
				for(String v:value_ar){
					if(v.equals(menu.getId())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	private String toAppJson(AppInfo appInfo,List<AppInfo> list){
 
		StringBuffer appItem = new StringBuffer();
		
		appItem.append("{");
		
		appItem.append("id:'");
		appItem.append(appInfo.getAppId());
		appItem.append("'");
		
		appItem.append(",text:'");
		appItem.append(appInfo.getAppName());
		appItem.append("'");
		
		appItem.append(",acctType:'");
		appItem.append(appInfo.getAcctType());
		appItem.append("'");
		
		
		appItem.append(",sourceFrom:'");
		appItem.append(appInfo.getSourceFrom());
		appItem.append("'");
		
		appItem.append(",children:'");
		appItem.append("'");
 
		appItem.append("}");
		
		return appItem.toString();
	}
	
	
	
	/**
	 * 根据menu实体生成json字串
	 * @param menu
	 * @return
	 */
	private  String toJson(Menu menu,List<Menu> menuList,String up_id){
		

		String title  =  menu.getTitle();
		String url = menu.getUrl();
		Integer selected = menu.getSelected();
		String type  = menu.getDatatype();
		String target=menu.getTarget();
 
		if(!"_blank".equals(target)){
			String ctx= EopSetting.CONTEXT_PATH;
			ctx=ctx.equals("/")?"":ctx;
			url=ctx + url;
		}
		
 
		StringBuffer menuItem = new StringBuffer();
		
		menuItem.append("{");
		
		menuItem.append("id:");
		menuItem.append(menu.getId());
		
		
		menuItem.append(",text:'");
		menuItem.append(title);
		menuItem.append("'");
		
		if("0".equals(menu.getPid())){
			up_id = menu.getId();
			
		}
		menuItem.append(",up_id:'");
		menuItem.append(up_id);
		menuItem.append("'");

		menuItem.append(",url:'");
		menuItem.append(url);
		menuItem.append("'");
		
		
		menuItem.append(",'default':");
		menuItem.append(selected);
 
		menuItem.append(",children:");
		menuItem.append(getChildrenJson(menu.getId(), menuList,up_id));
		
		menuItem.append(",type:'");
		menuItem.append(type);
		menuItem.append("'");
		
		menuItem.append(",target:'");
		menuItem.append(target);
		menuItem.append("'");
		
		menuItem.append("}");
		
		return menuItem.toString();
	}
	
	/**
	 * 将一个btnList转为json数组
	 * @param btnList
	 * @param allList
	 * @return
	 */
	public String toBtnJson(List<Map> btnList,List<Map> allList){
		StringBuffer btnItem = new StringBuffer();
		int i =0;
		
		for(Map map:btnList ){
			
			if(i!=0) btnItem.append(",");
			btnItem.append( toBtnJson (map,allList));
			i++;
			
		}
		
		return btnItem.toString();
	}
	
	private String toBtnJson(Map map,List<Map> list){
		 
		StringBuffer appItem = new StringBuffer();
		
		appItem.append("{");
		
		appItem.append("id:'");
		appItem.append(map.get("btn_role_code"));
		appItem.append("'");
		
		appItem.append(",text:'");
		appItem.append(map.get("btn_role_name"));
		appItem.append("'");
		
		appItem.append(",children:'");
		appItem.append("'");
 
		appItem.append("}");
		
		return appItem.toString();
	}
	
	/**
	 * 将一个shortCutList转为json数组
	 * @param btnList
	 * @param allList
	 * @return
	 */
	public String toShortCutJson(List<Map> shortCutList){
		StringBuffer shortCutItem = new StringBuffer();
		int i =0;
		
		for(Map map:shortCutList ){
			
			if(i!=0) shortCutItem.append(",");
			shortCutItem.append( toShortCutJson (map));
			i++;
			
		}
		
		return shortCutItem.toString();
	}
	
	private String toShortCutJson(Map map){
		 
		StringBuffer appItem = new StringBuffer();
		
		appItem.append("{");
		
		appItem.append("id:'");
		appItem.append(map.get("menu_id"));
		appItem.append("'");
		
		appItem.append(",url:'");
		String url = (String) map.get("menu_url");
		String ctx= EopSetting.CONTEXT_PATH;
		ctx=ctx.equals("/")?"":ctx;
		url=ctx + url;
		appItem.append(url);
		appItem.append("'");
		
		appItem.append(",img:'");
		String img = UploadUtilc.replacePath((String) map.get("img_path"));
		appItem.append(img);
		appItem.append("'");
		
		appItem.append(",text:'");
		appItem.append(map.get("menu_name"));
		appItem.append("'");
 
		appItem.append("}");
		
		return appItem.toString();
	}
	
	
	/**
	 * 根据菜单某种类型过滤出menuList
	 * @param menuType
	 * @param menuList
	 * @return
	 */
	public  List<Menu> getMenuList(String menuType,List<Menu> menuList){
		List<Menu> mlist = new ArrayList<Menu>();
		
		for(Menu menu :menuList ){
			if(menu.getMenutype() == new Integer(menuType).intValue() &&  menu.getPid().equals("0")){
				mlist.add(menu);
			}
		}
		
		return mlist;
	}
	
	
	/**
	 * 读取
	 * @param menuId
	 * @param menuList
	 * @return
	 */
	private  String  getChildrenJson(String menuId,List<Menu> menuList,String up_id){
		StringBuffer json = new StringBuffer(); 
		json.append("[");
		int i=0;
		for (Menu menu : menuList) {
			
			if (menuId.equals(menu.getPid())) {
				if(i!=0)
					json.append(",");
				json.append(toJson(menu, menuList,up_id));
				i++;
			}
		}
		json.append("]");
		return json.toString();
	}

}
