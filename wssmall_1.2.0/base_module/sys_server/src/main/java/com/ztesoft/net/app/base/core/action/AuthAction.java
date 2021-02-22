package com.ztesoft.net.app.base.core.action;

import java.util.ArrayList;
import java.util.List;

import params.adminuser.req.AuthActionAddReq;
import params.adminuser.req.AuthActionEditReq;
import params.adminuser.req.AuthDelReq;
import params.adminuser.req.AuthPageReq;
import params.adminuser.req.AuthReq;
import params.adminuser.req.RoleDataActionAddReq;
import params.adminuser.resp.AuthEditResp;
import params.adminuser.resp.AuthListResp;
import params.adminuser.resp.AuthResp;
import services.AuthInf;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 权限点
 * @author kingapex
 * 2010-11-9下午05:55:11
 */
public class AuthAction extends WWAction {
	private String name;
	private String authid;
	private String type;
	private int isEdit;
	private AuthInf authServ;
	private String[] menuids;
	private Integer[] appIds;
	private String[] btnIds;
	private String roleid;
	private String dataJSONObj;
	private com.ztesoft.net.app.base.core.model.AuthAction menuAuth;
	private com.ztesoft.net.app.base.core.model.AuthAction appAuth;
	private com.ztesoft.net.app.base.core.model.AuthAction btnAuth;
	private com.ztesoft.net.app.base.core.model.AuthAction dataAuth;
	private List authList;
	private String currUserId;
	public  String authRoleList(){
		AuthPageReq authPageReq = new AuthPageReq();
		authPageReq.setAct_id(authid);
		authPageReq.setName(name);
		authPageReq.setType(type);
		authPageReq.setPage(this.getPage());
		authPageReq.setPageSize(this.getPageSize());
		AuthListResp authListResp = authServ.authPage(authPageReq);
		this.currUserId = ManagerUtils.getUserId();
		if(authListResp != null){
			this.webpage = authListResp.getWebPage();
		}
		return "auth_list";
	}
	public  String authList(){
		AuthPageReq authPageReq = new AuthPageReq();
		authPageReq.setAct_id(authid);
		authPageReq.setName(name);
		authPageReq.setType(type);
		authPageReq.setPage(this.getPage());
		authPageReq.setPageSize(this.getPageSize());
		AuthListResp authListResp = authServ.authDataPage(authPageReq);
		this.currUserId = ManagerUtils.getUserId();
		if(authListResp != null){
			this.webpage = authListResp.getWebPage();
		}
		return "auth_list";
	}
	public String selAuth(){
		this.authRoleList();
		return "auth_sel";
	}
	public String add(){
		isEdit=0;
		return "input";
	} 
	
	
	public String edit(){
		isEdit=1;
		
		AuthReq authReq = new AuthReq();
		
		if(!StringUtil.isEmpty(roleid)){
			authReq.setRoleid(roleid);
			AuthResp authResp = this.authServ.getListByRoleId(authReq);
			getAuth(authResp);
		}else{
			authReq.setType(type);
			authReq.setAuthid(authid);
			AuthResp authResp = this.authServ.getList(authReq);
			getAuth(authResp);
		}
		return "input";
	}
	
	/**
	 * 获取实体
	 * @param authResp
	 */
	private void getAuth(AuthResp authResp){
		List<com.ztesoft.net.app.base.core.model.AuthAction> list = new ArrayList<com.ztesoft.net.app.base.core.model.AuthAction>();
		if(authResp != null){
			list = authResp.getList();
			if(!ListUtil.isEmpty(list)){
				for (int i = 0; i < list.size(); i++) {
					com.ztesoft.net.app.base.core.model.AuthAction auth = list.get(i);
					if(Consts.AUTH_MENU.equals(auth.getType())){
						menuAuth = auth;
						return;
					}else if(Consts.AUTH_APP.equals(auth.getType())){
						appAuth = auth;
						return;
					}else if(Consts.AUTH_BTN.equals(auth.getType())){
						btnAuth = auth;
						return;
					}else if(Consts.AUTH_DATA.equals(auth.getType())){
						dataAuth = auth;
						return;
					}
				}
			}
		}
	}
	
	
	public String save(){
		if(isEdit==0){
			return this.saveAdd();
		}else {
			return this.saveEdit();
		}
			
	}

