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



/**
 * 角色管理接口
 * @author hu.yi
 * @date 2013.12.23
 */
public interface RoleInf {
   
	/**
	 * 获取角色分页列表
	 */
	public RolePageResp rolePage(RolePageReq rolePageReq);
	/**
	 * 读取所有角色列表 
	 * @return
	 */
	public RoleListResp listRole(RoleListReq roleListReq);
	
	/**
	 * 根据角色id获取角色
	 * @param roleReq
	 * @return
	 */
	public RoleResp get(RoleReq roleReq);
	
	
	/**
	 * 新增角色
	 * @param roleAddReq
	 */
	public RoleAddResp add(RoleAddReq roleAddReq);
	
	
	/**
	 * 编辑角色
	 * @param roleAddReq
	 */
	public RoleAddResp edit(RoleEditReq roleEditReq);
	
	
	/**
	 * 删除角色
	 * @param roleReq
	 */
	public RoleDelResp delete(RoleDelReq roleDelReq);
	
	/**
	 * 获取所有角色分组
	 * @return
	 */
	public List<Map> getListRoleGroup();
	
}
