package com.ztesoft.net.app.base.core.service.auth.impl;

import java.util.List;

import com.ztesoft.net.app.base.core.model.AuthAction;
import com.ztesoft.net.app.base.core.model.Role;
import com.ztesoft.net.app.base.core.service.auth.IPermissionManager;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 权限管理
 * @author kingapex
 * 2010-11-4下午12:50:09
 */
public class PermissionManager extends BaseSupport implements IPermissionManager {
	
	/**
	 * 为某个用户赋予某些角色<br>
	 * 会清除此用户的前角色，重新赋予
	 * @param userid  用户id
	 * @param roleids 角色id数组
	 */
	@Override
	public void giveRolesToUser(String userid, String[] roleids) {
		if(roleids==null) return ;  // null 判断放在最前面。 要删除所有角色， 传入一个空数组
		this.baseDaoSupport.execute("delete from user_role where userid=?", userid);
//		for(String roleid:roleids){
//			this.baseDaoSupport.execute("insert into user_role(roleid,userid)values(?,?)", roleid,userid);
//		}
		for(int i=0;i<roleids.length;i++){
			String roleid = roleids[i];
			this.baseDaoSupport.execute("insert into user_role(id,userid,roleid)values(?,?,?)", this.baseDaoSupport.getSeqByTableName("ES_USER_ROLE"),userid,roleid);
		}
	}
	
	/**
	 * 读取某用户的权限点
	 * @param userid 用户id
	 * @param acttype 权限类型
	 * @return
	 */
	@Override
	public List<AuthAction> getUesrAct(String userid, String acttype) {
		
		String sourceSql = "" ;
		
		if(Consts.CURR_FOUNDER_1.equals(ManagerUtils.getFounder() + ""))
			sourceSql = " and source_from in('" + Consts.WSSMALL + "','" + 
							ManagerUtils.getSourceFrom() + "') ";
		else 
			sourceSql = " and source_from = '" + ManagerUtils.getSourceFrom() + "'";
		
		//查询权限表acttype符合条记录
		String sql ="select * from "+ this.getTableName("auth_action")+" where type=? " + sourceSql;
		//并且 权限id在用户的角色权限范围内
		sql+=" and actid in(select authid from  "+this.getTableName("role_auth")+" where roleid in ";
		//查询用户的角色列表
		sql+=" (select roleid from "+this.getTableName("user_role")+" where userid=? " + sourceSql + ")";
		sql+= sourceSql + " )";
	 
		return this.daoSupport.queryForList(sql,AuthAction.class,acttype,userid);
	}

	
	/**
	 * 读取某用户的角色集合
	 * @param userid
	 * @return 此用户的角色集合
	 */	
	@Override
	public List<Role> getUserRoles(String userid) {
//		if(Consts.CURR_FOUNDER_1.equals(ManagerUtils.getFounder() + "")){
//			return this.baseDaoSupport.queryForList("select a.roleid roleid,b.rolename rolename from  es_user_role  a " +
//					"left join es_role b on a.roleid=b.roleid and a.source_from = b.source_from where a.source_from = '" 
//					+ ManagerUtils.getSourceFrom() + "'");
//		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.*,(select count(*) from es_role_auth  where authid in(select id from  es_role_data) and roleid =a.roleid ) is_def FROM es_role a,es_user_role b");
		sql.append(" WHERE a.roleid = b.roleid");
		sql.append(" AND a.source_from = '"+ManagerUtils.getSourceFrom()+"'");
		sql.append(" AND b.source_from = '"+ManagerUtils.getSourceFrom()+"'");
		sql.append(" AND b.userid = '"+userid+"'");
		
		return this.baseDaoSupport.queryForList(sql.toString(),Role.class);
	}
	
	
	/**
	 * 删除某用户的所有角色
	 * @param userid 要删除角色的用户
	 */
	@Override
	public void cleanUserRoles(String userid){
		this.baseDaoSupport.execute("delete from user_role where userid=?", userid);
	}

	
	

}
