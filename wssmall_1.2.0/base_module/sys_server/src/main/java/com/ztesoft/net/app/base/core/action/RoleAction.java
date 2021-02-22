package com.ztesoft.net.app.base.core.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.ztesoft.net.app.base.core.model.Role;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import commons.CommonTools;
import params.adminuser.req.AuthListReq;
import params.adminuser.req.RoleAddReq;
import params.adminuser.req.RoleDelReq;
import params.adminuser.req.RoleEditReq;
import params.adminuser.req.RolePageReq;
import params.adminuser.req.RoleReq;
import params.adminuser.resp.AuthListResp;
import params.adminuser.resp.RolePageResp;
import params.adminuser.resp.RoleResp;
import services.AuthInf;
import services.RoleInf;

/**
 * 角色管理
 * @author kingapex
 * 2010-11-4下午05:25:48
 */
public class RoleAction extends WWAction {
	
	private RoleInf roleServ;
	private AuthInf authServ;
	
	private List roleList;
	private List authList;
	private String roleid;
	private Role role;
	private String[] acts;
	private int isEdit;
	
	private String currUserId;
	private String rolename;
	private String auth_type;
	private List<Map> roleGroupListMap; // 组织分类
	private String role_group ;
	private String role_group_name;
	//读取角色列表
	public String list(){
		this.currUserId = CommonTools.getUserId();
		
		//角色分组
		roleGroupListMap = authServ.roleGroupType();
		
		/* 未分页角色列表
		RoleListResp roleResp = new RoleListResp();
		RoleListReq roleListReq = new RoleListReq();
		
		roleListReq.setRolename(rolename);
    	roleResp = roleServ.listRole(roleListReq);//该员工权限下所以可供选择的角色
    	roleList = new ArrayList();
    	if(roleResp != null){
    		roleList = roleResp.getRoleList();
    	}*/
		//改成分页列表
		RolePageReq req = new RolePageReq();
		req.setPageNo(this.getPage());
		req.setPageSize(this.getPageSize());
		req.setRole_name(rolename);
		req.setRole_code(this.roleid);
		req.setAuth_type(auth_type);
		req.setRole_group(role_group);
		
    	RolePageResp  rolePageResp = new RolePageResp();
    	rolePageResp = roleServ.rolePage(req);
    	if(rolePageResp!=null){
    		this.webpage = rolePageResp.getWebPage();
    	}
 		return "list";
	}
	
	
	
	//到添加页面
	public String add(){
		AuthListReq authListReq = new AuthListReq();
		authListReq.setStr(new String []{});
		
		roleGroupListMap = authServ.roleGroupType();
		//AuthListResp authListResp = authServ.list(authListReq);
		
//		if(authListResp != null){
//			authList = authListResp.getList();
//		}
		
		return Action.INPUT;
	}
	
	/**
	 * 角色分组
	 * @return
	 */
	public List<Map> roleGroup() {
		return roleGroupListMap = authServ.roleGroupType();
	}
	
	
	//到修改页面
	public String edit(){
		
		isEdit= 1;
		
		RoleReq roleReq = new RoleReq();
		roleReq.setRoleid(roleid);
		RoleResp roleResp = this.roleServ.get(roleReq);
		this.role = new Role();
		if(roleResp != null){
			this.role = roleResp.getRole();
		}
		
		List<Map> listMap = authServ.roleGroupType();
		this.roleGroupListMap = new ArrayList<Map>();
		// 角色分组
		for (Map m: listMap) {
			String kry= m.get("pkey").toString();
			String role_group = this.role.getRole_group();
			if(!kry.equals(role_group)) {
				Map map  = new HashMap<String, String>();
				map.put("pkey", m.get("pkey"));
				map.put("pname", m.get("pname"));
				this.roleGroupListMap.add(map);	
			}else {
			 this.role_group=m.get("pkey").toString();
			 this.role_group_name=m.get("pname").toString();
			}
		}
		
		
		AuthListReq authListReq = new AuthListReq();
		authListReq.setStr(role.getActids());
		
		AuthListResp authListResp = authServ.list(authListReq);
		
		if(authListResp != null){
			authList = authListResp.getList();
		}
		
		return Action.INPUT;
	}
	
	
	
	//保存添加
	public String saveAdd(){
//		ManagerUtils mu = new ManagerUtils();
		String createUserId = ManagerUtils.getUserId();
		
		this.role.setCreateuserid(createUserId);
		
		RoleAddReq roleAddReq = new RoleAddReq();
		roleAddReq.setRole(role);
		roleAddReq.setActs(acts);
		
		this.roleServ.add(roleAddReq);
		
		this.msgs.add("角色添加成功");
		this.urls.put("角色列表", "role!list.do");	
	
		return WWAction.MESSAGE;
	}
	
	
	//保存修改
	public String saveEdit(){
		RoleEditReq roleEditReq = new RoleEditReq();
		roleEditReq.setRole(role);
		roleEditReq.setActs(acts);
		
		this.roleServ.edit(roleEditReq);
		this.msgs.add("角色修改成功");
		this.urls.put("角色列表", "role!list.do");		
		return WWAction.MESSAGE;
	}
	
	//删除角色
	public String delete(){
		RoleDelReq roleDelReq = new RoleDelReq();
		roleDelReq.setRoleid(roleid);
		
		this.roleServ.delete(roleDelReq);
		this.msgs.add("角色删除成功");
		this.urls.put("角色列表", "role!list.do");		
		return WWAction.MESSAGE;
	}
	
	



	public RoleInf getRoleServ() {
		return roleServ;
	}

	public void setRoleServ(RoleInf roleServ) {
		this.roleServ = roleServ;
	}

	public AuthInf getAuthServ() {
		return authServ;
	}

	public void setAuthServ(AuthInf authServ) {
		this.authServ = authServ;
	}

	public List getRoleList() {
		return roleList;
	}

	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}

	public List getAuthList() {
		return authList;
	}

	public void setAuthList(List authList) {
		this.authList = authList;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String[] getActs() {
		return acts;
	}
	public void setActs(String[] acts) {
		this.acts = acts;
	}

	public int getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(int isEdit) {
		this.isEdit = isEdit;
	}



	public String getCurrUserId() {
		return currUserId;
	}



	public void setCurrUserId(String currUserId) {
		this.currUserId = currUserId;
	}



	public String getRolename() {
		return rolename;
	}



	public void setRolename(String rolename) {
		this.rolename = rolename;
	}



	public String getAuth_type() {
		return auth_type;
	}



	public void setAuth_type(String auth_type) {
		this.auth_type = auth_type;
	}



	public List<Map> getRoleGroupListMap() {
		return roleGroupListMap;
	}



	public void setRoleGroupListMap(List<Map> roleGroupListMap) {
		this.roleGroupListMap = roleGroupListMap;
	}



	public String getRole_group() {
		return role_group;
	}

	public void setRole_group(String role_group) {
		this.role_group = role_group;
	}

	public String getRole_group_name() {
		return role_group_name;
	}

	public void setRole_group_name(String role_group_name) {
		this.role_group_name = role_group_name;
	}
	
	
}
