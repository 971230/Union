package com.ztesoft.net.mall.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.UsersMenu;
import com.ztesoft.net.mall.core.service.IUsersMenuManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import commons.CommonTools;

public class UsersMenuManagerImpl extends BaseSupport implements IUsersMenuManager{

	@Override
	public void add(UsersMenu usersMenu) {
		// TODO Auto-generated method stub
		usersMenu.setUser_id(CommonTools.getUserId());
		usersMenu.setSource_from(CommonTools.getSourceForm());
		usersMenu.setCreate_time(Consts.SYSDATE);
		usersMenu.setDisabled("0");
		this.baseDaoSupport.insert("es_user_smenu", usersMenu);
	}

	@Override
	public void edit(UsersMenu usersMenu, String user_id, String menu_id) {
		// TODO Auto-generated method stub
		this.baseDaoSupport.update("es_user_smenu", usersMenu, "user_id='" + user_id +"' and menu_id="+menu_id);
	}

	@Override
	public void delete(String user_id, String menu_id) {
		// TODO Auto-generated method stub
		String sql ="delete es_user_smenu  where menu_id =? and user_id=?";
		this.baseDaoSupport.execute(sql, menu_id,user_id);
	}

	@Override
	public Page listUsersMenu(int pageNo, int pageSize, String menu_name) {
		// TODO Auto-generated method stub
		String sql = "select * from es_user_smenu where disabled ='0' and source_from = '"+CommonTools.getSourceForm()+"' and user_id = '"+CommonTools.getUserId()+"'";
		if("1".equals(CommonTools.getUserId())){
			sql ="select * from es_user_smenu where disabled ='0' and source_from = '"+CommonTools.getSourceForm()+"'";
		}
		if(menu_name!=null&&!"".equals(menu_name)){
			sql += " and menu_name like '%"+menu_name+"%'";
		}
		return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
	}

	@Override
	public UsersMenu getUsersMenu(String user_id, String menu_id) {
		// TODO Auto-generated method stub
		String sql ="select * from es_user_smenu where disabled = '0' and user_id=? and menu_id=?";
		return (UsersMenu) this.baseDaoSupport.queryForObject(sql, UsersMenu.class, user_id,menu_id);
	}

	@Override
	public Page listSelMenu(int pageNo, int pageSize, String menu_name) {
		// TODO Auto-generated method stub
	    String sql ="select id menu_id ,title,url from es_menu where deleteflag= '0'  and source_from ='"+CommonTools.getSourceForm()+"'";
	    if(menu_name!=null&&!"".equals(menu_name)){
	    	sql +=" and title like '%"+menu_name+"%'";
	    }
	    if(!"1".equals(CommonTools.getUserId())){
	    	String menuIdsStr = this.getMenuIdStr();
	    	sql +=" and id in("+menuIdsStr+")";
	    }
	    return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize);
	}
	public String getMenuIdStr(){
		String menuIdsStr = "";
		String sql ="select objvalue from es_auth_action where actid in " +
				    "(select authid from es_role_auth where source_from='"+CommonTools.getSourceForm()+"' and roleid in" +
				    "(select roleid from es_user_role where source_from ='"+CommonTools.getSourceForm()+"' and userid  = '"+CommonTools.getUserId()+"' )) " +
				    "and  source_from ='"+CommonTools.getSourceForm()+"'";// and type='menu' ";
		List list = new ArrayList();
		list = this.baseDaoSupport.queryForList(sql);
		for(int i=0;i<list.size();i++){
			Map valueMap =(Map) list.get(i);
			String objValue = valueMap.get("objValue").toString();
			if(i==0){
				menuIdsStr = objValue;
			}else{
				if(objValue!=null&&!"".equals(objValue)){
					menuIdsStr=menuIdsStr +","+objValue;
				}
			}
		}
		return menuIdsStr;
	}

	@Override
	public int getCountByUserIdAndFromAndMenu(String menu_id) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from es_user_smenu where menu_id=? and source_from ='"+ManagerUtils.getSourceFrom()+"' and disabled ='0' and user_id='"+ManagerUtils.getUserId()+"'";
		int count = 0 ;
		count = this.baseDaoSupport.queryForInt(sql, menu_id);
		return count;
	}

}
