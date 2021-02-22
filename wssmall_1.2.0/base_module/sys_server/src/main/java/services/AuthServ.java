package services;

import java.util.List;
import java.util.Map;

import params.adminuser.req.AuthActionAddReq;
import params.adminuser.req.AuthActionEditReq;
import params.adminuser.req.AuthDelReq;
import params.adminuser.req.AuthListReq;
import params.adminuser.req.AuthPageReq;
import params.adminuser.req.AuthReq;
import params.adminuser.req.RoleDataActionAddReq;
import params.adminuser.resp.AuthActionEditResp;
import params.adminuser.resp.AuthDelResp;
import params.adminuser.resp.AuthEditResp;
import params.adminuser.resp.AuthListResp;
import params.adminuser.resp.AuthResp;

import com.ztesoft.net.app.base.core.model.AuthAction;
import com.ztesoft.net.app.base.core.model.RoleData;
import com.ztesoft.net.app.base.core.service.auth.IAuthActionManager;
import com.ztesoft.net.app.base.core.service.auth.IAuthDataManager;
import com.ztesoft.net.framework.database.Page;

public class AuthServ extends ServiceBase implements AuthInf{
	
	private IAuthActionManager authActionManager;
	
	private IAuthDataManager authDataManager;
	
	@Override
	public AuthResp getListByRoleId(AuthReq authReq){
		
		List<AuthAction> list = authActionManager.getListByRoleId(authReq.getRoleid());
		AuthResp authResp = new AuthResp();
		authResp.setList(list);
		addReturn(authReq, authResp);
		
		return authResp;
		
	}
	@Override
	public AuthResp getList(AuthReq authReq){
		List<AuthAction> list = authActionManager.getList(authReq.getAuthid(),authReq.getType());
		AuthResp authResp = new AuthResp();
		authResp.setList(list);
		addReturn(authReq, authResp);
		
		return authResp;
	}
	
	
	@Override
	public AuthResp getByRoleId(AuthReq authReq){
		AuthAction authAction = authActionManager.getByRoleId(authReq.getRoleid());
		AuthResp authResp = new AuthResp();
		authResp.setAuthAction(authAction);
		addReturn(authReq, authResp);
		
		return authResp;
	}
	
	@Override
	public AuthResp get(AuthReq authReq){
		AuthAction authAction = authActionManager.get(authReq.getAuthid());
		AuthResp authResp = new AuthResp();
		authResp.setAuthAction(authAction);
		addReturn(authReq, authResp);
		
		return authResp;
	}
	
	@Override
	public AuthActionEditResp edit(AuthActionEditReq authActionEditReq){
		AuthAction authAction = new AuthAction();
		authAction.setActid(authActionEditReq.getActid());
		authAction.setName(authActionEditReq.getName());
		authAction.setObjvalue(authActionEditReq.getObjvalue());
		authAction.setType(authActionEditReq.getType());
		authAction.setUserid(authActionEditReq.getUserid());
		
		authActionManager.edit(authAction);
		
		AuthActionEditResp authActionEditResp = new AuthActionEditResp();
		addReturn(authActionEditReq, authActionEditResp);
		return authActionEditResp;
	}
	
	public AuthActionEditResp delete(AuthActionEditReq authActionEditReq){
		AuthAction authAction = new AuthAction();
		authAction.setActid(authActionEditReq.getActid());
		authAction.setName(authActionEditReq.getName());
		authAction.setObjvalue(authActionEditReq.getObjvalue());
		authAction.setType(authActionEditReq.getType());
		authAction.setUserid(authActionEditReq.getUserid());
		
		authActionManager.edit(authAction);
		
		AuthActionEditResp authActionEditResp = new AuthActionEditResp();
		addReturn(authActionEditReq, authActionEditResp);
		return authActionEditResp;
	}
	
	@Override
	public AuthEditResp add(AuthActionAddReq authActionAddReq){
		AuthAction authAction = new AuthAction();
		authAction.setActid(authActionAddReq.getActid());
		authAction.setName(authActionAddReq.getName());
		authAction.setObjvalue(authActionAddReq.getObjvalue());
		authAction.setType(authActionAddReq.getType());
		authAction.setUserid(authActionAddReq.getUserid());
		
		String authid = authActionManager.add(authAction);
		
		AuthEditResp authEditResp = new AuthEditResp();
		authEditResp.setAuthid(authid);
		addReturn(authActionAddReq, authEditResp);
		return authEditResp;
	}
	
	@Override
	public AuthDelResp deleteAuthData(RoleDataActionAddReq req){
		authDataManager.delete(req.getId());
		AuthDelResp authDelResp = new AuthDelResp();
		addReturn(req, authDelResp);
		return authDelResp;
	}
	