	public String saveEdit(){
		ManagerUtils mu = new ManagerUtils();
		try{
			if(menuids != null){
				AuthActionEditReq authMenuActionReq = new AuthActionEditReq();
				authMenuActionReq.setName(name);
				authMenuActionReq.setType(Consts.AUTH_MENU);
				authMenuActionReq.setActid(authid);
				authMenuActionReq.setUserid(ManagerUtils.getUserId());
				authMenuActionReq.setObjvalue(StringUtil.arrayToString(menuids, ","));
				this.authServ.edit(authMenuActionReq);
			}
		
			if(appIds != null){
				AuthActionEditReq authAppActionReq = new AuthActionEditReq();
				authAppActionReq.setName(name);
				authAppActionReq.setType(Consts.AUTH_APP);
				authAppActionReq.setActid(authid);
				authAppActionReq.setUserid(ManagerUtils.getUserId());
				authAppActionReq.setObjvalue(StringUtil.arrayToString(appIds, ","));
				this.authServ.edit(authAppActionReq);
			}
		
			if(btnIds != null){
				AuthActionEditReq authBtnActionReq = new AuthActionEditReq();
				authBtnActionReq.setName(name);
				authBtnActionReq.setType(Consts.AUTH_BTN);
				authBtnActionReq.setActid(authid);
				authBtnActionReq.setUserid(ManagerUtils.getUserId());
				authBtnActionReq.setObjvalue(StringUtil.arrayToString(btnIds, ","));
				this.authServ.edit(authBtnActionReq);
			}
			
			if(dataJSONObj != null){
				RoleDataActionAddReq req = null;
				try {
					Class clazz = Class.forName("params.adminuser.req.RoleDataActionAddReq");
					req = (RoleDataActionAddReq) JsonUtil.fromJson(dataJSONObj, clazz);
					if(!StringUtils.isEmpty(name)){
						req.setRole_code(name);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if(req != null){
					this.authServ.editAuthData(req);
				}
			}
			
			this.json ="{result:1,authid:'"+authid+"'}";
		}catch(RuntimeException e){
			this.logger.info(e.getMessage(), e.fillInStackTrace());
			this.json ="{result:0,message:'"+e.getMessage()+"'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String saveAdd(){
		ManagerUtils mu = new ManagerUtils();
		try{
			AuthPageReq authPageReq = new AuthPageReq();
			authPageReq.setName(name);
			authPageReq.setPage(this.getPage());
			authPageReq.setPageSize(this.getPageSize());
			AuthListResp authListResp = authServ.authDataPage(authPageReq);
			Page page = authListResp.getWebPage();
			if(page!=null && page.getTotalCount()>0){
				this.json ="{result:0,message:'当前权限名称已经被使用，请使用其他权限名'}";
				return WWAction.JSON_MESSAGE;
			}
			AuthEditResp authEditResp = new AuthEditResp();
			if(menuids != null){
				AuthActionAddReq authMenuActionReq = new AuthActionAddReq();
				authMenuActionReq.setName(name);
				authMenuActionReq.setType(Consts.AUTH_MENU);
				authMenuActionReq.setUserid(ManagerUtils.getUserId());
				authMenuActionReq.setObjvalue(StringUtil.arrayToString(menuids, ","));
				authEditResp = this.authServ.add(authMenuActionReq);
			}
			if(appIds != null){
				AuthActionAddReq authAppActionReq = new AuthActionAddReq();
				if(!StringUtil.isEmpty(authEditResp.getAuthid())){
					authAppActionReq.setActid(authEditResp.getAuthid());
				}
				authAppActionReq.setName(name);
				authAppActionReq.setType(Consts.AUTH_APP);
				authAppActionReq.setUserid(ManagerUtils.getUserId());
				authAppActionReq.setObjvalue(StringUtil.arrayToString(appIds, ","));
				authEditResp = this.authServ.add(authAppActionReq);
			}
			if(btnIds != null){
				AuthActionAddReq authAppActionReq = new AuthActionAddReq();
				if(!StringUtil.isEmpty(authEditResp.getAuthid())){
					authAppActionReq.setActid(authEditResp.getAuthid());
				}
				authAppActionReq.setName(name);
				authAppActionReq.setType(Consts.AUTH_BTN);
				authAppActionReq.setUserid(ManagerUtils.getUserId());
				authAppActionReq.setObjvalue(StringUtil.arrayToString(btnIds, ","));
				authEditResp = this.authServ.add(authAppActionReq);
			}
			if(dataJSONObj != null && !"{\"role_code\":\"\",\"id\":\"\",\"flow_node\":\"\",\"order_region\":\"\",\"order_origin\":\"\",\"lock_status\":\"\",\"carry_mode\":\"\",\"normal_flag\":\"\",\"bespoke_flag\":\"\",\"society_flag\":\"\",\"son_order_type\":\"\",\"deal_org_type\":\"\",\"create_time\":\"\",\"create_user\":\"\",\"create_ip\":\"\",\"update_time\":\"\",\"update_user\":\"\",\"update_ip\":\"\",\"product_sub_type\":\"\",\"pay_mode\":\"\",\"handle_type\":\"\",\"product_busi_type\":\"\",\"develop_attribute\":\"\",\"product_brand\":\"\",\"orderexp_catalog\":\"0000,\",\"ord_busicty\":\"\"}".equals(dataJSONObj)){
				RoleDataActionAddReq req = null;
				try {
					Class clazz = Class.forName("params.adminuser.req.RoleDataActionAddReq");
					req = (RoleDataActionAddReq) JsonUtil.fromJson(dataJSONObj, clazz);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if(req != null){
					req.setRole_code(name);
					authEditResp = this.authServ.addAuthData(req);
				}
			}
			if(menuids == null && appIds == null && btnIds == null && dataJSONObj == null){
				AuthActionAddReq authActionReq = new AuthActionAddReq();
				if(!StringUtil.isEmpty(authEditResp.getAuthid())){
					authActionReq.setActid(authEditResp.getAuthid());
				}
				authActionReq.setName(name);
				authActionReq.setType(Consts.AUTH_MENU);
				authActionReq.setUserid(ManagerUtils.getUserId());
				authEditResp = this.authServ.add(authActionReq);
			}
			
			
			if(authEditResp != null){
				authid = authEditResp.getAuthid();
			}
			this.json ="{result:1,authid:'"+authid+"'}";
		}catch(RuntimeException e){
			this.logger.info(e.getMessage(), e.fillInStackTrace());
			this.json ="{result:0,message:'"+e.getMessage()+"'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	public String delete(){
		try{
			if(type.equals(Consts.AUTH_DATA)){
				RoleDataActionAddReq req = new RoleDataActionAddReq();
				req.setId(authid);
				this.authServ.deleteAuthData(req);
			}else{
				AuthDelReq authDelReq = new AuthDelReq();
				authDelReq.setAuthid(this.authid);
				this.authServ.delete(authDelReq);
			}
			this.json ="{result:1,authid:'"+authid+"'}";
		}catch(RuntimeException e){
			this.logger.info(e.getMessage(), e.fillInStackTrace());
			this.json ="{result:0,message:'"+e.getMessage()+"'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthid() {
		return authid;
	}

	public void setAuthid(String authid) {
		this.authid = authid;
	}

	public int getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(int isEdit) {
		this.isEdit = isEdit;
	}


	public AuthInf getAuthServ() {
		return authServ;
	}


	public void setAuthServ(AuthInf authServ) {
		this.authServ = authServ;
	}

	public String[] getMenuids() {
		return menuids;
	}
	public void setMenuids(String[] menuids) {
		this.menuids = menuids;
	}
	public com.ztesoft.net.app.base.core.model.AuthAction getMenuAuth() {
		return menuAuth;
	}


	public void setMenuAuth(com.ztesoft.net.app.base.core.model.AuthAction menuAuth) {
		this.menuAuth = menuAuth;
	}


	public com.ztesoft.net.app.base.core.model.AuthAction getAppAuth() {
		return appAuth;
	}


	public void setAppAuth(com.ztesoft.net.app.base.core.model.AuthAction appAuth) {
		this.appAuth = appAuth;
	}


	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public Integer[] getAppIds() {
		return appIds;
	}

	public void setAppIds(Integer[] appIds) {
		this.appIds = appIds;
	}

	public String[] getBtnIds() {
		return btnIds;
	}

	public void setBtnIds(String[] btnIds) {
		this.btnIds = btnIds;
	}

	public com.ztesoft.net.app.base.core.model.AuthAction getBtnAuth() {
		return btnAuth;
	}

	public void setBtnAuth(com.ztesoft.net.app.base.core.model.AuthAction btnAuth) {
		this.btnAuth = btnAuth;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List getAuthList() {
		return authList;
	}
	public void setAuthList(List authList) {
		this.authList = authList;
	}
	public String getCurrUserId() {
		return currUserId;
	}
	public void setCurrUserId(String currUserId) {
		this.currUserId = currUserId;
	}
	public com.ztesoft.net.app.base.core.model.AuthAction getDataAuth() {
		return dataAuth;
	}
	public void setDataAuth(com.ztesoft.net.app.base.core.model.AuthAction dataAuth) {
		this.dataAuth = dataAuth;
	}
	public String getDataJSONObj() {
		return dataJSONObj;
	}
	public void setDataJSONObj(String dataJSONObj) {
		this.dataJSONObj = dataJSONObj;
	}
	
}
