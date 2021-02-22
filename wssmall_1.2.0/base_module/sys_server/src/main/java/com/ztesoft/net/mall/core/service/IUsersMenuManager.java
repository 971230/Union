package com.ztesoft.net.mall.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.UsersMenu;

public interface IUsersMenuManager {
   public void add(UsersMenu usersMenu);
   
   public void edit(UsersMenu usersMenu,String user_id,String menu_id);
   
   public void delete(String user_id,String menu_id);
   
   public UsersMenu getUsersMenu(String user_id,String menu_id);
   
   public Page listUsersMenu(int pageNo,int pageSize,String menu_name);
   
   public Page listSelMenu(int pageNo,int pageSize,String menu_name);
   
   public int  getCountByUserIdAndFromAndMenu(String menu_id);
}
