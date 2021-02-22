package com.ztesoft.net.mall.core.workflow.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import services.ModHandlerInf;

import com.ztesoft.net.app.base.core.model.Supplier;
import com.ztesoft.net.app.base.core.model.SupplierAccount;
import com.ztesoft.net.mall.core.service.ISupplierAccountManager;
import com.ztesoft.net.mall.core.service.ISupplierManager;
import com.ztesoft.net.mall.core.service.SupplierStatus;

public class ModAccount_SupplierManager_Audit extends FlowBasicProcess {

	private ModHandlerInf modInfoHandler;
	private ISupplierManager supplierManager;
	private ISupplierAccountManager supplierAccountManager;
	
	@Override
	public void doBusinisees(Integer flow_inst_id, String ref_obj_id) {
		SupplierAccount supplierAccount=this.supplierAccountManager.findAccountById(ref_obj_id);
		Supplier supplier=this.supplierManager.findSupplierById(supplierAccount.getSupplier_id());
		
		List<Map<String, String>> modInfo = this.modInfoHandler
				.qryModInfo(flow_inst_id,ref_obj_id);
		if ("PASS".equals(this.audit_state)) {// 审批通过

			Map fields = new HashMap();
			fields.put("state", 1);
			for (Map dcMap : modInfo) {
				String field_name = (String) dcMap.get("field_name");
				String new_change = (String) dcMap.get("new_change");

				fields.put(field_name, new_change);
			}

			Map where = new HashMap();
			where.put("account_id", ref_obj_id);

			this.baseDaoSupport.update("es_supplier_account", fields, where);

			Map fields1 = new HashMap();
			fields1.put("state", "00F");

			Map where1 = new HashMap();
			where1.put("flow_inst_id", flow_inst_id);
			this.baseDaoSupport.update("es_mod_info_inst", fields1, where1);
			
			
			if(supplier.getSupplier_state().equals(SupplierStatus.SUPPLIER_STATE_4)){//注册申请没有通过而进行修改的
				this.supplierManager.RegisterApplyAudit(supplier.getSupplier_id());
			}
		} else if ("NO".equals(this.audit_state)) {
			Map fields = new HashMap();
			fields.put("state", -1);

			Map where = new HashMap();
			where.put("account_id", ref_obj_id);

			this.baseDaoSupport.update("es_supplier_account", fields, where);
		}
	}

	public ModHandlerInf getModInfoHandler() {
		return modInfoHandler;
	}

	public void setModInfoHandler(ModHandlerInf modInfoHandler) {
		this.modInfoHandler = modInfoHandler;
	}

	public ISupplierManager getSupplierManager() {
		return supplierManager;
	}

	public void setSupplierManager(ISupplierManager supplierManager) {
		this.supplierManager = supplierManager;
	}

	public ISupplierAccountManager getSupplierAccountManager() {
		return supplierAccountManager;
	}

	public void setSupplierAccountManager(
			ISupplierAccountManager supplierAccountManager) {
		this.supplierAccountManager = supplierAccountManager;
	}
}
