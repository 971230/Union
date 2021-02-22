package com.ztesoft.net.app.base.core.service.auth.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import services.AdminUserInf;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.AuthAction;
import com.ztesoft.net.app.base.core.model.Role;
import com.ztesoft.net.app.base.core.service.auth.IRoleManager;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import commons.CommonTools;

/**
 * 角色管理
 * @author kingapex
 * 2010-10-24下午11:08:12
 */
public class RoleManager extends BaseSupport<Role> implements IRoleManager {

	/**
	 * 添加一个角色
	 * @param role 角色实体
	 * @param acts 此角色的权限集合
	 */
	
	private ICacheUtil cacheUtil;
	private AdminUserInf adminUserServ;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(Role role, String[] authids) {
		
		//添加角色并
		role.setCreate_time(DBTUtil.current());
		this.baseDaoSupport.insert("role", role);
		
		//不赋予权限则直接返回
		if(authids==null) return ;
		
		//获取角色id
		String roleid =  role.getRoleid();
		String id = "";
		
		//为这个角色 赋予权限点，写入角色权限对照表
		for(String authid:authids){
			id = this.baseDaoSupport.getSequences("s_es_role_auth");
			this.baseDaoSupport.execute("insert into role_auth(id,roleid,authid)values(?,?,?)",id,roleid,authid);
		}
		
	}

	/**
	 * 删除某角色
	 * @param roleid
	 */	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(String roleid) {
		
		//删除用户角色
		this.baseDaoSupport.execute("delete from user_role where roleid=?", roleid);
		
		//删除角色权限
		this.baseDaoSupport.execute("delete from role_auth where roleid =?", roleid);
		
		//删除角色 
		this.baseDaoSupport.execute("delete from role where roleid =?", roleid);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void edit(Role role, String[] authids) {
		//校验参数 
		if(role.getRoleid().equals("0")) throw new IllegalArgumentException("编辑角色时id不可为空");
		if(StringUtil.isEmpty( role.getRolename())) throw new IllegalArgumentException("编辑角色时名称不可为空");
		
		//清除角色的权限
		this.baseDaoSupport.execute("delete from role_auth where roleid=?", role.getRoleid());
		String id = "";
		//为这个角色 赋予权限点，写入角色权限对照表
		for(String authid:authids){
			id = this.baseDaoSupport.getSequences("s_es_role_auth");
			this.baseDaoSupport.execute("insert into role_auth(id,roleid,authid)values(?,?,?)", id,role.getRoleid(),authid);
		}		
		//更新角色基本信息
		this.baseDaoSupport.update("role", role, "roleid='"+role.getRoleid()+"'");
	}
	/**
	 * 读取所有角色列表 分页
	 * @return
	 */
	@Override
	public Page rolePage(int pageNo, int pageSize, String role_name,String role_code,String auth_type,String role_group) {
		// TODO Auto-generated method stub
		int founder = ManagerUtils.getFounder();
		String sql ="";
		List param = new ArrayList();
		
		if(Consts.CURR_FOUNDER1 == founder){
			 sql ="select a.*,(select count(*) from es_role_auth  where authid in(select id from  es_role_data) and roleid =a.roleid ) is_def from es_role a where source_from = '"+CommonTools.getSourceForm()+"'";
		}else{
		    sql ="select a.*,(select count(*) from es_role_auth  where authid in(select id from  es_role_data) and roleid =a.roleid ) is_def from es_role a where  ( a.roleid in(select b.roleid from es_user_role b where b.userid=? "+ManagerUtils.apSFromCond("b")+")  and source_from ='"+ManagerUtils.getSourceFrom()+"'  or a.createuserid=?)";
		    param.add(CommonTools.getUserId());
		    param.add(CommonTools.getUserId());
		}
		if(role_name!=null&&!"".equals(role_name)){
		    sql +=" and a.rolename like '%"+role_name+"%'";
		}
		if(!StringUtil.isEmpty(role_code)){
		    sql +=" and a.roleid like '%"+role_code+"%'";
		}
		if(!StringUtils.isEmpty(role_group)) {
			sql+=" and a.role_group ='"+role_group+"'";
		}
		
		if(!StringUtil.isEmpty(auth_type)){
			if("data".equals(auth_type)){
				sql +=" and a.roleid in(select roleid from es_role_auth  where authid in(select id from  es_role_data))";
			}else{
				sql +=" and a.roleid in( select roleid from es_role_auth  where authid   in (select actid from es_auth_action where type=? and source_from ='"+ManagerUtils.getSourceFrom()+"') ) ";
			    param.add(auth_type);
			}
		}
		
		logger.info("角色管理sql:"+sql);
		String countSql = "select count(*) from ("+sql+")"; 
		
		return this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, Role.class, countSql, param.toArray());
	   // return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, param.toArray());
	}
	
	/**
	 * 读取所有角色列表 
	 * @return
	 */
	@Override
	public List<Role> list(String rolename) {
		String sql = "select * from role where 1=1 ";
		
		if(rolename!=null&&!"".equals(rolename)){
			sql+= "  and  rolename like '%"+rolename+"%'";
		}
		
		return this.baseDaoSupport.queryForList(sql,Role.class);
	}

	/**
	 * 读取某个角色信息，同时读取此角色权限
	 * @param roleid
	 * @return 权限id存于role.actids数组中
	 */
	@Override
	public Role get(String roleid){
		List arr = new ArrayList();
		String sql = "select a.actid, a.name,a.type,a.objvalue,a.userid from "+ this.getTableName("auth_action") +" a where actid in(select authid from "+this.getTableName("role_auth")+" where roleid =? "+ManagerUtils.apSFromCond("")+") "+ManagerUtils.apSFromCond("") +
				" union all " +
				"select d.id actid,d.role_code name,'data' type,'' objvalue,d.userid from "+ this.getTableName("role_data") +" d where id in(select authid from "+this.getTableName("role_auth")+" where roleid =? "+ManagerUtils.apSFromCond("")+") "+ManagerUtils.apSFromCond("");
		arr.add(roleid);
		arr.add(roleid);
		List  actlist = this.daoSupport.queryForList(sql,AuthAction.class, arr.toArray());
		Role role = this.baseDaoSupport.queryForObject("select * from role where roleid=?", Role.class, roleid);
		
		if(actlist!=null){
			String[] actids = new String[ actlist.size()];
			for(int i=0;i<actlist.size();i++){
				actids[i] =( (AuthAction)actlist.get(i)).getActid();
			}
			role.setActids(actids);
		}
		return  role;
	}

	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}

	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}

	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}

	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}

	@Override
	public List<Map> getListRoleGroup() {
		String sql = "select p.pkey ,p.pname from es_dc_public_ext p where p.stype='role_group' order by p.pkey desc";
		List<Map>  list =  this.baseDaoSupport.queryForListByMap(sql, null);
		if (list != null && list.size() > 0) {
        return list;
		}  
		return null;
	}

	
}
