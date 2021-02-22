package services;

import java.util.List;
import java.util.Map;

import params.adminuser.req.RoleAddReq;
import params.adminuser.req.RoleDelReq;
import params.adminuser.req.RoleEditReq;
import params.adminuser.req.RoleListReq;
import params.adminuser.req.RolePageReq;
import params.adminuser.req.RoleReq;
import params.adminuser.resp.RoleAddResp;
import params.adminuser.resp.RoleDelResp;
import params.adminuser.resp.RoleListResp;
import params.adminuser.resp.RolePageResp;
import params.adminuser.resp.RoleResp;

import com.ztesoft.net.app.base.core.model.Role;
import com.ztesoft.net.app.base.core.service.auth.IRoleManager;
import com.ztesoft.net.framework.database.Page;


public class RoleServ extends ServiceBase implements RoleInf{
	
	
	private IRoleManager roleManager;
	/**
	 * 读取所有角色分页列表 
	 * @return
	 */
	@Override
	public RolePageResp rolePage(RolePageReq rolePageReq) {
		// TODO Auto-generated method stub
		Page page = roleManager.rolePage(rolePageReq.getPageNo(), rolePageReq.getPageSize(), rolePageReq.getRole_name(),rolePageReq.getRole_code(),rolePageReq.getAuth_type(),rolePageReq.getRole_group());
		RolePageResp rolePageResp = new RolePageResp();
		rolePageResp.setWebPage(page);
		addReturn(rolePageReq, rolePageResp);
		return rolePageResp;
	}
	/**
	 * 读取所有角色列表 
	 * @return
	 */
	@Override
	public RoleListResp listRole(RoleListReq roleListReq) {
		 List<Role> list = roleManager.list(roleListReq.getRolename());
		 
		 RoleListResp roleResp = new RoleListResp();
		 roleResp.setRoleList(list);
		 addReturn(roleListReq, roleResp);
		 return roleResp;
	}

	
	@Override
	public RoleResp get(RoleReq roleReq){
		Role role = roleManager.get(roleReq.getRoleid());
		RoleResp roleResp = new RoleResp();
		roleResp.setRole(role);
		addReturn(roleReq, roleResp);
		return roleResp;
	}
	
	@Override
	public RoleAddResp add(RoleAddReq roleAddReq){
		
		roleManager.add(roleAddReq.getRole(),roleAddReq.getActs());
		
		RoleAddResp roleAddResp = new RoleAddResp();
		addReturn(roleAddReq, roleAddResp);
		return roleAddResp;
	}
	
	@Override
	public RoleAddResp edit(RoleEditReq roleEditReq){
		
		roleManager.edit(roleEditReq.getRole(),roleEditReq.getActs());
		
		RoleAddResp roleAddResp = new RoleAddResp();
		addReturn(roleEditReq, roleAddResp);
		return roleAddResp;
	}
	
	
	@Override
	public RoleDelResp delete(RoleDelReq roleDelReq){
		
		roleManager.delete(roleDelReq.getRoleid());
		
		RoleDelResp roleDelResp = new RoleDelResp();
		addReturn(roleDelReq, roleDelResp);
		return roleDelResp;
	}
	
	
	public IRoleManager getRoleManager() {
		return roleManager;
	}
	public void setRoleManager(IRoleManager roleManager) {
		this.roleManager = roleManager;
	}
	@Override
	public List<Map> getListRoleGroup() {
		
		return this.roleManager.getListRoleGroup();
	}


	
}