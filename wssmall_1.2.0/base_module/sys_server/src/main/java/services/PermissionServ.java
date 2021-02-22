package services;

import java.util.List;

import params.adminuser.req.RoleReq;
import params.adminuser.req.UserActReq;
import params.adminuser.req.UserPermissionReq;
import params.adminuser.resp.RoleListResp;
import params.adminuser.resp.UserActResp;
import params.adminuser.resp.UserPermissionResp;

import com.ztesoft.net.app.base.core.model.AuthAction;
import com.ztesoft.net.app.base.core.service.auth.IPermissionManager;


public class PermissionServ extends ServiceBase implements PermissionInf{
	
	private IPermissionManager permissionManager;
	
	@Override
	public UserPermissionResp giveRolesToUser(UserPermissionReq userPermissionReq){
		
		permissionManager.giveRolesToUser(userPermissionReq.getUserid(), userPermissionReq.getRoleids());
		
		UserPermissionResp userPermissionResp = new UserPermissionResp();
		addReturn(userPermissionReq, userPermissionResp);
		return userPermissionResp;
	}
	
	
	@Override
	public UserActResp getUesrAct(UserActReq userActReq){
		
		List<AuthAction> list = permissionManager.getUesrAct(userActReq.getUserid(),userActReq.getActtype());
		
		UserActResp userActResp = new UserActResp();
		userActResp.setList(list);
		addReturn(userActReq, userActResp);
		
		return userActResp;
	}
	
	
	@Override
	public RoleListResp getUserRoles(RoleReq roleReq){
		
		List list = permissionManager.getUserRoles(roleReq.getRoleid());
		
		RoleListResp roleResp = new RoleListResp();
		roleResp.setList(list);
		addReturn(roleReq, roleResp);
		
		return roleResp;
	}
	
	
	public IPermissionManager getPermissionManager() {
		return permissionManager;
	}

	public void setPermissionManager(IPermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}
}