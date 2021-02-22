package com.ztesoft.net.mall.core.action.backend;

import java.io.File;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.util.FileUtil;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.UsersMenu;
import com.ztesoft.net.mall.core.service.IUsersMenuManager;
import commons.CommonTools;

public class UsersMenuAction extends WWAction{
	private IUsersMenuManager usersMenuManager;
    private UsersMenu usersMenu;
    private String user_id;
    private String menu_id;
    private String menu_name;
    private String menu_title;
    private Page selMenuPage;
    private String currUserId;
    private File file;
	private String fileFileName;
    public String listUsersMenu(){
    	this.currUserId = CommonTools.getUserId();
    	this.webpage = this.usersMenuManager.listUsersMenu(this.getPage(), this.getPageSize(), menu_name);
    	return "usersMenuList";
    } 
    public String add(){
    	return "add";
    }
    public String addSave(){
    	try{
    		
    		int count = this.usersMenuManager.getCountByUserIdAndFromAndMenu(this.usersMenu.getMenu_id());
    		if(count>0){
    			 this.urls.put("快捷列表","usersMenuAction!listUsersMenu.do");
        		 this.msgs.add("操作失败：该菜单已经设置了快捷菜单,不能重复设置");
        		 return WWAction.MESSAGE;
    		}
    		String path = "";
    		if(!StringUtils.isEmpty(fileFileName)){
	    		if (!FileUtil.isAllowUp(fileFileName)){
	    			 this.json = "{result:1,message:'上传图片格式不正确'}";
	    			 String url ="/core/admin/usersMenuAction!add.do";
	        		 this.urls.put("添加快捷菜单",url);
	        		 this.msgs.add("操作失败：上传图片格式不正确");
	    			 return WWAction.MESSAGE;
	    		}
    		
    		 File fileItem = file;
			 String fileItemName = fileFileName;
			 path = UploadUtil.upload(fileItem, fileItemName, "usersMenu");
    		 }
			 usersMenu.setImg_path(path);
    		 this.usersMenuManager.add(usersMenu);
    		 this.json = "{result:0,message:'操作成功'}";
    		 String url ="usersMenuAction!listUsersMenu.do";
    		 this.urls.put("快捷菜单列表",url);
    		 this.msgs.add("操作成功");
		    
    	}catch(Exception e){
    		 e.printStackTrace();
    		 this.json = "{result:0,message:'操作成功'}";
    		 String url ="usersMenuAction!add.do";
    		 this.urls.put("添加快捷菜单",url);
    		 this.msgs.add("操作失败："+e.getMessage());
    		 return WWAction.MESSAGE;
    	}
    	     return WWAction.MESSAGE;
    }
    public String edit(){
    	this.usersMenu = this.usersMenuManager.getUsersMenu(user_id, menu_id);
    	return "edit";
    }
    public String editSave(){
    	try{
    		
    	   if(!this.menu_id.equals(this.usersMenu.getMenu_id())){
    			int count = this.usersMenuManager.getCountByUserIdAndFromAndMenu(this.usersMenu.getMenu_id());
        		if(count>0){
	    			 this.urls.put("快捷列表","usersMenuAction!listUsersMenu.do");
	        		 this.msgs.add("操作失败：该菜单已经设置了快捷菜单,不能重复设置");
	        		 return WWAction.MESSAGE;
        		}
    		}
            String path = "";
    		if(!"".equals(file)&&file!=null){
    		  if(!StringUtils.isEmpty(fileFileName)){
	    			if (!FileUtil.isAllowUp(fileFileName)){
	       			 String url ="usersMenuAction!edit.do?user_id="+user_id+"menu_id="+menu_id;
	           		 this.urls.put("修改快捷菜单",url);
	           		 this.msgs.add("操作失败：上传图片格式不正确");
	       			 return WWAction.MESSAGE;
	       		     }else{
	       		    	 File fileItem = file;
	       				 String fileItemName = fileFileName;
	       				 path = UploadUtil.upload(fileItem, fileItemName, "usersMenu");
	       				
	       		     }
    		  }
    		}
    		 if(!"".equals(path)){
    			 usersMenu.setImg_path(path);
    		 }
			 this.usersMenuManager.edit(usersMenu, user_id, menu_id);
       		 String url ="usersMenuAction!listUsersMenu.do";
       		 this.urls.put("快捷菜单列表",url);
       		 this.msgs.add("操作成功");
       		 return WWAction.MESSAGE;
			 
    	}catch(Exception e){
    		e.printStackTrace();
	    	 this.json = "{result:0,message:'操作成功'}";
	   		 String url ="usersMenuAction!edit.do?user_id="+user_id+"menu_id="+menu_id;
	   		 this.urls.put("修改快捷菜单",url);
	   		 this.msgs.add("操作失败："+e.getMessage());
	   		 return WWAction.MESSAGE;
    	}
    	  
    }
    public String delete(){
    	try{
    		this.usersMenuManager.delete(user_id, menu_id);
    		this.json ="{result:0,message:'操作成功'}";
    	}catch(Exception e){
    		e.printStackTrace();
    		this.json = "{result:1,message:'"+e.getMessage()+"'}";
    		return WWAction.JSON_MESSAGE;
    	}
    	return WWAction.JSON_MESSAGE;
    }
    public String selMenuList(){
    	this.selMenuPage = this.usersMenuManager.listSelMenu(this.getPage(), this.getPageSize(), menu_title);
    	return "selMenuList";
    }
	public UsersMenu getUsersMenu() {
		return usersMenu;
	}
	public void setUsersMenu(UsersMenu usersMenu) {
		this.usersMenu = usersMenu;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getMenu_title() {
		return menu_title;
	}
	public void setMenu_title(String menu_title) {
		this.menu_title = menu_title;
	}
	public IUsersMenuManager getUsersMenuManager() {
		return usersMenuManager;
	}
	public void setUsersMenuManager(IUsersMenuManager usersMenuManager) {
		this.usersMenuManager = usersMenuManager;
	}
	public Page getSelMenuPage() {
		return selMenuPage;
	}
	public void setSelMenuPage(Page selMenuPage) {
		this.selMenuPage = selMenuPage;
	}
	public String getCurrUserId() {
		return currUserId;
	}
	public void setCurrUserId(String currUserId) {
		this.currUserId = currUserId;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
    
    
}
