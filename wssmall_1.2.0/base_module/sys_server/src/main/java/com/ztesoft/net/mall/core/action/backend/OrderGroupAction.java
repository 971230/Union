package com.ztesoft.net.mall.core.action.backend;

import java.util.ArrayList;
import java.util.List;

import params.adminuser.req.RoleListReq;
import params.adminuser.resp.RoleListResp;
import services.RoleInf;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.OrderGroupDef;
import com.ztesoft.net.mall.core.model.OrderGroupRel;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IOrderGroupManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;

public class OrderGroupAction extends WWAction{
	private IOrderGroupManager  orderGroupManager;
	private RoleInf roleServ;
	private IDcPublicInfoManager dcPublicInfoManager;
	private String group_name;
	private String group_id;
	private OrderGroupDef orderGroupDef;
	private OrderGroupRel orderGroupRel;
	private List roleList;
	private List orderGroupRole;
	private List orderGroupUser;
	
	private String userid;
	private String username;
	private String rolIdstr;
	private String userIdStr;
	private List groupTypeList;
	private String group_type;
	public String list(){
		 DcPublicInfoCacheProxy dcPublicCache=new DcPublicInfoCacheProxy(dcPublicInfoManager);
    	 this.groupTypeList = dcPublicCache.getList("1001");
		if("qr".equals(group_type)){
			group_type = "confirm";
		}
		this.webpage = this.orderGroupManager.GroupDefList(group_name,group_type, this.getPageSize(), this.getPage());
		return "list";
	}
    public String addOrderGroupDef(){
    	
    	return "addOrderGrouDef";
    }
    public String saveOrderGroupDef(){
    	try{
    		int groupNameCount =  this.orderGroupManager.getCountByGroupName(orderGroupDef.getGroup_name());
    		if(groupNameCount>0){
    			this.json = "{result:1,message:'分组名已存在'}";
    			return WWAction.JSON_MESSAGE;
    		}
    		int groupCodeCount = this.orderGroupManager.getCountByGroupCode(orderGroupDef.getGroup_code());
    		if(groupCodeCount>0){
    			this.json = "{result:1,message:'分组编码已存在'}";
    			return WWAction.JSON_MESSAGE;
    		}
    		this.orderGroupManager.insertOrderGroupDef(orderGroupDef);
    		this.json = "{result:0,message:'操作成功'}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}

		return WWAction.JSON_MESSAGE;
    
    }
    public String delOrderGroupDef(){
    	try{
    		this.orderGroupManager.deleteOrderGroupDef(group_id);
    		this.json = "{result:0,message:'操作成功'}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}
		return WWAction.JSON_MESSAGE;
    }
    public String showSelRole(){
    	orderGroupRole = this.orderGroupManager.getOrderGroupRole(this.group_id);//已有角色
    	RoleListResp roleResp = new RoleListResp();
    	RoleListReq roleListReq = new RoleListReq();
    	roleResp = roleServ.listRole(roleListReq);//该员工权限下所以可供选择的角色
    	roleList = new ArrayList();
    	if(roleResp != null){
    		roleList = roleResp.getRoleList();
    	}
    	return "roleList";
    }
    public String saveRole(){
    	
    	try{
    		this.orderGroupRel.setRole_id(this.rolIdstr);
    		this.orderGroupManager.saveOrderGroupRelRole(this.orderGroupRel);
    		this.json = "{result:0,message:'操作成功'}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}
		return WWAction.JSON_MESSAGE;
    }
    public String showSelUser(){
    	orderGroupUser = this.orderGroupManager.getOrderGroupUser(this.group_id);//已有角色
    	return  "userSel";
    }
    public String userList(){
    	this.webpage = this.orderGroupManager.getUserList(username, userid, this.getPageSize(), this.getPage());
    	return "userList";
    }
    public String saveUser(){
    	try{
    		this.orderGroupRel.setUserid(userIdStr);
    		this.orderGroupManager.saveOrderGroupRelUser(this.orderGroupRel);
    		this.json = "{result:0,message:'操作成功'}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:1,message:'"+e.getMessage()+"'}";
		}
		return WWAction.JSON_MESSAGE;
    }
    public String addOrderGroupRel(){
    	
    	return  "addOrderGroupRel";
    }
    public String saveOrderGroupRel(){
    	
    	return "addOrderGroupRel";
    }
    public String updateOrderGroupRel(){
    	
    	return "updateOrderGroupRel";
    }
	public IOrderGroupManager getOrderGroupManager() {
		return orderGroupManager;
	}
	public void setOrderGroupManager(IOrderGroupManager orderGroupManager) {
		this.orderGroupManager = orderGroupManager;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public OrderGroupDef getOrderGroupDef() {
		return orderGroupDef;
	}
	public void setOrderGroupDef(OrderGroupDef orderGroupDef) {
		this.orderGroupDef = orderGroupDef;
	}
	public OrderGroupRel getOrderGroupRel() {
		return orderGroupRel;
	}
	public void setOrderGroupRel(OrderGroupRel orderGroupRel) {
		this.orderGroupRel = orderGroupRel;
	}
	public List getRoleList() {
		return roleList;
	}
	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}
	public List getOrderGroupRole() {
		return orderGroupRole;
	}
	public void setOrderGroupRole(List orderGroupRole) {
		this.orderGroupRole = orderGroupRole;
	}
	public List getOrderGroupUser() {
		return orderGroupUser;
	}
	public void setOrderGroupUser(List orderGroupUser) {
		this.orderGroupUser = orderGroupUser;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRolIdstr() {
		return rolIdstr;
	}
	public void setRolIdstr(String rolIdstr) {
		this.rolIdstr = rolIdstr;
	}
	public String getUserIdStr() {
		return userIdStr;
	}
	public void setUserIdStr(String userIdStr) {
		this.userIdStr = userIdStr;
	}
	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}
	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}
	public List getGroupTypeList() {
		return groupTypeList;
	}
	public void setGroupTypeList(List groupTypeList) {
		this.groupTypeList = groupTypeList;
	}
	public String getGroup_type() {
		return group_type;
	}
	public void setGroup_type(String group_type) {
		this.group_type = group_type;
	}
	public RoleInf getRoleServ() {
		return roleServ;
	}
	public void setRoleServ(RoleInf roleServ) {
		this.roleServ = roleServ;
	}
    
    
}
