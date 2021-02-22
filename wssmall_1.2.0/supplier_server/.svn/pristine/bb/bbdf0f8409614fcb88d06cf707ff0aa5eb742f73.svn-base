package com.ztesoft.net.mall.core.workflow.core;

import params.adminuser.req.UserPermissionReq;
import params.adminuser.req.UserRoleReq;
import params.adminuser.resp.UserRoleResp;
import services.AdminUserInf;
import services.PermissionInf;

import com.ztesoft.net.app.base.core.model.Supplier;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.ISupplierManager;
import com.ztesoft.net.mall.core.service.SupplierStatus;
import com.ztesoft.net.mall.core.workflow.core.FlowBasicProcess;

public class RegisterApply_SynergismManager_Audit extends FlowBasicProcess {

	private ISupplierManager supplierManager;
	private AdminUserInf adminUserServ;
	private PermissionInf permissionServ;
	
	@Override
	public void doBusinisees(Integer flow_inst_id, String ref_obj_id) {
		Supplier supplier=this.supplierManager.getSupplierSequM1AndState0(ref_obj_id);
		String roles = "";
		UserRoleResp userRoleResp = new UserRoleResp();
		if("NO".equals(this.audit_state) ){//审批不通过
			if (supplier.getSupplier_type().equals(SupplierStatus.TYPE_1)) {// 供货商
				UserRoleReq userRoleReq = new UserRoleReq();
				userRoleReq.setStype(Consts.DC_PUBLIC_STYPE_98763);
				userRoleReq.setPkey(Consts.DC_PUBLIC_98763_PKEY);
				
				userRoleResp = adminUserServ.getRolesToUser(userRoleReq);
				if(userRoleResp != null){
					roles = userRoleResp.getRoles();
				}
			} else {//经销商
				UserRoleReq userRoleReq = new UserRoleReq();
				userRoleReq.setStype(Consts.DC_PUBLIC_STYPE_98764);
				userRoleReq.setPkey(Consts.DC_PUBLIC_98764_PKEY);
				
				userRoleResp = adminUserServ.getRolesToUser(userRoleReq);
				if(userRoleResp != null){
					roles = userRoleResp.getRoles();
				}
			}
			
			supplier.setSupplier_state(SupplierStatus.SUPPLIER_STATE_4);
		}else if("PASS".equals(this.audit_state)){//审批通过
			if (supplier.getSupplier_type().equals(SupplierStatus.TYPE_1)) {// 供货商
				UserRoleReq userRoleReq = new UserRoleReq();
				userRoleReq.setStype(Consts.DC_PUBLIC_STYPE_98762);
				userRoleReq.setPkey(Consts.DC_PUBLIC_98762_PKEY);
				
				userRoleResp = adminUserServ.getRolesToUser(userRoleReq);
				if(userRoleResp != null){
					roles = userRoleResp.getRoles();
				}
			} else {//经销商
				UserRoleReq userRoleReq = new UserRoleReq();
				userRoleReq.setStype(Consts.DC_PUBLIC_STYPE_98764);
				userRoleReq.setPkey(Consts.DC_PUBLIC_98764_PKEY);
				
				userRoleResp = adminUserServ.getRolesToUser(userRoleReq);
				if(userRoleResp != null){
					roles = userRoleResp.getRoles();
				}
				
				supplierManager.addMember(supplier);
			}
			
			supplier.setSupplier_state(SupplierStatus.SUPPLIER_STATE_2);
		}
		
		UserPermissionReq userPermissionReq = new UserPermissionReq();
		userPermissionReq.setUserid(supplier.getUserid());
		userPermissionReq.setRoleids(new String[] { roles });
		
		permissionServ.giveRolesToUser(userPermissionReq);
		
		this.baseDaoSupport.update("ES_SUPPLIER", supplier, " supplier_id='"+ supplier.getSupplier_id()+"'");
	}

	public ISupplierManager getSupplierManager() {
		return supplierManager;
	}

	public void setSupplierManager(ISupplierManager supplierManager) {
		this.supplierManager = supplierManager;
	}

	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}

	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}

	public PermissionInf getPermissionServ() {
		return permissionServ;
	}

	public void setPermissionServ(PermissionInf permissionServ) {
		this.permissionServ = permissionServ;
	}

}