	@Override
	public AuthEditResp editAuthData(RoleDataActionAddReq req) {
		RoleData roleData = new RoleData();
		roleData.setId(req.getId());
		roleData.setBespoke_flag(req.getBespoke_flag());
		roleData.setCarry_mode(req.getCarry_mode());
		roleData.setDeal_org_type(req.getDeal_org_type());
		roleData.setDevelop_attribute(req.getDevelop_attribute());
		roleData.setFlow_node(req.getFlow_node());
		roleData.setHandle_type(req.getHandle_type());
		roleData.setLock_status(req.getLock_status());
		roleData.setNormal_flag(req.getNormal_flag());
		roleData.setOrder_origin(req.getOrder_origin());
		roleData.setOrder_region(req.getOrder_region());
		roleData.setPay_mode(req.getPay_mode());
		roleData.setProduct_brand(req.getProduct_brand());
		roleData.setProduct_busi_type(req.getProduct_busi_type());
		roleData.setProduct_sub_type(req.getProduct_sub_type());
		roleData.setRole_code(req.getRole_code());
		roleData.setSociety_flag(req.getSociety_flag());
		roleData.setSon_order_type(req.getSon_order_type());
		roleData.setSp_prod_type(req.getSp_prod_type());
		roleData.setOrder_model(req.getOrder_model());
		roleData.setOrderexp_catalog(req.getOrderexp_catalog());
		roleData.setOrd_receive_user(req.getOrd_receive_user());
		roleData.setOrd_lock_user(req.getOrd_lock_user());
		roleData.setBusicty_id(req.getBusicty_id());
		authDataManager.edit(roleData);
		AuthEditResp authEditResp = new AuthEditResp();
		authEditResp.setAuthid(req.getId());
		addReturn(req, authEditResp);
		return authEditResp;
	}
	@Override
	public AuthEditResp addAuthData(RoleDataActionAddReq req) {
		RoleData roleData = new RoleData();
		roleData.setBespoke_flag(req.getBespoke_flag());
		roleData.setCarry_mode(req.getCarry_mode());
		roleData.setDeal_org_type(req.getDeal_org_type());
		roleData.setDevelop_attribute(req.getDevelop_attribute());
		roleData.setFlow_node(req.getFlow_node());
		roleData.setHandle_type(req.getHandle_type());
		roleData.setLock_status(req.getLock_status());
		roleData.setNormal_flag(req.getNormal_flag());
		roleData.setOrder_origin(req.getOrder_origin());
		roleData.setOrder_region(req.getOrder_region());
		roleData.setPay_mode(req.getPay_mode());
		roleData.setProduct_brand(req.getProduct_brand());
		roleData.setProduct_busi_type(req.getProduct_busi_type());
		roleData.setProduct_sub_type(req.getProduct_sub_type());
		roleData.setRole_code(req.getRole_code());
		roleData.setSociety_flag(req.getSociety_flag());
		roleData.setSon_order_type(req.getSon_order_type());
		roleData.setSp_prod_type(req.getSp_prod_type());
		roleData.setOrd_receive_user(req.getOrd_receive_user());
		roleData.setOrder_model(req.getOrder_model());
		roleData.setOrderexp_catalog(req.getOrderexp_catalog());
		roleData.setOrd_lock_user(req.getOrd_lock_user());
		roleData.setBusicty_id(req.getBusicty_id());
		String id = authDataManager.add(roleData);
		AuthEditResp authEditResp = new AuthEditResp();
		authEditResp.setAuthid(id);
		addReturn(req, authEditResp);
		return authEditResp;
	}
	
	@Override
	public AuthDelResp delete(AuthDelReq authDelReq){
		authActionManager.delete(authDelReq.getAuthid());
		
		AuthDelResp authDelResp = new AuthDelResp();
		addReturn(authDelReq, authDelResp);
		return authDelResp;
	}
	
	
	@Override
	public AuthListResp list(AuthListReq authListReq){
		
		List list = authActionManager.list(authListReq.getStr());
		
		AuthListResp authListResp = new AuthListResp();
		authListResp.setList(list);
		addReturn(authListReq, authListResp);
		
		return authListResp;
	}
	
	@Override
	public AuthListResp authPage(AuthPageReq authPageReq) {
		Page page = authActionManager.authPage(authPageReq.getAct_id(), authPageReq.getName(), authPageReq.getType(), authPageReq.getPage(), authPageReq.getPageSize());
		AuthListResp authListResp = new AuthListResp();
		authListResp.setWebPage(page);
		addReturn(authPageReq, authListResp);
		return authListResp;
	}
	
	@Override
	public AuthListResp authDataPage(AuthPageReq authPageReq) {
		Page page = authDataManager.authListPage(authPageReq);
		AuthListResp authListResp = new AuthListResp();
		authListResp.setWebPage(page);
		addReturn(authPageReq, authListResp);
		return authListResp;
	}
	
	public IAuthActionManager getAuthActionManager() {
		return authActionManager;
	}

	public void setAuthActionManager(IAuthActionManager authActionManager) {
		this.authActionManager = authActionManager;
	}
	
	public IAuthDataManager getAuthDataManager() {
		return authDataManager;
	}
	
	public void setAuthDataManager(IAuthDataManager authDataManager) {
		this.authDataManager = authDataManager;
	}
	@Override
	public List<Map> roleGroupType() {
		return authActionManager.roleGroupType();
	}
	
}