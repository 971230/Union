package services;

import params.adminuser.req.RoleReq;
import params.adminuser.req.UserActReq;
import params.adminuser.req.UserPermissionReq;
import params.adminuser.resp.RoleListResp;
import params.adminuser.resp.UserActResp;
import params.adminuser.resp.UserPermissionResp;

/**
 * 权限管理接口
 * @author hu.yi
 * @date 2013.12.23
 */
public interface PermissionInf {

	/**
	 * 给用户赋权
	 * @param userPermissionReq
	 */
	public UserPermissionResp giveRolesToUser(UserPermissionReq userPermissionReq);
	
	/**
	 * 获取用户权限
	 * @return
	 */
	public UserActResp getUesrAct(UserActReq userActReq);
	
	/**
	 * 获取角色列表
	 * @param roleReq
	 * @return
	 */
	public RoleListResp getUserRoles(RoleReq roleReq);
}
